package nonBlock.controllable;

import ansicht.*;
import block.*;
import nonBlock.aktion.*;
import nonBlock.collide.*;
import wahr.zugriff.*;

public class Flag extends NBB implements Licht
{
	public Flag(WeltB welt, LichtW lw, WeltND dw, WeltNB bw)
	{
		super(welt, lw, dw, bw);
		lw.licht.add(this);
		welt.flags.add(this);
	}

	public void ende()
	{
		lw.licht.remove(this);
		welt.flags.remove(this);
		super.ende();
	}

	public void collide(Attk attk)
	{
		ende();
	}

	public void actCollide(Attk attk){}
	public void decollide(Attk attk){}
	public void wand(int welche){}
	protected void kontrolle(){}

	public K4 lichtPosition()
	{
		return position;
	}
	public double lichtRange()
	{
		return 200;
	}
	public double lichtPower()
	{
		return 30;
	}
	public double lichtPowerDecay()
	{
		return 2.8;
	}
}