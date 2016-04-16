package wahr.physisch;

import wahr.mage.*;
import wahr.zugriff.*;

class ThaCre2A
{
	public static void main(String[] args)
	{
		Staticf.initialCharge();
		Index.laden();
		Hauptschleife.init0();
		for(int i = 0; i < LPaneel.paneele.size(); i++)
			LPaneel.paneele.get(i).init1();
		Hauptschleife.init();
		for(int i = 0; i < LPaneel.paneele.size(); i++)
			LPaneel.paneele.get(i).init2();
		Hauptschleife.initOverlay();
		LagZeit.start();
		ZeitVerwalter.start();
		System.exit(0);
	}
}