package wahr.zugriff;

import java.lang.annotation.*;

@Target(ElementType.CONSTRUCTOR)
public @interface Chargeable1
{
	String[] indikatoren = new String[]{""};
}