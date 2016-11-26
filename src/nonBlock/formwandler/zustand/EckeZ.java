package nonBlock.formwandler.zustand;

import achsen.*;
import k4.*;
import nonBlock.aktion.*;
import nonBlock.formwandler.*;

public class EckeZ extends AllgKanteZ
{
	public EckeZ(FWA z, int richtung, K4 fp)
	{
		super(z, richtung);
		z.focus = new Focus(z, 20, fp, new Drehung(richtung * Math.PI / 2 + Math.PI / 4, 0));
		ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA3LR"), z, 20);
	}

	@Override
	public void kontrolle(int[] infl)
	{

	}
}