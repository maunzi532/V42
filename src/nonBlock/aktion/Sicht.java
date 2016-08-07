package nonBlock.aktion;

import achsen.*;
import ansicht.*;

public class Sicht extends LinAAktion
{
	private final int linAl;
	private final int linAb;
	private final boolean gm;
	private final Overlay master;
	private final NonBlock besitzer1;

	public Sicht(NonBlock target, int power, int linAl, int linAb, boolean gm, Overlay master)
	{
		super(target, -1, power);
		this.linAl = linAl;
		this.linAb = linAb;
		besitzer1 = (NonBlock) besitzer;
		besitzer1.resLink[linAl] = this;
		besitzer1.resLink[linAb] = this;
		this.gm = gm;
		this.master = master;
	}

	public void delink()
	{
		besitzer1.resLink[linAl] = null;
		besitzer1.resLink[linAb] = null;
	}

	public void tick()
	{
		if(master.sichtAn && (gm ? master.godMode : ((AkA)besitzer1).nofreeze() && !master.godMode))
		{
			besitzer1.linkAchsen[linAl].dreh.wl -= master.drehInput.wlmove() * 2.2d / master.auf.scF.width;
			besitzer1.linkAchsen[linAl].dreh.sichern();
			besitzer1.linkAchsen[linAb].dreh.wb -= master.drehInput.wbmove() * 2.2d / master.auf.scF.height;
			if(!gm)
			{
				if(besitzer1.linkAchsen[linAb].dreh.wb < 0.2)
					besitzer1.linkAchsen[linAb].dreh.wb = 0.2;
				if(besitzer1.linkAchsen[linAb].dreh.wb > 3.2)
					besitzer1.linkAchsen[linAb].dreh.wb = 3.2;
			}
			besitzer1.linkAchsen[linAb].dreh.sichern();
		}
	}
}