package nonblockbox.attk.move;

import java.util.*;

public class LadeMove
{
	final ArrayList<LadeAktionB> aktionen;
	int teilE;
	final ArrayList<Integer> zeitE;

	public LadeMove(String code)
	{
		zeitE = new ArrayList<>();
		aktionen = new ArrayList<>();
		String cde1 = code.replace("\n	", "|");
		String[] cde = cde1.split("\n");
		for(int i = 0; i < cde.length; i++)
		{
			if(!cde[i].isEmpty() && !cde[i].startsWith("/"))
			{
				if(cde[i].startsWith("Z "))
				{
					zeitE.add(Integer.parseInt(cde[i].substring(2)));
					teilE++;
				}
				else
				{
					String[] cd2 = cde[i].split(" ");
					aktionen.add(new LadeAktionB(teilE, cd2));
				}
			}
		}
	}
}