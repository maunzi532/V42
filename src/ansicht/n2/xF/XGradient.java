package ansicht.n2.xF;

import ansicht.n2.*;

import java.awt.*;

public class XGradient extends XFarbe
{
	private final Color f1;
	private final Color f2;
	private final int s1;
	private final int s2;

	public XGradient(Color f1, int s1, Color f2, int s2)
	{
		this.f1 = f1;
		this.s1 = s1;
		this.f2 = f2;
		this.s2 = s2;
	}

	public Paint gibFarb(N2 n)
	{
		if(n instanceof F2)
			return new GradientPaint(n.xse[s1], n.yse[s1], anpassen((F2)n, f1),
					n.xse[s2], n.yse[s2], anpassen((F2)n, f2));
		throw new RuntimeException("Nur F2 haben Gradienten");
	}
}