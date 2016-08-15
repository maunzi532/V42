package a3;

import achsen.*;
import java.util.*;
import k4.*;

public class PNonBlock3 extends Polygon3
{
	public PNonBlock3(TnTarget tn, LichtW lw, Boolean seite, IFarbeff2 farbe, int rSeed, K4[] eckenR, K4[] eckenK)
	{
		super(tn, lw, seite);
		this.farbe = (PolyFarbe)farbe;
		this.eckenR = eckenR;
		this.eckenK = eckenK;
		this.rSeed = rSeed;
		berechneMids();
	}

	public PNonBlock3(TnTarget tn, LichtW lw, Boolean seite, IFarbeff2 farbe,
			int rSeed, K4[] eckenR, K4[] eckenK, int splSeed)
	{
		this(tn, lw, seite, farbe, rSeed, eckenR, eckenK);
		nachSplitID = splSeed;
	}

	private PNonBlock3(PNonBlock3 main, int splitID, K4[] eckenR, K4[] eckenK)
	{
		super(main.tn, main.lw, main.seite);
		farbe = main.farbe;
		rSeed = main.rSeed;
		nachSplitID = main.nachSplitID * 4 + splitID + 1;
		this.eckenR = eckenR;
		this.eckenK = eckenK;
		berechneMids();
	}

	private double sizeForKam()
	{
		double k = 0;
		for(int i = 0; i < eckenK.length; i++)
		{
			double qa = eckenK[i].a - kamMid.a;
			double qb = eckenK[i].b - kamMid.b;
			double k2t = Math.sqrt(qa * qa + qb * qb);
			if(k2t > k)
				k = k2t;
		}
		return k;
	}

	public void splittern(ArrayList<Anzeige3> dieListe)
	{
		if(!anzeigen)
			return;
		//noinspection PointlessBooleanExpression,ConstantConditions
		if(Vor.splThr > 0 && sizeForKam() > Vor.splThr)
		{
			K4[] neueEckenK = new K4[eckenK.length];
			for(int j = 0; j < neueEckenK.length; j++)
			{
				K4 doubled = K4.plus(eckenK[j], eckenK[(j + 1) % neueEckenK.length]);
				neueEckenK[j] = new K4(doubled.a / 2, doubled.b / 2, doubled.c / 2, doubled.d / 2);
			}
			K4[] neueEckenR = new K4[eckenR.length];
			for(int j = 0; j < neueEckenR.length; j++)
			{
				K4 doubled = K4.plus(eckenR[j], eckenR[(j + 1) % neueEckenR.length]);
				neueEckenR[j] = new K4(doubled.a / 2, doubled.b / 2, doubled.c / 2, doubled.d / 2);
			}
			for(int j = 0; j < neueEckenK.length; j++)
			{
				K4[] eR = new K4[]
						{
								eckenR[j],
								neueEckenR[j],
								rMid,
								neueEckenR[j > 0 ? j - 1 : neueEckenR.length - 1]
						};
				K4[] eK = new K4[]
						{
								eckenK[j],
								neueEckenK[j],
								kamMid,
								neueEckenK[j > 0 ? j - 1 : neueEckenK.length - 1]
						};
				new PNonBlock3(this, j, eR, eK).splittern(dieListe);
			}
		}
		else
			dieListe.add(this);
	}
}