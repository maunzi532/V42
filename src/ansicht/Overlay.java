package ansicht;

import ansicht.text.*;
import block.*;
import falsch.*;

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
		hl = new BufferedImage(UIVerbunden.sc.width, UIVerbunden.sc.height, BufferedImage.TYPE_INT_ARGB_PRE);
		gd = hl.createGraphics();
		sl = new SchalterLayer();
		pa = new Panelizer(UIVerbunden.sc);
		normalSchalter = new ArrayList<>();
		normalSchalter.add(new SLF(sl, true, 0.1, 0.1, 0.1, 0.05)
		{
			public void onClick(boolean r)
			{
				if(r)
					pa.taType = pa.taType == 2 ? 0 : 2;
				else
					pa.taType = pa.taType == 1 ? 0 : 1;
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
			public void onClick(boolean r)
			{
				if(r)
					UIVerbunden.x4dization = UIVerbunden.x4dization > 0 ? 0 : 1;
				else
					UIVerbunden.x4dization = UIVerbunden.x4dization > 1 ? 0 : 2;
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
			public void onClick(boolean r)
			{
				UIVerbunden.d2tangibility = !UIVerbunden.d2tangibility;
			}

			public void tick()
			{
				if(UIVerbunden.d2tangibility)
					text = "Baumodus an";
				else
					text = "Baumodus aus";
			}
		});
		godModeSchalter = new ArrayList<>();
		godModeSchalter.add(new SLF(sl, true, 0.25, 0.1, 0.1, 0.05, "Speichern")
		{
			public void onClick(boolean r)
			{
				WeltB.speichern("Levels/Generiert1", new int[]{30, 50, 30, 4});
			}
		});
		godModeSchalter.add(new SLF(sl, true, 0.25, 0.2, 0.1, 0.05)
		{
			public void onClick(boolean r)
			{
				if(r)
					UIVerbunden.siehNonBlocks = !UIVerbunden.siehNonBlocks;
				else
					UIVerbunden.siehBlocks = !UIVerbunden.siehBlocks;
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
			public void onClick(boolean r)
			{
				UIVerbunden.xrmode = !UIVerbunden.xrmode;
			}

			public void tick()
			{
				if(UIVerbunden.xrmode)
					text = "XR an " + Staticf.xraywidth;
				else
					text = "XR aus";
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
		/*if(Staticf.x4dization > 0)
			gd.drawString(String.valueOf(Hauptschleife.n.position.d), 50, 50);
		WBP wbp = WeltB.tw(Hauptschleife.n.position);
		wbp.k[3]++;
		if(WeltB.gib(wbp) == 0)
			gd.drawString("Z Bereit", 300, 50);
		wbp.k[3] -= 2;
		if(WeltB.gib(wbp) == 0)
			gd.drawString("P Bereit", 300, 100);*/
		if(UIVerbunden.x4dization > 0)
			gd.drawString(String.valueOf(UIVerbunden.zp.d), 50, 50);
		if(UIVerbunden.zp.z)
			gd.drawString("Z Bereit", 300, 50);
		if(UIVerbunden.zp.p)
			gd.drawString("P Bereit", 300, 100);
	}
}