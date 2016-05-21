package wahr.mage;

import ansicht.*;
import block.generierung.*;
import nonBlock.aktion.*;
import nonBlock.aktion.lesen.*;
import nonBlock.aussehen.*;
import nonBlock.aussehen.ext.*;
import nonBlock.collide.*;
import nonBlock.controllable.*;
import nonBlock.formwandler.*;
import wahr.zugriff.*;

public class Hauptschleife
{
	public static AllWelt aw;

	public static void initWelt()
	{
		aw = new AllWelt();
		Generator g = new WeltLeser();
		g.gibInWelt(aw.wbl, "Levels/Test1");
		//g.gibInWelt("Levels/Generiert1");
		//g.gibInWelt();
		//g.gibInWelt(4, 4);
		aw.wbl.setzeSE(new K4(0, 0, 0, 0), new K4(20, 20, 20, 20));
		g.ermittleStart();
		for(int i = 0; i < aw.wbl.starts.length; i++)
		{
			Flag f = new Flag(aw.wbl, aw.lw, aw.dw, aw.bw);
			f.aussehen = new LadeModell();
			StandardAussehen.gibVonIndex2("Flagge/Sta").assignStandard(f);
			f.aussehen.reload(LadeTeil.gibVonIndex("Flagge/Achsen"));
			f.position = new K4(aw.wbl.starts[i]);
			f.dreh = new Drehung(aw.wbl.startdrehs[i]);
			f.collidable.add(new ColBox(f, 1, new EndScheibe(0.3), new EndScheibe(0.3), 1, 1));
			f.init();
		}
		createTheN2();
	}

	public static Tha giveNToOverlay(Overlay reciever)
	{
		LadeFWA lfwa = new LadeFWA(20);
		lfwa.charge(LadeFWATeil.gibVonIndex("SetN"));
		lfwa.charge(LadeFWATeil.gibVonIndex("Set1"));
		Tha n = new Tha(new SPController(reciever), lfwa, aw);
		n.tverlay = reciever;
		n.aussehen = new LadeModell().reload(
				LadeTeil.gibVonIndex("Hauptteil"),
				LadeTeil.gibVonIndex("Beine"),
				LadeTeil.gibVonIndex("Arme"),
				LadeTeil.gibVonIndex("Schuhe"),
				LadeTeil.gibVonIndex("Kopf"),
				LadeTeil.gibVonIndex("Sicht"));
		StandardAussehen.gibVonIndex2("TSSA").assignStandard(n,
				new Enhance(new LadeModell().reload(LadeTeil.gibVonIndex("Hand L2"))),
				new Enhance(new LadeModell().reload(LadeTeil.gibVonIndex("Hand R2"))),
				new H(0.5, 0.5, 10, 10, 4, 1, 0, 0.9),
				new H(0.2, 0.5, 4, 10, 7, 0.7, 0, 0.9),
				new H(0.2, 0.5, 4, 10, 7, 0.7, 0, 0.9),
				new H(0.2, 0.5, 4, 10, 3, 0.7, 0, 0.9),
				new H(0.2, 0.5, 4, 10, 7, 0.7, 0, 0.9)
				/*new H2(n, 0.5, 0.5, 10, 10, 4, 1, 0, 0.6),
				new H2(n, 0.2, 0.5, 4, 10, 7, 0.7, 0, 0.55),
				new H2(n, 0.2, 0.5, 4, 10, 7, 0.7, 0, 0.55),
				new H2(n, 0.2, 0.5, 4, 10, 3, 0.7, 0, 0.55),
				new H2(n, 0.2, 0.5, 4, 10, 7, 0.7, 0, 0.55)*/
		);
		n.position = aw.wbl.starts[0];
		n.position.b += n.block.get(0).airshift;
		n.dreh = aw.wbl.startdrehs[0];
		n.physik.add(new ColBox(n, 69, new EndScheibe(1.5), new EndScheibe(1.0), 1.1));
		n.physik.add(new ColBox(n, 78, new EndScheibe(1.0), new EndScheibe(0.7), 0.7));
		n.physik.add(new ColBox(n, 11, new EndScheibe(0.7), new EndScheibe(0.7), 1));
		n.physik.add(new ColBox(n, 12, new EndScheibe(0.7), new EndScheibe(0.7), 1));
		n.physik.add(new ColBox(n, 0, new EndEllipse(2.1, 1.2, 0), new EndEllipse(2.2, 1.4, 0), 1));
		n.physik.add(new ColBox(n, 1, new EndEllipse(2.2, 1.4, 0), new EndEllipse(2.2, 1.4, 0), 1));
		n.physik.add(new ColBox(n, 2, new EndEllipse(2.2, 1.4, 0), new EndEllipse(2.2, 1.4, 0), 1));
		n.init();
		n.aktionen.add(new Sicht(n, 10, 67, 67, false, reciever));
		aw.lw.licht.add(n);
		reciever.kamN = n;
		reciever.erzeugeGMK(aw, n.position);
		return n;
	}

	private static void createTheN2()
	{
		TSSA n2 = new TSSA(new Controller(), null, "Luft", aw)
		{
			public void collide(Collider attk)
			{
				AktionM.checkLinA(this, new AktionM(this, 20, 1, ADI.deg(16, 2, 14, new RZahl(3), new RZahl(45),
						new RZahl(0), new RZahl(0), new RZahl(0), false)));
			}

			public void actCollide(Collider attk){}

			public void decollide(Collider attk)
			{
				AlternateStandard.gibVonIndex1("TSSA2R").changeToThis(this, 40, 5);
			}

			public void wand(int welche){}

			public K4 kamP()
			{
				return new K4(punkte[73][14]);
			}

			public Drehung kamD()
			{
				return Drehung.plus(dreh, achsen[73].dreh);
			}

			public void doCommand(String command)
			{
				if(command.equals("B"))
				{
					dw.seq = new Move(LadeMove.gibVonIndex(true, "TPSQ"), tverlay, this);
				}
				if(command.equals("C"))
				{
					dw.seq = new Move(LadeMove.gibVonIndex(true, "TSQ"), tverlay, this); //da fehlt ein Ziel
				}
			}

			protected void doFall(String fall, boolean attachChainOnly){}
		};
		//MPS mp2 = new MPS();
		n2.aussehen = new LadeModell().reload(
				LadeTeil.gibVonIndex("Hauptteil"),
				LadeTeil.gibVonIndex("Beine"),
				LadeTeil.gibVonIndex("Arme"),
				LadeTeil.gibVonIndex("Schuhe"),
				LadeTeil.gibVonIndex("Kopf"),
				LadeTeil.gibVonIndex("Sicht"));
		StandardAussehen.gibVonIndex2("TSSA").assignStandard(n2,
				new Enhance(new LadeModell().reload(LadeTeil.gibVonIndex("Hand L2"))),
				new Enhance(new LadeModell().reload(LadeTeil.gibVonIndex("Hand R2"))),
				new H2(n2, 0.5, 0.5, 10, 10, 4, 1, 0, 0.6),
				new H2(n2, 0.2, 0.5, 4, 10, 7, 0.7, 0, 0.55),
				new H2(n2, 0.2, 0.5, 4, 10, 7, 0.7, 0, 0.55),
				new H2(n2, 0.2, 0.5, 4, 10, 3, 0.7, 0, 0.55),
				new H2(n2, 0.2, 0.5, 4, 10, 7, 0.7, 0, 0.55)/*,
				mp2*/
		);
		n2.position = aw.wbl.starts[1];
		n2.position.b += n2.block.get(0).airshift;
		//mp2.init();
		n2.dreh = aw.wbl.startdrehs[1];
		n2.tverlay = new Tverlay(aw.tw);
		n2.init();
		n2.collidable.add(new ColBox(n2, 0, new EndScheibe(4), new EndScheibe(4), 1, 1));
		n2.physik.add(new ColBox(n2, 69, new EndScheibe(1.5), new EndScheibe(1.0), 1.1));
		n2.physik.add(new ColBox(n2, 78, new EndScheibe(1.0), new EndScheibe(0.7), 0.7));
		n2.physik.add(new ColBox(n2, 11, new EndScheibe(0.7), new EndScheibe(0.7), 1));
		n2.physik.add(new ColBox(n2, 12, new EndScheibe(0.7), new EndScheibe(0.7), 1));
		n2.physik.add(new ColBox(n2, 0, new EndEllipse(2.1, 1.2, 0), new EndEllipse(2.2, 1.4, 0), 1));
		aw.lw.licht.add(n2);
	}
}