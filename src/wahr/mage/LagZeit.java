package wahr.mage;

import wahr.zugriff.*;

public class LagZeit
{
	public static void start()
	{
		Hauptschleife.theSpieler.overlay.z.siehNonBlocks = false;
		for(int i = 0; i < 10; i++)
		{
			Staticf.last = System.currentTimeMillis();
			if(Hauptschleife.theSpieler.eingabe(false))
				System.exit(0);
			Hauptschleife.aw.logik();
			Hauptschleife.theSpieler.overlay.rendern();
			Staticf.last2 = System.currentTimeMillis();
			Hauptschleife.theSpieler.overlay.panelize();
		}
		Hauptschleife.theSpieler.overlay.z.siehNonBlocks = true;
	}
}