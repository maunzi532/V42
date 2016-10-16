package edit;

import achsen.*;
import k4.*;

public class AEKam implements IKamera
{
	K4 position;
	Drehung dreh;
	K4 sichtP;
	K4 zielP;
	K4 startP;
	double sichtA;
	double zielA;
	double startA;
	int tick;
	int maxTicks;
	double turnSpeed;

	public AEKam(K4 ziel, double abstand, Drehung winkel, double turnSpeed)
	{
		sichtP = new K4(ziel);
		dreh = new Drehung(winkel);
		sichtA = abstand;
		this.turnSpeed = turnSpeed;
		errechnePosition();
	}

	public void tick(boolean l, boolean r, boolean o, boolean u)
	{
		boolean ePosition = false;
		int lr = 0;
		int ou = 0;
		if(l != r)
		{
			lr = r ? 1 : -1;
			ePosition = true;
		}
		if(o != u)
		{
			ou = u ? 1 : -1;
			ePosition = true;
		}
		dreh.wl += lr * (ou != 0 ? 0.7 : 1d) * turnSpeed;
		dreh.wb += ou * (lr != 0 ? 0.7 : 1d) * turnSpeed;
		if(dreh.wb > Math.PI / 2 && dreh.wb < Math.PI)
			dreh.wb = Math.PI / 2;
		if(dreh.wb < Math.PI * 3 / 2 && dreh.wb > Math.PI)
			dreh.wb = Math.PI * 3 / 2;
		if(ePosition)
			dreh.sichern();
		if(tick < maxTicks)
		{
			tick++;
			sichtP = K4.part(startP, zielP, tick / (double) maxTicks);
			ePosition = true;
		}
		if(ePosition)
			errechnePosition();
	}

	public void scroll(double scroll)
	{
		sichtA *= 1 + scroll;
		errechnePosition();
	}

	void beweg(K4 ziel, double abstand)
	{
		startP = new K4(sichtP);
		startA = sichtA;
		zielP = ziel;
		zielA = abstand;
		tick = 0;
		maxTicks = (int) Math.sqrt(K4.len(K4.diff(startP, zielP)) * 40);
	}

	void errechnePosition()
	{
		K4 tLen = new K4(0, 0, -sichtA, 0);
		tLen.transformWBL(dreh);
		position = K4.plus(sichtP, tLen);
	}

	@Override
	public Drehung kamD()
	{
		return dreh;
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