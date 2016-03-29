package ansicht.text;

import ansicht.*;
import wahr.zugriff.*;

import java.util.*;

public class InitSL
{
	public ArrayList<SLF> normalSchalter;
	public ArrayList<SLF> godModeSchalter;

	public InitSL(SchalterLayer sl, Overlay ov)
	{
		normalSchalter = new ArrayList<>();
		normalSchalter.add(new SLF(sl, true, 0.1, 0.1, 0.1, 0.05)
		{
			public void onClick(boolean r, double cx, double cy)
			{
				if(r)
					ov.pa.taType = ov.pa.taType == 2 ? 0 : 2;
				else
					ov.pa.taType = ov.pa.taType == 1 ? 0 : 1;
				super.onClick(r, cx, cy);
			}

			public void tick()
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
		normalSchalter.add(new SLF(sl, true, 0.1, 0.2, 0.1, 0.05)
		{
			public void onClick(boolean r, double cx, double cy)
			{
				if(r)
					ov.z.x4dization = ov.z.x4dization > 0 ? 0 : 1;
				else
					ov.z.x4dization = ov.z.x4dization > 1 ? 0 : 2;
				super.onClick(r, cx, cy);
			}

			public void tick()
			{
				switch(ov.z.x4dization)
				{
					case 0:
						text = "4D aus";
						break;
					case 1:
						text = "4D an";
						break;
					case 2:
						text = "4D an + Hinweise";
						break;
				}
			}
		});
		normalSchalter.add(new SLF(sl, true, 0.1, 0.3, 0.1, 0.05)
		{
			public void onClick(boolean r, double cx, double cy)
			{
				if(r)
					ov.z.d2tangibility = 0;
				else
					ov.z.d2tangibility = (ov.z.d2tangibility + 1) % 3;
				super.onClick(r, cx, cy);
			}

			public void tick()
			{
				switch(ov.z.d2tangibility)
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
		godModeSchalter = new ArrayList<>();
		godModeSchalter.add(new SLF(sl, true, 0.25, 0.2, 0.1, 0.05)
		{
			public void onClick(boolean r, double cx, double cy)
			{
				if(r)
					ov.z.siehNonBlocks = !ov.z.siehNonBlocks;
				else
					ov.z.siehBlocks = !ov.z.siehBlocks;
				super.onClick(r, cx, cy);
			}

			public void tick()
			{
				if(ov.z.siehBlocks)
				{
					if(ov.z.siehNonBlocks)
						text = "Alles";
					else
						text = "Blocks";
				}
				else
				{
					if(ov.z.siehNonBlocks)
						text = "NonBlocks";
					else
						text = "Nichts";
				}
			}
		});
		godModeSchalter.add(new SLF(sl, true, 0.25, 0.3, 0.1, 0.05)
		{
			public void onClick(boolean r, double cx, double cy)
			{
				ov.pa.xrmode = !ov.pa.xrmode;
				super.onClick(r, cx, cy);
			}

			public void tick()
			{
				if(ov.pa.xrmode)
					text = "XR an";
				else
					text = "XR aus";
			}
		});
		godModeSchalter.add(new Schieber(sl, 0.25, 0.4, 0.1, 0.05, 0, 100, 50)
		{
			public void tick()
			{
				Staticf.xraywidth = shift * shiftm + startw;
				text = String.valueOf(Staticf.xraywidth);
			}
		});
		godModeSchalter.add(new SLF(sl, true, 0.25, 0.5, 0.1, 0.05)
		{
			public void onClick(boolean r, double cx, double cy)
			{
				ov.aw.dw.gmFreeze = !ov.aw.dw.gmFreeze;
				super.onClick(r, cx, cy);
			}

			public void tick()
			{
				if(ov.aw.dw.gmFreeze)
					text = "Gestoppt";
				else
					text = "Stop";
			}
		});
		godModeSchalter.add(new SLF(sl, true, 0.4, 0.1, 0.1, 0.05, "Speichern")
		{
			public void onClick(boolean r, double cx, double cy)
			{
				ov.aw.wbl.speichern("Levels/Test2", Staticf.wspg.k);
				super.onClick(r, cx, cy);
			}
		});
		godModeSchalter.add(new Schieber(sl, 0.4, 0.2, 0.2, 0.025, 0, 100, ov.aw.wbl.end[0])
		{
			public void tick()
			{
				Staticf.wspg.k[0] = (int)(shift * shiftm + startw);
				text = String.valueOf(Staticf.wspg.k[0]);
			}
		});
		godModeSchalter.add(new Schieber(sl, 0.4, 0.225, 0.2, 0.025, 0, 100, ov.aw.wbl.end[1])
		{
			public void tick()
			{
				Staticf.wspg.k[1] = (int)(shift * shiftm + startw);
				text = String.valueOf(Staticf.wspg.k[1]);
			}
		});
		godModeSchalter.add(new Schieber(sl, 0.4, 0.25, 0.2, 0.025, 0, 100, ov.aw.wbl.end[2])
		{
			public void tick()
			{
				Staticf.wspg.k[2] = (int)(shift * shiftm + startw);
				text = String.valueOf(Staticf.wspg.k[2]);
			}
		});
		godModeSchalter.add(new Schieber(sl, 0.4, 0.275, 0.2, 0.025, 0, 100, ov.aw.wbl.end[3])
		{
			public void tick()
			{
				Staticf.wspg.k[3] = (int)(shift * shiftm + startw);
				text = String.valueOf(Staticf.wspg.k[3]);
			}
		});
	}
}