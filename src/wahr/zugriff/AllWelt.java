package wahr.zugriff;

import a3.*;
import ansicht.text.*;
import nonBlock.aktion.*;
import nonBlock.collide.*;
import welt.*;

public class AllWelt
{
	public WeltB wbl;
	public WeltND dw;
	public LichtW lw;
	public WeltNB bw;
	public TextWelt tw;

	public AllWelt()
	{
		wbl = new WeltB();
		dw = new WeltND();
		lw = new LichtW();
		bw = new WeltNB(dw);
		tw = new TextWelt();
	}

	public void logik()
	{
		dw.timetickN();
		Staticf.sca("WeltND tN (4) ");
		bw.timetick();
		Staticf.sca("WeltNB t (0) ");
		dw.timetickD();
		Staticf.sca("WeltND tD (1) ");
	}
}