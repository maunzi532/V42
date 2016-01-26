package nonBlock.aktion.lesen;

import ansicht.*;
import ansicht.text.*;
import nonBlock.aktion.*;
import nonBlock.collide.*;
import nonBlock.controllable.*;
import wahr.zugriff.*;

import java.util.*;

class LadeAktion
{
	public final int teil;
	public final int zeit;
	private int typ = -1;

	public int akteur = -1;

	private int dauer;
	private int power;

	private final double[] mvd = new double[6];
	private final Boolean[] mvd2 = new Boolean[6];
	private final ArrayList<Integer> linA = new ArrayList<>();
	private final ArrayList<ADI> adiA = new ArrayList<>();

	private Attk thatAttk;
	private int delay;

	private String text;
	private String dispName;
	private String codebez;
	private String emotion;

	private String dislocate;
	private String nhtDislocate;

	public LadeAktion(int teil, String[] cd2)
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
			case "TE":
				typ = 3;
				break;
			case "MD":
				typ = 4;
				break;
			case "Fokus":
				typ = 5;
				break;
			case "Alt":
				typ = 6;
				break;
			case "TP":
				typ = 7;
				break;
			case "Attk":
				typ = 8;
				break;
			default:
				typ = -1;
		}
		for(int t = 2; t < cd2.length; t++)
		{
			String[] cd3 = cd2[t].split("=", 2);
			switch(cd3[0])
			{
				case "Akt":
					akteur = Integer.parseInt(cd3[1]);
					break;
				case "Dauer":
					dauer = Integer.parseInt(cd3[1]);
					break;
				case "Power":
					power = Integer.parseInt(cd3[1]);
					break;
				case "LinA":
					linA.add(Integer.parseInt(cd3[1]));
					break;
				case "ADI":
					adiA.add(new ADI(cd3[1], false));
					break;
				case "ADIZV":
					adiA.add(new ADI(cd3[1], true));
					break;
				case "dis":
					dislocate = cd3[1];
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
				case "T":
					text = cd3[1];
					break;
				case "dispName":
					dispName = cd3[1];
					break;
				case "codebez":
					codebez = cd3[1];
					break;
				case "emotion":
					emotion = cd3[1];
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

	public ZDelay erzeugeAktion(NBD besitzer, Overlay overlay)
	{
		NBD b2;
		if(dislocate != null)
			b2 = besitzer.plzDislocate(dislocate);
		else
			b2 = besitzer;
		return erzeugeAktion(b2, besitzer, overlay);
	}

	private ZDelay erzeugeAktion(NBD dislocated, NBD besitzer2, Overlay overlay)
	{
		switch(typ)
		{
			case 0:
				AktionM am = new AktionM(dislocated, dauer, power, adiA.toArray(new ADI[adiA.size()]));
				AktionM.checkLinA(dislocated, am);
				break;
			case 1:
				Freeze fm = new Freeze(dislocated, dauer, power, linA.toArray(new Integer[linA.size()]));
				Freeze.checkLinA(dislocated, fm);
				break;
			case 2:
			case 3:
				TBox st2 = new TBox(overlay.sl, typ == 3, 0.1, 0.1, text);
				overlay.sl.placeTBox(st2, dispName, codebez, emotion);
				if(typ == 3)
					return st2;
				break;
			case 4:
				MDAktion md = new MDAktion(dislocated, dauer, power, mvd, mvd2);
				dislocated.aktionen.add(md);
				break;
			case 5:
				if(dislocated instanceof Controllable)
				{
					UIVerbunden.kamN = (Controllable)dislocated;
					if(!UIVerbunden.godMode)
						UIVerbunden.kamA = UIVerbunden.kamN;
				}
				break;
			case 6:
				Index.gibAlternateStandard(text).changeToThis(dislocated);
				break;
			case 7:
				tp(dislocated, mvd, mvd2);
				break;
			case 8:
				AttkAktion ak = new AttkAktion((NBB) dislocated, dauer, power, thatAttk,
						(NBB)besitzer2.plzDislocate(nhtDislocate));
				dislocated.aktionen.add(ak);
				if(delay > 0)
				{
					ak.delay = delay;
					return ak;
				}
				break;
		}
		return null;
	}

	private void tp(NBD target, double[] mvd, Boolean[] mvdA)
	{
		double[] mvd0 = new double[]{target.position.a, target.position.b,
				target.position.c, target.position.d, target.dreh.wl, target.dreh.wb};
		double[] mvdT = new double[6];
		for(int i = 0; i < 6; i++)
			if(mvdA[i] != null)
			{
				if(mvdA[i])
					mvdT[i] = mvd[i];
				else
					mvdT[i] = mvd0[i] + mvd[i];
			}
		mvdT[4] = Drehung.sichern(mvdT[4]);
		mvdT[5] = Drehung.sichern(mvdT[5]);
		if(mvdA[0] != null)
			target.position.a = mvdT[0];
		if(mvdA[1] != null)
			target.position.b = mvdT[1];
		if(mvdA[2] != null)
			target.position.c = mvdT[2];
		if(mvdA[3] != null)
			target.position.d = mvdT[3];
		if(mvdA[4] != null)
			target.dreh.wl = mvdT[4];
		if(mvdA[5] != null)
			target.dreh.wb = mvdT[5];
		target.forced.add(new Forced(new K4(), 20));
	}
}