package falsch;

import fnd.*;
import modell.ext.*;

public class ZeitVerwalter
{
	private static long last;
	private static int fskp;
	private static long over;
	private static boolean skpf;

	public static void start()
	{
		while(true)
		{
			boolean okay = LPaneel.fr.hasFocus();
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
						if(fskp < Staticf.maxfskp)
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
				//Erstes Frame
				skpf = true;
				External.nicht = false;
				last = System.currentTimeMillis();
			}
			if(okay)
			{
				Staticf.last = System.currentTimeMillis();
				if(Hauptschleife.eingabe())
					break;
				Hauptschleife.logik();
				if(!skpf)
				{
					Hauptschleife.rendern();
					new Thread()
					{
						public void run()
						{
							Staticf.last2 = System.currentTimeMillis();
							Hauptschleife.panelize();
						}
					}.start();
					fskp = -1;
				}
			}
		}
	}

	public static void schlaf(long zeit)
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