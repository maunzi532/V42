package wahr.mage;

import nonBlock.aussehen.ext.*;
import wahr.physisch.*;
import wahr.zugriff.*;

import java.awt.*;

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
			for(int i = 0; i < LPaneel.paneele.size(); i++)
				if(LPaneel.paneele.get(i).fr.hasFocus())
					okay = true;
			boolean skpf;
			if(last > 0)
			{
				skpf = false;
				if(okay)
				{
					long now = System.currentTimeMillis();
					if(Staticf.writeFrameTime)
						System.out.println("T " + (now - last) + " " + over);
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
								System.out.println("Frameskip " + fskp);
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
				boolean resize = false;
				for(int i = 0; i < LPaneel.paneele.size(); i++)
				{
					Dimension sc1 = LPaneel.paneele.get(i).fr.getSize();
					if(LPaneel.paneele.get(i).scF.width != sc1.width ||
							LPaneel.paneele.get(i).scF.height != sc1.height)
					{
						LPaneel.paneele.get(i).scF = sc1;
						resize = true;
					}
				}
				if(Hauptschleife.theSpieler.eingabe(resize))
					return;
				//Haare laggen furchtbar
				H.calculateH = !skpf;
				Hauptschleife.aw.logik();
				if(!skpf)
				{
					//Noch nicht aufmalen
					Hauptschleife.theSpieler.overlay.rendern();
					new Thread()
					{
						//Hier wird aufgemalt
						public void run()
						{
							if(!thd)
							{
								thd = true;
								long last4 = System.currentTimeMillis();
								Hauptschleife.theSpieler.overlay.panelize();
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