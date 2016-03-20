package nonBlock.aktion;

import block.*;
import nonBlock.collide.*;
import wahr.physisch.*;
import wahr.zugriff.*;

import java.util.*;

//Deprecated
public class ZP4C extends Aktion
{
	private double zd;
	private boolean activated;
	private Forced forced;

	public ZP4C(NBB besitzer, int power)
	{
		super(besitzer, -1, power);
		forced = new Forced(new boolean[]{false, false, false, true}, new K4(), 1);
	}

	public void tick()
	{
		NBB b = (NBB) besitzer;
		if(TA2.keyStat[0][7] == 2)
		{
			WBP wbp = b.welt.tw(b.position);
			double wd = b.welt.wt(wbp).d - b.welt.weltBlock.d / 2;
			int dif = (int)((b.position.d - wd) / b.welt.weltBlock.d * 4);
			switch(dif)
			{
				case 0:
					zd = b.welt.wt2(wbp).d;
					break;
				case 1:
				case 2:
					wbp.k[3]++;
					if(b.welt.opaque(b.welt.gib(wbp)))
						wbp.k[3]--;
					zd = b.welt.wt2(wbp).d - b.welt.weltBlock.d / 2;
					break;
				case 3:
					wbp.k[3]++;
					if(b.welt.opaque(b.welt.gib(wbp)))
						wbp.k[3]--;
					zd = b.welt.wt2(wbp).d;
					break;
			}
			activated = true;
		}
		if(TA2.keyStat[0][8] == 2)
		{
			WBP wbp = b.welt.tw(b.position);
			double wd = b.welt.wt(wbp).d - b.welt.weltBlock.d / 2;
			int dif = (int)((b.position.d - wd) / b.welt.weltBlock.d * 4);
			switch(dif)
			{
				case 0:
					wbp.k[3]--;
					if(b.welt.opaque(b.welt.gib(wbp)))
						wbp.k[3]++;
					zd = b.welt.wt2(wbp).d;
					break;
				case 1:
				case 2:
					wbp.k[3]--;
					if(b.welt.opaque(b.welt.gib(wbp)))
						wbp.k[3]++;
					zd = b.welt.wt2(wbp).d + b.welt.weltBlock.d / 2;
					break;
				case 3:
					zd = b.welt.wt2(wbp).d;
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

	private boolean geht(ArrayList<BlockBox> bb, double m, boolean min)
	{
		for(int i = 0; i < bb.size(); i++)
			if(!bb.get(i).checkOnly(new double[]{0, 0, 0,
					(m - Math.abs(bb.get(i).ee[3] - bb.get(i).se[3])) * (min ? -1 : 1)}, ((NBB)besitzer).welt))
				return false;
		return true;
	}
}
