package nonBlock.aktion;

public class Aktion
{
	public NBD besitzer;
	public int dauer;
	public int aktuell;
	public boolean needCancel;
	public int power;

	protected Aktion(NBD besitzer, int dauer, int power)
	{
		this.besitzer = besitzer;
		this.dauer = dauer;
		this.power = power;
	}

	protected Aktion(){}

	public void delink(){}

	public void tick(){}
}