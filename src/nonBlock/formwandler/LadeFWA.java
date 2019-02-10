package nonBlock.formwandler;

import java.util.*;

public class LadeFWA
{
	ArrayList<String> zustands;
	ArrayList<ArrayList<String>> usedInputs;
	ArrayList<ArrayList<LadeControlledMove>> availMoves;
	int cldSize;

	public LadeFWA(int cldSize)
	{
		zustands = new ArrayList<>();
		usedInputs = new ArrayList<>();
		availMoves = new ArrayList<>();
		this.cldSize = cldSize;
	}

	public void charge(LadeFWATeil teil)
	{
		for(int i = 0; i < teil.zustands.size(); i++)
		{
			if(zustands.contains(teil.zustands.get(i)))
			{
				int j = zustands.indexOf(teil.zustands.get(i));
				for(int k = 0; k < teil.usedInputs.get(i).size(); k++)
				{
					if(usedInputs.get(j).contains(teil.usedInputs.get(i).get(k)))
					{
						int l = usedInputs.get(j).indexOf(teil.usedInputs.get(i).get(k));
						usedInputs.get(j).set(l, teil.usedInputs.get(i).get(k));
						availMoves.get(j).set(l, teil.availMoves.get(i).get(k));
					}
					else
					{
						usedInputs.get(j).add(teil.usedInputs.get(i).get(k));
						availMoves.get(j).add(teil.availMoves.get(i).get(k));
					}
				}
			}
			else
			{
				int j = zustands.size();
				zustands.add(teil.zustands.get(i));
				usedInputs.add(new ArrayList<>());
				availMoves.add(new ArrayList<>());
				for(int k = 0; k < teil.usedInputs.get(i).size(); k++)
				{
					usedInputs.get(j).add(teil.usedInputs.get(i).get(k));
					availMoves.get(j).add(teil.availMoves.get(i).get(k));
				}
			}
		}
	}
}