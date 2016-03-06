package ansicht;

import ansicht.n2.*;
import ansicht.text.*;
import wahr.physisch.*;
import wahr.zugriff.*;

import java.awt.*;
import java.util.*;

public class Overlay
{
	public SchalterLayer sl;
	public ArrayList<SLF> normalSchalter;
	public ArrayList<SLF> godModeSchalter;
	public boolean sichtAn = true;
	public Panelizer pa;
	private AllWelt aw;
	public Zeichner z;
	private N2[] n2s2;

	public void initOverlay(AllWelt awA, String zDatLad)
	{
		aw = awA;
		z = new Zeichner(Index.gibText("Einstellungen", zDatLad), aw);
		UIVerbunden.mausLast = new Point(UIVerbunden.sc.width / 2, UIVerbunden.sc.height / 2);
		Point mm = LPaneel.fr.getLocationOnScreen();
		UIVerbunden.ro.mouseMove(UIVerbunden.sc.width / 2 + mm.x, UIVerbunden.sc.height / 2 + mm.y);
		sl = new SchalterLayer();
		pa = new Panelizer(UIVerbunden.sc);
		normalSchalter = new ArrayList<>();
		normalSchalter.add(new SLF(sl, true, 0.1, 0.1, 0.1, 0.05)
		{
			public void onClick(boolean r, double cx, double cy)
			{
				if(r)
					pa.taType = pa.taType == 2 ? 0 : 2;
				else
					pa.taType = pa.taType == 1 ? 0 : 1;
				super.onClick(r, cx, cy);
			}

			public void tick()
			{
				switch(pa.taType)
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
					z.x4dization = z.x4dization > 0 ? 0 : 1;
				else
					z.x4dization = z.x4dization > 1 ? 0 : 2;
				super.onClick(r, cx, cy);
			}

			public void tick()
			{
				switch(z.x4dization)
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
					z.d2tangibility = 0;
				else
					z.d2tangibility = (z.d2tangibility + 1) % 3;
				super.onClick(r, cx, cy);
			}

			public void tick()
			{
				switch(z.d2tangibility)
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
					z.siehNonBlocks = !z.siehNonBlocks;
				else
					z.siehBlocks = !z.siehBlocks;
				super.onClick(r, cx, cy);
			}

			public void tick()
			{
				if(z.siehBlocks)
				{
					if(z.siehNonBlocks)
						text = "Alles";
					else
						text = "Blocks";
				}
				else
				{
					if(z.siehNonBlocks)
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
				pa.xrmode = !pa.xrmode;
				super.onClick(r, cx, cy);
			}

			public void tick()
			{
				if(pa.xrmode)
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
				aw.dw.gmFreeze = !aw.dw.gmFreeze;
				super.onClick(r, cx, cy);
			}

			public void tick()
			{
				if(aw.dw.gmFreeze)
					text = "Gestoppt";
				else
					text = "Stop";
			}
		});
		godModeSchalter.add(new SLF(sl, true, 0.4, 0.1, 0.1, 0.05, "Speichern")
		{
			public void onClick(boolean r, double cx, double cy)
			{
				aw.wbl.speichern("Levels/Test1", Staticf.wspg.k);
				super.onClick(r, cx, cy);
			}
		});
		godModeSchalter.add(new Schieber(sl, 0.4, 0.2, 0.2, 0.025, 0, 100, aw.wbl.end[0])
		{
			public void tick()
			{
				Staticf.wspg.k[0] = (int)(shift * shiftm + startw);
				text = String.valueOf(Staticf.wspg.k[0]);
			}
		});
		godModeSchalter.add(new Schieber(sl, 0.4, 0.225, 0.2, 0.025, 0, 100, aw.wbl.end[1])
		{
			public void tick()
			{
				Staticf.wspg.k[1] = (int)(shift * shiftm + startw);
				text = String.valueOf(Staticf.wspg.k[1]);
			}
		});
		godModeSchalter.add(new Schieber(sl, 0.4, 0.25, 0.2, 0.025, 0, 100, aw.wbl.end[2])
		{
			public void tick()
			{
				Staticf.wspg.k[2] = (int)(shift * shiftm + startw);
				text = String.valueOf(Staticf.wspg.k[2]);
			}
		});
		godModeSchalter.add(new Schieber(sl, 0.4, 0.275, 0.2, 0.025, 0, 100, aw.wbl.end[3])
		{
			public void tick()
			{
				Staticf.wspg.k[3] = (int)(shift * shiftm + startw);
				text = String.valueOf(Staticf.wspg.k[3]);
			}
		});
	}

	public void resize()
	{
		pa.resize(UIVerbunden.sc);
	}

	public void rendern()
	{
		z.nehmen();
		Staticf.sca("Z nehmen (5) ");
		z.splittern();
		Staticf.sca("Z splittern (1) ");
		z.sortieren();
		Staticf.sca("Z sortieren (1) ");
		z.eckenEntf();
		Staticf.sca("Z eckenEntf (1) ");
		z.farbe_flaeche(pa.tnTarget);
		Staticf.sca("Z farbeflaeche (3) ");
		N2[] n2s3 = new N2[z.n2s.size()];
		for(int i = 0; i < n2s3.length; i++)
			n2s3[i] = z.n2s.get(i);
		n2s2 = n2s3;
	}

	public void panelize()
	{
		pa.panelize(n2s2, UIVerbunden.maus.x + UIVerbunden.sc.width / 2,
				UIVerbunden.maus.y + UIVerbunden.sc.height / 2);
		Staticf.sca2("Panelize (14) ");
		sl.draw(pa.gd);
		Staticf.sca2("Overlay (0) ");
		LPaneel.rePanel(pa.light);
		Staticf.sca2("RePanel (7) ");
	}
}