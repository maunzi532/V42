package edit;

import achsen.*;
import k4.*;

public class AEKam2 extends AEKam
{
	int w = 0;

	public AEKam2(K4 ziel, double abstand, Drehung winkel, double turnSpeed)
	{
		super(ziel, abstand, winkel, turnSpeed);
	}

	public void forderePositionAn(AchsenK1 ak1)
	{
		ak1.position = new K4(w, 0, 0, 0);
		ak1.dreh = new Drehung();
		w += 30;
	}
}