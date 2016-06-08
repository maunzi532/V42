package nonBlock.aussehen;

import nonBlock.aussehen.ext.*;
import wahr.zugriff.*;

public abstract class NonBlock
{
	private static int tnm = -1;

	public K4 position;
	public Drehung dreh;
	public final long tn;
	public Focus focus;

	public LadeModell aussehen;
	public int elimit;
	public External[] externals = new External[0];
	public LinkAchse[] linkAchsen;
	public Achse[] achsen;
	public K4[][] punkte;
	public K4[][] punkteK;

	public AlternateStandard standard = null;

	protected NonBlock()
	{
		tnm--;
		tn = tnm;
	}

	public void tick()
	{
		for(int i = 0; i < externals.length; i++)
			externals[i].tick();
	}

	public void entlinken()
	{
		if(focus != null)
			focus.lade();
		achsen = new Achse[linkAchsen.length];
		int nicht = 0;
		int entlinkt = 0;
		while(entlinkt + nicht < elimit)
		{
			nicht = 0;
			for(int i = 0; i < elimit; i++)
			{
				if(linkAchsen[i] != null && achsen[i] != null)
					continue;
				if(linkAchsen[i] == null)
				{
					nicht++;
					continue;
				}
				if(linkAchsen[i].gelinkt == null)
				{
					achsen[i] = linkAchsen[i].entlinken();
					entlinkt++;
				}
				else if(linkAchsen[i].gelinkt.gelinktA != null)
				{
					achsen[i] = linkAchsen[i].entlinken(linkAchsen[i].gelinkt.gelinktA);
					entlinkt++;
				}
			}
		}
		for(int i = 0; i < externals.length; i++)
			externals[i].entLink(dreh, position);
	}

	public void punkte()
	{
		punkte = new K4[achsen.length][];
		for(int i = 0; i < elimit && i < aussehen.punkte.length; i++)
		{
			punkte[i] = new K4[aussehen.punkte[i].size()];
			for(int j = 0; j < aussehen.punkte[i].size(); j++)
			{
				LadePunkt la = aussehen.punkte[i].get(j);
				Achse a = achsen[la.achse];
				if(a != null)
					punkte[i][j] = zuPunkt(a, la.abstand, 0, la.vor, la.spin, dreh, position);
			}
		}
		for(int i = 0; i < externals.length; i++)
			externals[i].punkte(punkte);
		for(int i = 0; i < achsen.length; i++)
			if(achsen[i] != null)
			{
				achsen[i].tStart = transformSet1(new K4(achsen[i].start), dreh, position);
				achsen[i].tEnd = transformSet1(new K4(LinkAchse.achseEnde(achsen[i], null)), dreh, position);
			}
	}

	public void punkteTransformKam(K4 kam, Drehung kDreh)
	{
		K4 relativ = transformSet2(new K4(kam), kDreh, null);
		punkteK = new K4[punkte.length][];
		for(int i = 0; i < punkteK.length; i++)
			if(punkte[i] != null)
			{
				punkteK[i] = new K4[punkte[i].length];
				for(int j = 0; j < punkte[i].length; j++)
					punkteK[i][j] = new K4(punkte[i][j]);
				for(int j = 0; j < punkteK[i].length; j++)
					if(punkteK[i][j] != null)
						punkteK[i][j] = transformSet2(punkteK[i][j], kDreh, relativ);
			}
		for(int i = 0; i < externals.length; i++)
			if(externals[i] instanceof H)
				((H)externals[i]).transformK(kam, kDreh);
	}

	public static K4 transformSet1(K4 thi, Drehung d, K4 add)
	{
		thi.transformWBL(d);
		return K4.plus(thi, add);
	}

	public static K4 zuPunkt(Achse achse, double abstand, double side, double vor, double spin,
			Drehung mDreh, K4 mPos)
	{
		K4 tLen = new K4(side, abstand, vor * achse.len, achse.dShift);
		tLen.transformWS(Drehung.plus(spin, achse.spin));
		tLen.transformWBL(achse.dreh);
		tLen = K4.plus(tLen, achse.start);
		tLen.transformWBL(mDreh);
		return K4.plus(tLen, mPos);
	}

	public static K4 transformSet2(K4 thi, Drehung d, K4 diff)
	{
		thi.transformKLB(d);
		if(diff == null)
			return thi;
		return K4.diff(diff, thi);
	}
}