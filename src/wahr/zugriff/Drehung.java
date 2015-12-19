package wahr.zugriff;

public class Drehung
{
	public double wl;
	public double wb;

	public Drehung(){}

	public Drehung(double wl, double wb)
	{
		this.wl = wl;
		this.wb = wb;
		sichern();
	}

	public Drehung(Drehung d)
	{
		wl = d.wl;
		wb = d.wb;
	}

	public void sichern()
	{
		wl = sichern(wl);
		wb = sichern(wb);
	}

	public static Drehung plus(Drehung d1, Drehung d2)
	{
		return new Drehung(plus(d1.wl, d2.wl), plus(d1.wb, d2.wb));
	}

	public static Drehung nplus(Drehung d1, Drehung d2)
	{
		return new Drehung(d1.wl + d2.wl, d1.wb + d2.wb);
	}

	public static double plus(double d1, double d2)
	{
		double d3 = d1 + d2;
		if(d3 >= Math.PI * 2)
			return d3 - Math.PI * 2;
		return sichern(d3);
	}

	public static double sichern(double winkel)
	{
		while(winkel >= Math.PI * 2)
			winkel -= Math.PI * 2;
		while(winkel < 0)
			winkel += Math.PI * 2;
		return winkel;
	}

	public static Drehung nDrehung(double wl, double wb)
	{
		Drehung d = new Drehung();
		d.wl = wl;
		d.wb = wb;
		return d;
	}
}