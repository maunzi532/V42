package nonBlock.aussehen.ext;

import wahr.zugriff.*;

import java.util.*;

class MPSF
{
	private MPS sup;
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
			ArrayList<K4> forces = new ArrayList<>();
			for(int i = 0; i < 4; i++)
				if(verb[i] != null)
				{
					K4 diff = K4.diff(ort, verb[i].ort);
					double abst = Math.sqrt(diff.a * diff.a + diff.b * diff.b +
							diff.c * diff.c + diff.d * diff.d);
					double multi = (abst - vAbstand[i]) / abst;
					K4 dif2 = new K4(diff.a * multi, diff.b * multi, diff.c * multi, diff.d * multi);
					forces.add(dif2);
				}
			for(int i = 0; i < 4; i+=2)
				if(verb[i] != null && verb [i + 1] != null)
				{
					K4 diffA = K4.diff(verb[i].ort, verb[i + 1].ort);
					double abstA = Math.sqrt(diffA.a * diffA.a + diffA.b * diffA.b +
							diffA.c * diffA.c + diffA.d * diffA.d);
					K4 mid2 = K4.plus(verb[i].ort, verb[i + 1].ort);
					K4 mid = new K4(mid2.a / 2, mid2.b / 2, mid2.c / 2, mid2.d / 2);
					double multi = (abstA - (vAbstand[i] + vAbstand[i + 1])) / abstA / 100;
					K4 dif3 = K4.diff(ort, mid);
					K4 dif2 = new K4(dif3.a * multi, dif3.b * multi, dif3.c * multi, dif3.d * multi);
					//System.out.println(dif2);
					forces.add(dif2);
				}
			/*NBB main3 = ((NBB)(sup.main2));
			for(int p = 0; p < main3.physik.size(); p++)
			{
				Double v = main3.physik.get(p).innen(ort);
				if(v != null)
				{
					double a1 = ort.a - sup.main2.position.a;
					double c1 = ort.c - sup.main2.position.c;
					double div = Math.sqrt(a1 * a1 + c1 * c1);
					a1 /= div;
					c1 /= div;
					if(v < 1)
						forces.add(new K4(a1 * v * -50, 0, c1 * v * -50, 0));
				}
			}*/
			K4 diffNeu = new K4();
			for(int i = 0; i < forces.size(); i++)
				diffNeu = K4.plus(diffNeu, forces.get(i));
			int div = 8;
			diffNeu = new K4(diffNeu.a / div, diffNeu.b / div, diffNeu.c / div, diffNeu.d / div);
			ortNeu = K4.plus(ort, diffNeu);
			//X
			/*K4 diffNeu = new K4();
			for(int i = 0; i < 4; i++)
				if(verb[i] != null)
					diffNeu = K4.plus(verb[i].ort, diffNeu);
				else
					diffNeu = K4.plus(ort, diffNeu);
			diffNeu = new K4(diffNeu.a / 4, diffNeu.b / 4, diffNeu.c / 4, diffNeu.d / 4);
			diffNeu = K4.diff(ort, diffNeu);
			K4 fall = new K4(0, -0.05, 0, 0);
			ortNeu = K4.plus(K4.plus(ort, diffNeu), fall);*/
		}
	}
}