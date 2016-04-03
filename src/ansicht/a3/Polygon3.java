package ansicht.a3;

import ansicht.*;
import wahr.zugriff.*;

import java.awt.*;

public abstract class Polygon3 extends Anzeige3
{
	public K4[] eckenR;
	public K4[] eckenK;
	public K4[] eckenCut;
	public Boolean seite;
	public LichtW lw;
	//TODO Farbe

	private int[] eckenPanelX;
	private int[] eckenPanelY;
	private double avkh2; //TODO errechnen irgendwo
	private boolean xrDraw;

	public Polygon3(long tn, Boolean seite, LichtW lw)
	{
		super(tn);
		this.seite =  seite;
		this.lw = lw;
	}

	public abstract boolean errechneKam(K4 kamP, Drehung kamD);

	public abstract void splittern(boolean gmVision);

	public void eckenEntf(int wI, int hI, int cI)
	{
		if(!anzeigen)
			return;
		int kl = eckenK.length;
		for(int v = 0; v < 4; v++)
		{
			if(kl == 0)
				continue;
			int falsch = 0;
			for(int i = 0; i < kl; i++)
				if(eckenK[i].c < (v % 2 == 0 ? eckenK[i].a * Staticf.scaleX * wI / cI :
						eckenK[i].b * Staticf.scaleX * hI / cI) * (v / 2 * 2 - 1))
					falsch++;
			if(falsch >= kl)
			{
				anzeigen = false;
				return;
			}
		}
		int vornIdx = -1;
		int hintIdx = -1;
		int falsch = 0;
		boolean vvor = eckenK[kl - 1].c > 0;
		for(int i = 0; i < kl; i++)
		{
			if(eckenK[i].c > 0)
			{
				if(!vvor)
					vornIdx = i;
				vvor = true;
			}
			else
			{
				if(vvor)
					hintIdx = i;
				falsch++;
				vvor = false;
			}
		}
		if(falsch >= kl)
		{
			anzeigen = false;
			return;
		}
		if(falsch <= 0)
		{
			eckenCut = new K4[kl];
			System.arraycopy(eckenK, 0, eckenCut, 0, kl);
		}
		else
		{
			int len = (hintIdx - vornIdx + kl) % kl + 2;
			eckenCut = new K4[len];
			for(int i = 0; i < len; i++)
				eckenCut[i] = new K4();
			for(int i = 0; i < len - 2; i++)
			{
				eckenCut[i].a = eckenK[(i + vornIdx + kl) % kl].a;
				eckenCut[i].b = eckenK[(i + vornIdx + kl) % kl].b;
				eckenCut[i].c = eckenK[(i + vornIdx + kl) % kl].c;
				eckenCut[i].d = eckenK[(i + vornIdx + kl) % kl].d;
			}
			eckenCut[len - 2].a = rkc(eckenK[(hintIdx - 1 + kl) % kl].a,
					eckenK[hintIdx].a, eckenK[(hintIdx - 1 + kl) % kl].c, eckenK[hintIdx].c);
			eckenCut[len - 1].a = rkc(eckenK[vornIdx].a, eckenK[(vornIdx - 1 + kl) % kl].a,
					eckenK[vornIdx].c, eckenK[(vornIdx - 1 + kl) % kl].c);
			eckenCut[len - 2].b = rkc(eckenK[(hintIdx - 1 + kl) % kl].b,
					eckenK[hintIdx].b, eckenK[(hintIdx - 1 + kl) % kl].c, eckenK[hintIdx].c);
			eckenCut[len - 1].b = rkc(eckenK[vornIdx].b, eckenK[(vornIdx - 1 + kl) % kl].b,
					eckenK[vornIdx].c, eckenK[(vornIdx - 1 + kl) % kl].c);
			eckenCut[len - 2].d = rkc(eckenK[(hintIdx - 1 + kl) % kl].d,
					eckenK[hintIdx].d, eckenK[(hintIdx - 1 + kl) % kl].c, eckenK[hintIdx].c);
			eckenCut[len - 1].d = rkc(eckenK[vornIdx].b, eckenK[(vornIdx - 1 + kl) % kl].b,
					eckenK[vornIdx].c, eckenK[(vornIdx - 1 + kl) % kl].c);
			eckenCut[len - 2].c = 0;
			eckenCut[len - 1].c = 0;
		}
	}

	private double rkc(double k0, double k1, double kc0, double kc1)
	{
		return (k0 * kc1 - k1 * kc0) / (kc1 - kc0);
	}

	public void farbeFlaeche(Long tnTarget, int wI, int hI)
	{
		if(anzeigen)
		{
			eckenPanelX = new int[eckenCut.length];
			eckenPanelY = new int[eckenCut.length];
			ddiff = 0;
			for(int j = 0; j < eckenCut.length; j++)
			{
				double ca = eckenCut[j].c;
				if(ca < Staticf.nnull)
					ca = Staticf.nnull;
				eckenPanelX[j] = ethaX(eckenCut[j].a, ca, wI);
				eckenPanelY[j] = ethaY(eckenCut[j].b, ca, wI, hI);
				ddiff += eckenCut[j].d;
			}
			ddiff /= eckenCut.length;
			/*if(spken != null)
			{
				xse = new int[spken.length];
				yse = new int[spken.length];
				for(int j = 0; j < spken.length; j++)
				{
					double ca = spken[j].c;
					if(ca < Staticf.nnull)
						ca = Staticf.nnull;
					xse[j] = ethaX(spken[j].a, ca, wI);
					yse[j] = ethaY(spken[j].b, ca, wI, hI);
				}
			}*/
			/*if(!farbe.shownext(this))
				anzeigen = false;
			else
				dFarb = farbe.gibFarb(this, tn);*///TODO
			//TODO errechne xrDraw
		}
	}

	public void Panel(Graphics2D gd)
	{
		gd.setPaint(dFarb);
		if(xrDraw)
			gd.draw(new Polygon(eckenPanelX, eckenPanelY, eckenPanelX.length));
		else
			gd.fill(new Polygon(eckenPanelX, eckenPanelY, eckenPanelX.length));
	}

	public void PanelDark(Graphics2D gd)
	{
		gd.fill(new Polygon(eckenPanelX, eckenPanelY, eckenPanelX.length));
	}
}