package nonBlock.controllable;

import a3.*;
import ansicht.*;
import java.util.*;
import k4.*;
import nonBlock.aktion.*;
import wahr.zugriff.*;
import welt.*;

public class GMKamera extends NBD implements Controllable, Licht, GMMover
{
	private final Controller control;
	private final Overlay overlay;
	public double[] canInfl;
	private WeltB welt;
	private int lockedDreh;

	private GMKamera(Controller control, Overlay overlay, WeltB welt, WeltND dw)
	{
		super(dw);
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
		return new Drehung(dreh.wl, dreh.wb);
	}

	public void kontrolle()
	{
		dreh.wl += achsen[0].dreh.wl;
		dreh.wb += achsen[0].dreh.wb;
		dreh.sichern();
		achsen[0].dreh.wl = 0;
		achsen[0].dreh.wb = 0;
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
				cb.a -= Math.sin(dreh.wl) * Math.cos(dreh.wb) * (infl[4] ? canInfl[0] : -canInfl[0]);
				cb.c += Math.cos(dreh.wl) * Math.cos(dreh.wb) * (infl[4] ? canInfl[0] : -canInfl[0]);
				cb.b += Math.sin(dreh.wb) * (infl[4] ? canInfl[1] : -canInfl[1]);
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
				if(overlay.pa.tnTarget >= 0)
				{
					WBP p = welt.decodeTn(overlay.pa.tnTarget);
					DerBlock block = welt.gib(p);
					welt.set(p, new DerBlock(block.typ + 1, block.dreh4));
				}
				break;
			case "Weg":
				if(overlay.pa.tnTarget >= 0)
				{
					WBP p = welt.decodeTn(overlay.pa.tnTarget);
					welt.set(p, new DerBlock(0, welt.gib(p).dreh4));
				}
				break;
			case "Dreh":
				if(overlay.pa.tnTarget >= 0)
				{
					WBP p = welt.decodeTn(overlay.pa.tnTarget);
					DerBlock block = welt.gib(p);
					block.dreh4 = (block.dreh4 + 1) % 4;
				}
				break;
			case "taGet":
				overlay.pa.taGet = !overlay.pa.taGet;
				if(overlay.pa.taGet)
				{
					lockedDreh = ((int)(dreh.wl / Math.PI * 4) + 1) % 8 / 2;
				}
				break;
			case "R0":
			case "R1":
			case "R2":
			case "R3":
				if(overlay.pa.tnTarget >= 0)
				{
					WBP p = welt.decodeTn(overlay.pa.tnTarget);
					if(p != null)
					{
						int d = (lockedDreh + command.charAt(1)) % 4;
						p.k[d % 2 == 0 ? 0 : 2] += d > 1 ? 1 : -1;
						long tn = welt.tn(p);
						if(tn >= 0)
							overlay.pa.tnTarget = tn;
					}
				}
				break;
			case "H0":
			case "H1":
				if(overlay.pa.tnTarget >= 0)
				{
					WBP p = welt.decodeTn(overlay.pa.tnTarget);
					if(p != null)
					{
						p.k[1] -= (command.charAt(1) - 48) * 2 - 1;
						long tn = welt.tn(p);
						if(tn >= 0)
							overlay.pa.tnTarget = tn;
					}
				}
				break;
			case "D0":
			case "D1":
				if(overlay.pa.tnTarget >= 0)
				{
					WBP p = welt.decodeTn(overlay.pa.tnTarget);
					if(p != null)
					{
						p.k[3] -= (command.charAt(1) - 48) * 2 - 1;
						long tn = welt.tn(p);
						if(tn >= 0)
							overlay.pa.tnTarget = tn;
					}
				}
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