package nonBlock.controllable;

import a3.*;
import k4.*;
import nonBlock.aktion.*;
import nonBlock.aktion.lesen.*;
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
		aktionen.add(new AktionM(this, 10000, 11, ADI.deg(0, 0, 1, new RZahl(0), new RZahl(180),
				new RZahl(0), new RZahl(0), new RZahl(0), true)));
	}

	public void actCollide(Collider collider){}
	public void decollide(Collider collider){}
	public void wand(int welche){}

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