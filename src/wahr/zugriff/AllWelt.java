package wahr.zugriff;

import ansicht.*;
import block.*;
import nonBlock.aktion.*;
import nonBlock.collide.*;

public class AllWelt
{
	public WeltB wbl;
	public WeltND dw;
	public LichtW lw;
	public WeltNB bw;

	public AllWelt()
	{
		wbl = new WeltB();
		dw = new WeltND();
		lw = new LichtW();
		bw = new WeltNB(this);
	}
}