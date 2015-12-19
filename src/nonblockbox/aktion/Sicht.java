package nonblockbox.aktion;

import ansicht.*;
import falsch.*;

public class Sicht extends Aktion
{
	private final int linA;
	private final boolean gm;

	public Sicht(NBD target, int power, int linA, boolean gm)
	{
		super(target, -1, power);
		this.linA = linA;
		besitzer.resLink[linA] = this;
		this.gm = gm;
	}

	public void delink()
	{
		besitzer.resLink[linA] = null;
	}

	public void tick()
	{
		if(Overlay.sichtAn && (gm ? UIVerbunden.godMode : WeltND.nfr))
		{
			besitzer.dreh.wl -= UIVerbunden.maus.x * Math.abs(UIVerbunden.maus.x)
					/ 10000d / UIVerbunden.sc.width;
			besitzer.dreh.sichern();
			besitzer.linkAchsen[linA].dreh.wb -= UIVerbunden.maus.y * Math.abs(UIVerbunden.maus.y)
					/ 10000d / UIVerbunden.sc.height;
			if(!gm)
			{
				if(besitzer.linkAchsen[linA].dreh.wb < 0.2)
					besitzer.linkAchsen[linA].dreh.wb = 0.2;
				if(besitzer.linkAchsen[linA].dreh.wb > 3.2)
					besitzer.linkAchsen[linA].dreh.wb = 3.2;
			}
			besitzer.linkAchsen[linA].dreh.sichern();
		}
	}
}