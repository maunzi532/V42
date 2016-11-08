package indexLader;

import java.lang.reflect.*;
import java.util.*;

public abstract class LC2
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

	public static String decomment(String build)
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

	public static ArrayList<Integer> areaEnds(String build)
	{
		ArrayList<Integer> ends = new ArrayList<>();
		ends.add(0);
		for(int i = 0; i < build.length(); i++)
			if(build.charAt(i) == ',' || build.charAt(i) == ';' || build.charAt(i) == '=')
				ends.add(i);
		ends.add(build.length());
		return ends;
	}

	public static ArrayList<String> klaSplit2(String build, boolean v2, int startN, ArrayList<Integer> ends2)
	{
		ArrayList<String> lines = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		int kla = 0;
		int end2 = startN;
		if(ends2 != null)
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
							if(ends2 != null)
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
		{
			end2++;
			if(ends2 != null)
				ends2.add(end2);
			lines.add(sb.toString());
		}
		return lines;
	}

	public static void removeKlammern(Object[] wugu, ErrorVial vial)
	{
		String build = (String) wugu[2];
		if(build == null)
			return;
		if(build.charAt(0) == '{' && build.charAt(build.length() - 1) == '}')
			wugu[2] = build.substring(1, build.length() - 1);
		else
			vial.add(new CError("Nicht alle Klammern vorhanden", (Integer) wugu[3], (Integer) wugu[4]));
	}

	public static Object[] extractKey(String build, int errStart, int errEnd, boolean really,
			boolean expectN, int lastN, boolean expectT, boolean keyOk, ErrorVial vial, Object... extra)
	{
		Object[] toR = new Object[6 + extra.length];
		System.arraycopy(extra, 0, toR, 6, extra.length);
		boolean moveStart = false;
		boolean zwei = true;
		if(really)
		{
			if(expectN || expectT)
			{
				int idx = build.indexOf('=');
				if(idx >= 0)
				{
					String left = build.substring(0, idx);
					moveStart = true;
					if(expectN)
						try
						{
							toR[0] = Integer.parseUnsignedInt(left);
						}
						catch(NumberFormatException e)
						{
							if(expectT)
								toR[1] = left;
							else
							{
								toR[0] = 0;
								vial.add(new CError("Nur positive ints im Key", errStart, errStart + 1));
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
						if(keyOk)
						{
							zwei = false;
							moveStart = true;
							toR[1] = build;
						}
						else
						{
							toR[1] = "";
							vial.add(new CError("Key zuweisen bitte", errStart, errEnd));
						}
					}
					else if(lastN >= 0)
						toR[0] = lastN + 1;
					else
					{
						toR[0] = 0;
						vial.add(new CError("Erster Zahlenkey muss existieren", errStart, errEnd));
					}
				}
			}
		}
		if(zwei)
			toR[2] = build;
		toR[3] = errStart + (moveStart ? 1 : 0);
		toR[4] = errEnd;
		toR[5] = vial;
		return toR;
	}

	public static int fillthis(int lnum, ArrayList fillthis, int errStart, int errEnd, ErrorVial vial)
	{
		if(lnum < fillthis.size())
		{
			vial.add(new CError("Key " + lnum + " zu klein, muss mindestens " + fillthis.size() + " sein",
					errStart, errEnd));
			lnum = fillthis.size();
		}
		while(lnum > fillthis.size())
			//noinspection unchecked
			fillthis.add(null);
		return lnum;
	}

	public enum TFV
	{
		UINT,
		DOUBLE,
		STRING
	}

	public static Object[] verifyTypes(ArrayList<String> checkThis,
			int errStart, int errEnd, ErrorVial vial, TFV... types)
	{
		Object[] results = new Object[types.length];
		if(checkThis.size() != types.length)
			vial.add(new CError("Parameter anzahl: " + checkThis.size() + ", muss " + types.length + " sein",
					errStart, errEnd));
		for(int i = 0; i < types.length; i++)
		{
			if(i < checkThis.size())
				switch(types[i])
				{
					case UINT:
						try
						{
							results[i] = Integer.parseUnsignedInt(checkThis.get(i));
						}
						catch(NumberFormatException e)
						{
							vial.add(new CError("Parameter Nummer " + i + " muss positiver int sein",
									errStart + i, errStart + i + 1));
							results[i] = 0;
						}
						break;
					case DOUBLE:
						try
						{
							results[i] = Double.parseDouble(checkThis.get(i));
						}
						catch(NumberFormatException e)
						{
							vial.add(new CError("Parameter Nummer " + i + " muss double sein",
									errStart + i, errStart + i + 1));
							results[i] = 0d;
						}
						break;
					case STRING:
						results[i] = checkThis.get(i);
						break;
				}
			else
				switch(types[i])
				{
					case UINT:
						results[i] = 0;
						break;
					case DOUBLE:
						results[i] = 0d;
						break;
					case STRING:
						results[i] = "";
						break;
				}
		}
		return results;
	}

	public static boolean requireValue(String value, int errStart, ErrorVial vial)
	{
		if(value != null)
			return true;
		vial.add(new CError("Value fehlt", errStart - 1, errStart));
		return false;
	}

	private static Class[] baseC = new Class[]{Integer.class, String.class,
			String.class, Integer.class, Integer.class, ErrorVial.class};

	public static void superwaguh(String build, int errStart, ErrorVial vial,
			KXS kxs, ArrayList fillthis, LC2 invoker, String superwaguhcall, Object... extra)
	{
		ArrayList<Integer> ends = new ArrayList<>();
		ArrayList<String> buildSpl = klaSplit2(build, kxs.splitv2, errStart, ends);
		int lnum = -1;
		for(int i = 0; i < buildSpl.size(); i++)
		{
			Object[] toInsert = extractKey(buildSpl.get(i), ends.get(i), ends.get(i + 1),
					kxs.extractKey, kxs.expectNumbers, lnum, kxs.expectText, kxs.keyOnlyOk, vial, extra);
			if(kxs.removeKlammern)
				removeKlammern(toInsert, vial);
			if(kxs.expectNumbers && toInsert[0] != null)
				lnum = fillthis((Integer) toInsert[0], fillthis, ends.get(i), ends.get(i) + 1, vial);
			try
			{
				Class[] params = new Class[baseC.length + extra.length];
				System.arraycopy(baseC, 0, params, 0, baseC.length);
				for(int j = 0; j < extra.length; j++)
					params[baseC.length + j] = extra[j].getClass();
				invoker.getClass().getMethod(superwaguhcall, params).invoke(invoker, toInsert);
			}catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
			{
				throw new RuntimeException(e);
			}
		}
	}
}