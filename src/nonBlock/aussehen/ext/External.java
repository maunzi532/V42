package nonBlock.aussehen.ext;

import ansicht.n2.*;
import nonBlock.*;
import wahr.zugriff.*;

import java.util.*;

public abstract class External
{
	public NonBlock main2;
	public int axn;
	public int anfang;
	public int laenge;
	protected K4 inmid;
	protected Drehung dmid;
	protected K4[][] into1;

	public abstract void entLink(Drehung mDreh, K4 mPos);

	public abstract void punkte(K4[][] into);

	public abstract ArrayList<F2> gibFl(K4[][] into);

	public abstract void tick();
}