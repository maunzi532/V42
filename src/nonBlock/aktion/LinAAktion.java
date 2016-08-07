package nonBlock.aktion;

import achsen.*;

public class LinAAktion extends Aktion implements LinkBlocker
{
	boolean[] needCancelAt;

	LinAAktion(NonBlock besitzer, int dauer, int power)
	{
		super((AkA) besitzer, dauer, power);
		needCancelAt = new boolean[besitzer.resLink.length];
	}

	LinAAktion(){}
}