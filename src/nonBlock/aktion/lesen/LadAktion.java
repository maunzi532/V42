package nonBlock.aktion.lesen;

import java.util.*;
import nonBlock.aktion.*;

public interface LadAktion
{
	HashMap<String, Class<LadAktion>> ak = new HashMap<>();

	ZDelay erzeuge(String whtd, NBD dislocated, NBD besitzer2, Tverlay tverlay,
			HashMap<String, String> parameters, ArrayList<String> list);
}