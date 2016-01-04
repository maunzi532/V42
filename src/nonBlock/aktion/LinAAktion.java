package nonBlock.aktion;

public class LinAAktion extends Aktion
{
	final boolean[] needCancelAt;

	protected LinAAktion(NBD besitzer, int dauer, int power)
	{
		super(besitzer, dauer, power);
		needCancelAt = new boolean[besitzer.resLink.length];
	}
}