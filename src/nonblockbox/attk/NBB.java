package nonblockbox.attk;

import k.*;
import nonblockbox.*;
import nonblockbox.aktion.*;
import nonblockbox.attk.move.*;

import java.util.*;

public abstract class NBB extends NBD
{
	public final ArrayList<BlockBox> block;
	public final ArrayList<ColBox> collidable;
	public final ArrayList<Attk> attks;
	final ArrayList<Attk> connected;
	public final ArrayList<Integer> cTime;
	public final NBB nht;
	public final ArrayList<ColBox> physik;
	protected final ArrayList<Move> moves;

	protected NBB()
	{
		super();
		block = new ArrayList<>();
		collidable = new ArrayList<>();
		connected = new ArrayList<>();
		cTime = new ArrayList<>();
		nht = null;
		attks = new ArrayList<>();
		physik = new ArrayList<>();
		moves = new ArrayList<>();
		WeltNB.vta.add(this);
	}

	public void ende()
	{
		WeltND.nonBlocks.remove(this);
		WeltNB.vta.remove(this);
	}

	public void tick()
	{
		if(WeltND.nfr)
		{
			for(int i = 0; i < connected.size(); i++) //Auf Kollisionen testen
			{
				if(cTime.get(i) == 0)
					collide(connected.get(i));
				else if(connected.get(i).con.indexOf(this) != -1 &&
						connected.get(i).changed.get(connected.get(i).con.indexOf(this)))
					actCollide(connected.get(i));
				if(cTime.get(i) >= connected.get(i).stop(this))
				{
					decollide(connected.get(i));
					connected.remove(i);
					cTime.remove(i);
					i--;
				}
				else
					cTime.set(i, cTime.get(i) + 1);
			}
			for(int i = 0; i < moves.size(); i++)
				if(moves.get(i).tick())
				{
					moves.remove(i);
					i--;
				}
		}
		super.tick();
	}

	protected void mTick()
	{
		K4 eb = new K4(bewegung); //Bewegunsfreiheit testen
		for(int i = 0; i < block.size(); i++)
		{
			K4 eb1 = block.get(i).check(bewegung);
			if(Math.abs(eb1.a) < Math.abs(eb.a))
				eb.a = eb1.a;
			if(Math.abs(eb1.b) < Math.abs(eb.b))
				eb.b = eb1.b;
			if(Math.abs(eb1.c) < Math.abs(eb.c))
				eb.c = eb1.c;
			if(Math.abs(eb1.d) < Math.abs(eb.d))
				eb.d = eb1.d;
		}
		if(eb.b != bewegung.b)
			wand(bewegung.b > 0 ? 3 : 2);
		if(eb.d != bewegung.d)
			wand(bewegung.d > 0 ? 7 : 6);
		if(eb.c != bewegung.c)
			wand(bewegung.c > 0 ? 5 : 4);
		if(eb.a != bewegung.a)
			wand(bewegung.a > 0 ? 1 : 0);

		bewegung = eb;
		position = K4.plus(position, bewegung); //Bewegen
	}

	public abstract void collide(Attk attk);

	public abstract void actCollide(Attk attk);

	public abstract void decollide(Attk attk);

	public abstract void wand(int welche);

	protected boolean naheWand(int welche, double abstand)
	{
		for(int i = 0; i < block.size(); i++)
			if(block.get(i).checkWand(welche, abstand))
				return true;
		return false;
	}
}