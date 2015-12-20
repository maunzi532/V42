package ansicht.n2.xF;

import ansicht.n2.*;

import java.awt.*;

public class XGradient extends XFarbe
{
	Color f1, f2;
	int s1, s2;

	public XGradient(Color f1, int s1, Color f2, int s2)
	{
		this.f1 = f1;
		this.s1 = s1;
		this.f2 = f2;
		this.s2 = s2;
	}

	public Paint gibFarb(N2 n)
	{
		return new GradientPaint(n.xse[s1], n.yse[s1], anpassen(n, f1),
				n.xse[s2], n.yse[s2], anpassen(n, f2));
	}
}