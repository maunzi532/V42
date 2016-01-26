package block.generierung;

import java.util.*;

class TestGenerator extends Generator
{
	int[][][][] generiere()
	{
		Random r = new Random();
		int[][][][] blocks = new int[40][40][40][3];
		for(int i = 0; i < blocks.length; i++)
			for(int j = 0; j < blocks[i].length; j++)
				for(int k = 0; k < blocks[i][j].length; k++)
					for(int l = 0; l < blocks[i][j][k].length; l++)
						if(r.nextInt(blocks[i].length) > j)
							blocks[i][j][k][l] = 1;
		blocks[19][20][21][1] = 2;
		blocks[19][20][20][1] = 2;
		blocks[19][21][21][1] = 0;
		blocks[19][21][20][1] = 0;
		blocks[19][22][21][1] = 0;
		blocks[19][22][20][1] = 0;
		starts = new int[][]{{19, 21, 20, 1},{19, 21, 21, 1}};
		enden = new int[]{1, 1, 1, 0, 1, 1, 0, 0};
		endOrder = new int[]{0, 2, 1, 3};
		return blocks;
	}
}