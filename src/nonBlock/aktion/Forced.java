package nonBlock.aktion;

import wahr.zugriff.*;

public class Forced
{
	boolean[] affected;
	K4 movement;
	int power;

	public Forced(boolean[] affected, K4 movement, int power)
	{
		this.affected = affected;
		this.movement = movement;
		this.power = power;
	}

	public Forced(K4 movement, int power)
	{
		affected = new boolean[]{true, true, true, true};
		this.power = power;
		this.movement = movement;
	}

	public void affect(K4 bewegung, int[] powers)
	{
		if(affected[0] && power > powers[0])
		{
			powers[0] = power;
			bewegung.a = movement.a;
		}
		if(affected[1] && power > powers[1])
		{
			powers[1] = power;
			bewegung.b = movement.b;
		}
		if(affected[2] && power > powers[2])
		{
			powers[2] = power;
			bewegung.c = movement.c;
		}
		if(affected[3] && power > powers[3])
		{
			powers[3] = power;
			bewegung.d = movement.d;
		}
	}
}