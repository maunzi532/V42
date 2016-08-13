package ansicht.text;

import a3.*;
import achsen.*;
import ansicht.*;
import java.util.*;
import k4.*;
import nonBlock.collide.*;
import nonBlock.controllable.*;
import wahr.zugriff.*;

public class InitSL implements SchalterInit
{
	public ArrayList<SLF> normalSchalter;
	public ArrayList<SLF> godModeSchalter;

	@Override
	public ArrayList<SLF> gibSchalter(boolean... zustands)
	{
		if(zustands[0])
			return new ArrayList<>();
		if(zustands[1])
			return godModeSchalter;
		return normalSchalter;
	}

	public InitSL(Overlay ov)
	{
		normalSchalter = new ArrayList<>();
		normalSchalter.add(new SLF(true, 0.1, 0.1, 0.1, 0.05)
		{
			public void onClick(SchalterLayer main, boolean r, double cx, double cy)
			{
				if(r)
					ov.pa.taType = ov.pa.taType == 2 ? 0 : 2;
				else
					ov.pa.taType = ov.pa.taType == 1 ? 0 : 1;
				super.onClick(main, r, cx, cy);
			}

			public void tick(SchalterLayer main)
			{
				switch(ov.pa.taType)
				{
					case 0:
						text = "Target aus";
						break;
					case 1:
						text = "Target an";
						break;
					case 2:
						text = "Linien an";
						break;
				}
			}
		});
		normalSchalter.add(new SLF(true, 0.1, 0.2, 0.1, 0.05)
		{
			public void onClick(SchalterLayer main, boolean r, double cx, double cy)
			{
				if(r)
					ov.vor.visionRange4D = ov.vor.visionRange4D > 0 ? 0 : 1;
				else
					ov.vor.visionRange4D = ov.vor.visionRange4D > 1 ? 0 : 2;
				super.onClick(main, r, cx, cy);
			}

			public void tick(SchalterLayer main)
			{
				switch(ov.vor.visionRange4D)
				{
					case 0:
						text = "4D aus";
						break;
					case 1:
						text = "4D an Lv 1";
						break;
					case 2:
						text = "4D an Lv 2";
						break;
				}
			}
		});
		normalSchalter.add(new SLF(true, 0.1, 0.3, 0.1, 0.05)
		{
			public void onClick(SchalterLayer main, boolean r, double cx, double cy)
			{
				if(r)
					ov.vor.baumodus = 0;
				else
					ov.vor.baumodus = (ov.vor.baumodus + 1) % 3;
				super.onClick(main, r, cx, cy);
			}

			public void tick(SchalterLayer main)
			{
				switch(ov.vor.baumodus)
				{
					case 0:
						text = "Baumodus aus";
						break;
					case 1:
						text = "Baumodus Kanten";
						break;
					case 2:
						text = "Baumodus an";
						break;
				}
			}
		});
		normalSchalter.add(new SLF(true, 0.1, 0.4, 0.1, 0.05, "Flagge")
		{
			public void onClick(SchalterLayer main, boolean r, double cx, double cy)
			{
				if(r)
					while(ov.aw.wbl.flags.size() > 0)
						ov.aw.wbl.flags.get(0).ende();
				else if(ov.kamN instanceof NBB)
				{
					NBB th = (NBB) ov.kamN;
					Flag f = new Flag(th.welt, th.dw, th.bw);
					f.aussehen = new LadeModell();
					StandardAussehen.gibVonIndexS("Flagge/Sta").assignStandard(f);
					f.aussehen.reload(LadeTeil.gibVonIndex("Flagge/Achsen", new PolyFarbe()));
					f.position = new K4(th.position);
					f.position.b -= th.block.get(0).airshift;
					f.dreh = new Drehung(th.dreh.wl, 0);
					f.collidable.add(new ColBox(f, 1, new EndScheibe(0.3), new EndScheibe(0.3), 1, 1));
					f.init();
				}
				super.onClick(main, r, cx, cy);
			}
		});
		godModeSchalter = new ArrayList<>();
		godModeSchalter.addAll(normalSchalter);
		godModeSchalter.add(new SLF(true, 0.25, 0.1, 0.1, 0.05, "Teleport")
		{
			public void onClick(SchalterLayer main, boolean r, double cx, double cy)
			{
				if(ov.kamN instanceof NonBlock)
				{
					if(r)
						ov.godModeKam.position = ((NonBlock)ov.kamN).position;
					else
						((NonBlock)ov.kamN).position = ov.godModeKam.position;
				}
				super.onClick(main, r, cx, cy);
			}
		});
		godModeSchalter.add(new SLF(true, 0.25, 0.2, 0.1, 0.05)
		{
			public void onClick(SchalterLayer main, boolean r, double cx, double cy)
			{
				if(r)
					ov.vor.siehNonBlocks = !ov.vor.siehNonBlocks;
				else
					ov.vor.upgradeActive = !ov.vor.upgradeActive;
				super.onClick(main, r, cx, cy);
			}

			public void tick(SchalterLayer main)
			{
				if(ov.vor.upgradeActive)
				{
					if(ov.vor.siehNonBlocks)
						text = "Alles";
					else
						text = "Blocks";
				}
				else
				{
					if(ov.vor.siehNonBlocks)
						text = "NonBlocks";
					else
						text = "Nichts";
				}
			}
		});
		godModeSchalter.add(new SLF(true, 0.25, 0.3, 0.1, 0.05)
		{
			public void onClick(SchalterLayer main, boolean r, double cx, double cy)
			{
				ov.pa.xrmode = !ov.pa.xrmode;
				super.onClick(main, r, cx, cy);
			}

			public void tick(SchalterLayer main)
			{
				if(ov.pa.xrmode)
					text = "XR an";
				else
					text = "XR aus";
			}
		});
		godModeSchalter.add(new Schieber(0.25, 0.4, 0.1, 0.05, 0, 100, 50)
		{
			public void tick(SchalterLayer main)
			{
				Vor.xraywidth = shift * shiftm + startw;
				text = String.valueOf(Vor.xraywidth);
			}
		});
		godModeSchalter.add(new SLF(true, 0.25, 0.5, 0.1, 0.05)
		{
			public void onClick(SchalterLayer main, boolean r, double cx, double cy)
			{
				ov.aw.dw.gmFreeze = !ov.aw.dw.gmFreeze;
				super.onClick(main, r, cx, cy);
			}

			public void tick(SchalterLayer main)
			{
				if(ov.aw.dw.gmFreeze)
					text = "Gestoppt";
				else
					text = "Stop";
			}
		});
		godModeSchalter.add(new SLF(true, 0.4, 0.1, 0.1, 0.05, "Speichern")
		{
			public void onClick(SchalterLayer main, boolean r, double cx, double cy)
			{
				ov.aw.wbl.speichern("Levels/Test1", Staticf.wspg.k);
				super.onClick(main, r, cx, cy);
			}
		});
		godModeSchalter.add(new Schieber(0.4, 0.2, 0.2, 0.025, 0, 100, ov.aw.wbl.end[0])
		{
			public void tick(SchalterLayer main)
			{
				Staticf.wspg.k[0] = (int)(shift * shiftm + startw);
				text = String.valueOf(Staticf.wspg.k[0]);
			}
		});
		godModeSchalter.add(new Schieber(0.4, 0.225, 0.2, 0.025, 0, 100, ov.aw.wbl.end[1])
		{
			public void tick(SchalterLayer main)
			{
				Staticf.wspg.k[1] = (int)(shift * shiftm + startw);
				text = String.valueOf(Staticf.wspg.k[1]);
			}
		});
		godModeSchalter.add(new Schieber(0.4, 0.25, 0.2, 0.025, 0, 100, ov.aw.wbl.end[2])
		{
			public void tick(SchalterLayer main)
			{
				Staticf.wspg.k[2] = (int)(shift * shiftm + startw);
				text = String.valueOf(Staticf.wspg.k[2]);
			}
		});
		godModeSchalter.add(new Schieber(0.4, 0.275, 0.2, 0.025, 0, 100, ov.aw.wbl.end[3])
		{
			public void tick(SchalterLayer main)
			{
				Staticf.wspg.k[3] = (int)(shift * shiftm + startw);
				text = String.valueOf(Staticf.wspg.k[3]);
			}
		});
	}
}