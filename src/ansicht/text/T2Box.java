package ansicht.text;

import wahr.physisch.*;

import java.awt.*;
import java.io.*;

public class T2Box extends SLF
{
	private TBoxOrt connect;
	public String codebez;
	private String emotion;
	private int fade;
	private double h2;

	public T2Box(SchalterLayer main, boolean tangible, double w, double h, TBoxOrt connect,
			String dispName, String codebez, String emotion)
	{
		super(main, tangible, connect.x - (connect.links ? -main.abstand : w + main.abstand),
				connect.y - (connect.oben ? h : 0), w, h, dispName);
		this.connect = connect;
		this.codebez = codebez;
		this.emotion = emotion;
		h2 = h / 3 * 2;
	}

	public void recharge(String dispName, String emotion)
	{
		text = dispName;
		this.emotion = emotion;
	}

	public void draw(Graphics2D gd)
	{
		if(fade > 0)
		{
			gd.setColor(new Color(main.back.getRed(), main.back.getGreen(), main.back.getBlue(),
					main.back.getAlpha() * fade / 255));
			gd.fillRect(main.mw(x), main.mh(y), main.mw(w), main.mh(h));
			gd.setColor(new Color(main.front.getRed(), main.front.getGreen(), main.front.getBlue(),
					main.front.getAlpha() * fade / 255));
			gd.setFont(new Font(null, Font.PLAIN, main.mh((h - h2) / 2)));
			gd.drawString(text, main.mw(x) + main.mh((h - h2) / 4),
					main.mh(y) + main.mh(h2) + main.mh((h - h2) * 2 / 3));
			gd.drawImage(Lader.gibBild("NAME" + File.separator + codebez + "_" + emotion + ".png"),
					main.mw(x), main.mh(y), main.mw(w), main.mh(h2), null);
		}
	}

	public void onClick(boolean r, double cx, double cy)
	{
		main.removeText(connect);
	}

	public void tick()
	{
		fade = main.giveMaxFade(connect);
	}
}