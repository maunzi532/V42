package ansicht.a3;

import nonBlock.aktion.*;

import java.util.*;

public class Vor
{
	public ArrayList<Anzeige3> anzeige;
	private final ArrayList<Double> abstands;
	private final ArrayList<Integer> splits;
	private WeltND dw;
	//4D-Blick an/aus
	public int visionRange4D;
	//Blocks sehen
	public boolean siehBlocks = true;
	//NonBlocks sehen
	public boolean siehNonBlocks = true;

	public Vor(String abstandSplitInput, WeltND dw)
	{
		abstands = new ArrayList<>();
		splits = new ArrayList<>();
		String[] zeilen = abstandSplitInput.split("\n");
		for(int i = 0; i < zeilen.length; i++)
			if(!zeilen[i].isEmpty() && !zeilen[i].startsWith("/"))
			{
				String[] z1 = zeilen[i].split(" ");
				abstands.add(Double.parseDouble(z1[0]));
				splits.add(Integer.parseInt(z1[1]));
			}
		this.dw = dw;
		//TODO BlockN3 init
	}
}