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
				10, 10, 4, 4, 10, 10, 0, 0);
		ibr.checkLen(3, 1);
		ibr.checkLen(2, 2);
		ibr.zusammenfassen(1);
		ibr.checkLen(0, 2);
		if(ibr.entspricht(new int[][][][]{{{{0, 2}}, {{1, 1}}}}, null))
		{
			z.dreh.sichern();
			int richtung = InBlockRaster.drehIntH(z.dreh.wl);
			focus(new Focus(z, 20,
					ankanten(z.welt, z.position(), richtung, 3.6, -0.2, 0),
					Drehung.nDrehung(z.dreh.wl, 0)), new AerialZ(z));
			ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA"), z, 20);
		}
		else if(ibr.entspricht(new int[][][][]{{{{0, 6}}, {{1, 2}}}}, null))
		{
			z.dreh.sichern();
			int richtung = InBlockRaster.drehIntH(z.dreh.wl);
			focus(new Focus(z, 20,
					ankanten(z.welt, z.position(), richtung, -3.6, -0.2, 0),
					Drehung.nDrehung(z.dreh.wl, 0)), new AerialZ(z));
			ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA"), z, 20);
		}
	}

	public void fallen(FWA z)
	{
		InBlockRaster ibr = new InBlockRaster(z.welt, z.position, z.dreh.wl, true,
				10, 10, 4, 4, 10, -10, 0, 0);
		ibr.checkLen(3, 1);
		ibr.checkLen(2, 1);
		ibr.zusammenfassen(1);
		ibr.checkLen(0, 2);
		if(ibr.entspricht(new int[][][][]{{{{1, 0}}}}, null))
		{
			z.dreh.sichern();
			int richtung = InBlockRaster.drehIntH(z.dreh.wl);
			focus(new Focus(z, 2,
					ankanten(z.welt, z.position(), richtung, -4, -20, 0),
					Drehung.nDrehung(z.dreh.wl, 0)), new AerialZ(z));
			ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA"), z, 20);
		}
	}

	public void seitlichKlettern(FWA z, boolean r)
	{
		InBlockRaster ibr = new InBlockRaster(z.welt, z.position, z.dreh.wl, true,
				10, 10, r ? 4 : 6, r ? 6 : 4, 10, 10, 0, 0);
		ibr.checkLen(3, 1);
		ibr.checkLen(2, 2);
		ibr.checkLen(0, 2);
		if(ibr.zerteilen(1).stream().allMatch(e ->
				e.entspricht(new int[][][][]{{{{1, 2}}, {{1, 1}}}}, null) ||
				e.entspricht(new int[][][][]{{{{1, 6}}, {{1, 2}}}}, null)))
		{
			z.focus = new Focus(z, 5, ankanten(z.welt, z.position, richtung,
					-3.6, -19.4, r ? 1.8 : -1.8), Drehung.nDrehung(z.dreh.wl, 0));
		}
		else
		{
			ibr.checkLen(1, 2);
			if(r && (ibr.entspricht(new int[][][][]{{{{1, 2}, {1, 1}}, {{1, 1}, {1, 1}}}}, null) ||
					ibr.entspricht(new int[][][][]{{{{1, 6}, {1, 1}}, {{1, 2}, {1, 1}}}}, null)))
			{
				int richtung = InBlockRaster.drehIntH(z.dreh.wl);
				K4 fp = AllgKanteZ.anecken(z.welt, K4.plus(z.position,
						InBlockRaster.shift1d(z.welt, (richtung + 3) % 4)),
						richtung, -3.6, -19.4);
				focus(new Focus(z, 20, fp, new Drehung(richtung * Math.PI / 2 + Math.PI / 4, 0)),
						new EckeZ(z, richtung, fp));
				ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA3LR"), z, 20);
			}
			if(!r && (ibr.entspricht(new int[][][][]{{{{1, 1}, {1, 2}}, {{1, 1}, {1, 1}}}}, null) ||
					ibr.entspricht(new int[][][][]{{{{1, 1}, {1, 6}}, {{1, 1}, {1, 2}}}}, null)))
			{
				int richtung = (InBlockRaster.drehIntH(z.dreh.wl) + 3) % 4;
				K4 fp = AllgKanteZ.anecken(z.welt, K4.plus(z.position,
						InBlockRaster.shift1d(z.welt, (richtung + 2) % 4)),
						richtung, -3.6, -19.4);
				focus(new Focus(z, 20, fp, new Drehung(richtung * Math.PI / 2 + Math.PI / 4, 0)),
						new EckeZ(z, richtung, fp));
				ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA3LR"), z, 20);
			}
		}
	}
}