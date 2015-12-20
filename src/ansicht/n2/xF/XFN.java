package ansicht.n2.xF;

import ansicht.n2.*;

import java.awt.*;

public class XFN extends XFarbe
{
	Color farb;

	public XFN(Color farb)
	{
		this.farb = farb;
	}

	XFN(){}

	public Paint gibFarb(N2 n)
	{
		return anpassen(n, farb);
	}
}