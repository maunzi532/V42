package nonBlock.collide;

import k4.*;

public class EndScheibe implements End3
{
	private final double radius;

	public EndScheibe(double radius)
	{
		this.radius = radius;
	}

	public double radiusHier(K4 thiz, K4 other, double wl)
	{
		return radius;
	}
}