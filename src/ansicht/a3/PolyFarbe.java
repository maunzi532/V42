package ansicht.a3;

import wahr.zugriff.*;

import java.awt.*;
import java.util.*;

public class PolyFarbe
{
	private static final int[] splN;
	static
	{
		Random r = new Random();
		splN = new int[Staticf.seedifier];
		for(int i = 0; i < Staticf.seedifier; i++)
			splN[i] = r.nextInt(Staticf.seedifier);
	}

	Color baseColor;

	public boolean showFade(Polygon3 target)
	{
		if(target.rSeed <= 0) //TODO viel zu viel code
			return true;
		if(target.ddiff >= 0)
			return target.ddiff / Staticf.diffusewidth - Staticf.safezone <=
					((Math.abs(target.rSeed) + splN[(target.nachSplitID % Staticf.seedifier)]) %
							Staticf.seedifier) / (double)Staticf.seedifier;
		return -target.ddiff / Staticf.diffusewidth - Staticf.safezone <=
				(Staticf.seedifier - 1 - ((Math.abs(target.rSeed) +
						splN[(target.nachSplitID % Staticf.seedifier)]) %
						Staticf.seedifier)) / (double)Staticf.seedifier;
	}

	public Paint errechneFarbe(Polygon3 target, long tn)
	{
		return baseColor;
	}
}