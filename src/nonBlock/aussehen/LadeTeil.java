package nonBlock.aussehen;

import ansicht.a3.*;
import wahr.zugriff.*;

import java.util.*;

public class LadeTeil
{
	final HashMap<Integer, ArrayList<LadePunkt>> punkte;
	final ArrayList<LadeF2> f2;
	final int end;

	private LadeTeil(String code, IFarbeff2 factory)
	{
		String[] zeilen = code.split("\n");
		int achseAktuell = -1;
		punkte = new HashMap<>();
		f2 = new ArrayList<>();
		for(int i = 0; i < zeilen.length; i++)
		{
			if(!zeilen[i].isEmpty() && !zeilen[i].startsWith("/"))
			{
				if(zeilen[i].startsWith("Achse "))
				{
					achseAktuell = Integer.parseInt(zeilen[i].substring(6, zeilen[i].length()));
					punkte.put(achseAktuell, new ArrayList<>());
				}
				else if(zeilen[i].startsWith("F") && zeilen[i].charAt(2) == ' ')
					fx(zeilen[i], factory);
				else if(zeilen[i].startsWith("PRISMA"))
					prisma(zeilen[i]);
				else if(zeilen[i].startsWith("PYRAMIDE"))
					pyramide(zeilen[i]);
				else
				{
					String[] x3 = zeilen[i].split(" ");
					punkte.get(achseAktuell).add(new LadePunkt(achseAktuell,
							Double.parseDouble(x3[0]), Double.parseDouble(x3[1]), Double.parseDouble(x3[2])));
				}
			}
		}
		end = Collections.max(punkte.keySet());
	}

	private void fx(String zeile, IFarbeff2 factory)
	{
		Boolean seite = null;
		String[] x2 = zeile.split(" ");
		if(x2[0].equals("FA"))
			seite = true;
		else if(x2[0].equals("FV"))
			seite = false;
		IFarbeff2 fff2 = factory.gibNeu(x2[1]);
		LadeF2 f22 = new LadeF2(fff2, seite);
		f2.add(f22);
		for(int j = 0; j + 2 < x2.length; j++)
		{
			String[] cx2 = x2[j + 2].split("-");
			if(cx2.length == 1)
			{
				f22.ecken1.add(f22.ecken1.get(f22.ecken1.size() - 1));
				f22.ecken3.add(f22.ecken3.get(f22.ecken3.size() - 1));
				f22.ecken2.add(Integer.parseInt(cx2[0]));
			}
			else if(cx2.length == 2)
			{
				if(cx2[0].startsWith("+"))
				{
					f22.ecken1.add(Integer.parseInt(cx2[0].substring(1)));
					f22.ecken3.add(true);
				}
				else
				{
					f22.ecken1.add(Integer.parseInt(cx2[0]));
					f22.ecken3.add(false);
				}
				f22.ecken2.add(Integer.parseInt(cx2[1]));
			}
			else
			{
				if(cx2[0].startsWith("+"))
				{
					f22.spken1.add(Integer.parseInt(cx2[0].substring(1)));
					f22.spken3.add(true);
				}
				else
				{
					f22.spken1.add(Integer.parseInt(cx2[0]));
					f22.spken3.add(false);
				}
				f22.spken2.add(Integer.parseInt(cx2[1]));
			}
		}
	}

	private void prisma(String zeile)
	{
		String[] x2 = zeile.split(" ");
		Boolean seite = null;
		if(x2[0].endsWith("+"))
			seite = true;
		else if(x2[0].endsWith("-"))
			seite = false;
		int seitig = Integer.parseInt(x2[1]);
		int[][] daten = new int[seitig][6];
		int sh = 2;
		int[] all = new int[]{-1, -1};
		for(int j = 0; j < seitig; j++)
			for(int k = 0; k < 2; k++)
			{
				if(all[k] < 0)
				{
					String[] cx2 = x2[sh].split("-");
					if(cx2.length > 2 && cx2[2].equals("ALL"))
						all[k] = j;
					if(cx2.length == 1)
					{
						daten[j][k * 3] = daten[j - 1][k * 3];
						daten[j][k * 3 + 2] = daten[j - 1][k * 3 + 2];
						daten[j][k * 3 + 1] = Integer.parseInt(cx2[0]);
					}
					else
					{
						if(cx2[0].startsWith("+"))
						{
							daten[j][k * 3] = Integer.parseInt(cx2[0].substring(1));
							daten[j][k * 3 + 2] = 1;
						}
						else
							daten[j][k * 3] = Integer.parseInt(cx2[0]);
						daten[j][k * 3 + 1] = Integer.parseInt(cx2[1]);
					}
					sh++;
				}
				else
				{
					daten[j][k * 3] = daten[all[k]][k * 3];
					daten[j][k * 3 + 1] = daten[all[k]][k * 3 + 1] + j - all[k];
					daten[j][k * 3 + 2] = daten[all[k]][k * 3 + 2];
				}
			}
		PolyFarbe fall = null;
		for(int j = 0; j < seitig; j++)
		{
			LadeF2 l;
			if(fall != null)
				l = new LadeF2(fall, seite);
			else if(x2[sh].equals("NICHT"))
			{
				sh++;
				continue;
			}
			else if(x2[sh].endsWith("-ALL"))
			{
				fall = new PolyFarbe(x2[sh].substring(0, x2[sh].length() - 4));
				l = new LadeF2(fall, seite);
			}
			else
				l = new LadeF2(new PolyFarbe(x2[sh]), seite);
			for(int m = 0; m < 4; m++)
			{
				l.ecken1.add(daten[(j + (m == 1 || m == 2 ? 1 : 0)) % seitig][m > 1 ? 3 : 0]);
				l.ecken2.add(daten[(j + (m == 1 || m == 2 ? 1 : 0)) % seitig][m > 1 ? 4 : 1]);
				l.ecken3.add(daten[(j + (m == 1 || m == 2 ? 1 : 0)) % seitig][m > 1 ? 5 : 2] == 1);
			}
			f2.add(l);
			sh++;
		}
	}

	private void pyramide(String zeile)
	{
		String[] x2 = zeile.split(" ");
		Boolean seite = null;
		if(x2[0].endsWith("+"))
			seite = true;
		else if(x2[0].endsWith("-"))
			seite = false;
		int seitig = Integer.parseInt(x2[1]);
		int[][] daten = new int[seitig][3];
		int ecke1;
		boolean ecke3 = false;
		String[] cxe2 = x2[2].split("-");
		if(cxe2[0].startsWith("+"))
		{
			ecke1 = Integer.parseInt(cxe2[0].substring(1));
			ecke3 = true;
		}
		else
			ecke1 = Integer.parseInt(cxe2[0]);
		int ecke2 = Integer.parseInt(cxe2[1]);
		int sh = 3;
		int all = -1;
		for(int j = 0; j < seitig; j++)
		{
			if(all < 0)
			{
				String[] cx2 = x2[sh].split("-");
				if(cx2.length > 2 && cx2[2].equals("ALL"))
					all = j;
				if(cx2.length == 1)
				{
					daten[j][0] = daten[j - 1][0];
					daten[j][2] = daten[j - 1][2];
					daten[j][1] = Integer.parseInt(cx2[0]);
				}
				else
				{
					if(cx2[0].startsWith("+"))
					{
						daten[j][0] = Integer.parseInt(cx2[0].substring(1));
						daten[j][2] = 1;
					}
					else
						daten[j][0] = Integer.parseInt(cx2[0]);
					daten[j][1] = Integer.parseInt(cx2[1]);
				}
				sh++;
			}
			else
			{
				daten[j][0] = daten[all][0];
				daten[j][1] = daten[all][1] + j - all;
				daten[j][2] = daten[all][2];
			}
		}
		PolyFarbe fall = null;
		for(int j = 0; j < seitig; j++)
		{
			LadeF2 l;
			if(fall != null)
				l = new LadeF2(fall, seite);
			else if(x2[sh].equals("NICHT"))
			{
				sh++;
				continue;
			}
			else if(x2[sh].endsWith("-ALL"))
			{
				fall = new PolyFarbe(x2[sh].substring(0, x2[sh].length() - 4));
				l = new LadeF2(fall, seite);
			}
			else
				l = new LadeF2(new PolyFarbe(x2[sh]), seite);
			for(int m = 0; m < 3; m++)
			{
				if(m != 2)
				{
					l.ecken1.add(daten[(j + m) % seitig][0]);
					l.ecken2.add(daten[(j + m) % seitig][1]);
					l.ecken3.add(daten[(j + m) % seitig][2] == 1);
				}
				else
				{
					l.ecken1.add(ecke1);
					l.ecken2.add(ecke2);
					l.ecken3.add(ecke3);
				}
			}
			f2.add(l);
			sh++;
		}
	}

	public static LadeTeil gibVonIndex(String name, IFarbeff2 factory)
	{
		if(Index.geladen.containsKey(name))
			return (LadeTeil) Index.geladen.get(name);
		LadeTeil s = new LadeTeil(Index.bauName("Ladeteile", name), factory);
		Index.geladen.put(name, s);
		return s;
	}
}