package nonBlock.aktion;

public class ADI
{
	int linA;
	int anfD;
	int lenD;
	double lenZ;
	double dwbZ;
	double dwlZ;
	double spnZ;
	double dd4Z;
	boolean zv;

	private ADI(){}

	public static ADI rad(int linA, int anfD, int lenD,
			double lenZ, double dwbZ, double dwlZ, double spnZ, double dd4Z, boolean zv)
	{
		ADI a = new ADI();
		a.linA = linA;
		a.anfD = anfD;
		a.lenD = lenD;
		a.lenZ = lenZ;
		a.dwbZ = dwbZ;
		a.dwlZ = dwlZ;
		a.spnZ = spnZ;
		a.dd4Z = dd4Z;
		a.zv = zv;
		return a;
	}

	public static ADI deg(int linA, int anfD, int lenD,
			double lenZ, double dwbZ, double dwlZ, double spnZ, double dd4Z, boolean zv)
	{
		ADI a = new ADI();
		a.linA = linA;
		a.anfD = anfD;
		a.lenD = lenD;
		a.lenZ = lenZ;
		a.dwbZ = dwbZ / 180 * Math.PI;
		a.dwlZ = dwlZ / 180 * Math.PI;
		a.spnZ = spnZ / 180 * Math.PI;
		a.dd4Z = dd4Z;
		a.zv = zv;
		return a;
	}

	public ADI(String code, boolean zv)
	{
		String[] cde1 = code.split(",");
		linA = Integer.parseInt(cde1[0]);
		anfD = Integer.parseInt(cde1[1]);
		lenD = Integer.parseInt(cde1[2]);
		lenZ = Double.parseDouble(cde1[3]);
		dwbZ = Double.parseDouble(cde1[4]) / 180 * Math.PI;
		dwlZ = Double.parseDouble(cde1[5]) / 180 * Math.PI;
		spnZ = Double.parseDouble(cde1[6]) / 180 * Math.PI;
		dd4Z = Double.parseDouble(cde1[7]);
		this.zv = zv;
	}
}