package achsen;

import indexLader.*;
import java.util.*;

public interface IFarbe
{
	IFarbe gibNeu(String s);

	IFarbe gibNeu(ArrayList<String> s);

	IFarbe gibNeu(String build, int errStart, int errEnd, ErrorVial vial);
}