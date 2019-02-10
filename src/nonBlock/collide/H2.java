package nonBlock.collide;

import ext.*;

public class H2 extends H
{
	private final NBB main3;

	public H2(NBB main3, double w, double h, int wt, int ht, int nlen, double wwl, double hwl, double len)
	{
		super(w, h, wt, ht, nlen, wwl, hwl, len);
		this.main3 = main3;
	}

	public void tick()
	{
		for(int k = 0; k < nlen - 1; k++)
		{
			for(int i = 0; i < h2.length; i++)
				for(int j = 0; j < h2[i].length; j++)
					if(k >= 1)
					{
						double in;
						int in2 = 0;
						if(k < nlen - 2)
							do
							{
								in = Double.MAX_VALUE;
								for(int p = 0; p < main3.physik.size(); p++)
								{
									Double v = main3.physik.get(p).innen(h2a[i][j][k + 2].start);
									if(v != null)
										if(v < in)
											in = v;
								}
								if(in2 >= 3)
									break;
								if(in < -1)
									h2[i][j][k].dreh.wb -= 0.02;
								else if(in < 0)
									h2[i][j][k].dreh.wb += 0.01 * in;
								else if(in >= 1)
									h2[i][j][k].dreh.wb += 0.031;
								else if(in >= 0.2)
									h2[i][j][k].dreh.wb += 0.011 * in;
								in2++;
							}
							while(in < 0);
						in2 = 0;
						do
						{
							in = Double.MAX_VALUE;
							for(int p = 0; p < main3.physik.size(); p++)
							{
								Double v = main3.physik.get(p).innen(h2a[i][j][k + 1].start);
								if(v != null)
									if(v < in)
										in = v;
							}
							if(in2 >= 2)
								break;
							if(in < -1)
								h2[i][j][k].dreh.wb -= 0.02;
							else if(in < 0)
								h2[i][j][k].dreh.wb += 0.01 * in;
							else if(in >= 1)
								h2[i][j][k].dreh.wb += 0.031;
							else if(in >= 0.2)
								h2[i][j][k].dreh.wb += 0.011 * in;
							in2++;
						}
						while(in < 0);
						//h2[i][j][k].dreh.wl += (r.nextDouble() - 0.5) / 100;
					}
		}
	}
}