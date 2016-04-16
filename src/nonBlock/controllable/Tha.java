package nonBlock.controllable;

import ansicht.*;
import block.*;
import nonBlock.aktion.*;
import nonBlock.aktion.lesen.*;
import nonBlock.aussehen.*;
import nonBlock.collide.*;
import nonBlock.formwandler.*;
import wahr.zugriff.*;

public class Tha extends TSSA
{
	private NBB fL;
	private NBB fR;

	private Tha(SPController control, LadeFWA abilities, WeltB welt, LichtW lw, WeltND dw, WeltNB bw)
	{
		super(control, abilities, "Luft", welt, lw, dw, bw);
	}

	public Tha(SPController control, LadeFWA abilities, AllWelt aw)
	{
		this(control, abilities, aw.wbl, aw.lw, aw.dw, aw.bw);
	}

	public void init()
	{
		super.init();
		fL = new NBB(welt, lw, dw, bw)
		{
			public void collide(Attk attk){}
			public void actCollide(Attk attk){}
			public void decollide(Attk attk){}
			public void wand(int welche){}
			protected void kontrolle(){}
		};
		StandardAussehen.gibVonIndex2("H4/Sta").assignStandard(fL);
		fL.aussehen = new LadeModell().reload(LadeTeil.gibVonIndex("H4/Achsen"));
		fL.focus = new Mount(fL, this, 15, 0, new Drehung(0, Math.PI * 3 / 2), 0);
		fL.init();
		fR = new NBB(welt, lw, dw, bw)
		{
			public void collide(Attk attk){}
			public void actCollide(Attk attk){}
			public void decollide(Attk attk){}
			public void wand(int welche){}
			protected void kontrolle(){}
		};
		StandardAussehen.gibVonIndex2("H4/Sta").assignStandard(fR);
		fR.aussehen = new LadeModell().reload(LadeTeil.gibVonIndex("H4/Achsen"));
		fR.focus = new Mount(fR, this, 16, 0, new Drehung(0, Math.PI * 3 / 2), 0);
		fR.init();
	}

	public void kontrolle()
	{
		if(!dw.nofreeze())
			return;
		super.kontrolle();
		fL.linkAchsen[0].dreh.wl += 0.03;
		fL.linkAchsen[0].dreh.sichern();
		fR.linkAchsen[0].dreh.wl -= 0.03;
		fR.linkAchsen[0].dreh.sichern();
	}

	public NBD plzDislocate(String info)
	{
		switch(info)
		{
			case "HL":
				return fL;
			case "HR":
				return fR;
			default:
				return this;
		}
	}

	public void collide(Attk attk){}

	public void actCollide(Attk attk){}

	public void decollide(Attk attk){}

	public void wand(int welche){}

	public K4 kamP()
	{
		return new K4(punkte[73][14]);
	}

	public Drehung kamD()
	{
		return Drehung.plus(dreh, achsen[73].dreh);
	}

	/*public void doCommand(String command)
	{
		if(command.equals("A"))
		{
			Flag f = new Flag(welt, lw, dw, bw);
			f.aussehen = new LadeModell();
			Index.gibStandardAussehen("Flagge/Sta").assignStandard(f);
			f.aussehen.reload(Index.gibLadeTeil("Flagge/Achsen"));
			f.position = new K4(this.position);
			f.position.b -= this.block.get(0).airshift;
			f.dreh = new Drehung(this.dreh.wl, 0);
			f.collidable.add(new ColBox(f, 1, new EndScheibe(0.3), new EndScheibe(0.3), 1, 1));
			f.init();
			return;
		}
		if(command.equals("KL"))
		{
			if(overlay.pa.taType > 0 && overlay.pa.tnTarget != null && overlay.pa.tnTarget >= 0)
			{
				moves.add(new Move(Index.gibLadeMove(false, "KBL",
						String.valueOf(-70 - UIVerbunden.maus.x / 10),
						String.valueOf(60 - UIVerbunden.maus.y / 10)), this));
				fL.aktionen.add(new Abbau(fL, 60, 5, overlay.pa.tnTarget, 20, this, 15,
						new Drehung(0, Math.PI * 3 / 2), 40));
			}
			else
				moves.add(new Move(Index.gibLadeMove(false, "KL",
						String.valueOf(-70 - UIVerbunden.maus.x / 10),
						String.valueOf(60 - UIVerbunden.maus.y / 10)), this));
			return;
		}
		if(command.equals("KR"))
		{
			if(overlay.pa.taType > 0 && overlay.pa.tnTarget != null && overlay.pa.tnTarget >= 0)
			{
				moves.add(new Move(Index.gibLadeMove(false, "KBR",
						String.valueOf(70 - UIVerbunden.maus.x / 10),
						String.valueOf(60 - UIVerbunden.maus.y / 10)), this));
				fR.aktionen.add(new Abbau(fR, 60, 5, overlay.pa.tnTarget, 20, this, 16,
						new Drehung(0, Math.PI * 3 / 2), 40));
			}
			else
				moves.add(new Move(Index.gibLadeMove(false, "KR",
						String.valueOf(70 - UIVerbunden.maus.x / 10),
						String.valueOf(60 - UIVerbunden.maus.y / 10)), this));
			return;
		}
		super.doCommand(command);
	}*/

	protected void doFall(String fall, boolean attachChainOnly)
	{
		switch(fall)
		{
			case "Luftvor":
				boolean can = true;
				for(Move a : moves)
					if(a.name.equals("WK"))
					{
						can = false;
						break;
					}
				if(can)
				{
					if(attemptAirgrab(0, position, position.d))
					{
						if(position.d - welt.intiize(position.d / welt.weltBlock.d) *
								welt.weltBlock.d < Staticf.zpSpeed)
							attemptAirgrab(0, new K4(position.a, position.b, position.c,
									position.d - Staticf.zpSpeed), position.d - Staticf.zpSpeed / 2);
						if(position.d - welt.intiize(position.d / welt.weltBlock.d) *
								welt.weltBlock.d > welt.weltBlock.d - Staticf.zpSpeed)
							attemptAirgrab(0, new K4(position.a, position.b, position.c,
									position.d + Staticf.zpSpeed), position.d + Staticf.zpSpeed / 2);
					}
				}
				break;
			case "WandL":
				kletterSeitlich(false);
				break;
			case "WandR":
				kletterSeitlich(true);
				break;
			case "KletterHoch":
				kletterHoch();
				break;
			case "LassLos":
				lassLos();
				break;
			case "Ducken":
				if(approxRichtung() % 2 == 0)
					AlternateStandard.gibVonIndex1("TSSA2L").changeToThis(this, 30, 3);
				else
					AlternateStandard.gibVonIndex1("TSSA2R").changeToThis(this, 30, 3);
				lastZ = currentZ;
				currentZ = "Ducken";
				break;
			case "Aufstehen":
				AlternateStandard.gibVonIndex1("TSSA").changeToThis(this, 40, 3);
				lastZ = currentZ;
				currentZ = "Normal";
				break;
		}
	}
}