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
		if(Overlay.sichtAn && (gm ? Staticf.godMode : WeltND.nfr))
		{
			besitzer.dreh.wl -= Hauptschleife.maus.x * Math.abs(Hauptschleife.maus.x)
					/ 10000d / Staticf.sc.width;
			besitzer.dreh.sichern();
			besitzer.linkAchsen[linA].dreh.wb -= Hauptschleife.maus.y * Math.abs(Hauptschleife.maus.y)
					/ 10000d / Staticf.sc.height;
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