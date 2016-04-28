package ansicht;

public class TnZuordnung
{
	long tn;
	int wI;
	int hI;
	boolean set;
	int minX;
	int maxX;
	int minY;
	int maxY;

	public TnZuordnung(long tn)
	{
		this.tn = tn;
	}

	public TnZuordnung(long tn, int wI, int hI)
	{
		this.tn = tn;
		this.wI = wI;
		this.hI = hI;
	}

	public void actBounds(int x, int y)
	{
		if(set)
		{
			if(x < minX)
				minX = x < 0 ? 0 : x;
			if(y < minY)
				minY = y < 0 ? 0 : y;
			if(x > maxX)
				maxX = x >= wI ? wI - 1 : x;
			if(y > maxY)
				maxY = y >= hI ? hI - 1 : y;
		}
		else
		{
			minX = x;
			minY = y;
			maxX = x;
			maxY = y;
			set = true;
		}
	}
}