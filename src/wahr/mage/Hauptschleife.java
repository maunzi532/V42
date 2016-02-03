package wahr.mage;

import ansicht.*;
import block.generierung.*;
import nonBlock.aktion.*;
import nonBlock.aktion.lesen.*;
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
	static AllWelt aw;
	public static Overlay theOverlay;

	public static void init()
	{
		theOverlay = new Overlay();
		aw = new AllWelt();
		Generator g = new WeltLeser();
		g.gibInWelt(aw.wbl, "Levels/Test1");
		//g.gibInWelt("Levels/Generiert1");
		//g.gibInWelt();
		//g.gibInWelt(4, 4);
		aw.wbl.setzeSE(new K4(0, 0, 0, 0), new K4(20, 20, 20, 20));
		g.ermittleStart();

		n = new Tha(theOverlay, aw);
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
				new H(0.5, 0.5, 10, 10, 4, 1, 0, 0.9),
				new H(0.2, 0.5, 4, 10, 7, 0.7, 0, 0.9),
				new H(0.2, 0.5, 4, 10, 7, 0.7, 0, 0.9),
				new H(0.2, 0.5, 4, 10, 3, 0.7, 0, 0.9),
				new H(0.2, 0.5, 4, 10, 7, 0.7, 0, 0.9)
		);
		n.position = aw.wbl.starts[0];
		n.position.b += n.block.get(0).airshift;
		n.dreh = new Drehung(1, 0);
		n.init();

		TSSA n2 = new TSSA(new Controller()
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
		}, null, aw)
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
				if(command.equals("B"))
				{
					dw.seq = new Move(Index.gibLadeMove("TPSQ"), theOverlay, this, n);
				}
				if(command.equals("C"))
				{
					dw.seq = new Move(Index.gibLadeMove("TSQ"), theOverlay, this, n);
				}
			}
		};
		MPS mp2 = new MPS();
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
				new H2(n2, 0.5, 0.5, 10, 10, 4, 1, 0, 0.6),
				new H2(n2, 0.2, 0.5, 4, 10, 7, 0.7, 0, 0.55),
				new H2(n2, 0.2, 0.5, 4, 10, 7, 0.7, 0, 0.55),
				new H2(n2, 0.2, 0.5, 4, 10, 3, 0.7, 0, 0.55),
				new H2(n2, 0.2, 0.5, 4, 10, 7, 0.7, 0, 0.55),
				mp2
				);
		n2.position = aw.wbl.starts[1];
		n2.position.b += n2.block.get(0).airshift;
		mp2.init();
		n2.dreh = new Drehung(Math.PI, 0);
		n2.init();
		UIVerbunden.kamN = n;
		UIVerbunden.kamA = UIVerbunden.kamN;
		n2.collidable.add(new ColBox(n2, 0, new EndScheibe(4), new EndScheibe(4), 1, 1));
		n2.physik.add(new ColBox(n2, 69, new EndScheibe(1.5), new EndScheibe(1.0), 1.1));
		n2.physik.add(new ColBox(n2, 78, new EndScheibe(1.0), new EndScheibe(0.7), 0.7));
		n2.physik.add(new ColBox(n2, 11, new EndScheibe(0.7), new EndScheibe(0.7), 1));
		n2.physik.add(new ColBox(n2, 12, new EndScheibe(0.7), new EndScheibe(0.7), 1));
		n2.physik.add(new ColBox(n2, 0, new EndEllipse(2.1, 1.2, 0), new EndEllipse(2.2, 1.4, 0), 1));
		n.aktionen.add(new Sicht(n, 10, 67, 67, false, theOverlay));
		UIVerbunden.zp = new ZP4C(n, 0);
		n.aktionen.add(UIVerbunden.zp);
		//aw.lw.licht.add(n);
		//aw.lw.licht.add(n2);
		UIVerbunden.godModeKam = new GMKamera(new GMC(), theOverlay, aw);
		UIVerbunden.godModeKam.position = new K4(n.position);
		UIVerbunden.godModeKam.dreh = new Drehung();
		UIVerbunden.godModeKam.canInfl = new double[]{1, 1, 1, 1};
		UIVerbunden.godModeKam.aussehen = new LadeModell();
		Index.gibStandardAussehen("Kam").assignStandard(UIVerbunden.godModeKam);
		UIVerbunden.godModeKam.aussehen.reload();
		UIVerbunden.godModeKam.elimit = 1;
		UIVerbunden.godModeKam.init();
		UIVerbunden.godModeKam.aktionen.add(new Sicht(UIVerbunden.godModeKam, 10, 0, 0, true, theOverlay));
		try
		{
			UIVerbunden.ro = new Robot();
		}catch(AWTException e)
		{
			throw new RuntimeException(e);
		}
		LPaneel.setC0();
	}

	public static void initOverlay()
	{
		theOverlay.initOverlay(aw, "SPL");
	}

	public static boolean eingabe()
	{
		TA2.move();
		if(TA2.keyStat[0] > 0)
			return true;
		if(UIVerbunden.sc.width != LPaneel.fr.getSize().width ||
				UIVerbunden.sc.height != LPaneel.fr.getSize().height)
		{
			UIVerbunden.sc = LPaneel.fr.getSize();
			theOverlay.resize();
		}
		Staticf.sca("TA2 ");
		Point maus = MouseInfo.getPointerInfo().getLocation();
		Staticf.sca("Mx ");
		Point mm = LPaneel.fr.getLocationOnScreen();
		Staticf.sca("Mx1 ");
		maus.translate(-mm.x, -mm.y);
		if(TA2.keyStat[13] <= 0)
			UIVerbunden.ro.mouseMove(UIVerbunden.mausLast.x + mm.x, UIVerbunden.mausLast.y + mm.y);
		else
			UIVerbunden.mausLast = new Point(maus);
		if(TA2.keyStat[15] == 2)
		{
			if(theOverlay.sl.click(maus.x, maus.y, false))
				TA2.keyStat[15] = 1;
		}
		else if(TA2.keyStat[16] == 2)
		{
			if(theOverlay.sl.click(maus.x, maus.y, true))
				TA2.keyStat[16] = 1;
		}
		Staticf.sca("SL ");
		maus.translate(-UIVerbunden.mausLast.x, -UIVerbunden.mausLast.y);
		UIVerbunden.mausv = new Point(maus);
		UIVerbunden.maus = maus;
		Staticf.sca("RO ");
		if(TA2.keyStat[13] == 2 && theOverlay.sichtAn)
		{
			theOverlay.sichtAn = false;
			theOverlay.sl.layer.addAll(theOverlay.normalSchalter);
			LPaneel.setC1();
		}
		if(TA2.keyStat[13] == -1 && !theOverlay.sichtAn)
		{
			theOverlay.sichtAn = true;
			theOverlay.sl.layer.clear();
			LPaneel.setC0();
		}
		if(TA2.keyStat[17] == 2 && UIVerbunden.godModeKam != null)
		{
			UIVerbunden.godMode = !UIVerbunden.godMode;
			if(UIVerbunden.godMode)
			{
				UIVerbunden.kamA = UIVerbunden.godModeKam;
				theOverlay.normalSchalter.addAll(theOverlay.godModeSchalter);
				aw.lw.licht.add(UIVerbunden.godModeKam);
			}
			else
			{
				UIVerbunden.kamA = UIVerbunden.kamN;
				theOverlay.normalSchalter.removeAll(theOverlay.godModeSchalter);
				aw.lw.licht.remove(UIVerbunden.godModeKam);
				theOverlay.pa.xrmode = false;
				theOverlay.z.siehBlocks = true;
				theOverlay.z.siehNonBlocks = true;
			}
		}
		theOverlay.sl.actTex();
		Staticf.sca("M und T (0) ");
		return false;
	}
}