package nonBlock.formwandler.zustand;

import k4.*;
import nonBlock.formwandler.*;
import welt.*;

public abstract class AllgKanteZ extends SnappedZ
{
	int richtung;

	public AllgKanteZ(FWA z, int richtung)
	{
		super(z);
		this.richtung = richtung;
	}

	public static K4 ankanten(WeltB w, K4 zp, int richtung, double outward, double upward)
	{
		K4 fp = w.wt(w.tw(zp));
		fp.b += w.weltBlock.b + upward;
		if(richtung % 2 == 0)
		{
			if(richtung == 0)
				fp.c += w.weltBlock.c + outward;
			else
				fp.c -= outward;
			fp.a = zp.a;
		}
		else
		{
			if(richtung == 3)
				fp.a += w.weltBlock.a + outward;
			else
				fp.a -= outward;
			fp.c = zp.c;
		}
		fp.d = zp.d;
		return fp;
	}

	public static K4 anecken(WeltB w, K4 zp, int richtung, double outward, double upward)
	{
		K4 fp = w.wt(w.tw(zp));
		fp.b += w.weltBlock.b + upward;
		if(richtung == 0 || richtung == 3)
			fp.c += w.weltBlock.c + outward;
		else
			fp.c -= outward;
		if(richtung >= 2)
			fp.a += w.weltBlock.a + outward;
		else
			fp.a -= outward;
		fp.d = zp.d;
		return fp;
	}
}