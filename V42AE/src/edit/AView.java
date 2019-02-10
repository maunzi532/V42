package edit;

import achsen1.*;
import java.util.*;
import javax.swing.*;
import k4.*;

public class AView extends JButton
{
	public AchsenK1 ak1;
	String name;
	String drehfile;
	List<String> sichtbar;

	double kamDistance;
	Drehung kamDreh;
	K4 kamMoved;

	public AView(String name, String build, List<AchsenK1> liste, AEKam aeKam) throws NumberFormatException
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
		text();
		createAk1(liste, aeKam);
	}

	public AView(String name, List<AchsenK1> liste, AEKam aeKam)
	{
		this.name = name;
		kamDistance = 10;
		kamDreh = new Drehung();
		kamMoved = new K4();
		sichtbar = new ArrayList<>();
		text();
		createAk1(liste, aeKam);
	}

	public void text()
	{
		setText(drehfile != null ? drehfile : "Sehe nichts");
	}

	public void createAk1(List<AchsenK1> liste, AEKam aeKam)
	{
		ak1 = new AchsenK1(new K4(), new Drehung(), name);
		aeKam.forderePositionAn(ak1);
		liste.add(ak1);
		actualize();
	}

	public void aktivieren(List<EditerTab> dTabs, List<EditerTab> fTabs)
	{
		dTabs.stream().filter(dTab -> dTab.name.equals(drehfile))
				.forEach(dTab -> dTab.radioButton.setSelected(true));
		fTabs.stream().forEach(fTab -> fTab.checkBox.setSelected(sichtbar.contains(fTab.name)));
	}

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

	public void actualize(Standard1 sta, HashMap<String, Alternate1> alts, HashMap<String, AnzTeil1> teils)
	{
		ak1.reload(sta, alts.get(drehfile), sichtbar.stream().map(teils::get).toArray(AnzTeil1[]::new));
	}

	public void avac(AEKam kam)
	{
		kamDistance = kam.avAbstand();
		kamDreh = kam.avDreh();
		kamMoved = kam.zP;
	}

	public StringBuilder saveText()
	{
		StringBuilder sb = new StringBuilder();
		sb.append('\n').append(kamDistance).append(", ").append(kamDreh.wl).append(", ").append(kamDreh.wb)
				.append(", ").append(kamMoved.a).append(", ").append(kamMoved.b).append(", ")
				.append(kamMoved.c).append(", ").append(kamMoved.d).append(", ").append(drehfile);
		for(int i = 0; i < sichtbar.size(); i++)
			sb.append(", ").append(sichtbar.get(i));
		return sb.append(';');
	}
}