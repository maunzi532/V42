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

	public boolean opaque()
	{
		return typ > 0;
	}

	boolean vKanten()
	{
		return typ == 2;
	}


	public String toString()
	{
		return typ + "-" + dreh4;
	}
}