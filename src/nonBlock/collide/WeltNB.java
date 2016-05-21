package nonBlock.collide;

import wahr.zugriff.*;

import java.util.*;

public class WeltNB
{
	public final ArrayList<NBB> vta = new ArrayList<>();
	public final ArrayList<Collider> colliders = new ArrayList<>();
	private final AllWelt aw;

	public WeltNB(AllWelt aw)
	{
		this.aw = aw;
	}

	public void timetick()
	{
		if(aw.dw.nofreeze())
		{
			for(int i = 0; i < vta.size(); i++)
				for(int j = 0; j < vta.get(i).cTime.size(); j++)
					vta.get(i).cTime.set(j, vta.get(i).cTime.get(j));
			for(int i = 0; i < colliders.size(); i++)
				colliders.get(i).reset();
			Staticf.sca("WeltNB Reset (0) ");
			for(int i = 0; i < colliders.size(); i++)
				for(int j = 0; j < colliders.get(i).h.size(); j++)
					if(colliders.get(i).h.get(j).dauerNoch > 0)
					colliders.get(i).h.get(j).bereit();
			for(int i = 0; i < vta.size(); i++)
				for(int j = 0; j < vta.get(i).collidable.size(); j++)
					vta.get(i).collidable.get(j).bereit();
			for(int i = 0; i < colliders.size(); i++)
				for(int j = 0; j < vta.size(); j++)
					if(vta.get(j).nht == null || colliders.get(i).nht != vta.get(j).nht)
						for(int k = 0; k < colliders.get(i).h.size(); k++)
							if(colliders.get(i).h.get(k).dauerNoch > 0)
								for(int l = 0; l < vta.get(j).collidable.size(); l++)
									if(!colliders.get(i).h.get(k).connected.contains(vta.get(j).collidable.get(l)))
										if(colliders.get(i).h.get(k).collide(vta.get(j).collidable.get(l)))
											colliders.get(i).h.get(k).connected.add(vta.get(j).collidable.get(l));
			Staticf.sca("WeltNB Collide (0) ");
			for(int i = 0; i < colliders.size(); i++)
				colliders.get(i).resolve();
			Staticf.sca("WeltNB Resolve (0) ");
		}
		for(int i = 0; i < vta.size(); i++)
			for(int j = 0; j < vta.get(i).physik.size(); j++)
				vta.get(i).physik.get(j).bereit();
	}
}