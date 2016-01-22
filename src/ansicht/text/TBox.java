package ansicht.text;

import java.awt.*;

public class TBox extends SLF
{
	public boolean clicked;
	public int ort;
	public int fade;
	public int timer;

	public TBox(SchalterLayer main, boolean tangible, double w, double h, String text)
	{
		super(main, tangible, 0, 0, w, h, text);
		fade = 255;
		timer = tangible ? -1 : 100;
	}

	public void draw(Graphics2D gd)
	{
		gd.setColor(new Color(main.back.getRed(), main.back.getGreen(), main.back.getBlue(),
				main.back.getAlpha() * fade / 255));
		gd.fillRect(main.mw(x), main.mh(y), main.mw(w), main.mh(h));
		if(text != null)
		{
			gd.setColor(new Color(main.front.getRed(), main.front.getGreen(), main.front.getBlue(),
					main.front.getAlpha() * fade / 255));
			gd.setFont(new Font(null, Font.PLAIN, main.mh(h / 2)));
			gd.drawString(text, main.mw(x) + main.mh(h / 4), main.mh(y) + main.mh(h * 2 / 3));
		}
	}

	public boolean click(double xc, double yc)
	{
		return true;
	}

	public void onClick(boolean r, double cx, double cy)
	{
		clicked = true;
		main.removeText();
	}

	public void tick()
	{
		double ny = main.giveNewH(this);
		if(ny < y - main.movePerTick)
			y -= main.movePerTick;
		else if(ny > y + main.movePerTick)
			y += main.movePerTick;
		else
			y = ny;
		if(fade < 255)
			fade -= main.fadePerTick;
		else if(timer > 0)
			timer--;
		if(timer == 0 && fade >= 255)
			fade--;
		if(fade <= 0)
			main.removeTBox(this);
	}
}