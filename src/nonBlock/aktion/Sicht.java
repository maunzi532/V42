package nonBlock.aktion;

import ansicht.*;
import wahr.zugriff.*;

public class Sicht extends Aktion
{
	private final int linAl;
	private final int linAb;
	private final boolean gm;

	public Sicht(NBD target, int power, int linAl, int linAb, boolean gm)
	{
		super(target, -1, power);
		this.linAl = linAl;
		this.linAb = linAb;
		besitzer.resLink[linAl] = this;
		besitzer.resLink[linAb] = this;
		this.gm = gm;
	}

	public void delink()
	{
		besitzer.resLink[linAl] = null;
		besitzer.resLink[linAb] = null;
	}

	public void tick()
	{
		if(Overlay.sichtAn && (gm ? UIVerbunden.godMode : WeltND.nfr))
		{
			besitzer.linkAchsen[linAl].dreh.wl -= UIVerbunden.mausv.x * 1.1d / UIVerbunden.sc.width;
			besitzer.linkAchsen[linAl].dreh.sichern();
			besitzer.linkAchsen[linAb].dreh.wb -= UIVerbunden.mausv.y * 1.1d / UIVerbunden.sc.height;
			if(!gm)
			{
				if(besitzer.linkAchsen[linAb].dreh.wb < 0.2)
					besitzer.linkAchsen[linAb].dreh.wb = 0.2;
				if(besitzer.linkAchsen[linAb].dreh.wb > 3.2)
					besitzer.linkAchsen[linAb].dreh.wb = 3.2;
			}
			besitzer.linkAchsen[linAb].dreh.sichern();
		}
	}
}