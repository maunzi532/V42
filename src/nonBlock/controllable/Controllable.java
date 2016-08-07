package nonBlock.controllable;

import achsen.*;

public interface Controllable extends IKamera
{
	void kontrolle();
	void doCommand(String command);
}