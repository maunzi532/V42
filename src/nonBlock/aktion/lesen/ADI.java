package nonBlock.aktion.lesen;

public class ADI
{
	public int linA;
	public int anfD;
	public int lenD;
	public RZahl lenZ;
	public RZahl dwbZ;
	public RZahl dwlZ;
	public RZahl spnZ;
	public RZahl dd4Z;
	public boolean zv;

	private ADI(){}

	public static ADI rad(int linA, int anfD, int lenD,
			RZahl lenZ, RZahl dwbZ, RZahl dwlZ, RZahl spnZ, RZahl dd4Z, boolean zv)
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
			RZahl lenZ, RZahl dwbZ, RZahl dwlZ, RZahl spnZ, RZahl dd4Z, boolean zv)
	{
		ADI a = new ADI();
		a.linA = linA;
		a.anfD = anfD;
		a.lenD = lenD;
		a.lenZ = lenZ;
		dwbZ.deg2rad = true;
		dwlZ.deg2rad = true;
		spnZ.deg2rad = true;
		a.dwbZ = dwbZ;
		a.dwlZ = dwlZ;
		a.spnZ = spnZ;
		a.dd4Z = dd4Z;
		a.zv = zv;
		return a;
	}

	public ADI(String code, boolean zv, LadeAktion e)
	{
		String[] cde1 = code.split(",");
		linA = Integer.parseInt(cde1[0]);
		anfD = Integer.parseInt(cde1[1]);
		lenD = Integer.parseInt(cde1[2]);
		lenZ = new RZahl(cde1[3], false, e);
		dwbZ = new RZahl(cde1[4], true, e);
		dwlZ = new RZahl(cde1[5], true, e);
		spnZ = new RZahl(cde1[6], true, e);
		dd4Z = new RZahl(cde1[7], false, e);
		this.zv = zv;
	}
}