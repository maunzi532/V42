package block.generierung;

import block.*;
import wahr.physisch.*;

public class WeltLeser extends Generator
{
	private String ort;
	private int[] size;
	int c0 = 0;
	int c1 = 0;
	int c2 = 0;
	int c3 = 0;
	int[][][][] blocks = null;

	public void gibInWelt(WeltB welt, Object... z)
	{
		ort = (String)z[0];
		super.gibInWelt(welt);
	}

	int[][][][] generiere()
	{
		String[] w1 = Lader.gibText(ort).replace("\n	", "").split("\n");
		for(String w2 : w1)
		{
			String[] w3 = w2.split(" = ");
			switch(w3[0])
			{
				case "S":
					String[] w41 = w3[1].split(",");
					size = new int[w41.length];
					for(int i = 0; i < w41.length; i++)
						size[i] = Integer.parseInt(w41[i]);
					blocks = new int[size[0]][size[1]][size[2]][size[3]];
					break;
				case "E":
					String[] w42 = w3[1].split(",");
					enden = new int[w42.length];
					for(int i = 0; i < w42.length; i++)
						enden[i] = ladeBlock(w42[i]);
					break;
				case "O":
					String[] w43 = w3[1].split(",");
					endOrder = new int[w43.length];
					for(int i = 0; i < w43.length; i++)
						endOrder[i] = Integer.parseInt(w43[i]);
					break;
				case "B":
					assert blocks != null;
					String[] w44 = w3[1].split(",");
					for(int i = 0; i < w44.length; i++)
					{
						if(w44[i].contains("x"))
						{
							String[] w441 = w44[i].split("x");
							int m = Integer.parseInt(w441[0]);
							for(int j = 0; j < m; j++)
								nex(w441[1]);
						}
						else
							nex(w44[i]);
					}
					break;
			}
		}
		starts = new int[][]{{size[0] / 2 - 1, size[1] / 2 + 1, size[2] / 2, size[3] / 2},
				{size[0] / 2 - 1, size[1] / 2 + 1, size[2] / 2 + 1, size[3] / 2}};
		return blocks;
	}

	private void nex(String t)
	{
		blocks[c0][c1][c2][c3] = ladeBlock(t);
		c3++;
		if(c3 >= size[3])
		{
			c3 = 0;
			c2++;
			if(c2 >= size[2])
			{
				c2 = 0;
				c1++;
				if(c1 >= size[1])
				{
					c1 = 0;
					c0++;
				}
			}
		}
	}

	private int ladeBlock(String s)
	{
		return Integer.parseInt(s);
	}
}