package falsch;

import fnd.*;
import modell.ext.*;

public class ZeitVerwalter
{
	private static long last;
	private static int fskp;
	private static long over;

	public static void start()
	{
		while(true)
		{
			boolean okay = LPaneel.fr.hasFocus();
			boolean skpf;
			if(last > 0)
			{
				skpf = false;
				if(okay)
				{
					long now = System.currentTimeMillis();
					if(Staticf.writeFrameTime)
						System.out.println("T " + (now - last) + " " + over);
					if(over + now - last < Staticf.stdfms)
					{
						over = over + now - last - Staticf.stdfms;
						if(over < -Staticf.stdfms)
							over = -Staticf.stdfms;
					}
					else if(over + now - last > Staticf.stdfms)
					{
						if(fskp < Staticf.mfskp)
						{
							skpf = true;
							fskp++;
							if(Staticf.writeFrameskips)
								System.out.println("Frameskip " + fskp);
							over = over + now - last - Staticf.stdfms;
						}
						else
							over = 0;
					}
					else
						over = 0;
					last = System.currentTimeMillis();
				}
				else
				{
					schlaf(Staticf.blcfms);
					last = 0;
					over = 0;
				}
				External.nicht = skpf;
			}
			else
			{
				skpf = true;
				External.nicht = false;
				last = System.currentTimeMillis();
			}
			if(okay)
			{
				Staticf.last = System.currentTimeMillis();
				if(Hauptschleife.mainTick(skpf))
					break;
				if(!skpf)
					fskp = -1;
			}
		}
		System.exit(0);
	}

	private static void schlaf(long zeit)
	{
		try
		{
			Thread.sleep(zeit);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
