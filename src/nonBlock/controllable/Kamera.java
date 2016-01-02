package nonBlock.controllable;

import ansicht.*;
import block.*;
import nonBlock.aktion.*;
import wahr.zugriff.*;

import java.util.*;

public class Kamera extends NBD implements Controllable, Licht
{
	private final Controller control;
	public double[] canInfl;

	public Kamera(Controller control)
	{
		super();
		this.control = control;
	}

	public K4 kamP()
	{
		return new K4(position);
	}

	public Drehung kamD()
	{
		return new Drehung(dreh.wl, achsen[0].dreh.wb);
	}

	public void kontrolle()
	{
		dreh.wl += achsen[0].dreh.wl;
		dreh.sichern();
		achsen[0].dreh.wl = 0;
		ArrayList<String> commands = control.giveCommands();
		for(int i = 0; i < commands.size(); i++)
			doCommand(commands.get(i));
		if(canInfl != null)
		{
			boolean[] infl = control.infl();
			if(infl[0] != infl[1])
			{
				bewegung.a += Math.cos(dreh.wl) * (infl[0] ? canInfl[0] : -canInfl[0]);
				bewegung.c += Math.sin(dreh.wl) * (infl[0] ? canInfl[0] : -canInfl[0]);
			}
			if(infl[2] != infl[3])
				bewegung.b += infl[2] ? canInfl[1] : -canInfl[2];
			if(infl[4] != infl[5])
			{
				bewegung.a -= Math.sin(dreh.wl) * (infl[4] ? canInfl[0] : -canInfl[0]);
				bewegung.c += Math.cos(dreh.wl) * (infl[4] ? canInfl[0] : -canInfl[0]);
			}
			if(infl[6] != infl[7])
				bewegung.d += infl[6] ? canInfl[3] : -canInfl[3];
			bewegung = new K4(bewegung.a * 0.7, bewegung.b * 0.7, bewegung.c * 0.7, bewegung.d * 0.7);
		}
	}

	public NBD plzDislocate(String info)
	{
		return this;
	}

	public void doCommand(String command)
	{
		switch(command)
		{
			case "Hoch":
				if(Overlay.pa.tnTarget != null && Overlay.pa.tnTarget >= 0)
				{
					WBP p = Koord.decodeTn(Overlay.pa.tnTarget);
					WeltB.set(p, WeltB.gib(p) + 1);
				}
				break;
			case "Weg":
				if(Overlay.pa.tnTarget != null && Overlay.pa.tnTarget >= 0)
					WeltB.set(Koord.decodeTn(Overlay.pa.tnTarget), 0);
				break;
		}
	}

	public K4 lichtPosition()
	{
		return position;
	}

	public double lichtRange()
	{
		return 500;
	}

	public double lichtPower()
	{
		return -50;
	}

	public double lichtPowerDecay()
	{
		return 0.001;
	}
}