package ansicht.text;

import java.awt.*;

abstract class SLF
{
	final boolean tangible;
	double x;
	double y;
	final double w;
	final double h;
	String text;

	public SLF(boolean tangible, double x, double y, double w, double h)
	{
		this.tangible = tangible;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		text = "";
		tick(null);
	}

	public SLF(boolean tangible, double x, double y, double w, double h, String text)
	{
		this.tangible = tangible;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.text = text;
	}

	public boolean click(double xc, double yc)
	{
		return xc >= x && yc >= y && xc < x + w && yc < y + h;
	}

	public void draw(Graphics2D gd, SchalterLayer main)
	{
		gd.setColor(main.back);
		gd.fillRect(main.mw(x), main.mh(y), main.mw(w), main.mh(h));
		if(text != null)
		{
			gd.setColor(main.front);
			gd.setFont(new Font(null, Font.PLAIN, main.mh(h / 2)));
			gd.drawString(text, main.mw(x) + main.mh(h / 4), main.mh(y) + main.mh(h * 2 / 3));
		}
	}

	public void onClick(SchalterLayer main, boolean r, double cx, double cy)
	{
		tick(main);
	}

	public void tick(SchalterLayer main){}
}