package welt;

import java.util.*;
import k4.*;

public class TestGenerator extends Generator
{
	private Random r;

	DerBlock[][][][] generiere()
	{
		r = new Random();
		DerBlock[][][][] blocks = new DerBlock[40][40][40][3];
		for(int i = 0; i < blocks.length; i++)
			for(int j = 0; j < blocks[i].length; j++)
				for(int k = 0; k < blocks[i][j].length; k++)
					for(int l = 0; l < blocks[i][j][k].length; l++)
						if(r.nextInt(blocks[i].length) > j)
							blocks[i][j][k][l] = rDreh(1);
						else
							blocks[i][j][k][l] = rDreh(0);
		blocks[19][20][21][1] = rDreh(2);
		blocks[19][20][20][1] = rDreh(2);
		blocks[19][21][21][1] = rDreh(0);
		blocks[19][21][20][1] = rDreh(0);
		blocks[19][22][21][1] = rDreh(0);
		blocks[19][22][20][1] = rDreh(0);
		enden = new DerBlock[]{rDreh(1), rDreh(1), rDreh(1), rDreh(0),
				rDreh(1), rDreh(1), rDreh(0), rDreh(0)};
		endOrder = new int[]{0, 2, 1, 3};
		starts = new WBP[2];
		welt.startdrehs = new Drehung[2];
		starts[0] = new WBP(19, 21, 20, 1);
		welt.startdrehs[0] = new Drehung(1, 0);
		starts[1] = new WBP(19, 21, 21, 1);
		welt.startdrehs[1] = new Drehung(Math.PI, 0);
		return blocks;
	}

	private DerBlock rDreh(int typ)
	{
		return new DerBlock(typ, r.nextInt(4));
	}
}