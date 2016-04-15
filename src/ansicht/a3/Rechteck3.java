package ansicht.a3;

import ansicht.*;
import wahr.zugriff.*;

import java.awt.*;

public class Rechteck3 extends Anzeige3
{
	protected String text;
	protected boolean targetable;
	protected Color background;
	protected Color foreground;

	protected int xe;
	protected int ye;
	protected double scale;
	protected Paint backgroundNew;
	protected Paint foregroundNew;

	public Rechteck3(long tn, LichtW lw, K4 mid, K4 kMid, boolean targetable, String text)
	{
		super(tn, lw);
		rMid = mid;
		kamMid = kMid;
		this.targetable = targetable;
		this.text = text;
	}

	public void farbeFlaeche(Long tnTarget, int wI, int hI, K4 kam, double xrZone)
	{
		double ca = kamMid.c;
		if(ca < Staticf.nnull)
			ca = Staticf.nnull;
		xe = ethaX(kamMid.a, ca, wI);
		ye = ethaY(kamMid.b, ca, wI, hI);
		ddiff = kamMid.d;
		scale = (int)(1000 / ca) + 1;
		checkForVanishing(background);
		if(!anzeigen)
			return;
		backgroundNew = errechneFarbe(background, tnTarget);
		foregroundNew = errechneFarbe(foreground, tnTarget);
	}

	public Paint errechneFarbe(Color fc, Long tnC)
	{
		double weg = Math.sqrt(kamMid.a * kamMid.a + kamMid.b * kamMid.b +
				kamMid.c * kamMid.c + kamMid.d * kamMid.d);
		double nah = (Staticf.sicht - weg) / Staticf.sicht; //Wenn nah 1, am Rand 0
		if(nah < 0)
			nah = 0;
		if(tn != -1 && tnC != null && tn == tnC)
			fc = limit(fc, 60, 60, 60); //Sichthilfe wenn getargetet
		return new Color((int)(fc.getRed() * nah + 20 * (1 - nah)),
				(int)(fc.getGreen() * nah + 0 * (1 - nah)),
				(int)(fc.getBlue() * nah + 0 * (1 - nah)), fc.getAlpha()); //Fading nach Dunkelrot
	}

	public void panel(Graphics2D gd)
	{
		gd.setPaint(backgroundNew);
		gd.setFont(new Font("Consolas", Font.PLAIN, (int)(scale > 20 ? 20 : scale)));
		if(text != null)
		{
			double w = text.length() * scale / 3;
			double h = scale / 2;
			gd.fillRect((int)(xe - w), (int)(ye - h), (int)(w * 2), (int)(h * 2));
			gd.setPaint(foregroundNew);
			gd.drawString(text, (int)(xe - w + scale / 8), (int)(ye + h - scale / 8));
		}
	}

	public void panelDark(Graphics2D gd){}

	public Color limit(Color c, int r, int g, int b)
	{
		r += c.getRed();
		if(r > 255)
			r = 255;
		if(r < 0)
			r = 0;
		g += c.getGreen();
		if(g > 255)
			g = 255;
		if(g < 0)
			g = 0;
		b += c.getBlue();
		if(b > 255)
			b = 255;
		if(b < 0)
			b = 0;
		return new Color(r, g, b, c.getAlpha());
	}
}