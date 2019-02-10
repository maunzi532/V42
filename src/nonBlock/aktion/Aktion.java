package nonBlock.aktion;

public class Aktion
{
	public AkA besitzer;
	public int dauer;
	public int aktuell;
	public boolean needCancel;
	public int power;

	protected Aktion(AkA besitzer, int dauer, int power)
	{
		this.besitzer = besitzer;
		this.dauer = dauer;
		this.power = power;
	}

	protected Aktion(){}

	public void delink(){}

	public void tick(){}
}