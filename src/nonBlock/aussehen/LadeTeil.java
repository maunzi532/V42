package nonBlock.aussehen;

import ansicht.n2.xF.*;

import java.util.*;

public class LadeTeil
{
	final HashMap<Integer, ArrayList<LadePunkt>> punkte;
	final ArrayList<LadeF2> f2;
	final int end;

	public LadeTeil(String code)
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
				{
					XFarbe farbe;
					Boolean seite = null;
					String[] x2 = zeilen[i].split(" ");
					if(x2[0].equals("FA"))
						seite = true;
					else if(x2[0].equals("FV"))
						seite = false;
					farbe = XFarbe.t2xf(x2[1]);
					f2.add(new LadeF2(farbe, seite));
					for(int j = 0; j + 2 < x2.length; j++)
					{
						String[] cx2 = x2[j + 2].split("-");
						if(cx2.length <= 2)
						{
							if(cx2[0].startsWith("+"))
							{
								f2.get(f2.size() - 1).ecken1.add(Integer.parseInt(cx2[0].substring(1)));
								f2.get(f2.size() - 1).ecken3.add(true);
							}
							else
							{
								f2.get(f2.size() - 1).ecken1.add(Integer.parseInt(cx2[0]));
								f2.get(f2.size() - 1).ecken3.add(false);
							}
							f2.get(f2.size() - 1).ecken2.add(Integer.parseInt(cx2[1]));
						}
						else
						{
							if(cx2[0].startsWith("+"))
							{
								f2.get(f2.size() - 1).spken1.add(Integer.parseInt(cx2[0].substring(1)));
								f2.get(f2.size() - 1).spken3.add(true);
							}
							else
							{
								f2.get(f2.size() - 1).spken1.add(Integer.parseInt(cx2[0]));
								f2.get(f2.size() - 1).spken3.add(false);
							}
							f2.get(f2.size() - 1).spken2.add(Integer.parseInt(cx2[1]));
						}
					}
				}
				else if(zeilen[i].startsWith("PRISMA"))
				{
					String[] x2 = zeilen[i].split(" ");
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
								if(cx2[0].startsWith("+"))
								{
									daten[j][k * 3] = Integer.parseInt(cx2[0].substring(1));
									daten[j][k * 3 + 2] = 1;
								}
								else
									daten[j][k * 3] = Integer.parseInt(cx2[0]);
								daten[j][k * 3 + 1] = Integer.parseInt(cx2[1]);
								sh++;
							}
							else
							{
								daten[j][k * 3] = daten[all[k]][k * 3];
								daten[j][k * 3 + 1] = daten[all[k]][k * 3 + 1] + j - all[k];
								daten[j][k * 3 + 2] = daten[all[k]][k * 3 + 2];
							}
						}
					XFarbe xfall = null;
					for(int j = 0; j < seitig; j++)
					{
						LadeF2 l;
						if(xfall != null)
							l = new LadeF2(xfall, seite);
						else if(x2[sh].equals("NICHT"))
						{
							sh++;
							continue;
						}
						else if(x2[sh].endsWith("-ALL"))
						{
							xfall = XFarbe.t2xf(x2[sh].substring(0, x2[sh].length() - 4));
							l = new LadeF2(xfall, seite);
						}
						else
							l = new LadeF2(XFarbe.t2xf(x2[sh]), seite);
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
				else if(zeilen[i].startsWith("PYRAMIDE"))
				{
					String[] x2 = zeilen[i].split(" ");
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
							if(cx2[0].startsWith("+"))
							{
								daten[j][0] = Integer.parseInt(cx2[0].substring(1));
								daten[j][2] = 1;
							}
							else
								daten[j][0] = Integer.parseInt(cx2[0]);
							daten[j][1] = Integer.parseInt(cx2[1]);
							sh++;
						}
						else
						{
							daten[j][0] = daten[all][0];
							daten[j][1] = daten[all][1] + j - all;
							daten[j][2] = daten[all][2];
						}
					}
					XFarbe xfall = null;
					for(int j = 0; j < seitig; j++)
					{
						LadeF2 l;
						if(xfall != null)
							l = new LadeF2(xfall, seite);
						else if(x2[sh].equals("NICHT"))
						{
							sh++;
							continue;
						}
						else if(x2[sh].endsWith("-ALL"))
						{
							xfall = XFarbe.t2xf(x2[sh].substring(0, x2[sh].length() - 4));
							l = new LadeF2(xfall, seite);
						}
						else
							l = new LadeF2(XFarbe.t2xf(x2[sh]), seite);
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
}