package nonblockbox.aktion;

import falsch.*;
import k.*;
import modell.*;
import nonBlock.*;

import java.util.*;

public abstract class NBD extends NonBlock
{
	public Aktion[] resLink;
	public final ArrayList<Aktion> aktionen;
	public K4 bewegung;
	public AlternateStandard standard = null;
	public Aktion currentTrans = null;

	protected NBD()
	{
		super();
		bewegung = new K4();
		aktionen = new ArrayList<>();
	}

	public void init()
	{
		resLink = new Aktion[linkAchsen.length];
	}

	public void tick()
	{
		super.tick();
		for(int i = 0; i < aktionen.size(); i++)
		{
			aktionen.get(i).tick();
			if(aktionen.get(i).dauer >= 0)
			{
				aktionen.get(i).aktuell++;
				if(aktionen.get(i).aktuell > aktionen.get(i).dauer)
				{
					aktionen.get(i).delink();
					aktionen.remove(i);
					i--;
				}
			}
		}

		if(WeltND.nfr || UIVerbunden.godMode)
			kontrolle();
		mTick();
	}

	protected void mTick()
	{
		position = K4.plus(position, bewegung);
	}

	protected abstract void kontrolle();

	public NBD plzDislocate(String info)
	{
		return this;
	}
}