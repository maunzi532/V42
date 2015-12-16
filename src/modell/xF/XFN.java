package modell.xF;

import ansicht.*;

import java.awt.*;

public class XFN extends XFarbe
{
	protected Color farb;

	public XFN(Color farb)
	{
		this.farb = farb;
	}

	protected XFN(){}

	public void setFarb(N2 n)
	{
		setFarb(n, farb);
	}
}