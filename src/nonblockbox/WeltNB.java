package nonblockbox;

import falsch.*;
import nonblockbox.aktion.*;
import nonblockbox.attk.*;

import java.util.*;

public class WeltNB
{
	public static final ArrayList<NBB> vta = new ArrayList<>();
	public static final ArrayList<Attk> attks = new ArrayList<>();

	public static void timetick()
	{
		if(WeltND.nfr && !UIVerbunden.godMode)
		{
			for(int i = 0; i < vta.size(); i++)
				for(int j = 0; j < vta.get(i).cTime.size(); j++)
					vta.get(i).cTime.set(j, vta.get(i).cTime.get(j));
			for(int i = 0; i < attks.size(); i++)
				attks.get(i).reset();
			Staticf.sca("TR ");
			for(int i = 0; i < attks.size(); i++)
				for(int j = 0; j < attks.get(i).h.size(); j++)
					if(attks.get(i).h.get(j).dauerNoch > 0)
					attks.get(i).h.get(j).bereit();
			for(int i = 0; i < vta.size(); i++)
				for(int j = 0; j < vta.get(i).collidable.size(); j++)
					vta.get(i).collidable.get(j).bereit();
			for(int i = 0; i < attks.size(); i++)
				for(int j = 0; j < vta.size(); j++)
					if(vta.get(j).nht == null || attks.get(i).nht != vta.get(j).nht)
						for(int k = 0; k < attks.get(i).h.size(); k++)
							if(attks.get(i).h.get(k).dauerNoch > 0)
								for(int l = 0; l < vta.get(j).collidable.size(); l++)
									if(!attks.get(i).h.get(k).connected.contains(vta.get(j).collidable.get(l)))
										if(attks.get(i).h.get(k).collide(vta.get(j).collidable.get(l)))
											attks.get(i).h.get(k).connected.add(vta.get(j).collidable.get(l));
			Staticf.sca("TC ");
			for(int i = 0; i < attks.size(); i++)
				attks.get(i).resolve();
			Staticf.sca("TR1 ");
		}
		for(int i = 0; i < vta.size(); i++)
			for(int j = 0; j < vta.get(i).physik.size(); j++)
				vta.get(i).physik.get(j).bereit();
	}
}