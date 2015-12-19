package nonblockbox.aktion;

import k.*;

public class MDAktion extends Aktion
{
	private final Boolean[] mvdA;
	private final double[] mvd0;
	private final double[] mvdT;

	public MDAktion(NBD besitzer, int dauer, double[] mvd, Boolean[] mvdA)
	{
		super(besitzer, dauer, 0);
		this.mvdA = mvdA;
		mvd0 = new double[]{besitzer.position.a, besitzer.position.b,
				besitzer.position.c, besitzer.position.d, besitzer.dreh.wl, besitzer.dreh.wb};
		mvdT = new double[6];
		for(int i = 0; i < 6; i++)
			if(mvdA[i] != null)
			{
				if(mvdA[i])
					mvdT[i] = mvd[i];
				else
					mvdT[i] = mvd0[i] + mvd[i];
			}
		mvdT[4] = Drehung.sichern(mvdT[4]);
		mvdT[5] = Drehung.sichern(mvdT[5]);
	}

	public void tick()
	{
		int m1 = dauer - aktuell;
		if(mvdA[0] != null)
			besitzer.bewegung.a = (mvd0[0] * m1 + mvdT[0] * aktuell) / dauer - besitzer.position.a;
		if(mvdA[1] != null)
			besitzer.bewegung.b = (mvd0[1] * m1 + mvdT[1] * aktuell) / dauer - besitzer.position.b;
		if(mvdA[2] != null)
			besitzer.bewegung.c = (mvd0[2] * m1 + mvdT[2] * aktuell) / dauer - besitzer.position.c;
		if(mvdA[3] != null)
			besitzer.bewegung.d = (mvd0[3] * m1 + mvdT[3] * aktuell) / dauer - besitzer.position.d;
		if(mvdA[4] != null)
			besitzer.dreh.wl = mvd0[4] + (mvdT[4] - mvd0[4]) * aktuell / dauer;
		if(mvdA[5] != null)
			besitzer.dreh.wb = mvd0[5] + (mvdT[5] - mvd0[5]) * aktuell / dauer;
		besitzer.dreh.sichern();
	}
}