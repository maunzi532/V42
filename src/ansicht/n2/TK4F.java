package ansicht.n2;

import nonBlock.aussehen.*;
import wahr.zugriff.*;

public class TK4F
{
	//1x pro Punkt
	public static K4 zuPunkt(Achse achse, double abstand, double side, double vor, double spin,
			Drehung mDreh, K4 mPos)
	{
		K4 tLen = new K4(side, abstand, vor * achse.len, achse.dShift);
		tLen.transformWS(Drehung.plus(spin, achse.spin));
		tLen.transformWBL(achse.dreh);
		tLen = K4.plus(tLen, achse.start);
		tLen.transformWBL(mDreh);
		return K4.plus(tLen, mPos);
	}

	//Verbesserte Form H
	public static K4 zuPunktXH(Achse achse, double abstand, double side)
	{
		K4 tLen = new K4(side, abstand, 0, achse.dShift);
		tLen.transformWBL(achse.dreh);
		return K4.plus(tLen, achse.start);
	}

	//2x pro Achse
	public static K4 transformSet1(K4 thi, Drehung d, K4 add)
	{
		thi.transformWBL(d);
		return K4.plus(thi, add);
	}

	public static K4 mkT1(K4 thi, Drehung d, K4 add)
	{
		thi = K4.plus(thi, add);
		thi.transformWBL(d);
		return thi;
	}

	//1x pro Punkt, nur wenn nicht in Lag
	public static K4 transformSet2(K4 thi, Drehung d, K4 diff)
	{
		thi.transformKLB(d);
		if(diff == null)
			return thi;
		return K4.diff(diff, thi);
	}

	//1x pro LinkAchse (auch in Externals) + 1x pro Achse
	public static K4 achseEnde(Achse a, K4 tele)
	{
		if(a.len == 0)
		{
			if(tele != null)
				return new K4(a.start.a + tele.a, a.start.b + tele.b,
						a.start.c + tele.c, a.start.d + tele.d + a.dShift);
			else
				return new K4(a.start.a, a.start.b,
						a.start.c, a.start.d + a.dShift);
		}
		K4 tLen = new K4(0, 0, a.len, a.dShift);
		tLen.transformWBL(a.dreh);
		if(tele != null)
			tLen = K4.plus(tLen, tele);
		return K4.plus(a.start, tLen);
	}
}