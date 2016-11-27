package nonBlock.formwandler.zustand;

import k4.*;
import nonBlock.formwandler.*;
import welt.*;

public class AerialZ extends MovableZ
{
	public AerialZ(FWA z)
	{
		super(z);
		canInfl = new double[]{0.1, 0.2, 0.2, 0.1};
	}

	@Override
	public void kontrolle(int[] infl)
	{
		super.kontrolle(infl);
		z.beweg.add(K4.mult(z.bewegung, 0.85));
		z.beweg.add(new K4(0, -0.05, 0, 0));
		if(z.naheWand(2, 0.1))
		{
			z.zustand = new BodenZ(z);
			//ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA"), z, 20);
			return;
		}
		if(infl[1] > 0)
			attemptAirgrab(z);
	}

	public static boolean attemptAirgrab(FWA z1)
	{
		WBP p = z1.welt.tw(z1.position);
		boolean drehArt = InBlockRaster.drehArt(z1.dreh.wl);
		if(drehArt)
		{
			InBlockRaster ibr = new InBlockRaster(z1.welt, z1.position, z1.dreh.wl, true, 4, 16, 4, 4, 0, 20, 0, 0);
			ibr.zusammenfassen(3);
			ibr.len(2, 2);
			ibr.zusammenfassen(1);
			ibr.len(0, 2);
			if(ibr.entspricht(new int[][][][]{{{{1, 2}}, {{1, 1}}}},
					new boolean[][][][]{{{{true, true}}, {{true, true}}}}))
			{
				int richtung = InBlockRaster.drehIntH(z1.dreh.wl);
				K4 fp = z1.welt.wt(p);
				fp.b += z1.welt.weltBlock.b + 0.6;
				if(richtung % 2 == 0)
				{
					if(richtung == 0)
						fp.c += z1.welt.weltBlock.c - 3.6;
					else
						fp.c += 3.6;
					fp.a = z1.position.a;
				}
				else
				{
					if(richtung == 3)
						fp.a += z1.welt.weltBlock.a - 3.6;
					else
						fp.a += 3.6;
					fp.c = z1.position.c;
				}
				fp.d = z1.position.d;
				if(InBlockRaster.drehIntH2(z1.dreh.wl) == 7)
					richtung = 4;
				z1.zustand = new KanteZ(z1, richtung, fp);
				return true;
			}
		}
		else
		{
			InBlockRaster ibr = new InBlockRaster(z1.welt, z1.position, z1.dreh.wl, false, 4, 16, 16, 4, 0, 20, 0, 0);
			ibr.zusammenfassen(3);
			ibr.len(2, 2);
			ibr.len(1, 2);
			ibr.len(0, 2);
			if(ibr.entspricht(new int[][][][]{{{{1, 2}, {1, 1}}, {{1, 1}, {1, 1}}}},
					new boolean[][][][]{{{{true, true}, {true, true}}, {{true, true}, {true, true}}}}))
			{
				int richtung = InBlockRaster.drehIntD(z1.dreh.wl);
				K4 fp = z1.welt.wt(p);
				fp.b += z1.welt.weltBlock.b + 0.6;
				if(richtung == 0 || richtung == 3)
					fp.c += z1.welt.weltBlock.c - 3.6;
				else
					fp.c += 3.6;
				if(richtung >= 2)
					fp.a += z1.welt.weltBlock.a - 3.6;
				else
					fp.a += 3.6;
				fp.d = z1.position.d;
				z1.zustand = new EckeZ(z1, richtung, fp);
				return true;
			}
		}
		return false;
	}
}