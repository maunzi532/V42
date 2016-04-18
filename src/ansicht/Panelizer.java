package ansicht;

import ansicht.a3.*;
import wahr.zugriff.*;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class Panelizer
{
	public BufferedImage light;
	public Graphics2D gd;
	private Graphics2D darkCopy;
	private int darkUsed;
	private HashMap<Long, Integer> darkC;
	private HashMap<Integer, Long> darkC2;
	private BufferedImage dark;
	public long tnTarget;
	public int taType = 0;
	public boolean taGet;
	//X_Ray-Modus an/aus
	public boolean xrmode = false;

	public void resize(int w, int h)
	{
		light = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB_PRE);
		gd = light.createGraphics();
		dark = new BufferedImage(w, h, BufferedImage.TYPE_INT_BGR);
		darkCopy = dark.createGraphics();
	}

	private void DPA2(Graphics2D gd, int mx, int my)
	{
		if(!taGet && taType > 0 && mx >= 0 && my >= 0 && mx < dark.getWidth() && my < dark.getHeight())
		{
			int cl = dark.getRGB(mx, my);
			if(cl + 16777216 > 0)
			{
				tnTarget = darkC2.get(cl + 16777216);
				DPA3(cl);
			}
			else
				tnTarget = -1;
		}
		if(taType > 0)
		{
			gd.setColor(new Color(0, 180, 0));
			gd.setFont(new Font(null, Font.PLAIN, 20));
			gd.drawString(String.valueOf(tnTarget), 50, 150);
		}
	}

	private void DPA3(int cl)
	{
		if(taType > 1 && tnTarget != -1)
		{
			int[][] cls = new int[dark.getWidth()][dark.getHeight()];
			for(int ix = 0; ix < cls.length; ix++)
				for(int iy = 0; iy < cls[ix].length; iy++)
					if(dark.getRGB(ix, iy) == cl)
						cls[ix][iy] = Staticf.targetW + 1;
			for(int i = Staticf.targetW; i > 0; i--)
				for(int ix = 0; ix < cls.length; ix++)
					for(int iy = 0; iy < cls[ix].length; iy++)
						if(cls[ix][iy] < i && ((ix > 0 && cls[ix - 1][iy] > i) ||
								(iy > 0 && cls[ix][iy - 1] > i) ||
								(ix < cls.length - 1 && cls[ix + 1][iy] > i) ||
								(iy < cls[ix].length - 1 && cls[ix][iy + 1] > i)))
							cls[ix][iy] = i;

			if(taType == 2)
			{
				//A
				for(int ix = 0; ix < cls.length; ix++)
					for(int iy = 0; iy < cls[ix].length; iy++)
						if(cls[ix][iy] > 0 && cls[ix][iy] <= Staticf.targetW)
						{
							gd.setColor(new Color(0, 0, 127 - 255 * cls[ix][iy] / Staticf.targetW / 2));
							gd.fillRect(ix, iy, 1, 1);
						}
			}
			Staticf.sca("TN Linie (45) ");
		}
	}

	public void panelize(ArrayList<Anzeige3> anzeige, int mx, int my)
	{
		gd.setColor(new Color(20, 0, 0));
		gd.fillRect(0, 0, light.getWidth(), light.getHeight());
		if(taType > 0)
		{
			darkCopy.setColor(Color.BLACK);
			darkCopy.fillRect(0, 0, dark.getWidth(), dark.getHeight());
			darkC = new HashMap<>();
			darkC2 = new HashMap<>();
			darkUsed = 0;
		}
		for(int i = 0; i < anzeige.size(); i++)
			if(anzeige.get(i).anzeigen)
			{
				if(taType > 0)
				{
					Long ta = anzeige.get(i).tn;
					if(darkC != null && !darkC.containsKey(ta))
					{
						darkUsed++;
						darkC.put(ta, darkUsed);
						darkC2.put(darkUsed, ta);
					}
					if(darkC != null && darkC.containsKey(ta) && darkC.get(ta) != null)
					{
						darkCopy.setColor(new Color(darkC.get(ta)));
						anzeige.get(i).panelDark(darkCopy);
					}
				}
				anzeige.get(i).panel(gd);
			}
		DPA2(gd, mx, my);
		//gd.drawImage(dark, 700, 0, 300, 200, null);
	}
}