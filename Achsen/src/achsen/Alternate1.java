package achsen;

public class Alternate1
{
	ADreh1[] drehungen;

	public Alternate1(String build, int len)
	{
		drehungen = new ADreh1[len];
		int next = 0;
		for(String line : build.split(";"))
		{
			if(line.length() == 0)
				continue;
			if(line.contains("="))
			{
				String[] lr = line.split("=");
				next = Integer.parseInt(lr[0]);
				line = lr[1];
			}
			drehungen[next] = new ADreh1(line);
			next++;
		}
	}
}