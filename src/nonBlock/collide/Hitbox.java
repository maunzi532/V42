package nonBlock.collide;

import wahr.zugriff.*;

import java.util.*;

public class Hitbox extends ColBox
{
	public ArrayList<ColBox> connected;
	public int dauerNoch;
	final int dauer;

	public Hitbox(Collider von, int linkN, End3 rad1, End3 rad2, double lnm, int time, double connectibility)
	{
		super(von.besitzer, linkN, rad1, rad2, lnm, connectibility);
		connected = new ArrayList<>();
		dauer = time;
		dauerNoch = dauer;
	}

	public Hitbox(String s)
	{
		super();
		String[] sa = s.split(",");
		linkN = Integer.parseInt(sa[0]);
		rad1 = End3.text(sa[1]);
		rad2 = End3.text(sa[2]);
		lnm = Double.parseDouble(sa[3]);
		connected = new ArrayList<>();
		dauer = Integer.parseInt(sa[4]);
		dauerNoch = dauer;
		connectibility = Double.parseDouble(sa[5]);
	}

	public boolean collide(ColBox col)
	{
		double cu1b = cu.b >= col.cu.b ? cu.b : col.cu.b;
		double co1b = co.b <= col.co.b ? co.b : col.co.b;
		if(cu1b > co1b)
			return false;
		boolean su = false;
		if(Math.abs(cu.b - col.cu.b) < Staticf.ebhc)
		{
			su = true;
			if(checkAtB(cu, rcu, wl, col.cu, col.rcu, col.wl))
				return true;
		}
		boolean cuX = cu.b >= col.cu.b;
		K4 cu1 = cuX ? cu : col.cu;
		boolean so = false;
		if(Math.abs(co.b - col.co.b) < Staticf.ebhc)
		{
			so = true;
			if(checkAtB(co, rco, wl, col.co, col.rco, col.wl))
				return true;
		}
		boolean coX = co.b <= col.co.b;
		K4 co1 = coX ? co : col.co;
		if(co1.b - cu1.b < Staticf.ebhc)
		{
			if(su && so)
				return false;
			if(su ? !coX : cuX)
				return checkAtB(cu, rcu, wl, col.co, col.rco, col.wl);
			else
				return checkAtB(co, rco, wl, col.cu, col.rcu, col.wl);
		}
		if(!su)
		{
			if(cuX)
			{
				K4 s2 = new K4();
				End3 r2 = neueScheibe(col.cu, col.rcu, col.co, col.rco, cu1.b, s2, cu1, col.wl);
				if(checkAtB(cu, rcu, wl, s2, r2, col.wl))
					return true;
			}
			else
			{
				K4 s2 = new K4();
				End3 r2 = neueScheibe(cu, rcu, co, rco, cu1.b, s2, cu1, wl);
				if(checkAtB(s2, r2, wl, col.cu, col.rcu, col.wl))
					return true;
			}
		}
		if(!so)
		{
			if(coX)
			{
				K4 s2 = new K4();
				End3 r2 = neueScheibe(col.cu, col.rcu, col.co, col.rco, co1.b, s2, co1, col.wl);
				if(checkAtB(co, rco, wl, s2, r2, col.wl))
					return true;
			}
			else
			{
				K4 s2 = new K4();
				End3 r2 = neueScheibe(cu, rcu, co, rco, co1.b, s2, co1, wl);
				if(checkAtB(s2, r2, wl, col.co, col.rco, col.wl))
					return true;
			}
		}
		if(co1.b - cu1.b > Staticf.abhc)
			for(double i = 1; cu1.b + i * Staticf.abhc < co1.b; i++)
			{
				K4 s1 = new K4();
				K4 s2 = new K4();
				End3 r1 = neueScheibe(cu, rcu, co, rco, cu1.b + i * Staticf.abhc, s1, cu1, wl);
				End3 r2 = neueScheibe(col.cu, col.rcu, col.co, col.rco, cu1.b + i * Staticf.abhc, s2, co1, col.wl);
				if(checkAtB(s1, r1, wl, s2, r2, col.wl))
					return true;
			}
		return false;
	}
}