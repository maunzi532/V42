package block.generierung;

import java.util.*;

class LabG extends Generator
{
	private static final int[][] x = new int[][]
			{
					{-1, 0, 0, 0},
					{1, 0, 0, 0},
					{0, -1, 0, 0},
					{0, 1, 0, 0},
					{0, 0, -1, 0},
					{0, 0, 1, 0},
					{0, 0, 0, -1},
					{0, 0, 0, 1},
			};

	private int[][][][] b1;
	private int[] size;
	private int[] position;

	public int[][][][] generiere()
	{
		int dim = 4;
		int sa = 4;
		size = new int[]{sa, dim > 2 ? sa : 1, sa, dim > 3 ? sa : 1};
		Random r = new Random();
		b1 = new int[size[0]][size[1]][size[2]][size[3]];
		starts = new int[2][4];
		for(int i = 0; i < 4; i++)
			starts[0][i] = r.nextInt(size[i]);
		position = starts[0].clone();
		boolean[] paths = new boolean[8];
		ArrayList<Integer> pathr = new ArrayList<>();
		boolean canHoch = true;
		set(2);
		while(true)
		{
			pathr.clear();
			int pathc = 0;
			for(int i = 0; i < paths.length; i++)
			{
				boolean b = b3(i);
				if((i == 3 || i >= 6) && !canHoch)
					b = false;
				paths[i] = b;
				if(b)
				{
					pathc++;
					pathr.add(i);
					set(i, 1);
				}
			}
			if(pathc == 0)
			{
				starts[1] = position;
				break;
			}
			int ra = pathr.get(r.nextInt(pathr.size()));
			canHoch = ra != 3;
			int[] last = position.clone();
			for(int i = 0; i < 4; i++)
				position[i] += x[ra][i];
			set(2);
		}
		int[][][][] blocks = new int[size[0]][size[1]][size[2]][size[3]];
		for(int ia = 0; ia < size[0]; ia++)
			for(int ib = 0; ib < size[1]; ib++)
				for(int ic = 0; ic < size[2]; ic++)
					for(int id = 0; id < size[3]; id++)
						blocks[ia][ib][ic][id] = b1[ia][ib][ic][id] == 2 ? 0 : 1;
		enden = new int[]{1, 1, 1, 1, 1, 1, 1, 1};
		//enden = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		//enden = new int[]{1, 1, 1, 0, 1, 1, 1, 1};
		endOrder = new int[]{0, 2, 1, 3};
		return blocks;
	}

	private int b2(int no)
	{
		return b1[position[0] + x[no][0]][position[1] + x[no][1]]
				[position[2] + x[no][2]][position[3] + x[no][3]];
	}

	private boolean b3(int no)
	{
		return !(no % 2 == 0 && position[no / 2] <= 0) &&
				!(no % 2 != 0 && position[no / 2] >= size[no / 2] - 1) && b2(no) == 0;
	}

	private void set(int no, int set)
	{
		if(!(no % 2 == 0 && position[no / 2] <= 0) &&
				!(no % 2 != 0 && position[no / 2] >= size[no / 2] - 1))
			b1[position[0] + x[no][0]][position[1] + x[no][1]]
					[position[2] + x[no][2]][position[3] + x[no][3]] = set;
	}

	private void set(int set)
	{
		b1[position[0]][position[1]][position[2]][position[3]] = set;
	}
}