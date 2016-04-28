package ansicht.a3;

import ansicht.*;
import block.*;
import wahr.zugriff.*;

import java.awt.*;

public class BlockDInfo3 extends Rechteck3
{
	private double ddiff1;
	private double ddiff2;

	public BlockDInfo3(long tn, LichtW lw, K4 mid, K4 rMid, boolean targetable,
			DerBlock block, double ddiff1, double ddiff2)
	{
		super(tn, lw, mid, rMid, targetable, null);
		this.ddiff1 = ddiff1;
		this.ddiff2 = ddiff2;
		foreground = Color.WHITE;
		background = Color.DARK_GRAY;
		text = block.toString();
	}

	public void panel(Graphics2D gd)
	{
		int[] xp = new int[3];
		int[] yp = new int[3];
		xp[0] = xe;
		yp[1] = ye;
		xp[2] = xe;
		if(ddiff1 < 0)
		{
			xp[1] = (int) (xe - scale * 3);
			yp[0] = (int) (ye + ddiff2 * scale * 3);
			yp[2] = (int) (ye + ddiff1 * scale * 3);
		}
		else if(ddiff2 > 0)
		{
			xp[1] = (int) (xe + scale * 3);
			yp[0] = (int) (ye + ddiff1 * scale * 3);
			yp[2] = (int) (ye + ddiff2 * scale * 3);
		}
		gd.setPaint(backgroundNew);
		gd.fillPolygon(xp, yp, 3);
		//gd.setFont(new Font("Consolas", Font.PLAIN, (int)(scale > 20 ? 20 : scale / 2 + 10)));
		/*if(text != null)
		{
			double w = text.length() * scale / 3;
			double h = scale / 2;
			double h2 = 0;
			if(align != 0)
				h2 = h * align;
			double w2 = 0;
			if(align != 0)
				w2 = w * align;
			gd.fillRect((int)(xe - w + w2), (int)(ye - h + h2), (int)(w * 2), (int)(h * 2));
			gd.setPaint(foreground);
			gd.drawString(text, (int)(xe - w + w2 + scale / 8), (int)(ye + h + h2 - scale / 8));
		}*/
	}

	public void panelDark(Graphics2D gd, TnZuordnung tnz)
	{
		if(targetable)
		{
			int[] xp = new int[3];
			int[] yp = new int[3];
			xp[0] = xe;
			yp[0] = ye;
			xp[2] = xe;
			if(ddiff1 < 0)
			{
				xp[1] = (int)(xe - scale * 3);
				yp[1] = (int)(ye + ddiff2 * scale * 3);
				yp[2] = (int)(ye + ddiff1 * scale * 3);
			}
			else if(ddiff2 > 0)
			{
				xp[1] = (int)(xe + scale * 3);
				yp[1] = (int)(ye + ddiff1 * scale * 3);
				yp[2] = (int)(ye + ddiff2 * scale * 3);
			}
			if(tnz != null)
			{
				tnz.actBounds(xp[1], yp[1]);
				tnz.actBounds(xp[2], yp[2]);
			}
			gd.fillPolygon(xp, yp, 3);
		}
	}
}