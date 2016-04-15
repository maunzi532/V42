package ansicht.a3;

import ansicht.*;
import nonBlock.aktion.*;
import nonBlock.aussehen.*;
import nonBlock.controllable.*;
import wahr.zugriff.*;

import java.util.*;

public class Vor
{
	public ArrayList<Anzeige3> anzeige;
	ArrayList<Double> abstands;
	ArrayList<Integer> splits;
	private WeltND dw;
	private LichtW lw;
	private BlockZuAnz za;
	public int visionRange4D = 1;
	public int baumodus;
	public boolean siehBlocks = true;
	public boolean siehNonBlocks = true;

	public Vor(String abstandSplitInput, AllWelt aw)
	{
		abstands = new ArrayList<>();
		splits = new ArrayList<>();
		String[] zeilen = abstandSplitInput.split("\n");
		for(int i = 0; i < zeilen.length; i++)
			if(!zeilen[i].isEmpty() && !zeilen[i].startsWith("/"))
			{
				String[] z1 = zeilen[i].split(" ");
				abstands.add(Double.parseDouble(z1[0]));
				splits.add(Integer.parseInt(z1[1]));
			}
		za = new BlockZuAnz(aw.wbl, aw.lw);
		dw = aw.dw;
		lw = aw.lw;
	}

	public void vorbereiten(Overlay theOverlay)
	{
		Controllable kam = theOverlay.kamZurZeit();
		K4 kp = kam.kamP();
		Drehung kd = kam.kamD();
		anzeige = new ArrayList<>();
		if(siehNonBlocks)
			for(NonBlock nb : dw.nonBlocks)
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
						//TODO Nicht sicher, checken
						if(kam == nb && eckenK[k].a * eckenK[k].a + eckenK[k].b * eckenK[k].b +
								eckenK[k].c * eckenK[k].c < Staticf.sichtMin * Staticf.sichtMin)
						{
							eckenK = null;
							break;
						}
					}
					if(eckenK != null)
					{
						/*K4[] spken = new K4[f2.spken1.size()];
						for(int k = 0; k < f2.spken1.size(); k++)
							spken[k] = nb.punkteK[f2.spken1.get(k)][f2.spken2.get(k)];*/
						anzeige.add(new PNonBlock3(nb.tn, lw, f2.seite, f2.polyFarbe, f2.seed, eckenR, eckenK));
					}
				}
				for(int i = 0; i < nb.externals.length; i++)
					nb.externals[i].gibPl(anzeige, nb.punkteK, nb.lw, theOverlay.godMode,
							nb == theOverlay.kamZurZeit());
			}
		if(siehBlocks)
			za.zuAnz(anzeige, kp, kd, new K4(Staticf.sicht, Staticf.sicht,
					Staticf.sicht, 0), visionRange4D, baumodus);
		for(int i = 0; i < anzeige.size(); i++)
			anzeige.get(i).splittern(anzeige, theOverlay.godMode, this);
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
			anzeige.get(i).eckenEntf(theOverlay.wI, theOverlay.hI, theOverlay.auf.scF.width);
		for(int i = 0; i < anzeige.size(); i++)
			anzeige.get(i).farbeFlaeche(theOverlay.pa.tnTarget, theOverlay.wI, theOverlay.hI,
					theOverlay.kamZurZeit().kamP(), /*Staticf.xraywidth*/ 0);
	}

	private void soutA()
	{
		int z = 0;
		for(int i = 0; i < anzeige.size(); i++)
			if(anzeige.get(i).anzeigen)
				z++;
		System.out.println(z);
	}
}