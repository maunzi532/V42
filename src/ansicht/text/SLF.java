package ansicht.text;

import java.awt.*;

public abstract class SLF
{
	private final SchalterLayer main;
	final boolean tangible;
	private final double x;
	private final double y;
	private final double w;
	private final double h;
	protected String text;

	public SLF(SchalterLayer main, boolean tangible, double x, double y, double w, double h)
	{
		this.main = main;
		this.tangible = tangible;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		text = "";
		tick();
	}

	public SLF(SchalterLayer main, boolean tangible, double x, double y, double w, double h, String text)
	{
		this.main = main;
		this.tangible = tangible;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.text = text;
	}

	public boolean click(int xc, int yc)
	{
		return xc >= main.mw(x) && yc >= main.mh(y) && xc < main.mw(x + w) && yc < main.mh(y + h);
	}

	public void draw(Graphics2D gd)
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

	public void onClick(boolean r){}

	public void tick(){}
}