package ansicht.text;

import wahr.zugriff.*;

import java.awt.*;
import java.util.*;

public class SchalterLayer
{
	final Color back = Color.BLACK;
	final Color front = Color.WHITE;
	final Color schieber = new Color(46, 46, 46);
	final Color schieberKante = Color.GRAY;

	public final ArrayList<SLF> layer = new ArrayList<>();
	private final ArrayList[] tex;
	private final String[] texNamen = new String[0];

	public SchalterLayer()
	{
		tex = new ArrayList[texNamen.length];
		for(int i = 0; i < tex.length; i++)
			tex[i] = new ArrayList<>();
	}

	public int mw(double multiplikator)
	{
		return (int)(UIVerbunden.sc.width * multiplikator);
	}

	public int mh(double multiplikator)
	{
		return (int)(UIVerbunden.sc.height * multiplikator);
	}

	public double nmw(double multiplikator)
	{
		return multiplikator / UIVerbunden.sc.width;
	}

	public double nmh(double multiplikator)
	{
		return multiplikator / UIVerbunden.sc.height;
	}

	public boolean click(int x, int y, boolean r)
	{
		if(x >= mw(1) || y >= mh(1) || x < 0 || y < 0)
			return false;
		for(SLF slf : layer)
			if(slf.tangible && slf.click(nmw(x), nmh(y)))
			{
				slf.onClick(r, (nmw(x) - slf.x) / slf.w, (nmh(y) - slf.y) / slf.h);
				return true;
			}
		return false;
	}

	public void draw(Graphics2D gd)
	{
		for(int i = 0; i < layer.size(); i++)
			layer.get(i).draw(gd);
	}
}