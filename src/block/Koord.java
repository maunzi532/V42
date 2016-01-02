package block;

import wahr.zugriff.*;

public class Koord
{
	static final int[][] seiten = new int[][]
			{
					{-1, 0, 0},
					{1, 0, 0},
					{0, -1, 0},
					{0, 1, 0},
					{0, 0, -1},
					{0, 0, 1},
			};
	public static K4 weltBlock;
	public static K4 startWelt;
	public static int[] end;

	public static WBP tw(K4 k)
	{
		return new WBP(intiize((k.a - startWelt.a) / weltBlock.a), intiize((k.b - startWelt.b) / weltBlock.b),
				intiize((k.c - startWelt.c) / weltBlock.c), intiize((k.d - startWelt.d) / weltBlock.d));
	}

	public static WBP tw(double[] k)
	{
		return new WBP(intiize((k[0] - startWelt.a) / weltBlock.a), intiize((k[1] - startWelt.b) / weltBlock.b),
				intiize((k[2] - startWelt.c) / weltBlock.c), intiize((k[3] - startWelt.d) / weltBlock.d));
	}

	public static int intiize(double d)
	{
		if(d >= 0)
			return (int) d;
		if((int) d == d)
			return (int) d;
		return (int) (d - 1);
	}

	public static K4 wt(WBP p)
	{
		return new K4(p.k[0] * weltBlock.a + startWelt.a, p.k[1] * weltBlock.b + startWelt.b,
				p.k[2] * weltBlock.c + startWelt.c, p.k[3] * weltBlock.d + weltBlock.d / 2 + startWelt.d);
	}

	public static K4 wt2(WBP p)
	{
		return new K4(p.k[0] * weltBlock.a + weltBlock.a / 2 + startWelt.a,
				p.k[1] * weltBlock.b + weltBlock.b / 2 + startWelt.b,
				p.k[2] * weltBlock.c + weltBlock.c / 2 + startWelt.c,
				p.k[3] * weltBlock.d + weltBlock.d / 2 + startWelt.d);
	}

	public static void setzeSE(K4 midShift, K4 setWeltBlock)
	{
		weltBlock = setWeltBlock;
		end = new int[4];
		end[0] = WeltB.blocks.length;
		end[1] = WeltB.blocks[0].length;
		end[2] = WeltB.blocks[0][0].length;
		end[3] = WeltB.blocks[0][0][0].length;
		startWelt = new K4(midShift.a - weltBlock.a / 2 * end[0], midShift.b - weltBlock.b / 2 * end[1],
				midShift.c - weltBlock.c / 2 * end[2], midShift.d - weltBlock.d / 2 * end[3]);
	}

	public static WBP decodeTn(long tn)
	{
		int a = (int)(tn / end[1] / end[2] / end[3]);
		tn -= a * end[1] * end[2] * end[3];
		int b = (int)(tn / end[2] / end[3]);
		tn -= b * end[2] * end[3];
		int c = (int)(tn / end[3]);
		int d = (int)(tn - c * end[3]);
		return new WBP(a, b, c, d);
	}

	public static K4 N2Start(int[] ort)
	{
		return new K4(startWelt.a + weltBlock.a / 2 + weltBlock.a * ort[0],
				startWelt.b + weltBlock.b * ort[1],
				startWelt.c + weltBlock.c / 2 + weltBlock.c * ort[2],
				startWelt.d + weltBlock.d / 2 + weltBlock.d * ort[3]);
	}
}