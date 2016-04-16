package wahr.mage;

import wahr.zugriff.*;

public class LagZeit
{
	public static void start()
	{
		Hauptschleife.theOverlays[0].vor.siehNonBlocks = false;
		for(int i = 0; i < 10; i++)
		{
			Staticf.last = System.currentTimeMillis();
			if(Hauptschleife.theOverlays[0].eingabe(false))
				System.exit(0);
			Hauptschleife.aw.logik();
			Hauptschleife.theOverlays[0].vorbereiten();
			Staticf.last2 = System.currentTimeMillis();
			Hauptschleife.theOverlays[0].panelize();
		}
		Hauptschleife.theOverlays[0].vor.siehNonBlocks = true;
	}
}