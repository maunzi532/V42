package nonBlock.aktion;

import wahr.spieler.*;

public class Sicht extends LinAAktion
{
	private final int linAl;
	private final int linAb;
	private final boolean gm;
	private final Spieler master;

	public Sicht(NBD target, int power, int linAl, int linAb, boolean gm, Spieler master)
	{
		super(target, -1, power);
		this.linAl = linAl;
		this.linAb = linAb;
		besitzer.resLink[linAl] = this;
		besitzer.resLink[linAb] = this;
		this.gm = gm;
		this.master = master;
	}

	public void delink()
	{
		besitzer.resLink[linAl] = null;
		besitzer.resLink[linAb] = null;
	}

	public void tick()
	{
		if(master.overlay.sichtAn && (gm ? master.godMode : (besitzer.dw.nofreeze() && !master.godMode)))
		{
			besitzer.linkAchsen[linAl].dreh.wl -= master.drehInput.wlmove() * 1.1d / master.overlay.auf.scF.width;
			besitzer.linkAchsen[linAl].dreh.sichern();
			besitzer.linkAchsen[linAb].dreh.wb -= master.drehInput.wbmove() * 1.1d / master.overlay.auf.scF.height;
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