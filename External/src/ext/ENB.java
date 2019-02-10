package ext;

import a3.*;
import achsen.*;
import java.util.*;
import k4.*;

public class ENB extends NonBlock
{
	public External[] externals = new External[0];

	public void tick()
	{
		for(int i = 0; i < externals.length; i++)
			externals[i].tick();
	}

	public void additional(ArrayList rein, Object lw, boolean kam)
	{
		for(int i = 0; i < externals.length; i++)
			externals[i].gibPl(rein, punkteK, (LichtW)lw, kam);
	}

	public void entlinken()
	{
		super.entlinken();
		for(int i = 0; i < externals.length; i++)
			externals[i].entLink(dreh, position);
	}

	public void punkte2()
	{
		for(int i = 0; i < externals.length; i++)
			externals[i].punkte(punkte);
	}

	public void punkteTransformKam(K4 kam, Drehung kDreh)
	{
		super.punkteTransformKam(kam, kDreh);
		for(int i = 0; i < externals.length; i++)
			if(externals[i] instanceof H)
				((H)externals[i]).transformK(kam, kDreh);
	}
}