package indexLader;

import java.util.*;

public interface Decodable
{
	ArrayList<CError> argh(String build);

	static ArrayList<CError> combine(ArrayList... errors)
	{
		ArrayList<CError> toR = new ArrayList<>();
		for(ArrayList e : errors)
			toR.addAll(e);
		return toR;
	}
}