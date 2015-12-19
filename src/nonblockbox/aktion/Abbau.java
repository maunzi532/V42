package nonblockbox.aktion;

import block.*;
import k.*;
import nonBlock.*;

public class Abbau extends Aktion
{
	private final long tn;
	private final int start;
	private final NBD reMount;
	private final int linA;
	private final Drehung reD;
	private final int reT;

	public Abbau(NBD besitzer, int dauer, int power, long tn, int start, NBD reMount, int linA, Drehung reD, int reT)
	{
		super(besitzer, dauer, power);
		this.tn = tn;
		this.start = start;
		this.reMount = reMount;
		this.linA = linA;
		this.reD = reD;
		this.reT = reT;
	}

	public void tick()
	{
		if(aktuell == start)
			besitzer.focus = new Focus(besitzer, dauer - start, Koord.wt2(Koord.decodeTn(tn)));
		if(aktuell == dauer - 1)
		{
			besitzer.focus = new Mount(besitzer, reMount, linA, 0, reD, reT);
			WeltB.set(Koord.decodeTn(tn), 0);
		}
	}
}