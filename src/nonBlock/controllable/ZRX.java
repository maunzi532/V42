package nonBlock.controllable;

import a3.*;
import java.util.*;
import k4.*;
import nonBlock.collide.*;
import nonBlock.formwandler.*;
import wahr.zugriff.*;

public class ZRX extends FWA implements Licht
{
	//Drehgeschwindigkeit
	public static final double nachDreh = 0.1;
	public static int hda = 67;

	protected ZRX(Controller control, LadeFWA abilities, String currentZ, AllWelt aw)
	{
		super(control, abilities, currentZ, aw);
	}

	@Override
	public void kontrolle()
	{
		if(!dw.nofreeze())
			return;
		/*int zIndex = zustands.indexOf(currentZ);
		double[] canInfl;
		if(zboden[zIndex] != null)
		{
			boolean boden = naheWand(2, 0.1); //und b-speed
			if(zboden[zIndex] != boden)
			{
				lastZ = currentZ;
				if(boden)
					currentZ = "Normal";
				else
					currentZ = "Luft";
				ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA"), this, 20);
			}
		}
		canInfl = zinfl[zIndex];*/
		dreheSicht(false);//
		/*boolean[] infl = control.infl();
		if(canInfl != null)
		{
			K4 cb = new K4();
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
		}

		inflChecks(infl);*/
		ArrayList<String> commands = control.giveCommands();
		for(int i = 0; i < commands.size(); i++)
			doCommand(commands.get(i));
		beweg.add(new K4(bewegung.a * 0.85, bewegung.b * 0.85, bewegung.c * 0.85, bewegung.d * 0.85));
		beweg.add(new K4(0, -0.05, 0, 0));
	}

	private void dreheSicht(boolean mitdrehen)
	{
		if(mitdrehen)
		{
			if(achsen[hda].dreh.wl < nachDreh || achsen[hda].dreh.wl > Math.PI * 2 - nachDreh)
			{
				dreh.wl += achsen[hda].dreh.wl;
				achsen[hda].dreh.wl = 0;
			}
			else if(achsen[hda].dreh.wl > Math.PI)
			{
				dreh.wl -= nachDreh;
				achsen[hda].dreh.wl += nachDreh;
				achsen[hda].dreh.sichern();
			}
			else
			{
				dreh.wl += nachDreh;
				achsen[hda].dreh.wl -= nachDreh;
				achsen[hda].dreh.sichern();
			}
			dreh.sichern();
		}
		else
		{
			if(achsen[hda].dreh.wl < Math.PI * 1.5 && achsen[hda].dreh.wl > Math.PI * 0.5)
			{
				if(achsen[hda].dreh.wl > Math.PI)
					achsen[hda].dreh.wl = Math.PI * 1.5;
				else
					achsen[hda].dreh.wl = Math.PI * 0.5;
			}
		}
	}

	@Override
	protected void doFall(String fall, boolean attachChainOnly)
	{

	}

	@Override
	public void wand(int welche)
	{

	}

	@Override
	public void collide(Collider collider)
	{

	}

	@Override
	public void actCollide(Collider collider)
	{

	}

	@Override
	public void decollide(Collider collider)
	{

	}

	@Override
	public K4 lichtPosition()
	{
		return null;
	}

	@Override
	public double lichtRange()
	{
		return 0;
	}

	@Override
	public double lichtPower()
	{
		return 0;
	}

	@Override
	public double lichtPowerDecay()
	{
		return 0;
	}
}