package wahr.mage;

import ansicht.*;
import ansicht.n2.*;
import block.*;
import block.generierung.*;
import nonBlock.aktion.*;
import nonBlock.aktion.seq.*;
import nonBlock.aussehen.*;
import nonBlock.aussehen.ext.*;
import nonBlock.collide.*;
import nonBlock.controllable.*;
import wahr.physisch.*;
import wahr.zugriff.*;

import java.awt.*;
import java.util.*;

public class Hauptschleife
{
	private static TSSA n;
	private static TSSA n2;
	public static Zeichner z;
	private static N2[] n2s2;

	public static void init()
	{
		Generator g = new WeltLeser();
		g.gibInWelt("Levels/Test1");
		//g.gibInWelt("Levels/Generiert1");
		//g.gibInWelt();
		//g.gibInWelt(4, 4);
		Koord.setzeSE(new K4(0, 0, 0, 0), new K4(20, 20, 20, 20));
		g.ermittleStart();
		n = new Tha();
		n.aussehen = new LadeModell().reload(
				Index.gibLadeTeil("T_H"),
				Index.gibLadeTeil("T_B"),
				Index.gibLadeTeil("T_A"),
				Index.gibLadeTeil("T_F"),
				Index.gibLadeTeil("T_K"),
				Index.gibLadeTeil("T_S"));
		Index.gibStandardAussehen("TSSA").assignStandard(n,
				new Enhance(new LadeModell().reload(Index.gibLadeTeil("T_HL2"))),
				new Enhance(new LadeModell().reload(Index.gibLadeTeil("T_HR2"))),
				new MK(0, 0.1, 0.5, 1, 8, 4, new int[]{32, 33, 34, 35, 36, 37, 38, 39}),
				//new MK(n, 0, 0.25, 1, 0.25, 1, 8, 14, new int[]{32, 33, 34, 35, 36, 37, 38, 39}),
				new H(0.5, 0.5, 10, 10, 4, 1, 0, 0.9),
				new H(0.2, 0.5, 4, 10, 7, 0.7, 0, 0.9),
				new H(0.2, 0.5, 4, 10, 7, 0.7, 0, 0.9),
				new H(0.2, 0.5, 4, 10, 3, 0.7, 0, 0.9),
				new H(0.2, 0.5, 4, 10, 7, 0.7, 0, 0.9)
		);
		n.position = WeltB.starts[0];
		n.position.b += n.block.get(0).airshift;
		n.dreh = new Drehung(1, 0);
		n.init();

		n2 = new TSSA(new Controller()
		{
			public ArrayList<String> giveCommands()
			{
				ArrayList<String> cmd = new ArrayList<>();
				if(TA2.keyStat[9] == 2)
					cmd.add("A");
				if(TA2.keyStat[10] == 2)
					cmd.add("B");
				if(TA2.keyStat[11] == 2)
					cmd.add("C");
				if(TA2.keyStat[15] == 2)
					cmd.add("KL");
				if(TA2.keyStat[16] == 2)
					cmd.add("KR");
				if(TA2.keyStat[6] == 2)
					cmd.add("unten");
				if(TA2.keyStat[5] == 2)
					cmd.add("oben");
				if(TA2.keyStat[3] == 2)
					cmd.add("vorne");
				if(TA2.keyStat[4] == 2)
					cmd.add("hinten");
				return cmd;
			}
		})
		{
			public void collide(Attk attk)
			{
				AktionM.checkLinA(this, new AktionM(this, 20, 1, ADI.deg(16, 2, 14, 3, 45, 0, 0, 0, false)));
			}

			public void actCollide(Attk attk){}

			public void decollide(Attk attk)
			{
				Index.gibAlternateStandard("TSSA2R").changeToThis(this);
			}

			public void wand(int welche){}

			public K4 kamP()
			{
				return new K4(punkte[73][14]);
			}

			public Drehung kamD()
			{
				return Drehung.plus(dreh, achsen[73].dreh);
			}

			public void doCommand(String command)
			{
				if(command.equals("C"))
				{
					WeltND.nfr = false;
					WeltND.seq = new Sequenz(Index.gibLadeSequenz("TSQ"), this, n);
				}
			}
		};
		n2.aussehen = new LadeModell().reload(

				Index.gibLadeTeil("T_H"),
				Index.gibLadeTeil("T_B"),
				Index.gibLadeTeil("T_A"),
				Index.gibLadeTeil("T_F"),
				Index.gibLadeTeil("T_K"),
				Index.gibLadeTeil("T_S"));
		Index.gibStandardAussehen("TSSA").assignStandard(n2,
				new Enhance(new LadeModell().reload(Index.gibLadeTeil("T_HL2"))),
				new Enhance(new LadeModell().reload(Index.gibLadeTeil("T_HR2"))),
				new MK(0, 0.1, 0.5, 1, 8, 4, new int[]{32, 33, 34, 35, 36, 37, 38, 39}),
				//new MK(0.25, 0.75, 0.25, 1, 8, 14, new int[]{32, 33, 34, 35, 36, 37, 38, 39}),
				new H2(n2, 0.5, 0.5, 10, 10, 4, 1, 0, 0.6),
				new H2(n2, 0.2, 0.5, 4, 10, 7, 0.7, 0, 0.6),
				new H2(n2, 0.2, 0.5, 4, 10, 7, 0.7, 0, 0.6),
				new H2(n2, 0.2, 0.5, 4, 10, 3, 0.7, 0, 0.6),
				new H2(n2, 0.2, 0.5, 4, 10, 7, 0.7, 0, 0.6)
				);
		n2.position = WeltB.starts[1];
		n2.position.b += n2.block.get(0).airshift;
		n2.dreh = new Drehung(Math.PI, 0);
		n2.init();
		UIVerbunden.kamN = n;
		UIVerbunden.kamA = UIVerbunden.kamN;
		z = new Zeichner(Index.gibText("SPL"));
		n2.collidable.add(new ColBox(n2, 0, new EndScheibe(4), new EndScheibe(4), 1, 1));
		n.physik.add(new ColBox(n, 69, new EndScheibe(1.6), new EndScheibe(1.3), 1.1));
		n.physik.add(new ColBox(n, 78, new EndScheibe(1.3), new EndScheibe(1), 1));
		n.physik.add(new ColBox(n, 11, new EndScheibe(0.7), new EndScheibe(0.7), 1));
		n.physik.add(new ColBox(n, 12, new EndScheibe(0.7), new EndScheibe(0.7), 1));
		n.physik.add(new ColBox(n, 0, new EndEllipse(2.1, 1.4, 0), new EndEllipse(2.1, 1.4, 0), 1));
		n2.physik.add(new ColBox(n2, 69, new EndScheibe(1.6), new EndScheibe(1.3), 1.1));
		n2.physik.add(new ColBox(n2, 78, new EndScheibe(1.3), new EndScheibe(1), 1));
		n2.physik.add(new ColBox(n2, 11, new EndScheibe(0.7), new EndScheibe(0.7), 1));
		n2.physik.add(new ColBox(n2, 12, new EndScheibe(0.7), new EndScheibe(0.7), 1));
		n2.physik.add(new ColBox(n2, 0, new EndEllipse(2.1, 1.4, 0), new EndEllipse(2.1, 1.4, 0), 1));
		n.aktionen.add(new Sicht(n, 10, 67, false));
		UIVerbunden.zp = new ZP4C(n, 0);
		n.aktionen.add(UIVerbunden.zp);
		WeltND.licht.add(n);
		WeltND.licht.add(n2);
		UIVerbunden.godModeKam = new Kamera(new GMC());
		UIVerbunden.godModeKam.position = new K4(n.position);
		UIVerbunden.godModeKam.dreh = new Drehung();
		UIVerbunden.godModeKam.canInfl = new double[]{1, 1, 1, 1};
		UIVerbunden.godModeKam.aussehen = new LadeModell();
		Index.gibStandardAussehen("Kam").assignStandard(UIVerbunden.godModeKam);
		UIVerbunden.godModeKam.aussehen.reload();
		UIVerbunden.godModeKam.elimit = 1;
		UIVerbunden.godModeKam.init();
		UIVerbunden.godModeKam.aktionen.add(new Sicht(UIVerbunden.godModeKam, 10, 0, true));
		try
		{
			UIVerbunden.ro = new Robot();
		}catch(AWTException e)
		{
			throw new RuntimeException(e);
		}
		LPaneel.setC0();
	}

	public static boolean eingabe()
	{
		if(UIVerbunden.sc.width != LPaneel.fr.getSize().width ||
				UIVerbunden.sc.height != LPaneel.fr.getSize().height)
		{
			UIVerbunden.sc = LPaneel.fr.getSize();
			Overlay.resize();
		}
		TA2.move();
		if(TA2.keyStat[0] > 0)
			return true;
		Staticf.sca("TA2 ");
		Point maus = MouseInfo.getPointerInfo().getLocation();
		Staticf.sca("Mx ");
		Point mm = LPaneel.fr.getLocationOnScreen();
		Staticf.sca("Mx1 ");
		maus.translate(-mm.x, -mm.y);
		if(TA2.keyStat[15] == 2)
		{
			if(Overlay.sl.click(maus.x, maus.y, false))
				TA2.keyStat[15] = 1;
		}
		else if(TA2.keyStat[16] == 2)
		{
			if(Overlay.sl.click(maus.x, maus.y, true))
				TA2.keyStat[16] = 1;
		}
		Staticf.sca("SL ");
		maus.translate(-UIVerbunden.sc.width / 2, -UIVerbunden.sc.height / 2);
		UIVerbunden.mausv = new Point(maus);
		//UIVerbunden.mausv.translate(-UIVerbunden.maus.x, -UIVerbunden.maus.y);
		UIVerbunden.maus = maus;
		if(TA2.keyStat[13] <= 0)
			UIVerbunden.ro.mouseMove(UIVerbunden.sc.width / 2 + mm.x, UIVerbunden.sc.height / 2 + mm.y);
		Staticf.sca("RO ");
		if(TA2.keyStat[13] == 2 && Overlay.sichtAn)
		{
			Overlay.sichtAn = false;
			Overlay.sl.layer.addAll(Overlay.normalSchalter);
			LPaneel.setC1();
		}
		if(TA2.keyStat[13] == -1 && !Overlay.sichtAn)
		{
			Overlay.sichtAn = true;
			Overlay.sl.layer.clear();
			LPaneel.setC0();
		}
		if(TA2.keyStat[17] == 2 && UIVerbunden.godModeKam != null)
		{
			UIVerbunden.godMode = !UIVerbunden.godMode;
			if(UIVerbunden.godMode)
			{
				UIVerbunden.kamA = UIVerbunden.godModeKam;
				Overlay.normalSchalter.addAll(Overlay.godModeSchalter);
				WeltND.licht.add(UIVerbunden.godModeKam);
			}
			else
			{
				UIVerbunden.kamA = UIVerbunden.kamN;
				Overlay.normalSchalter.removeAll(Overlay.godModeSchalter);
				WeltND.licht.remove(UIVerbunden.godModeKam);
				UIVerbunden.xrmode = false;
				UIVerbunden.siehBlocks = true;
				UIVerbunden.siehNonBlocks = true;
			}
		}
		Staticf.sca("M und T (0) ");
		return false;
	}

	public static void logik()
	{
		WeltND.timetickN();
		Staticf.sca("WeltND tN (7) ");
		WeltNB.timetick();
		Staticf.sca("WeltNB t (0) ");
		WeltND.timetickD();
		Staticf.sca("WeltND tD (0) ");
	}

	public static void rendern()
	{
		z.nehmen();
		Staticf.sca("Z nehmen (5) ");
		z.splittern();
		Staticf.sca("Z splittern (1) ");
		z.sortieren();
		Staticf.sca("Z sortieren (1) ");
		z.eckenEntf();
		Staticf.sca("Z eckenEntf (1) ");
		z.farbe_flaeche();
		Staticf.sca("Z farbeflaeche (3) ");
		N2[] n2s3 = new N2[z.n2s.size()];
		for(int i = 0; i < n2s3.length; i++)
			n2s3[i] = z.n2s.get(i);
		n2s2 = n2s3;
	}

	public static void panelize()
	{
		Overlay.pa.panelize(n2s2, UIVerbunden.maus.x + UIVerbunden.sc.width / 2,
				UIVerbunden.maus.y + UIVerbunden.sc.height / 2);
		Staticf.sca2("P panelize (14) ");
		Overlay.gd.drawImage(Overlay.pa.light, 0, 0, null);
		Staticf.sca2("O draw P (4) ");
		Overlay.overlay();
		Staticf.sca2("O overlay (0) ");
		//Hier Hauptthread
		LPaneel.rePanel(Overlay.hl);
		Staticf.sca2("LP rePanel (7) ");
	}
}