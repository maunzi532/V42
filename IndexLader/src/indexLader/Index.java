package indexLader;

import java.io.*;
import java.util.*;

public class Index
{
	public static final HashMap<String, String> teilNamen = new HashMap<>();
	public static final HashMap<String, Object> geladen = new HashMap<>();
	public static String textPrefix;

	public static void laden(String textPrefix1)
	{
		textPrefix = textPrefix1;
		String index = Lader.gibText("Index");
		String[] indexe = index.split("\n");
		for(int i = 0; i < indexe.length; i++)
			if(!indexe[i].isEmpty() && !indexe[i].startsWith("/"))
			{
				String[] eintrag = indexe[i].split("=");
				teilNamen.put(eintrag[0], eintrag[1]);
			}
	}

	public static String bauName(String ordner, String name)
	{
		return Lader.gibText(teilNamen.get(ordner) + File.separator + name.replace("/", File.separator));
	}

	public static String gibPfad(String ordner)
	{
		return teilNamen.get(ordner);
	}

	public static String gibText(String ordner, String name)
	{
		if(geladen.containsKey(textPrefix + name))
			return (String) geladen.get(textPrefix + name);
		String s = bauName(ordner, name);
		geladen.put(textPrefix + name, s);
		return s;
	}
}