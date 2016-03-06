package ansicht.n2.xF;

import ansicht.n2.*;

import java.awt.*;

public class XGradient extends XFarbe
{
	private final Color f1;
	private final Color f2;
	private final int s1;
	private final int s2;
	private final Material mat;

	public XGradient(Color f1, int s1, Color f2, int s2, Material mat)
	{
		this.f1 = f1;
		this.s1 = s1;
		this.f2 = f2;
		this.s2 = s2;
		this.mat = mat;
	}

	public Paint gibFarb(N2 n, Long tn)
	{
		if(n instanceof F2)
			return new GradientPaint(n.xse[s1], n.yse[s1], anpassen((F2)n, f1, mat, tn),
					n.xse[s2], n.yse[s2], anpassen((F2)n, f2, mat, tn));
		throw new RuntimeException("Nur F2 haben Gradienten");
	}
}