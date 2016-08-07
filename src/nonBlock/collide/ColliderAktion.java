package nonBlock.collide;

import java.util.*;
import java.util.stream.*;
import nonBlock.aktion.*;
import nonBlock.aktion.lesen.*;

public class ColliderAktion extends Aktion implements ZDelay, LadAktion
{
	private Collider collider;
	public int delay;

	public ColliderAktion(){}

	public ColliderAktion(NBB besitzer, int dauer, int power, Collider collider, NBB nht)
	{
		super(besitzer, dauer, power);
		this.collider = collider;
		collider.charge(besitzer, nht);
		for(int i = 0; i < collider.h.size(); i++)
			collider.h.get(i).besitzer = besitzer;
	}

	public void delink()
	{
		collider.ende();
	}

	public boolean fertig(int timeLeft)
	{
		if(collider.con.size() == 0)
			return timeLeft >= 0;
		else
			return timeLeft >= delay;
	}

	@Override
	public ZDelay erzeuge(String whtd, AkA dislocated, AkA besitzer2, Tverlay tverlay,
			HashMap<String, String> parameters, ArrayList<String> list)
	{
		Collider newc = new Collider(Integer.parseInt(parameters.get("collider")));
		newc.h.addAll(list.stream().map(Hitbox::new).collect(Collectors.toList()));
		ColliderAktion ak = new ColliderAktion((NBB) dislocated,
				Integer.parseInt(parameters.get("dauer")),
				Integer.parseInt(parameters.get("power")),
				newc, (NBB) besitzer2.plzDislocate(parameters.get("nht")));
		dislocated.addAktion(ak);
		if(parameters.containsKey("delay"))
		{
			ak.delay = Integer.parseInt(parameters.get("delay"));
			return ak;
		}
		return null;
	}
}