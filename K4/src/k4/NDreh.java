package k4;

public class NDreh
{
	public double wl;
	public double wb;

	public NDreh(){}

	public NDreh(double wl, double wb)
	{
		this.wl = wl;
		this.wb = wb;
	}

	public NDreh(NDreh d)
	{
		wl = d.wl;
		wb = d.wb;
	}

	public static NDreh plus(NDreh d1, NDreh d2)
	{
		return new NDreh(d1.wl + d2.wl, d1.wb + d2.wb);
	}

	public static double sichern(double winkel)
	{
		while(winkel >= Math.PI * 2)
			winkel -= Math.PI * 2;
		while(winkel < 0)
			winkel += Math.PI * 2;
		return winkel; //+0.01 = waguhtime
	}

	public String toString()
	{
		return "WL: " + (wl / Math.PI * 180) + " WB: " + (wb / Math.PI * 180);
	}
}