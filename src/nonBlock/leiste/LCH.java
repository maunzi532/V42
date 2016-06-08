package nonBlock.leiste;

import block.*;
import nonBlock.aktion.*;
import nonBlock.collide.*;
import nonBlock.controllable.*;
import nonBlock.formwandler.*;
import wahr.zugriff.*;

public abstract class LCH extends FWA
{
	protected LCH(Controller control, LadeFWA abilities, String currentZ, WeltB welt,
			WeltND dw, WeltNB bw)
	{
		super(control, abilities, currentZ, welt, dw, bw);
	}

	protected LCH(Controller control, LadeFWA abilities, String currentZ, AllWelt aw)
	{
		super(control, abilities, currentZ, aw);
	}

	public void collide(Collider collider)
	{

	}

	public void actCollide(Collider collider)
	{

	}

	public void decollide(Collider collider)
	{

	}
}