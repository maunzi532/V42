package a3;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class Panelizer
{
	//Target-Markierungsbreite
	public static final int targetW = 5;
	private static final int bgr = 16777216;

	public BufferedImage light;
	public Graphics2D gd;
	private Graphics2D darkCopy;
	private int darkUsed = 0;
	private TnZuordnung[] darkZ = new TnZuordnung[10000];
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

	public void panelize(ArrayList<Anzeige3> anzeige, int mx, int my)
	{
		gd.setColor(new Color(20, 0, 0));
		gd.fillRect(0, 0, light.getWidth(), light.getHeight());
		if(taType > 0)
		{
			darkCopy.setColor(Color.BLACK);
			darkCopy.fillRect(0, 0, dark.getWidth(), dark.getHeight());
			darkUsed = 0;
		}
		if(taType > 0)
		{
			for(int i = 0; i < anzeige.size(); i++)
				if(anzeige.get(i).anzeigen)
				{
					long tn = anzeige.get(i).tn;
					boolean neu = true;
					int farbe = 0;
					for(int j = 1; j <= darkUsed; j++)
						if(darkZ[j].tn == tn)
						{
							neu = false;
							farbe = j;
							break;
						}
					if(neu)
					{
						darkUsed++;
						farbe = darkUsed;
						if(taType > 1)
							darkZ[darkUsed] = new TnZuordnung(tn, dark.getWidth(), dark.getHeight());
						else
							darkZ[darkUsed] = new TnZuordnung(tn);
					}
					darkCopy.setColor(new Color(farbe));
					anzeige.get(i).panelDark(darkCopy, taType > 1 ? darkZ[farbe] : null);
				}
		}
		for(int i = 0; i < anzeige.size(); i++)
			if(anzeige.get(i).anzeigen)
				anzeige.get(i).panel(gd);
		DPA2(mx, my);
	}

	private void DPA2(int mx, int my)
	{
		if(!taGet && taType > 0 && mx >= 0 && my >= 0 && mx < dark.getWidth() && my < dark.getHeight())
		{
			int cl = dark.getRGB(mx, my);
			if(cl + bgr > 0)
			{
				tnTarget = darkZ[cl + bgr].tn;
				DPA3(cl);
			}
			else
				tnTarget = -1;
		}
	}

	private void DPA3(int cl)
	{
		if(taType > 1 && tnTarget != -1)
		{
			int dw = dark.getWidth();
			int dh = dark.getHeight();
			int[][] cls = new int[dw + 2][dh + 2];
			TnZuordnung tnz = darkZ[cl + bgr];
			for(int imx = tnz.minX; imx <= tnz.maxX; imx++)
				for(int imy = tnz.minY; imy <= tnz.maxY; imy++)
					if(dark.getRGB(imx, imy) == cl)
						cls[imx + 1][imy + 1] = targetW + 1;
			for(int i = targetW; i > 0; i--)
				for(int imx = tnz.minX > targetW ? tnz.minX - targetW : 0;
					imx <= tnz.maxX + targetW && imx < dw; imx++)
					for(int imy = tnz.minY > targetW ? tnz.minY - targetW : 0;
						imy < tnz.maxY + targetW && imy < dh; imy++)
					{
						if(cls[imx + 1][imy + 1] < i &&
								(cls[imx][imy + 1] > i ||
										cls[imx + 1][imy] > i ||
										cls[imx + 2][imy + 1] > i ||
										cls[imx + 1][imy + 2] > i))
							cls[imx + 1][imy + 1] = i;
					}
			if(taType == 2)
			{
				for(int imx = 0; imx < dw; imx++)
					for(int imy = 0; imy < dh; imy++)
						if(cls[imx + 1][imy + 1] > 0 && cls[imx + 1][imy + 1] <= targetW)
						{
							gd.setColor(new Color(0, 0, 127 - 255 * cls[imx + 1][imy + 1] / targetW / 2));
							gd.fillRect(imx, imy, 1, 1);
						}
			}
		}
	}
}