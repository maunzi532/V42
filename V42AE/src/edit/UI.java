package edit;

import frame.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class UI
{
	public JPanel left;
	public JPanel right;

	public JPanel upperPanel;
	public JFileChooser filech;
	public JPanel luPanel;
	public JPanel ruPanel;
	public ArrayList<Type> types;
	public JList<Type> typesL;

	public JButton actualize;
	public JCheckBox ac1;
	public JCheckBox fl2;
	public JCheckBox xr3;

	public File directoryForNew;
	public File chargeInNew;
	public boolean switchType;
	public Type currentType;
	public boolean saveAndActualize;
	public boolean changeSettings;
	public boolean ac1v;
	public boolean fl2v;
	public boolean xr3v;
	public boolean focus;
	public int scroll;
	public boolean reload;
	public int rWidth;
	public int rHeight;

	public AView currentAView()
	{
		return null;
	}

	public EditerTab currentEditerTab()
	{
		return null;
	}

	public UI(JFrame frame, TA2 ta)
	{
		JPanel pl = new JPanel(new GridLayout(1, 2));
		frame.setContentPane(pl);
		left = new JPanel();
		pl.add(left);

		//setup right
		right = new JPanel();
		right.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent mouseEvent)
			{
				focus = true;
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

		//setup left
		left.setLayout(new BorderLayout());
		upperPanel = new JPanel();
		left.add(upperPanel, BorderLayout.PAGE_START);
		luPanel = new JPanel();
		upperPanel.add(luPanel);
		types = new ArrayList<>();
		typesL = new JList<>();
		typesL.addListSelectionListener(e -> switchType = true);
		luPanel.add(typesL);
		filech = new JFileChooser();
		filech.setCurrentDirectory(new File("Ladeteile1"));
		filech.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		filech.setAcceptAllFileFilterUsed(false);
		JButton addEC = new JButton("Neu");
		addEC.addActionListener(e ->
		{
			int re = filech.showOpenDialog(left);
			if(re == JFileChooser.APPROVE_OPTION)
				directoryForNew = filech.getSelectedFile();
			else
				directoryForNew = filech.getCurrentDirectory();
			File fileNew = new File(directoryForNew.getPath() + File.separator +
					JOptionPane.showInputDialog(left, "Name"));
			if(!fileNew.exists())
				chargeInNew = fileNew;
		});
		luPanel.add(addEC);
		JButton ladEC = new JButton("Laden");
		luPanel.add(ladEC);
		ladEC.addActionListener(e ->
		{
			int re = filech.showOpenDialog(left);
			if(re == JFileChooser.APPROVE_OPTION)
				chargeInNew = filech.getSelectedFile();
		});

		ruPanel = new JPanel();
		upperPanel.add(ruPanel);

		actualize = new JButton("Aktualisieren");
		actualize.addActionListener(e -> saveAndActualize = true);
		ruPanel.add(actualize);

		//add view settings
		ac1 = new JCheckBox("Achsen");
		fl2 = new JCheckBox("Flaechen", true);
		xr3 = new JCheckBox("Linien");
		ac1.addActionListener(e -> changeSettings = true);
		fl2.addActionListener(e -> changeSettings = true);
		xr3.addActionListener(e -> changeSettings = true);
		ruPanel.add(ac1);
		ruPanel.add(fl2);
		ruPanel.add(xr3);
		changeSettings = true;
	}

	public void flt()
	{
		rWidth = right.getWidth();
		rHeight =  right.getHeight();
		if(scroll != 0)
		{
			Start.kam.scroll(scroll / 60d);
			scroll = 0;
		}
		if(focus)
			right.requestFocusInWindow();
		else if(right.hasFocus()/* && editerTabs.size() > 0*/)
		{
			//editerTabs.get(currentE).get(tabPanel.getSelectedIndex()).plane.requestFocusInWindow();
			Start.ta.away();
		}
		if(chargeInNew != null)
		{
			try
			{
				if(!chargeInNew.exists())
					chargeInNew.mkdir();
				Type new1 = new Type(chargeInNew, Start.ak1s);
				boolean ok = true;
				for(Type type1 : types)
					if(type1.location.equals(chargeInNew))
						ok = false;
				if(ok)
				{
					types.add(new1);
					typesL.setListData(types.toArray(new Type[types.size()]));
					typesL.setSelectedValue(new1, true);
					switchType = true;
				}
				else
					JOptionPane.showMessageDialog(left, "Existiert bereits");
			}catch(IOException e)
			{
				JOptionPane.showMessageDialog(left, "Behinderter Fehler: " + e.getMessage());
			}
			chargeInNew = null;
		}
		if(switchType)
		{
			if(currentType != null)
				left.remove(currentType);
			currentType = typesL.getSelectedValue();
			left.add(currentType, BorderLayout.CENTER);
			left.updateUI();
			switchType = false;
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
			//for(EditerTab ed : editerTabs.get(currentE))
				//ed.save();
			currentType.reload();
		}
		if(reload)
		{
			reload = false;
			currentType.reload();
		}
	}
}