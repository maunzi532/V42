package nonblockbox.aktion;

import modell.*;

public class ATR extends R
{
	public static void summonATRandCheck(NBD target, int linA, AltTrans command)
	{
		if(linA >= target.elimit)
			return;
		if(target.resLink[linA] != null && target.resLink[linA].power > -1)
			return;
		ATR r = new ATR(target, command);
		AlternateStandard std = target.standard;
		r.a = ADI.rad(linA, 1, command.dauer - 1, std.lens[linA], std.drehs[linA].wb,
				std.drehs[linA].wl, std.spins[linA], std.dShifts[linA], false);
		target.aktionen.add(r);
	}

	private final AltTrans command;

	private ATR(NBD besitzer, AltTrans command)
	{
		super(besitzer, command.dauer);
		this.command = command;
	}

	public void tick()
	{
		if(needCancel)
			return;
		aktuell = command.aktuell;
		super.tick();
	}
}
