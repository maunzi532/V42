package nonBlock.controllable;

import achsen.*;

public interface Controllable extends IKamera
{
	void doCommand(String command);
}