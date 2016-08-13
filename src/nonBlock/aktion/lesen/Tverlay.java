package nonBlock.aktion.lesen;

import ansicht.text.*;
import java.util.*;
import nonBlock.aktion.*;
import nonBlock.formwandler.*;

public class Tverlay implements LadAktion
{
	public SchalterLayer sl;
	public TextWelt tw;

	public Tverlay(){}

	public Tverlay(TextWelt tw)
	{
		sl = new SchalterLayer();
		this.tw = tw;
	}

	@Override
	public ZDelay erzeuge(String whtd, AkA dislocated, AkA besitzer2,
			HashMap<String, String> parameters, ArrayList<String> list, AkA[] akteure2)
	{
		if(parameters.containsKey("toall"))
			((FWA)dislocated).tverlay.tw.sendThemAll(parameters.get("text"),
					parameters.get("dispname"),
					parameters.get("codebez"),
					parameters.get("emotion"));
		else
		{
			TBox tBox = new TBox(whtd.charAt(0) == 'E', parameters.get("text"));
			((FWA)dislocated).tverlay.sl.placeTBox(tBox, parameters.get("dispname"),
					parameters.get("codebez"), parameters.get("emotion"));
			if(whtd.charAt(0) == 'E')
				return tBox;
		}
		return null;
	}
}