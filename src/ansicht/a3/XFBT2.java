package ansicht.a3;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class XFBT2 extends PolyFarbe
{
	private final HashMap<Integer, Color[]> farben;

	public XFBT2(ArrayList<BufferedImage> bilder, int seite)
	{
		farben = new HashMap<>();
		baseColor = Color.WHITE;
		for(int i = 0; i < bilder.size(); i++)
		{
			int n = bilder.get(i).getHeight();
			Color[] lies = new Color[n * n];
			for(int j = 0; j < n; j++)
				for(int k = 0; k < n; k++)
					lies[j * n + k] = new Color(bilder.get(i).getRGB(n * seite + k, j));
			farben.put(n, lies);
			if(n == 1)
				baseColor = lies[0];
		}
		mat = Material.B;
	}

	public Paint gibFarbe(Polygon3 target, Long tn)
	{
		assert target instanceof PBlock3;
		PBlock3 t1 = (PBlock3) target;
		if(farben.containsKey(t1.splitDepth))
			return errechneFarbe(farben.get(t1.splitDepth)[t1.nachSplitID], target, tn);
		return errechneFarbe(baseColor, target, tn);
	}
}