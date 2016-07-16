package a3;

import java.awt.*;
import k4.*;
import welt.*;

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