package welt;

import java.util.*;
import k4.*;

public abstract class PfadG extends Generator
{
	protected int[] ws;
	protected Random r;

	public PfadG(int... ws)
	{
		this.ws = ws;
		r = new Random();
	}

	@Override
	protected DerBlock[][][][] generiere()
	{
		DerBlock[][][][] blocks = new DerBlock[ws[0]][ws[1]][ws[2]][ws[3]];
		for(int i = 0; i < blocks.length; i++)
			for(int j = 0; j < blocks[i].length; j++)
				for(int k = 0; k < blocks[i][j].length; k++)
					for(int l = 0; l < blocks[i][j][k].length; l++)
						blocks[i][j][k][l] = new DerBlock(0, r.nextInt(4));
		enden = new DerBlock[]{new DerBlock(3, 0), new DerBlock(3, 0),
				new DerBlock(3, 0), new DerBlock(0, 0),
				new DerBlock(3, 0), new DerBlock(3, 0),
				new DerBlock(3, 0), new DerBlock(3, 0)};
		endOrder = new int[]{0, 2, 1, 3};
		pfadGen(blocks);
		starts = new WBP[2];
		welt.startdrehs = new Drehung[2];
		starts[0] = new WBP(0, 0, 0, 0);
		welt.startdrehs[0] = new Drehung(Math.PI * 7 / 4, 0);
		starts[1] = new WBP(0, ws[1], 0, 0);
		welt.startdrehs[1] = new Drehung(0, 0);
		return blocks;
	}

	protected abstract void pfadGen(DerBlock[][][][] blocks);

	protected DerBlock rDreh(int typ)
	{
		return new DerBlock(typ, r.nextInt(4));
	}
}