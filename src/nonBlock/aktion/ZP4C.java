package nonBlock.aktion;

import block.*;
import nonBlock.collide.*;
import wahr.physisch.*;

import java.util.*;

public class ZP4C extends Aktion
{
	private double zd;
	private boolean activated;
	public boolean z;
	public boolean p;
	public double d;

	public ZP4C(NBB besitzer, int power)
	{
		super(besitzer, -1, power);
	}

	public void tick()
	{
		NBB b = (NBB) besitzer;
		d = b.position.d;
		z = geht(b.block, Koord.weltBlock.d, false);
		p = geht(b.block, Koord.weltBlock.d, true);
		if(TA2.keyStat[7] == 2) //Z
		{
			WBP wbp = Koord.tw(b.position);
			wbp.k[3]++;
			if(WeltB.opaque(WeltB.gib(wbp)))
				wbp.k[3]--;
			zd = Koord.wt2(wbp).d;
			activated = true;
		}
		if(TA2.keyStat[8] == 2) //P
		{
			WBP wbp = Koord.tw(b.position);
			wbp.k[3]--;
			if(WeltB.opaque(WeltB.gib(wbp)))
				wbp.k[3]++;
			zd = Koord.wt2(wbp).d;
			activated = true;
		}
		if(activated)
		{
			if(Math.abs(zd - b.position.d) < 1)
			{
				b.bewegung.d = zd - b.position.d;
				activated = false;
			}
			else if(zd > b.position.d)
				b.bewegung.d = 1;
			else
				b.bewegung.d = -1;
		}
		else
			b.bewegung.d = 0;
		if(!geht(b.block, b.bewegung.d, false))
			activated = false;
	}

	private static boolean geht(ArrayList<BlockBox> b, double m, boolean min)
	{
		for(int i = 0; i < b.size(); i++)
			if(!b.get(i).checkOnly(new double[]{0, 0, 0,
					(m - Math.abs(b.get(i).ee[3] - b.get(i).se[3])) * (min ? -1 : 1)}))
				return false;
		return true;
	}
}
