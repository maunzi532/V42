package nonBlock.aktion;

import java.util.*;
import nonBlock.aktion.lesen.*;

public class Freeze extends LinAAktion implements LadAktion
{
	Integer[] linA;

	public Freeze(){}

	@Override
	public ZDelay erzeuge(String whtd, NBD dislocated, NBD besitzer2, Tverlay tverlay,
			HashMap<String, String> parameters, ArrayList<String> list)
	{
		Integer[] linA2 = new Integer[list.size()];
		for(int i = 0; i < list.size(); i++)
			linA2[i] = Integer.parseInt(list.get(i));
		Freeze toR = new Freeze(dislocated,
				Integer.parseInt(parameters.get("dauer")),
				Integer.parseInt(parameters.get("power")), linA2);
		checkLinA(dislocated, toR);
		return null;
	}

	public Freeze(NBD besitzer, int dauer, int power, Integer... linA)
	{
		super(besitzer, dauer, power);
		this.linA = linA;
	}

	public void delink()
	{
		for(int i = 0; i < linA.length; i++)
			if(!needCancelAt[linA[i]])
			{
				besitzer.resLink[linA[i]] = null;
				R.summonR(besitzer, linA[i]);
			}
	}

	public static void checkLinA(NBD target, Freeze ak)
	{
		for(int i = 0; i < ak.linA.length; i++)
			if(target.resLink[ak.linA[i]] != null)
			{
				if(target.resLink[ak.linA[i]].power > ak.power)
				{
					ak.needCancel = true;
					ak.needCancelAt[ak.linA[i]] = true;
				}
				else
				{
					target.resLink[ak.linA[i]].needCancel = true;
					target.resLink[ak.linA[i]].needCancelAt[ak.linA[i]] = true;
				}
			}
		for(int i = 0; i < ak.linA.length; i++)
			if(!ak.needCancelAt[ak.linA[i]])
				ak.besitzer.resLink[ak.linA[i]] = ak;
		target.aktionen.add(ak);
	}
}
