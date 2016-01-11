package ansicht;

import ansicht.text.*;
import block.*;
import wahr.physisch.*;
import wahr.zugriff.*;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class Overlay
{
	public static BufferedImage hl;
	public static Graphics2D gd;
	public static SchalterLayer sl;
	public static ArrayList<SLF> normalSchalter;
	public static ArrayList<SLF> godModeSchalter;
	public static boolean sichtAn = true;
	public static Panelizer pa;

	public static void initOverlay()
	{
		UIVerbunden.mausLast = new Point(UIVerbunden.sc.width / 2, UIVerbunden.sc.height / 2);
		Point mm = LPaneel.fr.getLocationOnScreen();
		UIVerbunden.ro.mouseMove(UIVerbunden.sc.width / 2 + mm.x, UIVerbunden.sc.height / 2 + mm.y);
		hl = new BufferedImage(UIVerbunden.sc.width, UIVerbunden.sc.height, BufferedImage.TYPE_INT_ARGB_PRE);
		gd = hl.createGraphics();
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
					UIVerbunden.x4dization = UIVerbunden.x4dization > 0 ? 0 : 1;
				else
					UIVerbunden.x4dization = UIVerbunden.x4dization > 1 ? 0 : 2;
				super.onClick(r, cx, cy);
			}

			public void tick()
			{
				switch(UIVerbunden.x4dization)
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
					UIVerbunden.d2tangibility = 0;
				else
					UIVerbunden.d2tangibility = (UIVerbunden.d2tangibility + 1) % 3;
				super.onClick(r, cx, cy);
			}

			public void tick()
			{
				switch(UIVerbunden.d2tangibility)
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
					UIVerbunden.siehNonBlocks = !UIVerbunden.siehNonBlocks;
				else
					UIVerbunden.siehBlocks = !UIVerbunden.siehBlocks;
				super.onClick(r, cx, cy);
			}

			public void tick()
			{
				if(UIVerbunden.siehBlocks)
				{
					if(UIVerbunden.siehNonBlocks)
						text = "Alles";
					else
						text = "Blocks";
				}
				else
				{
					if(UIVerbunden.siehNonBlocks)
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
				UIVerbunden.xrmode = !UIVerbunden.xrmode;
				super.onClick(r, cx, cy);
			}

			public void tick()
			{
				if(UIVerbunden.xrmode)
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
		godModeSchalter.add(new SLF(sl, true, 0.4, 0.1, 0.1, 0.05, "Speichern")
		{
			public void onClick(boolean r, double cx, double cy)
			{
				WeltB.speichern("Levels/Test1", Staticf.wspg.k);
				super.onClick(r, cx, cy);
			}
		});
		godModeSchalter.add(new Schieber(sl, 0.4, 0.2, 0.2, 0.025, 0, 100, Koord.end[0])
		{
			public void tick()
			{
				Staticf.wspg.k[0] = (int)(shift * shiftm + startw);
				text = String.valueOf(Staticf.wspg.k[0]);
			}
		});
		godModeSchalter.add(new Schieber(sl, 0.4, 0.225, 0.2, 0.025, 0, 100, Koord.end[1])
		{
			public void tick()
			{
				Staticf.wspg.k[1] = (int)(shift * shiftm + startw);
				text = String.valueOf(Staticf.wspg.k[1]);
			}
		});
		godModeSchalter.add(new Schieber(sl, 0.4, 0.25, 0.2, 0.025, 0, 100, Koord.end[2])
		{
			public void tick()
			{
				Staticf.wspg.k[2] = (int)(shift * shiftm + startw);
				text = String.valueOf(Staticf.wspg.k[2]);
			}
		});
		godModeSchalter.add(new Schieber(sl, 0.4, 0.275, 0.2, 0.025, 0, 100, Koord.end[3])
		{
			public void tick()
			{
				Staticf.wspg.k[3] = (int)(shift * shiftm + startw);
				text = String.valueOf(Staticf.wspg.k[3]);
			}
		});
	}

	public static void resize()
	{
		hl = new BufferedImage(UIVerbunden.sc.width, UIVerbunden.sc.height, BufferedImage.TYPE_INT_ARGB_PRE);
		gd = hl.createGraphics();
		pa.resize(UIVerbunden.sc);
	}

	public static void overlay()
	{
		sl.draw(gd);
		gd.setColor(new Color(0, 0, 180));
		gd.setFont(new Font(null, Font.PLAIN, 20));
		if(UIVerbunden.x4dization > 0)
			gd.drawString(String.valueOf(UIVerbunden.zp.d), 50, 50);
		if(UIVerbunden.zp.z)
			gd.drawString("Z Bereit", 300, 50);
		if(UIVerbunden.zp.p)
			gd.drawString("P Bereit", 300, 100);
	}
}