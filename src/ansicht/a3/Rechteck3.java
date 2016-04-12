package ansicht.a3;

import wahr.zugriff.*;

import java.awt.*;

public class Rechteck3 extends Anzeige3
{
	protected String text;
	protected boolean targetable;
	protected K4 rMid;
	protected Color background;
	protected Color foreground;

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
		//TODO background und foreground
	}

	public void Panel(Graphics2D gd)
	{
		gd.setPaint(background);
		gd.setFont(new Font("Consolas", Font.PLAIN, (int)(scale > 20 ? 20 : scale)));
		if(text != null)
		{
			double w = text.length() * scale / 3;
			double h = scale / 2;
			gd.fillRect((int)(xe - w), (int)(ye - h), (int)(w * 2), (int)(h * 2));
			gd.setPaint(foreground);
			gd.drawString(text, (int)(xe - w + scale / 8), (int)(ye + h - scale / 8));
		}
	}

	public void PanelDark(Graphics2D gd){}
}