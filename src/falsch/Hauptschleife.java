package falsch;

import ansicht.*;
import block.*;
import block.generierung.*;
import fnd.*;
import k.*;
import modell.*;
import modell.ext.*;
import nbc.*;
import nonblockbox.*;
import nonblockbox.aktion.*;
import nonblockbox.aktion.seq.*;
import nonblockbox.attk.*;

import java.awt.*;
import java.util.*;

public class Hauptschleife
{
	public static TSSA n;
	static TSSA n2;
	public static Point maus;
	public static Kamera godModeKam;
	public static Zeichner z;
	public static Controllable kamN;
	public static ZP4C zp;
	public static N2[] n2s2;

	public static void init()
	{
		Generator g = new TestGenerator();
		//((WeltLeser) g).ort = "Levels/Generiert1";
		g.gibInWelt();
		Koord.setzeSE(new K4(0, 0, 0, 0), new K4(20, 20, 20, 20));
		g.ermittleStart();
		n = new Tha();
		n.aussehen = new LadeModell().reload(new External[]{
						new H(n, 71, 0.5, 0.5, 10, 10, 6, 1, 0, 0, 0),
						//new H(n, 74, 0.2, 0.5, 4, 10, 6, 0.5, 0, 0, -0.2),
						new H(n, 75, 0.2, 0.5, 4, 10, 6, 0.5, 0, 0, -0.2),
						new H(n, 76, 0.2, 0.5, 4, 10, 6, 0.5, 0, 0, -0.2),
						new H(n, 77, 0.2, 0.5, 4, 10, 6, 0.5, 0, 0, -0.2),
						new MK(n, 0, 0, 0.1, 0.5, 1, 8, 4, new int[]{32, 33, 34, 35, 36, 37, 38, 39}),
						//new MK(n, 0, 0.25, 1, 0.25, 1, 8, 14, new int[]{32, 33, 34, 35, 36, 37, 38, 39}),
				},
				Index.gibLadeTeil("T_H"),
				Index.gibLadeTeil("T_B"),
				Index.gibLadeTeil("T_A"),
				Index.gibLadeTeil("T_F"),
				Index.gibLadeTeil("T_K"),
				Index.gibLadeTeil("T_S"));
		Index.gibStandardAussehen("TSSA").assignStandard(n,
				new LadeModell().reload(Index.gibLadeTeil("T_HL2")),
				new LadeModell().reload(Index.gibLadeTeil("T_HR2")));
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
				System.out.println("collide");
				AktionM.checkLinA(this, new AktionM(this, 20, 1, ADI.deg(16, 2, 14, 3, 45, 0, 0, 0, false)));
			}

			public void actCollide(Attk attk)
			{
				System.out.println("actcollide");
			}

			public void decollide(Attk attk)
			{
				System.out.println("decollide");
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
		n2.aussehen = new LadeModell().reload(new External[]{
						new H2(n2, 71, 0.5, 0.5, 10, 10, 6, 1, 0, 0, 0),
						new H2(n2, 74, 0.2, 0.5, 4, 10, 7, 0.5, 0, 0, -0.2),
						new H2(n2, 75, 0.2, 0.5, 4, 10, 7, 0.5, 0, 0, -0.2),
						new H2(n2, 76, 0.2, 0.5, 4, 10, 4, 0.5, 0, 0, -0.2),
						new H2(n2, 77, 0.2, 0.5, 4, 10, 7, 0.5, 0, 0, -0.2),
						new MK(n2, 0, 0, 0.1, 0.5, 1, 8, 4, new int[]{32, 33, 34, 35, 36, 37, 38, 39}),
						//new MK(n2, 0, 0.25, 0.75, 0.25, 1, 8, 14, new int[]{32, 33, 34, 35, 36, 37, 38, 39}),
				},
				Index.gibLadeTeil("T_H"),
				Index.gibLadeTeil("T_B"),
				Index.gibLadeTeil("T_A"),
				Index.gibLadeTeil("T_F"),
				Index.gibLadeTeil("T_K"),
				Index.gibLadeTeil("T_S"));
		Index.gibStandardAussehen("TSSA").assignStandard(n2,
				new LadeModell().reload(Index.gibLadeTeil("T_HL2")),
				new LadeModell().reload(Index.gibLadeTeil("T_HR2")));
		n2.position = WeltB.starts[1];
		n2.position.b += n2.block.get(0).airshift;
		n2.dreh = new Drehung(Math.PI, 0);
		n2.init();
		kamN = n;
		z = new Zeichner(kamN, Index.gibText("SPL"));
		n2.collidable.add(new ColBox(n2, 0, 4, 4, 1, 1));
		n2.physik.add(new ColBox(n2, 69, 1.5, 1.6, 1.1));
		n2.physik.add(new ColBox(n2, 78, 1.5, 1, 1));
		n2.physik.add(new ColBox(n2, 11, 0.7, 0.7, 1));
		n2.physik.add(new ColBox(n2, 12, 0.7, 0.7, 1));
		n2.physik.add(new ColBox(n2, 0, 1.8, 1.8, 1));
		n.aktionen.add(new Sicht(n, 10, 67, false));
		zp = new ZP4C(n, 0);
		n.aktionen.add(zp);
		WeltND.licht.add(n);
		WeltND.licht.add(n2);
		godModeKam = new Kamera(new GMC());
		godModeKam.position = new K4(n.position);
		godModeKam.dreh = new Drehung();
		godModeKam.canInfl = new double[]{1, 1, 1, 1};
		godModeKam.aussehen = new LadeModell();
		Index.gibStandardAussehen("Kam").assignStandard(godModeKam);
		godModeKam.aussehen.reload();
		godModeKam.elimit = 1;
		godModeKam.init();
		godModeKam.aktionen.add(new Sicht(godModeKam, 10, 0, true));
	}

	public static boolean eingabe()
	{
		if(Staticf.sc.width != LPaneel.fr.getSize().width ||
				Staticf.sc.height != LPaneel.fr.getSize().height)
		{
			Staticf.sc = LPaneel.fr.getSize();
			Overlay.resize();
		}
		TA2.move();
		if(TA2.keyStat[0] > 0)
			return true;
		maus = MouseInfo.getPointerInfo().getLocation();
		Point mm = LPaneel.fr.getLocationOnScreen();
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
		Overlay.sl.tick();
		maus.translate(-Staticf.sc.width / 2, -Staticf.sc.height / 2);
		if(TA2.keyStat[13] == 2 && Overlay.sichtAn)
		{
			Overlay.sichtAn = false;
			Overlay.sl.layer.addAll(Overlay.normalSchalter);
		}
		if(TA2.keyStat[13] == -1 && !Overlay.sichtAn)
		{
			Overlay.sichtAn = true;
			Overlay.sl.layer.clear();
		}
		if(TA2.keyStat[17] == 2 && godModeKam != null)
		{
			Staticf.godMode = !Staticf.godMode;
			if(Staticf.godMode)
			{
				z.kam = godModeKam;
				Overlay.normalSchalter.addAll(Overlay.godModeSchalter);
				WeltND.licht.add(godModeKam);
			}
			else
			{
				z.kam = kamN;
				Overlay.normalSchalter.removeAll(Overlay.godModeSchalter);
				WeltND.licht.remove(godModeKam);
				Staticf.xrmode = false;
				Staticf.siehBlocks = true;
				Staticf.siehNonBlocks = true;
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
		Staticf.sca("WeltND tD (5) ");
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
		Overlay.pa.panelize(n2s2, maus.x + Staticf.sc.width / 2,
				maus.y + Staticf.sc.height / 2);
		Staticf.sca("		P panelize (14) ");
		Overlay.gd.drawImage(Overlay.pa.light, 0, 0, null);
		Staticf.sca("		O draw P (4) ");
		Overlay.overlay();
		Staticf.sca("		O overlay (0) ");
		//Hier Hauptthread
		LPaneel.rePanel(Overlay.hl);
		Staticf.sca("		LP rePanel (7) ");
	}
}