package welt;

public class DerBlock
{
	public int typ, dreh4;

	public DerBlock(int typ, int dreh4)
	{
		this.typ = typ;
		this.dreh4 = dreh4;
	}

	public DerBlock(String s)
	{
		String[] c1 = s.split("-");
		typ = Integer.parseInt(c1[0]);
		if(c1.length > 1)
			dreh4 = Integer.parseInt(c1[1]);
	}

	public String name()
	{
		return String.valueOf(typ);
	}

	public boolean collideOpaque()
	{
		return typ != 0;
	}

	public boolean sichtOpaque()
	{
		return typ > 0;
	}

	public boolean vKanten()
	{
		return typ == 2;
	}

	public int toBitfield()
	{
		int bitfield = collideOpaque() ? 2 : 1;
		if(vKanten())
			bitfield += 4;
		return bitfield;
	}

	public String toString()
	{
		return typ + "-" + dreh4;
	}
}