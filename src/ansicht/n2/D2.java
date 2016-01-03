package ansicht.n2;

import ansicht.*;
import ansicht.n2.xF.*;
import wahr.zugriff.*;

import java.awt.*;
import java.util.*;

public class D2 extends N2
{
	private final String text;
	private final boolean quadrat;
	private final Boolean dSide;
	private int xe;
	private int ye;
	private int scale;

	public static void atl(ArrayList al, D2 d2, Drehung kDreh, K4 relativ)
	{
		d2.mid = TK4F.transformSet2(d2.mid, kDreh, relativ);
		al.add(d2);
	}

	public D2(boolean quadrat, Boolean dSide, XFarbe farbe, String text, K4 mid, long tn)
	{
		block = true;
		this.quadrat = quadrat;
		this.dSide = dSide;
		this.farbe = farbe;
		this.text = text;
		this.mid = mid;
		this.tn = tn;
	}

	public boolean draw()
	{
		return mid.c > Staticf.d2min;
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
		if(quadrat)
		{
			int r = scale * 2;
			int[] xp;
			int[] yp;
			if(dSide == null)
			{
				xp = new int[]{xe - r, xe, xe + r, xe};
				yp = new int[]{ye, ye - r, ye, ye + r};
			}
			else if(dSide)
			{
				xp = new int[]{xe - r, xe, xe};
				yp = new int[]{ye, ye - r, ye + r};
			}
			else
			{
				xp = new int[]{xe, xe + r, xe};
				yp = new int[]{ye - r, ye, ye + r};
			}
			darkCopy.fillPolygon(xp, yp, dSide != null ? 3 : 4);
		}
	}

	public void panel(Panelizer pa)
	{
		pa.gd.setPaint(dFarb);
		pa.gd.setFont(new Font("Consolas", Font.PLAIN, scale > 20 ? 20 : scale));
		if(quadrat)
		{
			int r = scale * 2;
			int[] xp;
			int[] yp;
			if(dSide == null)
			{
				xp = new int[]{xe - r, xe, xe + r, xe};
				yp = new int[]{ye, ye - r, ye, ye + r};
			}
			else if(dSide)
			{
				xp = new int[]{xe - r, xe, xe};
				yp = new int[]{ye, ye - r, ye + r};
			}
			else
			{
				xp = new int[]{xe, xe + r, xe};
				yp = new int[]{ye - r, ye, ye + r};
			}
			pa.gd.fillPolygon(xp, yp, dSide != null ? 3 : 4);
		}
		if(text != null)
		{
			int w = text.length() * scale / 3;
			int h = scale / 2;
			int h2 = 0;
			if(dSide != null)
				h2 = h * (dSide ? 1 : -1);
			int w2 = 0;
			if(dSide != null)
				w2 = w * (dSide ? 1 : -1);
			pa.gd.fillRect(xe - w + w2, ye - h + h2, w * 2, h * 2);
			pa.gd.setPaint(Color.WHITE);
			pa.gd.drawString(text, xe - w + w2 + scale / 8, ye + h + h2 - scale / 8);
		}
	}
}