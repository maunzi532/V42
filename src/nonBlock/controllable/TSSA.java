package nonBlock.controllable;

import a3.*;
import java.util.*;
import k4.*;
import nonBlock.aktion.*;
import nonBlock.collide.*;
import nonBlock.formwandler.*;
import wahr.zugriff.*;
import welt.*;

public abstract class TSSA extends FWA implements Licht
{
	//private static final double nachDreh = 0.1;
	//private int hda = 67;

	TSSA(Controller control, LadeFWA abilities, String currentZ, WeltB welt, WeltND dw, WeltNB bw)
	{
		super(control, abilities, currentZ, welt, dw, bw);
	}

	protected TSSA(Controller control, LadeFWA abilities, String currentZ, AllWelt aw)
	{
		this(control, abilities, currentZ, aw.wbl, aw.dw, aw.bw);
	}

	public void kontrolle()
	{
		if(!dw.nofreeze())
			return;
		zustand.kontrolleDrehung();
		zustand.kontrolle(control.infl());
		ArrayList<String> commands = control.giveCommands();
		for(int i = 0; i < commands.size(); i++)
			doCommand(commands.get(i));
	}

	public K4 lichtPosition()
	{
		return position;
	}

	public double lichtRange()
	{
		return 800;
	}

	public double lichtPower()
	{
		return 30;
	}

	public double lichtPowerDecay()
	{
		return 0.8;
	}
}