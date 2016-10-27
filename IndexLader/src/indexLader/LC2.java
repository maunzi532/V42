package indexLader;

import java.util.*;

public interface LC2
{
	static String decommentandcut(String build)
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

	static ArrayList<String> klaSplit(String build)
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

	static String decomment(String build)
	{
		StringBuilder sb = new StringBuilder();
		boolean comment = false;
		for(int i = 0; i < build.length(); i++)
		{
			char c = build.charAt(i);
			if(comment)
			{
				if(c == '\n')
					comment = false;
				sb.append(' ');
			}
			else if(c == '/')
			{
				comment = true;
				sb.append(' ');
			}
			else if(c == '\n' || c == '\t')
				sb.append(' ');
			else
				sb.append(c);
		}
		return sb.toString();
	}

	static ArrayList<Integer> areaEnds(String build)
	{
		ArrayList<Integer> ends = new ArrayList<>();
		for(int i = 0; i < build.length(); i++)
			if(build.charAt(i) == ',' || build.charAt(i) == ';' || build.charAt(i) == '=')
				ends.add(i);
		return ends;
	}

	//Evil C++ Techs here
	static ArrayList<String> klaSplit2(String build, boolean v2, int startN, ArrayList<Integer> ends2)
	{
		ArrayList<String> lines = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		int kla = 0;
		int end2 = startN;
		ends2.add(end2);
		for(int i = 0; i < build.length(); i++)
		{
			char i1 = build.charAt(i);
			if(kla > 0)
			{
				switch(i1)
				{
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
					case ',':
					case ';':
					case '=':
						end2++;
					default:
						sb.append(i1);
				}
			}
			else
			{
				switch(i1)
				{
					case ' ':
						break;
					case ',':
					case ';':
						end2++;
						if((i1 == ';') == v2)
						{
							ends2.add(end2);
							lines.add(sb.toString());
							sb = new StringBuilder();
						}
						else
							sb.append(i1);
						break;
					case '{':
						kla++;
						sb.append(i1);
						break;
					case '=':
						end2++;
					default:
						sb.append(i1);
				}
			}
		}
		if(!v2)
			lines.add(sb.toString());
		return lines;
	}

	//Argh
	static Object[] extractKey(String build, int startN, int nachEndN,
			boolean removeK, boolean expectN, boolean expectT, int lastN, ErrorVial errors)
	{
		Object[] toR = new Object[3];
		if(expectN || expectT)
		{
			int idx = build.indexOf('=');
			if(idx >= 0)
			{
				String left = build.substring(0, idx);
				if(expectN)
					try
					{
						toR[0] = Integer.parseUnsignedInt(left);
					}catch(NumberFormatException e)
					{
						if(expectT)
							toR[1] = left;
						else
						{
							toR[0] = 0;
							errors.add(new CError("Nur positive Zahlen im Key", startN, startN + 1));
						}
					}
				else
					toR[1] = left;
				build = build.substring(idx + 1);
			}
			else
			{
				if(!expectN)
				{
					toR[1] = "";
					errors.add(new CError("Key zuweisen bitte", startN, startN + 1));
				}
				else if(lastN >= 0)
					toR[0] = lastN + 1;
				else
				{
					toR[0] = 0;
					errors.add(new CError("Erster Zahlenkey muss existieren", startN, startN + 1));
				}
			}
		}
		if(removeK)
		{
			if(build.charAt(0) == '{' && build.charAt(build.length() - 1) == '}')
				build = build.substring(1, build.length() - 1);
			else
				errors.add(new CError("Nicht alle Klammern vorhanden", startN + 1, nachEndN - 1));
		}
		toR[2] = build;
		return toR;
	}

	static int fillthis(Object[] ret, ArrayList fillthis,
			ArrayList<Integer> ends2, int inends, ErrorVial errors)
	{
		int lnum = (Integer) ret[0];
		if(lnum < fillthis.size())
		{
			errors.add(new CError("Key " + lnum + " zu klein, muss mindestens " + fillthis.size() + " sein",

					ends2.get(inends), ends2.get(inends) + 1));
			lnum = fillthis.size();
		}
		while(lnum > fillthis.size())
			//noinspection unchecked
			fillthis.add(null);
		return lnum;
	}
}