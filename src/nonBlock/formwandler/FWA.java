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
		cooldowns = new double[abilities.cldSize];
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

	public void kontrolle()
	{
		if(!dw.nofreeze())
			return;

		double[] canInfl;
		boolean boden = naheWand(2, 0.1);
		if(boden)
			canInfl = new double[]{0.2, 0, 0, 0.2};
		else
			canInfl = new double[]{0.1, 0, 0.2, 0.1};

		K4 cb = new K4();
		boolean[] infl = control.infl();
		if(infl[0] != infl[1])
		{
			cb.a += Math.cos(dreh.wl) * (infl[0] ? canInfl[0] : -canInfl[0]);
			cb.c += Math.sin(dreh.wl) * (infl[0] ? canInfl[0] : -canInfl[0]);
		}
		if(infl[2] != infl[3])
			cb.b += infl[2] ? canInfl[1] : -canInfl[2];
		if(infl[4] != infl[5])
		{
			cb.a -= Math.sin(dreh.wl) * (infl[4] ? canInfl[0] : -canInfl[0]);
			cb.c += Math.cos(dreh.wl) * (infl[4] ? canInfl[0] : -canInfl[0]);
		}
		if(infl[6] != infl[7])
			cb.d += infl[6] ? canInfl[3] : -canInfl[3];
		beweg.add(cb);

		ArrayList<String> commands = control.giveCommands();
		for(int i = 0; i < commands.size(); i++)
			doCommand(commands.get(i));

		beweg.add(new K4(bewegung.a * 0.85, bewegung.b * 0.85, bewegung.c * 0.85, bewegung.d * 0.85));
		beweg.add(new K4(0, -0.05, 0, 0));
	}

	public void doCommand(String command)
	{
		int i = abilities.zustands.indexOf(currentZ);
		int j = abilities.usedInputs.get(i).indexOf(command);
		LadeControlledMove td = abilities.availMoves.get(i).get(j);
		for(int k = 0; k < td.braucht.size(); k++)
			if(verwendet[verwendbar.indexOf(td.braucht.get(k))] > td.brauchtLevel.get(k))
				return;
		if(cooldowns[td.sharedcooldown] > 0)
			return;
		if(td.isChainOnly && moves.contains(chain))
			return;
		Move m = new Move(Index.gibLadeMove(false, td.theMove), this);
		if(td.isChainOnly)
			chain = m;
		moves.add(m);
		cooldowns[td.sharedcooldown] = td.cooldown;
	}
}