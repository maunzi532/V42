package ansicht.a3;

import ansicht.*;
import wahr.zugriff.*;

public class PNonBlock3 extends Polygon3
{
	boolean canSplit;
	int splitDepth;
	K4 rMid;
	//TODO spken

	public PNonBlock3(long tn, Boolean seite, LichtW lw, PolyFarbe farbe, K4[] ecken)
	{
		super(tn, seite, lw);
		this.farbe = farbe;
		eckenR = ecken;
	}

	public PNonBlock3(PNonBlock3 main, K4[] eckenR, K4[] eckenK)
	{
		super(main.tn, main.seite, main.lw);
		farbe = main.farbe;
		this.eckenR = eckenR;
		this.eckenK = eckenK;
		//TODO erneut mid berechnen
	}

	public boolean errechneKam(K4 kamP, Drehung kamD)
	{
		return false; //TODO
	}

	public void splittern(boolean gmVision)
	{
		if(Staticf.splThr > 0 && canSplit/* && maxAbs() > Staticf.splThr*/)
		{
			anzeigen = false;
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
				PNonBlock3 tspt = new PNonBlock3(this, eR, eK);
				//farbe, seite, lw, spld + 1, seed, tn);
				//tspt.mid();
				//tspt.splseed = splseed * 4 + j + 1;
				//n2s.add(tspt);
				//TODO splitDepth
			}
		}
	}
}