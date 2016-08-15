package a3;

import indexLader.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import k4.*;

public class XFBT2 extends PolyFarbe
{
	private final ArrayList<Color[]> farben;

	private XFBT2(ArrayList<BufferedImage> bilder, int seite)
	{
		farben = new ArrayList<>();
		baseColor = Color.WHITE;
		for(int i = 0; i < bilder.size(); i++)
		{
			int n = bilder.get(i).getHeight();
			Color[] lies = new Color[n * n];
			for(int j = 0; j < n; j++)
				for(int k = 0; k < n; k++)
					lies[j * n + k] = new Color(bilder.get(i).getRGB(n * seite + k, j));
			farben.add(lies);
			if(n == 1)
				baseColor = lies[0];
		}
		mat = Material.B;
	}

	public Paint gibFarbe(Polygon3 target, TnTarget tn)
	{
		assert target instanceof PBlock3;
		PBlock3 t1 = (PBlock3) target;
		int nummer = ((PBlock3) target).vd.splits.indexOf(t1.splitDepth);
		if(nummer >= 0)
			return errechneFarbe(farben.get(nummer)[t1.nachSplitID], target, tn);
		return errechneFarbe(baseColor, target, tn);
	}

	public static XFBT2 gibVonIndex(String name, int seite, VorDaten vd)
	{
		if(Index.geladen.containsKey(name + seite))
			return (XFBT2) Index.geladen.get(name + seite);
		ArrayList<BufferedImage> imgs = new ArrayList<>();
		for(int i = 0; i < vd.splits.size(); i++)
			imgs.add((BufferedImage) Lader.gibBild(Index.teilNamen.get("Blocks") + File.separator +
					name.replace("/", File.separator) + " " + vd.splits.get(i) + ".png"));
		XFBT2 s = new XFBT2(imgs, seite);
		Index.geladen.put(name + seite, s);
		return s;
	}
}