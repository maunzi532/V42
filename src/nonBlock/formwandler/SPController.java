package nonBlock.formwandler;

import nonBlock.controllable.*;
import wahr.physisch.*;
import wahr.spieler.*;

import java.util.*;

public class SPController extends Controller
{
	private static int[] kh = new int[]
			{
					2, 1, 5, 6, 3, 4, 7, 8
			};

	boolean lockL;
	boolean lockR;
	int k = -1;
	Spieler master;

	public SPController(Spieler master)
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
			if(lockL && TA2.keyStat[master.taIndex][15] <= 0)
			{
				cmd.add("L_" + k);
				lockL = false;
			}
			if(lockR && TA2.keyStat[master.taIndex][16] <= 0)
			{
				cmd.add("R_" + k);
				lockR = false;
			}
		}
		else
		{
			if(TA2.keyStat[master.taIndex][15] > 0)
			{
				lockL = true;
				k = -1;
			}
			if(TA2.keyStat[master.taIndex][16] > 0)
			{
				lockR = true;
				k = -1;
			}
		}
		if(lockL || lockR)
		{
			for(int i = 0; i < kh.length; i++)
				if(TA2.keyStat[master.taIndex][kh[i]] == 2)
					k = i;
		}
		if(!lockL && !lockR)
		{
			if(TA2.keyStat[master.taIndex][2] == 2)
				cmd.add("Rechts");
			if(TA2.keyStat[master.taIndex][1] == 2)
				cmd.add("Links");
			if(TA2.keyStat[master.taIndex][5] == 2)
				cmd.add("Oben");
			if(TA2.keyStat[master.taIndex][6] == 2)
				cmd.add("Unten");
			if(TA2.keyStat[master.taIndex][3] == 2)
				cmd.add("Vorne");
			if(TA2.keyStat[master.taIndex][4] == 2)
				cmd.add("Hinten");
			if(TA2.keyStat[master.taIndex][7] == 2)
				cmd.add("Rot");
			if(TA2.keyStat[master.taIndex][8] == 2)
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
						TA2.keyStat[master.taIndex][2] > 0,
						TA2.keyStat[master.taIndex][1] > 0,
						TA2.keyStat[master.taIndex][5] > 0,
						TA2.keyStat[master.taIndex][6] > 0,
						TA2.keyStat[master.taIndex][3] > 0,
						TA2.keyStat[master.taIndex][4] > 0,
						TA2.keyStat[master.taIndex][7] > 0,
						TA2.keyStat[master.taIndex][8] > 0,
				};
		if(k >= 0 && k < 8)
		{
			b[k] = false;
			if(TA2.keyStat[master.taIndex][kh[k]] <= 0)
				k = -1;
		}
		return b;
	}
}