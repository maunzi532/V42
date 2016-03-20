package wahr.spieler;

import wahr.physisch.*;
import wahr.zugriff.*;

import java.awt.*;

public class MausDrehInput implements DrehInput
{
	Spieler master;
	//Maus Robot
	public Robot ro;
	//Mausverschiebung
	public Point mausv;
	//Maus Letzter Fokus
	public Point mausLast;

	public MausDrehInput(Spieler master)
	{
		this.master = master;
		mausLast = new Point(UIVerbunden.sc.width / 2, UIVerbunden.sc.height / 2);
		Point mm = LPaneel.fr.getLocationOnScreen();
		try
		{
			ro = new Robot();
		}catch(AWTException e)
		{
			throw new RuntimeException(e);
		}
		ro.mouseMove(UIVerbunden.sc.width / 2 + mm.x, UIVerbunden.sc.height / 2 + mm.y);
		mausv = new Point();
	}

	public void ablesen(boolean mode)
	{
		Point mausNeu = MouseInfo.getPointerInfo().getLocation();
		Point mm = LPaneel.fr.getLocationOnScreen();
		mausNeu.translate(-mm.x, -mm.y);
		//mausNeu ist jetzt location in Frame
		if(TA2.keyStat[master.taIndex][13] <= 0)
			ro.mouseMove(mausLast.x + mm.x, mausLast.y + mm.y); //zurueck wo du warst
		else
			mausLast = new Point(mausNeu); //wo du warst wird gesetzt
		mausNeu.translate(-mausLast.x, -mausLast.y); //Unterschied
		mausv = mausNeu;
	}

	public double wlmove()
	{
		return mausv.x;
	}

	public double wbmove()
	{
		return mausv.y;
	}

	public int xP()
	{
		return mausLast.x;
	}

	public int yP()
	{
		return mausLast.y;
	}
}