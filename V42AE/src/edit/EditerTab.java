package edit;

import java.awt.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import javax.swing.*;

public class EditerTab extends JScrollPane
{
	File file;
	String name;
	public JTextArea plane;
	public JCheckBox checkBox;
	public JRadioButton radioButton;

	public EditerTab(File file) throws IOException
	{
		this.file = file;
		name = file.getName();
		plane = new JTextArea();
		plane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		plane.setFont(new Font("Courier New", Font.PLAIN, 15));
		plane.setTabSize(4);
		setViewportView(plane);
		plane.setText(new String(Files.readAllBytes(Paths.get(file.getPath())), Charset.forName("UTF-8")));
	}

	public void save() throws IOException
	{
		Files.write(Paths.get(file.getPath()), plane.getText().getBytes(Charset.forName("UTF-8")));
	}
}