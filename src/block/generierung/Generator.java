package block.generierung;

import block.*;
import wahr.zugriff.*;

public abstract class Generator
{
	int[][] starts;
	int[] enden;
	int[] endOrder;

	abstract int[][][][] generiere();

	public void gibInWelt(Object... z)
	{
		WeltB.blocks = generiere();
		WeltB.enden = enden;
		WeltB.endOrder = endOrder;
	}

	public void ermittleStart()
	{
		WeltB.starts = new K4[starts.length];
		for(int i = 0; i < starts.length; i++)
			WeltB.starts[i] = Koord.N2Start(starts[i]);
	}
}