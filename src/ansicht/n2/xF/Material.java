package ansicht.n2.xF;

public enum Material
{
	N(50, -255, 1),
	H(200, -255, 2),
	B(100, -255, 1),
	STOFF(20, -200, 0.6),
	METALL(120, -255, 1.2),
	PLASTIK(40, -100, 1),
	NOSHADE(0, 0, 0),
	DARKNESS(0, -1000, 0);

	public double shadeMultiplier;
	int startPower;
	public double lichtAffection;

	Material(double shadeMultiplier, int startPower, double lichtAffection)
	{
		this.shadeMultiplier = shadeMultiplier;
		this.startPower = startPower;
		this.lichtAffection = lichtAffection;
	}
}