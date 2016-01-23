package nonBlock.aktion;

import ansicht.*;
import nonBlock.*;
import nonBlock.aktion.lesen.*;
import wahr.zugriff.*;

import java.util.*;

public class WeltND
{
	public static final ArrayList<NonBlock> nonBlocks = new ArrayList<>();
	public static final ArrayList<Licht> licht = new ArrayList<>();
	public static Sequenz seq = null;
	public static boolean nfr = true;

	public static void timetickN()
	{
		ArrayList<NonBlock> noch = nonBlocks;
		while(noch.size() > 0)
		{
			ArrayList<NonBlock> noch2 = new ArrayList<>();
			for(int i = 0; i < noch.size(); i++)
			{
				Focus fc = noch.get(i).focus;
				if(fc == null || !(fc instanceof Mount) || !noch.contains(((Mount)fc).master))
				{
					noch.get(i).entlinken();
					noch.get(i).punkte();
				}
				else
					noch2.add(noch.get(i));
			}
			noch = noch2;
		}
	}

	public static void timetickD()
	{
		if(!nfr && !UIVerbunden.godMode)
			if(seq.tick())
				seq = null;
		if(UIVerbunden.godMode)
			UIVerbunden.godModeKam.tick();
		else
			for(int i = 0; i < nonBlocks.size(); i++)
				nonBlocks.get(i).tick();
	}
}