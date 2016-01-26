package nonBlock.aussehen;

import wahr.zugriff.*;

public class Mount extends Focus
{
	public final NonBlock master;
	private final int achse;
	private final int punkt;
	private final Drehung dreh;

	public Mount(NonBlock the, NonBlock master, int achse, int punkt, Drehung dreh, int focusTime)
	{
		super(the);
		this.master = master;
		this.achse = achse;
		this.punkt = punkt;
		this.dreh = dreh;
		this.focusTime = focusTime;
	}

	public void lade()
	{
		targetPosition = new K4(master.punkte[achse][punkt]);
		targetDreh = Drehung.plus(Drehung.plus(master.achsen[achse].dreh, master.dreh), dreh);
		super.lade();
	}
}