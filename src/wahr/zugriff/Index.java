package wahr.zugriff;

import ansicht.n2.xF.*;
import nonBlock.aktion.lesen.*;
import nonBlock.aussehen.*;
import nonBlock.formwandler.*;
import wahr.physisch.*;

import java.awt.image.*;
import java.io.*;
import java.util.*;

public class Index
{
	private static final HashMap<String, String> teilNamen = new HashMap<>();
	private static final HashMap<String, Object> geladen = new HashMap<>();

	public static void laden()
	{
		String index = Lader.gibText("Index");
		String[] indexe = index.split("\n");
		for(int i = 0; i < indexe.length; i++)
			if(!indexe[i].isEmpty() && !indexe[i].startsWith("/"))
			{
				String[] eintrag = indexe[i].split("=");
				teilNamen.put(eintrag[0], eintrag[1]);
			}
	}

	private static String replace(String s, String... replace)
	{
		String[] s1 = s.split("%");
		StringBuilder s2 = new StringBuilder();
		for(int i = 0; i < s1.length; i++)
			if(i % 2 == 0)
				s2.append(s1[i]);
			else
				s2.append(replace[Integer.parseInt(s1[i])]);
		return s2.toString();
	}

	private static String bauName(String ordner, String name)
	{
		return Lader.gibText(teilNamen.get(ordner) + File.separator + name.replace("/", File.separator));
	}

	public static String gibPfad(String ordner)
	{
		return teilNamen.get(ordner);
	}

	public static String gibText(String ordner, String name, String... replace)
	{
		if(replace.length <= 0)
		{
			if(geladen.containsKey(name))
				return (String) geladen.get(name);
			String s = bauName(ordner, name);
			geladen.put(name, s);
			return s;
		}
		else
			return replace(bauName(ordner, name), replace);
	}

	public static LadeTeil gibLadeTeil(String name, String... replace)
	{
		if(replace.length <= 0)
		{
			if(geladen.containsKey(name))
				return (LadeTeil) geladen.get(name);
			LadeTeil s = new LadeTeil(bauName("Ladeteile", name));
			geladen.put(name, s);
			return s;
		}
		else
			return new LadeTeil(replace(bauName("Ladeteile", name), replace));
	}

	public static StandardAussehen gibStandardAussehen(String name, String... replace)
	{
		if(replace.length <= 0)
		{
			if(geladen.containsKey(name))
				return (StandardAussehen) geladen.get(name);
			StandardAussehen s = new StandardAussehen(bauName("Ladeteile", name));
			geladen.put(name, s);
			return s;
		}
		else
			return new StandardAussehen(replace(bauName("Ladeteile", name), replace));
	}

	public static AlternateStandard gibAlternateStandard(String name, String... replace)
	{
		if(replace.length <= 0)
		{
			if(geladen.containsKey(name))
				return (AlternateStandard) geladen.get(name);
			AlternateStandard s = new AlternateStandard(bauName("Ladeteile", name));
			geladen.put(name, s);
			return s;
		}
		else
			return new AlternateStandard(replace(bauName("Ladeteile", name), replace));
	}

	public static LadeMove gibLadeMove(boolean seq, String name, String... replace)
	{
		if(replace.length <= 0)
		{
			if(geladen.containsKey(name))
				return (LadeMove) geladen.get(name);
			LadeMove s = new LadeMove(name, bauName(seq ? "Sequenzen" : "Moves", name));
			geladen.put(name, s);
			return s;
		}
		else
			return new LadeMove(name, replace(bauName(seq ? "Sequenzen" : "Moves", name), replace));
	}

	public static XFBT gibXFBT(String name, String... replace)
	{
		if(replace.length <= 0)
		{
			if(geladen.containsKey(name))
				return (XFBT) geladen.get(name);
			XFBT s = new XFBT(bauName("Blocks", name));
			geladen.put(name, s);
			return s;
		}
		else
			return new XFBT(replace(bauName("Blocks", name), replace));
	}

	public static XFBT gibXFBT(String name, int seite, int max)
	{
		if(geladen.containsKey(name + seite))
			return (XFBT) geladen.get(name + seite);
		ArrayList<BufferedImage> imgs = new ArrayList<>();
		for(int i = 1; i <= max; i++)
		{
			String text = teilNamen.get("Blocks") + File.separator +
					name.replace("/", File.separator) + " " + i + ".png";
			if(new File(text).exists())
				imgs.add((BufferedImage)Lader.gibBild(text));
		}
		XFBT s = new XFBT(imgs, seite);
		geladen.put(name + seite, s);
		return s;
	}

	public static LadeFWATeil gibLadeFWATeil(String name, String... replace)
	{
		if(replace.length <= 0)
		{
			if(geladen.containsKey(name))
				return (LadeFWATeil) geladen.get(name);
			LadeFWATeil s = new LadeFWATeil(bauName("FWA", name));
			geladen.put(name, s);
			return s;
		}
		else
			return new LadeFWATeil(replace(bauName("FWA", name), replace));
	}

	public static LadeControlledMove gibLadeControlledMove(String name, String... replace)
	{
		int xe = name.indexOf('-');
		if(xe == -1)
			throw new RuntimeException("LadeControlledMove name muss - enthalten");
		if(replace.length <= 0)
		{
			if(geladen.containsKey(name))
				return (LadeControlledMove) geladen.get(name);
			LadeControlledMove s = new LadeControlledMove(bauName("FWA", name.substring(0, xe)),
					name.substring(xe + 1));
			geladen.put(name, s);
			return s;
		}
		else
			return new LadeControlledMove(replace(bauName("FWA", name.substring(0, xe)), replace),
					name.substring(xe + 1));
	}
}