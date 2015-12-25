package wahr.mage;

import wahr.physisch.*;
import wahr.zugriff.*;

public class ZeitVerwalter
{
	private static long last;
	private static int fskp;
	private static long over;
	private static boolean thd;

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
			}
			else
			{
				//Erstes Frame
				skpf = true;
				last = System.currentTimeMillis();
			}
			if(okay)
			{
				Staticf.last = System.currentTimeMillis();
				Staticf.last2 = System.currentTimeMillis();
				if(Hauptschleife.eingabe())
					break;
				UIVerbunden.calculateH = !skpf;
				Hauptschleife.logik();
				if(!skpf)
				{
					Hauptschleife.rendern();
					new Thread()
					{
						public void run()
						{
							if(!thd)
							{
								thd = true;
								//Staticf.last2 = System.currentTimeMillis();
								Hauptschleife.panelize();
								thd = false;
							}
						}
					}.start();
					fskp = -1;
				}
			}
		}
	}

	private static void schlaf(long zeit)
	{
		try
		{
			Thread.sleep(zeit);
		}
		catch(InterruptedException ignored){}
	}
}