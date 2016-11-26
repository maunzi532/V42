package nonBlock.controllable;

import a3.*;
import achsen.*;
import java.util.*;
import k4.*;
import nonBlock.aktion.*;
import nonBlock.aktion.lesen.*;
import nonBlock.collide.*;
import nonBlock.formwandler.*;
import wahr.zugriff.*;
import welt.*;

public abstract class TSSA extends FWA implements Licht
{
	//private static final double nachDreh = 0.1;
	//private int hda = 67;
	/*private static List<String> zustands = Arrays.asList("Normal", "Kante", "Luft", "Ducken");
	private static double[][] zinfl = new double[][]{{0.2, 0, 0, 0.2}, null,
			{0.1, 0, 0.2, 0.1}, null};
	private static Boolean[] zboden = new Boolean[]{true, null, false, true};
	private static boolean[] canDreh = new boolean[]{true, false, true, false};*/
	//Zustand in FWA einbauen
	//Zustand als Objekt
	//mit zusatz werten
	private int grabRichtung = -1;

	TSSA(Controller control, LadeFWA abilities, String currentZ, WeltB welt, WeltND dw, WeltNB bw)
	{
		super(control, abilities, currentZ, welt, dw, bw);
	}

	protected TSSA(Controller control, LadeFWA abilities, String currentZ, AllWelt aw)
	{
		this(control, abilities, currentZ, aw.wbl, aw.dw, aw.bw);
	}

	public void kontrolle()
	{
		if(!dw.nofreeze())
			return;
		zustand.kontrolleDrehung();
		zustand.kontrolle(control.infl());
		//inflChecks(control.infl());
		ArrayList<String> commands = control.giveCommands();
		for(int i = 0; i < commands.size(); i++)
			doCommand(commands.get(i));
	}

	//Links 7 Rechts 0
	int approxRichtung()
	{
		return 7 - (int)(Drehung.sichern(dreh.wl) * 4 / Math.PI);
	}

	boolean attemptAirgrab(K4 dlPosition, double dUnedited)
	{
		WBP p = welt.tw(dlPosition);
		boolean drehArt = InBlockRaster.drehArt(dreh.wl);
		if(drehArt)
		{
			InBlockRaster ibr = new InBlockRaster(welt, position, dreh.wl, true, 4, 16, 4, 4, 0, 20, 0, 0);
			ibr.zusammenfassen(3);
			ibr.len(2, 2);
			ibr.zusammenfassen(1);
			ibr.len(0, 2);
			if(ibr.entspricht(new int[][][][]{{{{1, 2}}, {{1, 1}}}},
					new boolean[][][][]{{{{true, true}}, {{true, true}}}}))
			{
				int richtung = InBlockRaster.drehIntH(dreh.wl);
				K4 fp = welt.wt(p);
				fp.b += welt.weltBlock.b + 0.6;
				if(richtung % 2 == 0)
				{
					if(richtung == 0)
						fp.c += welt.weltBlock.c - 3.6;
					else
						fp.c += 3.6;
					fp.a = dlPosition.a;
				}
				else
				{
					if(richtung == 3)
						fp.a += welt.weltBlock.a - 3.6;
					else
						fp.a += 3.6;
					fp.c = dlPosition.c;
				}
				fp.d = dUnedited;
				grabRichtung = approxRichtung();
				if(InBlockRaster.drehIntH2(dreh.wl) == 7)
					richtung = 4;
				focus = new Focus(this, 20, fp, Drehung.nDrehung(richtung * Math.PI / 2, 0));
				ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA3LR"), this, 20);
				//lastZ = currentZ;
				//currentZ = "Kante";
				return false;
			}
		}
		else
		{
			InBlockRaster ibr = new InBlockRaster(welt, position, dreh.wl, false, 4, 16, 16, 4, 0, 20, 0, 0);
			//System.out.println(ibr);
			ibr.zusammenfassen(3);
			ibr.len(2, 2);
			ibr.len(1, 2);
			ibr.len(0, 2);
			if(ibr.entspricht(new int[][][][]{{{{1, 2}, {1, 1}}, {{1, 1}, {1, 1}}}},
					new boolean[][][][]{{{{true, true}, {true, true}}, {{true, true}, {true, true}}}}))
			{
				int richtung = InBlockRaster.drehIntD(dreh.wl);
				K4 fp = welt.wt(p);
				fp.b += welt.weltBlock.b + 0.6;
				if(richtung == 0 || richtung == 3)
					fp.c += welt.weltBlock.c - 3.6;
				else
					fp.c += 3.6;
				if(richtung >= 2)
					fp.a += welt.weltBlock.a - 3.6;
				else
					fp.a += 3.6;
				fp.d = dUnedited;
				//grabRichtung = approxRichtung();
				focus = new Focus(this, 20, fp, new Drehung(richtung * Math.PI / 2 + Math.PI / 4, 0));
				ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA3LR"), this, 20);
				//lastZ = currentZ;
				//currentZ = "Ecke";
				return false;
			}
		}
		return true;
	}

	boolean kletterHoch()
	{
		if(grabRichtung >= 0)
		{
			WBP p = welt.tw(focus.targetPosition);
			p.k[1]--;
			K4 dif = K4.diff(welt.wt(p), focus.targetPosition);
			if(dif.b > welt.weltBlock.b)
			{
				Boolean ck = canKlettern(grabRichtung, dif, p);
				if(ck != null)
				{
					ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA"), this, 30);
					//lastZ = currentZ;
					//currentZ = "Luft";
					focus = null;
					grabRichtung = -1;
					if(ck)
						moves.add(new Move(LadeMove.gibVonIndex(false, "WK"), this));
					else
						moves.add(new Move(LadeMove.gibVonIndex(false, "WK2"), this));
					return false;
				}
			}
		}
		return true;
	}

	boolean lassLos()
	{
		if(grabRichtung >= 0)
		{
			ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA"), this, 20);
			//lastZ = currentZ;
			//currentZ = "Luft";
			focus = null;
			grabRichtung = -1;
			return false;
		}
		return true;
	}

	private Boolean canKlettern(int richtung, K4 dif, WBP p)
	{
		if(richtung % 2 == 0 && (dif.a < 3 || dif.a > welt.weltBlock.a - 3))
		{
			Boolean k1 = WeltB.tk2(welt, p, richtung);
			if(k1 == null)
				return null;
			p.k[0] += dif.a > welt.weltBlock.a / 2 ? 1 : -1;
			Boolean k2 = WeltB.tk2(welt, p, richtung);
			if(k2 == null)
				return null;
			return k1 && k2;
		}
		else if(richtung % 2 != 0 && (dif.c < 3 || dif.c > welt.weltBlock.c - 3))
		{
			Boolean k1 = WeltB.tk2(welt, p, richtung);
			if(k1 == null)
				return null;
			p.k[2] += dif.c > welt.weltBlock.c / 2 ? 1 : -1;
			Boolean k2 = WeltB.tk2(welt, p, richtung);
			if(k2 == null)
				return null;
			return k1 && k2;
		}
		else
			return WeltB.tk2(welt, p, richtung);
	}

	void kletterSeitlich(boolean richtung)
	{
		if(grabRichtung >= 0)
		{
			K4 p2 = new K4(focus.targetPosition);
			int kR = (grabRichtung + (richtung ? 1 : 3)) % 4;
			if(kR % 2 == 0)
				p2.c += 3.2 * (1 - kR);
			else
				p2.a += 3.2 * (2 - kR);
			p2.b -= 2;
			if(WeltB.tk1(welt, welt.tw(p2), grabRichtung))
			{
				if(kR % 2 == 0)
					focus.targetPosition.c += 0.4 * (1 - kR);
				else
					focus.targetPosition.a += 0.4 * (2 - kR);
			}
		}
	}

	public K4 lichtPosition()
	{
		return position;
	}

	public double lichtRange()
	{
		return 800;
	}

	public double lichtPower()
	{
		return 30;
	}

	public double lichtPowerDecay()
	{
		return 0.8;
	}
}