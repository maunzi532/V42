package nonBlock.formwandler;

import java.util.*;
import nonBlock.aktion.*;
import nonBlock.aktion.lesen.*;
import nonBlock.collide.*;
import nonBlock.controllable.*;
import nonBlock.formwandler.zustand.*;
import welt.*;

public abstract class FWA extends NBB implements Controllable
{
	protected final Controller control;
	public Zustand zustand;
	private ArrayList<FWVerwendet> verwendbar;
	private int[] verwendet;
	private double[] cooldowns;
	private Move chain;
	private LadeFWA abilities;
	public Tverlay tverlay;

	protected FWA(Controller control, LadeFWA abilities, String currentZ, WeltB welt, WeltND dw, WeltNB bw)
	{
		super(welt, dw, bw);
		this.control = control;
		this.abilities = abilities;
		if(abilities != null)
			cooldowns = new double[abilities.cldSize];
		else
			cooldowns = new double[0];
		zustand = new AerialZ(this);
	}

	public void tick()
	{
		for(int i = 0; i < cooldowns.length; i++)
			cooldowns[i]--;
		super.tick();
	}

	public void doCommand(String command)
	{
		int i = abilities.zustands.indexOf(zustand.getClass().getSimpleName());
		if(i < 0)
			return;
		int j = abilities.usedInputs.get(i).indexOf(command);
		if(j < 0)
			return;
		LadeControlledMove td = abilities.availMoves.get(i).get(j);
		for(int k = 0; k < td.braucht.size(); k++)
			if(verwendet[verwendbar.indexOf(td.braucht.get(k))] > td.brauchtLevel.get(k))
				return;
		if(td.sharedcooldown >= 0 && cooldowns[td.sharedcooldown] > 0)
			return;
		if(td.isChainOnly && moves.contains(chain))
			return;
		if(td.power > 0)
			for(int k = 0; k < moves.size(); k++)
				if(moves.get(k).lad.resist < td.power)
					moves.get(k).kill = true;
		boolean zx = td.theMoveTargeted != null || td.theFallTargeted != null;
		String fall1 = zx && targetFall() ? td.theFallTargeted : td.theFall;
		String move1 = zx && targetFall() ? td.theMoveTargeted : td.theMove;
		if(fall1 != null)
			doFall(fall1, td.isChainOnly);
		if(move1 != null)
		{
			Move m = new Move(LadeMove.gibVonIndex(false, move1), this);
			if(td.isChainOnly)
				chain = m;
			moves.add(m);
		}
		if(td.sharedcooldown >= 0)
			cooldowns[td.sharedcooldown] = td.cooldown;
	}

	protected boolean targetFall()
	{
		return false;
	}

	protected abstract void doFall(String fall, boolean attachChainOnly);
}