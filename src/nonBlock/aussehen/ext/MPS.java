package nonBlock.aussehen.ext;

import ansicht.*;
import ansicht.n2.*;
import ansicht.n2.xF.*;
import wahr.zugriff.*;

import java.awt.*;
import java.util.*;

public class MPS extends External
{
	ArrayList<MPSF> teile;
	ArrayList<int[]> flaechen;
	private ArrayList<Integer> seeds;
	XFarbe xf;

	public MPS()
	{

	}

	public void init()
	{
		teile = new ArrayList<>();
		MPSF[][] t = new MPSF[15][30];
		for(int i = 0; i < t.length; i++)
			for(int j = 0; j < t[i].length; j++)
			{
				t[i][j] = new MPSF(this, new K4(main2.position.a + Math.sin(j / 5d) * 5, main2.position.b - i * 2,
						main2.position.c + Math.cos(j / 5d) * 5, main2.position.d));
				if(i == 0 && j % 4 == 0)
				{
					t[i][j].fixedto1 = 0;
					t[i][j].fixedto2 = j / 4;
				}
				teile.add(t[i][j]);
			}
		for(int i = 0; i < t.length; i++)
			for(int j = 0; j < t[i].length; j++)
			{
				if(i > 0)
				{
					t[i][j].verb[0] = t[i - 1][j];
					t[i][j].vAbstand[0] = 1;
				}
				if(i < t.length - 1)
				{
					t[i][j].verb[1] = t[i + 1][j];
					t[i][j].vAbstand[1] = 1;
				}
				if(j > 0)
				{
					t[i][j].verb[2] = t[i][j - 1];
					t[i][j].vAbstand[2] = 1;
				}
				if(j < t[i].length - 1)
				{
					t[i][j].verb[3] = t[i][j + 1];
					t[i][j].vAbstand[3] = 1;
				}
			}
		flaechen = new ArrayList<>();
		for(int i = 0; i < t.length - 1; i++)
			for(int j = 0; j < t[i].length; j++)
				flaechen.add(new int[]{i * t[i].length + j, i * t[i].length + (j + 1) % t[i].length,
						(i + 1) * t[i].length + (j + 1) % t[i].length, (i + 1) * t[i].length + j});
		Random r = new Random();
		seeds = new ArrayList<>();
		for(int i = 0; i < flaechen.size(); i++)
			seeds.add(r.nextInt());
		xf = new XFN(new Color(150, 150, 150), Material.STOFF);
	}

	public void entLink(Drehung mDreh, K4 mPos)
	{

	}

	public void punkte(K4[][] into)
	{
		into[anfang] = new K4[teile.size()];
		for(int i = 0; i < teile.size(); i++)
			into[anfang][i] = new K4(teile.get(i).ort);
	}

	public ArrayList<F2> gibFl(K4[][] into, LichtW lw, boolean gmVision, boolean isMasterVision)
	{
		ArrayList<F2> toR = new ArrayList<>();
		for(int i = 0; i < flaechen.size(); i++)
		{
			K4[] ecken = new K4[flaechen.get(i).length];
			for(int j = 0; j < flaechen.get(i).length; j++)
				ecken[j] = into[anfang][flaechen.get(i)[j]];
			NF2.atl(toR, new NF2(ecken, null, null, xf, null, lw, -1, seeds.get(i), main2.tn), gmVision);
		}
		return toR;
	}

	public void tick()
	{
		/*for(int i = 0; i < teile.size(); i++)
			teile.get(i).tick();
		for(int i = 0; i < teile.size(); i++)
			teile.get(i).ort = teile.get(i).ortNeu;*/
	}
}