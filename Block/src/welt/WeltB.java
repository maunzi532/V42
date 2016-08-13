package welt;

import java.io.*;
import java.util.*;
import k4.*;

public class WeltB
{
	//Umbruch beim WeltB speichern
	public static final int maxCta = 60;

	public K4 weltBlock;
	public K4 startWelt;
	public int[] end;
	public DerBlock[][][][] blocks;
	public DerBlock[] enden;
	public int[] endOrder;
	public K4[] starts;
	public Drehung[] startdrehs;
	public ArrayList<IFlag> flags = new ArrayList<>();

	public void setzeSE(K4 midShift, K4 setWeltBlock)
	{
		weltBlock = setWeltBlock;
		end = new int[4];
		end[0] = blocks.length;
		end[1] = blocks[0].length;
		end[2] = blocks[0][0].length;
		end[3] = blocks[0][0][0].length;
		startWelt = new K4(midShift.a - weltBlock.a / 2 * end[0], midShift.b - weltBlock.b / 2 * end[1],
				midShift.c - weltBlock.c / 2 * end[2], midShift.d - weltBlock.d / 2 * end[3]);
	}

	public WBP tw(K4 k)
	{
		return new WBP(intiize((k.a - startWelt.a) / weltBlock.a), intiize((k.b - startWelt.b) / weltBlock.b),
				intiize((k.c - startWelt.c) / weltBlock.c), intiize((k.d - startWelt.d) / weltBlock.d));
	}

	public WBP tw(double[] k)
	{
		return new WBP(intiize((k[0] - startWelt.a) / weltBlock.a), intiize((k[1] - startWelt.b) / weltBlock.b),
				intiize((k[2] - startWelt.c) / weltBlock.c), intiize((k[3] - startWelt.d) / weltBlock.d));
	}

	public int intiize(double d)
	{
		if(d >= 0)
			return (int) d;
		if((int) d == d)
			return (int) d;
		return (int) (d - 1);
	}

	public K4 wt(WBP p)
	{
		return new K4(p.k[0] * weltBlock.a + startWelt.a, p.k[1] * weltBlock.b + startWelt.b,
				p.k[2] * weltBlock.c + startWelt.c, p.k[3] * weltBlock.d + weltBlock.d / 2 + startWelt.d);
	}

	public K4 wt2(WBP p)
	{
		return new K4(p.k[0] * weltBlock.a + weltBlock.a / 2 + startWelt.a,
				p.k[1] * weltBlock.b + weltBlock.b / 2 + startWelt.b,
				p.k[2] * weltBlock.c + weltBlock.c / 2 + startWelt.c,
				p.k[3] * weltBlock.d + weltBlock.d / 2 + startWelt.d);
	}

	public K4 wt3(WBP p)
	{
		return new K4(p.k[0] * weltBlock.a + weltBlock.a / 2 + startWelt.a,
				p.k[1] * weltBlock.b + startWelt.b,
				p.k[2] * weltBlock.c + weltBlock.c / 2 + startWelt.c,
				p.k[3] * weltBlock.d + weltBlock.d / 2 + startWelt.d);
	}

	public WBP decodeTn(long tn)
	{
		if(tn < 0)
			return null;
		int a = (int)(tn / end[1] / end[2] / end[3]);
		tn -= a * end[1] * end[2] * end[3];
		int b = (int)(tn / end[2] / end[3]);
		tn -= b * end[2] * end[3];
		int c = (int)(tn / end[3]);
		int d = (int)(tn - c * end[3]);
		return new WBP(a, b, c, d);
	}

	public DerBlock gib(WBP p)
	{
		for(int i = 0; i < 4; i++)
		{
			if(p.k[endOrder[i]] < 0)
				return enden[endOrder[i] * 2];
			if(p.k[endOrder[i]] >= end[endOrder[i]])
				return enden[endOrder[i] * 2 + 1];
		}
		return blocks[p.k[0]][p.k[1]][p.k[2]][p.k[3]];
	}

	public boolean set(WBP p, DerBlock block)
	{
		if(p.k[0] < 0 || p.k[0] >= end[0] || p.k[1] < 0 || p.k[1] >= end[1] ||
				p.k[2] < 0 || p.k[2] >= end[2] || p.k[3] < 0 || p.k[3] >= end[3])
			return false;
		blocks[p.k[0]][p.k[1]][p.k[2]][p.k[3]] = block;
		return true;
	}

	public boolean opaque(DerBlock block)
	{
		return block.typ > 0;
	}

	private boolean vKanten(DerBlock block)
	{
		return block.typ == 2;
	}

	public boolean innen(WBP p)
	{
		for(int i = 0; i < 4; i++)
		{
			if(p.k[endOrder[i]] < 0)
				return false;
			if(p.k[endOrder[i]] >= end[endOrder[i]])
				return false;
		}
		return true;
	}

	public long tn(WBP p)
	{
		if(innen(p))
			return p.k[3] + end[3] * (p.k[2] + end[2] * (p.k[1] + end[1] * p.k[0]));
		return -1;
	}

	public boolean tk1(WBP p, int richtung)
	{
		if(opaque(gib(p)))
			return false;
		p.k[richtung % 2 == 0 ? 2 : 0] += richtung < 2 ? 1 : -1;
		if(!opaque(gib(p)))
			return false;
		boolean v = vKanten(gib(p));
			p.k[1]++;
		if(!v && opaque(gib(p)))
			return false;
		p.k[richtung % 2 == 0 ? 2 : 0] -= richtung < 2 ? 1 : -1;
		if(opaque(gib(p)))
			return false;
		p.k[1]--;
		return true;
	}

	public Boolean tk2(WBP p, int richtung)
	{
		if(opaque(gib(p)))
			return null;
		p.k[richtung % 2 == 0 ? 2 : 0] += richtung < 2 ? 1 : -1;
		if(!opaque(gib(p)))
			return null;
		p.k[1]++;
		boolean rf = !opaque(gib(p));
		p.k[richtung % 2 == 0 ? 2 : 0] -= richtung < 2 ? 1 : -1;
		if(opaque(gib(p)))
			return null;
		p.k[1]--;
		return rf;
	}

	public void speichern(String name, int[] size)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("S = ").append(size[0]).append(",")
				.append(size[1]).append(",")
				.append(size[2]).append(",")
				.append(size[3]).append(",").append("\n");
		sb.append("E = ").append(gib(new WBP(-1, 0, 0, 0))).append(",")
				.append(gib(new WBP(end[0], 0, 0, 0))).append(",")
				.append(gib(new WBP(0, -1, 0, 0))).append(",")
				.append(gib(new WBP(0, end[1], 0, 0))).append(",")
				.append(gib(new WBP(0, 0, -1, 0))).append(",")
				.append(gib(new WBP(0, 0, end[2], 0))).append(",")
				.append(gib(new WBP(0, 0, 0, -1))).append(",")
				.append(gib(new WBP(0, 0, 0, end[3]))).append(",").append("\n");
		sb.append("O = ").append(endOrder[0]).append(",")
				.append(endOrder[1]).append(",")
				.append(endOrder[2]).append(",")
				.append(endOrder[3]).append(",").append("\n");
		sb.append("F = ");
		for(int i = 0; i < flags.size(); i++)
		{
			K4 position = flags.get(i).position();
			Drehung dreh = flags.get(i).dreh();
			sb.append(position.a).append(",").append(position.b).append(",").append("\n	")
					.append(position.c).append(",").append(position.d).append(",").append("\n	")
					.append(dreh.wl).append(",").append(dreh.wb).append(",").append("\n	");
		}
		sb.append("\n");
		sb.append("B = ");
		int cta = 0;
		String last = "";
		int lastc = 0;
		for(int i0 = 0; i0 < size[0]; i0++)
			for(int i1 = 0; i1 < size[1]; i1++)
				for(int i2 = 0; i2 < size[2]; i2++)
					for(int i3 = 0; i3 < size[3]; i3++)
					{
						String b = gib(new WBP(i0, i1, i2, i3)).toString();
						if(b.equals(last))
							lastc++;
						else
						{
							if(lastc > 0)
							{
								if(lastc > 1)
									last = lastc + "x" + last;
								sb.append(last);
								cta += last.length() + 1;
								if(cta > maxCta)
								{
									cta = 0;
									sb.append(",\n	");
								}
								else
									sb.append(",");
							}
							last = b;
							lastc = 1;
						}

					}
		if(lastc > 1)
			last = lastc + "x" + last;
		sb.append(last).append(",");
		try
		{
			FileWriter fw = new FileWriter(new File(name.replace('/', File.separatorChar)));
			fw.write(sb.toString());
			fw.close();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}