package a3;

public enum Material
{
	N(50, 1),
	H(200, 2),
	B(100, 1),
	STOFF(20, 0.6),
	METALL(120, 1.2),
	PLASTIK(40, 1),
	NOSHADE(0, 0);

	public double shadeMultiplier;
	public double lichtAffection;

	Material(double shadeMultiplier, double lichtAffection)
	{
		this.shadeMultiplier = shadeMultiplier;
		this.lichtAffection = lichtAffection;
	}
}