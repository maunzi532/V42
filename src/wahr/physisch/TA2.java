package wahr.physisch;

import javax.swing.*;
import java.awt.event.*;

public class TA2
{
	private static boolean[] keys;
	private static boolean[] k2;
	private static int[][][] moves;
	public static int[][] keyStat;
	private static final int magicNumber = 700;

	public static void addToFrame(JFrame frame, boolean ermittlung)
	{
		if(ermittlung)
		{
			frame.addKeyListener(new KeyListener()
			{
				public void keyTyped(KeyEvent e){}
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
			frame.addMouseListener(new MouseListener()
			{
				public void mouseClicked(MouseEvent e){}
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
				public void mouseEntered(MouseEvent e){}
				public void mouseExited(MouseEvent e){}
			});
		}
		else
		{
			frame.addKeyListener(new KeyListener()
			{
				public void keyTyped(KeyEvent e){}
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
			frame.addMouseListener(new MouseListener()
			{
				public void mouseClicked(MouseEvent e){}
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
				public void mouseEntered(MouseEvent e){}
				public void mouseExited(MouseEvent e){}
			});
		}
	}

	public static void move(int spieler)
	{
		boolean n;
		for(int i = 0; i < moves[spieler].length; i++)
		{
			n = false;
			for(int j : moves[spieler][i])
				if(keys[j] || k2[j])
					n = true;
			if(n)
			{
				if(keyStat[spieler][i] > 0)
					keyStat[spieler][i] = 1;
				else
					keyStat[spieler][i] = 2;
			}
			else
			{
				if(keyStat[spieler][i] <= 0)
					keyStat[spieler][i] = 0;
				else
					keyStat[spieler][i] = -1;
			}
		}
		k2 = new boolean[1024];
	}

	public static void setzeAnz(int spielerAnz)
	{
		moves = new int[spielerAnz][][];
		keyStat = new int[spielerAnz][];
	}

	public static void feedMoves(String feed, int spieler)
	{
		keys = new boolean[1024];
		k2 = new boolean[1024];
		String[] na = feed.split("\n");
		moves[spieler] = new int[Integer.parseInt(na[0])][];
		for(int i = 1; i < na.length; i++)
			if(!na[i].equals("") && !na[i].startsWith("/"))
			{
				String[] nb = na[i].split("=");
				String[] nc = nb[1].split(",");
				String[] nd = nb[0].split("-");
				int ne = Integer.parseInt(nd[0]);
				moves[spieler][ne] = new int[nc.length];
				for(int j = 0; j < nc.length; j++)
				{
					if(nc[j].charAt(0) == 'n')
						moves[spieler][ne][j] = Integer.parseInt(nc[j].substring(1));
					else if(nc[j].charAt(0) == 'm')
						moves[spieler][ne][j] = Integer.parseInt(nc[j].substring(1)) + magicNumber;
					else if(nc[j].length() == 1)
						moves[spieler][ne][j] = nc[j].charAt(0);
					else
						throw new RuntimeException("Falscher Text in TA2");
				}
			}
		for(int i = 0; i < moves[spieler].length; i++)
			if(moves[spieler][i] == null)
				moves[spieler][i] = new int[0];
		keyStat[spieler] = new int[moves[spieler].length];
	}
}