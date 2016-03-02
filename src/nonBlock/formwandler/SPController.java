package nonBlock.formwandler;

import nonBlock.controllable.*;
import wahr.physisch.*;

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
		return cmd;
	}

	public boolean[] infl()
	{
		if(lockL || lockR)
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