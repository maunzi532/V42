package nonBlock.formwandler;

import ansicht.*;
import block.*;
import nonBlock.aktion.*;
import nonBlock.aktion.lesen.*;
import nonBlock.collide.*;
import nonBlock.controllable.*;
import wahr.zugriff.*;

import java.util.*;

public abstract class FWA extends NBB implements Controllable
{
	protected final Controller control;
	protected String currentZ;
	protected String lastZ;
	int transformTime;
	private ArrayList<FWVerwendet> verwendbar;
	private int[] verwendet;
	private double[] cooldowns;
	private Move chain;
	private LadeFWA abilities;

	protected FWA(Controller control, LadeFWA abilities, String currentZ, WeltB welt, LichtW lw, WeltND dw, WeltNB bw)
	{
		super(welt, lw, dw, bw);
		this.control = control;
		this.abilities = abilities;
		if(abilities != null)
			cooldowns = new double[abilities.cldSize];
		else
			cooldowns = new double[0];
		this.currentZ = currentZ;
		lastZ = currentZ;
	}

	protected FWA(Controller control, LadeFWA abilities, String currentZ, AllWelt aw)
	{
		super(aw);
		this.control = control;
		this.abilities = abilities;
		cooldowns = new double[abilities.cldSize];
		this.currentZ = currentZ;
		lastZ = currentZ;
	}

	public void tick()
	{
		for(int i = 0; i < cooldowns.length; i++)
			cooldowns[i]--;
		super.tick();
	}

	public void doCommand(String command)
	{
		int i = abilities.zustands.indexOf(currentZ);
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
		if(td.theFall != null)
			doFall(td.theFall, td.isChainOnly);
		if(td.theMove != null)
		{
			Move m = new Move(LadeMove.gibVonIndex(false, td.theMove), this);
			if(td.isChainOnly)
				chain = m;
			moves.add(m);
		}
		if(td.sharedcooldown >= 0)
			cooldowns[td.sharedcooldown] = td.cooldown;
	}

	protected void inflChecks(boolean[] infl)
	{
		for(int i = 0; i < 8; i++)
			if(infl[i])
			doCommand("I" + i);
	}

	protected abstract void doFall(String fall, boolean attachChainOnly);
}