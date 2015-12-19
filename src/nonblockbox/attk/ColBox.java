package nonblockbox.attk;

import k.*;

public class ColBox
{
	NBB besitzer;
	int linkN;
	double rad1;
	double rad2;
	double lnm;
	double connectibility;

	public ColBox(NBB besitzer, int linkN, double rad1, double rad2, double lnm, double connectibility)
	{
		this.besitzer = besitzer;
		this.linkN = linkN;
		this.rad1 = rad1;
		this.rad2 = rad2;
		this.lnm = lnm;
		this.connectibility = connectibility;
	}

	public ColBox(NBB besitzer, int linkN, double rad1, double rad2, double lnm)
	{
		this.besitzer = besitzer;
		this.linkN = linkN;
		this.rad1 = rad1;
		this.rad2 = rad2;
		this.lnm = lnm;
		connectibility = 0;
	}

	ColBox(){}

	K4 cu;
	double rcu;
	K4 co;
	double rco;

	public void bereit()
	{
		boolean cwn = besitzer.achsen[linkN].tStart.b < besitzer.achsen[linkN].tEnd.b;
		K4 c1 = besitzer.achsen[linkN].tStart;
		K4 c2 = besitzer.achsen[linkN].tEnd;
		K4 ce = new K4(c1.a + (c2.a - c1.a) * lnm, c1.b + (c2.b - c1.b) * lnm,
				c1.c + (c2.c - c1.c) * lnm, c1.d + (c2.d - c1.d) * lnm);
		if(cwn)
		{
			cu = c1;
			co = ce;
			rcu = rad1;
			rco = rad2;
		}
		else
		{
			co = c1;
			cu = ce;
			rco = rad1;
			rcu = rad2;
		}

	}

	public Double innen(K4 k)
	{
		if(cu.b > k.b || co.b < k.b)
			return null;
		if(cu.b == k.b)
			return checkAtB2(cu, rcu, k);
		if(co.b == k.b)
			return checkAtB2(co, rco, k);
		K4 in = new K4();
		double rm = neueScheibe(cu, rcu, co, rco, k.b, in);
		return checkAtB2(in, rm, k);
	}

	static boolean checkAtB(K4 t1, double r1, K4 t2, double r2)
	{
		double a = t1.a - t2.a;
		double c = t1.c - t2.c;
		double d = t1.d - t2.d;
		double lq = a * a + c * c + d * d;
		double r = r1 + r2;
		return r * r >= lq;
	}

	private static double checkAtB2(K4 t1, double r1, K4 t2)
	{
		double a = t1.a - t2.a;
		double c = t1.c - t2.c;
		double d = t1.d - t2.d;
		double lq = a * a + c * c + d * d;
		return Math.sqrt(lq) - r1;
	}

	static double neueScheibe(K4 u, double ru, K4 o, double ro, double b, K4 in)
	{
		double mu = o.b - b;
		double mo = b - u.b;
		double div = o.b - u.b;
		in.a = (u.a * mu + o.a * mo) / div;
		in.b = b;
		in.c = (u.c * mu + o.c * mo) / div;
		in.d = (u.d * mu + o.d * mo) / div;
		return (ru * mu + ro * mo) / div;
	}
}