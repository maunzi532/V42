package nonBlock.controllable;

import ansicht.*;
import wahr.physisch.*;

import java.util.*;

public class GMC extends Controller
{
	Overlay master;

	public GMC(Overlay master)
	{
		this.master = master;
	}

	public ArrayList<String> giveCommands()
	{
		ArrayList<String> cmd = new ArrayList<>();
		if(master.godMode)
		{
			if(TA2.keyStat[master.taIndex][15] == 2)
				cmd.add("Hoch");
			if(TA2.keyStat[master.taIndex][16] == 2)
				cmd.add("Weg");
			if(TA2.keyStat[master.taIndex][14] == 2)
				cmd.add("Dreh");
			if(TA2.keyStat[master.taIndex][9] == 2)
				cmd.add("taGet");
			if(TA2.keyStat[master.taIndex][2] == 2)
				cmd.add("R2");
			if(TA2.keyStat[master.taIndex][1] == 2)
				cmd.add("R0");
			if(TA2.keyStat[master.taIndex][5] == 2)
				cmd.add("H0");
			if(TA2.keyStat[master.taIndex][6] == 2)
				cmd.add("H1");
			if(TA2.keyStat[master.taIndex][3] == 2)
				cmd.add("R3");
			if(TA2.keyStat[master.taIndex][4] == 2)
				cmd.add("R1");
			if(TA2.keyStat[master.taIndex][7] == 2)
				cmd.add("D1");
			if(TA2.keyStat[master.taIndex][8] == 2)
				cmd.add("D0");
		}
		return cmd;
	}

	public boolean[] infl()
	{
		if(master.godMode)
			return new boolean[]
					{
							TA2.keyStat[master.taIndex][2] > 0,
							TA2.keyStat[master.taIndex][1] > 0,
							TA2.keyStat[master.taIndex][5] > 0,
							TA2.keyStat[master.taIndex][6] > 0,
							TA2.keyStat[master.taIndex][3] > 0,
							TA2.keyStat[master.taIndex][4] > 0,
							TA2.keyStat[master.taIndex][7] > 0,
							TA2.keyStat[master.taIndex][8] > 0,
					};
		else
			return new boolean[8];
	}
}