package edit;

import achsen.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class Type extends JPanel implements LC1
{
	String name;
	File location;

	JTabbedPane tabPanel;
	//List<EditerTab> editerTabs;
	EditerTab sTab;
	List<EditerTab> dTabs;
	List<EditerTab> fTabs;
	ButtonGroup drehfilesGroup;
	List<AView> views;
	AView currentView;
	JPanel viewsPanel;
	JPanel innerViewsPanel;

	AView switchToView;

	public Type(File location, List<AchsenK1> liste) throws IOException
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
		File v42c = null;
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
			v42s.createNewFile();
		}

		int lastTab = 0;
		sTab = new EditerTab(v42s);
		tabPanel.add(sTab);
		tabPanel.setTabComponentAt(lastTab, sTab.getOverS());
		dTabs = new ArrayList<>();
		drehfilesGroup = new ButtonGroup();
		for(File file : v42d)
		{
			EditerTab tab = new EditerTab(file);
			dTabs.add(tab);
			tabPanel.add(tab);
			lastTab++;
			tabPanel.setTabComponentAt(lastTab, tab.getOverD(drehfilesGroup));
		}
		fTabs = new ArrayList<>();
		for(File file : v42f)
		{
			EditerTab tab = new EditerTab(file);
			fTabs.add(tab);
			tabPanel.add(tab);
			lastTab++;
			tabPanel.setTabComponentAt(lastTab, tab.getOverF());
		}
		add(tabPanel);

		views = new ArrayList<>();
		String[] lines = decommentandcut(config).split(";");
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
				AView av = new AView(name, lines[i], liste);
				views.add(av);
				if(dch == i)
					currentView = av;
			}
			catch(NumberFormatException ignored){}
		}
		if(views.size() == 0)
			views.add(new AView(name, liste));
		if(currentView == null)
			currentView = views.get(0);

		JButton minusView = new JButton("-");
		//minusView.addActionListener();
		viewsPanel.add(minusView, BorderLayout.LINE_START);
		innerViewsPanel = new JPanel(new GridLayout(1, 0));
		viewsPanel.add(innerViewsPanel, BorderLayout.CENTER);
		JButton plusView = new JButton("+");
		plusView.addActionListener(e ->
		{
			AView av = new AView(name, liste);
			views.add(av);
			innerViewsPanel.add(av);
			innerViewsPanel.updateUI();
		});
		viewsPanel.add(plusView, BorderLayout.LINE_END);
		views.forEach(innerViewsPanel::add);
	}

	public void reload()
	{
		//TODO sonst sieht man nix
	}

	@Override
	public String toString()
	{
		return name;
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		Type type = (Type) o;

		return !(location != null ? !location.equals(type.location) : type.location != null);

	}

	@Override
	public int hashCode()
	{
		return location != null ? location.hashCode() : 0;
	}
}