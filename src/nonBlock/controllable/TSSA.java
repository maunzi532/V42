package nonBlock.controllable;

import ansicht.*;
import block.*;
import nonBlock.aktion.*;
import nonBlock.aktion.lesen.*;
import nonBlock.aussehen.*;
import nonBlock.collide.*;
import nonBlock.formwandler.*;
import wahr.zugriff.*;

import java.util.*;

public abstract class TSSA extends FWA implements Licht
{
	final Overlay overlay;
	boolean boden;
	int grabRichtung = -1;

	TSSA(Controller control, LadeFWA abilities, String currentZ, Overlay overlay, WeltB welt, LichtW lw, WeltND dw, WeltNB bw)
	{
		super(control, abilities, currentZ, welt, lw, dw, bw);
		this.overlay = overlay;
	}

	protected TSSA(Controller control, LadeFWA abilities, String currentZ, Overlay overlay, AllWelt aw)
	{
		this(control, abilities, currentZ, overlay, aw.wbl, aw.lw, aw.dw, aw.bw);
	}

	public void kontrolle()
	{
		if(!dw.nofreeze())
			return;
		double[] canInfl;
		if(grabRichtung >= 0)
		{
			canInfl = null;
			boden = false;
		}
		else
		{
			boden = naheWand(2, 0.1);
			if(boden)
				canInfl = new double[]{0.2, 0, 0, 0.2};
			else
				canInfl = new double[]{0.1, 0, 0.2, 0.1};
		}
		if(grabRichtung < 0)
		{
			if(achsen[67].dreh.wl < Staticf.nachDreh || achsen[67].dreh.wl > Math.PI * 2 - Staticf.nachDreh)
			{
				dreh.wl += achsen[67].dreh.wl;
				achsen[67].dreh.wl = 0;
			}
			else if(achsen[67].dreh.wl > Math.PI)
			{
				dreh.wl -= Staticf.nachDreh;
				achsen[67].dreh.wl += Staticf.nachDreh;
				achsen[67].dreh.sichern();
			}
			else
			{
				dreh.wl += Staticf.nachDreh;
				achsen[67].dreh.wl -= Staticf.nachDreh;
				achsen[67].dreh.sichern();
			}
			dreh.sichern();
		}
		else
		{
			if(achsen[67].dreh.wl < Math.PI * 1.5 && achsen[67].dreh.wl > Math.PI * 0.5)
			{
				if(achsen[67].dreh.wl > Math.PI)
					achsen[67].dreh.wl = Math.PI * 1.5;
				else
					achsen[67].dreh.wl = Math.PI * 0.5;
			}
		}
		boolean[] infl = control.infl();
		if(canInfl != null)
		{
			K4 cb = new K4();
			if(infl[0] != infl[1])
			{
				cb.a += Math.cos(dreh.wl) * (infl[0] ? canInfl[0] : -canInfl[0]);
				cb.c += Math.sin(dreh.wl) * (infl[0] ? canInfl[0] : -canInfl[0]);
			}
			if(infl[2] != infl[3])
				cb.b += infl[2] ? canInfl[1] : -canInfl[2];
			if(infl[4] != infl[5])
			{
				cb.a -= Math.sin(dreh.wl) * (infl[4] ? canInfl[0] : -canInfl[0]);
				cb.c += Math.cos(dreh.wl) * (infl[4] ? canInfl[0] : -canInfl[0]);
			}
			if(infl[6] != infl[7])
				cb.d += infl[6] ? canInfl[3] : -canInfl[3];
			beweg.add(cb);
		}

		inflChecks(infl);
		ArrayList<String> commands = control.giveCommands();
		for(int i = 0; i < commands.size(); i++)
			doCommand(commands.get(i));
		beweg.add(new K4(bewegung.a * 0.85, bewegung.b * 0.85, bewegung.c * 0.85, bewegung.d * 0.85));
		beweg.add(new K4(0, -0.05, 0, 0));
	}

	//Links 7 Rechts 0
	int approxRichtung()
	{
		return 7 - (int)(Drehung.sichern(dreh.wl) * 4 / Math.PI);
	}

	boolean attemptAirgrab(int type, K4 dlPosition, double dUnedited)
	{
		if(grabRichtung < 0 && !boden)
		{
			WBP p = welt.tw(dlPosition);
			K4 dif = K4.diff(welt.wt(p), dlPosition);
			if(dif.b < welt.weltBlock.b - 4 || dif.b > welt.weltBlock.b)
			{
				if(dif.b < 4)
				{
					p.k[1]--;
					dif.b += welt.weltBlock.b;
				}
				else
					return true;
			}
			int richtung = (approxRichtung() + 1) % 8 / 2;
			switch(type)
			{
				case 0:
					if(canAirgrab(richtung, dif, new WBP(p)))
					{
						K4 fp = welt.wt2(p);
						fp.b += welt.weltBlock.b / 2 + 0.6;
						if(richtung % 2 == 0)
						{
							fp.c += (welt.weltBlock.c / 2 - 3.6) * (1 - richtung);
							fp.a = dlPosition.a;
						}
						else
						{
							fp.a += (welt.weltBlock.a / 2 - 3.6) * (2 - richtung);
							fp.c = dlPosition.c;
						}
						fp.d = dUnedited;
						grabRichtung = richtung;
						if(approxRichtung() == 7)
							richtung = 4;
						focus = new Focus(this, 20, fp, Drehung.nDrehung((4 - richtung) * Math.PI / 2, 0));
						Index.gibAlternateStandard("TSSA3LR").changeToThis(this, 20, 8);
						return false;
					}
					break;
			}
		}
		return true;
	}

	private boolean canAirgrab(int richtung, K4 dif, WBP p)
	{
		switch(richtung)
		{
			case 0:
				if(dif.c > welt.weltBlock.c - 8 && dif.c < welt.weltBlock.c - 2)
				{
					if(dif.a < 3 || dif.a > welt.weltBlock.a - 3)
					{
						if(!welt.tk1(p, richtung))
							return false;
						p.k[0] += dif.a > welt.weltBlock.a / 2 ? 1 : -1;
						return welt.tk1(p, richtung);
					}
					else
						return welt.tk1(p, richtung);
				}
				break;
			case 1:
				if(dif.a > welt.weltBlock.a - 8 && dif.a < welt.weltBlock.a - 2)
				{
					if(dif.c < 3 || dif.c > welt.weltBlock.c - 3)
					{
						if(!welt.tk1(p, richtung))
							return false;
						p.k[2] += dif.c > welt.weltBlock.c / 2 ? 1 : -1;
						return welt.tk1(p, richtung);
					}
					else
						return welt.tk1(p, richtung);
				}
				break;
			case 2:
				if(dif.c < 8 && dif.c > 2)
				{
					if(dif.a < 3 || dif.a > welt.weltBlock.a - 3)
					{
						if(!welt.tk1(p, richtung))
							return false;
						p.k[0] += dif.a > welt.weltBlock.a / 2 ? 1 : -1;
						return welt.tk1(p, richtung);
					}
					else
						return welt.tk1(p, richtung);
				}
				break;
			case 3:
				if(dif.a < 8 && dif.a > 2)
				{
					if(dif.c < 3 || dif.c > welt.weltBlock.c - 3)
					{
						if(!welt.tk1(p, richtung))
							return false;
						p.k[2] += dif.c > welt.weltBlock.c / 2 ? 1 : -1;
						return welt.tk1(p, richtung);
					}
					else
						return welt.tk1(p, richtung);
				}
				break;
		}
		return false;
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
					Index.gibAlternateStandard("TSSA").changeToThis(this, 30, 10);
					focus = null;
					grabRichtung = -1;
					if(ck)
						moves.add(new Move(Index.gibLadeMove(false, "WK", String.valueOf(-Math.sin(dreh.wl) * 5),
								String.valueOf(Math.cos(dreh.wl) * 5)), this));
					else
						moves.add(new Move(Index.gibLadeMove(false, "WK", "0", "0"), this));
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
			Index.gibAlternateStandard("TSSA").changeToThis(this, 20, 10);
			focus = null;
			grabRichtung = -1;
			//bewegung.b = 2;
			return false;
		}
		return true;
	}

	private Boolean canKlettern(int richtung, K4 dif, WBP p)
	{
		if(richtung % 2 == 0 && (dif.a < 3 || dif.a > welt.weltBlock.a - 3))
		{
			Boolean k1 = welt.tk2(p, richtung);
			if(k1 == null)
				return null;
			p.k[0] += dif.a > welt.weltBlock.a / 2 ? 1 : -1;
			Boolean k2 = welt.tk2(p, richtung);
			if(k2 == null)
				return null;
			return k1 && k2;
		}
		else if(richtung % 2 != 0 && (dif.c < 3 || dif.c > welt.weltBlock.c - 3))
		{
			Boolean k1 = welt.tk2(p, richtung);
			if(k1 == null)
				return null;
			p.k[2] += dif.c > welt.weltBlock.c / 2 ? 1 : -1;
			Boolean k2 = welt.tk2(p, richtung);
			if(k2 == null)
				return null;
			return k1 && k2;
		}
		else
			return welt.tk2(p, richtung);
	}

	boolean kletterSeitlich(boolean richtung)
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
			if(welt.tk1(welt.tw(p2), grabRichtung))
			{
				if(kR % 2 == 0)
					focus.targetPosition.c += 0.2 * (1 - kR);
				else
					focus.targetPosition.a += 0.2 * (2 - kR);
			}
		}
		return false;
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
		return 0.4;
	}
}