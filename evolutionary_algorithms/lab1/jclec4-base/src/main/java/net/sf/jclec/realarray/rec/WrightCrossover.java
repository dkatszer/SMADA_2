package net.sf.jclec.realarray.rec;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Apply a Wright crossover for two individuals
 * 
 * @author Alberto Lamarca 
 * @author Sebastian Ventura 
 *
 */
public class WrightCrossover extends UniformCrossover2x1 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////
	
	/** Generated by eclipse */
	
	private static final long serialVersionUID = 8642560169907081850L;
		
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor
	 */
	
	public WrightCrossover() 
	{
		super();
	}
	
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	// java.lang.Object methods
	
	public boolean equals(Object other)
	{
		if (other instanceof WrightCrossover) {
			WrightCrossover o = (WrightCrossover) other;
			EqualsBuilder eb = new EqualsBuilder();
			eb.append(locusRecProb, o.locusRecProb);
			return eb.isEquals();
		}
		else {
			return false;
		}
	}	
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////
	
	// UniformCrossover2x1 methods
	
	@Override
	protected void recombineLocus(double[] p0_genome, double[] p1_genome, double[] s0_genome, int locusIndex) 
	{
		double newValue =  p0_genome[locusIndex] + 
			randgen.raw() * (p0_genome[locusIndex] - p1_genome[locusIndex]);				
		// Check interval locus				
		s0_genome[locusIndex] = 
			genotypeSchema[locusIndex].nearestOf(newValue);		
	}

	// UniformCrossover methods

	@Override
	protected double defaultLocusRecProb()
	{
		return 0.6;
	}
}
