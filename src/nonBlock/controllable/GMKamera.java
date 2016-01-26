package nonBlock.controllable;

import ansicht.*;
import block.*;
import nonBlock.aktion.*;
import wahr.zugriff.*;

import java.util.*;

public class GMKamera extends NBD implements Controllable, Licht
{
	private final Controller control;
	private final Overlay overlay;
	public double[] canInfl;
	private WeltB welt;

	private GMKamera(Controller control, Overlay overlay, WeltB welt, WeltND dw)
	{
		super(null, dw);
		this.welt = welt;
		this.control = control;
		this.overlay = overlay;
	}

	public GMKamera(Controller control, Overlay overlay, AllWelt aw)
	{
		this(control, overlay, aw.wbl, aw.dw);
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
			beweg.add(new K4(bewegung.a * 0.7, bewegung.b * 0.7, bewegung.c * 0.7, bewegung.d * 0.7));
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
				if(overlay.pa.tnTarget != null && overlay.pa.tnTarget >= 0)
				{
					WBP p = welt.decodeTn(overlay.pa.tnTarget);
					welt.set(p, welt.gib(p) + 1);
				}
				break;
			case "Weg":
				if(overlay.pa.tnTarget != null && overlay.pa.tnTarget >= 0)
					welt.set(welt.decodeTn(overlay.pa.tnTarget), 0);
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