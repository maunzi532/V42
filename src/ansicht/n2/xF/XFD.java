package ansicht.n2.xF;

import ansicht.n2.*;

import java.awt.*;

public class XFD extends XFarbe
{
	private Color farb;

	public XFD(Color farb)
	{
		this.farb = farb;
	}

	public Paint gibFarb(N2 n)
	{
		return farb;
	}
}