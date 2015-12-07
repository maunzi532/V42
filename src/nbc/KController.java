package nbc;

import fnd.*;

import java.util.*;

public class KController extends Controller
{
	public ArrayList<String> giveCommands()
	{
		ArrayList<String> cmd = new ArrayList<>();
		if(TA2.keyStat[9] == 2)
			cmd.add("A");
		if(TA2.keyStat[10] == 2)
			cmd.add("B");
		if(TA2.keyStat[15] == 2)
			cmd.add("KL");
		if(TA2.keyStat[16] == 2)
			cmd.add("KR");
		if(TA2.keyStat[6] == 2)
			cmd.add("unten");
		if(TA2.keyStat[5] == 2)
			cmd.add("oben");
		if(TA2.keyStat[3] == 2)
			cmd.add("vorne");
		if(TA2.keyStat[4] == 2)
			cmd.add("hinten");
		return cmd;
	}

	public boolean[] infl()
	{
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
	}
}