package nonBlock.controllable;

import ansicht.*;
import block.*;
import nonBlock.*;
import nonBlock.collide.*;
import nonBlock.aktion.move.*;
import wahr.zugriff.*;

import java.util.*;

public abstract class TSSA extends NBB implements Controllable, Licht
{
	private final Controller control;
	boolean boden;
	int grabRichtung = -1;

	public TSSA(Controller control)
	{
		super();
		this.control = control;
	}

	public void kontrolle()
	{
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
		ArrayList<String> commands = control.giveCommands();
		for(int i = 0; i < commands.size(); i++)
			doCommand(commands.get(i));
		if(canInfl != null)
		{
			boolean[] infl = control.infl();
			if(infl[0] != infl[1])
			{
				bewegung.a += Math.cos(dreh.wl) * (infl[0] ? canInfl[0] : -canInfl[0]);
				bewegung.c += Math.sin(dreh.wl) * (infl[0] ? canInfl[0] : -canInfl[0]);
			}
			if(infl[2] != infl[3])
				bewegung.b += infl[2] ? canInfl[1] : -canInfl[2];
			if(infl[4] != infl[5])
			{
				bewegung.a -= Math.sin(dreh.wl) * (infl[4] ? canInfl[0] : -canInfl[0]);
				bewegung.c += Math.cos(dreh.wl) * (infl[4] ? canInfl[0] : -canInfl[0]);
			}
			if(infl[6] != infl[7])
				bewegung.d += infl[6] ? canInfl[3] : -canInfl[3];
		}
		bewegung = new K4(bewegung.a * 0.85, bewegung.b * 0.85 - 0.05, bewegung.c * 0.85, bewegung.d * 0.85);
	}

	public abstract K4 kamP();

	public abstract Drehung kamD();

	public abstract void doCommand(String command);

	//Links 7 Rechts 0
	int approxRichtung()
	{
		return 7 - (int)(Drehung.sichern(dreh.wl) * 4 / Math.PI);
	}

	boolean attemptAirgrab(int type, K4 dlPosition)
	{
		if(grabRichtung < 0 && !boden)
		{
			WBP p = Koord.tw(dlPosition);
			K4 dif = K4.diff(Koord.wt(p), dlPosition);
			if(dif.b < Koord.weltBlock.b - 4 || dif.b > Koord.weltBlock.b)
			{
				if(dif.b < 4)
				{
					p.k[1]--;
					dif.b += Koord.weltBlock.b;
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
						K4 fp = Koord.wt2(p);
						fp.b += Koord.weltBlock.b / 2 + 0.6;
						if(richtung % 2 == 0)
						{
							fp.c += (Koord.weltBlock.c / 2 - 3.6) * (1 - richtung);
							fp.a = dlPosition.a;
						}
						else
						{
							fp.a += (Koord.weltBlock.a / 2 - 3.6) * (2 - richtung);
							fp.c = dlPosition.c;
						}
						fp.d = dlPosition.d;
						grabRichtung = richtung;
						if(approxRichtung() == 7)
							richtung = 4;
						focus = new Focus(this, 20, fp, Drehung.nDrehung((4 - richtung) * Math.PI / 2, 0));
						Index.gibAlternateStandard("TSSA3LR").changeToThis(this);
						return false;
					}
					break;
			}
		}
		return true;
	}

	private static boolean canAirgrab(int richtung, K4 dif, WBP p)
	{
		switch(richtung)
		{
			case 0:
				if(dif.c > Koord.weltBlock.c - 8 && dif.c < Koord.weltBlock.c - 2)
				{
					if(dif.a < 3 || dif.a > Koord.weltBlock.a - 3)
					{
						if(!WeltB.tk1(p, richtung))
							return false;
						p.k[0] += dif.a > Koord.weltBlock.a / 2 ? 1 : -1;
						return WeltB.tk1(p, richtung);
					}
					else
						return WeltB.tk1(p, richtung);
				}
				break;
			case 1:
				if(dif.a > Koord.weltBlock.a - 8 && dif.a < Koord.weltBlock.a - 2)
				{
					if(dif.c < 3 || dif.c > Koord.weltBlock.c - 3)
					{
						if(!WeltB.tk1(p, richtung))
							return false;
						p.k[2] += dif.c > Koord.weltBlock.c / 2 ? 1 : -1;
						return WeltB.tk1(p, richtung);
					}
					else
						return WeltB.tk1(p, richtung);
				}
				break;
			case 2:
				if(dif.c < 8 && dif.c > 2)
				{
					if(dif.a < 3 || dif.a > Koord.weltBlock.a - 3)
					{
						if(!WeltB.tk1(p, richtung))
							return false;
						p.k[0] += dif.a > Koord.weltBlock.a / 2 ? 1 : -1;
						return WeltB.tk1(p, richtung);
					}
					else
						return WeltB.tk1(p, richtung);
				}
				break;
			case 3:
				if(dif.a < 8 && dif.a > 2)
				{
					if(dif.c < 3 || dif.c > Koord.weltBlock.c - 3)
					{
						if(!WeltB.tk1(p, richtung))
							return false;
						p.k[2] += dif.c > Koord.weltBlock.c / 2 ? 1 : -1;
						return WeltB.tk1(p, richtung);
					}
					else
						return WeltB.tk1(p, richtung);
				}
				break;
		}
		return false;
	}

	boolean kletterHoch()
	{
		if(grabRichtung >= 0)
		{
			WBP p = Koord.tw(focus.targetPosition);
			p.k[1]--;
			K4 dif = K4.diff(Koord.wt(p), focus.targetPosition);
			if(dif.b > Koord.weltBlock.b)
			{
				Boolean ck = canKlettern(grabRichtung, dif, p);
				if(ck != null)
				{
					Index.gibAlternateStandard("TSSA").changeToThis(this);
					focus = null;
					grabRichtung = -1;
					if(ck)
						moves.add(new Move(Index.gibLadeMove("WK", String.valueOf(-Math.sin(dreh.wl) * 5),
								String.valueOf(Math.cos(dreh.wl) * 5)), this));
					else
						moves.add(new Move(Index.gibLadeMove("WK", "0", "0"), this));
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
			Index.gibAlternateStandard("TSSA").changeToThis(this);
			focus = null;
			grabRichtung = -1;
			//bewegung.b = 2;
			return false;
		}
		return true;
	}

	private static Boolean canKlettern(int richtung, K4 dif, WBP p)
	{
		if(richtung % 2 == 0 && (dif.a < 3 || dif.a > Koord.weltBlock.a - 3))
		{
			Boolean k1 = WeltB.tk2(p, richtung);
			if(k1 == null)
				return null;
			p.k[0] += dif.a > Koord.weltBlock.a / 2 ? 1 : -1;
			Boolean k2 = WeltB.tk2(p, richtung);
			if(k2 == null)
				return null;
			return k1 && k2;
		}
		else if(richtung % 2 != 0 && (dif.c < 3 || dif.c > Koord.weltBlock.c - 3))
		{
			Boolean k1 = WeltB.tk2(p, richtung);
			if(k1 == null)
				return null;
			p.k[2] += dif.c > Koord.weltBlock.c / 2 ? 1 : -1;
			Boolean k2 = WeltB.tk2(p, richtung);
			if(k2 == null)
				return null;
			return k1 && k2;
		}
		else
			return WeltB.tk2(p, richtung);
	}

	public K4 lichtPosition()
	{
		return position;
	}

	public double lichtRange()
	{
		return 300;
	}

	public double lichtPower()
	{
		return 30;
	}

	public double lichtPowerDecay()
	{
		return 0.1;
	}
}