package nonBlock.aktion.lesen;

import java.util.*;
import nonBlock.aktion.*;

public class LadeAktion
{
	private static String[] typen = new String[]
			{
					"Multiachsendrehung", //M
					"Freeze", //F
					"KText", //K
					"EndText", //E
					"Bewegung", //B
					"Fokus", //Fo
					"Transformieren", //T
					"Teleport", //Te
					"Collider" //C
			};

	private static List<String> listTypes = Arrays.asList("lina", "adi", "hbox");

	public final int teil;
	public final int zeit;
	private String typ;
	public int akteur = -1;
	private String dislocate;

	private HashMap<String, String> para2 = new HashMap<>();
	private ArrayList<String> theList = new ArrayList<>();

	public LadeAktion(int teil, String[] cd2)
	{
		this.teil = teil;
		zeit = Integer.parseInt(cd2[0]);
		for(int i = 0; i < typen.length; i++)
			if(typen[i].startsWith(cd2[1]))
			{
				typ = typen[i];
				break;
			}
		for(int t = 2; t < cd2.length; t++)
		{
			String[] cd3 = cd2[t].split("=", 2);
			if(cd3[0].equalsIgnoreCase("dis"))
				dislocate = cd3[1];
			else if(cd3[0].equalsIgnoreCase("akt"))
				akteur = Integer.parseInt(cd3[1]);
			else if(listTypes.contains(cd3[0].toLowerCase()))
				theList.add(cd3[1]);
			else
				para2.put(cd3[0].toLowerCase(), cd3[1]);
		}
	}

	public ZDelay erzeugeAktion(AkA besitzer, Tverlay tverlay)
	{
		AkA b2;
		if(dislocate != null)
			b2 = besitzer.plzDislocate(dislocate);
		else
			b2 = besitzer;
		try
		{
			return LadAktion.ak.get(typ).newInstance().erzeuge(typ, b2, besitzer, tverlay, para2, theList);
		}catch(InstantiationException | IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

}