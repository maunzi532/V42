package edit;

import achsen.*;
import indexLader.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import k4.*;
import frame.*;

public class LRPL
{
	public JPanel left;
	public JPanel right;

	public JPanel upperPanel;
	public JList<String> editingChooser;
	public List<AchsenK1> edit;

	public List<List<EditerTab>> editerTabs;
	public JTabbedPane tabPanel;
	public List<ButtonGroup> whichD;

	public JButton actualize;
	public JCheckBox ac1;
	public JCheckBox fl2;
	public JCheckBox xr3;

	public String createNew;
	public String chargeInNew;
	public int currentE;
	public String currentEChosen;
	public String newEChosen;
	public int newE;
	public boolean saveAndActualize;
	public String createFileD;
	public String createFileF;
	public boolean changeSettings;
	public boolean ac1v;
	public boolean fl2v;
	public boolean xr3v;
	public boolean reload;
	public boolean focus;
	public int scroll;

	public LRPL(JFrame frame, TA2 ta)
	{
		JPanel pl = new JPanel(new GridLayout(1, 2));
		frame.setContentPane(pl);
		left = new JPanel();
		pl.add(left);
		right = new JPanel();
		right.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent mouseEvent)
			{
				focus =  true;
			}

			@Override
			public void mouseExited(MouseEvent mouseEvent)
			{
				focus = false;
			}
		});
		right.addMouseWheelListener(new MouseAdapter()
		{
			@Override
			public void mouseWheelMoved(MouseWheelEvent e)
			{
				scroll = e.getUnitsToScroll();
			}
		});
		pl.add(right);
		ta.addToFrame(right);

		left.setLayout(new BorderLayout());
		upperPanel = new JPanel();
		left.add(upperPanel, BorderLayout.PAGE_START);
		editingChooser = new JList<>();
		editingChooser.addListSelectionListener(e ->
		{
			newEChosen = editingChooser.getSelectedValue();
			newE = editingChooser.getSelectedIndex();
		});
		upperPanel.add(editingChooser);
		JButton addEC = new JButton("Neu");
		addEC.addActionListener(e -> createNew = JOptionPane.showInputDialog(upperPanel, "Name"));
		upperPanel.add(addEC);
		JButton ladEC = new JButton("Laden");
		upperPanel.add(ladEC);
		ladEC.addActionListener(e -> chargeInNew = JOptionPane.showInputDialog(upperPanel, "Name"));

		actualize = new JButton("Aktualisieren");
		actualize.addActionListener(e -> saveAndActualize = true);
		upperPanel.add(actualize);
		ac1 = new JCheckBox("Achsen");
		fl2 = new JCheckBox("Flaechen", true);
		xr3 = new JCheckBox("Linien");
		ac1.addActionListener(e -> changeSettings = true);
		fl2.addActionListener(e -> changeSettings = true);
		xr3.addActionListener(e -> changeSettings = true);
		upperPanel.add(ac1);
		upperPanel.add(fl2);
		upperPanel.add(xr3);
		changeSettings = true;

		tabPanel = new JTabbedPane();
		left.add(tabPanel, BorderLayout.CENTER);
		edit = new ArrayList<>();
		editerTabs = new ArrayList<>();
		whichD = new ArrayList<>();
	}

	public void flt() throws IOException
	{
		if(scroll != 0)
		{
			Start.kam.scroll(scroll / 60d);
			scroll = 0;
		}
		if(focus)
			right.requestFocusInWindow();
		else if(right.hasFocus() && editerTabs.size() > 0)
		{
			editerTabs.get(currentE).get(tabPanel.getSelectedIndex()).plane.requestFocusInWindow();
			Start.ta.away();
		}
		if(createNew != null)
		{
			File f = new File(Lader4.bauName("Ladeteile1", createNew));
			if(!edit.stream().anyMatch(e -> createNew.equals(e.nameLook)) && !f.exists())
			{
				try
				{
					f.mkdir();
					new File(Lader4.bauName("Ladeteile1", createNew, "Standard.v42s")).createNewFile();
				}
				catch(IOException e)
				{
					JOptionPane.showMessageDialog(upperPanel, "Datei konnte nicht erstellt werden",
							"Behinderter Fehler", JOptionPane.ERROR_MESSAGE);
				}
				AchsenK1 ak1 = new AchsenK1(new K4(), new Drehung(), createNew);
				edit.add(ak1);
				editingChooser.setListData(edit.stream().map(e -> e.nameLook).toArray(String[]::new));
				editingChooser.setSelectedValue(createNew, true);
				newEChosen = createNew;
				Start.ak1s.put(createNew, ak1);
			}
			createNew = null;
		}
		if(chargeInNew != null)
		{
			if(!edit.stream().anyMatch(e -> chargeInNew.equals(e.nameLook)))
			{
				AchsenK1 ak1 = new AchsenK1(new K4(), new Drehung(), chargeInNew);
				edit.add(ak1);
				editingChooser.setListData(edit.stream().map(e -> e.nameLook).toArray(String[]::new));
				editingChooser.setSelectedValue(chargeInNew, true);
				newEChosen = chargeInNew;
				Start.ak1s.put(chargeInNew, ak1);
			}
			chargeInNew = null;
		}
		if(newEChosen != null && (currentEChosen == null || !currentEChosen.equals(newEChosen)))
		{
			currentEChosen = newEChosen;
			currentE = newE;
			newEChosen = null;
			tabPanel.removeAll();
			if(editerTabs.size() > currentE)
			{
				List<EditerTab> tabs = editerTabs.get(currentE);
				for(int i = 0; i < tabs.size(); i++)
				{
					EditerTab ta = tabs.get(i);
					JPanel pl = new JPanel();
					pl.setLayout(new BoxLayout(pl, BoxLayout.LINE_AXIS));
					pl.add(new JLabel(ta.name));
					if(ta.radioButton != null)
						pl.add(ta.radioButton);
					else if(ta.checkBox != null)
						pl.add(ta.checkBox);
					tabPanel.add(ta);
					tabPanel.setTabComponentAt(i, pl);
				}
			}
			else
			{
				editerTabs.add(new ArrayList<>());
				whichD.add(new ButtonGroup());
				File[] fa = new File(Lader4.bauName("Ladeteile1", currentEChosen)).listFiles();
				if(fa == null)
					currentEChosen = null;
				else
				{
					boolean setD = true;
					for(int i = 0; i < fa.length; i++)
					{
						setD = addTab(fa[i], i, setD);
						if("Standard.v42s".equals(fa[i].getName()))
							tabPanel.setSelectedIndex(tabPanel.getTabCount() - 1);
					}
				}
			}
		}
		if(createFileD != null)
		{
			File f = new File(Lader4.bauName("Ladeteile1", currentEChosen, createFileD + ".v42d"));
			if(!f.exists())
				try
				{
					f.createNewFile();
					addTab(f, tabPanel.getTabCount(), false);
				}catch(IOException e)
				{
					JOptionPane.showMessageDialog(upperPanel, "Datei konnte nicht erstellt werden",
							"Behinderter Fehler", JOptionPane.ERROR_MESSAGE);
				}
			createFileD = null;
		}
		if(createFileF != null)
		{
			File f = new File(Lader4.bauName("Ladeteile1", currentEChosen, createFileF + ".v42f"));
			if(!f.exists())
				try
				{
					f.createNewFile();
					addTab(f, tabPanel.getTabCount(), false);
				}catch(IOException e)
				{
					JOptionPane.showMessageDialog(upperPanel, "Datei konnte nicht erstellt werden",
							"Behinderter Fehler", JOptionPane.ERROR_MESSAGE);
				}
			createFileF = null;
		}
		if(changeSettings)
		{
			changeSettings = false;
			ac1v = ac1.isSelected();
			fl2v = fl2.isSelected();
			xr3v = xr3.isSelected();
		}
		if(saveAndActualize)
		{
			saveAndActualize = false;
			reload = false;
			for(EditerTab ed : editerTabs.get(currentE))
				ed.save();
			reload();
		}
		if(reload)
		{
			reload = false;
			reload();
		}
	}

	private boolean addTab(File f, int num, boolean dToSet) throws IOException
	{
		EditerTab ed = new EditerTab(f);
		editerTabs.get(currentE).add(ed);
		JPanel pl = new JPanel();
		pl.setLayout(new BoxLayout(pl, BoxLayout.LINE_AXIS));
		pl.add(new JLabel(f.getName()));
		if(f.getName().endsWith(".v42d"))
		{
			JRadioButton rd = new JRadioButton();
			rd.addActionListener(e -> reload = true);
			if(dToSet)
			{
				rd.setSelected(true);
				dToSet = false;
			}
			whichD.get(currentE).add(rd);
			ed.radioButton = rd;
			pl.add(rd);
		}
		else if(f.getName().endsWith(".v42f"))
		{
			JCheckBox ch = new JCheckBox();
			ch.addActionListener(e -> reload = true);
			ed.checkBox = ch;
			pl.add(ch);
		}
		else if(f.getName().endsWith(".v42s"))
		{
			JButton newD = new JButton("+d");
			JButton newF = new JButton("+f");
			newD.addActionListener(e -> createFileD = JOptionPane.showInputDialog(tabPanel, "Neue .v42d Datei"));
			newF.addActionListener(e -> createFileF = JOptionPane.showInputDialog(tabPanel, "Neue .v42f Datei"));
			pl.add(newD);
			pl.add(newF);
		}
		tabPanel.addTab(f.getName(), ed);
		tabPanel.setTabComponentAt(num, pl);
		return dToSet;
	}

	private void reload()
	{
		ArrayList<String> fs = new ArrayList<>();
		String theD = null;
		for(EditerTab ed : editerTabs.get(currentE))
		{
			if(ed.radioButton != null && ed.radioButton.isSelected())
				theD = ed.name;
			if(ed.checkBox != null && ed.checkBox.isSelected())
				fs.add(ed.name);
		}
		if(currentEChosen != null)
		{
			if(theD == null)
			{
				Start.ak1s.get(currentEChosen).reload();
				actualize.setBackground(Color.WHITE);
			}
			else try
			{
				Start.ak1s.get(currentEChosen).reload(false, currentEChosen,
						theD, fs.toArray(new String[fs.size()]));
				actualize.setBackground(Color.WHITE);
			}catch(Exception e)
			{
				actualize.setBackground(Color.RED);
				Start.ak1s.get(currentEChosen).reload();
			}
		}
	}
}