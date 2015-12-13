package nbc;

import ansicht.*;
import falsch.*;
import k.*;
import modell.*;
import nonBlock.*;
import nonblockbox.aktion.*;
import nonblockbox.attk.*;
import nonblockbox.attk.move.*;

public class Tha extends TSSA
{
	public NBB fL;
	public NBB fR;

	public Tha()
	{
		super(new KController());
	}

	public void init()
	{
		super.init();
		fL = new NBB()
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
		fR = new NBB()
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
			NBB f = new NBB()
			{
				public void collide(Attk attk)
				{
					ende();
				}

				public void actCollide(Attk attk){}
				public void decollide(Attk attk){}
				public void wand(int welche){}
				protected void kontrolle(){}
			};
			f.aussehen = new LadeModell();
			Index.gibStandardAussehen("B_Main").assignStandard(f);
			f.aussehen.reload(Index.gibLadeTeil("B_X"));
			f.position = new K4(this.position);
			f.position.b -= this.block.get(0).airshift;
			f.dreh = new Drehung(this.dreh.wl, 0);
			f.collidable.add(new ColBox(f, 1, 0.3, 0.3, 1, 1));
			f.init();
		}
		if(command.equals("KL"))
		{
			if(Overlay.pa.taType > 0 && Overlay.pa.tnTarget != null && Overlay.pa.tnTarget > 0)
			{
				moves.add(new Move(Index.gibLadeMove("KBL",
						String.valueOf(-70 - Hauptschleife.maus.x / 10),
						String.valueOf(60 - Hauptschleife.maus.y / 10)), this));
				fL.aktionen.add(new Abbau(fL, 60, 5, Overlay.pa.tnTarget, 20, this, 15,
						new Drehung(0, Math.PI * 3 / 2), 40));
			}
			else
				moves.add(new Move(Index.gibLadeMove("KL",
						String.valueOf(-70 - Hauptschleife.maus.x / 10),
						String.valueOf(60 - Hauptschleife.maus.y / 10)), this));
		}
		if(command.equals("KR"))
		{
			if(Overlay.pa.taType > 0 && Overlay.pa.tnTarget != null && Overlay.pa.tnTarget > 0)
			{
				moves.add(new Move(Index.gibLadeMove("KBR",
						String.valueOf(70 - Hauptschleife.maus.x / 10),
						String.valueOf(60 - Hauptschleife.maus.y / 10)), this));
				fR.aktionen.add(new Abbau(fR, 60, 5, Overlay.pa.tnTarget, 20, this, 16,
						new Drehung(0, Math.PI * 3 / 2), 40));
			}
			else
				moves.add(new Move(Index.gibLadeMove("KR",
						String.valueOf(70 - Hauptschleife.maus.x / 10),
						String.valueOf(60 - Hauptschleife.maus.y / 10)), this));
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
					attemptAirgrab(0);
			}
		}
		if(command.equals("hinten"))
		{
			if(grabRichtung >= 0)
				lassLos();
		}
	}
}