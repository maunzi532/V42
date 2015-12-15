package ansicht.text;

import java.awt.*;

public class TBox extends SLF
{
	public TBox(SchalterLayer main, boolean tangible, double x, double y, double w, double h, String text)
	{
		super(main, tangible, x, y, w, h, text);
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

	public void onClick(boolean r)
	{
		main.layer.remove(this);
	}

	public void tick(){}
}