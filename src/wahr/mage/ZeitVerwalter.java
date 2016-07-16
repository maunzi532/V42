package wahr.mage;

import ext.*;
import java.awt.*;
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
			boolean okay = false;
			for(int i = 0; i < ThaCre2A.paneele.length; i++)
				if(ThaCre2A.paneele[i].fr.hasFocus())
					okay = true;
			boolean skpf;
			if(last > 0)
			{
				skpf = false;
				if(okay)
				{
					long now = System.currentTimeMillis();
					if(Staticf.writeFrameTime)
						System.out.println("FRT: " + (now - last) + " OVER: " + over);
					//Dauer checken
					if(over + now - last < Staticf.stdfms)
					{
						//Zu kurz
						over = over + now - last - Staticf.stdfms;
						if(over < -Staticf.stdfms)
						{
							schlaf(-over - Staticf.stdfms);
							over = -Staticf.stdfms;
						}
					}
					else if(over + now - last > Staticf.stdfms)
					{
						//Zu lang
						if(fskp < Staticf.maxfskp)
						{
							//Frame skippen
							skpf = true;
							fskp++;
							if(Staticf.writeFrameskips)
								System.out.println("FSKP: " + fskp);
							over = over + now - last - Staticf.stdfms;
						}
						else
							//Lagt zu stark
							over = 0;
					}
					else
						over = 0;
					last = System.currentTimeMillis();
				}
				else
				{
					//Hat keinen Fokus, warten
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
				Staticf.last3 = System.currentTimeMillis();
			}
			if(okay)
			{
				//Hat Fokus
				Staticf.last = System.currentTimeMillis();
				Staticf.last2 = System.currentTimeMillis();
				for(int i = 0; i < ThaCre2A.paneele.length; i++)
				{
					Dimension sc1 = ThaCre2A.paneele[i].fr.getSize();
					if(ThaCre2A.paneele[i].scF.width != sc1.width ||
							ThaCre2A.paneele[i].scF.height != sc1.height)
					{
						ThaCre2A.paneele[i].scF = sc1;
						for(int j = 0; j < ThaCre2A.theOverlays.length; j++)
							if(ThaCre2A.theOverlays[j].auf == ThaCre2A.paneele[i])
								ThaCre2A.theOverlays[j].resize();
					}
				}
				for(int i = 0; i < ThaCre2A.theOverlays.length; i++)
					if(ThaCre2A.theOverlays[i].eingabe())
						return;
				//Haare laggen furchtbar
				H.calculateH = !skpf;
				Hauptschleife.aw.logik();
				if(!skpf)
				{
					//Noch nicht aufmalen
					for(int i = 0; i < ThaCre2A.theOverlays.length; i++)
						ThaCre2A.theOverlays[i].vorbereiten();
					new Thread()
					{
						//Hier wird aufgemalt
						public void run()
						{
							if(!thd)
							{
								thd = true;
								long last4 = System.currentTimeMillis();
								for(int i = 0; i < ThaCre2A.theOverlays.length; i++)
									ThaCre2A.theOverlays[i].panelize();
								if(Staticf.writeVisibleTime)
									System.out.println("VIS: " + (System.currentTimeMillis() - Staticf.last3));
								Staticf.last3 = System.currentTimeMillis();
								if(Staticf.writeThreadTime)
									System.out.println("THR: " + (System.currentTimeMillis() - last4));
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