package nonBlock.aktion;

import block.*;
import nonBlock.collide.*;
import wahr.physisch.*;
import wahr.zugriff.*;

import java.util.*;

public class ZP4C extends Aktion
{
	private double zd;
	private boolean activated;
	public boolean z;
	public boolean p;
	public double d;
	Forced forced;

	public ZP4C(NBB besitzer, int power)
	{
		super(besitzer, -1, power);
		forced = new Forced(new boolean[]{false, false, false, true}, new K4(), 1);
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
			double wd = Koord.wt(wbp).d - Koord.weltBlock.d / 2;
			int dif = (int)((b.position.d - wd) / Koord.weltBlock.d * 4);
			switch(dif)
			{
				case 0:
					zd = Koord.wt2(wbp).d;
					break;
				case 1:
				case 2:
					wbp.k[3]++;
					if(WeltB.opaque(WeltB.gib(wbp)))
						wbp.k[3]--;
					zd = Koord.wt2(wbp).d - Koord.weltBlock.d / 2;
					break;
				case 3:
					wbp.k[3]++;
					if(WeltB.opaque(WeltB.gib(wbp)))
						wbp.k[3]--;
					zd = Koord.wt2(wbp).d;
					break;
			}
			activated = true;
		}
		if(TA2.keyStat[8] == 2) //P
		{
			WBP wbp = Koord.tw(b.position);
			double wd = Koord.wt(wbp).d - Koord.weltBlock.d / 2;
			int dif = (int)((b.position.d - wd) / Koord.weltBlock.d * 4);
			switch(dif)
			{
				case 0:
					wbp.k[3]--;
					if(WeltB.opaque(WeltB.gib(wbp)))
						wbp.k[3]++;
					zd = Koord.wt2(wbp).d;
					break;
				case 1:
				case 2:
					wbp.k[3]--;
					if(WeltB.opaque(WeltB.gib(wbp)))
						wbp.k[3]++;
					zd = Koord.wt2(wbp).d + Koord.weltBlock.d / 2;
					break;
				case 3:
					zd = Koord.wt2(wbp).d;
					break;
			}
			activated = true;
		}
		if(activated)
		{
			if(Math.abs(zd - b.position.d) < Staticf.zpSpeed)
			{
				forced.movement.d = zd - b.position.d;
				activated = false;
			}
			else if(zd > b.position.d)
				forced.movement.d = Staticf.zpSpeed;
			else
				forced.movement.d = -Staticf.zpSpeed;
		}
		else
			forced.movement.d = 0;
		b.forced.add(forced);
		if(!geht(b.block, forced.movement.d, false))
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
