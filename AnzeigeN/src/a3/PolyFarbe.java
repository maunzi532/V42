package a3;

import achsen.*;
import indexLader.*;
import java.awt.*;
import java.util.*;
import k4.*;

public class PolyFarbe implements IFarbe
{
	//Anzahl unterschiedlicher Diffundierungs-Seeds
	public static final int seedifier = 100;
	//Weite in Blocks wo NonBlocks in d nicht diffundieren
	public static final double safezone = 1;
	//Weite danach wo NonBlocks in d diffundieren
	public static final double diffusewidth = 40;
	public static final double redEnd = 300;
	private static final int[] splN;
	static
	{
		Random r = new Random();
		splN = new int[seedifier];
		for(int i = 0; i < seedifier; i++)
			splN[i] = r.nextInt(seedifier);
	}

	public Color baseColor;
	public Material mat;

	@Override
	public IFarbe gibNeu(String s)
	{
		return new PolyFarbe(s);
	}

	@Override
	public IFarbe gibNeu(ArrayList<String> s)
	{
		return new PolyFarbe(s);
	}

	@Override
	public IFarbe gibNeu(String build, int errStart, int errEnd, ErrorVial vial)
	{
		PolyFarbe create;
		//TODO
		try
		{
			create = new PolyFarbe(build);
		}
		catch(Exception e)
		{
			create = new PolyFarbe("255-255-255,N");
		}
		return create;
	}

	public PolyFarbe(){}

	public PolyFarbe(String text)
	{
		String[] tex = text.split(",");
		baseColor = farbCode(tex[0]);
		if(tex.length > 1)
			mat = Material.valueOf(tex[1]);
		else
			mat = Material.N;
	}

	public PolyFarbe(ArrayList<String> text)
	{
		baseColor = farbCode(text.get(0));
		if(text.size() > 1)
			mat = Material.valueOf(text.get(1));
		else
			mat = Material.N;
	}

	public boolean showFade(Polygon3 target)
	{
		if(target.rSeed <= 0)
			return true;
		if(target.ddiff >= 0)
			return target.ddiff / diffusewidth - safezone <=
					((Math.abs(target.rSeed) + splN[(target.nachSplitID % seedifier)]) %
							seedifier) / (double) seedifier;
		return -target.ddiff / diffusewidth - safezone <=
				(seedifier - 1 - ((Math.abs(target.rSeed) +
						splN[(target.nachSplitID % seedifier)]) %
						seedifier)) / (double) seedifier;
	}

	public Paint gibFarbe(Linie3 target, TnTarget tn)
	{
		Color fc = baseColor;
		if(target.ddiff > 0) //Rot
			fc = limit(fc, (int)(target.ddiff * 10), (int)(target.ddiff * -5), (int)(target.ddiff * -5));
		if(target.ddiff < 0) //Gn
			fc = limit(fc, (int)(target.ddiff * 5), (int)(target.ddiff * -10), (int)(target.ddiff * 5));
		if(target.lw == null)
			return fc; //Kein Licht, keine andere Farbe
		double power = -255;
		for(int i = 0; i < target.lw.licht.size(); i++)
		{
			K4 lr = K4.diff(target.lw.licht.get(i).lichtPosition(), target.rMid);
			double ld = Math.sqrt(lr.a * lr.a + lr.b * lr.b + lr.c * lr.c);
			if(ld > target.lw.licht.get(i).lichtRange())
				continue;
			double pow = target.lw.licht.get(i).lichtPower();
			pow -= ld * target.lw.licht.get(i).lichtPowerDecay();
			pow *= mat.lichtAffection;
			if(pow > power)
				power = pow;
		}
		fc = limit(fc, (int)power, (int)power, (int)power); //Beleuchten
		double weg = Math.sqrt(target.kamMid.a * target.kamMid.a + target.kamMid.b * target.kamMid.b +
				target.kamMid.c * target.kamMid.c + target.kamMid.d * target.kamMid.d);
		double nah = (redEnd - weg) / redEnd; //Wenn nah 1, am Rand 0
		if(nah < 0)
			nah = 0;
		if(tn != null && target.tn != null && tn.target == target.tn.target)
		{
			fc = limit(fc, 30, 30, 30); //Sichthilfe wenn getargetet
			if(tn.inner == target.tn.inner)
				fc = limit(fc, 30, 30, 30);
		}
		return new Color((int)(fc.getRed() * nah + 20 * (1 - nah)),
				(int)(fc.getGreen() * nah),
				(int)(fc.getBlue() * nah), fc.getAlpha()); //Fading nach Dunkelrot
	}

	public Paint gibFarbe(Polygon3 target, TnTarget tn)
	{
		return errechneFarbe(baseColor, target, tn);
	}

	public Paint errechneFarbe(Color fc, Polygon3 target, TnTarget tn)
	{
		if(target.ddiff > 0) //Rot
			fc = limit(fc, (int)(target.ddiff * 10), (int)(target.ddiff * -5), (int)(target.ddiff * -5));
		if(target.ddiff < 0) //Gn
			fc = limit(fc, (int)(target.ddiff * 5), (int)(target.ddiff * -10), (int)(target.ddiff * 5));
		if(target.lw == null)
			return fc; //Kein Licht, keine andere Farbe
		double power = -255;
		for(int i = 0; i < target.lw.licht.size(); i++)
		{
			K4 lr = K4.diff(target.lw.licht.get(i).lichtPosition(), target.rMid);
			double ld = Math.sqrt(lr.a * lr.a + lr.b * lr.b + lr.c * lr.c);
			if(ld > target.lw.licht.get(i).lichtRange())
				continue;
			double pow = target.lw.licht.get(i).lichtPower();
			pow -= ld * target.lw.licht.get(i).lichtPowerDecay();
			pow *= mat.lichtAffection;
			if(mat.shadeMultiplier != 0)
				pow -= shadeWinkel(target, target.lw.licht.get(i).lichtPosition()) * mat.shadeMultiplier;
			if(pow > power)
				power = pow;
		}
		fc = limit(fc, (int)power, (int)power, (int)power); //Beleuchten und shaden
		double weg = Math.sqrt(target.kamMid.a * target.kamMid.a + target.kamMid.b * target.kamMid.b +
				target.kamMid.c * target.kamMid.c + target.kamMid.d * target.kamMid.d);
		double nah = (redEnd - weg) / redEnd; //Wenn nah 1, am Rand 0
		if(nah < 0)
			nah = 0;
		if(tn != null && target.tn != null && tn.target == target.tn.target)
		{
			fc = limit(fc, 30, 30, 30); //Sichthilfe wenn getargetet
			if(tn.inner == target.tn.inner)
				fc = limit(fc, 30, 30, 30);
		}
		return new Color((int)(fc.getRed() * nah + 20 * (1 - nah)),
				(int)(fc.getGreen() * nah),
				(int)(fc.getBlue() * nah), fc.getAlpha()); //Fading nach Dunkelrot
	}

	private double shadeWinkel(Polygon3 mirror, K4 lichtOrt)
	{
		for(int i = 0; i < mirror.eckenR.length; i++)
			if(mirror.eckenR[i] == null)
				return 0;
		K4 v1 = K4.diff(mirror.eckenR[0], mirror.eckenR[1]);
		K4 v2 = K4.diff(mirror.eckenR[0], mirror.eckenR[mirror.eckenR.length - 1]);
		K4 kp = new K4(v1.b * v2.c - v2.b * v1.c, v1.c * v2.a - v2.c * v1.a,
				v1.a * v2.b - v2.a * v1.b, 0);
		K4 lr = K4.diff(lichtOrt, mirror.rMid);
		double kd = Math.sqrt(kp.a * kp.a + kp.b * kp.b + kp.c * kp.c);
		double ld = Math.sqrt(lr.a * lr.a + lr.b * lr.b + lr.c * lr.c);
		if(kd == 0 || ld == 0)
			return 2;
		K4 kn = new K4(kp.a / kd, kp.b / kd, kp.c / kd, 0);
		K4 ln = new K4(lr.a / ld, lr.b / ld, lr.c / ld, 0);
		double d1 = 2;
		double d2 = 2;
		if(mirror.seite == null || mirror.seite)
		{
			K4 dif1 = K4.diff(kn, ln);
			d1 = Math.sqrt(dif1.a * dif1.a + dif1.b * dif1.b + dif1.c * dif1.c);
		}
		if(mirror.seite == null || !mirror.seite)
		{
			K4 dif2 = K4.plus(kn, ln);
			d2 = Math.sqrt(dif2.a * dif2.a + dif2.b * dif2.b + dif2.c * dif2.c);
		}
		return d1 < d2 ? d1 : d2;
	}

	private Color limit(Color c, int r, int g, int b)
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

	private static Color farbCode(String code)
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
}