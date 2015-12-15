package fnd;

import ansicht.*;
import falsch.*;

public class ThaCre2A
{
	public static void main(String[] args)
	{
		Staticf.initialCharge();
		Index.laden();
		LPaneel.init1();
		Hauptschleife.init();
		LPaneel.init2();
		Overlay.initOverlay();
		LagZeit.start();
		ZeitVerwalter.start();
		System.exit(0);
	}
}