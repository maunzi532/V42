package nonBlock.collide;

import wahr.zugriff.*;

public class EndScheibe implements End3
{
	double radius;

	public EndScheibe(double radius)
	{
		this.radius = radius;
	}

	public double radiusHier(K4 thiz, K4 other, double wl)
	{
		return radius;
	}
}