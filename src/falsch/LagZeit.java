package falsch;

public class LagZeit
{
	public static void start()
	{
		Staticf.siehNonBlocks = false;
		for(int i = 0; i < 10; i++)
		{
			Staticf.last = System.currentTimeMillis();
			if(Hauptschleife.eingabe())
				System.exit(0);
			Hauptschleife.logik();
			Hauptschleife.rendern();
		}
		Staticf.siehNonBlocks = true;
	}
}