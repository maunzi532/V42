package ansicht.n2;

import wahr.zugriff.*;

import java.awt.*;

public class Rechteck3 extends Anzeige3
{
	protected String text;
	protected boolean targetable;
	protected K4 rMid;

	protected int xe;
	protected int ye;
	protected double scale;

	public Rechteck3(long tn, K4 mid, boolean targetable, String text)
	{
		super(tn);
		rMid = mid;
		this.targetable = targetable;
		this.text = text;
	}

	public boolean errechneKam(K4 kamP, Drehung kamD)
	{
		return false; //TODO
	}

	public void farbeFlaeche(Long tnTarget, int wI, int hI)
	{
		double ca = kamMid.c;
		if(ca < Staticf.nnull)
			ca = Staticf.nnull;
		xe = ethaX(kamMid.a, ca, wI);
		ye = ethaY(kamMid.b, ca, wI, hI);
		ddiff = kamMid.d;
		scale = (int)(1000 / ca) + 1;
		//TODO dFarb = farbe.errechneFarbe(this);
	}

	public void Panel(Graphics2D gd)
	{
		//TODO
	}

	public void PanelDark(Graphics2D gd)
	{
		//TODO
	}
}