package wahr.physisch;

import wahr.zugriff.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class LPaneel
{
	public JFrame fr;
	public Dimension scF;

	public LPaneel(TA2 theTA, int xw, int yh, boolean max, boolean undecorated, int xp, int yp)
	{
		fr = new JFrame();
		fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		if(undecorated)
			fr.setUndecorated(true);
		fr.setLayout(null);
		if(max)
			fr.setExtendedState(Frame.MAXIMIZED_BOTH);
		fr.setSize(xw, yh);
		fr.setLocation(xp, yp);
		fr.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE), new Point(), null));
		theTA.addToFrame(fr, Staticf.writeKeyIndex);
	}

	public void showFrames()
	{
		fr.setVisible(true);
		while(true)
			if(fr.isActive())
				break;
		scF = fr.getSize();
	}

	public void rePanel(Image img, int xp, int yp)
	{
		fr.getGraphics().drawImage(img, xp, yp, null);
	}
}