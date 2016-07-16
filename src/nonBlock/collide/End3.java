package nonBlock.collide;

import k4.*;

public interface End3
{
	double radiusHier(K4 thiz, K4 other, double wl);

	static End3 text(String text)
	{
		if(text.startsWith("EL"))
		{
			String[] te2 = text.split("-");
			return new EndEllipse(Double.parseDouble(te2[1]), Double.parseDouble(te2[2]),
					Double.parseDouble(te2[3]));
		}
		return new EndScheibe(Double.parseDouble(text));
	}
}