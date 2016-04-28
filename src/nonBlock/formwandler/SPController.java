package nonBlock.formwandler;

import ansicht.*;
import nonBlock.controllable.*;

import java.util.*;

public class SPController extends Controller
{
	private static int[] kh = new int[]
			{
					2, 1, 5, 6, 3, 4, 7, 8
			};

	private boolean lockL;
	private boolean lockR;
	private int k = -1;
	private Overlay master;

	public SPController(Overlay master)
	{
		this.master = master;
	}

	public ArrayList<String> giveCommands()
	{
		if(master.godMode)
			return new ArrayList<>();
		ArrayList<String> cmd = new ArrayList<>();
		if(lockL || lockR)
		{
			if(lockL && master.ta.keyStat[master.taIndex][15] <= 0)
			{
				cmd.add("L_" + k);
				lockL = false;
			}
			if(lockR && master.ta.keyStat[master.taIndex][16] <= 0)
			{
				cmd.add("R_" + k);
				lockR = false;
			}
		}
		else
		{
			if(master.ta.keyStat[master.taIndex][15] > 0)
			{
				lockL = true;
				k = -1;
			}
			if(master.ta.keyStat[master.taIndex][16] > 0)
			{
				lockR = true;
				k = -1;
			}
		}
		if(lockL || lockR)
		{
			for(int i = 0; i < kh.length; i++)
				if(master.ta.keyStat[master.taIndex][kh[i]] == 2)
					k = i;
		}
		if(!lockL && !lockR)
		{
			if(master.ta.keyStat[master.taIndex][2] == 2)
				cmd.add("Rechts");
			if(master.ta.keyStat[master.taIndex][1] == 2)
				cmd.add("Links");
			if(master.ta.keyStat[master.taIndex][5] == 2)
				cmd.add("Oben");
			if(master.ta.keyStat[master.taIndex][6] == 2)
				cmd.add("Unten");
			if(master.ta.keyStat[master.taIndex][3] == 2)
				cmd.add("Vorne");
			if(master.ta.keyStat[master.taIndex][4] == 2)
				cmd.add("Hinten");
			if(master.ta.keyStat[master.taIndex][7] == 2)
				cmd.add("Rot");
			if(master.ta.keyStat[master.taIndex][8] == 2)
				cmd.add("Gn");
		}
		return cmd;
	}

	public boolean[] infl()
	{
		if(master.godMode || lockL || lockR)
			return new boolean[8];
		boolean[] b = new boolean[]
				{
						master.ta.keyStat[master.taIndex][2] > 0,
						master.ta.keyStat[master.taIndex][1] > 0,
						master.ta.keyStat[master.taIndex][5] > 0,
						master.ta.keyStat[master.taIndex][6] > 0,
						master.ta.keyStat[master.taIndex][3] > 0,
						master.ta.keyStat[master.taIndex][4] > 0,
						master.ta.keyStat[master.taIndex][7] > 0,
						master.ta.keyStat[master.taIndex][8] > 0,
				};
		if(k >= 0 && k < 8)
		{
			b[k] = false;
			if(master.ta.keyStat[master.taIndex][kh[k]] <= 0)
				k = -1;
		}
		return b;
	}
}