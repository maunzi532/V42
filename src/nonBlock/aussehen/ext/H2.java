package nonBlock.aussehen.ext;

import nonBlock.collide.*;
import wahr.zugriff.*;

public class H2 extends H
{
	private final NBB main3;
	private Drehung mDreh;
	private K4 mPos;

	public H2(NBB main3, int axn, double w, double h, int wt, int ht, int nlen, double wwl, double hwl,
			double wwb, double hwb)
	{
		super(main3, axn, w, h, wt, ht, nlen, wwl, hwl, wwb, hwb);
		this.main3 = main3;
		for(int i = 0; i < ht; i++)
			for(int j = 0; j < wt; j++)
				for(int k = 0; k < nlen; k++)
				{
					h2[i][j].get(k).len = 0.6;
				}
	}

	public void entLink(Drehung mDreh, K4 mPos)
	{
		super.entLink(mDreh, mPos);
		this.mDreh = mDreh;
		this.mPos = mPos;
	}

	public void tick()
	{
		for(int k = 0; k < nlen - 1; k++)
		{
			entLink(mDreh, mPos);
			for(int i = 0; i < h2.length; i++)
				for(int j = 0; j < h2[i].length; j++)
					if(k >= 1)
					{
						double in;
						int in2 = 0;
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
							if(in2 >= 5)
								break;
							if(in < -1)
							{
								h2[i][j].get(k).dreh.wb -= 0.02;
							}
							else if(in < 0)
							{
								h2[i][j].get(k).dreh.wb += 0.01 * in;
							}
							else if(in >= 1)
							{
								h2[i][j].get(k).dreh.wb += 0.031;
							}
							else if(in >= 0.2)
							{
								h2[i][j].get(k).dreh.wb += 0.011 * in;
							}
							in2++;
						}
						while(in < 0);
						/*double a1 = h2a[i][j][k].start.a - inmid.a;
						double c1 = h2a[i][j][k].start.c - inmid.c;
						double ab = Math.sqrt(a1 * a1 + c1 * c1);
						double de1 = ab > 1 ? 1 / ab : 1;
						double depth = Math.PI * (1.5 + de1 * 0.5);
						if(h2a[i][j][k].dreh.wb > depth + 0.05 || h2a[i][j][k].dreh.wb < depth - Math.PI - 0.05)
							h2[i][j].get(k).dreh.wb -= 0.05;
						else if(h2a[i][j][k].dreh.wb < depth - 0.05 && h2a[i][j][k].dreh.wb > depth - Math.PI + 0.05)
							h2[i][j].get(k).dreh.wb += 0.05;*/
					}
		}
	}
}
