package ansicht.a3;

import ansicht.*;
import wahr.zugriff.*;

public class PBlock3 extends Polygon3
{
	public K4[] unSpldEckenK;
	public K4[] unSpldEckenR;
	private double rEnd;
	private double gEnd;

	public PBlock3(long tn, Boolean seite, LichtW lw)
	{
		super(tn, seite, lw); //TODO alles
	}

	public boolean errechneKam(K4 kamP, Drehung kamD)
	{
		return false; //TODO
	}

	public void splittern(boolean gmVision)
	{
		//midsp();
		int splB = 5;//sqToSplit(midsp.a * midsp.a + midsp.b * midsp.b + midsp.c * midsp.c);
		anzeigen = false;
		K4[][] neueEckenK = new K4[splB + 1][splB + 1];
		for(int j = 0; j <= splB; j++)
			for(int k = 0; k <= splB; k++)
				neueEckenK[j][k] = splInn(unSpldEckenK, j, k, splB);
		K4[][] neueEckenR = new K4[splB + 1][splB + 1];
		for(int j = 0; j <= splB; j++)
			for(int k = 0; k <= splB; k++)
				neueEckenR[j][k] = splInn(unSpldEckenR, j, k, splB);
		for(int j = 0; j < splB; j++)
			for(int k = 0; k < splB; k++)
			{
				/*PBlock3 tspt = new PBlock3(tsp, splB);
				tspt.ecken(j, k, splB, splB);
				tspt.mid();
				tspt.splseed = j * splB + k;
				PBlock3.atls(n2s, tspt, gmVision);*///TODO
			}
	}

	public K4 splInn(K4[] ecken, int ax, int ay, int size)
	{
		return new K4((ecken[0].a * ax * ay + ecken[1].a * (size - ax) * ay +
				ecken[2].a * (size - ax) * (size - ay) + ecken[3].a * ax * (size - ay)) / size / size,
				(ecken[0].b * ax * ay + ecken[1].b * (size - ax) * ay +
						ecken[2].b * (size - ax) * (size - ay) + ecken[3].b * ax * (size - ay)) / size / size,
				(ecken[0].c * ax * ay + ecken[1].c * (size - ax) * ay +
						ecken[2].c * (size - ax) * (size - ay) + ecken[3].c * ax * (size - ay)) / size / size,
				(ecken[0].d * ax * ay + ecken[1].d * (size - ax) * ay +
						ecken[2].d * (size - ax) * (size - ay) + ecken[3].d * ax * (size - ay)) / size / size);
	}
}