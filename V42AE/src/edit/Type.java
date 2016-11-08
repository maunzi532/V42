package edit;

import achsen.*;
import indexLader.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.List;
import java.util.stream.*;
import javax.swing.*;

public class Type extends JPanel
{
	String name;
	File location;

	JTabbedPane tabPanel;
	EditerTab sTab;
	List<EditerTab> dTabs;
	List<EditerTab> fTabs;
	ButtonGroup drehfilesGroup;
	List<AView> views;
	JPanel viewsPanel;
	JPanel innerViewsPanel;
	List<EditerTab> editerTabs;

	File v42c;
	AView currentView;
	AView switchToView;
	boolean toView;
	boolean reloadVSet;
	public boolean saveAndActualize;
	public boolean reload;
	public boolean plusView;
	public EditerTab currentTab;
	public String newD;
	public String newF;
	int lastTab;

	public Type(File location, List<AchsenK1> liste, AEKam aeKam) throws IOException
	{
		this.location = location;
		name = location.getName();
		setLayout(new BorderLayout());
		viewsPanel = new JPanel(new BorderLayout());
		add(viewsPanel, BorderLayout.PAGE_START);
		tabPanel = new JTabbedPane();
		add(tabPanel, BorderLayout.CENTER);

		File[] files = location.listFiles();
		assert files != null;
		File v42s = null;
		List<File> v42d = new ArrayList<>();
		List<File> v42f = new ArrayList<>();
		for(File file : files)
		{
			String name = file.getName();
			if(name.endsWith(".v42c"))
				v42c = file;
			else if(name.endsWith(".v42s"))
				v42s = file;
			else if(name.endsWith(".v42d"))
				v42d.add(file);
			else if(name.endsWith(".v42f"))
				v42f.add(file);
		}
		String config;
		if(v42c != null)
			config = new String(Files.readAllBytes(v42c.toPath()), Charset.forName("UTF-8"));
		else
		{
			v42c = new File(location.getPath() + File.separator + "C.v42c");
			StringBuilder sb = new StringBuilder();
			sb.append("0;\n");
			sb.append("10, 0, 0, 0, 0, 0, 0, ");
			if(v42d.size() > 0)
				sb.append(v42d.get(0).getName());
			else
				sb.append("keines");
			sb.append(';');
			config = sb.toString();
			Files.write(v42c.toPath(), config.getBytes("UTF-8"));
		}
		if(v42s == null)
		{
			v42s = new File(location.getPath() + File.separator + "Standard.v42s");
			//noinspection ResultOfMethodCallIgnored
			v42s.createNewFile();
		}

		editerTabs = new ArrayList<>();
		sTab = new EditerTab(v42s, this);
		editerTabs.add(sTab);
		tabPanel.add(sTab);
		tabPanel.setTabComponentAt(lastTab, sTab.getOverS());
		currentTab = sTab;
		dTabs = new ArrayList<>();
		drehfilesGroup = new ButtonGroup();
		for(File file : v42d)
		{
			EditerTab tab = new EditerTab(file, this);
			dTabs.add(tab);
			editerTabs.add(tab);
			tabPanel.add(tab);
			lastTab++;
			tabPanel.setTabComponentAt(lastTab, tab.getOverD(drehfilesGroup));
		}
		fTabs = new ArrayList<>();
		for(File file : v42f)
		{
			EditerTab tab = new EditerTab(file, this);
			fTabs.add(tab);
			editerTabs.add(tab);
			tabPanel.add(tab);
			lastTab++;
			tabPanel.setTabComponentAt(lastTab, tab.getOverF());
		}
		tabPanel.addChangeListener(e -> currentTab = (EditerTab) tabPanel.getSelectedComponent());
		add(tabPanel);

		views = new ArrayList<>();
		String[] lines = LC2.decommentandcut(config).split(";");
		int dch;
		try
		{
			dch = Integer.parseInt(lines[0]);
		}
		catch(Exception e)
		{
			dch = 0;
		}
		for(int i = 1; i < lines.length; i++)
		{
			try
			{
				AView av = new AView(name, lines[i], liste, aeKam);
				views.add(av);
				if(dch == i - 1)
					currentView = av;
			}
			catch(NumberFormatException ignored){}
		}
		if(views.size() == 0)
			views.add(new AView(name, liste, aeKam));
		if(currentView == null)
			currentView = views.get(0);

		JButton minusView = new JButton("-");
		minusView.setBackground(Color.BLACK);
		viewsPanel.add(minusView, BorderLayout.LINE_START);
		innerViewsPanel = new JPanel(new GridLayout(1, 0));
		viewsPanel.add(innerViewsPanel, BorderLayout.CENTER);
		JButton plusView1 = new JButton("+");
		plusView1.addActionListener(e -> plusView = true);
		viewsPanel.add(plusView1, BorderLayout.LINE_END);
		for(AView v : views)
			v.addActionListener(e -> switchToView = v);
		views.forEach(innerViewsPanel::add);
		currentView.aktivieren(dTabs, fTabs);
		toView = true;
		reload(null);
	}

	public void reload(AView view)
	{
		Standard1 lsta = new Standard1();
		ErrorVial standardErrors = lsta.argh(sTab.gSave());
		if(standardErrors.worked())
		{
			HashMap<String, AnzTeil1> lteils = new HashMap<>();
			if(view != null)
			{
				//Alternate1 lalt = new Alternate1()
			}
			else
			{
				HashMap<String, Alternate1> lalts = new HashMap<>();
				for(EditerTab dt : dTabs)
					lalts.put(dt.name, new Alternate1(dt.gSave(), lsta.achsenAnz()));
				for(EditerTab ft : fTabs)
				{
					//lteils.put(ft.name, new AnzTeil1(ft.gSave()));
					AnzTeil1 lanz = new AnzTeil1();
					ErrorVial fErrors = lanz.argh(ft.gSave(), lsta);
					ft.applyVial(fErrors);
					lteils.put(ft.name, lanz);
				}
				for(AView av : views)
					av.actualize(lsta, lalts, lteils);
			}
		}
		else
			sTab.applyVial(standardErrors);
	}

	public void saveConfig(AEKam aeKam, boolean x)
	{
		if(currentView != null)
		{
			if(x)
				currentView.avac(aeKam);
			StringBuilder sb = new StringBuilder();
			int index = views.indexOf(currentView);
			if(index < 0)
				return;
			sb.append(index).append(';');
			for(int i = 0; i < views.size(); i++)
				sb.append(views.get(i).saveText());
			try
			{
				Files.write(v42c.toPath(), sb.toString().getBytes("UTF-8"));
			}catch(IOException e)
			{
				JOptionPane.showMessageDialog(this, "Behinderter Fehler: " + e.getMessage());
			}
		}
	}

	public void flt(AEKam aeKam, List<AchsenK1> liste)
	{
		if(plusView)
		{
			AView av = new AView(name, liste, aeKam);
			av.addActionListener(e1 -> switchToView = av);
			views.add(av);
			innerViewsPanel.add(av);
			innerViewsPanel.updateUI();
			plusView = false;
		}
		if(switchToView != null)
		{
			currentView.avac(aeKam);
			currentView = switchToView;
			currentView.aktivieren(dTabs, fTabs);
			switchToView = null;
			toView = true;
			reloadVSet = false;
		}
		if(toView)
		{
			aeKam.beweg(currentView.kamDistance, currentView.kamDreh, currentView.kamMoved, currentView.ak1.position);
			toView = false;
		}
		if(reloadVSet)
		{
			currentView.sichtbar = new ArrayList<>();
			dTabs.stream().filter(dTab -> dTab.radioButton.isSelected())
					.forEach(dTab -> currentView.drehfile = dTab.name);
			currentView.text();
			currentView.sichtbar.addAll(fTabs.stream().filter(fTab -> fTab.checkBox.isSelected())
					.map(fTab -> fTab.name).collect(Collectors.toList()));
			reloadVSet = false;
			reload = true;
		}
		if(newD != null)
		{
			if(!newD.isEmpty())
			{
				try
				{
					File f = new File(location.getPath() + File.separator + newD);
					if(!f.exists())
					{
						//noinspection ResultOfMethodCallIgnored
						f.createNewFile();
						EditerTab tab = new EditerTab(f, this);
						editerTabs.add(tab);
						dTabs.add(tab);
						tabPanel.add(tab);
						lastTab++;
						tabPanel.setTabComponentAt(lastTab, tab.getOverD(drehfilesGroup));
					}
				}
				catch(IOException e)
				{
					JOptionPane.showMessageDialog(this, "Behinderter Fehler: " + e.toString());
				}
			}
			newD = null;
		}
		if(newF != null)
		{
			if(!newF.isEmpty())
			{
				try
				{
					File f = new File(location.getPath() + File.separator + newF);
					if(!f.exists())
					{
						//noinspection ResultOfMethodCallIgnored
						f.createNewFile();
						EditerTab tab = new EditerTab(f, this);
						editerTabs.add(tab);
						fTabs.add(tab);
						tabPanel.add(tab);
						lastTab++;
						tabPanel.setTabComponentAt(lastTab, tab.getOverF());
					}
				}
				catch(IOException e)
				{
					JOptionPane.showMessageDialog(this, "Behinderter Fehler: " + e.toString());
				}
			}
			newF = null;
		}
		if(saveAndActualize)
		{
			saveAndActualize = false;
			reload = false;
			for(EditerTab ed : editerTabs)
				try
				{
					ed.save();
				}catch(IOException e)
				{
					JOptionPane.showMessageDialog(this, "Behinderter Fehler: " + e.getMessage());
				}
			reload(null);
		}
		if(reload)
		{
			reload = false;
			reload(null);
		}
	}

	@Override
	public String toString()
	{
		return name;
	}
}