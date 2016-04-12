package ansicht.a3;

import wahr.zugriff.*;

import java.awt.*;

public class BlockDInfo3 extends Rechteck3
{
	private Boolean align;

	public BlockDInfo3(long tn, K4 mid, boolean targetable, String text, Boolean align)
	{
		super(tn, mid, targetable, text);
		this.align = align;
	}

	public void Panel(Graphics2D gd)
	{
		gd.setPaint(background);
		gd.setFont(new Font("Consolas", Font.PLAIN, (int)(scale > 20 ? 20 : scale)));
		int r = (int)(scale * 2);
		int[] xp;
		int[] yp;
		if(align == null)
		{
			xp = new int[]{xe - r, xe, xe + r, xe};
			yp = new int[]{ye, ye - r, ye, ye + r};
		}
		else if(align)
		{
			xp = new int[]{xe - r, xe, xe};
			yp = new int[]{ye, ye - r, ye + r};
		}
		else
		{
			xp = new int[]{xe, xe + r, xe};
			yp = new int[]{ye - r, ye, ye + r};
		}
		gd.fillPolygon(xp, yp, align != null ? 3 : 4);
		if(text != null)
		{
			double w = text.length() * scale / 3;
			double h = scale / 2;
			double h2 = 0;
			if(align != null)
				h2 = h * (align ? 1 : -1);
			double w2 = 0;
			if(align != null)
				w2 = w * (align ? 1 : -1);
			gd.fillRect((int)(xe - w + w2), (int)(ye - h + h2), (int)(w * 2), (int)(h * 2));
			gd.setPaint(foreground);
			gd.drawString(text, (int)(xe - w + w2 + scale / 8), (int)(ye + h + h2 - scale / 8));
		}
	}

	public void PanelDark(Graphics2D gd)
	{
		int r = (int)(scale * 2);
		int[] xp;
		int[] yp;
		if(align == null)
		{
			xp = new int[]{xe - r, xe, xe + r, xe};
			yp = new int[]{ye, ye - r, ye, ye + r};
		}
		else if(align)
		{
			xp = new int[]{xe - r, xe, xe};
			yp = new int[]{ye, ye - r, ye + r};
		}
		else
		{
			xp = new int[]{xe, xe + r, xe};
			yp = new int[]{ye - r, ye, ye + r};
		}
		gd.fillPolygon(xp, yp, align != null ? 3 : 4);
	}
}