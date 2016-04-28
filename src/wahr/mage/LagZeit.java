package wahr.mage;

import wahr.physisch.*;
import wahr.zugriff.*;

public class LagZeit
{
	public static void start()
	{
		ThaCre2A.theOverlays[0].vor.siehNonBlocks = false;
		for(int i = 0; i < 10; i++)
		{
			Staticf.last = System.currentTimeMillis();
			if(ThaCre2A.theOverlays[0].eingabe())
				System.exit(0);
			Hauptschleife.aw.logik();
			ThaCre2A.theOverlays[0].vorbereiten();
			Staticf.last2 = System.currentTimeMillis();
			ThaCre2A.theOverlays[0].panelize();
		}
		ThaCre2A.theOverlays[0].vor.siehNonBlocks = true;
	}
}