package edit;

import achsen.*;
import java.util.*;
import javax.swing.*;
import k4.*;

public class AView extends JButton
{
	AchsenK1 ak1;
	String name;
	String drehfile;
	List<String> sichtbar;

	Drehung kamDreh;
	K4 kamMoved;
	double kamDistance;

	public AView(String name, String build, List<AchsenK1> liste) throws NumberFormatException
	{
		this.name = name;
		String[] werte = build.split(",");
		kamDistance = Double.parseDouble(werte[0]);
		kamDreh = new Drehung(Double.parseDouble(werte[1]), Double.parseDouble(werte[2]));
		kamMoved = new K4(Double.parseDouble(werte[3]), Double.parseDouble(werte[4]),
				Double.parseDouble(werte[5]), Double.parseDouble(werte[6]));
		if(!"keines".equals(werte[7]))
			drehfile = werte[7];
		sichtbar = new ArrayList<>();
		sichtbar.addAll(Arrays.asList(werte).subList(8, werte.length));
		setText(drehfile != null ? drehfile : "Sehe nichts");
		createAk1(liste);
	}

	public AView(String name, List<AchsenK1> liste)
	{
		this.name = name;
		kamDistance = 10;
		kamDreh = new Drehung();
		kamMoved = new K4();
		sichtbar = new ArrayList<>();
		setText("Sehe nichts");
		createAk1(liste);
	}

	public void createAk1(List<AchsenK1> liste)
	{
		ak1 = new AchsenK1(new K4(), new Drehung(), name);
		//Positionen.forderePositionAn(ak1);
		liste.add(ak1);
		actualize();
	}

	//TODO Funktion einbauen

	public void actualize()
	{
		if(drehfile != null)
		{
			try
			{
				ak1.reload(false, name, drehfile, sichtbar.toArray(new String[sichtbar.size()]));
			}catch(Exception e)
			{
				//Fehler
			}
		}
		else
			ak1.reload();
	}
}