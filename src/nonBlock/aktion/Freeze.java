package nonBlock.aktion;

import achsen.*;
import java.util.*;
import nonBlock.aktion.lesen.*;

public class Freeze extends LinAAktion implements LadAktion
{
	Integer[] linA;

	public Freeze(){}

	@Override
	public ZDelay erzeuge(String whtd, AkA dislocated, AkA besitzer2,
			HashMap<String, String> parameters, ArrayList<String> list, AkA[] akteure2)
	{
		Integer[] linA2 = new Integer[list.size()];
		for(int i = 0; i < list.size(); i++)
			linA2[i] = Integer.parseInt(list.get(i));
		Freeze toR = new Freeze((NonBlock) dislocated,
				Integer.parseInt(parameters.get("dauer")),
				Integer.parseInt(parameters.get("power")), linA2);
		checkLinA((NonBlock) dislocated, toR);
		return null;
	}

	public Freeze(NonBlock besitzer, int dauer, int power, Integer... linA)
	{
		super(besitzer, dauer, power);
		this.linA = linA;
	}

	public void delink()
	{
		for(int i = 0; i < linA.length; i++)
			if(!needCancelAt[linA[i]])
			{
				((NonBlock) besitzer).resLink[linA[i]] = null;
				R.summonR((NonBlock) besitzer, linA[i]);
			}
	}

	public static void checkLinA(NonBlock target, Freeze ak)
	{
		for(int i = 0; i < ak.linA.length; i++)
			if(target.resLink[ak.linA[i]] != null)
			{
				if(((LinAAktion) target.resLink[ak.linA[i]]).power > ak.power)
				{
					ak.needCancel = true;
					ak.needCancelAt[ak.linA[i]] = true;
				}
				else
				{
					((LinAAktion) target.resLink[ak.linA[i]]).needCancel = true;
					((LinAAktion) target.resLink[ak.linA[i]]).needCancelAt[ak.linA[i]] = true;
				}
			}
		for(int i = 0; i < ak.linA.length; i++)
			if(!ak.needCancelAt[ak.linA[i]])
				((NonBlock) ak.besitzer).resLink[ak.linA[i]] = ak;
		((AkA) target).addAktion(ak);
	}
}
