package nonblockbox.aktion;

public class Freeze extends Aktion
{
	public static void checkLinA(NBD target, Freeze ak)
	{
		for(int i = 0; i < ak.linA.length; i++)
		{
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
		}
		for(int i = 0; i < ak.linA.length; i++)
			if(!ak.needCancelAt[ak.linA[i]])
				ak.besitzer.resLink[ak.linA[i]] = ak;
		target.aktionen.add(ak);
	}

	private final Integer[] linA;

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
}
