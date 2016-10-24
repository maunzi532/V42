package achsen;

import java.util.*;

public interface LC1
{
	public static String decommentandcut(String build)
	{
		String[] lines = build.split("\n");
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < lines.length; i++)
		{
			if(lines[i].contains("/"))
			{
				boolean write = true;
				StringBuilder sb1 = new StringBuilder();
				for(int j = 0; j < lines[i].length(); j++)
				{
					if(lines[i].charAt(j) == '/')
						write = !write;
					else if(write)
						sb1.append(lines[i].charAt(j));
				}
				sb.append(sb1);
			}
			else
				sb.append(lines[i]);
		}
		return sb.toString().replace("\t", "").replace(" ", "");
	}

	public static ArrayList<String> klaSplit(String build)
	{
		build = build.replace("\t", "").replace("\n", "").replace(" ", "");
		ArrayList<String> lines = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		int kla = 0;
		for(int i = 0; i < build.length(); i++)
		{
			char i1 = build.charAt(i);
			if(kla > 0)
			{
				switch(i1)
				{
					case '\t':
					case '\n':
					case ' ':
						break;
					case '{':
						kla++;
						sb.append(i1);
						break;
					case '}':
						kla--;
						sb.append(i1);
						break;
					default:
						sb.append(i1);
				}
			}
			else
			{
				switch(i1)
				{
					case '\t':
					case '\n':
					case ' ':
						break;
					case ',':
						lines.add(sb.toString());
						sb = new StringBuilder();
						break;
					case '{':
						kla++;
					default:
						sb.append(i1);
				}
			}
		}
		lines.add(sb.toString());
		return lines;
	}
}