package nbc;

import falsch.*;
import fnd.*;

import java.util.*;

public class GMC extends Controller
{
	public ArrayList<String> giveCommands()
	{
		ArrayList<String> cmd = new ArrayList<>();
		if(UIVerbunden.godMode)
		{
			if(TA2.keyStat[15] == 2)
				cmd.add("Hoch");
			if(TA2.keyStat[16] == 2)
				cmd.add("Weg");
		}
		return cmd;
	}

	public boolean[] infl()
	{
		if(UIVerbunden.godMode)
			return new boolean[]
					{
							TA2.keyStat[2] > 0,
							TA2.keyStat[1] > 0,
							TA2.keyStat[5] > 0,
							TA2.keyStat[6] > 0,
							TA2.keyStat[3] > 0,
							TA2.keyStat[4] > 0,
							TA2.keyStat[7] > 0,
							TA2.keyStat[8] > 0,
					};
		else
			return new boolean[8];
	}
}