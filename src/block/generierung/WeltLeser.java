package block.generierung;

import fnd.*;

public class WeltLeser extends Generator
{
	public String ort;
	private int[] size;

	int[][][][] generiere()
	{
		int[][][][] blocks = null;
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
					String[] w44 = w3[1].split(",");
					int c0 = 0;
					int c1 = 0;
					int c2 = 0;
					int c3 = 0;
					for(int i = 0; i < w44.length; i++)
					{
						assert blocks != null;
						blocks[c0][c1][c2][c3] = ladeBlock(w44[i]);
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
					break;
			}
		}
		starts = new int[][]{{size[0] / 2 - 1, size[1] / 2 + 1, size[2] / 2, size[3] / 2},
				{size[0] / 2 - 1, size[1] / 2 + 1, size[2] / 2 + 1, size[3] / 2}};
		return blocks;
	}

	private int ladeBlock(String s)
	{
		return Integer.parseInt(s);
	}
}