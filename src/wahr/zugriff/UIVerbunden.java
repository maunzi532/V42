package wahr.zugriff;

import nonBlock.controllable.*;
import nonBlock.aktion.*;

import java.awt.*;

public class UIVerbunden
{
	//4D-Bewegungs-Checker
	//pro Spieler
	public static ZP4C zp;
	//Zeitgefrorener Spectator Modus
	//pro Spieler
	public static boolean godMode = false;
	//Spectator-Modus-Kamera
	//pro Spieler
	public static GMKamera godModeKam;
	//Zurzeit benutzte Kamera, falls nicht im godMode
	//pro Spieler/Zeichner
	public static Controllable kamN;
	//Zurzeit benutzte Kamera
	//pro Spieler/Zeichner
	public static Controllable kamA;
	//Letzte Mausposition
	public static Point maus = new Point();
	//Maus Robot
	public static Robot ro;
	//Mausverschiebung
	public static Point mausv;
	//Maus Letzter Fokus
	public static Point mausLast;
	//Fenster w/h
	public static Dimension sc;
	//Haare berechnen
	public static boolean calculateH = true;
}