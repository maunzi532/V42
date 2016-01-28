package nonBlock.formwandler;

import ansicht.*;
import block.*;
import nonBlock.aktion.*;
import nonBlock.collide.*;
import nonBlock.controllable.*;
import wahr.zugriff.*;

public abstract class FWA extends NBB implements Controllable
{
	FWZustand current;
	FWZustand last;
	int transformTime;
	int[] verwendet;
	boolean usesChainOnly;
	int[] cooldowns;

	protected FWA(WeltB welt, LichtW lw, WeltND dw, WeltNB bw)
	{
		super(welt, lw, dw, bw);
	}

	protected FWA(AllWelt aw)
	{
		super(aw);
	}
}