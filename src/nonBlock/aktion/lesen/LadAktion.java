package nonBlock.aktion.lesen;

import java.util.*;
import nonBlock.aktion.*;

public interface LadAktion
{
	HashMap<String, Class<LadAktion>> ak = new HashMap<>();

	ZDelay erzeuge(String whtd, AkA dislocated, AkA besitzer2,
			HashMap<String, String> parameters, ArrayList<String> list, AkA[] akteure2);
}