package nonBlock.aktion.move;

import ansicht.*;
import ansicht.text.*;
import nonBlock.aktion.*;
import nonBlock.collide.*;
import wahr.zugriff.*;

import java.util.*;

class LadeAktionB
{
	final int teil;
	final int zeit;
	private int typ = -1;
	private int dauer;
	private int power;
	private final double[] mvd = new double[6];
	private final Boolean[] mvd2 = new Boolean[6];
	private final ArrayList<Integer> linA = new ArrayList<>();
	private final ArrayList<ADI> adiA = new ArrayList<>();
	private Attk thatAttk;
	private int delay;
	private String text;
	private String dislocate;
	private String nhtDislocate;

	public LadeAktionB(int teil, String[] cd2)
	{
		this.teil = teil;
		zeit = Integer.parseInt(cd2[0]);
		switch(cd2[1])
		{
			case "AM":
				typ = 0;
				break;
			case "AF":
				typ = 1;
				break;
			case "T":
				typ = 2;
				break;
			case "Attk":
				typ = 3;
				break;
			case "MD":
				typ = 4;
				break;
			case "Alt":
				typ = 6;
				break;
			default:
				typ = -1;
		}
		for(int t = 2; t < cd2.length; t++)
		{
			String[] cd3 = cd2[t].split("=", 2);
			switch(cd3[0])
			{
				case "Dauer":
					dauer = Integer.parseInt(cd3[1]);
					break;
				case "Power":
					power = Integer.parseInt(cd3[1]);
					break;
				case "LinA":
					linA.add(Integer.parseInt(cd3[1]));
					break;
				case "dis":
					dislocate = cd3[1];
					break;
				case "ADI":
					adiA.add(new ADI(cd3[1], false));
					break;
				case "ADIZV":
					adiA.add(new ADI(cd3[1], true));
					break;
				case "T":
					text = cd3[1];
					break;
				case "Attk":
					thatAttk = new Attk(Integer.parseInt(cd3[1]));
					break;
				case "nht":
					nhtDislocate = cd3[1];
					break;
				case "hbox":
					thatAttk.h.add(new Hitbox(cd3[1]));
					break;
				case "delay":
					delay = Integer.parseInt(cd3[1]);
					break;
				case "aM":
					mvd2[0] = false;
					mvd[0] = Double.parseDouble(cd3[1]);
					break;
				case "aT":
					mvd2[0] = true;
					mvd[0] = Double.parseDouble(cd3[1]);
					break;
				case "bM":
					mvd2[1] = false;
					mvd[1] = Double.parseDouble(cd3[1]);
					break;
				case "bT":
					mvd2[1] = true;
					mvd[1] = Double.parseDouble(cd3[1]);
					break;
				case "cM":
					mvd2[2] = false;
					mvd[2] = Double.parseDouble(cd3[1]);
					break;
				case "cT":
					mvd2[2] = true;
					mvd[2] = Double.parseDouble(cd3[1]);
					break;
				case "dM":
					mvd2[3] = false;
					mvd[3] = Double.parseDouble(cd3[1]);
					break;
				case "dT":
					mvd2[3] = true;
					mvd[3] = Double.parseDouble(cd3[1]);
					break;
				case "wlM":
					mvd2[4] = false;
					mvd[4] = Double.parseDouble(cd3[1]) * Math.PI / 180;
					break;
				case "wlT":
					mvd2[4] = true;
					mvd[4] = Double.parseDouble(cd3[1]) * Math.PI / 180;
					break;
				case "wbM":
					mvd2[5] = false;
					mvd[5] = Double.parseDouble(cd3[1]) * Math.PI / 180;
					break;
				case "wbT":
					mvd2[5] = true;
					mvd[5] = Double.parseDouble(cd3[1]) * Math.PI / 180;
					break;
			}
		}
	}

	public AttkAktion erzeugeAktion(NBD besitzer)
	{
		NBD b2;
		if(dislocate != null)
			b2 = besitzer.plzDislocate(dislocate);
		else
			b2 = besitzer;
		switch(typ)
		{
			case 0:
				AktionM am = new AktionM(b2, dauer, power, adiA.toArray(new ADI[adiA.size()]));
				AktionM.checkLinA(b2, am);
				break;
			case 1:
				Freeze fm = new Freeze(b2, dauer, power, linA.toArray(new Integer[linA.size()]));
				Freeze.checkLinA(b2, fm);
				break;
			case 2:
				TBox st = new TBox(Overlay.sl, false, 0.2, 0.8, 0.1, 0.1, text);
				Overlay.sl.layer.add(st);
				break;
			case 3:
				AttkAktion ak = new AttkAktion((NBB) b2, dauer, power, thatAttk,
						(NBB)besitzer.plzDislocate(nhtDislocate));
				b2.aktionen.add(ak);
				if(delay > 0)
				{
					ak.delay = delay;
					return ak;
				}
				break;
			case 4:
				MDAktion md = new MDAktion(b2, dauer, mvd, mvd2);
				b2.aktionen.add(md);
				break;
			case 6:
				Index.gibAlternateStandard(text).changeToThis(b2);
				break;
		}
		return null;
	}
}