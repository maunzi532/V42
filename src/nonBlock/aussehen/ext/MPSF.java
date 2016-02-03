package nonBlock.aussehen.ext;

import wahr.zugriff.*;

class MPSF
{
	MPS sup;
	K4 ort;
	MPSF[] verb;
	int[] vAbstand;
	int fixedto1 = -1;
	int fixedto2 = -1;

	public MPSF(MPS sup, K4 ort)
	{
		this.sup = sup;
		this.ort = ort;
		verb = new MPSF[4];
		vAbstand = new int[4];
	}

	public MPSF(MPS sup, K4 ort, int fixedto1, int fixedto2)
	{
		this.sup = sup;
		this.ort = ort;
		verb = new MPSF[4];
		vAbstand = new int[4];
		this.fixedto1 = fixedto1;
		this.fixedto2 = fixedto2;
	}

	public void tick()
	{
		if(fixedto1 >= 0)
			ort = sup.main2.punkte[fixedto1][fixedto2];
		else
		{
			//X
		}
	}
}