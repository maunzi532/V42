package a3;

import achsen.*;
import java.util.*;
import k4.*;

public class Vor
{
	//Sichtweite NonBlocks
	public static final double sicht = 150;
	//NonBlock Polygon Teilung bis kleiner als das
	public static final double splThr = 1.2;
	//Blickbreite
	public static double scaleX = 0.8;
	//Weite des X-Ray-Modus
	public static double xraywidth = 50;
	//Eigene H ausblenden
	public static final double sichtMin = 2;

	public ArrayList<Anzeige3> anzeigeZ;
	public ArrayList<Anzeige3> anzeige;
	private ArrayList<NonBlock> nonBlocks;
	private LichtW lw;
	private IVUpgrade za;
	public int visionRange4D = 1;
	public int baumodus;
	public boolean siehBlocks = true;
	public boolean siehNonBlocks = true;
	private VorDaten vorDaten;

	public Vor(String abstandSplitInput, ArrayList<NonBlock> nonBlocks, LichtW lw, IVUpgrade upgrade)
	{
		vorDaten = new VorDaten(abstandSplitInput);
		za = upgrade;
		this.nonBlocks = nonBlocks;
		this.lw = lw;
	}

	public void vorbereiten(IKamera kam, int wI, int hI, int cI, Long tnTarget, boolean xr)
	{
		K4 kp = kam.kamP();
		Drehung kd = kam.kamD();
		anzeigeZ = new ArrayList<>();
		if(siehNonBlocks)
			for(NonBlock nb : nonBlocks)
			{
				if(nb.punkte == null)
					continue;
				nb.punkteTransformKam(kp, kd);
				for(int j = 0; j < nb.aussehen.f2.size(); j++)
				{
					LadeF2 f2 = nb.aussehen.f2.get(j);
					K4[] eckenR = new K4[f2.ecken1.size()];
					for(int k = 0; k < f2.ecken1.size(); k++)
						eckenR[k] = nb.punkte[f2.ecken1.get(k)][f2.ecken2.get(k)];
					K4[] eckenK = new K4[f2.ecken1.size()];
					for(int k = 0; k < f2.ecken1.size(); k++)
					{
						eckenK[k] = nb.punkteK[f2.ecken1.get(k)][f2.ecken2.get(k)];
						if(kam == nb && eckenK[k].a * eckenK[k].a + eckenK[k].b * eckenK[k].b +
								eckenK[k].c * eckenK[k].c < sichtMin * sichtMin)
						{
							eckenK = null;
							break;
						}
					}
					if(eckenK != null)
						anzeigeZ.add(new PNonBlock3(nb.tn, lw, f2.seite, f2.fff2, f2.seed, eckenR, eckenK));
				}
				nb.additional(anzeigeZ, lw, nb == kam);
			}
		if(siehBlocks)
			za.zuAnz(anzeigeZ, kp, kd, new K4(sicht, sicht, sicht, 0), visionRange4D, baumodus);
		anzeige = new ArrayList<>();
		for(int i = 0; i < anzeigeZ.size(); i++)
			anzeigeZ.get(i).splittern(anzeige, vorDaten);
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
		for(int i = 0; i < anzeige.size(); i++)
			anzeige.get(i).farbeFlaeche(tnTarget, wI, hI, kam.kamP(),  xr ? xraywidth : 0);
	}
}