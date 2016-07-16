package ansicht.text;

import ansicht.*;

import java.util.*;

public class TextWelt
{
	public ArrayList<Overlay> texters = new ArrayList<>();

	public void sendThemAll(String text, String dispName, String codebez, String emotion)
	{
		for(int i = 0; i < texters.size(); i++)
		{
			texters.get(i).sl.placeTBox(new TBox(false, text),
					dispName, codebez, emotion);
		}
	}
}