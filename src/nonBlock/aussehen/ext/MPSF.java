package nonBlock.aussehen.ext;

import wahr.zugriff.*;

class MPSF
{
	MPS sup;
	K4 ort;
	K4 ortNeu;
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
			ortNeu = new K4(sup.main2.punkte[fixedto1][fixedto2]);
		else
		{
			//X
			K4 diffNeu = new K4();
			for(int i = 0; i < 4; i++)
				if(verb[i] != null)
					diffNeu = K4.plus(verb[i].ort, diffNeu);
				else
					diffNeu = K4.plus(ort, diffNeu);
			diffNeu = new K4(diffNeu.a / 4, diffNeu.b / 4, diffNeu.c / 4, diffNeu.d / 4);
			diffNeu = K4.diff(ort, diffNeu);
			K4 fall = new K4(0, -0.05, 0, 0);
			ortNeu = K4.plus(K4.plus(ort, diffNeu), fall);
		}
	}
}