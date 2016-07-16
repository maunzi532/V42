package nonBlock.controllable;

import a3.*;
import k4.*;
import nonBlock.aktion.*;
import nonBlock.collide.*;
import welt.*;

public class Flag extends NBB implements Licht, IFlag
{
	public Flag(WeltB welt, WeltND dw, WeltNB bw)
	{
		super(welt, dw, bw);
		//lw.licht.add(this);
		welt.flags.add(this);
	}

	public void ende()
	{
		//lw.licht.remove(this);
		welt.flags.remove(this);
		super.ende();
	}

	public void collide(Collider collider)
	{
		ende();
	}

	public void actCollide(Collider collider){}
	public void decollide(Collider collider){}
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

	public K4 position()
	{
		return position;
	}

	public Drehung dreh()
	{
		return dreh;
	}
}