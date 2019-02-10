package edit;

import achsen1.*;
import k4.*;

public class AEKam2 extends AEKam
{
	int w = 0;

	public AEKam2(double abstand, double turnSpeed, double mvSpeed)
	{
		super(abstand, turnSpeed, mvSpeed);
	}

	public void forderePositionAn(AchsenK1 ak1)
	{
		ak1.position = new K4(w, 0, 0, 0);
		ak1.dreh = new Drehung();
		w += 30;
	}
}