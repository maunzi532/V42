package ansicht.n2.xF;

public enum Material
{
	N(50, -255),
	H(600, -255),
	B(100, -255);

	double shadeMultiplier;
	int startPower;

	Material(double shadeMultiplier, int startPower)
	{
		this.shadeMultiplier = shadeMultiplier;
		this.startPower = startPower;
	}
}