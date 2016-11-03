package indexLader;

import java.util.*;

public class ErrorVial
{
	ArrayList<CError> errors;
	ArrayList<Integer> areaEnds;
	String buildX;

	public ErrorVial()
	{
		errors = new ArrayList<>();
	}

	public String prep(String build)
	{
		buildX = build;
		build = LC2.decomment(build);
		areaEnds = LC2.areaEnds(build);
		return build;
	}

	public void add(CError error)
	{
		errors.add(error);
	}

	public boolean worked()
	{
		return errors.isEmpty();
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for(CError err : errors)
		{
			sb.append(err.message).append('\n');
			int anf = err.areaStart > 5 ? err.areaStart - 5 : 0;
			int end = buildX.length() - err.areaEnd > 5 ? err.areaEnd + 5 : buildX.length();
			sb.append(" Ort: ").append(buildX.substring(anf, end).replace("\n", "\\n")).append('\n');
		}
		return sb.toString();
	}
}