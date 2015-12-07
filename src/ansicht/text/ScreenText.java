package ansicht.text;

import falsch.*;

import java.awt.*;

public class ScreenText
{
	private final String text;
	private final Image bild;
	private final String name;
	private final int dauer;
	private int fade;
	private int aktuell;
	private final boolean seite;
	public boolean clicked;

	public String[] zeilen;

	public ScreenText(String text, Image bild, String name, int dauer, boolean seite)
	{
		this.text = text;
		this.bild = bild;
		this.name = name;
		this.dauer = dauer;
		this.seite = seite;
	}

	public boolean act(boolean click)
	{
		if(click)
			clicked = true;
		if((dauer > 0 && aktuell >= dauer) || (dauer <= 0 && clicked))
		{
			fade++;
			if(fade >= Staticf.textFade)
				return true;
		}
		else if(dauer > 0)
				aktuell++;
		return false;
	}

	public int reservedHeight()
	{
		return (int)(100 * fadeT(fade));
	}

	public void z(Graphics2D gd, int x, int y)
	{
		gd.setColor(new Color(0, 0, 0, (int)(255 * fadeT(fade))));
		gd.fillRect(x, y, 300, 50);
		gd.setColor(new Color(255, 255, 255, (int)(255 * fadeT(fade))));
		gd.drawString(text, x, y + 30);
	}

	private static double fadeT(int fade)
	{
		return 1 - (double) fade / Staticf.textFade;
	}
}