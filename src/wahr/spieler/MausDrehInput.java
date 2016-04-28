package wahr.spieler;

import ansicht.*;

import java.awt.*;

public class MausDrehInput implements DrehInput
{
	private Overlay master;
	//Maus Robot
	private Robot ro;
	//Mausverschiebung
	private Point mausv;
	//Maus Letzter Fokus
	private Point mausLast;

	public MausDrehInput(Overlay master)
	{
		this.master = master;
		mausLast = new Point(master.wI / 2, master.hI / 2);
		Point mm = master.auf.fr.getLocationOnScreen();
		try
		{
			ro = new Robot();
		}catch(AWTException e)
		{
			throw new RuntimeException(e);
		}
		ro.mouseMove(master.xI + master.wI / 2 + mm.x, master.yI + master.hI / 2 + mm.y);
		mausv = new Point();
	}

	public void ablesen(boolean mode)
	{
		Point mausNeu = MouseInfo.getPointerInfo().getLocation();
		Point mm = master.auf.fr.getLocationOnScreen();
		mausNeu.translate(-mm.x - master.xI, -mm.y - master.yI);
		//mausNeu ist jetzt location in Frame
		if(master.ta.keyStat[master.taIndex][13] <= 0)
			ro.mouseMove(mausLast.x + mm.x + master.xI, mausLast.y + mm.y + master.yI); //zurueck wo du warst
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