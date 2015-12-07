package ansicht.text;

import falsch.*;

import java.awt.*;
import java.util.*;

public class SchalterLayer
{
	final Color back = Color.BLACK;
	final Color front = Color.WHITE;

	public final ArrayList<SLF> layer = new ArrayList<>();

	public int mw(double multiplikator)
	{
		return (int)(Staticf.sc.width * multiplikator);
	}

	public int mh(double multiplikator)
	{
		return (int)(Staticf.sc.height * multiplikator);
	}

	public boolean click(int x, int y, boolean r)
	{
		if(x >= mw(1) || y >= mh(1) || x < 0 || y < 0)
			return false;
		for(int i = 0; i < layer.size(); i++)
			if(layer.get(i).tangible && layer.get(i).click(x, y))
			{
				layer.get(i).onClick(r);
				return true;
			}
		return false;
	}

	public void tick()
	{
		for(int i = 0; i < layer.size(); i++)
			layer.get(i).tick();
	}

	public void draw(Graphics2D gd)
	{
		for(int i = 0; i < layer.size(); i++)
			layer.get(i).draw(gd);
	}
}