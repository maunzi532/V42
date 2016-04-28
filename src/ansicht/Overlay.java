package ansicht;

import ansicht.a3.*;
import ansicht.text.*;
import nonBlock.aktion.*;
import nonBlock.aktion.lesen.*;
import nonBlock.aussehen.*;
import nonBlock.controllable.*;
import wahr.physisch.*;
import wahr.spieler.*;
import wahr.zugriff.*;

import java.io.*;
import java.util.*;

public class Overlay extends Tverlay
{
	public TA2 ta;
	public LPaneel auf;
	private double[] ort;
	public int xI, yI, wI, hI;
	private InitSL isl;
	public boolean sichtAn = true;
	public Panelizer pa;
	public AllWelt aw;
	public Vor vor;
	private ArrayList<Anzeige3> a3s2;
	public boolean godMode = false;
	public GMKamera godModeKam;
	public Controllable kamN;
	public int taIndex;
	private boolean schalterSichtbar;
	public DrehInput drehInput;

	public void initOverlay(TA2 ta, int taIndex, AllWelt awA, String zDatLad, LPaneel auf, double[] ort)
	{
		this.ta = ta;
		this.taIndex = taIndex;
		this.auf = auf;
		this.ort = ort;
		aw = awA;
		tw = aw.tw;
		aw.tw.texters.add(this);
		vor = new Vor(Index.gibText("Einstellungen", zDatLad), aw);
		sl = new SchalterLayer();
		pa = new Panelizer();
		resize();
		isl = new InitSL(sl, this);
	}

	public void resize()
	{
		xI = (int) (ort[0] * auf.scF.width);
		yI = (int) (ort[1] * auf.scF.height);
		wI = (int) (ort[2] * auf.scF.width);
		hI = (int) (ort[3] * auf.scF.height);
		pa.resize(wI, hI);
		sl.resize(wI, hI);
	}

	public void vorbereiten()
	{
		vor.vorbereiten(kamZurZeit(), wI, hI, auf.scF.width, pa.tnTarget, pa.xrmode);
		ArrayList<Anzeige3> a3s3 = new ArrayList<>();
		for(int i = 0; i < vor.anzeige.size(); i++)
			if(vor.anzeige.get(i).anzeigen)
				a3s3.add(vor.anzeige.get(i));
		a3s2 = a3s3;
	}

	public void panelize()
	{
		Staticf.sca2("Start2 (0) ");
		if(schalterSichtbar)
			pa.panelize(a3s2, drehInput.xP(), drehInput.yP());
		else
			pa.panelize(a3s2, xI + wI / 2, yI + hI / 2);
		sl.draw(pa.gd);
		pa.gd.drawImage(Lader.gibBild(Index.gibPfad("Einstellungen") + File.separator + "ThaCursor.png"),
				drehInput.xP() - 16, drehInput.yP() - 16, 32, 32, null);
		Staticf.sca2("Overlay (0) ");
		auf.rePanel(pa.light, xI, yI);
		Staticf.sca2("RePanel (8) ");
	}

	public void erzeugeGMK(AllWelt aw, K4 gmkpos)
	{
		godModeKam = new GMKamera(new GMC(this), this, aw);
		godModeKam.position = new K4(gmkpos);
		godModeKam.dreh = new Drehung();
		godModeKam.canInfl = new double[]{1, 1, 1, 1};
		godModeKam.aussehen = new LadeModell();
		StandardAussehen.gibVonIndex2("Kam").assignStandard(godModeKam);
		godModeKam.aussehen.reload();
		godModeKam.init();
		godModeKam.aktionen.add(new Sicht(godModeKam, 10, 0, 0, true, this));
	}

	private Controllable kamZurZeit()
	{
		if(godMode)
			return godModeKam;
		return kamN;
	}

	public boolean eingabe()
	{
		ta.move(taIndex);
		if(ta.keyStat[taIndex][0] > 0)
			return true;
		schalterSichtbar = ta.keyStat[taIndex][13] > 0;
		drehInput.ablesen(schalterSichtbar);
		if(schalterSichtbar)
		{
			if(ta.keyStat[taIndex][15] == 2)
			{
				if(sl.click(drehInput.xP(), drehInput.yP(), false))
					ta.keyStat[taIndex][15] = 1;
			}
			else if(ta.keyStat[taIndex][16] == 2)
			{
				if(sl.click(drehInput.xP(), drehInput.yP(), true))
					ta.keyStat[taIndex][16] = 1;
			}
		}
		if(ta.keyStat[taIndex][13] == 2 && sichtAn)
		{
			sichtAn = false;
			sl.layer.addAll(isl.normalSchalter);
		}
		if(ta.keyStat[taIndex][13] == -1 && !sichtAn)
		{
			sichtAn = true;
			sl.layer.clear();
		}
		if(ta.keyStat[taIndex][17] == 2 && godModeKam != null)
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
				vor.siehBlocks = true;
				vor.siehNonBlocks = true;
			}
		}
		sl.actTex();
		Staticf.sca("M / T (1) ");
		return false;
	}
}