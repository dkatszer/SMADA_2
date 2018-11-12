package net.sf.jclec.exprtree.fun;

import net.sf.jclec.exprtree.fun.AbstractPrimitive;
import net.sf.jclec.exprtree.fun.ExprTreeFunction;

public class Div extends AbstractPrimitive 
{
	/** Generated by Eclipse */
	
	private static final long serialVersionUID = 8989376553955448176L;

	/**
	 * This operator receives two double arrays as arguments and return
	 * a double array as result.
	 */
	
	public Div() 
	{
		super(new Class<?> [] {Double.class, Double.class}, Double.class);
	}

	@Override
	protected void evaluate(ExprTreeFunction context) 
	{
		// Get arguments (in context stack)
		Double arg1 = pop(context);
		Double arg2 = pop(context);
		// Push result in context stack
		push(context, arg2/arg1);
	}

	// java.lang.Object methods
	
	public boolean equals(Object other)
	{
		return other instanceof Div;
	}	
	
	public String toString()
	{
		return "/";
	}	
}