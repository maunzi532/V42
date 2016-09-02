package a3;

import achsen.*;
import java.util.*;
import k4.*;

public class Vor1
{
	//NonBlock Polygon Teilung bis kleiner als das
	public static final double splThr = 1.2;
	//Blickbreite
	public static double scaleX = 0.8;
	//Weite des X-Ray-Modus
	public static double xraywidth = 50;
	//Eigene H ausblenden
	public static final double ausblendRange = 2;

	public static final double minSicht = 50;
	public static final double maxSicht = PolyFarbe.redEnd;

	public double sicht = minSicht;
	private int rchecks;
	private int rfail;
	public ArrayList<Anzeige3> anzeigeZ;
	public ArrayList<Anzeige3> anzeige;
	private ArrayList<AchsenK1> ak1s;
	private LichtW lw;
	private IVUpgrade za;
	public int visionRange4D = 0;
	public int baumodus;
	public boolean upgradeActive;
	public boolean siehAk1s = true;

	public Vor1(ArrayList<AchsenK1> ak1s, LichtW lw, IVUpgrade upgrade)
	{
		za = upgrade;
		if(za != null)
			upgradeActive = true;
		this.ak1s = ak1s;
		this.lw = lw;
	}

	public void vorbereiten(IKamera kam, int wI, int hI, int cI, TnTarget tnTarget, boolean xr)
	{
		K4 kp = kam.kamP();
		Drehung kd = kam.kamD();
		K4 relativ = new K4(kp);
		relativ.transformKLB(kd);
		anzeigeZ = new ArrayList<>();
		if(siehAk1s)
			for(AchsenK1 ak : ak1s)
			{
				for(int j = 0; j < ak.plys.size(); j++)
					for(int l = 0; l < ak.plys.get(j).plys.size(); l++)
					{
						Ply1 f2 = ak.plys.get(j).plys.get(l);
						K4[] eckenR = new K4[f2.punkte.size()];
						for(int k = 0; k < f2.punkte.size(); k++)
							eckenR[k] = ak.punkt(f2.punkte.get(k).achse, f2.punkte.get(k).punkt);
						K4[] eckenK = new K4[f2.punkte.size()];
						for(int k = 0; k < f2.punkte.size(); k++)
						{
							eckenK[k] = ak.punktK(f2.punkte.get(k).achse, f2.punkte.get(k).punkt, relativ, kd);
							if(kam == ak && eckenK[k].a * eckenK[k].a + eckenK[k].b * eckenK[k].b +
									eckenK[k].c * eckenK[k].c < ausblendRange * ausblendRange)
							{
								eckenK = null;
								break;
							}
						}
						if(eckenK != null)
							anzeigeZ.add(new PNonBlock3(ak.tNum, lw, f2.seite, f2.farbe, /*seed*/10, eckenR, eckenK));
					}
			}
		if(upgradeActive)
			za.zuAnz(anzeigeZ, kp, kd, new K4(sicht, sicht, sicht, 0), visionRange4D, baumodus);
		//System.out.println(anzeigeZ.stream().filter(e -> e.anzeigen).count());
		anzeige = new ArrayList<>();
		for(int i = 0; i < anzeigeZ.size(); i++)
			anzeigeZ.get(i).splittern(anzeige);
		//System.out.println(anzeige.stream().filter(e -> e.anzeigen).count());
		Collections.sort(anzeige, (t1, t2) ->
		{
			if(!t1.anzeigen && !t2.anzeigen)
				return 0;
			if(!t1.anzeigen)
				return -1;
			if(!t2.anzeigen)
				return 1;
			return -Double.compare(t1.kamMid.c, t2.kamMid.c);
		});
		for(int i = 0; i < anzeige.size(); i++)
			anzeige.get(i).eckenEntf(wI, hI, cI);
		//System.out.println(anzeige.stream().filter(e -> e.anzeigen).count());
		for(int i = 0; i < anzeige.size(); i++)
			anzeige.get(i).farbeFlaeche(tnTarget, wI, hI, kam.kamP(),  xr ? xraywidth : 0);
		//System.out.println(anzeige.stream().filter(e -> e.anzeigen).count());
	}

	public void changeRange(boolean lower)
	{
		rchecks++;
		if(lower)
			rfail++;
		if(rchecks >= 20)
		{
			switch(rfail * 5 / rchecks)
			{
				case 0:
					sicht += 30;
					if(sicht > maxSicht)
						sicht = maxSicht;
					break;
				case 4:
					sicht -= 20;
				case 3:
					sicht -= 10;
					if(sicht < minSicht)
						sicht = minSicht;
					break;
				case 5:
					sicht = minSicht;
					break;
			}
			rchecks = 0;
			rfail = 0;
		}
	}
}