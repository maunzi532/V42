package ansicht;

import ansicht.n2.*;
import ansicht.text.*;
import nonBlock.aktion.*;
import nonBlock.aussehen.*;
import nonBlock.controllable.*;
import wahr.physisch.*;
import wahr.spieler.*;
import wahr.zugriff.*;

import java.awt.*;
import java.io.*;

public class Overlay
{
	public LPaneel auf;
	public SchalterLayer sl;
	public InitSL isl;
	public boolean sichtAn = true;
	public Panelizer pa;
	public AllWelt aw;
	public Zeichner z;
	private N2[] n2s2;
	//Spectator Modus
	public boolean godMode = false;
	//Spectator-Modus-Kamera
	public GMKamera godModeKam;
	//Zurzeit benutzte Kamera, falls nicht im godMode
	public Controllable kamN;
	//Index in TA2
	public int taIndex;
	public boolean schalterSichtbar;
	public DrehInput drehInput;

	public void initOverlay(int taIndex, AllWelt awA, String zDatLad, LPaneel auf)
	{
		this.taIndex = taIndex;
		this.auf = auf;
		aw = awA;
		z = new Zeichner(Index.gibText("Einstellungen", zDatLad), aw);
		sl = new SchalterLayer(this);
		pa = new Panelizer(auf.scF);
		isl = new InitSL(sl, this);
	}

	public void rendern()
	{
		z.nehmen(this);
		Staticf.sca("Z nehmen (5) ");
		z.splittern(godMode);
		Staticf.sca("Z splittern (1) ");
		z.sortieren();
		Staticf.sca("Z sortieren (1) ");
		z.eckenEntf(auf.scF);
		Staticf.sca("Z eckenEntf (1) ");
		z.farbe_flaeche(pa.tnTarget, auf.scF);
		Staticf.sca("Z farbeflaeche (3) ");
		N2[] n2s3 = new N2[z.n2s.size()];
		for(int i = 0; i < n2s3.length; i++)
			n2s3[i] = z.n2s.get(i);
		n2s2 = n2s3;
	}

	public void panelize()
	{
		if(schalterSichtbar)
			pa.panelize(n2s2, drehInput.xP(), drehInput.yP());
		else
			pa.panelize(n2s2, auf.scF.width / 2, auf.scF.height / 2);
		Staticf.sca2("Panelize (14) ");
		sl.draw(pa.gd);
		pa.gd.drawImage(Lader.gibBild(Index.gibPfad("Einstellungen") + File.separator + "ThaCursor.png"),
				drehInput.xP() - 10, drehInput.yP() - 10, 20, 20, null);
		Staticf.sca2("Overlay (0) ");
		auf.rePanel(pa.light, 0, 0); //TODO
		Staticf.sca2("RePanel (7) ");
	}

	public void erzeugeGMK(AllWelt aw, K4 gmkpos)
	{
		godModeKam = new GMKamera(new GMC(this), this, aw);
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

	public boolean eingabe()
	{
		TA2.move(taIndex);
		if(TA2.keyStat[taIndex][0] > 0)
			return true;
		boolean resize = false;
		for(int i = 0; i < LPaneel.paneele.size(); i++)
		{
			Dimension sc1 = LPaneel.paneele.get(i).fr.getSize();
			if(LPaneel.paneele.get(i).scF.width != sc1.width ||
					LPaneel.paneele.get(i).scF.height != sc1.height)
			{
				LPaneel.paneele.get(i).scF = sc1;
				resize = true;
			}
		}
		if(resize)
			pa.resize(auf.scF);
		Staticf.sca("TA2 ");
		schalterSichtbar = TA2.keyStat[taIndex][13] > 0;
		drehInput.ablesen(schalterSichtbar);
		Staticf.sca("DL ");
		if(schalterSichtbar)
		{
			if(TA2.keyStat[taIndex][15] == 2)
			{
				if(sl.click(drehInput.xP(), drehInput.yP(), false))
					TA2.keyStat[taIndex][15] = 1;
			}
			else if(TA2.keyStat[taIndex][16] == 2)
			{
				if(sl.click(drehInput.xP(), drehInput.yP(), true))
					TA2.keyStat[taIndex][16] = 1;
			}
		}
		Staticf.sca("SL ");
		if(TA2.keyStat[taIndex][13] == 2 && sichtAn)
		{
			sichtAn = false;
			sl.layer.addAll(isl.normalSchalter);
		}
		if(TA2.keyStat[taIndex][13] == -1 && !sichtAn)
		{
			sichtAn = true;
			sl.layer.clear();
		}
		if(TA2.keyStat[taIndex][17] == 2 && godModeKam != null)
		{
			godMode = !godMode;
			if(godMode)
			{
				isl.normalSchalter.addAll(isl.godModeSchalter);
				godModeKam.lw.licht.add(godModeKam);
			}
			else
			{
				isl.normalSchalter.removeAll(isl.godModeSchalter);
				godModeKam.lw.licht.remove(godModeKam);
				pa.xrmode = false;
				z.siehBlocks = true;
				z.siehNonBlocks = true;
			}
		}
		sl.actTex();
		Staticf.sca("M und T (0) ");
		return false;
	}
}