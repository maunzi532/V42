package welt.generator;

import k4.*;
import welt.*;

public abstract class Generator
{
	protected WeltB welt;
	protected WBP[] starts;
	protected DerBlock[] enden;
	protected int[] endOrder;

	protected abstract DerBlock[][][][] generiere();

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
			welt.starts[i] = welt.wt3(starts[i]);
	}
}