package ansicht.text;

import nonBlock.aktion.lesen.*;

import java.awt.*;

public class TBox extends SLF implements ZDelay
{
	private boolean clicked;
	public int ort;
	public int fade;
	private int timer;

	public TBox(boolean tangible, String text)
	{
		super(tangible, 0, 0, 0.1, 0.1, text);
		schalter = false;
		fade = 255;
		timer = tangible ? -1 : 100;
	}

	public void draw(Graphics2D gd, SchalterLayer main)
	{
		if(fade > 0)
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
	}

	public boolean click(double xc, double yc)
	{
		return true;
	}

	public void onClick(SchalterLayer main, boolean r, double cx, double cy)
	{
		clicked = true;
		main.removeText();
	}

	public void tick(SchalterLayer main)
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

	public boolean fertig(int timeLeft)
	{
		return timeLeft >= 0 && clicked;
	}
}