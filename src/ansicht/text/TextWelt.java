package ansicht.text;

import java.util.*;
import nonBlock.aktion.lesen.*;

public class TextWelt
{
	public ArrayList<Tverlay> texters = new ArrayList<>();

	public void sendThemAll(String text, String dispName, String codebez, String emotion)
	{
		for(int i = 0; i < texters.size(); i++)
		{
			texters.get(i).sl.placeTBox(new TBox(false, text),
					dispName, codebez, emotion);
		}
	}
}