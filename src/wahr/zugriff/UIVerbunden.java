package wahr.zugriff;

import nonBlock.controllable.*;
import nonBlock.aktion.*;

import java.awt.*;

public class UIVerbunden
{
	//4D-Bewegungs-Checker
	public static ZP4C zp;
	//Zeitgefrorener Spectator Modus
	public static boolean godMode = false;
	//Spectator-Modus-Kamera
	public static Kamera godModeKam;
	//Zurzeit benutzte Kamera, falls nicht im godMode
	public static Controllable kamN;
	//Zurzeit benutzte Kamera
	public static Controllable kamA;
	//Letzte Mausposition
	public static Point maus = new Point();
	//Maus Robot
	public static Robot ro;
	//Mausverschiebung
	public static Point mausv;
	//Fenster w/h
	public static Dimension sc;
	//4D-Blick an/aus
	public static int x4dization = 0;
	//D2 anklickbarkeit
	public static boolean d2tangibility = false;
	//Blocks sehen
	public static boolean siehBlocks = true;
	//NonBlocks sehen
	public static boolean siehNonBlocks = true;
	//X_Ray-Modus an/aus
	public static boolean xrmode = false;
}