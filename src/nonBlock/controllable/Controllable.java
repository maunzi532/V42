package nonBlock.controllable;

import wahr.zugriff.*;

public interface Controllable
{
	void kontrolle();
	K4 kamP();
	Drehung kamD();
	void doCommand(String command);
}