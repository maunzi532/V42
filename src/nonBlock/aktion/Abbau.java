package nonBlock.aktion;

import achsen.*;
import k4.*;
import nonBlock.collide.*;
import welt.*;

public class Abbau extends Aktion
{
	private final TnTarget tn;
	private final int start;
	private final NBB reMount;
	private final int linA;
	private final Drehung reD;
	private final int reT;

	public Abbau(NBB besitzer, int dauer, int power, TnTarget tn,
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
		WeltB welt = ((NBB) besitzer).welt;
		if(aktuell == start)
			((NonBlock) besitzer).focus = new Focus((NonBlock) besitzer, dauer - start,
					welt.wt2(welt.decodeTn(tn.target)));
		if(aktuell == dauer - 1)
		{
			((NonBlock) besitzer).focus = new Mount((NonBlock) besitzer, reMount, linA, 0, reD, reT);
			WBP p = welt.decodeTn(tn.target);
			welt.set(p, new DerBlock(0, welt.gib(p).dreh4));
		}
	}
}