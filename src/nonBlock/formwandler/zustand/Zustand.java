package nonBlock.formwandler.zustand;

import achsen.*;
import nonBlock.formwandler.*;

public abstract class Zustand
{
	FWA z;
	Focus timer;
	Zustand next;

	public Zustand(FWA z)
	{
		this.z = z;
	}

	public abstract void kontrolleDrehung();

	protected abstract void kontrolleX(int[] infl);

	protected abstract void next();

	public void kontrolle(int[] infl)
	{
		//System.out.println(z.zustand.getClass());
		if(next == null)
			kontrolleX(infl);
		else if(timer == null || timer.focusTime <= 0)
		{
			z.zustand = next;
			next.next();
		}
	}

	public void focus(Focus timer, Zustand next)
	{
		this.timer = timer;
		this.next = next;
		if(timer != null)
			z.focus = timer;
	}
}