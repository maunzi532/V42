package welt;

import k4.*;

public class InBlockRaster
{
	//0 c+
	//1 a-
	//2 c-
	//3 a+

	//2  90|-a
	//-c   |    c
	//-----------
	//180  |    0
	//  270|a

	public static int drehIntD(double drehung)
	{
		drehung = Drehung.sichern(drehung);
		return (int)(drehung / Math.PI * 2);
	}

	public static int drehIntH(double drehung)
	{
		drehung = Drehung.sichern(drehung);
		return ((int)(drehung / Math.PI * 4) / 2) % 4;
	}

	public static int drehIntH2(double drehung)
	{
		drehung = Drehung.sichern(drehung);
		return (int)(drehung / Math.PI * 4) % 8;
	}

	public static int drehInt8(double drehung)
	{
		drehung = Drehung.sichern(drehung);
		return ((int)(drehung / Math.PI * 8) / 2) % 8;
	}

	int[][][][] raster;
	boolean nochOk = true;

	//true = horizontal -> hinten, vorne, links, rechts, unten, oben, -4d, +4d
	//false = diagonal -> lhinten, rhinten, lvorne, rvorne, unten, oben, -4d, +4d
	public InBlockRaster(WeltB w, K4 position, double drehung, boolean typ, double... area)
	{
		if(typ)
		{
			int dreh = drehIntH(drehung);
		}
		else
		{
			int dreh = drehIntD(drehung);
		}
		//Raster fuellen
	}

	private void rastern(int dreh, int hintenEnde, int vorneEnde, int linksEnde, int rechtsEnde,
			int untenEnde, int obenEnde, int m4dEnde, int p4dEnde)
	{

	}

	//horizontal -> hv, lr, uo, 4
	//diagonal -> lh-rv, lv-rh, uo, 4
	public void zusammenfassen(int richtung)
	{
		zusammenfassen(richtung, 0, 0);
	}

	public void zusammenfassen(int richtung, int startA, int endA)
	{

	}
}