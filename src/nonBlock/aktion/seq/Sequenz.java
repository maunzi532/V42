package nonBlock.aktion.seq;

import ansicht.text.*;
import nonBlock.aktion.*;

public class Sequenz
{
	private final LadeSequenz lad;
	private int teilA;
	private int zeitA;
	private final NBD[] akteure;
	private TBox ender;

	public Sequenz(LadeSequenz lad, NBD... akteure)
	{
		this.akteure = akteure;
		this.lad = lad;
		WeltND.nfr = false;
	}

	public boolean tick()
	{
		for(int i = 0; i < lad.aktionen.size(); i++)
		{
			LadeAktion lad1 = lad.aktionen.get(i);
			if(lad1.teil == teilA && lad1.zeit == zeitA)
			{
				TBox a = lad1.erzeugeAktion(lad1.akteur >= 0 ? akteure[lad1.akteur] : null);
				if(a != null)
					ender = a;
			}
		}
		if(zeitA >= lad.zeitE.get(teilA) && (ender == null || ender.clicked))
		{
			teilA++;
			zeitA = -1;
			ender = null;
			if(teilA >= lad.teilE)
			{
				WeltND.nfr = true;
				return true;
			}
		}
		zeitA++;
		return false;
	}
}