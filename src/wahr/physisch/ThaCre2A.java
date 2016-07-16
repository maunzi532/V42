package wahr.physisch;

import ansicht.*;
import indexLader.*;
import wahr.mage.*;
import wahr.spieler.*;
import wahr.zugriff.*;

public class ThaCre2A
{
	public static LPaneel[] paneele = new LPaneel[1];
	public static Overlay[] theOverlays;
	public static TA2 theTA;

	public static void main(String[] args)
	{
		Index.laden("T ");
		Staticf.scaCharge(Index.gibText("Einstellungen", "Sca"));
		theTA = new TA2();
		initPaneele();
		Hauptschleife.initWelt();
		for(int i = 0; i < paneele.length; i++)
			paneele[i].showFrames();
		createOverlays();
		LagZeit.start();
		ZeitVerwalter.start();
		System.exit(0);
	}

	public static void initPaneele()
	{
		String text = Index.gibText("Einstellungen", "Fenster");
		String[] zeilen = text.split("\n");
		paneele = new LPaneel[zeilen.length];
		for(int i = 0; i < zeilen.length; i++)
		{
			String[] teile = zeilen[i].split(" ");
			boolean max = false;
			boolean undecorated = false;
			int sx = 0;
			int sy = 0;
			boolean sxset = false;
			for(int j = 2; j < teile.length; j++)
				switch(teile[j])
				{
					case "max":
						max = true;
						break;
					case "undecorated":
						undecorated = true;
						break;
					default:
						if(sxset)
							sy = Integer.parseInt(teile[j]);
						else
						{
							sx = Integer.parseInt(teile[j]);
							sxset = true;
						}
				}
			paneele[i] = new LPaneel(theTA, Integer.parseInt(teile[0]), Integer.parseInt(teile[1]),
					max, undecorated, sx, sy);
		}
	}

	public static void createOverlays()
	{
		int spielerzahl = Integer.parseInt(Index.gibText("Einstellungen", "Spielerzahl"));
		theTA.setzeAnz(spielerzahl);
		theOverlays = new Overlay[spielerzahl];
		for(int i = 0; i < spielerzahl; i++)
			theOverlays[i] = new Overlay();
		String text = Index.gibText("Einstellungen", "Overlays" + spielerzahl);
		String[] zeilen = text.split("\n");
		for(int i = 0; i < spielerzahl; i++)
		{
			String[] teile = zeilen[i].split(" ");
			Hauptschleife.giveNToOverlay(theOverlays[i]);
			double[] ort = new double[4];
			for(int j = 0; j < 4; j++)
				ort[j] = Double.parseDouble(teile[j + 4]);
			theTA.feedMoves(Index.gibText("Einstellungen", teile[3]), i);
			LPaneel auf = paneele[Integer.parseInt(teile[1])];
			theOverlays[i].initOverlay(theTA, i, Hauptschleife.aw, teile[0], auf, ort);
			switch(teile[2])
			{
				case "Maus":
					theOverlays[i].drehInput = new MausDrehInput(theOverlays[i]);
					break;
				case "Tasten":
					theOverlays[i].drehInput = new TastenDrehInput(theOverlays[i]);
					break;
			}
		}
	}
}