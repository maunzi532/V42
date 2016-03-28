package nonBlock.aktion.lesen;

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

	public double gib(LadeAktion e)
	{
		if(name != null)
			return 0;
		return zahl;
	}
}