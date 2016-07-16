package achsen;

public class LadePunkt
{
	public final int achse;
	public final double vor;
	public final double spin;
	public final double abstand;

	public LadePunkt(int achse, double vor, double spinA, double abstand)
	{
		this.achse = achse;
		this.vor = vor;
		spin = spinA * Math.PI / 180;
		this.abstand = abstand;
	}
}