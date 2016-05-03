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