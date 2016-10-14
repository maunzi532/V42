package edit;

import frame.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UI
{
	public JPanel left;
	public JPanel right;

	public JPanel upperPanel;
	public JList<Type> editingChooser;

	public JButton actualize;
	public JCheckBox ac1;
	public JCheckBox fl2;
	public JCheckBox xr3;

	public String createNew;
	public String chargeInNew;
	public Type newEChosen;
	public int newE;
	public boolean saveAndActualize;
	public boolean changeSettings;
	public boolean focus;
	public int scroll;
	public boolean reload;

	public UI(JFrame frame, TA2 ta)
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

		left.setLayout(new BorderLayout());
		upperPanel = new JPanel();
		left.add(upperPanel, BorderLayout.PAGE_START);
		editingChooser = new JList<>();
		/*editingChooser.addListSelectionListener(e ->
		{
			newEChosen = editingChooser.getSelectedValue();
			newE = editingChooser.getSelectedIndex();
		});*/
		upperPanel.add(editingChooser);
		JButton addEC = new JButton("Neu");
		//addEC.addActionListener(e -> createNew = JOptionPane.showInputDialog(upperPanel, "Name"));
		upperPanel.add(addEC);
		JButton ladEC = new JButton("Laden");
		upperPanel.add(ladEC);
		//ladEC.addActionListener(e -> chargeInNew = JOptionPane.showInputDialog(upperPanel, "Name"));

		actualize = new JButton("Aktualisieren");
		//actualize.addActionListener(e -> saveAndActualize = true);
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
	}

	public void flt()
	{
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
		if(changeSettings)
		{
			changeSettings = false;
			//ac1v = ac1.isSelected();
			//fl2v = fl2.isSelected();
			//xr3v = xr3.isSelected();
		}
		if(saveAndActualize)
		{
			saveAndActualize = false;
			reload = false;
			//for(EditerTab ed : editerTabs.get(currentE))
				//ed.save();
			reload();
		}
		if(reload)
		{
			reload = false;
			reload();
		}
	}

	public void reload()
	{

	}
}