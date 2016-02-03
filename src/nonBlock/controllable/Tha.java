package nonBlock.controllable;

import ansicht.*;
import block.*;
import nonBlock.aktion.lesen.*;
import nonBlock.aussehen.*;
import nonBlock.aktion.*;
import nonBlock.collide.*;
import wahr.zugriff.*;

public class Tha extends TSSA
{
	private NBB fL;
	private NBB fR;

	private Tha(Overlay overlay, WeltB welt, LichtW lw, WeltND dw, WeltNB bw)
	{
		super(new KController(), overlay, welt, lw, dw, bw);
	}

	public Tha(Overlay overlay, AllWelt aw)
	{
		this(overlay, aw.wbl, aw.lw, aw.dw, aw.bw);
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
		Index.gibStandardAussehen("H4_Main").assignStandard(fL);
		fL.aussehen = new LadeModell().reload(Index.gibLadeTeil("H4_X"));
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
		Index.gibStandardAussehen("H4_Main").assignStandard(fR);
		fR.aussehen = new LadeModell().reload(Index.gibLadeTeil("H4_X"));
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

	public void doCommand(String command)
	{
		if(command.equals("A"))
		{
			Flag f = new Flag(welt, lw, dw, bw);
			f.aussehen = new LadeModell();
			Index.gibStandardAussehen("F_Main").assignStandard(f);
			f.aussehen.reload(Index.gibLadeTeil("F_X"));
			f.position = new K4(this.position);
			f.position.b -= this.block.get(0).airshift;
			f.dreh = new Drehung(this.dreh.wl, 0);
			f.collidable.add(new ColBox(f, 1, new EndScheibe(0.3), new EndScheibe(0.3), 1, 1));
			f.init();
		}
		if(command.equals("KL"))
		{
			if(overlay.pa.taType > 0 && overlay.pa.tnTarget != null && overlay.pa.tnTarget >= 0)
			{
				moves.add(new Move(Index.gibLadeMove("KBL",
						String.valueOf(-70 - UIVerbunden.maus.x / 10),
						String.valueOf(60 - UIVerbunden.maus.y / 10)), this));
				fL.aktionen.add(new Abbau(fL, 60, 5, overlay.pa.tnTarget, 20, this, 15,
						new Drehung(0, Math.PI * 3 / 2), 40));
			}
			else
				moves.add(new Move(Index.gibLadeMove("KL",
						String.valueOf(-70 - UIVerbunden.maus.x / 10),
						String.valueOf(60 - UIVerbunden.maus.y / 10)), this));
		}
		if(command.equals("KR"))
		{
			if(overlay.pa.taType > 0 && overlay.pa.tnTarget != null && overlay.pa.tnTarget >= 0)
			{
				moves.add(new Move(Index.gibLadeMove("KBR",
						String.valueOf(70 - UIVerbunden.maus.x / 10),
						String.valueOf(60 - UIVerbunden.maus.y / 10)), this));
				fR.aktionen.add(new Abbau(fR, 60, 5, overlay.pa.tnTarget, 20, this, 16,
						new Drehung(0, Math.PI * 3 / 2), 40));
			}
			else
				moves.add(new Move(Index.gibLadeMove("KR",
						String.valueOf(70 - UIVerbunden.maus.x / 10),
						String.valueOf(60 - UIVerbunden.maus.y / 10)), this));
		}
		if(command.equals("unten"))
		{
			if(boden)
				if(approxRichtung() % 2 == 0)
					Index.gibAlternateStandard("TSSA2L").changeToThis(this);
				else
					Index.gibAlternateStandard("TSSA2R").changeToThis(this);
		}
		if(command.equals("oben"))
		{
			if(boden)
			{
				moves.add(new Move(Index.gibLadeMove("MV1"), this));
				Index.gibAlternateStandard("TSSA").changeToThis(this);
				boden = false;
			}
			else if(grabRichtung < 0)
				Index.gibAlternateStandard("TSSA").changeToThis(this);
		}
		if(command.equals("vorne"))
		{
			if(grabRichtung >= 0)
			{
				boolean can = true;
				for(Aktion a : aktionen)
					if(a instanceof AltTrans)
					{
						can = false;
						break;
					}
				if(can)
					kletterHoch();
			}
		}
		if(command.equals("vor"))
		{
			if(grabRichtung < 0 && !boden)
			{
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
			}
		}
		if(command.equals("links"))
		{
			kletterSeitlich(false);
		}
		if(command.equals("rechts"))
		{
			kletterSeitlich(true);
		}
		if(command.equals("hinten"))
		{
			if(grabRichtung >= 0)
				lassLos();
		}
	}
}