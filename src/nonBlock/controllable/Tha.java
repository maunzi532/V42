package nonBlock.controllable;

import a3.*;
import achsen.*;
import ansicht.*;
import k4.*;
import nonBlock.aktion.*;
import nonBlock.aktion.lesen.*;
import nonBlock.collide.*;
import nonBlock.formwandler.*;
import wahr.zugriff.*;
import welt.*;

public class Tha extends TSSA
{
	private NBB fL;
	private NBB fR;

	private Tha(SPController control, LadeFWA abilities, WeltB welt, WeltND dw, WeltNB bw)
	{
		super(control, abilities, "Luft", welt, dw, bw);
	}

	public Tha(SPController control, LadeFWA abilities, AllWelt aw)
	{
		this(control, abilities, aw.wbl, aw.dw, aw.bw);
	}

	public void init()
	{
		super.init();
		fL = new NBB(welt, dw, bw)
		{
			public void collide(Collider collider){}
			public void actCollide(Collider collider){}
			public void decollide(Collider collider){}
			public void wand(int welche){}
			protected void kontrolle(){}
		};
		StandardAussehen.gibVonIndexS("H4/Sta").assignStandard(fL);
		fL.aussehen = new LadeModell().reload(LadeTeil.gibVonIndex("H4/Achsen", new PolyFarbe()));
		fL.focus = new Mount(fL, this, 15, 0, new Drehung(0, Math.PI * 3 / 2), 0);
		fL.init();
		fR = new NBB(welt, dw, bw)
		{
			public void collide(Collider collider){}
			public void actCollide(Collider collider){}
			public void decollide(Collider collider){}
			public void wand(int welche){}
			protected void kontrolle(){}
		};
		StandardAussehen.gibVonIndexS("H4/Sta").assignStandard(fR);
		fR.aussehen = new LadeModell().reload(LadeTeil.gibVonIndex("H4/Achsen", new PolyFarbe()));
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

	public void collide(Collider collider){}

	public void actCollide(Collider collider){}

	public void decollide(Collider collider){}

	public void wand(int welche){}

	public K4 kamP()
	{
		return new K4(punkte[73][14]);
	}

	public Drehung kamD()
	{
		return Drehung.plus(dreh, achsen[73].dreh);
	}

	protected boolean targetFall()
	{
		return ((Overlay)tverlay).pa.taType > 0 && ((Overlay)tverlay).pa.tnTarget >= 0;
	}

	protected void doFall(String fall, boolean attachChainOnly)
	{
		switch(fall)
		{
			case "Luftvor":
				boolean can = true;
				for(Move a : moves)
					if(a.name.equals("WK") || a.name.equals("WK2"))
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
					ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA2L"), this, 30);
				else
					ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA2R"), this, 30);
				lastZ = currentZ;
				currentZ = "Ducken";
				break;
			case "Aufstehen":
				ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA"), this, 40);
				lastZ = currentZ;
				currentZ = "Normal";
				break;
			case "KL":
			{
				Panelizer pa = ((Overlay) tverlay).pa;
				moves.add(new Move(LadeMove.gibVonIndex(false, "KL2"), this));
				fL.aktionen.add(new Abbau(fL, 60, 5, pa.tnTarget, 20, this, 15,
						new Drehung(0, Math.PI * 3 / 2), 40));
				break;
			}
			case "KR":
			{
				Panelizer pa = ((Overlay) tverlay).pa;
				moves.add(new Move(LadeMove.gibVonIndex(false, "KR2"), this));
				fR.aktionen.add(new Abbau(fR, 60, 5, pa.tnTarget, 20, this, 16,
						new Drehung(0, Math.PI * 3 / 2), 40));
				break;
			}
		}
	}

	/*public double lichtRange()
	{
		return 0;
	}*/
}