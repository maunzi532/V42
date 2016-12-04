package nonBlock.formwandler.zustand;

import achsen.*;
import k4.*;
import nonBlock.aktion.*;
import nonBlock.formwandler.*;
import welt.*;

public class KanteZ extends AllgKanteZ
{
	public KanteZ(FWA z, int richtung1, K4 fp)
	{
		super(z, richtung1 % 4);
	}

	/* Movements
	 * Hochklettern (AerialZ)
	 * Fallenlassen (AerialZ)
	 * Seitlich klettern (KanteZ, EckeZ)
	 * Seitlich sehen (SeitlichKanteZ)
	 */

	@Override
	public void kontrolleX(int[] infl)
	{
		if(infl[1] > 0 || infl[5] > 0)
			hochklettern(z);
	}

	public void hochklettern(FWA z)
	{
		InBlockRaster ibr = new InBlockRaster(z.welt, z.position, z.dreh.wl, true,
				10, 10, 4, 4, -20, 20, 0, 0);
		ibr.checkLen(3, 1);
		ibr.checkLen(2, 1);
		ibr.zusammenfassen(1);
		ibr.checkLen(0, 2);
		if(ibr.entspricht(new int[][][][]{{{{1, 1}}}},
				new boolean[][][][]{{{{true, true}}}}))
		{
			z.dreh.sichern();
			int richtung = InBlockRaster.drehIntH(z.dreh.wl);
			focus(new Focus(z, 20, ankanten(z.welt, z.position(), richtung, 3.6, -0.2),
					Drehung.nDrehung(z.dreh.wl, 0)), new AerialZ(z));
			ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA"), z, 20);
		}
	}

	public void seitlichKlettern(FWA z, boolean r)
	{
		InBlockRaster ibr = new InBlockRaster(z.welt, z.position, z.dreh.wl, true,
				-10, 10, r ? 4 : 6, r ? 6 : 4, 0, 20, 0, 0);
		ibr.checkLen(3, 1);
		ibr.checkLen(2, 2);
		ibr.checkLen(0, 2);

		if(ibr.entspricht(new int[][][][]{{{{1, 1}}}},
				new boolean[][][][]{{{{true, true}}}}))
		{
			z.dreh.sichern();
			int richtung = InBlockRaster.drehIntH(z.dreh.wl);
			focus(new Focus(z, 20, ankanten(z.welt, z.position(), richtung, 3.6, -0.2),
					Drehung.nDrehung(z.dreh.wl, 0)), new AerialZ(z));
			ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA"), z, 20);
		}
	}
}