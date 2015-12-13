package falsch;

import k.*;
import modell.ext.*;

public class LagZeit
{
	public static void start()
	{
		Staticf.siehNonBlocks = false;
		for(int i = 0; i < 10; i++)
		{
			Staticf.last = System.currentTimeMillis();
			if(Hauptschleife.mainTick(false))
				System.exit(0);
			External.nicht = true;
		}
		Staticf.siehNonBlocks = true;
		for(int i = 0; i < 5; i++)
		{
			H h = (H) Hauptschleife.n2.aussehen.ext.get(i);
			K4[][] into = new K4[300][10];
			for(int j = 0; j < 30; j++)
			{
				h.entLink(Hauptschleife.n2.dreh, Hauptschleife.n2.position);
				h.punkte(into);
				h.tick();
			}
		}
	}
}