package nonBlock.aussehen;

import ansicht.n2.xF.*;

import java.util.*;

public class LadeF2
{
	public final ArrayList<Integer> ecken1;
	public final ArrayList<Integer> ecken2;
	public final ArrayList<Boolean> ecken3;
	public final ArrayList<Integer> spken1;
	public final ArrayList<Integer> spken2;
	public final ArrayList<Boolean> spken3;
	public final XFarbe farbe;
	public final Boolean seite;
	public final int seed;

	private static final Random seedgeber = new Random();

	public LadeF2(XFarbe farbe, Boolean seite)
	{
		ecken1 = new ArrayList<>();
		ecken2 = new ArrayList<>();
		ecken3 = new ArrayList<>();
		spken1 = new ArrayList<>();
		spken2 = new ArrayList<>();
		spken3 = new ArrayList<>();
		this.farbe = farbe;
		this.seite = seite;
		seed = seedgeber.nextInt();
	}
}