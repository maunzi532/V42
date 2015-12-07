package fnd;

import javax.swing.*;
import java.awt.event.*;

public class TA2
{
	private static boolean[] keys;
	private static boolean[] k2;
	private static int[][] moves;
	public static int[] keyStat;
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

	public static void move()
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
			}
			else
			{
				if(keyStat[i] <= 0)
					keyStat[i] = 0;
				else
					keyStat[i] = -1;
			}
		}
		k2 = new boolean[1024];
	}

	public static void feedMoves(String feed)
	{
		keys = new boolean[1024];
		k2 = new boolean[1024];
		String[] na = feed.split("\n");
		moves = new int[na.length][];
		for(int i = 0; i < na.length; i++)
		{
			String[] nb = na[i].split(" ");
			moves[i] = new int[nb.length];
			for(int j = 0; j < nb.length; j++)
				moves[i][j] = Integer.parseInt(nb[j]);
		}
		keyStat = new int[moves.length];
	}
}