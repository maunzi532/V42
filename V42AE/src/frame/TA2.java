package frame;

import java.awt.event.*;
import javax.swing.*;

public class TA2
{
	private static final int magicNumber = 700;
	public boolean ermittlung;

	private boolean[] keys;
	private boolean[] k2;

	private int[][] moves;
	public int[] keyStat;
	public boolean[] keyStat2;

	public void away()
	{
		keys = new boolean[1024];
		k2 = new boolean[1024];
	}

	public void addToFrame(JComponent frame)
	{
		keys = new boolean[1024];
		k2 = new boolean[1024];
		if(ermittlung)
		{
			frame.addKeyListener(new KeyAdapter()
			{
				public void keyPressed(KeyEvent e)
				{
					if(e.getKeyCode() < 1024)
					{
						if(!keys[e.getKeyCode()])
							k2[e.getKeyCode()] = true;
						keys[e.getKeyCode()] = true;
					}
				}
				public void keyReleased(KeyEvent e)
				{
					if(e.getKeyCode() < 1024)
					{
						keys[e.getKeyCode()] = false;
						System.out.println(e.getKeyCode());
					}
				}
			});
			frame.addMouseListener(new MouseAdapter()
			{
				public void mousePressed(MouseEvent e)
				{
					if(!keys[e.getButton() + magicNumber])
						k2[e.getButton() + magicNumber] = true;
					keys[e.getButton() + magicNumber] = true;
				}
				public void mouseReleased(MouseEvent e)
				{
					keys[e.getButton() + magicNumber] = false;
					System.out.println(e.getButton() + magicNumber);
				}
			});
		}
		else
		{
			frame.addKeyListener(new KeyAdapter()
			{
				public void keyPressed(KeyEvent e)
				{
					if(e.getKeyCode() < 1024)
					{
						if(!keys[e.getKeyCode()])
							k2[e.getKeyCode()] = true;
						keys[e.getKeyCode()] = true;
					}
				}
				public void keyReleased(KeyEvent e)
				{
					if(e.getKeyCode() < 1024)
						keys[e.getKeyCode()] = false;
				}
			});
			frame.addMouseListener(new MouseAdapter()
			{
				public void mousePressed(MouseEvent e)
				{
					if(!keys[e.getButton() + magicNumber])
						k2[e.getButton() + magicNumber] = true;
					keys[e.getButton() + magicNumber] = true;
				}
				public void mouseReleased(MouseEvent e)
				{
					keys[e.getButton() + magicNumber] = false;
				}
			});
		}
	}

	public void move()
	{
		boolean n;
		for(int i = 0; i < moves.length; i++)
		{
			n = false;
			for(int j : moves[i])
				if(keys[j] || k2[j])
					n = true;
			if(n)
			{
				if(keyStat[i] > 0)
					keyStat[i] = 1;
				else
					keyStat[i] = 2;
				keyStat2[i] = true;
			}
			else
			{
				if(keyStat[i] <= 0)
					keyStat[i] = 0;
				else
					keyStat[i] = -1;
				keyStat2[i] = false;
			}
		}
		k2 = new boolean[1024];
	}

	public void feedMoves(String feed)
	{
		String[] na = feed.split("\n");
		moves = new int[Integer.parseInt(na[0])][];
		for(int i = 1; i < na.length; i++)
			if(!na[i].equals("") && !na[i].startsWith("/"))
			{
				String[] nb = na[i].split("=");
				String[] nc = nb[1].split(",");
				String[] nd = nb[0].split("-");
				int ne = Integer.parseInt(nd[0]);
				moves[ne] = new int[nc.length];
				for(int j = 0; j < nc.length; j++)
				{
					if(nc[j].charAt(0) == 'n')
						moves[ne][j] = Integer.parseInt(nc[j].substring(1));
					else if(nc[j].charAt(0) == 'm')
						moves[ne][j] = Integer.parseInt(nc[j].substring(1)) + magicNumber;
					else if(nc[j].length() == 1)
						moves[ne][j] = nc[j].charAt(0);
					else
						throw new RuntimeException("Falscher Text in TA2");
				}
			}
		for(int i = 0; i < moves.length; i++)
			if(moves[i] == null)
				moves[i] = new int[0];
		keyStat = new int[moves.length];
		keyStat2 = new boolean[moves.length];
	}
}