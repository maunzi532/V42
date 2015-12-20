package nonBlock.aktion;

import ansicht.*;
import wahr.zugriff.*;

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
			//besitzer.dreh.wl -= UIVerbunden.mausv.x * Math.abs(UIVerbunden.mausv.x)
					/// 10000d / UIVerbunden.sc.width;
			besitzer.dreh.wl -= UIVerbunden.mausv.x * 1.1d / UIVerbunden.sc.width;
			besitzer.dreh.sichern();
			//besitzer.linkAchsen[linA].dreh.wb -= UIVerbunden.mausv.y * Math.abs(UIVerbunden.mausv.y)
					/// 10000d / UIVerbunden.sc.height;
			besitzer.linkAchsen[linA].dreh.wb -= UIVerbunden.mausv.y * 1.1d / UIVerbunden.sc.height;
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