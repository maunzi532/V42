package wahr.spieler;

import ansicht.*;
import nonBlock.aktion.*;
import nonBlock.aussehen.*;
import nonBlock.controllable.*;
import wahr.physisch.*;
import wahr.zugriff.*;

import java.awt.*;

public class Spieler
{
	public Overlay overlay;
	//Spectator Modus
	public boolean godMode = false;
	//Spectator-Modus-Kamera
	public GMKamera godModeKam;
	//Zurzeit benutzte Kamera, falls nicht im godMode
	public Controllable kamN;
	//Index in TA2
	public int taIndex;

	//Letzte Mausposition
	public Point maus = new Point();
	//Maus Robot
	public Robot ro;
	//Mausverschiebung
	public Point mausv;
	//Maus Letzter Fokus
	public Point mausLast;

	public Spieler(Overlay overlay, int taIndex)
	{
		this.overlay = overlay;
		this.taIndex = taIndex;
	}

	public void erzeugeGMK(AllWelt aw, K4 gmkpos)
	{
		godModeKam = new GMKamera(new GMC(this), overlay, aw);
		godModeKam.position = new K4(gmkpos);
		godModeKam.dreh = new Drehung();
		godModeKam.canInfl = new double[]{1, 1, 1, 1};
		godModeKam.aussehen = new LadeModell();
		Index.gibStandardAussehen("Kam").assignStandard(godModeKam);
		godModeKam.aussehen.reload();
		godModeKam.init();
		godModeKam.aktionen.add(new Sicht(godModeKam, 10, 0, 0, true, this));
	}

	public Controllable kamZurZeit()
	{
		if(godMode)
			return godModeKam;
		return kamN;
	}

	public boolean eingabe(boolean resize)
	{
		TA2.move(taIndex);
		if(TA2.keyStat[taIndex][0] > 0)
			return true;
		if(resize)
			overlay.resize();
		Staticf.sca("TA2 ");
		//Drehinput
		Point mausNeu = MouseInfo.getPointerInfo().getLocation();
		Staticf.sca("Mx ");
		Point mm = LPaneel.fr.getLocationOnScreen();
		Staticf.sca("Mx1 ");
		mausNeu.translate(-mm.x, -mm.y);
		//mausNeu ist jetzt location in Frame
		if(TA2.keyStat[taIndex][13] <= 0)
			ro.mouseMove(mausLast.x + mm.x, mausLast.y + mm.y); //zurueck wo du warst
		else
			mausLast = new Point(mausNeu); //wo du warst wird gesetzt
		//Drehinput ende
		if(TA2.keyStat[taIndex][15] == 2)
		{
			if(overlay.sl.click(mausNeu.x, mausNeu.y, false)) //so geht das nicht
				TA2.keyStat[taIndex][15] = 1;
		}
		else if(TA2.keyStat[taIndex][16] == 2)
		{
			if(overlay.sl.click(mausNeu.x, mausNeu.y, true))
				TA2.keyStat[taIndex][16] = 1;
		}
		Staticf.sca("SL ");
		//Drehinput
		mausNeu.translate(-mausLast.x, -mausLast.y); //Unterschied
		mausv = new Point(mausNeu);
		maus = mausNeu; //warum beides?
		Staticf.sca("RO ");
		//Drehinput ende
		if(TA2.keyStat[taIndex][13] == 2 && overlay.sichtAn)
		{
			overlay.sichtAn = false;
			overlay.sl.layer.addAll(overlay.normalSchalter);
			LPaneel.setC1();
		}
		if(TA2.keyStat[taIndex][13] == -1 && !overlay.sichtAn)
		{
			overlay.sichtAn = true;
			overlay.sl.layer.clear();
			LPaneel.setC0();
		}
		if(TA2.keyStat[taIndex][17] == 2 && godModeKam != null)
		{
			godMode = !godMode;
			if(godMode)
			{
				overlay.normalSchalter.addAll(overlay.godModeSchalter);
				godModeKam.lw.licht.add(godModeKam);
			}
			else
			{
				overlay.normalSchalter.removeAll(overlay.godModeSchalter);
				godModeKam.lw.licht.remove(godModeKam);
				overlay.pa.xrmode = false;
				overlay.z.siehBlocks = true;
				overlay.z.siehNonBlocks = true;
			}
		}
		overlay.sl.actTex();
		Staticf.sca("M und T (0) ");
		return false;
	}
}