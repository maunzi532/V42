package nonBlock.formwandler.zustand;

import achsen.*;
import k4.*;
import nonBlock.aktion.*;
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
	public void kontrolleX(int[] infl)
	{
		super.kontrolleX(infl);
		z.beweg.add(K4.mult(z.bewegung, 0.85));
		z.beweg.add(new K4(0, -0.05, 0, 0));
		if(z.naheWand(2, 0.1))
		{
			z.zustand = new BodenZ(z);
			//ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA"), z, 20);
			return;
		}
		if(infl[1] > 0)
			attemptAirgrab();
	}

	public void attemptAirgrab()
	{
		WBP p = z.welt.tw(z.position);
		boolean drehArt = InBlockRaster.drehArt(z.dreh.wl);
		if(drehArt)
		{
			InBlockRaster ibr = new InBlockRaster(z.welt, z.position, z.dreh.wl, true, 4, 16, 4, 4, 0, 20, 0, 0);
			ibr.zusammenfassen(3);
			ibr.len(2, 2);
			ibr.zusammenfassen(1);
			ibr.len(0, 2);
			if(ibr.entspricht(new int[][][][]{{{{1, 2}}, {{1, 1}}}},
					new boolean[][][][]{{{{true, true}}, {{true, true}}}}))
			{
				int richtung = InBlockRaster.drehIntH(z.dreh.wl);
				K4 fp = z.welt.wt(p);
				fp.b += z.welt.weltBlock.b + 0.6;
				if(richtung % 2 == 0)
				{
					if(richtung == 0)
						fp.c += z.welt.weltBlock.c - 3.6;
					else
						fp.c += 3.6;
					fp.a = z.position.a;
				}
				else
				{
					if(richtung == 3)
						fp.a += z.welt.weltBlock.a - 3.6;
					else
						fp.a += 3.6;
					fp.c = z.position.c;
				}
				fp.d = z.position.d;
				if(InBlockRaster.drehIntH2(z.dreh.wl) == 7)
					richtung = 4;
				focus(new Focus(z, 20, fp, Drehung.nDrehung(richtung * Math.PI / 2, 0)),
						new KanteZ(z, richtung, fp));
				ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA3LR"), z, 20);
			}
		}
		else
		{
			InBlockRaster ibr = new InBlockRaster(z.welt, z.position, z.dreh.wl, false, 4, 16, 16, 4, 0, 20, 0, 0);
			ibr.zusammenfassen(3);
			ibr.len(2, 2);
			ibr.len(1, 2);
			ibr.len(0, 2);
			if(ibr.entspricht(new int[][][][]{{{{1, 2}, {1, 1}}, {{1, 1}, {1, 1}}}},
					new boolean[][][][]{{{{true, true}, {true, true}}, {{true, true}, {true, true}}}}))
			{
				int richtung = InBlockRaster.drehIntD(z.dreh.wl);
				K4 fp = z.welt.wt(p);
				fp.b += z.welt.weltBlock.b + 0.6;
				if(richtung == 0 || richtung == 3)
					fp.c += z.welt.weltBlock.c - 3.6;
				else
					fp.c += 3.6;
				if(richtung >= 2)
					fp.a += z.welt.weltBlock.a - 3.6;
				else
					fp.a += 3.6;
				fp.d = z.position.d;
				focus(new Focus(z, 20, fp, new Drehung(richtung * Math.PI / 2
						+ Math.PI / 4, 0)), new EckeZ(z, richtung, fp));
				ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA3LR"), z, 20);
			}
		}
	}
}