package nonBlock.collide;

import java.util.*;
import k4.*;
import nonBlock.aktion.*;
import wahr.zugriff.*;
import welt.*;

public abstract class NBB extends NBD
{
	public final ArrayList<BlockBox> block;
	public ArrayList<double[]> bl;
	public final ArrayList<ColBox> collidable;
	public final ArrayList<Collider> colliders;
	final ArrayList<Collider> connected;
	public final ArrayList<Integer> cTime;
	public final NBB nht;
	public final ArrayList<ColBox> physik;
	public WeltB welt;
	public WeltNB bw;

	protected NBB(WeltB welt, WeltND dw, WeltNB bw)
	{
		super(dw);
		this.welt = welt;
		this.bw = bw;
		block = new ArrayList<>();
		collidable = new ArrayList<>();
		connected = new ArrayList<>();
		cTime = new ArrayList<>();
		nht = null;
		colliders = new ArrayList<>();
		physik = new ArrayList<>();
		bw.vta.add(this);
	}

	protected NBB(AllWelt aw)
	{
		this(aw.wbl, aw.dw, aw.bw);
	}

	public void chargeBlockBox(String input)
	{
		ArrayList<double[]> blockbox = gibBlockBox(input);
		bl = blockbox;
		for(int i = 0; i < blockbox.size(); i++)
			block.add(new BlockBox(this, new K4(blockbox.get(i)[0],
					blockbox.get(i)[1], blockbox.get(i)[2], blockbox.get(i)[3]),
					new K4(blockbox.get(i)[4], blockbox.get(i)[5],
							blockbox.get(i)[6], blockbox.get(i)[7]), blockbox.get(i)[8]));
	}

	public static ArrayList gibBlockBox(String input)
	{
		String[] zeilen = input.split("\n");
		ArrayList<double[]> blockbox = new ArrayList<>();
		for(int i = 0; i < zeilen.length; i++)
		{
			if(!zeilen[i].isEmpty() && !zeilen[i].startsWith("/"))
			{
				String[] y1 = zeilen[i].split(" ");
				double[] da = new double[9];
				for(int j = 0; j < 9; j++)
					da[j] = Double.parseDouble(y1[j]);
				blockbox.add(da);
			}
		}
		return blockbox;
	}

	public void ende()
	{
		dw.nonBlocks.remove(this);
		bw.vta.remove(this);
	}

	public void tick()
	{
		if(dw.nofreeze())
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
		}
		super.tick();
	}

	protected void mTick()
	{
		super.mTick();
		K4 eb = new K4(bewegung); //Bewegunsfreiheit testen
		for(int i = 0; i < block.size(); i++)
		{
			K4 eb1 = block.get(i).check(bewegung, welt);
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
	}

	public abstract void collide(Collider collider);

	public abstract void actCollide(Collider collider);

	public abstract void decollide(Collider collider);

	public abstract void wand(int welche);

	protected boolean naheWand(int welche, double abstand)
	{
		for(int i = 0; i < block.size(); i++)
			if(block.get(i).checkWand(welche, abstand, welt))
				return true;
		return false;
	}
}