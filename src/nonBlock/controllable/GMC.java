package nonBlock.controllable;

import wahr.physisch.*;
import wahr.zugriff.*;

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
			if(TA2.keyStat[9] == 2)
				cmd.add("taGet");
			if(TA2.keyStat[2] == 2)
				cmd.add("R2");
			if(TA2.keyStat[1] == 2)
				cmd.add("R0");
			if(TA2.keyStat[5] == 2)
				cmd.add("H0");
			if(TA2.keyStat[6] == 2)
				cmd.add("H1");
			if(TA2.keyStat[3] == 2)
				cmd.add("R3");
			if(TA2.keyStat[4] == 2)
				cmd.add("R1");
			if(TA2.keyStat[7] == 2)
				cmd.add("D1");
			if(TA2.keyStat[8] == 2)
				cmd.add("D0");
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