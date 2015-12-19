package nonBlock.aussehen.ext;

import ansicht.n2.*;
import nonBlock.aussehen.*;
import ansicht.n2.xF.*;
import nonBlock.*;
import wahr.zugriff.*;

import java.awt.*;
import java.util.*;

public class MK extends External
{
	private final double fwm;
	private final double fwx;
	private final int nw;
	private final int nt;
	private final int[] linkedP;
	private final LinkAchse[][] h2;
	private final Achse[][] h2a;
	private final int[][] seeds;
	private K4[][] into1;

	public MK(NonBlock main2, int axn, double fwm, double fwx, double fhm, double fhx,
			int nw, int nt, int[] linkedP)
	{
		super(main2, axn);
		this.fwm = fwm;
		this.fwx = fwx;
		this.nw = nw;
		this.nt = nt;
		this.linkedP = linkedP;
		h2 = new LinkAchse[nt][nw];
		h2a = new Achse[nt][nw];

		for(int t = 0; t < nt; t++)
			for(int w = 0; w < nw; w++)
				h2[t][w] = new LinkAchse(new Drehung(Math.PI * 2 * w / nw, 0),
						(fhm * (nt - t) + fhx * t) / nt, 0, 0);

		Random r = new Random();
		seeds = new int[nt][nw];
		for(int i = 0; i < nt; i++)
			for(int j = 0; j < nw; j++)
				seeds[i][j] = r.nextInt();
	}

	public void tick()
	{

	}

	public int platz()
	{
		if(nicht)
			return 0;
		return nt * nw;
	}

	public void entLink(Drehung mDreh, K4 mPos)
	{
		if(nicht)
			return;
		for(int w = 0; w < nw; w++)
		{
			LadePunkt la = main2.aussehen.punkte[axn].get(linkedP[w]);
			h2a[0][w] = h2[0][w].entlinken(TK4F.zuPunkt(main2.achsen[axn],
					la.abstand, 0, la.vor,
					la.spin, mDreh, mPos), main2.achsen[axn]);
		}
		for(int t = 1; t < nt; t++)
			for(int w = 0; w < nw; w++)
				h2a[t][w] = h2[t][w].entlinken(TK4F.achseEnde(h2a[t - 1][w],
						TK4F.mkT1(new K4(Math.sin(h2[t][w].dreh.wl) * t / nt *
								(fwm * (nt - t) + fwx * t) / nt, 0,
								Math.cos(h2[t][w].dreh.wl) * t / nt *
								(fwm * (nt - t) + fwx * t) / nt, 0),
								mDreh, new K4())), h2a[t - 1][w]);
	}

	public void punkte(K4[][] into)
	{
		if(nicht)
			return;
		int cy = this.anfang;
		for(int i = 0; i < nt; i++)
			for(int j = 0; j < nw; j++)
			{
				into[cy] = new K4[]{TK4F.zuPunktXH(h2a[i][j], 0, 0)};
				cy++;
			}
		into1 = into;
	}

	public ArrayList<F2> gibFl(K4[][] into)
	{
		ArrayList<F2> al = new ArrayList<>();
		if(nicht)
			return al;
		int cy = anfang;
		for(int t = 0; t < nt - 1; t++)
			for(int w = 0; w < nw; w++)
			{
				XFarbe fn = new XFN(Color.WHITE);
				K4[] k0 = into[cy];
				K4[] k01 = into1[cy];
				int pm = w == nw - 1 ? 1 - nw : 1;
				K4[] k1 = into[cy + pm];
				K4[] k11 = into1[cy + pm];
					al.add(new NF2(new K4[]{k0[0], k1[0], into[cy + nw + pm][0], into[cy + nw][0]},
							new K4[]{k01[0], k11[0], into1[cy + nw + pm][0], into1[cy + nw][0]},
							fn, seeds[t][w], 0, main2.tn));
				cy++;
			}
		return al;
	}
}