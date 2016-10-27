package indexLader;

import java.util.*;

public class ErrorVial
{
	ArrayList<CError> errors;
	ArrayList<Integer> areaEnds;

	public String prep(String build)
	{
		errors = new ArrayList<>();
		build = LC2.decomment(build);
		areaEnds = LC2.areaEnds(build);
		return build;
	}

	public void add(CError error)
	{
		errors.add(error);
	}
}