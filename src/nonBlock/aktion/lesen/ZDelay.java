package nonBlock.aktion.lesen;

public interface ZDelay
{
	boolean fertig(int timeLeft);

	static boolean fertig(ZDelay ender, int timeLeft)
	{
		if(ender == null)
			return timeLeft >= 0;
		else
			return ender.fertig(timeLeft);
	}
}