package frame;

import java.awt.*;
import javax.swing.*;

public class LPaneel
{
	public JFrame fr;
	public Dimension scF;

	public LPaneel(int xw, int yh, boolean max, int xp, int yp)
	{
		fr = new JFrame();
		fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		if(max)
			fr.setExtendedState(Frame.MAXIMIZED_BOTH);
		fr.setSize(xw, yh);
		fr.setLocation(xp, yp);
	}

	public void showFrame()
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