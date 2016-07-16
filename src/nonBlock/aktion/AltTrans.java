package nonBlock.aktion;

import java.util.*;
import k4.*;
import nonBlock.collide.*;

public class AltTrans extends Aktion
{
	private ArrayList<double[]> neu;
	private ArrayList<double[]> alt;

	public AltTrans(NBB besitzer, int dauer, int power, ArrayList<double[]> blockbox)
	{
		super(besitzer, dauer, power);
		neu = blockbox;
		alt = new ArrayList<>();
		for(int i = 0; i < besitzer.block.size(); i++)
		{
			BlockBox b = besitzer.block.get(i);
			alt.add(new double[]{b.se[0], b.se[1], b.se[2], b.se[3],
					b.ee[0], b.ee[1], b.ee[2], b.ee[3], b.airshift});
		}
		if(besitzer.currentTrans != null && besitzer.currentTrans instanceof AltTrans)
		{
			((AltTrans)besitzer.currentTrans).endtick();
			tick();
		}
		besitzer.currentTrans = this;
	}

	public AltTrans(NBD besitzer, int dauer, int power)
	{
		super(besitzer, dauer, power);
		if(besitzer.currentTrans != null && besitzer.currentTrans instanceof AltTrans)
		{
			((AltTrans)besitzer.currentTrans).endtick();
			tick();
		}
		besitzer.currentTrans = this;
	}

	public void delink()
	{
		if(besitzer.currentTrans == this)
			besitzer.currentTrans = null;
	}

	public void tick()
	{
		if(besitzer instanceof NBB)
		{
			NBB be = ((NBB)besitzer);
			ArrayList<BlockBox> ba = be.block;
			for(int i = 0; i < ba.size(); i++)
			{
				double[] lmo = new double[9];
				for(int j = 0; j < 9; j++)
					lmo[j] = (neu.get(i)[j] - alt.get(i)[j]) / dauer;
				lmo[1] += lmo[8];
				lmo[5] += lmo[8];
				for(int j = 0; j < 8; j++)
					if(lmo[j] <= 0)
						lmo[j] = 0;
				K4 a1 = new K4(-lmo[0], -lmo[1], -lmo[2], -lmo[3]);
				K4 a2 = new K4(lmo[4], lmo[5], lmo[6], -lmo[7]);
				K4 b1 = ba.get(i).check(a1, be.welt);
				K4 b2 = ba.get(i).check(a2, be.welt);
				if(a1.a != b1.a || a1.b != b1.b || a1.c != b1.c || a1.d != b1.d ||
						a2.a != b2.a || a2.b != b2.b || a2.c != b2.c || a2.d != b2.d)
				{
					aktuell--;
					return;
				}
			}
			double maxAir = -Double.MAX_VALUE;
			for(int i = 0; i < ba.size(); i++)
			{
				BlockBox b = ba.get(i);
				double altAir = b.airshift;
				for(int j = 0; j < 4; j++)
					b.se[j] = (neu.get(i)[j] * aktuell + alt.get(i)[j] * (dauer - aktuell)) / dauer;
				for(int j = 0; j < 4; j++)
					b.ee[j] = (neu.get(i)[j + 4] * aktuell + alt.get(i)[j + 4] * (dauer - aktuell)) / dauer;
				b.airshift = (neu.get(i)[8] * aktuell + alt.get(i)[8] * (dauer - aktuell)) / dauer;
				if(b.airshift - altAir > maxAir)
					maxAir = b.airshift - altAir;
			}
			if(maxAir != -Double.MAX_VALUE)
				besitzer.position.b += maxAir;
		}
	}

	private void endtick()
	{
		aktuell = dauer;
		needCancel = true;
		if(besitzer instanceof NBB)
		{
			NBB be = ((NBB)besitzer);
			ArrayList<BlockBox> ba = be.block;
			for(int i = 0; i < ba.size(); i++)
			{
				double[] lmo = new double[9];
				for(int j = 0; j < 9; j++)
					lmo[j] = (neu.get(i)[j] - alt.get(i)[j]) / dauer;
				lmo[1] += lmo[8];
				lmo[5] += lmo[8];
				for(int j = 0; j < 8; j++)
					if(lmo[j] <= 0)
						lmo[j] = 0;
				K4 a1 = new K4(-lmo[0], -lmo[1], -lmo[2], -lmo[3]);
				K4 a2 = new K4(lmo[4], lmo[5], lmo[6], -lmo[7]);
				K4 b1 = ba.get(i).check(a1, be.welt);
				K4 b2 = ba.get(i).check(a2, be.welt);
				if(a1.a != b1.a || a1.b != b1.b || a1.c != b1.c || a1.d != b1.d ||
						a2.a != b2.a || a2.b != b2.b || a2.c != b2.c || a2.d != b2.d)
				{
					aktuell--;
					return;
				}
			}
			double maxAir = -Double.MAX_VALUE;
			for(int i = 0; i < ba.size(); i++)
			{
				BlockBox b = ba.get(i);
				double altAir = b.airshift;
				b.airshift = neu.get(i)[8];
				if(b.airshift - altAir > maxAir)
					maxAir = b.airshift - altAir;
			}
			if(maxAir != -Double.MAX_VALUE)
				besitzer.position.b += maxAir;
		}
	}
}