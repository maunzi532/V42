package nonBlock.aktion.lesen;

import ansicht.text.*;

public class Tverlay
{
	public SchalterLayer sl;
	public TextWelt tw;

	protected Tverlay(){}

	public Tverlay(TextWelt tw)
	{
		sl = new SchalterLayer();
		this.tw = tw;
	}
}