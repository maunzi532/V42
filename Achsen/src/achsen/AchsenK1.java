package achsen;

import java.util.*;
import k4.*;

public class AchsenK1 implements IKamera
{
	private static int tnm;

	public String nameLook;
	Standard1 achsen;
	Alternate1 alternate;
	public List<AnzTeil1> plys;
	public TnTarget tNum;
	public ADreh1[] drehs;
	ArrayList<K4>[] entlinkt;
	Drehung[] drehA;
	ArrayList<K4>[] punkteK;
	public K4 position;
	public Drehung dreh;
	LinkBlocker[] resLink;

	public AchsenK1(K4 position, Drehung dreh, boolean save, String name, String altName, String... teilNamen)
	{
		tnm--;
		tNum = new TnTarget(tnm, 0);
		this.position = position;
		this.dreh = dreh;
		resLink = new LinkBlocker[0];
		nameLook = name;
		reload(save, name, altName, teilNamen);
	}

	public AchsenK1(K4 position, Drehung dreh, String name)
	{
		tnm--;
		tNum = new TnTarget(tnm, 0);
		this.position = position;
		this.dreh = dreh;
		resLink = new LinkBlocker[0];
		nameLook = name;
		reload();
	}

	public void reload(boolean save, String name, String altName, String... teilNamen)
	{
		achsen = Standard1.gibVonL4(name, save);
		alternate = Alternate1.gibVonL4(name, altName, achsen.achsen.length, save);
		drehs = new ADreh1[achsen.achsen.length];
		for(int i = 0; i < achsen.achsen.length; i++)
			drehs[i] = new ADreh1(alternate.drehungen[i]);
		plys = new ArrayList<>();
		for(String teilName : teilNamen)
			plys.add(AnzTeil1.gibVonL4(name, teilName, save));
	}

	public void reload()
	{
		achsen = new Standard1("");
		alternate = new Alternate1("", 0);
		drehs = new ADreh1[0];
		plys = new ArrayList<>();
	}

	public void reload(Standard1 sta1, Alternate1 alt1, AnzTeil1... lad1)
	{
		achsen = sta1;
		alternate = alt1;
		drehs = new ADreh1[sta1.achsen.length];
		for(int i = 0; i < sta1.achsen.length; i++)
			drehs[i] = new ADreh1(alternate.drehungen[i]);
		plys = new ArrayList<>();
		Collections.addAll(plys, lad1);
	}

	@SuppressWarnings("unchecked")
	public void reset()
	{
		int anz = achsen.achsen.length;
		entlinkt = new ArrayList[anz];
		drehA = new Drehung[anz];
		punkteK = new ArrayList[anz];
	}

	public ADreh1 achse(int nummer)
	{
		if(nummer < 0 || nummer >= drehs.length)
			throw new RuntimeException("Achse Nummer Fehler");
		if(drehA[nummer] != null)
			return drehs[nummer];
		entlinkt[nummer] = new ArrayList<>();
		if(achsen.achsen[nummer].linkedAchse < 0)
		{
			entlinkt[nummer].add(new K4(position));
			drehA[nummer] = new Drehung(dreh);
		}
		else
		{
			int nummerZ = achsen.achsen[nummer].linkedAchse;
			achse(nummerZ);
			entlinkt[nummer].add(punkt(nummerZ, 1));
			drehA[nummer] = Drehung.plus(drehA[nummerZ], drehs[nummerZ].dreh);
		}
		return drehs[nummer];
	}

	public K4 punkt(int anum, int punkt)
	{
		if(entlinkt[anum] != null && entlinkt[anum].size() > punkt && entlinkt[anum].get(punkt) != null)
			return entlinkt[anum].get(punkt);
		ADreh1 a1 = achse(anum);
		if(punkt == 0)
			return entlinkt[anum].get(0);
		K4 tp = zuPunkt(new K4(), a1, achsen.achsen[anum].punkte.get(punkt),
				entlinkt[anum].get(0), drehA[anum]);
		if(entlinkt[anum].size() > punkt)
			entlinkt[anum].set(punkt, tp);
		else
		{
			while(entlinkt[anum].size() < punkt)
				entlinkt[anum].add(null);
			entlinkt[anum].add(tp);
		}
		return entlinkt[anum].get(punkt);
	}

	public K4 punktK(int anum, int punkt, K4 relativ, Drehung kDreh)
	{
		if(punkteK[anum] != null && punkteK[anum].size() > punkt && punkteK[anum].get(punkt) != null)
			return punkteK[anum].get(punkt);
		K4 k = transformSet2(new K4(punkt(anum, punkt)), kDreh, relativ);
		if(punkteK[anum] == null)
			punkteK[anum] = new ArrayList<>();
		if(punkteK[anum].size() > punkt)
			punkteK[anum].set(punkt, k);
		else
		{
			while(punkteK[anum].size() < punkt)
				punkteK[anum].add(null);
			punkteK[anum].add(k);
		}
		return punkteK[anum].get(punkt);
	}

	@Override
	public Drehung kamD()
	{
		return dreh;
	}

	@Override
	public K4 kamP()
	{
		return position;
	}

	public static K4 zuPunkt(K4 start, ADreh1 aDreh, Punkt1 punkt, K4 mPos, Drehung mDreh)
	{
		K4 tLen = new K4(0, punkt.abstand, punkt.vor * aDreh.len, aDreh.dShift);
		tLen.transformWS(Drehung.plus(punkt.spin, aDreh.spin));
		tLen.transformWBL(aDreh.dreh);
		tLen = K4.plus(tLen, start);
		tLen.transformWBL(mDreh);
		return K4.plus(tLen, mPos);
	}

	public static K4 transformSet2(K4 thi, Drehung d, K4 diff)
	{
		thi.transformKLB(d);
		return K4.diff(diff, thi);
	}
}