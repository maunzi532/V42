package welt;

import k4.*;

public abstract class Generator
{
	protected WeltB welt;
	int[][] starts;
	DerBlock[] enden;
	int[] endOrder;

	abstract DerBlock[][][][] generiere();

	public void gibInWelt(WeltB welt, Object... z)
	{
		this.welt = welt;
		welt.blocks = generiere();
		welt.enden = enden;
		welt.endOrder = endOrder;
	}

	public void ermittleStart()
	{
		welt.starts = new K4[starts.length];
		for(int i = 0; i < starts.length; i++)
			welt.starts[i] = welt.N2Start(starts[i]);
	}
}