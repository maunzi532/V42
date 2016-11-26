package nonBlock.aktion;

import ext.*;
import java.util.*;
import k4.*;
import nonBlock.aktion.lesen.*;

public abstract class NBD extends ENB implements AkA
{
	public ArrayList<Aktion> aktionen;
	public K4 bewegung;
	public ArrayList<K4> beweg;
	public ArrayList<Forced> forced;
	protected ArrayList<Move> moves;

	public WeltND dw;

	protected NBD(WeltND dw)
	{
		super();
		this.dw = dw;
		dw.nonBlocks.add(this);
		bewegung = new K4();
		beweg = new ArrayList<>();
		forced = new ArrayList<>();
		aktionen = new ArrayList<>();
		moves = new ArrayList<>();
	}

	@Override
	public void tick()
	{
		super.tick();
		for(int i = 0; i < moves.size(); i++)
			if(moves.get(i).tick())
			{
				moves.remove(i);
				i--;
			}
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
		kontrolle();
		mTick();
		position = K4.plus(position, bewegung);
	}

	@Override
	public void mTick()
	{
		bewegung = new K4();
		int[] powers = new int[4];
		for(int i = 0; i < beweg.size(); i++)
			bewegung = K4.plus(bewegung, beweg.get(i));
		for(int i = 0; i < forced.size(); i++)
			forced.get(i).affect(bewegung, powers);
		beweg.clear();
		forced.clear();
	}

	@Override
	public K4 position()
	{
		return position;
	}

	@Override
	public Drehung dreh()
	{
		return dreh;
	}

	@Override
	public AkA plzDislocate(String info)
	{
		return this;
	}

	@Override
	public void addForced(Forced f)
	{
		forced.add(f);
	}

	@Override
	public void addAktion(Aktion a)
	{
		aktionen.add(a);
	}

	@Override
	public boolean nofreeze()
	{
		return dw.nofreeze();
	}
}