package wahr.spieler;

import ansicht.*;
import wahr.physisch.*;

import java.awt.*;

public class MausDrehInput implements DrehInput
{
	Overlay master;
	//Maus Robot
	public Robot ro;
	//Mausverschiebung
	public Point mausv;
	//Maus Letzter Fokus
	public Point mausLast;

	public MausDrehInput(Overlay master)
	{
		this.master = master;
		mausLast = new Point(master.auf.scF.width / 2, master.auf.scF.height / 2);
		Point mm = master.auf.fr.getLocationOnScreen();
		try
		{
			ro = new Robot();
		}catch(AWTException e)
		{
			throw new RuntimeException(e);
		}
		ro.mouseMove(master.auf.scF.width / 2 + mm.x, master.auf.scF.height / 2 + mm.y);
		mausv = new Point();
	}

	public void ablesen(boolean mode)
	{
		Point mausNeu = MouseInfo.getPointerInfo().getLocation();
		Point mm = master.auf.fr.getLocationOnScreen();
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