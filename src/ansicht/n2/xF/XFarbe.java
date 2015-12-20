package ansicht.n2.xF;

import ansicht.n2.*;
import nonBlock.aktion.*;
import wahr.zugriff.*;

import java.awt.*;
import java.util.*;

public abstract class XFarbe
{
	private static final int[] splN;
	static
	{
		Random r = new Random();
		splN = new int[Staticf.seedifier];
		for(int i = 0; i < Staticf.seedifier; i++)
			splN[i] = r.nextInt(Staticf.seedifier);
	}

	public abstract Paint gibFarb(N2 n);

	public boolean shownext(N2 n)
	{
		F2 f = (F2) n;
		if(f.seed <= 0)
			return true;
		if(f.ddiff >= 0)
			return f.ddiff / Staticf.diffusewidth - Staticf.safezone <=
					((Math.abs(f.seed) + splN[(f.splseed % Staticf.seedifier)]) %
					Staticf.seedifier) / (double)Staticf.seedifier;
		return -f.ddiff / Staticf.diffusewidth - Staticf.safezone <=
				(Staticf.seedifier - 1 - ((Math.abs(f.seed) +
				splN[(f.splseed % Staticf.seedifier)]) % Staticf.seedifier)) / (double)Staticf.seedifier;
	}

	static Color anpassen(N2 n, Color fc)
	{
		if(n.ddiff > 0)
			fc = limit(fc, (int)(n.ddiff * 10), (int)(n.ddiff * -5), (int)(n.ddiff * -5));
		else if(n.ddiff < 0)
			fc = limit(fc, (int)(n.ddiff * 5), (int)(n.ddiff * -10), (int)(n.ddiff * 5));
		fc = shade(fc, n);
		double weg = Math.sqrt(n.mid.a * n.mid.a + n.mid.b * n.mid.b + n.mid.c * n.mid.c + n.mid.d * n.mid.d);
		double weg2 = (Staticf.sicht - weg) / Staticf.sicht;
		if(weg2 < 0)
			weg2 = 0;
		return new Color((int)(fc.getRed() * weg2 + 20 * (1 - weg2)),
				(int)(fc.getGreen() * weg2 + 0 * (1 - weg2)),
				(int)(fc.getBlue() * weg2 + 0 * (1 - weg2)), fc.getAlpha());
	}

	private static Color shade(Color fc, N2 n)
	{
		double power = -255;
		for(int i = 0; i < WeltND.licht.size(); i++)
		{
			if(n instanceof F2)
			{
				F2 f = (F2) n;
				K4 lr = K4.diff(WeltND.licht.get(i).lichtPosition(), f.mid1());
				/*if(true)
					return limit(fc, (int) (1 / Math.abs(lr.a) * -100),
							(int) (1 / Math.abs(lr.b) * -100), (int) (1 / Math.abs(lr.c) * -100));*/
				double ld = Math.sqrt(lr.a * lr.a + lr.b * lr.b + lr.c * lr.c);
				/*if(true)
					return limit(fc, (int) (ld * -1), (int) (ld * -1), 0);*/
				if(ld > WeltND.licht.get(i).lichtRange())
					continue;
				double pow = WeltND.licht.get(i).lichtPower();
				pow -= ld * WeltND.licht.get(i).lichtPowerDecay();
				if(f.eckenNK != null)
					pow -= shadeWinkel(f, WeltND.licht.get(i).lichtPosition()) * 40;
				if(pow > power)
					power = pow;
			}
		}
		return limit(fc, (int)power, (int)power, (int)power);
	}

	private static double shadeWinkel(F2 n, K4 lichtp)
	{
		for(int i = 0; i < n.eckenNK.length; i++)
			if(n.eckenNK[i] == null)
				return 0;
		K4 v1 = K4.diff(n.eckenNK[0], n.eckenNK[1]);
		K4 v2 = K4.diff(n.eckenNK[0], n.eckenNK[n.eckenNK.length - 1]);
		K4 kp = new K4(v1.b * v2.c - v2.b * v1.c, v1.c * v2.a - v2.c * v1.a,
				v1.a * v2.b - v2.a * v1.b, 0);
		K4 lr = K4.diff(lichtp, n.mid1());
		double kd = Math.sqrt(kp.a * kp.a + kp.b * kp.b + kp.c * kp.c);
		double ld = Math.sqrt(lr.a * lr.a + lr.b * lr.b + lr.c * lr.c);
		if(kd == 0 || ld == 0)
			return 2;
		K4 kn = new K4(kp.a / kd, kp.b / kd, kp.c / kd, 0);
		K4 ln = new K4(lr.a / ld, lr.b / ld, lr.c / ld, 0);
		double d1 = 2;
		double d2 = 2;
		if(n.seite == null || n.seite)
		{
			K4 dif1 = K4.diff(kn, ln);
			d1 = Math.sqrt(dif1.a * dif1.a + dif1.b * dif1.b + dif1.c * dif1.c);
		}
		if(n.seite == null || !n.seite)
		{
			K4 dif2 = K4.plus(kn, ln);
			d2 = Math.sqrt(dif2.a * dif2.a + dif2.b * dif2.b + dif2.c * dif2.c);
		}
		return d1 < d2 ? d1 : d2;
	}

	public static Color farbCode(String code)
	{
		String[] cx1 = code.split("-");
		switch(cx1.length)
		{
			case 1:
				int f0 = Integer.parseInt(cx1[0]);
				return new Color(f0, f0, f0);
			case 2:
				int f1 = Integer.parseInt(cx1[0]);
				return new Color(f1, f1, f1, Integer.parseInt(cx1[1]));
			case 3:
				return new Color(Integer.parseInt(cx1[0]), Integer.parseInt(cx1[1]),
						Integer.parseInt(cx1[2]));
			case 4:
				return new Color(Integer.parseInt(cx1[0]), Integer.parseInt(cx1[1]),
						Integer.parseInt(cx1[2]), Integer.parseInt(cx1[3]));
			default:
				return Color.WHITE;
		}
	}

	public static Color limit(int r, int g, int b)
	{
		if(r > 255)
			r = 255;
		if(r < 0)
			r = 0;
		if(g > 255)
			g = 255;
		if(g < 0)
			g = 0;
		if(b > 255)
			b = 255;
		if(b < 0)
			b = 0;
		return new Color(r, g, b);
	}

	private static Color limit(Color c, int r, int g, int b)
	{
		r += c.getRed();
		if(r > 255)
			r = 255;
		if(r < 0)
			r = 0;
		g += c.getGreen();
		if(g > 255)
			g = 255;
		if(g < 0)
			g = 0;
		b += c.getBlue();
		if(b > 255)
			b = 255;
		if(b < 0)
			b = 0;
		return new Color(r, g, b, c.getAlpha());
	}

	public static XFarbe t2xf(String tex)
	{
		String[] cx0 = tex.split(",");
		if(cx0.length == 1)
			return new XFN(farbCode(cx0[0]));
		if(cx0.length == 2)
			return new XGradient(farbCode(cx0[0]), 0, farbCode(cx0[1]), 1);
		return null;
	}
}