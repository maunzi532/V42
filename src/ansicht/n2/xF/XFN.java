package ansicht.n2.xF;

import ansicht.n2.*;

import java.awt.*;

public class XFN extends XFarbe
{
	Material mat;
	Color farb;

	public XFN(Color farb, Material mat)
	{
		this.farb = farb;
		this.mat = mat;
	}

	XFN(){}

	public Paint gibFarb(N2 n, Long tn)
	{
		if(n instanceof F2)
			return anpassen((F2)n, farb, mat, tn);
		return farb;
	}
}