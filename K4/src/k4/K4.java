package k4;

public class K4
{
	public double a;
	public double b;
	public double c;
	public double d;

	public K4(){}

	public K4(double a, double b, double c, double d)
	{
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	public K4(K4 k4)
	{
		a = k4.a;
		b = k4.b;
		c = k4.c;
		d = k4.d;
	}

	public static K4 plus(K4 start, K4 end)
	{
		return new K4(end.a + start.a, end.b + start.b, end.c + start.c, end.d + start.d);
	}

	public static K4 diff(K4 start, K4 end)
	{
		return new K4(end.a - start.a, end.b - start.b, end.c - start.c, end.d - start.d);
	}

	public static K4 mult(K4 start, double mul)
	{
		return new K4(start.a * mul, start.b * mul, start.c * mul, start.d * mul);
	}

	public static K4 div(K4 start, double div)
	{
		return new K4(start.a / div, start.b / div, start.c / div, start.d / div);
	}

	public static K4 part(K4 start, K4 end, double l)
	{
		return new K4(end.a * l + start.a * (1 - l), end.b * l + start.b * (1 - l),
				end.c * l + start.c * (1 - l), end.d * l + start.d * (1 - l));
	}

	public static double len(K4 l)
	{
		return Math.sqrt(l.a * l.a + l.b * l.b + l.c * l.c + l.d * l.d);
	}

	/*void transformWL(double wl)
	{
		if(wl == 0)
			return;
		double cwl = Math.cos(wl);
		double swl = Math.sin(wl);
		double c1 = c;
		c = c1 * cwl + a * swl;
		a = a * cwl - c1 * swl;
	}

	void transformWB(double wb)
	{
		if(wb == 0)
			return;
		double cwb = Math.cos(wb);
		double swb = Math.sin(wb);
		double b1 = b;
		b = b1 * cwb + c * swb;
		c = c * cwb - b1 * swb;
	}*/

	public void transformWS(double ws)
	{
		if(ws == 0)
			return;
		double cws = Math.cos(ws);
		double sws = Math.sin(ws);
		double a1 = a;
		a = a1 * cws + b * sws;
		b = b * cws - a1 * sws;
	}

	public void transformWBL(Drehung w)
	{
		if(w.wb == 0)
		{
			if(w.wl == 0)
				return;
			double cwl = Math.cos(w.wl);
			double swl = Math.sin(w.wl);
			double c1 = c;
			double a1 = a;
			c = c1 * cwl + a1 * swl;
			a = a1 * cwl - c1 * swl;
			return;
		}
		if(w.wl == 0)
		{
			double cwb = Math.cos(w.wb);
			double swb = Math.sin(w.wb);
			double b1 = b;
			double c1 = c;
			b = b1 * cwb + c1 * swb;
			c = c1 * cwb - b1 * swb;
			return;
		}
		double cwb = Math.cos(w.wb);
		double cwl = Math.cos(w.wl);
		double swb = Math.sin(w.wb);
		double swl = Math.sin(w.wl);
		double b1 = b;
		b = b1 * cwb + c * swb;
		double c1 = c * cwb - b1 * swb;
		c = c1 * cwl + a * swl;
		a = a * cwl - c1 * swl;
	}

	public void transformKLB(Drehung w)
	{
		if(w.wl == 0)
		{
			if(w.wb == 0)
				return;
			double cwb = Math.cos(-w.wb);
			double swb = Math.sin(-w.wb);
			double b1 = b;
			b = b1 * cwb + c * swb;
			c = c * cwb - b1 * swb;
			return;
		}
		if(w.wb == 0)
		{
			double cwl = Math.cos(-w.wl);
			double swl = Math.sin(-w.wl);
			double c1 = c;
			c = c1 * cwl + a * swl;
			a = a * cwl - c1 * swl;
			return;
		}
		double cwb = Math.cos(-w.wb);
		double cwl = Math.cos(-w.wl);
		double swb = Math.sin(-w.wb);
		double swl = Math.sin(-w.wl);
		double a1 = a;
		a = a1 * cwl - c * swl;
		double c1 = c * cwl + a1 * swl;
		c = c1 * cwb - b * swb;
		b = b * cwb + c1 * swb;
	}

	public String toString()
	{
		return a + " " + b + " " + c + " " + d;
	}
}