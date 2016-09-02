package welt;

public class PfadG1 extends PfadG
{
	public PfadG1(int... ws)
	{
		super(ws);
	}

	@Override
	protected void pfadGen(DerBlock[][][][] blocks)
	{
		int[][] flachU;
		int[][] flachO;
		int x = 0;
		int z = 0;
		int lastd = 0;
		for(int hi = 1; hi < ws[1]; hi += 2)
		{
			flachU = new int[ws[0]][ws[2]];
			flachO = new int[ws[0]][ws[2]];
			//ArrayList<Integer> wahl = new ArrayList<>();
			x = r.nextInt(ws[0]);
			z = r.nextInt(ws[2]);
			flachU[x][z] = 2;
			flachO[x][z] = 0;
			if(x > 0)
				flachO[x - 1][z] = 1;
			if(x < ws[0] - 1)
				flachO[x + 1][z] = 1;
			if(z > 0)
				flachO[x][z - 1] = 1;
			if(z < ws[2] - 1)
				flachO[x][z + 1] = 1;
			for(int i = 0; i < blocks.length; i++)
				for(int k = 0; k < blocks[i][hi].length; k++)
				{
					blocks[i][hi - 1][k][0] = rDreh(flachU[i][k] == 0 ? 0 : 2);
					blocks[i][hi][k][0] = rDreh(flachO[i][k] == 0 ? 1 : 0);
				}
		}
	}
}