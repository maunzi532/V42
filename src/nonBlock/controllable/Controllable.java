package nonBlock.controllable;

import a3.*;

public interface Controllable extends IKamera
{
	void kontrolle();
	void doCommand(String command);
}