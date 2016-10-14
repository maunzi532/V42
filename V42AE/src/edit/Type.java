package edit;

import achsen.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import javax.swing.*;

public class Type implements LC1
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

	public Type(File location) throws IOException
	{
		this.location = location;
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
		String config = null;
		if(v42c == null)
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
		if(config == null)
			config = new String(Files.readAllBytes(v42c.toPath()), Charset.forName("UTF-8"));

		tabPanel = new JTabbedPane();
		sTab = new EditerTab(v42s);
		tabPanel.add(sTab);
		for(File file : v42d)
		{
			EditerTab tab = new EditerTab(file);
			JRadioButton rd = new JRadioButton();
			//rd.addActionListener(e -> reload = true);
			drehfilesGroup.add(rd);
			tab.radioButton = rd;
			dTabs.add(tab);
			tabPanel.add(tab);
		}
		for(File file : v42f)
		{
			EditerTab tab = new EditerTab(file);
			tab.checkBox = new JCheckBox();
			dTabs.add(tab);
			tabPanel.add(tab);
		}

		String[] lines = decommentandcut(config).split(";");
		String dch = lines[0];
		for(int i = 1; i < lines.length; i++)
		{
			views.add(new AView(name, lines[i]));
		}
	}
}