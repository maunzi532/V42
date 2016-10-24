package edit;

import achsen.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import javax.swing.*;

public class EditerTab extends JScrollPane
{
	File file;
	public String name;
	public JTextArea plane;
	public JPanel over;
	public JCheckBox checkBox;
	public JRadioButton radioButton;
	public Type von;

	public EditerTab(File file, Type von) throws IOException
	{
		this.file = file;
		this.von = von;
		name = file.getName();
		plane = new JTextArea();
		plane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		plane.setFont(new Font("Courier New", Font.PLAIN, 15));
		plane.setTabSize(4);
		setViewportView(plane);
		plane.setText(new String(Files.readAllBytes(Paths.get(file.getPath())), Charset.forName("UTF-8")));
	}

	public JPanel getOverS()
	{
		if(over != null)
			return over;
		over = new JPanel();
		over.add(new JLabel(name));
		JButton plusD = new JButton("+D");
		plusD.addActionListener(e -> von.newD = JOptionPane.showInputDialog(this, "Name der .v42d Datei") + ".v42d");
		over.add(plusD);
		JButton plusF = new JButton("+F");
		plusF.addActionListener(e -> von.newF = JOptionPane.showInputDialog(this, "Name der .v42f Datei") + ".v42f");
		over.add(plusF);
		return over;
	}

	public JPanel getOverD(ButtonGroup group)
	{
		if(over != null)
			return over;
		over = new JPanel();
		over.add(new JLabel(name));
		radioButton = new JRadioButton();
		radioButton.addActionListener(e -> von.reloadVSet = true);
		group.add(radioButton);
		over.add(radioButton);
		return over;
	}

	public JPanel getOverF()
	{
		if(over != null)
			return over;
		over = new JPanel();
		over.add(new JLabel(name));
		checkBox = new JCheckBox();
		checkBox.addActionListener(e -> von.reloadVSet = true);
		over.add(checkBox);
		return over;
	}

	public void save() throws IOException
	{
		String text = plane.getText();
		if(radioButton != null)
		{
			text = Alternate1.auto(text, Standard1.achsennummern(von.sTab.plane.getText()));
			plane.setText(text);
		}
		Files.write(Paths.get(file.getPath()), text.getBytes(Charset.forName("UTF-8")));
	}
}