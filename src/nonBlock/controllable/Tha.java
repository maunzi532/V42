package nonBlock.controllable;

import a3.*;
import achsen.*;
import ansicht.*;
import k4.*;
import nonBlock.aktion.*;
import nonBlock.aktion.lesen.*;
import nonBlock.collide.*;
import nonBlock.formwandler.*;
import nonBlock.formwandler.zustand.*;
import wahr.zugriff.*;
import welt.*;

public class Tha extends TSSA
{
	private NBB fL;
	private NBB fR;

	private Tha(SPController control, LadeFWA abilities, WeltB welt, WeltND dw, WeltNB bw)
	{
		super(control, abilities, welt, dw, bw);
	}

	public Tha(SPController control, LadeFWA abilities, AllWelt aw)
	{
		this(control, abilities, aw.wbl, aw.dw, aw.bw);
	}

	@Override
	public void init()
	{
		super.init();
		fL = new NBB(welt, dw, bw)
		{
			public void collide(Collider collider){}
			public void actCollide(Collider collider){}
			public void decollide(Collider collider){}
			public void wand(int welche){}
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
		};
		StandardAussehen.gibVonIndexS("H4/Sta").assignStandard(fR);
		fR.aussehen = new LadeModell().reload(LadeTeil.gibVonIndex("H4/Achsen", new PolyFarbe()));
		fR.focus = new Mount(fR, this, 16, 0, new Drehung(0, Math.PI * 3 / 2), 0);
		fR.init();
	}

	@Override
	public void kontrolle()
	{
		if(!dw.nofreeze())
			return;
		super.kontrolle();
		if(zustand instanceof BodenZ)
		{
			fL.linkAchsen[0].dreh.wl += 0.03;
			fL.linkAchsen[0].dreh.sichern();
			fR.linkAchsen[0].dreh.wl -= 0.03;
			fR.linkAchsen[0].dreh.sichern();
		}
	}

	@Override
	public AkA plzDislocate(String info)
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

	@Override
	public void collide(Collider collider){}

	@Override
	public void actCollide(Collider collider){}

	@Override
	public void decollide(Collider collider){}

	@Override
	public void wand(int welche){}

	@Override
	public K4 kamP()
	{
		return new K4(punkte[73][14]);
	}

	@Override
	public Drehung kamD()
	{
		return Drehung.plus(dreh, achsen[73].dreh);
	}

	@Override
	protected boolean targetFall()
	{
		return ((Overlay)tverlay).pa.taType > 0 && ((Overlay)tverlay).pa.tnTarget.target >= 0;
	}

	@Override
	protected void doFall(String fall, boolean attachChainOnly)
	{
		switch(fall)
		{
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

	@Override
	public double lichtRange()
	{
		return super.lichtRange();
	}
}