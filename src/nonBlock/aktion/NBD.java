package nonBlock.aktion;

import ansicht.*;
import nonBlock.aktion.lesen.*;
import nonBlock.aussehen.*;
import wahr.zugriff.*;

import java.util.*;

public abstract class NBD extends NonBlock
{
	public LinAAktion[] resLink;
	public final ArrayList<Aktion> aktionen;
	protected K4 bewegung;
	protected ArrayList<K4> beweg;
	public ArrayList<Forced> forced;
	public AlternateStandard standard = null;
	public Aktion currentTrans = null;
	protected final ArrayList<Move> moves;

	protected NBD(LichtW lw, WeltND dw)
	{
		super(lw, dw);
		bewegung = new K4();
		beweg = new ArrayList<>();
		forced = new ArrayList<>();
		aktionen = new ArrayList<>();
		moves = new ArrayList<>();
	}

	protected NBD(AllWelt aw)
	{
		this(aw.lw, aw.dw);
	}

	public void init()
	{
		resLink = new LinAAktion[linkAchsen.length];
	}

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

	protected void mTick()
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

	protected abstract void kontrolle();

	public NBD plzDislocate(String info)
	{
		return this;
	}
}