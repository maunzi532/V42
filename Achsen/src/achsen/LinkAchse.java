package achsen;

import k4.*;

public class LinkAchse
{
	public final LinkAchse gelinkt;
	public final Drehung dreh;
	public double len;
	public double spin;
	public double dShift;
	private K4 tele;
	public Achse gelinktA;

	public LinkAchse(LinkAchse gelinkt, Drehung dreh, double len, double spin,
			double dShift, K4 tele)
	{
		this.gelinkt = gelinkt;
		this.dreh = dreh;
		this.len = len;
		this.spin = spin;
		this.dShift = dShift;
		this.tele = tele;
	}

	public LinkAchse(Drehung dreh, double len, double spin, double dShift)
	{
		gelinkt = null;
		this.dreh = dreh;
		this.len = len;
		this.spin = spin;
		this.dShift = dShift;
	}

	public Achse entlinken(Achse gelinkt)
	{
		gelinktA = new Achse(achseEnde(gelinkt, tele),
					Drehung.nplus(dreh, gelinkt.dreh), len, spin, dShift);
		return gelinktA;
	}

	public Achse entlinken()
	{
		if(tele != null)
			gelinktA = new Achse(new K4(tele), dreh, len, spin, dShift);
		else
			gelinktA = new Achse(new K4(0, 0, 0, 0), dreh, len, spin, dShift);
		return gelinktA;
	}

	public Achse entlinken(K4 ende, Achse inlinkt, Drehung mDreh)
	{
		gelinktA = new Achse(ende, Drehung.nplus(Drehung.nplus(dreh, inlinkt.dreh), mDreh), len, spin, dShift);
		return gelinktA;
	}

	public Achse entlinken(K4 ende, Achse inlinkt)
	{
		gelinktA = new Achse(ende, Drehung.nplus(dreh, inlinkt.dreh), len, spin, dShift);
		return gelinktA;
	}

	public static K4 achseEnde(Achse a, K4 tele)
	{
		if(a.len == 0)
		{
			if(tele != null)
				return new K4(a.start.a + tele.a, a.start.b + tele.b,
						a.start.c + tele.c, a.start.d + tele.d + a.dShift);
			else
				return new K4(a.start.a, a.start.b,
						a.start.c, a.start.d + a.dShift);
		}
		K4 tLen = new K4(0, 0, a.len, a.dShift);
		tLen.transformWBL(a.dreh);
		if(tele != null)
			tLen = K4.plus(tLen, tele);
		return K4.plus(a.start, tLen);
	}
}