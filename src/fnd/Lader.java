package fnd;

import javax.imageio.stream.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class Lader
{
	private static final HashMap<String, String> texte = new HashMap<>();
	private static final HashMap<String, Image> bilder = new HashMap<>();

	public static String gibText(String key)
	{
		if(texte.containsKey(key))
			return texte.get(key);
		return neuText(key);
	}

	public static Image gibBild(String key)
	{
		if(bilder.containsKey(key))
			return bilder.get(key);
		return neuBild(key);
	}

	private static String neuText(String key)
	{
		String val = umwandelnT(new File(key.replace('/', File.separatorChar)));
		val = val.replace("\r", "");
		texte.put(key, val);
		return val;
	}

	private static Image neuBild(String key)
	{
		Image val = umwandelnB(new File(key.replace('/', File.separatorChar)));
		bilder.put(key, val);
		return val;
	}

	private static String umwandelnT(File file)
	{
		try
		{
			FileReader frr = new FileReader(file);
			char[] c = new char[(int) file.length()];
			frr.read(c);
			frr.close();
			int c1 = c.length;
			do
			{
				c1--;
			} while(c[c1] == 0);//length ist falsch
			char[] c2 = new char[c1];
			System.arraycopy(c, 0, c2, 0, c1);
			return new String(c2);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private static Image umwandelnB(File file)
	{
		try
		{
			FileImageInputStream fl = new FileImageInputStream(file);
			byte[] by = new byte[(int) file.length()];
			fl.read(by);
			ImageIcon ico = new ImageIcon(by);
			fl.close();
			return ico.getImage();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}