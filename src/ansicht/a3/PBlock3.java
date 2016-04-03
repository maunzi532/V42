package ansicht.a3;

import ansicht.*;
import wahr.zugriff.*;

public class PBlock3 extends Polygon3
{
	public K4[] unSpldEckenK;
	private double rEnd;
	private double gEnd;

	public PBlock3(long tn, Boolean seite, LichtW lw)
	{
		super(tn, seite, lw);
	}

	public boolean errechneKam(K4 kamP, Drehung kamD)
	{
		return false; //TODO
	}

	public void splittern(boolean gmVision)
	{
		//TODO
	}
}