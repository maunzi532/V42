package ansicht.a3;

import ansicht.*;
import block.*;
import wahr.zugriff.*;

import java.util.*;

class BlockZuAnz
{
	private static final int[][] seiten = new int[][]
			{
					{5, 7, 3, 1},
					{4, 6, 7, 5},
					{0, 2, 6, 4},
					{1, 3, 2, 0},
					{7, 6, 2, 3},
					{0, 4, 5, 1}

			};

	private static final int[][] punkte = new int[][]
			{
					{0, 0, 0},
					{1, 0, 0},
					{0, 1, 0},
					{1, 1, 0},
					{0, 0, 1},
					{1, 0, 1},
					{0, 1, 1},
					{1, 1, 1}
			};

	private static final int[][] allD = new int[][]
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

	private WeltB wb;
	private LichtW lw;

	public BlockZuAnz(WeltB wb, LichtW lw)
	{
		this.wb = wb;
		this.lw = lw;
	}

	public void zuAnz(ArrayList<Anzeige3> dieListe, K4 kam, Drehung kDreh,
			K4 radius, int visionRange4D, int baumodus)
	{
		WBP kaw0 = new WBP(wb.tw(new K4(kam.a - radius.a, kam.b - radius.b,
				kam.c - radius.c, kam.d - radius.d)));
		WBP kawEnd = new WBP(wb.tw(new K4(kam.a + radius.a + 1, kam.b + radius.b + 1,
				kam.c + radius.c + 1, kam.d + radius.d + 1)));
		double wbD = (kam.d - wb.startWelt.d) / wb.weltBlock.d;
		int theD = wb.intiize((kam.d - wb.startWelt.d) / wb.weltBlock.d);
		double wbDShift = wbD - theD;
		K4 relativ = TK4F.transformSet2(new K4(kam), kDreh, null);
		for(int a = kaw0.k[0]; a < kawEnd.k[0]; a++)
			for(int b = kaw0.k[1]; b < kawEnd.k[1]; b++)
				for(int c = kaw0.k[2]; c < kawEnd.k[2]; c++)
				{
					for(int type = 0; type < 3; type++)
						addA3(dieListe, visionRange4D, type, new int[]{a, b, c, theD}, kam.d, relativ, kDreh);
					addDInfo(dieListe, visionRange4D, new int[]{a, b, c, theD},
							wbD, wbDShift, baumodus, relativ, kDreh);
				}
	}

	private boolean sichtOpaque(DerBlock block)
	{
		return block.typ > 0;
	}

	private void addA3(ArrayList<Anzeige3> dieListe, int visionRange4D, int type, int[] ort,
			double kamD, K4 relativ, Drehung kDreh)
	{
		for(int d = ort[3] - visionRange4D; d <= ort[3] + visionRange4D; d++)
		{
			int[] ort1 = new int[]{ort[0], ort[1], ort[2], d};
			int[] ort2 = new int[]{ort[0], ort[1], ort[2], d};
			ort2[type] = ort2[type] - 1;
			DerBlock block1 = wb.gib(new WBP(ort1));
			DerBlock block2 = wb.gib(new WBP(ort2));
			boolean sicht1 = sichtOpaque(block1);
			if(sicht1 != sichtOpaque(block2))
			{
				int flaechenNummer = type / 2;
				if(sicht1)
					flaechenNummer += 2;
				if(type == 1)
					flaechenNummer = 4 + (sicht1 ? 1 : 0);
				WBP ortA = new WBP(sicht1 ? ort1 : ort2);
				DerBlock attach = sicht1 ? block1 : block2;
				K4[] eR = new K4[4];
				K4[] eK = new K4[4];
				for(int i = 0; i < 4; i++)
				{
					int[] plus = punkte[seiten[flaechenNummer][i]];
					WBP ortP = new WBP(ortA);
					for(int j = 0; j < 3; j++)
						ortP.k[j] = ortP.k[j] + plus[j];
					eR[i] = wb.wt(ortP);
					eK[i] = TK4F.transformSet2(new K4(eR[i]), kDreh, relativ);
				}
				dieListe.add(new PBlock3(wb.tn(ortA), lw, true, (wb.wt2(ortA).d - kamD) / wb.weltBlock.d - 0.5,
						(wb.wt2(ortA).d - kamD) / wb.weltBlock.d + 0.5,
						XFBT2.gibVonIndex(attach.name(), flaechenNummer, 10/*WTF*/), eR, eK));
			}
		}
	}

	private void addDInfo(ArrayList<Anzeige3> dieListe, int visionRange4D, int[] ort1,
			double wbD, double wbDShift, int baumodus, K4 relativ, Drehung kDreh)
	{
		boolean showRot = true;
		boolean showGn = true;
		switch((int)(wbDShift * 4))
		{
			case 0:
				int[] ort21 = new int[ort1.length];
				System.arraycopy(ort1, 0, ort21, 0, 4);
				ort21[3] = ort21[3] - 1;
				if(sichtOpaque(wb.gib(new WBP(ort21))))
					showRot = false;
				if(sichtOpaque(wb.gib(new WBP(ort1))))
					showGn = false;
				break;
			case 1:
			case 2:
				if(sichtOpaque(wb.gib(new WBP(ort1))))
					return;
				break;
			case 3:
				int[] ort22 = new int[ort1.length];
				System.arraycopy(ort1, 0, ort22, 0, 4);
				ort22[3] = ort22[3] - 1;
				if(sichtOpaque(wb.gib(new WBP(ort1))))
					showRot = false;
				if(sichtOpaque(wb.gib(new WBP(ort22))))
					showGn = false;
				break;
		}
		if(showRot)
			for(int i = 0; i <= visionRange4D; i++)
			{
				int[] ort7 = new int[ort1.length];
				System.arraycopy(ort1, 0, ort7, 0, 4);
				ort7[3] = ort7[3] - i;
				K4 mid7 = wb.wt2(new WBP(ort7));
				double ddiff1 = (mid7.d - wbD) / wb.weltBlock.d - 0.5;
				double ddiff2 = (mid7.d - wbD) / wb.weltBlock.d + 0.5;
				if(ddiff2 > 0)
					ddiff2 = 0;
				maybeAddDInfo(dieListe, new WBP(ort7), mid7, ddiff1, ddiff2, baumodus, relativ, kDreh);
			}
		if(showGn)
			for(int i = 0; i <= visionRange4D; i++)
			{
				int[] ort7 = new int[ort1.length];
				System.arraycopy(ort1, 0, ort7, 0, 4);
				ort7[3] = ort7[3] + i;
				K4 mid7 = wb.wt2(new WBP(ort7));
				double ddiff1 = (mid7.d - wbD) / wb.weltBlock.d - 0.5;
				double ddiff2 = (mid7.d - wbD) / wb.weltBlock.d + 0.5;
				if(ddiff1 < 0)
					ddiff1 = 0;
				maybeAddDInfo(dieListe, new WBP(ort7), mid7, ddiff1, ddiff2, baumodus, relativ, kDreh);
			}
	}

	private void maybeAddDInfo(ArrayList<Anzeige3> dieListe, WBP w7, K4 mid7,
			double ddiff1, double ddiff2, int baumodus, K4 relativ, Drehung kDreh)
	{
		if(!wb.innen(w7))
			return;
		boolean ok = false;
		if(sichtOpaque(wb.gib(w7)))
			ok = true;
		if(baumodus > 1)
			ok = true;
		if(baumodus == 1)
		{
			for(int i = 0; i < 8; i++)
			{
				if(sichtOpaque(wb.gib(new WBP(w7.k[0] + allD[i][0], w7.k[1] + allD[i][1],
						w7.k[2] + allD[i][2], w7.k[3] + allD[i][3]))))
				{
					ok = true;
					break;
				}
			}
		}
		if(ok)
		{
			K4 kMid = TK4F.transformSet2(mid7, kDreh, relativ);
			dieListe.add(new BlockDInfo3(wb.tn(w7), lw, mid7, kMid, true,
					wb.gib(w7), ddiff1, ddiff2));
		}
	}
}