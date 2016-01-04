package nonBlock.aktion;

public class Aktion
{
	final NBD besitzer;
	public final int dauer;
	public int aktuell;
	public boolean needCancel;
	public final int power;

	protected Aktion(NBD besitzer, int dauer, int power)
	{
		this.besitzer = besitzer;
		this.dauer = dauer;
		this.power = power;
	}

	public void delink(){}

	public void tick(){}
}