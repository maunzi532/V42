package nonBlock.aktion;

import java.util.*;
import k4.*;
import nonBlock.aktion.lesen.*;

public class MDAktion extends Aktion implements LadAktion
{
	private Boolean[] mvdA;
	private double[] mvd0;
	private double[] mvdT;
	private Forced forced;

	public MDAktion(){}

	@Override
	public ZDelay erzeuge(String whtd, AkA dislocated, AkA besitzer2, Tverlay tverlay,
			HashMap<String, String> parameters, ArrayList<String> list)
	{
		RZahl[] mvd = new RZahl[6];
		Boolean[] mvd2 = new Boolean[6];
		for(String cd0 : parameters.keySet())
		{
			if(cd0.endsWith("m") || cd0.endsWith("t"))
			{
				if(cd0.length() == 2)
				{
					int nd = cd0.charAt(0) - 97;
					if(nd >= 0 && nd < 4)
					{
						mvd2[nd] = cd0.endsWith("t");
						mvd[nd] = new RZahl(parameters.get(cd0), false);
					}
				}
				else if(cd0.length() == 3 && cd0.charAt(0) == 'w')
				{
					int nd = 0;
					if(cd0.charAt(1) == 'l')
						nd = 4;
					else if(cd0.charAt(1) == 'b')
						nd = 5;
					if(nd > 0)
					{
						mvd2[nd] = cd0.endsWith("t");
						mvd[nd] = new RZahl(parameters.get(cd0), true);
					}
				}
			}
		}
		if(whtd.charAt(0) == 'B')
		{
			double[] mvdR = new double[mvd.length];
			for(int i1 = 0; i1 < mvd.length; i1++)
				if(mvd[i1] != null)
				{
					mvd[i1].v1 = dislocated;
					mvdR[i1] = mvd[i1].rechne();
				}
			MDAktion md = new MDAktion(dislocated,
					Integer.parseInt(parameters.get("dauer")),
					Integer.parseInt(parameters.get("power")), mvdR, mvd2);
			dislocated.addAktion(md);
		}
		else
		{
			double[] mvd0 = new double[]{dislocated.position().a, dislocated.position().b,
					dislocated.position().c, dislocated.position().d, dislocated.dreh().wl, dislocated.dreh().wb};
			double[] mvdT = new double[6];
			for(int i = 0; i < 6; i++)
				if(mvdA[i] != null)
				{
					if(mvdA[i])
						mvdT[i] = mvd[i].rechne();
					else
						mvdT[i] = mvd0[i] + mvd[i].rechne();
				}
			mvdT[4] = Drehung.sichern(mvdT[4]);
			mvdT[5] = Drehung.sichern(mvdT[5]);
			if(mvdA[0] != null)
				dislocated.position().a = mvdT[0];
			if(mvdA[1] != null)
				dislocated.position().b = mvdT[1];
			if(mvdA[2] != null)
				dislocated.position().c = mvdT[2];
			if(mvdA[3] != null)
				dislocated.position().d = mvdT[3];
			if(mvdA[4] != null)
				dislocated.dreh().wl = mvdT[4];
			if(mvdA[5] != null)
				dislocated.dreh().wb = mvdT[5];
			dislocated.addForced(new Forced(new K4(), 20));
		}
		return null;
	}

	public MDAktion(AkA besitzer, int dauer, int power, double[] mvd, Boolean[] mvdA)
	{
		super(besitzer, dauer, power);
		this.mvdA = mvdA;
		mvd0 = new double[]{besitzer.position().a, besitzer.position().b,
				besitzer.position().c, besitzer.position().d, besitzer.dreh().wl, besitzer.dreh().wb};
		mvdT = new double[6];
		for(int i = 0; i < 6; i++)
			if(mvdA[i] != null)
			{
				if(mvdA[i])
					mvdT[i] = mvd[i];
				else
					mvdT[i] = mvd0[i] + mvd[i];
			}
		mvdT[4] = Drehung.sichern(mvdT[4]);
		mvdT[5] = Drehung.sichern(mvdT[5]);
		boolean[] fa = new boolean[4];
		for(int i = 0; i < 4; i++)
			if(mvdA[i] != null)
				fa[i] = true;
		forced = new Forced(fa, new K4(), power);
	}

	public void tick()
	{
		int m1 = dauer - aktuell;
		if(mvdA[0] != null)
			forced.movement.a = (mvd0[0] * m1 + mvdT[0] * aktuell) / dauer - besitzer.position().a;
		if(mvdA[1] != null)
			forced.movement.b = (mvd0[1] * m1 + mvdT[1] * aktuell) / dauer - besitzer.position().b;
		if(mvdA[2] != null)
			forced.movement.c = (mvd0[2] * m1 + mvdT[2] * aktuell) / dauer - besitzer.position().c;
		if(mvdA[3] != null)
			forced.movement.d = (mvd0[3] * m1 + mvdT[3] * aktuell) / dauer - besitzer.position().d;
		besitzer.addForced(forced);
		if(mvdA[4] != null)
			besitzer.dreh().wl = mvd0[4] + (mvdT[4] - mvd0[4]) * aktuell / dauer;
		if(mvdA[5] != null)
			besitzer.dreh().wb = mvd0[5] + (mvdT[5] - mvd0[5]) * aktuell / dauer;
		besitzer.dreh().sichern();
	}
}