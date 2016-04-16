package nonBlock.aussehen.ext;

import ansicht.*;
import ansicht.a3.*;
import nonBlock.aussehen.*;
import wahr.zugriff.*;

import java.util.*;

public abstract class External
{
	public NonBlock main2;
	public int axn;
	public int anfang;
	public int laenge;
	K4 inmid;
	Drehung dmid;
	K4[][] into1;

	public abstract void entLink(Drehung mDreh, K4 mPos);

	public abstract void punkte(K4[][] into);

	public abstract void gibPl(ArrayList<Anzeige3> dieListe, K4[][] into, LichtW lw,
			boolean gmVision, boolean isMasterVision);

	public abstract void tick();
}