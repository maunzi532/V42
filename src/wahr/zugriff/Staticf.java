package wahr.zugriff;

import block.*;

public class Staticf
{
	//Zeit an der sca gemessen werden soll
	public static long last;
	public static long last2;
	public static long last3;
	//Debug-Zeiten ausgeben an/aus
	private static boolean sca = false;
	//Debug-Zeiten in Threads ausgeben an/aus
	private static boolean sca2 = false;
	//Tasten-Indexes ausgeben
	public static boolean writeKeyIndex = false;
	//Frameskips ausgeben
	public static boolean writeFrameskips = false;
	//Frameskip Daten ausgeben
	public static boolean writeFrameTime = false;
	//Zeit zwischen sichtbaren Frames ausgeben
	public static boolean writeVisibleTime = false;
	//Zeit die der Thread braucht ausgeben
	public static boolean writeThreadTime = false;

	//Debug-Textausgabe
	public static void sca(Object w)
	{
		if(sca)
			System.out.println(w.toString() + (System.currentTimeMillis() - last));
	}

	public static void sca2(Object w)
	{
		if(sca2)
			System.out.println("		" + w.toString() + (System.currentTimeMillis() - last2));
	}

	public static void scaCharge(String t)
	{
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
					/*case "scaleX":
						if(t2.length > 1)
							scaleX = Double.parseDouble(t2[1]);
						break;*/
					case "sca":
						if(z != null)
							sca = z;
						break;
					case "sca2":
						if(z != null)
							sca2 = z;
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
					case "writeVisibleTime":
						if(z != null)
							writeVisibleTime = z;
						break;
					case "writeThreadTime":
						if(z != null)
							writeThreadTime = z;
						break;
				}
			}
	}

	//Blickbreite
	public static double scaleX = 0.8;
	//Sichtweite NonBlocks
	public static final double sicht = 150;
	//Eigene H ausblenden
	public static final double sichtMin = 2;

	//Target-Markierungsbreite
	public static final int targetW = 5;

	//NonBlock Polygon Teilung bis kleiner als das
	public static final double splThr = 1.2;

	//Maximale Anzahl Frameskips
	public static final int maxfskp = 5;
	//Anzahl Millisekunden pro Frame
	public static final long stdfms = 20;
	//Anzahl Millisekunden zwischen Checks ob wieder im Fokus
	public static final long blcfms = 200;

	//BlockBox-Genauigkeit in Dezimeter
	public static final double step = 0.01;
	//Ungenauigkeit bei Hitboxes oben und unten
	public static final double ebhc = 0.1;
	//Schrittweite bei Hitboxes
	public static final double abhc = 1;

	//D2 Mindestabstand um gesehen zu werden
	public static final double d2min = 1; //TODO

	//Weite des X-Ray-Modus
	public static double xraywidth = 50;

	//Speichergroesse
	public static final WBP wspg = new WBP(1, 1, 1, 1);
	//Umbruch beim WeltB speichern
	public static final int maxCta = 60;

	//D Relokalisierungsgeschwindigkeit
	public static final double zpSpeed = 1;
	//Drehgeschwindigkeit
	public static final double nachDreh = 0.1;
}