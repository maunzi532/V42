package nonBlock.collide;

import wahr.zugriff.*;

public class EndEllipse implements End3
{
	double ra;
	double rc;
	double rd;

	public EndEllipse(double ra, double rc, double rd)
	{
		this.ra = ra;
		this.rc = rc;
		this.rd = rd;
	}

	public double radiusHier(K4 thiz, K4 other, double wl)
	{
		K4 diff = K4.diff(thiz, other);
		double w = Math.PI;
		if(diff.a != 0)
			w = Math.atan(diff.c / diff.a) + wl;
		double ca = Math.abs(Math.cos(w));
		double sa = Math.abs(Math.sin(w));
		return (ra * ca + rc * sa) / (ca + sa);
	}
}