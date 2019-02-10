package indexLader;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;
import javax.imageio.*;

public class Lader
{
	private static final HashMap<String, String> texte = new HashMap<>();
	private static final HashMap<String, Image> bilder = new HashMap<>();

	public static File jarLocation;
	public static boolean jar;
	public static Path dataRoot1;
	public static ZipFile dataRoot2;

	public static void inJarCheck()
	{
		try
		{
			URL url = Lader.class.getResource("Lader.class");
			jar = url.getProtocol().equals("jar");
			String dir = "";
			if(jar)
			{
				String jarname = url.getPath().substring(url.getPath().indexOf(":") + 1, url.getPath().indexOf("!"));
				jarLocation = new File(jarname).getParentFile();
				dataRoot2 = new ZipFile(jarname);
			}
			else
			{
				dataRoot1 = Paths.get(dir);
				jarLocation = new File(dir);
			}
		}catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}

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
		//String val = umwandelnT(new File(key.replace('/', File.separatorChar)));
		String val = text(key);
		val = val.replace("\r", "");
		texte.put(key, val);
		return val;
	}

	private static Image neuBild(String key)
	{
		//Image val = umwandelnB(new File(key.replace('/', File.separatorChar)));
		Image val = bild(key);
		bilder.put(key, val);
		return val;
	}

	public static String text(String location)
	{
		try
		{
			if(jar)
			{
				InputStream is = dataRoot2.getInputStream(dataRoot2.getEntry(location));
				ByteArrayOutputStream result = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int length;
				while((length = is.read(buffer)) != -1)
				{
					result.write(buffer, 0, length);
				}
				return result.toString(StandardCharsets.UTF_8.name());
			}
			else
			{
				return new String(Files.readAllBytes(dataRoot1.resolve(location)), Charset.forName("UTF-8"));
			}
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static BufferedImage bild(String location)
	{
		try
		{
			if(jar)
				return ImageIO.read(dataRoot2.getInputStream(dataRoot2.getEntry(location)));
			else
				return ImageIO.read(dataRoot1.resolve(dataRoot1.resolve(location)).toFile());
		}
		catch(IOException e)
		{
			return null;
		}
	}
}