package edit;

import achsen.*;
import achsen1.*;
import k4.*;

public class AEKam implements IKamera
{
	double sichtA;
	double zielA;
	double startA;
	Drehung sichtD;
	Drehung zielD;
	Drehung startD;
	K4 sichtP;
	K4 zielP;
	K4 startP;
	K4 zP;

	K4 position;
	int tick;
	int maxTicks;
	double turnSpeed;
	double mvSpeed;

	public AEKam(double abstand, double turnSpeed, double mvSpeed)
	{
		sichtP = new K4();
		sichtD = new Drehung();
		sichtA = abstand;
		zP = new K4();
		this.turnSpeed = turnSpeed;
		this.mvSpeed = mvSpeed;
		errechnePosition();
	}

	public void tick(boolean[] keyStat)
	{
		boolean ePosition = false;
		if(tick >= maxTicks)
		{
			int lr = 0;
			int ou = 0;
			if(keyStat[1] != keyStat[2])
			{
				lr = keyStat[2] ? 1 : -1;
				ePosition = true;
			}
			if(keyStat[3] != keyStat[4])
			{
				ou = keyStat[4] ? 1 : -1;
				ePosition = true;
			}
			sichtD.wl += lr * (ou != 0 ? 0.7 : 1d) * turnSpeed;
			sichtD.wb += ou * (lr != 0 ? 0.7 : 1d) * turnSpeed;
			if(sichtD.wb > Math.PI / 2 && sichtD.wb < Math.PI)
				sichtD.wb = Math.PI / 2;
			if(sichtD.wb < Math.PI * 3 / 2 && sichtD.wb > Math.PI)
				sichtD.wb = Math.PI * 3 / 2;
			if(ePosition)
				sichtD.sichern();
			int mou = 0;
			if(keyStat[5] != keyStat[6])
			{
				mou = keyStat[5] ? 1 : -1;
				ePosition = true;
			}
			zP.b += mou * mvSpeed;
			sichtP.b += mou * mvSpeed;
		}
		else
		{
			tick++;
			sichtA = startA * (1 - tick / (double) maxTicks) + zielA * (tick / (double) maxTicks);
			sichtD = new Drehung(partD(startD.wl, zielD.wl, tick / (double) maxTicks),
					partD(startD.wb, zielD.wb, tick / (double) maxTicks));
			sichtP = K4.part(startP, zielP, tick / (double) maxTicks);
			ePosition = true;
		}
		if(ePosition)
			errechnePosition();
	}

	private double partD(double start, double ziel, double l)
	{
		while(start < 0)
			start += Math.PI * 2;
		while(start >= Math.PI * 2)
			start -= Math.PI * 2;
		while(ziel < 0)
			ziel += Math.PI * 2;
		while(ziel >= Math.PI * 2)
			ziel -= Math.PI * 2;
		if(start - ziel > Math.PI)
			ziel += Math.PI * 2;
		else if(ziel - start > Math.PI)
			start += Math.PI * 2;
		return Drehung.sichern(start * (1 - l) + ziel * l);
	}

	public void scroll(double scroll)
	{
		if(tick >= maxTicks)
		{
			sichtA *= 1 + scroll;
			errechnePosition();
		}
	}

	void beweg(double abstand, Drehung dreh, K4 z, K4 ziel)
	{
		startA = sichtA;
		startD = new Drehung(sichtD);
		startP = new K4(sichtP);
		zielA = abstand;
		zielD = dreh;
		zielP = K4.plus(z, ziel);
		zP = z;
		tick = 0;
		maxTicks = 1 + (int) Math.sqrt(K4.len(K4.diff(startP, zielP)) * 40);
	}

	void errechnePosition()
	{
		K4 tLen = new K4(0, 0, -sichtA, 0);
		tLen.transformWBL(sichtD);
		position = K4.plus(sichtP, tLen);
	}

	public double avAbstand()
	{
		if(tick < maxTicks)
			return zielA;
		else
			return sichtA;
	}

	public Drehung avDreh()
	{
		if(tick < maxTicks)
			return zielD;
		else
			return sichtD;
	}

	@Override
	public Drehung kamD()
	{
		return sichtD;
	}

	@Override
	public K4 kamP()
	{
		return position;
	}

	public void forderePositionAn(AchsenK1 ak1)
	{

	}
}