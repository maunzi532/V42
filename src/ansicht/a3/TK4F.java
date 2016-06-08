package ansicht.a3;

import nonBlock.aussehen.*;
import wahr.zugriff.*;

public class TK4F
{

	//Verbesserte Form H
	public static K4 zuPunktXH(Achse achse, double abstand, double side)
	{
		K4 tLen = new K4(side, abstand, 0, achse.dShift);
		tLen.transformWBL(achse.dreh);
		return K4.plus(tLen, achse.start);
	}

	public static K4 mkT1(K4 thi, Drehung d, K4 add)
	{
		thi = K4.plus(thi, add);
		thi.transformWBL(d);
		return thi;
	}

}