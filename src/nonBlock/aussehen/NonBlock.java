package nonBlock.aussehen;

import ansicht.*;
import ansicht.a3.*;
import nonBlock.aussehen.ext.*;
import nonBlock.aktion.*;
import wahr.zugriff.*;

public abstract class NonBlock
{
	private static int tnm = -1;

	public K4 position;
	public Drehung dreh;
	public final long tn;
	public Focus focus;
	public LichtW lw;
	public WeltND dw;

	public LadeModell aussehen;
	public int elimit;
	public External[] externals = new External[0];
	public LinkAchse[] linkAchsen;
	public Achse[] achsen;
	public K4[][] punkte;
	public K4[][] punkteK;

	protected NonBlock(LichtW lw, WeltND dw)
	{
		this.lw = lw;
		this.dw = dw;
		tnm--;
		tn = tnm;
		dw.nonBlocks.add(this);
	}

	protected NonBlock(AllWelt aw)
	{
		this(aw.lw, aw.dw);
	}

	public void ende()
	{
		dw.nonBlocks.remove(this);
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
					punkte[i][j] = TK4F.zuPunkt(a, la.abstand, 0, la.vor, la.spin, dreh, position);
			}
		}
		for(int i = 0; i < externals.length; i++)
			externals[i].punkte(punkte);
		for(int i = 0; i < achsen.length; i++)
			if(achsen[i] != null)
			{
				achsen[i].tStart = TK4F.transformSet1(new K4(achsen[i].start), dreh, position);
				achsen[i].tEnd = TK4F.transformSet1(new K4(TK4F.achseEnde(achsen[i], null)), dreh, position);
			}
	}

	public void punkteTransformKam(K4 kam, Drehung kDreh)
	{
		K4 relativ = TK4F.transformSet2(new K4(kam), kDreh, null);
		punkteK = new K4[punkte.length][];
		for(int i = 0; i < punkteK.length; i++)
			if(punkte[i] != null)
			{
				punkteK[i] = new K4[punkte[i].length];
				for(int j = 0; j < punkte[i].length; j++)
					punkteK[i][j] = new K4(punkte[i][j]);
				for(int j = 0; j < punkteK[i].length; j++)
					if(punkteK[i][j] != null)
						punkteK[i][j] = TK4F.transformSet2(punkteK[i][j], kDreh, relativ);
			}
		for(int i = 0; i < externals.length; i++)
			if(externals[i] instanceof H)
				((H)externals[i]).transformK(kam, kDreh);
	}
}