package ansicht.n2;

import ansicht.*;
import ansicht.n2.xF.*;
import wahr.zugriff.*;

import java.awt.*;

public class D2 extends N2
{
	private final String text;
	private final boolean quadrat;
	private int xe;
	private int ye;
	private int scale;

	public D2(boolean quadrat, XFarbe farbe, String text, K4 mid, long tn)
	{
		block = true;
		this.quadrat = quadrat;
		this.farbe = farbe;
		this.text = text;
		this.mid = mid;
		this.tn = tn;
	}

	public void panelDaten()
	{
		double ca = mid.c;
		if(ca < Staticf.nnull)
			ca = Staticf.nnull;
		xe = ethaX(mid.a, ca);
		ye = ethaY(mid.b, ca);
		ddiff = mid.d;
		scale = (int)(1000 / ca) + 1;
	}

	public void panelDark(Graphics2D darkCopy)
	{
		if(UIVerbunden.d2tangibility)
		{
			int r = scale * 2;
			int[] xp = new int[]{xe - r, xe, xe + r, xe};
			int[] yp = new int[]{ye, ye - r, ye, ye + r};
			darkCopy.fillPolygon(xp, yp, 4);
		}
	}

	public void panel(Panelizer pa)
	{
		pa.gd.setColor(dFarb);
		pa.gd.setFont(new Font("Consolas", Font.PLAIN, scale > 20 ? 20 : scale));
		if(UIVerbunden.d2tangibility)
		{
			int r = scale * 2;
			int[] xp = new int[]{xe - r, xe, xe + r, xe};
			int[] yp = new int[]{ye, ye - r, ye, ye + r};
			pa.gd.fillPolygon(xp, yp, 4);
		}
		if(text != null)
		{
			int w = text.length() * scale / 3;
			int h = scale / 2;
			pa.gd.fillRect(xe - w, ye - h, w * 2, h * 2);
			pa.gd.setColor(Color.WHITE);
			pa.gd.drawString(text, xe - w + scale / 8, ye + h - scale / 8);
		}
	}
}