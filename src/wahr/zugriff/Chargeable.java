package wahr.zugriff;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
public @interface Chargeable
{
	int n1 = 0;
	String field = null;
	String indikator = null;
	int an = 0;
}