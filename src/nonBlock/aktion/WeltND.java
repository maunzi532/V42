package nonBlock.aktion;

import nonBlock.aktion.lesen.*;
import nonBlock.aussehen.*;
import wahr.zugriff.*;

import java.util.*;

public class WeltND
{
	public final ArrayList<NonBlock> nonBlocks = new ArrayList<>();
	public Move seq = null;
	public boolean gmFreeze;

	public boolean nofreeze()
	{
		return seq == null && (!UIVerbunden.godMode || !gmFreeze);
	}

	private boolean noGMFreeze()
	{
		return !UIVerbunden.godMode || !gmFreeze;
	}

	public void timetickN()
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

	public void timetickD()
	{
		if(!nofreeze() && seq != null)
			if(seq.tick())
				seq = null;
		if(UIVerbunden.godMode)
			UIVerbunden.godModeKam.tick();
		if(noGMFreeze())
			for(int i = 0; i < nonBlocks.size(); i++)
				nonBlocks.get(i).tick();
	}
}