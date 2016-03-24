package nonBlock.aktion;

import block.*;
import nonBlock.aussehen.*;
import nonBlock.collide.*;
import wahr.zugriff.*;

public class Abbau extends Aktion
{
	private final long tn;
	private final int start;
	private final NBB reMount;
	private final int linA;
	private final Drehung reD;
	private final int reT;

	public Abbau(NBB besitzer, int dauer, int power, long tn,
			int start, NBB reMount, int linA, Drehung reD, int reT)
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
		WeltB welt = ((NBB)besitzer).welt;
		if(aktuell == start)
			besitzer.focus = new Focus(besitzer, dauer - start, welt.wt2(welt.decodeTn(tn)));
		if(aktuell == dauer - 1)
		{
			besitzer.focus = new Mount(besitzer, reMount, linA, 0, reD, reT);
			WBP p = welt.decodeTn(tn);
			welt.set(p, new DerBlock(0, welt.gib(p).dreh4));
		}
	}
}