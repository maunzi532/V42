package ansicht.n2.xF;

import ansicht.n2.*;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class XFBT extends XFN
{
	private final HashMap<Integer, XFarbe[]> farben;

	public XFBT(String farbText)
	{
		farben = new HashMap<>();
		int cfarb = 0;
		XFarbe[] c1 = null;
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
					c1 = new XFarbe[cfarb * cfarb];
				}
				else
				{
					String[] z1 = zeilen[i].split(" ");
					for(int j = 0; j < cfarb; j++)
						//c1[c2 * cfarb + j] = XFarbe.farbCode(z1[j]);
						c1[c2 * cfarb + j] = t2xf(z1[j]);
					c2++;
				}
			}
		if(cfarb != 0)
			farben.put(cfarb, c1);
		mat = Material.B;
	}

	public XFBT(ArrayList<BufferedImage> bilder, int seite)
	{
		farben = new HashMap<>();
		farb = Color.WHITE;
		for(int i = 0; i < bilder.size(); i++)
		{
			int n = bilder.get(i).getHeight();
			XFarbe[] lies = new XFarbe[n * n];
			for(int j = 0; j < n; j++)
				for(int k = 0; k < n; k++)
					lies[j * n + k] = new XFN(new Color(bilder.get(i).getRGB(n * seite + k, j)), mat = Material.B);
			farben.put(n, lies);
			if(n == 1)
				farb = ((XFN)lies[0]).farb;
		}
		mat = Material.B;
	}

	public Paint gibFarb(N2 n)
	{
		assert n instanceof F2;
		F2 f = (F2) n;
		if(f.spld <= 0 || !farben.containsKey(f.spld))
			return super.gibFarb(f);
		else
			return farben.get(f.spld)[f.splseed].gibFarb(f);
	}
}