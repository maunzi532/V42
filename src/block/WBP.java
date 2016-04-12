package block;

public class WBP
{
	public final int[] k;

	public WBP(WBP w)
	{
		k = new int[4];
		System.arraycopy(w.k, 0, k, 0, 4);
	}

	public WBP(int[] k)
	{
		this.k = k;
	}

	public WBP(int a, int b, int c, int d)
	{
		k = new int[4];
		k[0] = a;
		k[1] = b;
		k[2] = c;
		k[3] = d;
	}
}