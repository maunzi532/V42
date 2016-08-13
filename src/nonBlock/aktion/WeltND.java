package nonBlock.aktion;

import achsen.*;
import java.util.*;
import nonBlock.aktion.lesen.*;

public class WeltND
{
	public final ArrayList<NonBlock> nonBlocks = new ArrayList<>();
	public Move seq = null;
	public boolean gmFreeze;

	public boolean nofreeze()
	{
		return seq == null && !gmFreeze;
	}

	public void entlinke()
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

	public void alleTick()
	{
		if(!nofreeze() && seq != null)
			if(seq.tick())
				seq = null;
		for(int i = 0; i < nonBlocks.size(); i++)
			if(!gmFreeze || nonBlocks.get(i) instanceof GMMover)
				nonBlocks.get(i).tick();
	}
}