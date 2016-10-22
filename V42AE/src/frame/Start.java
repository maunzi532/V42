package frame;

import a3.*;
import achsen.*;
import edit.*;
import indexLader.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import k4.*;

public class Start
{
	public static TA2 ta;
	static JFrame fr;
	static UI ui;
	static Vor1 vor1;
	static LichtW lw;
	public static List<AchsenK1> ak1s;
	public static AEKam kam;
	static Panelizer panelizer;
	static IFarbe ff2;
	static PolyFarbe achsenFarbe = new PolyFarbe("0-0-255");

	public static void main(String[] args)
	{
		ta = new TA2();
		ta.feedMoves(Lader4.readText(Lader4.bauName("E", "TA2"), true));
		fr = new JFrame();
		fr.setExtendedState(Frame.MAXIMIZED_BOTH);
		fr.setSize(800, 600);
		kam = new AEKam2(new K4(), 20, new Drehung(), 0.05);
		ui = new UI(fr, ta, kam);
		fr.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				ui.saveConfigs(kam);
				System.exit(0);
			}
		});
		fr.setVisible(true);
		while(true)
			if(fr.isActive())
				break;
		ak1s = new ArrayList<>();
		lw = new LichtW();
		lw.licht.add(new Licht()
		{
			@Override
			public K4 lichtPosition()
			{
				return new K4(10, 10, 5, 0);
			}

			@Override
			public double lichtRange()
			{
				return 100;
			}

			@Override
			public double lichtPower()
			{
				return 100;
			}

			@Override
			public double lichtPowerDecay()
			{
				return 0;
			}
		});
		vor1 = new Vor1(ak1s, lw, null);
		panelizer = new Panelizer();
		panelizer.resize(ui.right.getWidth(), ui.right.getHeight());
		ArrayList<Anzeige3> a3s2;
		ff2 = new PolyFarbe();
		LadeTeil1.factory = new PolyFarbe();
		while(true)
		{
			ta.move();
			if(ta.keyStat[0] > 0)
				break;
			kam.tick(ta.keyStat[1] > 0, ta.keyStat[2] > 0, ta.keyStat[3] > 0, ta.keyStat[4] > 0);
			ui.flt(ak1s);
			ak1s.forEach(AchsenK1::reset);
			vor1.vorbereiten(kam, ui.rWidth, ui.rHeight, ui.rWidth,
					null, ui.xr3v, ui.ac1v ? achsenFarbe : null, ui.fl2v);
			ArrayList<Anzeige3> a3s3 = new ArrayList<>();
			vor1.anzeige.stream().filter(e -> e.anzeigen).forEach(a3s3::add);
			a3s2 = a3s3;
			panelizer.panelize(a3s2, ui.rWidth / 4, ui.rHeight / 2);
			ui.right.getGraphics().drawImage(panelizer.light, 0, 0, null);
			sleep(10);
		}
		ui.saveConfigs(kam);
		System.exit(0);
	}

	public static void sleep(long millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch(InterruptedException ignored){}
	}
}