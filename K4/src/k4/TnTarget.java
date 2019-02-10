package k4;

public class TnTarget
{
	public final long target;
	public final long inner;

	public TnTarget(long target, long inner)
	{
		this.target = target;
		this.inner = inner;
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		TnTarget tnTarget = (TnTarget) o;
		return target == tnTarget.target && inner == tnTarget.inner;

	}

	@Override
	public int hashCode()
	{
		int result = (int) (target ^ (target >>> 32));
		result = 31 * result + (int) (inner ^ (inner >>> 32));
		return result;
	}
}