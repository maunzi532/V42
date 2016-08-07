package nonBlock.aktion.lesen;

import ansicht.text.*;
import java.util.*;
import nonBlock.aktion.*;

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
	public ZDelay erzeuge(String whtd, NBD dislocated, NBD besitzer2, Tverlay tverlay,
			HashMap<String, String> parameters, ArrayList<String> list)
	{
		if(parameters.containsKey("toAll"))
			tverlay.tw.sendThemAll(parameters.get("text"),
					parameters.get("dispName"),
					parameters.get("codebez"),
					parameters.get("emotion"));
		else
		{
			TBox tBox = new TBox(whtd.charAt(0) == 'E', parameters.get("text"));
			tverlay.sl.placeTBox(tBox, parameters.get("dispName"),
					parameters.get("codebez"), parameters.get("emotion"));
			if(whtd.charAt(0) == 'E')
				return tBox;
		}
		return null;
	}
}