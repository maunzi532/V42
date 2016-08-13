package ansicht.text;

import java.awt.*;
import java.util.*;

public class SchalterLayer
{
	final Color back = Color.BLACK;
	final Color front = Color.WHITE;
	final Color schieber = new Color(46, 46, 46);
	final Color schieberKante = Color.GRAY;

	private ArrayList<SLF> layer = new ArrayList<>();

	private final ArrayList[] tex;
	private final ArrayList<Integer> lastTex;

	private final TBoxOrt[] orte = new TBoxOrt[]
			{
					new TBoxOrt(0.15, 0.9, false, true),
					new TBoxOrt(0.85, 0.9, true, true)
			};
	private final T2Box[] texters;

	public final double abstand = 0.01;
	public final int fadePerTick = 10;
	public final double movePerTick = 0.01;

	private int wI;
	private int hI;

	public SchalterLayer()
	{
		tex = new ArrayList[orte.length];
		for(int i = 0; i < orte.length; i++)
			tex[i] = new ArrayList<>();
		lastTex = new ArrayList<>();
		for(int i = 0; i < orte.length; i++)
			lastTex.add(i);
		texters = new T2Box[orte.length];
	}

	public void resize(int wI, int hI)
	{
		this.wI = wI;
		this.hI = hI;
	}

	public int mw(double multiplikator)
	{
		return (int)(wI * multiplikator);
	}

	public int mh(double multiplikator)
	{
		return (int)(hI * multiplikator);
	}

	private double nmw(double multiplikator)
	{
		return multiplikator / wI;
	}

	private double nmh(double multiplikator)
	{
		return multiplikator / hI;
	}

	public boolean click(int x, int y, boolean r)
	{
		if(x >= mw(1) || y >= mh(1) || x < 0 || y < 0)
			return false;
		for(SLF slf : layer)
			if(slf.tangible && slf.click(nmw(x), nmh(y)))
			{
				slf.onClick(this, r, (nmw(x) - slf.x) / slf.w, (nmh(y) - slf.y) / slf.h);
				return true;
			}
		return false;
	}

	public void draw(Graphics2D gd)
	{
		for(int i = 0; i < layer.size(); i++)
			layer.get(i).draw(gd, this);
	}

	public void replaceSchalter(ArrayList<SLF> schalter)
	{
		layer.removeIf(slf -> slf.schalter);
		layer.addAll(schalter);
	}

	public void actTex()
	{
		for(int i = 0; i < orte.length; i++)
		{
			for(int j = 0; j < tex[i].size(); j++)
				((TBox)tex[i].get(j)).tick(this);
			if(texters[i] != null)
				texters[i].tick(this);
		}
	}

	public void removeText()
	{
		for(int i = 0; i < tex.length; i++)
			for(int j = 0; j < tex[i].size(); j++)
				((TBox)tex[i].get(j)).fade -= 1;
	}

	public void removeText(TBoxOrt hier)
	{
		for(int i = 0; i < orte.length; i++)
			if(hier == orte[i])
			{
				for(int j = 0; j < tex[i].size(); j++)
					((TBox)tex[i].get(j)).fade -= 1;
				break;
			}
	}

	public void placeTBox(TBox t, String dispName, String codebez, String emotion)
	{
		for(int i = 0; i < texters.length; i++)
			if(texters[i] != null && texters[i].codebez.equals(codebez))
			{
				placeTBox(t, i);
				texters[i].recharge(dispName, emotion);
				return;
			}
		for(int i = 0; i < texters.length; i++)
			if(texters[i] == null)
			{
				texters[i] = new T2Box(abstand, true, 0.1, 0.3, orte[i], dispName, codebez, emotion);
				layer.add(texters[i]);
				placeTBox(t, i);
				return;
			}
		int i = lastTex.get(0);
		ArrayList<TBox> tb = tex[i];
		for(int j = 0; j < tb.size(); j++)
			removeTBox(tb.get(j));
		texters[i] = new T2Box(abstand, true, 0.1, 0.3, orte[i], dispName, codebez, emotion);
		layer.add(texters[i]);
		placeTBox(t, i);
	}

	private void placeTBox(TBox t, int ort)
	{
		lastTex.remove((Integer)ort);
		lastTex.add(ort);
		TBoxOrt to = orte[ort];
		double tx = to.x;
		if(to.links)
			tx -= t.w;
		t.x = tx;
		double ty = to.y;
		if(to.oben)
		{
			for(int i = 0; i < tex[ort].size(); i++)
				ty -= ((TBox)tex[ort].get(i)).h + abstand;
			ty -= t.h;
		}
		else
			for(int i = 0; i < tex[ort].size(); i++)
				ty += ((TBox)tex[ort].get(i)).h + abstand;
		t.y = ty;
		t.ort = ort;
		tex[ort].add(t);
		layer.add(t);
	}

	public void removeTBox(TBox t)
	{
		layer.remove(t);
		tex[t.ort].remove(t);
		if(tex[t.ort].size() == 0)
		{
			if(layer.contains(texters[t.ort]))
				layer.remove(texters[t.ort]);
			texters[t.ort] = null;
		}
	}

	public double giveNewH(TBox t)
	{
		TBoxOrt to = orte[t.ort];
		double ty = to.y;
		ArrayList<TBox> tb = tex[t.ort];
		if(to.oben)
		{
			for(int i = 0; i < tb.size() && tb.get(i) != t; i++)
				ty -= tb.get(i).h + abstand;
			ty -= t.h;
		}
		else
			for(int i = 0; i < tb.size() && tb.get(i) != t; i++)
				ty += tb.get(i).h + abstand;
		return ty;
	}

	public int giveMaxFade(TBoxOrt hier)
	{
		int fade = 0;
		for(int i = 0; i < orte.length; i++)
			if(hier == orte[i])
			{
				for(int j = 0; j < tex[i].size(); j++)
					if(((TBox)tex[i].get(j)).fade > fade)
						fade = ((TBox)tex[i].get(j)).fade;
				break;
			}
		return fade;
	}
}