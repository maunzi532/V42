package nonBlock.aktion.lesen;

import k4.*;
import nonBlock.aktion.*;

class RWert
{
	private String name;
	private double zahl;

	public RWert(String text)
	{
		if((text.charAt(0) >= 48 && text.charAt(0) < 58) || text.charAt(0) == '-')
			zahl = Double.parseDouble(text);
		else
			name = text;
	}

	public RWert(double zahl)
	{
		this.zahl = zahl;
	}

	public double gib(AkA v1, K4 target)
	{
		if(name != null)
			switch(name)
			{
				case "sinwl":
					return Math.sin(v1.dreh().wl);
				case "coswl":
					return Math.cos(v1.dreh().wl);
				case "ba":
					return target.a - v1.position().a;
				case "bb":
					return target.b - v1.position().b;
				case "bc":
					return target.c - v1.position().c;
				case "bd":
					return target.d - v1.position().d;
				case "bv":
					return 0;
				case "bs":
					return 0;
				default:
					assert false;
			}
		return zahl;
	}
}