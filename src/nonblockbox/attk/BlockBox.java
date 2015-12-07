package nonblockbox.attk;

import block.*;
import falsch.*;
import k.*;

import java.util.*;

public class BlockBox
{
	private static final int[][] wandRichtung = new int[][]
			{
					{-1, 0, 0, 0},
					{1, 0, 0, 0},
					{0, -1, 0, 0},
					{0, 1, 0, 0},
					{0, 0, -1, 0},
					{0, 0, 1, 0},
					{0, 0, 0, -1},
					{0, 0, 0, 1}
			};

	private final NBB besitzer;
	public final double[] se;
	public final double[] ee;
	public double airshift;

	public BlockBox(NBB besitzer, K4 se, K4 ee, double airshift)
	{
		this.besitzer = besitzer;
		this.se = new double[]{se.a, se.b, se.c, se.d};
		this.ee = new double[]{ee.a, ee.b, ee.c, ee.d};
		this.airshift = airshift;
	}

	public K4 check(K4 mov)
	{
		double[] mov1 = new double[4];
		mov1[0] = mov.a;
		mov1[1] = mov.b;
		mov1[2] = mov.c;
		mov1[3] = mov.d;
		return check(mov1);
	}

	private K4 check(double[] mov1)
	{
		double len = Math.sqrt(mov1[0] * mov1[0] + mov1[1] * mov1[1] + mov1[2] * mov1[2] + mov1[3] * mov1[3]);
		if(len <= 0)
			return new K4();
		double[] movStep = new double[4];
		for(int i = 0; i < 4; i++)
			movStep[i] = mov1[i] / len * Staticf.step;
		int[] end = new int[4];
		Arrays.fill(end, -1);
		for(int i = 0; i < 4; i++)
			if(mov1[i] == 0)
				end[i] = -2;
		double[] reMov = new double[4];
		double[] fin = new double[4];
		for(int i = 0; i <= len / Staticf.step; i++)
		{
			for(int k1 = 0; k1 < 4; k1++)
				reMov[k1] = end[k1] == -1 ? movStep[k1] * i : end[k1] == -2 ? 0 : movStep[k1] * end[k1];
			for(int k1 = 0; k1 < 4; k1++)
			{
				if(end[k1] == -1)
				{
					double cha = mov1[k1] > 0 ? ee[k1] : se[k1];
					double[] chse = new double[]{
							besitzer.position.a + reMov[0] + se[0],
							besitzer.position.b + reMov[1] + se[1],
							besitzer.position.c + reMov[2] + se[2],
							besitzer.position.d + reMov[3] + se[3]};
					chse[k1] += cha - se[k1];
					double[] chee = new double[]{
							besitzer.position.a + reMov[0] + ee[0],
							besitzer.position.b + reMov[1] + ee[1],
							besitzer.position.c + reMov[2] + ee[2],
							besitzer.position.d + reMov[3] + ee[3]
					};
					chee[k1] += cha + movStep[k1] - ee[k1];
					WBP p0 = Koord.tw(chse);
					WBP p1 = Koord.tw(chee);
					if(p0.k[k1] != p1.k[k1])
					{
						p0.k[k1] = p1.k[k1];
						for(int ja = p0.k[0]; ja <= p1.k[0]; ja++)
							for(int jb = p0.k[1]; jb <= p1.k[1]; jb++)
								for(int jc = p0.k[2]; jc <= p1.k[2]; jc++)
									for(int jd = p0.k[3]; jd <= p1.k[3]; jd++)
										if(WeltB.opaque(WeltB.gib(new WBP(ja, jb, jc, jd))))
										{
											end[k1] = i;
											fin[k1] = movStep[k1] * i;
										}
					}
					if(end[k1] == -1)
						reMov[k1] += movStep[k1];
				}
			}
		}
		for(int i = 0; i < 4; i++)
			if(end[i] == -1)
				fin[i] = mov1[i];
		return new K4(fin[0], fin[1], fin[2], fin[3]);
	}

	public boolean checkOnly(K4 mov)
	{
		double[] mov1 = new double[4];
		mov1[0] = mov.a;
		mov1[1] = mov.b;
		mov1[2] = mov.c;
		mov1[3] = mov.d;
		return checkOnly(mov1);
	}

	public boolean checkOnly(double[] mov1)
	{
		double len = Math.sqrt(mov1[0] * mov1[0] + mov1[1] * mov1[1] + mov1[2] * mov1[2] + mov1[3] * mov1[3]);
		if(len <= 0)
			return true;
		double[] movStep = new double[4];
		for(int i = 0; i < 4; i++)
			movStep[i] = mov1[i] / len * Staticf.step;
		int[] end = new int[4];
		Arrays.fill(end, -1);
		for(int i = 0; i < 4; i++)
			if(mov1[i] == 0)
				end[i] = -2;
		double[] reMov = new double[4];
		for(int i = 0; i <= len / Staticf.step; i++)
		{
			for(int k1 = 0; k1 < 4; k1++)
				reMov[k1] = end[k1] == -1 ? movStep[k1] * i : end[k1] == -2 ? 0 : movStep[k1] * end[k1];
			for(int k1 = 0; k1 < 4; k1++)
			{
				if(end[k1] == -1)
				{
					double cha = mov1[k1] > 0 ? ee[k1] : se[k1];
					double[] chse = new double[]{
							besitzer.position.a + reMov[0] + se[0],
							besitzer.position.b + reMov[1] + se[1],
							besitzer.position.c + reMov[2] + se[2],
							besitzer.position.d + reMov[3] + se[3]};
					chse[k1] += cha - se[k1];
					double[] chee = new double[]{
							besitzer.position.a + reMov[0] + ee[0],
							besitzer.position.b + reMov[1] + ee[1],
							besitzer.position.c + reMov[2] + ee[2],
							besitzer.position.d + reMov[3] + ee[3]
					};
					chee[k1] += cha + movStep[k1] - ee[k1];
					WBP p0 = Koord.tw(chse);
					WBP p1 = Koord.tw(chee);
					if(p0.k[k1] != p1.k[k1])
					{
						p0.k[k1] = p1.k[k1];
						for(int ja = p0.k[0]; ja <= p1.k[0]; ja++)
							for(int jb = p0.k[1]; jb <= p1.k[1]; jb++)
								for(int jc = p0.k[2]; jc <= p1.k[2]; jc++)
									for(int jd = p0.k[3]; jd <= p1.k[3]; jd++)
										if(WeltB.opaque(WeltB.gib(new WBP(ja, jb, jc, jd))))
											return false;
					}
					if(end[k1] == -1)
						reMov[k1] += movStep[k1];
				}
			}
		}
		return true;
	}

	public boolean checkWand(int welche, double abstand)
	{
		double[] c1 = new double[4];
		for(int i = 0; i < 4; i++)
			c1[i] = wandRichtung[welche][i] * abstand;
		return !checkOnly(c1);
	}
}