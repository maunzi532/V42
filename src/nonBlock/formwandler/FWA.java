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
	private final Controller control;
	String currentZ;
	String lastZ;
	int transformTime;
	ArrayList<FWVerwendet> verwendbar;
	int[] verwendet;
	double[] cooldowns;
	Move chain;
	LadeFWA abilities;

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
		Move m = new Move(Index.gibLadeMove(false, td.theMove), this);
		if(td.isChainOnly)
			chain = m;
		moves.add(m);
		if(td.sharedcooldown >= 0)
			cooldowns[td.sharedcooldown] = td.cooldown;
	}
}