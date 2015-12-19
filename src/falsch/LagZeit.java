package falsch;

public class LagZeit
{
	public static void start()
	{
		UIVerbunden.siehNonBlocks = false;
		for(int i = 0; i < 10; i++)
		{
			Staticf.last = System.currentTimeMillis();
			if(Hauptschleife.eingabe())
				System.exit(0);
			Hauptschleife.logik();
			Hauptschleife.rendern();
			/*new Thread()
			{
				public void run()
				{*/
					Staticf.last2 = System.currentTimeMillis();
					Hauptschleife.panelize();
				/*}
			}.start();*/
		}
		UIVerbunden.siehNonBlocks = true;
	}
}