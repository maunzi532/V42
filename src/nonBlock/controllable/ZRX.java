package nonBlock.controllable;

import a3.*;
import k4.*;
import nonBlock.collide.*;
import nonBlock.formwandler.*;
import wahr.zugriff.*;

public class ZRX extends FWA implements Licht
{
	protected ZRX(Controller control, LadeFWA abilities, String currentZ, AllWelt aw)
	{
		super(control, abilities, currentZ, aw);
	}

	@Override
	public void kontrolle()
	{

	}

	@Override
	protected void doFall(String fall, boolean attachChainOnly)
	{

	}

	@Override
	public void wand(int welche)
	{

	}

	@Override
	public void collide(Collider collider)
	{

	}

	@Override
	public void actCollide(Collider collider)
	{

	}

	@Override
	public void decollide(Collider collider)
	{

	}

	@Override
	public K4 lichtPosition()
	{
		return null;
	}

	@Override
	public double lichtRange()
	{
		return 0;
	}

	@Override
	public double lichtPower()
	{
		return 0;
	}

	@Override
	public double lichtPowerDecay()
	{
		return 0;
	}
}