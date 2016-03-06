package nonBlock.formwandler;

import nonBlock.controllable.*;
import wahr.physisch.*;
import wahr.zugriff.*;

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

	public ArrayList<String> giveCommands()
	{
		if(UIVerbunden.godMode)
			return new ArrayList<>();
		ArrayList<String> cmd = new ArrayList<>();
		if(lockL || lockR)
		{
			if(lockL && TA2.keyStat[15] <= 0)
			{
				cmd.add("L_" + k);
				lockL = false;
			}
			if(lockR && TA2.keyStat[16] <= 0)
			{
				cmd.add("R_" + k);
				lockR = false;
			}
		}
		else
		{
			if(TA2.keyStat[15] > 0)
			{
				lockL = true;
				k = -1;
			}
			if(TA2.keyStat[16] > 0)
			{
				lockR = true;
				k = -1;
			}
		}
		if(lockL || lockR)
		{
			for(int i = 0; i < kh.length; i++)
				if(TA2.keyStat[kh[i]] == 2)
					k = i;
		}
		if(!lockL && !lockR)
		{
			if(TA2.keyStat[2] == 2)
				cmd.add("Rechts");
			if(TA2.keyStat[1] == 2)
				cmd.add("Links");
			if(TA2.keyStat[5] == 2)
				cmd.add("Oben");
			if(TA2.keyStat[6] == 2)
				cmd.add("Unten");
			if(TA2.keyStat[3] == 2)
				cmd.add("Vorne");
			if(TA2.keyStat[4] == 2)
				cmd.add("Hinten");
			if(TA2.keyStat[7] == 2)
				cmd.add("Rot");
			if(TA2.keyStat[8] == 2)
				cmd.add("Gn");
		}
		return cmd;
	}

	public boolean[] infl()
	{
		if(UIVerbunden.godMode || lockL || lockR)
			return new boolean[8];
		boolean[] b = new boolean[]
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
		if(k >= 0 && k < 8)
		{
			b[k] = false;
			if(TA2.keyStat[kh[k]] <= 0)
				k = -1;
		}
		return b;
	}
}