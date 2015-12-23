package wahr.zugriff;

import wahr.physisch.*;

public class Staticf
{

	//Debug-Zeiten ausgeben an/aus
	private static boolean sca = false;
	//Zeit an der sca gemessen werden soll
	public static long last;
	public static long last2;

	//Debug-Textausgabe
	public static void sca(Object w)
	{
		if(sca)
			System.out.println(w.toString() + (System.currentTimeMillis() - last));
	}

	public static void sca2(Object w)
	{
		if(sca)
			System.out.println("		" + w.toString() + (System.currentTimeMillis() - last2));
	}

	//Fenster ohne Rahmen
	public static boolean undecoratedFrame = false;
	//Fenster unmaximiert w/h
	public static int frameXW = 500;
	public static int frameYH = 500;
	//Fenster am Anfang maximiert
	public static boolean automaximize = true;
	//Scaling am Bildschirm
	public static int scaleX = 560;
	public static int scaleY = 560;
	//Panelizer fast Null
	public static double nnull = 0.0001;
	//Tasten-Indexes ausgeben
	public static boolean writeKeyIndex = false;
	//Frameskips ausgeben
	public static boolean writeFrameskips = false;
	//Frameskips ausgeben
	public static boolean writeFrameTime = false;

	public static void initialCharge()
	{
		String t = Lader.gibText("Initial");
		String[] t1 = t.split("\n");
		for(int i = 0; i < t1.length; i++)
			if(!t1[i].isEmpty() && !t1[i].startsWith("/"))
			{
				String[] t2 = t1[i].split("=");
				Boolean z = null;
				if(t2.length == 1)
					if(t2[0].endsWith("!"))
					{
						t2[0] = t2[0].substring(0, t2[0].length() - 1);
						z = false;
					}
					else
						z = true;
				switch(t2[0])
				{
					case "undecoratedFrame":
						if(z != null)
							undecoratedFrame = z;
						break;
					case "frameXW":
						if(t2.length > 1)
							frameXW = Integer.parseInt(t2[1]);
						break;
					case "frameYH":
						if(t2.length > 1)
							frameYH = Integer.parseInt(t2[1]);
						break;
					case "automaximize":
						if(z != null)
							automaximize = z;
						break;
					case "scaleX":
						if(t2.length > 1)
							scaleX = Integer.parseInt(t2[1]);
						break;
					case "scaleY":
						if(t2.length > 1)
							scaleY = Integer.parseInt(t2[1]);
						break;
					case "nnull":
						if(t2.length > 1)
							nnull = Double.parseDouble(t2[1]);
						break;
					case "sca":
						if(z != null)
							sca = z;
						break;
					case "writeKeyIndex":
						if(z != null)
							writeKeyIndex = z;
						break;
					case "writeFrameskips":
						if(z != null)
							writeFrameskips = z;
						break;
					case "writeFrameTime":
						if(z != null)
							writeFrameTime = z;
						break;
				}
			}
	}

	//Sichtweite NonBlocks
	public static final double sicht = 100;
	//Eigene H ausblenden
	public static final double sichtMin = 2;
	//Sichtweite d NonBlocks
	public static final double sichtd = 100;
	//Target-Markierungsbreite
	public static final int targetW = 5;
	//NonBlock F2 Teilung bis kleiner als das
	public static final double splThr = 1.2;
	//Maximale Anzahl Frameskips
	public static final int maxfskp = 10;
	//Anzahl Millisekunden pro Frame
	public static final long stdfms = 20;
	//Anzahl Millisekunden zwischen Checks ob wieder im Fokus
	public static final long blcfms = 200;
	//Weite in Blocks wo NonBlocks in d nicht diffundieren
	public static final double safezone = 1;
	//Weite danach wo NonBlocks in d diffundieren
	public static final double diffusewidth = 40;
	//Anzahl unterschiedlicher Diffundierungs-Seeds
	public static final int seedifier = 100;
	//BlockBox-Genauigkeit in Dezimeter
	public static final double step = 0.01;
	//Ungenauigkeit bei Hitboxes oben und unten
	public static final double ebhc = 0.1;
	//Schrittweite bei Hitboxes
	public static final double abhc = 1;
	//Weite des X-Ray-Modus
	public static final int xraywidth = 50;

	public static void charge2()
	{

	}
}