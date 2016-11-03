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

	//WAGUUUH
	public String markText()
	{
		StringBuilder sb = new StringBuilder();
		int ed = 0;
		sb.append(buildX.substring(0, areaEnds.get(0)).replace("\n", "<br>"));
		for(int i = 0; i < areaEnds.size(); i++)
		{
			if(i + 1 < areaEnds.size())
				sb.append(buildX.substring(areaEnds.get(i), areaEnds.get(i + 1)).replace("\n", "<br>"));
			int edA = ed;
			for(CError err : errors)
			{
				if(err.areaStart == i)
					ed++;
				if(err.areaEnd == i)
					ed--;
			}
			if(ed < 0)
				ed = 0;
			if(ed > 0 && edA == 0)
				sb.append("<b>");
			if(ed == 0 && edA > 0)
				sb.append("</b>");
		}
		return sb.toString();
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