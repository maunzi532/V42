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
		z.focus = new Focus(z, 20, fp, Drehung.nDrehung(richtung * Math.PI / 2, 0));
		ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA3LR"), z, 20);
	}

	@Override
	public void kontrolle(int[] infl)
	{

	}

	boolean kletterHoch()
	{
		WBP p = z.welt.tw(z.focus.targetPosition);
		p.k[1]--;
		K4 dif = K4.diff(z.welt.wt(p), z.focus.targetPosition);
		if(dif.b > z.welt.weltBlock.b)
		{
			//Boolean ck = canKlettern(grabRichtung, dif, p);
			//if(ck != null)
			{
				ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA"), z, 30);
				//z.zustand =
				z.focus = null;
				/*if(ck)
					moves.add(new Move(LadeMove.gibVonIndex(false, "WK"), this));
				else
					moves.add(new Move(LadeMove.gibVonIndex(false, "WK2"), this));*/
				return false;
			}
		}
		return true;
	}
}