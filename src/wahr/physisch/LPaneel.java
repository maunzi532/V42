package wahr.physisch;

import wahr.zugriff.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class LPaneel
{
	public static ArrayList<LPaneel> paneele = new ArrayList<>();

	public JFrame fr;
	public Dimension scF;

	public void init1()
	{
		fr = new JFrame();
		fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		if(Staticf.undecoratedFrame)
			fr.setUndecorated(true);
		fr.setLayout(null);
		if(Staticf.automaximize)
			fr.setExtendedState(Frame.MAXIMIZED_BOTH);
		fr.setSize(Staticf.frameXW, Staticf.frameYH);
		TA2.setzeAnz(1);
		TA2.feedMoves(Index.gibText("Einstellungen", "TA"), 0);
		TA2.addToFrame(fr, Staticf.writeKeyIndex);
	}

	public void init2()
	{
		fr.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE), new Point(), null));
		fr.setVisible(true);
		while(true)
			if(fr.isActive())
				break;
		//UIVerbunden.sc = fr.getSize();
		scF = fr.getSize();
	}

	public void rePanel(Image img, double xp, double yp)
	{
		fr.getGraphics().drawImage(img, (int) (xp * scF.width), (int) (yp * scF.height), null);
	}
}