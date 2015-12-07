package modell.ext;

import ansicht.*;
import k.*;
import nonBlock.*;

import java.util.*;

public abstract class External
{
	public static boolean nicht;
	final NonBlock main2;
	int axn;
	int anfang;

	public External(NonBlock main2)
	{
		this.main2 = main2;
	}

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