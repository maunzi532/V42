package nonBlock.aussehen.ext;

import ansicht.*;
import ansicht.a3.*;
import nonBlock.aussehen.*;
import wahr.zugriff.*;

import java.util.*;

public class Enhance extends External
{
	private final LadeModell enh;

	public Enhance(LadeModell enh)
	{
		this.enh = enh;
	}

	public void entLink(Drehung mDreh, K4 mPos)
	{
		inmid = TK4F.zuPunkt(main2.achsen[axn], 0, 0, 1, 0, mDreh, mPos);
		dmid = new Drehung(main2.achsen[axn].dreh);
		int nicht = 0;
		int entlinkt = 0;
		while(entlinkt + nicht < laenge)
		{
			nicht = 0;
			for(int i = anfang; i < anfang + laenge; i++)
			{
				if(main2.linkAchsen[i] != null && main2.achsen[i] != null)
					continue;
				if(main2.linkAchsen[i] == null)
				{
					nicht++;
					continue;
				}
				if(main2.linkAchsen[i].gelinkt == null)
				{
					main2.achsen[i] = main2.linkAchsen[i].entlinken();
					entlinkt++;
				}
				else if(main2.achsen[Arrays.asList(main2.linkAchsen).indexOf(main2.linkAchsen[i].gelinkt)] != null)
				{
					main2.achsen[i] = main2.linkAchsen[i].entlinken(
							main2.achsen[Arrays.asList(main2.linkAchsen).indexOf(main2.linkAchsen[i].gelinkt)]);
					entlinkt++;
				}
			}
		}
	}

	public void punkte(K4[][] into)
	{
		for(int k = 0; k < enh.punkte.length; k++)
		{
			into[anfang + k] = new K4[enh.punkte[k].size()];
			for(int i = 0; i < enh.punkte[k].size(); i++)
			{
				LadePunkt la = enh.punkte[k].get(i);
				Achse a = main2.achsen[la.achse + anfang];
				if(a != null)
					into[anfang + k][i] = TK4F.zuPunkt(a, la.abstand, 0, la.vor, la.spin,
							Drehung.nplus(dmid, main2.dreh), inmid);
			}
		}
		into1 = into;
	}

	public void gibPl(ArrayList<Anzeige3> dieListe, K4[][] into, LichtW lw,
			boolean gmVision, boolean isMasterVision)
	{
		for(int j = 0; j < enh.f2.size(); j++)
		{
			LadeF2 f2 = enh.f2.get(j);
			K4[] eckenK = new K4[f2.ecken1.size()];
			for(int k = 0; k < f2.ecken1.size(); k++)
				eckenK[k] = into[f2.ecken1.get(k) + (f2.ecken3.get(k) ? 0 : anfang)][f2.ecken2.get(k)];
			K4[] eckenR = new K4[f2.ecken1.size()];
			for(int k = 0; k < f2.ecken1.size(); k++)
				eckenR[k] = into1[f2.ecken1.get(k) + (f2.ecken3.get(k) ? 0 : anfang)][f2.ecken2.get(k)];
			/*K4[] spken = new K4[f2.spken1.size()];
			for(int k = 0; k < f2.spken1.size(); k++)
				spken[k] = into[f2.spken1.get(k) + (f2.ecken3.get(k) ? 0 : anfang)][f2.spken2.get(k)];*/
			dieListe.add(new PNonBlock3(main2.tn, lw, f2.seite,
					f2.polyFarbe, f2.seed, eckenR, eckenK));
		}
	}

	public void tick(){}
}