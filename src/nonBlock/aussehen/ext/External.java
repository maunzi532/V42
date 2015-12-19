package nonBlock.aussehen.ext;

import ansicht.n2.*;
import nonBlock.*;
import wahr.zugriff.*;

import java.util.*;

public abstract class External
{
	public static boolean nicht;
	final NonBlock main2;
	final int axn;
	int anfang;

	External(NonBlock main2, int axn)
	{
		this.main2 = main2;
		this.axn = axn;
	}

	public abstract void tick();
	public abstract int platz();
	public abstract void entLink(Drehung mDreh, K4 mPos);

	public void anfang(int anfang)
	{
		this.anfang = anfang;
	}

	public abstract void punkte(K4[][] into);

	public abstract ArrayList<F2> gibFl(K4[][] into);

}