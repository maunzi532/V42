package wahr.spieler;

import ansicht.*;
import wahr.physisch.*;

public class TastenDrehInput implements DrehInput
{
	private Overlay master;
	private double movementX;
	private double movementY;
	private int positionX;
	private int positionY;

	public TastenDrehInput(Overlay master)
	{
		this.master = master;
		positionX = master.wI / 2;
		positionY = master.hI / 2;
	}

	public void ablesen(boolean mode)
	{
		movementX = 0;
		movementY = 0;
		if(mode)
		{
			if(TA2.keyStat[master.taIndex][21] > 0)
				positionX -= 20;
			if(TA2.keyStat[master.taIndex][22] > 0)
				positionX += 20;
			if(TA2.keyStat[master.taIndex][23] > 0)
				positionY -= 20;
			if(TA2.keyStat[master.taIndex][24] > 0)
				positionY += 20;
		}
		else
		{
			if(TA2.keyStat[master.taIndex][21] > 0)
				movementX -= 20;
			if(TA2.keyStat[master.taIndex][22] > 0)
				movementX += 20;
			if(TA2.keyStat[master.taIndex][23] > 0)
				movementY -= 20;
			if(TA2.keyStat[master.taIndex][24] > 0)
				movementY += 20;
		}
	}

	public double wlmove()
	{
		return movementX;
	}

	public double wbmove()
	{
		return movementY;
	}

	public int xP()
	{
		return positionX;
	}

	public int yP()
	{
		return positionY;
	}
}