package net.sf.jclec.algorithm.classic;

import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

import net.sf.jclec.IMutator;
import net.sf.jclec.IDistance;
import net.sf.jclec.IConfigure;
import net.sf.jclec.IIndividual;
import net.sf.jclec.IRecombinator;

import net.sf.jclec.util.random.IRandGen;
import net.sf.jclec.util.random.IRandGenFactory;

import net.sf.jclec.selector.BettersSelector;

import net.sf.jclec.algorithm.PopulationAlgorithm;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationRuntimeException;

/**
 * CHC algorithm. This implementation  allows non-binary individuals. To do that,
 * user has to define the distance to use in the incest prevention phase, as well
 * as the recombinator to  use in the generation phase  and the mutator to use in 
 * the restarting phase (optional). 
 * 
 * @author Sebastian Ventura
 */

public class CHC extends PopulationAlgorithm 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////

	/** Generated by Eclipse */
	
	private static final long serialVersionUID = -1226411728565815007L;

	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////

	/** Initial value of d parameter */
	
	protected int initialD;
	
	/** Distance between two individuals */
	
	protected IDistance distance;
	
	/** Recombinator */
	
	protected IRecombinator recombinator;
	
	// Restart parameters
	
	/** Value of d parameter after restart operation (diverge) */
	
	protected int restartD;

	/** Number of individuals that survive */
	
	protected int numberOfSurvivors;
	
	/** Mutator (used in restart phase) */
	
	protected IMutator mutator;

	/////////////////////////////////////////////////////////////////
	// ------------------------------------------- Internal variables
	/////////////////////////////////////////////////////////////////

	/** Random generator (used to shuffle bset) */
	
	private transient IRandGen randgen;
	
	/** Actual value of distance parameter */
	
	private transient int d;
	
	/** Best individuals selector */
	
	private transient BettersSelector bettersSelector = new BettersSelector(this);
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty (default) constructor
	 */
	
	public CHC() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////

	// Setting and getting configuration parameters
	
	/**
	 * Once the randGenFactory has been set, set the random generator
	 * used to shuffle indviduals in selection phase.
	 * 
	 * {@inheritDoc} 
	 */
	
	@Override
	public void setRandGenFactory(IRandGenFactory randGenFactory)
	{
		// Call super method
		super.setRandGenFactory(randGenFactory);
		// Set the random generator
		randgen = randGenFactory.createRandGen();
	}

	/**
	 * Access to initial D
	 * 
	 * @return Initial D
	 */
	
	public int getInitialD() 
	{
		return initialD;
	}

	/**
	 * Set initial D
	 * 
	 * @param initialD Initial D
	 */
	
	public void setInitialD(int initialD) 
	{
		this.initialD = initialD;
	}

	/**
	 * Access to distance between individuals. This distance is used
	 * in the 'incest prevention' phase.
	 * 
	 * @return Distance used at incest prevention phase
	 */
	
	public IDistance getDistance() 
	{
		return distance;
	}

	/**
	 * Sets the distance to use in the 'incest prevention' phase.
	 * 
	 * @param distance New distance
	 */
	
	public void setDistance(IDistance distance) 
	{
		this.distance = distance;
	}

	/**
	 * Access to individuals recombinator
	 * 
	 * @return Individuals recombinator
	 */
	
	public IRecombinator getRecombinator() 
	{
		return recombinator;
	}

	/**
	 * Sets the individuals recombinator.
	 * 
	 * @param recombinator Individual recombinator
	 */
	
	public void setRecombinator(IRecombinator recombinator) 
	{
		// Sets recombinator
		this.recombinator = recombinator;
		// Contextualize parents selector
		recombinator.contextualize(this);
	}
	
	/**
	 * Access to D value for restart
	 * 
	 * @return D value for restart
	 */
	
	public int getRestartD() 
	{
		return restartD;
	}

	/**
	 * Sets the value of D after restarting the algorithm
	 * 
	 * @param restartD value of D after restarting 
	 */
	
	public void setRestartD(int restartD) 
	{
		this.restartD = restartD;
	}

	/**
	 * Number of survivors after restarting
	 * 
	 * @return Number of survivors after restarting
	 */
	
	public int getNumberOfSurvidors()
	{
		return numberOfSurvivors;
	}
	
	/**
	 * Sets the number of individuals that survive after restarting
	 * 
	 * @param numberOfSurvivors Number of survivors
	 */
	
	public void setNumberOfSurvivors(int numberOfSurvivors)
	{
		this.numberOfSurvivors = numberOfSurvivors;
	}

	/**
	 * Access to mutator used in restarting 
	 * 
	 * @return Restating mutator
	 */
	
	public IMutator getMutator() 
	{
		return mutator;
	}

	/**
	 * Sets the mutator used in restarting
	 * 
	 * @param mutator Mutator used in restarting
	 */
	
	public void setMutator(IMutator mutator) 
	{
		// Sets mutator
		this.mutator= mutator;
		// Contextualize mutator
		mutator.contextualize(this);
	}

	// IConfigure interface
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	@SuppressWarnings("unchecked")
	public void configure(Configuration configuration)
	{
		// Call super.configure() method
		super.configure(configuration);
		// Initial d
		int initialD = configuration.getInt("initial-d");
		setInitialD(initialD);
		///////////////////////////////
		// Distance between individuals
		///////////////////////////////
		try {
			// Distance classname
			String distanceClassname = 
				configuration.getString("distance[@type]");
			// Distance class
			Class<? extends IDistance> distanceClass = 
				(Class<? extends IDistance>) Class.forName(distanceClassname);
			// Instance of IDistance object
			IDistance distance = distanceClass.newInstance();
			// Configure distance if necessary
			if (distance instanceof IConfigure) {
				// Extract distance configuration
				Configuration distanceConfiguration = configuration.subset("distance");
				// Configure species
				((IConfigure) distance).configure(distanceConfiguration);
			}
			// Set distance
			setDistance(distance);
		}
		catch (ClassNotFoundException e) {
			throw new ConfigurationRuntimeException("Illegal distance classname");
		} 
		catch (InstantiationException e) {
			throw new ConfigurationRuntimeException("Problems creating an instance of distance", e);
		} 
		catch (IllegalAccessException e) {
			throw new ConfigurationRuntimeException("Problems creating an instance of distance", e);
		}	 
		/////////////////
		// Recombinator 
		/////////////////
		try {
			// Recombinator classname
			String recombinatorClassname = 
				configuration.getString("recombinator[@type]");
			// Recombinator class
			Class<? extends IRecombinator> recombinatorClass = 
				(Class<? extends IRecombinator>) Class.forName(recombinatorClassname);
			// Recombinator instance
			IRecombinator recombinator = recombinatorClass.newInstance();
			// Configure recombinator if necessary
			if (recombinator instanceof IConfigure) {
				// Extract recombinator configuration
				Configuration recombinatorConfiguration = configuration.subset("recombinator");
				// Configure species
				((IConfigure) recombinator).configure(recombinatorConfiguration);
			}
			// Set recombinator
			setRecombinator(recombinator);
		} 
		catch (ClassNotFoundException e) {
			throw new ConfigurationRuntimeException("Illegal recombinator classname");
		} 
		catch (InstantiationException e) {
			throw new ConfigurationRuntimeException("Problems creating an instance of recombinator", e);
		} 
		catch (IllegalAccessException e) {
			throw new ConfigurationRuntimeException("Problems creating an instance of recombinator", e);
		}
		// Restart d
		int restartD = configuration.getInt("restart-d");
		setRestartD(restartD);
		// Number of survivors
		int numberOfSurvivors = configuration.getInt("number-of-survivors", 1);
		setNumberOfSurvivors(numberOfSurvivors);
		//////////////////////////
		// Mutator used in restart
		//////////////////////////
		String mutatorClassname = 
			configuration.getString("mutator[@type]");
		// If mutator classname is empty, mutator is null
		if (mutatorClassname == null) {
			mutator = null;
		}
		// Else, create and configure mutator
		else {
			try{
				// Mutator class
				Class<? extends IMutator> mutatorClass = 
					(Class<? extends IMutator>) Class.forName(mutatorClassname);
				// Mutator instance
				IMutator mutator = mutatorClass.newInstance();
				// Configure mutator if necessary
				if (mutator instanceof IConfigure) {
					// Extract mutator configuration
					Configuration mutatorConfiguration = configuration.subset("mutator");
					// Configure mutator
					((IConfigure) mutator).configure(mutatorConfiguration);
				}
				// Set mutator
				setMutator(mutator);
			}
			catch (ClassNotFoundException e) {
				throw new ConfigurationRuntimeException("Illegal mutator classname");
			} 
			catch (InstantiationException e) {
				throw new ConfigurationRuntimeException("Problems creating an instance of mutator", e);
			} 
			catch (IllegalAccessException e) {
				throw new ConfigurationRuntimeException("Problems creating an instance of mutator", e);
			}
		}
	}
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Call super.doInit(), then initialize d value
	 * 
	 * {@inheritDoc} 
	 */
	
	@Override
	protected void doInit()
	{
		// Call super method
		super.doInit();
		// Initialize d value
		d = initialD;
	}

	/**
	 * Shuffle individuals in bset, then prevents individuals incest 
	 */
	
	@Override
	protected void doSelection() 
	{
		//////////////////////////////
		// Shuffle individuals in bset
		//////////////////////////////
		
		shuffle(bset);
		
		////////////////////
		// Incest prevention
		////////////////////
		
		// Init pset
		pset = new ArrayList<IIndividual> ();	
		// Ensures that number of individuals in bset is even
		int psm1 = populationSize-1;
		// Filter the remaining individuals in bset (couples)
		for (int i=0; i<psm1; i+=2)
		{
			// Couple of individuals
			IIndividual ind1 = bset.get(i);
			IIndividual ind2 = bset.get(i+1);
			// If individuals distance is greater that minimum
			if (distance.distance(ind1, ind2) > 2*d)
			{
				pset.add(ind1);
				pset.add(ind2);
			}
		}
	}

	/**
	 * Apply recombinator over parents
	 */
	
	@Override
	protected void doGeneration() 
	{
		// Create sons
		cset = recombinator.recombine(pset);
		// Evaluate sons
		evaluator.evaluate(cset);
	}

	/**
	 * Do nothing
	 */
	
	@Override
	protected void doReplacement() 
	{
		// Do nothing
	}

	/**
	 * This method includes the following actions:
	 * 
	 * <ul>
	 * <li>Check whether recombination was successful. If so, return new population</li>
	 * <li>If recombination was not successful, update d parameter</li>
	 * <li>If d parameter is negative, performs restart</li>
	 * </ul> 
	 */
	
	@Override
	protected void doUpdate() 
	{
		// Join bset and cset individuals
		bset.addAll(cset);
		// Take best individuals in this join set
		bset = bettersSelector.select(bset, populationSize);
		// If bset(t) and bset(t-1) are different -that is, if bset(t)
		// contains at least one cset(t) element, then return
		for (IIndividual ind : cset) {
			if (bset.contains(ind)) return;
		}
		// Else, decrement d
		d--;
		// If d is less than 0, diverge ...
		if (d<0) {
			// Take individuals that survive restart
			bset = bettersSelector.select(bset, numberOfSurvivors);
			// Number of individuals to create
			int ninds = populationSize - numberOfSurvivors;
			// If mutator is null, create new individuals from scratch
			if (mutator == null)
			{
				bset.addAll(provider. provide(ninds));
			}
			else {
				// Use pset as auxiliary list
				pset.clear();			
				// Take seed individual
				IIndividual seed = bset.get(0);
				// Put seeds in auxiliary array 
				for (int i=0; i<ninds; i++)
					pset.add(seed.copy());
				// Add mutated individuals to bset
				bset.addAll(mutator.mutate(pset));
			}
			// Evaluate new individuals
			evaluator.evaluate(bset);
			// Update d value
			d = restartD;
		}
	}

	//////////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Private methods
	//////////////////////////////////////////////////////////////////////
	
	/**
	 * Based on shuffle implemented in java.util.Collections
	 */
	
    private final void shuffle(List<IIndividual> list) 
    {
    	int size = list.size();
    	IIndividual arr[] = list.toArray(new IIndividual[size]);
    	// Shuffle array
    	for (int i=size; i>1; i--) {
    		swap(arr, i-1, randgen.choose(i));
    	}
    	// Dump array back into list
    	ListIterator<IIndividual> it = list.listIterator();
    	for (int i=0; i<arr.length; i++) {
    		it.next();
    		it.set(arr[i]);
    	}
    }

    /**
     * Swaps the two specified elements in the specified array.
     */

    private static final void swap(IIndividual[] arr, int i, int j) {
        IIndividual tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
