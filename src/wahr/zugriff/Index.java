package wahr.zugriff;

import nonBlock.aussehen.*;
import ansicht.n2.xF.*;
import nonBlock.aktion.seq.*;
import nonBlock.aktion.move.*;
import wahr.physisch.*;

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

	public static String gibText(String name, String... replace)
	{
		if(replace.length <= 0)
		{
			if(geladen.containsKey(name))
				return (String) geladen.get(name);
			String s = Lader.gibText(teilNamen.get(name));
			geladen.put(name, s);
			return s;
		}
		else
			return replace(Lader.gibText(teilNamen.get(name)), replace);
	}

	public static LadeTeil gibLadeTeil(String name, String... replace)
	{
		if(replace.length <= 0)
		{
			if(geladen.containsKey(name))
				return (LadeTeil) geladen.get(name);
			LadeTeil s = new LadeTeil(Lader.gibText(teilNamen.get(name)));
			geladen.put(name, s);
			return s;
		}
		else
			return new LadeTeil(replace(Lader.gibText(teilNamen.get(name)), replace));
	}

	public static StandardAussehen gibStandardAussehen(String name, String... replace)
	{
		if(replace.length <= 0)
		{
			if(geladen.containsKey(name))
				return (StandardAussehen) geladen.get(name);
			StandardAussehen s = new StandardAussehen(Lader.gibText(teilNamen.get(name)));
			geladen.put(name, s);
			return s;
		}
		else
			return new StandardAussehen(replace(Lader.gibText(teilNamen.get(name)), replace));
	}

	public static AlternateStandard gibAlternateStandard(String name, String... replace)
	{
		if(replace.length <= 0)
		{
			if(geladen.containsKey(name))
				return (AlternateStandard) geladen.get(name);
			AlternateStandard s = new AlternateStandard(Lader.gibText(teilNamen.get(name)));
			geladen.put(name, s);
			return s;
		}
		else
			return new AlternateStandard(replace(Lader.gibText(teilNamen.get(name)), replace));
	}

	public static LadeSequenz gibLadeSequenz(String name, String... replace)
	{
		if(replace.length <= 0)
		{
			if(geladen.containsKey(name))
				return (LadeSequenz) geladen.get(name);
			LadeSequenz s = new LadeSequenz(Lader.gibText(teilNamen.get(name)));
			geladen.put(name, s);
			return s;
		}
		else
			return new LadeSequenz(replace(Lader.gibText(teilNamen.get(name)), replace));
	}

	public static LadeMove gibLadeMove(String name, String... replace)
	{
		if(replace.length <= 0)
		{
			if(geladen.containsKey(name))
				return (LadeMove) geladen.get(name);
			LadeMove s = new LadeMove(name, Lader.gibText(teilNamen.get(name)));
			geladen.put(name, s);
			return s;
		}
		else
			return new LadeMove(name, replace(Lader.gibText(teilNamen.get(name)), replace));
	}

	public static XFBT gibXFBT(String name, String... replace)
	{
		if(replace.length <= 0)
		{
			if(geladen.containsKey(name))
				return (XFBT) geladen.get(name);
			XFBT s = new XFBT(Lader.gibText(teilNamen.get(name)));
			geladen.put(name, s);
			return s;
		}
		else
			return new XFBT(replace(Lader.gibText(teilNamen.get(name)), replace));
	}
}