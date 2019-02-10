package nonBlock.formwandler.zustand;

import achsen.*;
import k4.*;
import nonBlock.aktion.*;
import nonBlock.formwandler.*;
import welt.*;

public class EckeZ extends AllgKanteZ
{
	public EckeZ(FWA z, int richtung, K4 fp)
	{
		super(z, richtung);
	}

	/* Movements
	 * Hochklettern (AerialZ)
	 * Fallenlassen (AerialZ)
	 * Seitlich klettern (KanteZ)
	 */

	@Override
	public void kontrolleX(int[] infl)
	{
		/*boolean sth = false;
		for(int i = 0; i < infl.length; i++)
			if(infl[i] != 0)
				sth = true;
		if(sth)
			System.out.println(Arrays.toString(infl));*/

		if(infl[1] > 0 || infl[5] > 0)
			hochklettern(z);
		else if(infl[0] > 0 || infl[4] > 0)
			fallen(z);
		else
		{
			if(infl[2] > 0)
				seitlichKlettern(z, false);
			if(infl[3] > 0)
				seitlichKlettern(z, true);
		}
	}

	public void hochklettern(FWA z)
	{
		InBlockRaster ibr = new InBlockRaster(z.welt, z.position, z.dreh.wl, true,
				4, 4, 4, 4, 10, 10, 0, 0);
		ibr.checkLen(3, 1);
		ibr.checkLen(2, 2);
		ibr.checkLen(1, 2);
		ibr.checkLen(0, 2);
		if(ibr.entspricht(new int[][][][]{{{{0, 0}, {0, 2}}, {{1, 1}, {1, 1}}}}, null))
		{
			z.dreh.sichern();
			int richtung = InBlockRaster.drehIntD(z.dreh.wl);
			K4 fp = AllgKanteZ.anecken(z.welt, z.position, richtung, 3.6, -0.2);
			focus(new Focus(z, 20, fp, Drehung.nDrehung(z.dreh.wl, 0)), new AerialZ(z));
			ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA"), z, 20);
		}
	}

	public void fallen(FWA z)
	{
		InBlockRaster ibr = new InBlockRaster(z.welt, z.position, z.dreh.wl, true,
				4, 4, 4, 4, 10, -10, 0, 0);
		ibr.checkLen(3, 1);
		ibr.checkLen(2, 1);
		ibr.checkLen(1, 2);
		ibr.checkLen(0, 2);
		if(ibr.entspricht(new int[][][][]{{{{1, 0}, {0, 0}}}}, null))
		{
			z.dreh.sichern();
			int richtung = InBlockRaster.drehIntD(z.dreh.wl);
			K4 fp = AllgKanteZ.anecken(z.welt, z.position, richtung, -4, -20);
			focus(new Focus(z, 2, fp, Drehung.nDrehung(z.dreh.wl, 0)), new AerialZ(z));
			ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA"), z, 20);
		}
	}

	public void seitlichKlettern(FWA z, boolean r)
	{
		InBlockRaster ibr = new InBlockRaster(z.welt, z.position, z.dreh.wl, true,
				4, 4, 4, 4, 10, 10, 0, 0);
		ibr.checkLen(3, 1);
		ibr.checkLen(2, 2);
		ibr.checkLen(1, 2);
		ibr.checkLen(0, 2);
		if(ibr.entspricht(new int[][][][]{{{{1, 1}, {1, 2}}, {{1, 1}, {1, 1}}}}, null) ||
				ibr.entspricht(new int[][][][]{{{{1, 1}, {1, 6}}, {{1, 1}, {1, 2}}}}, null))
		{
			if(r)
			{
				int richtung = (InBlockRaster.drehIntD(z.dreh.wl) + 1) % 4;
				K4 fp = ankanten(z.welt, K4.plus(z.position,
						InBlockRaster.shift1d(z.welt, (richtung + 3) % 4)),
						richtung, -3.6, -19.4, -10);
				focus(new Focus(z, 20, fp, Drehung.nDrehung(richtung * Math.PI / 2, 0)),
						new KanteZ(z, richtung, fp));
				ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA3LR"), z, 20);
			}
			else
			{
				int richtung = InBlockRaster.drehIntD(z.dreh.wl);
				K4 fp = ankanten(z.welt, K4.plus(z.position,
						InBlockRaster.shift1d(z.welt, (richtung + 1) % 4)),
						richtung, -3.6, -19.4, 10);
				focus(new Focus(z, 20, fp, Drehung.nDrehung(richtung * Math.PI / 2, 0)),
						new KanteZ(z, richtung, fp));
				ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA3LR"), z, 20);
			}
		}
	}
}