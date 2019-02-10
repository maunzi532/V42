package nonBlock.controllable;

import ansicht.*;

import java.util.*;

public class GMC extends Controller
{
	private Overlay master;

	public GMC(Overlay master)
	{
		this.master = master;
	}

	public ArrayList<String> giveCommands()
	{
		ArrayList<String> cmd = new ArrayList<>();
		if(master.godMode)
		{
			if(master.ta.keyStat[master.taIndex][15] == 2)
				cmd.add("Weg");
			if(master.ta.keyStat[master.taIndex][16] == 2)
				cmd.add("Neu");
			if(master.ta.keyStat[master.taIndex][14] == 2)
				cmd.add("Dreh");
			if(master.ta.keyStat[master.taIndex][10] == 2)
				cmd.add("ScrollB");
			if(master.ta.keyStat[master.taIndex][9] == 2)
				cmd.add("taGet");
			if(master.ta.keyStat[master.taIndex][2] == 2)
				cmd.add("R2");
			if(master.ta.keyStat[master.taIndex][1] == 2)
				cmd.add("R0");
			if(master.ta.keyStat[master.taIndex][5] == 2)
				cmd.add("H0");
			if(master.ta.keyStat[master.taIndex][6] == 2)
				cmd.add("H1");
			if(master.ta.keyStat[master.taIndex][3] == 2)
				cmd.add("R3");
			if(master.ta.keyStat[master.taIndex][4] == 2)
				cmd.add("R1");
			if(master.ta.keyStat[master.taIndex][7] == 2)
				cmd.add("D1");
			if(master.ta.keyStat[master.taIndex][8] == 2)
				cmd.add("D0");
		}
		return cmd;
	}

	public int[] infl()
	{
		if(master.godMode)
		{
			int[] src = master.ta.keyStat[master.taIndex];
			return new int[]{src[4], src[3], src[1], src[2], src[6], src[5], src[7], src[8]};
		}
		else
			return new int[8];
	}
}