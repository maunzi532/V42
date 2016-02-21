package wahr.physisch;

import wahr.zugriff.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class LPaneel
{
	public static JFrame fr;

	public static void init1()
	{
		fr = new JFrame();
		fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		if(Staticf.undecoratedFrame)
			fr.setUndecorated(true);
		fr.setLayout(null);
		if(Staticf.automaximize)
			fr.setExtendedState(Frame.MAXIMIZED_BOTH);
		fr.setSize(Staticf.frameXW, Staticf.frameYH);
		TA2.feedMoves(Index.gibText("Einstellungen", "TA"));
		TA2.addToFrame(fr, Staticf.writeKeyIndex);
	}

	public static void init2()
	{
		fr.setVisible(true);
		while(true)
			if(fr.isActive())
				break;
		UIVerbunden.sc = fr.getSize();
	}

	public static void setC1()
	{
		fr.setCursor(Cursor.getDefaultCursor());
	}

	public static void setC0()
	{
		fr.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_INDEXED), new Point(), null));
		//fr.setCursor(Cursor.getDefaultCursor());
	}

	public static void rePanel(Image img)
	{
		fr.getGraphics().drawImage(img, 0, 0, null);
	}
}