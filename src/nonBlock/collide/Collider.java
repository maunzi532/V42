package nonBlock.collide;

import java.util.*;

public class Collider
{
	public NBB besitzer;
	public final ArrayList<Hitbox> h;
	public NBB nht;
	public ArrayList<NBB> con;
	private ArrayList<Double> conP;
	ArrayList<Boolean> changed;
	private final int cll;
	private ArrayList<Hitbox> col1;
	private ArrayList<ColBox> col2;

	public Collider(int cll)
	{
		this.cll = cll;
		h = new ArrayList<>();
	}

	public void charge(NBB besitzer, NBB nht)
	{
		this.besitzer = besitzer;
		this.nht = nht;
		con = new ArrayList<>();
		conP = new ArrayList<>();
		changed = new ArrayList<>();
		col1 = new ArrayList<>();
		col2 = new ArrayList<>();
		for(int i = 0; i < h.size(); i++)
		{
			h.get(i).connected = new ArrayList<>();
			h.get(i).dauerNoch = h.get(i).dauer;
		}
		besitzer.colliders.add(this);
		besitzer.bw.colliders.add(this);
	}

	public void reset()
	{
		for(int i = 0; i < changed.size(); i++)
			changed.set(i, false);
		for(int i = 0; i < h.size(); i++)
			h.get(i).dauerNoch--;
	}

	public void ende()
	{
		besitzer.colliders.remove(this);
		besitzer.bw.colliders.remove(this);
	}

	public void resolve()
	{
		for(int i = 0; i < h.size(); i++)
			if(h.get(i).dauerNoch > 0)
				for(int j = 0; j < h.get(i).connected.size(); j++)
				{
					int ind = con.indexOf(h.get(i).connected.get(j).besitzer);
					if(ind >= 0)
					{
						double power = power(h.get(i).connectibility, h.get(i).connected.get(j).connectibility);
						if(power > conP.get(ind))
						{
							if(conP.get(ind) < 0 && power >= 0)
							{
								h.get(i).connected.get(j).besitzer.connected.add(this);
								h.get(i).connected.get(j).besitzer.cTime.add(0);
							}
							conP.set(ind, power);
							col1.set(ind, h.get(i));
							col2.set(ind, h.get(i).connected.get(j));
							changed.set(ind, true);
						}
					}
					else
					{
						double power = power(h.get(i).connectibility, h.get(i).connected.get(j).connectibility);
						if(power >= 0)
						{
							h.get(i).connected.get(j).besitzer.connected.add(this);
							h.get(i).connected.get(j).besitzer.cTime.add(0);
						}
						con.add(h.get(i).connected.get(j).besitzer);
						conP.add(power);
						col1.add(h.get(i));
						col2.add(h.get(i).connected.get(j));
						changed.add(true);
					}
				}
	}

	public int stop(NBB n)
	{
		return cll;
	}

	private static double power(double hPower, double cPower)
	{
		if(hPower < 0 && cPower < 0)
			return -hPower * cPower;
		return hPower * cPower;
	}
}