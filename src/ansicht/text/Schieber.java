package ansicht.text;

import java.awt.*;

public class Schieber extends SLF
{
	public double shift;
	public final double shiftm;
	public final double startw;

	public Schieber(double x, double y, double w, double h,
			double startw, double endw, double init)
	{
		super(true, x, y, w, h);
		this.startw = startw;
		shiftm = endw - startw;
		shift = (init - startw) / shiftm;
		tick(null);
	}

	public void draw(Graphics2D gd, SchalterLayer main)
	{
		gd.setColor(main.back);
		gd.fillRect(main.mw(x), main.mh(y), main.mw(w), main.mh(h));
		int[] xa = new int[]{main.mw(x + w * shift), main.mw(x + w * shift + w / 20),
				main.mw(x + w * shift), main.mw(x + w * shift - w / 20)};
		int[] ya = new int[]{main.mh(y), main.mh(y + h / 2), main.mh(y + h), main.mh(y + h / 2)};
		gd.setColor(main.schieber);
		gd.fillPolygon(xa, ya, 4);
		gd.setColor(main.schieberKante);
		gd.drawPolygon(xa, ya, 4);
		if(text != null)
		{
			gd.setColor(main.front);
			gd.setFont(new Font(null, Font.PLAIN, main.mh(h / 2)));
			gd.drawString(text, main.mw(x) + main.mh(h / 4), main.mh(y) + main.mh(h * 2 / 3));
		}
	}

	public void onClick(SchalterLayer main, boolean r, double cx, double cy)
	{
		shift = cx;
		super.onClick(main, r, cx, cy);
	}
}