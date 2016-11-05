package indexLader;

public class KXS
{
	final boolean splitv2;
	final boolean extractKey;
	final boolean expectNumbers;
	final boolean expectText;
	final boolean keyOnlyOk;
	final boolean removeKlammern;

	public KXS(boolean splitv2, boolean extractKey, boolean expectNumbers, boolean expectText, boolean keyOnlyOk,
			boolean removeKlammern)
	{
		this.splitv2 = splitv2;
		this.extractKey = extractKey;
		this.expectNumbers = expectNumbers;
		this.expectText = expectText;
		this.keyOnlyOk = keyOnlyOk;
		this.removeKlammern = removeKlammern;
	}
}