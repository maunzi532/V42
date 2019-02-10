package wahr.mage;

import ansicht.*;
import wahr.zugriff.*;

public class LagZeit
{
	public static void start(Overlay[] theOverlays)
	{
		theOverlays[0].vor.siehNonBlocks = false;
		for(int i = 0; i < 10; i++)
		{
			Staticf.last = System.currentTimeMillis();
			if(theOverlays[0].eingabe())
				System.exit(0);
			Hauptschleife.aw.logik();
			theOverlays[0].vorbereiten();
			Staticf.last2 = System.currentTimeMillis();
			theOverlays[0].panelize();
		}
		theOverlays[0].vor.siehNonBlocks = true;
	}
}