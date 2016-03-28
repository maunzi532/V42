package nonBlock.aktion.lesen;

import nonBlock.aktion.*;

public class RWert
{
	String name;
	double zahl;

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

	public double gib(NBD v1)
	{
		if(name != null)
			switch(name)
			{
				case "sinwl":
					return Math.sin(v1.dreh.wl);
				case "coswl":
					return Math.cos(v1.dreh.wl);
				default:
					assert false;
			}
		return zahl;
	}
}