package wahr.mage;

import wahr.zugriff.*;

public class LagZeit
{
	public static void start()
	{
		Hauptschleife.theOverlay.z.siehNonBlocks = false;
		for(int i = 0; i < 10; i++)
		{
			Staticf.last = System.currentTimeMillis();
			if(Hauptschleife.theOverlay.eingabe())
				System.exit(0);
			Hauptschleife.aw.logik();
			Hauptschleife.theOverlay.rendern();
			Staticf.last2 = System.currentTimeMillis();
			Hauptschleife.theOverlay.panelize();
		}
		Hauptschleife.theOverlay.z.siehNonBlocks = true;
	}
}