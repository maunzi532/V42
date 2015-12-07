package nonBlock;

import k.*;

public class Focus
{
	protected final NonBlock the;
	protected int focusTime;
	protected K4 targetPosition;
	protected Drehung targetDreh;

	public Focus(NonBlock the, int focusTime, K4 targetPosition, Drehung targetDreh)
	{
		this.the = the;
		this.focusTime = focusTime;
		this.targetPosition = targetPosition;
		this.targetDreh = targetDreh;
	}

	public Focus(NonBlock the, int focusTime, K4 targetPosition)
	{
		this.the = the;
		this.focusTime = focusTime;
		this.targetPosition = targetPosition;
	}

	public Focus(NonBlock the)
	{
		this.the = the;
	}

	public void lade()
	{
		if(focusTime > 0)
		{
			the.position = new K4((the.position.a * (focusTime - 1) + targetPosition.a) / focusTime,
					(the.position.b * (focusTime - 1) + targetPosition.b) / focusTime,
					(the.position.c * (focusTime - 1) + targetPosition.c) / focusTime,
					(the.position.d * (focusTime - 1) + targetPosition.d) / focusTime);
			if(targetDreh != null)
				the.dreh = new Drehung((the.dreh.wl * (focusTime - 1) + targetDreh.wl) / focusTime,
						(the.dreh.wb * (focusTime - 1) + targetDreh.wb) / focusTime);
			focusTime--;
		}
		else if(focusTime == 0)
		{
			the.position = new K4(targetPosition);
			if(targetDreh != null)
				the.dreh = new Drehung(targetDreh);
		}
	}
}