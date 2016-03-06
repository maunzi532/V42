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

	public Paint gibFarb(N2 n, Long tn)
	{
		Color fc = farb;
		if(n.tn != -1 && tn != null && n.tn == tn)
			fc = limit(fc, 60, 60, 60);
		return fc;
	}
}