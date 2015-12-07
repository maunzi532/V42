package modell.xF;

import ansicht.*;

import java.awt.*;
import java.util.*;

public class XFBT extends XFN
{
	private final HashMap<Integer, Color[]> farben;

	public XFBT(String farbText)
	{
		farben = new HashMap<>();
		int cfarb = 0;
		Color[] c1 = null;
		int c2 = 0;
		String[] zeilen = farbText.split("\n");
		for(int i = 0; i < zeilen.length; i++)
			if(!zeilen[i].isEmpty() && !zeilen[i].startsWith("/"))
			{
				if(zeilen[i].startsWith("F"))
				{
					String[] z1 = zeilen[i].split(" ");
					farb = XFarbe.farbCode(z1[1]);
				}
				else if(zeilen[i].startsWith("+"))
				{
					if(cfarb != 0)
					{
						farben.put(cfarb, c1);
						c2 = 0;
					}
					String[] z1 = zeilen[i].split(" ");
					cfarb = Integer.parseInt(z1[1]);
					c1 = new Color[cfarb * cfarb];
				}
				else
				{
					String[] z1 = zeilen[i].split(" ");
					for(int j = 0; j < cfarb; j++)
						c1[c2 * cfarb + j] = XFarbe.farbCode(z1[j]);
					c2++;
				}
			}
		if(cfarb != 0)
			farben.put(cfarb, c1);
	}

	public void setFarb(Graphics2D gd, double ddiff, N2 n)
	{
		F2 f = (F2) n;
		Color fc;
		if(f.spld <= 0 || !farben.containsKey(f.spld))
			fc = farb;
		else
			fc = farben.get(f.spld)[f.splseed];
		setFarb(gd, ddiff, n, fc);
	}
}