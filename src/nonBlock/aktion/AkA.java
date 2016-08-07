package nonBlock.aktion;

import k4.*;

public interface AkA
{
	K4 position();
	Drehung dreh();
	void mTick();
	default void kontrolle(){}
	AkA plzDislocate(String info);
	void addForced(Forced f);
	void addAktion(Aktion a);
	boolean nofreeze();
}