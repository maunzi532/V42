package nonBlock.formwandler.zustand;

import achsen.*;
import k4.*;
import nonBlock.aktion.*;
import nonBlock.formwandler.*;

public class KanteZ extends AllgKanteZ
{
	public KanteZ(FWA z, int richtung1, K4 fp)
	{
		super(z, richtung1 % 4);
		z.focus = new Focus(z, 20, fp, Drehung.nDrehung(richtung * Math.PI / 2, 0));
		ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA3LR"), z, 20);
	}

	@Override
	public void kontrolle(int[] infl)
	{

	}
}