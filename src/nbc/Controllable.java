package nbc;

import k.*;

public interface Controllable
{
	void kontrolle();
	K4 kamP();
	Drehung kamD();
	void doCommand(String command);
}