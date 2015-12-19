package nonblockbox.aktion;

import ansicht.*;
import falsch.*;
import nonBlock.*;
import nonblockbox.aktion.seq.*;

import java.util.*;

public class WeltND
{
	public static final ArrayList<NonBlock> nonBlocks = new ArrayList<>();
	public static final ArrayList<Licht> licht = new ArrayList<>();
	public static Sequenz seq = null;
	public static boolean nfr = true;

	public static void timetickN()
	{
		for(int i = 0; i < nonBlocks.size(); i++)
			nonBlocks.get(i).entlinkt = false;
		int toEntLink = nonBlocks.size();
		while(toEntLink > 0)
			for(int i = 0; i < nonBlocks.size(); i++)
				if(!nonBlocks.get(i).entlinkt)
				{
					Focus fc = nonBlocks.get(i).focus;
					if(fc == null || !(fc instanceof Mount) || ((Mount)fc).master.entlinkt)
					{
						nonBlocks.get(i).entlinken();
						nonBlocks.get(i).punkte();
						nonBlocks.get(i).entlinkt = true;
						toEntLink--;
					}
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