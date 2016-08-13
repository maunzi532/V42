package nonBlock.aktion.lesen;

import ansicht.*;
import nonBlock.aktion.*;
import nonBlock.collide.*;

public class ZLad
{
	public static void eintrag(String name, Class cl)
	{
		LadAktion.ak.put(name, cl);
	}

	public static void rage()
	{
		eintrag("Multiachsendrehung", AktionM.class);
		eintrag("Freeze", Freeze.class);
		eintrag("KText", Tverlay.class);
		eintrag("EndText", Tverlay.class);
		eintrag("Bewegung", MDAktion.class);
		eintrag("Fokus", Overlay.class);
		eintrag("Transformieren", AktionM.class);
		eintrag("Teleport", MDAktion.class);
		eintrag("Collider", ColliderAktion.class);
	}
}