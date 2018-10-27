package net.sf.jclec.algorithm.multiobjective;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import net.sf.jclec.IConfigure;
import net.sf.jclec.IEvaluator;
import net.sf.jclec.IFitness;
import net.sf.jclec.IIndividual;
import net.sf.jclec.IMutator;
import net.sf.jclec.IRecombinator;
import net.sf.jclec.ISelector;
import net.sf.jclec.algorithm.PopulationAlgorithm;
import net.sf.jclec.base.FilteredMutator;
import net.sf.jclec.base.FilteredRecombinator;
import net.sf.jclec.fitness.CompositeFitness;
import net.sf.jclec.fitness.IValueFitness;
import net.sf.jclec.fitness.ParetoComparator;
import net.sf.jclec.fitness.ValueFitnessComparator;
import net.sf.jclec.selector.WorsesSelector;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationRuntimeException;

/**
 * "SPEA-II" algorithm.
 * 
 *  	The Strength Pareto Evolutionary Algorithm (SPEA2) is a technique 
 *  for finding or approximating the Pareto-optimal set for multiobjective 
 *  optimization problems. It is an elitism algorithm and it incorporates 
 *  in contrast to its predecessor (SPEA) a fine-grained fitness assignment 
 *  strategy, a density estimation technique, and an enhanced archive 
 *  truncation method. 
 *   
 * @author Amelia Zafra
 * @author Revised by: Jose Mari?oea Luna-Ariza
 */

public class SPEA2 extends PopulationAlgorithm
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////

	/** Generated by Eclipse */
	
	private static final long serialVersionUID = -7514647303485840499L;
	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////

	/** External population */
	
	List<IIndividual> eset = new ArrayList<IIndividual>();
	
	/** Worses Selector */
	
	WorsesSelector worstSelect = new WorsesSelector(this);
	
	/** Pareto comparator */
	
	ParetoComparator paretoComparator = new ParetoComparator();
	
	/** Individuals mutator */
	
	protected FilteredMutator mutator;

	/** Individuals recombinator */

	protected FilteredRecombinator recombinator;
	
	/** Parents selector */

	protected ISelector parentsSelector;
	
	/** Size of the external population */
	
	protected int externalSize;
	
	/** The nearest k-th neighbour which we use in the comparison */	
	
	protected int kValue;
	
	/** Maximize fitness */

	private boolean maximize;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty constructor.
	 */
	
	public SPEA2() 
	{
		super();
	}
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------- Setting and getting properties
	/////////////////////////////////////////////////////////////////

	/**
	 * Access to parents selector
	 * 
	 * @return Actual parents selector
	 */
	
	public ISelector getParentsSelector() 
	{
		return parentsSelector;
	}

	/**
	 * Sets the parents selector.
	 * 
	 * @param parentsSelector parents selector
	 */
	public void setParentsSelector(ISelector parentsSelector) 
	{
		// Set parents selector
		this.parentsSelector = parentsSelector;
		// Contextualize selector
		parentsSelector.contextualize(this);
	}
	
	/**
	 * Access to size of the external population
	 * 
	 * @return The size of the external population
	 */
	
	public int getExternalPopulationSize() 
	{
		return externalSize;
	}
	
	/**
	 * Set the size of the population
	 *  
	 * @param externalSize external population's size
	 */
	
	public void setExternalPopulationSize( int  externalSize) 
	{
		this.externalSize = externalSize;
	}
			
	/**
	 * Access the k value, which represents the k-th neighbour
	 * in proximity which is going to consider in the comparison.
	 *
	 * @return k value.
	 */
	
	public int getKValue()
	{
		return kValue;
	}
	
	/**
	 * Get the external population
	 */
	public List<IIndividual> geExternalPopulation()
	{
		return this.eset;
	}
	
	/**
	 * Set the k value, which represents the k-th neighbour
	 * in proximity which is going to consider in the comparison.
	 *
	 * @param kValue the k value new.
	 */
	
	public void setKValue(int kValue)
	{
		this.kValue = kValue;
	}
	
	/**
	 * Access to parents recombinator
	 * 
	 * @return Actual parents recombinator
	 */
	
	public FilteredRecombinator getRecombinator() 
	{
		return recombinator;
	}

	/**
	 * Sets the parents recombinator.
	 * 
	 * @param recombinator New parents recombinator
	 */
	
	public void setRecombinator(IRecombinator recombinator) 
	{
		if(this.recombinator == null)
			this.recombinator = new FilteredRecombinator(this);
		
		this.recombinator.setDecorated(recombinator);
	}
	
	/**
	 * Access to individuals mutator.
	 * 
	 * @return Individuals mutator
	 */
	
	public FilteredMutator getMutator() 
	{
		return mutator;
	}
	
	/**
	 * Set individuals mutator.
	 * 
	 * @param mutator Individuals mutator
	 */
	
	public void setMutator(IMutator mutator) 
	{
		if(this.mutator == null)
			this.mutator = new FilteredMutator(this);
		
		this.mutator.setDecorated(mutator);
	}

	/**
	 * Set individuals evaluator.
	 *  
	 * @param evaluator Individuals evaluator
	 */
	
	@SuppressWarnings({"unchecked" })
	public void setEvaluator(IEvaluator evaluator)
	{
		this.evaluator = evaluator;
		
		Comparator<IFitness> componentComparators [] = new Comparator[2];
		componentComparators[0] = new ValueFitnessComparator(!maximize);
		componentComparators[1] = new ValueFitnessComparator(!maximize);
		
		// Set comparators of the mutiobjective
		paretoComparator.setComponentComparators(componentComparators);
	}
	
	/**
	 * Set the recombinator probability
	 */
	
	public void setRecombinationProb(double recProb) 
	{
		((FilteredRecombinator) this.recombinator).setRecProb(recProb);
	}	
	
	/**
	 * Set the mutator probability
	 */
	
	public void setMutationProb(double mutProb) 
	{
		((FilteredMutator) this.mutator).setMutProb(mutProb);
	}
	
	/**
	 * @return the maximize
	 */
	public boolean isMaximize() {
		return maximize;
	}

	/**
	 * @param maximize the maximize to set
	 */
	public void setMaximize(boolean maximize) {
		this.maximize = maximize;
	}

	/////////////////////////////////////////////////////////////////
	// ---------------------------- Implementing IConfigure interface
	/////////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	public void configure(Configuration settings)
	{
		super.configure(settings);
		
		// Parents selector
		try {
			// Selector classname
			String parentsSelectorClassname = 
				settings.getString("parents-selector[@type]");
			// Species class
			Class<? extends ISelector> parentsSelectorClass = 
				(Class<? extends ISelector>) Class.forName(parentsSelectorClassname);
			// Species instance
			ISelector parentsSelector = parentsSelectorClass.newInstance();
			
			// Configure species if necessary
			if (parentsSelector instanceof IConfigure) {
				// Extract species configuration
				Configuration parentsSelectorConfiguration = settings.subset("parents-selector");
				// Configure species
				((IConfigure) parentsSelector).configure(parentsSelectorConfiguration);
			}
			
			// Set species
			setParentsSelector(parentsSelector);
		} 
		catch (ClassNotFoundException e) {
			throw new ConfigurationRuntimeException("Illegal parents selector classname");
		} 
		catch (InstantiationException e) {
			throw new ConfigurationRuntimeException("Problems creating an instance of parents selector", e);
		} 
		catch (IllegalAccessException e) {
			throw new ConfigurationRuntimeException("Problems creating an instance of parents selector", e);
		}
		
		// Extract External Population Size
		int externalPopulationSize = settings.getInt("external-population-size");
		setExternalPopulationSize(externalPopulationSize);
		// Extract K Value 
		int kValue = settings.getInt("k-value");
		setKValue(kValue);
	}
	
	/////////////////////////////////////////////////////////////////
	// ---------------------------- Overwriting BaseAlgorithm methods
	/////////////////////////////////////////////////////////////////
	
	/**
	  * This method performs the next steps
	  * 
	  * Step 1: Unite in a one population, the current population
	  * and the external population.
	  * 
	  * Step 2: Calculate the fitness values of individuals in 
	  * current population (Pt) and external population (At).
	  * (function: fitnessAssignment) 
	  * 
	  * Step 3: At+1 = nondominated individuals in Pt and At.
	  * If size of At+1 > N then reduce At+1, else if size 
	  * of At+1 < N then fill At+1 with dominated individuals 
	  * in Pt and At. (function: environmentalSelection)
	*/
	protected void doSelection() 
	{
		//Unite the populations
		List<IIndividual> aset = new ArrayList<IIndividual> ();
		aset.addAll(bset);
		
		for(IIndividual ind : eset)
			if(!aset.contains(ind))
				aset.add((IIndividual) ind);
		
		//Calculate fitness value of all population individuals
		fitnessAssignment(aset);
		//Assign the individuals that are goint to belong to the external population
		
		eset = environmentalSelection(aset);

		//Unite the populations
		aset = new ArrayList<IIndividual> ();
		aset.addAll(bset);
		
		for(IIndividual ind : eset)
			if(!aset.contains(ind))
				aset.add((IIndividual) ind);

		// Obtain the individuals from which the offspring will be created
		pset = parentsSelector.select(aset, bset.size());
	}

	protected void doGeneration() {
		//Do nothing
	}

	@Override
	protected void doReplacement() {
		// Do nothing
	}
	
	@Override
	protected void doUpdate() {
		bset = cset;
		
		// Clear pset & rset
		pset = cset = null;
	}
	
	/**
	 * 
	 * 	Calculate the fitness value of the individuals.
	 * The fitness of each individual takes into account the number 
	 * of individuals which it dominates and also the number of individuals 
	 * which are dominated by it; in adittion, a nearest neighbor density 
	 * estimation is added to fitness. This is a more accurate fitness 
	 * function in terms of comparation with the other members of the 
	 * population and archive population.
	 *
	 *
	 * @param pob, The population to calculate the fitness 
	 */
	protected void fitnessAssignment (List<IIndividual> pop)
	{
		long [] S = new long [pop.size()];
		long [] R = new long [pop.size()];
		double [] D = new double [pop.size()];
		double [][] distance = new double [pop.size()][pop.size()]; 
		
		//The vectors are initialized
		for(int i=0; i<pop.size(); i++)
		{
			S[i] = 0;
			R[i] = 0;
			D[i] = 0.0;
		}
		
		/**
		 * One of the components of fitness value that we
		 * needed is the Raw Fitness, R
		 */
		
		//The first step is to calculate the number of individuals
		//to which it dominates each individual. This is the
		//strength value of each individual
		for(int i=0; i<pop.size(); i++)
			for(int j=0; j<pop.size(); j++)
				if(i!=j && paretoComparator.compare( (CompositeFitness) pop.get(i).getFitness(), (CompositeFitness)pop.get(j).getFitness()) == 1)
					S[i] ++;
		
		//Now, calculates the raw fitness which is the
		//sum of strength values of the individuals that 
		//dominate it
		for(int i=0; i<pop.size(); i++)
			for(int j=0; j<pop.size(); j++)
				if(i!=j && paretoComparator.compare((CompositeFitness)pop.get(j).getFitness(), (CompositeFitness)pop.get(i).getFitness()) == 1)
					R[i] += S[j];

		/**
		 * The other component of fitness that we needed
		 * is the density of information, D
		 */
		
		//Calculate the distance between the individuals
		for(int i=0; i<pop.size(); i++)
		{
			for(int j=i+1; j<pop.size(); j++)
			{
				distance[i][j] = calculateDistance(pop.get(i), pop.get(j));
				distance[j][i] = distance[i][j];
			}
			
			distance[i][i] = 0;
		}
		
		//Sort the individuals by rows
		for(int i= 0; i<pop.size(); i++)
			Arrays.sort(distance[i]);
				
		//The k_th neighbour closest is selected
		for(int i=0; i<pop.size(); i++)
			D[i] = (1 / ( distance[i][kValue] + 2.0 ));
		
		//Finally, the fitness value is assigned
		for(int i=0; i<pop.size(); i++)
			((IValueFitness) (pop.get(i)).getFitness()).setValue(D[i] + R[i]);
		
		S = R = null;
		D = null;

	}

	/**
	 * Calculate the euclidean distance of their objectives
	 * which exists between two individuals.
	 * 
	 * @param ind1 individuo al que se quiere calcular su distancia
	 * con respecto al ind2
	 * 
	 * @param ind2 individuo al que se quiere calcular su distancia
	 * con respecto al ind1
	 * 
	 * @return The euclidean distance between the individuals 
	 * which are sent by parameter. 
	 */
	
	public double calculateDistance(IIndividual ind1, IIndividual ind2)
	{
		double distance = 0.0;
		IFitness [] f0 = ((CompositeFitness) ind1.getFitness()).getComponents();
		IFitness [] f1 = ((CompositeFitness) ind2.getFitness()).getComponents();
		int nc = f0.length;
		
		//calculate the distance of each one of the fitness components
		for(int i=0; i<nc; i++)
			distance += Math.pow(((IValueFitness) f0[i]).getValue() - ((IValueFitness) f1[i]).getValue(),2.0);
			
		return (Math.sqrt(distance));
	}
	
	/**
	 * Update of the external population.
	 * During environmental selection, the first step is to 
	 * copy all nondominated individuals, i.e., those which 
	 * have a fitness lower than one, from archive and 
	 * population to the archive of the next generation: 
	 * 		 At+1 = {i | i c Pt + At & F(i) < 1}
	 * 
	 * If the nondominated front fits exactly into the archive (|At+1| = N) 
	 * then the environmental selection step is completed. 
	 * Otherwise, there can be two situations: 
	 *      -Either the archive is too small ( |At+1| < N) then 
	 *               we will have to add new individuals 
	 *      - or too large (|At+1| >  N) then 
	 *               we will have to eliminate individuals
	 * 
	 */
	
	public List<IIndividual> environmentalSelection(List<IIndividual> aset)
	{
		List<IIndividual> eset = new ArrayList<IIndividual>();
		
		//The individual nondominated by anybody are added
		//in the external population
		for(IIndividual ind : aset)
			if(!eset.contains(ind))
				if(((IValueFitness) ind.getFitness()).getValue() < 1)
					eset.add(ind);
		
		/**
		 * When the individuals nondominated are added in 
		 * the external population, it can happen two cases:
		 *  	1) The external population has a smaller size
		 * 		2) The external population has a grater size 
		 */
		
		//In this case, the external population has less individuals
		//of those than should to have
		if (externalSize > eset.size())
			eset = incrementPopulation(aset, eset, eset.size());
				
		//In this case, the external population has more individuals
		//of it should to have, therefore we have to eliminate individuals
		else if(externalSize < eset.size())
			eset = decrementPopulation(eset, eset.size());

		return eset;
	}

	/**
	 * This method is invoked when the archive is too large 
	 * (|At+1| > N), i.e, when the size of the current nondominated 
	 * (multi)set exceeds N, an archive truncation procedure 
	 * is invoked which iteratively removes individuals from 
	 * At+1 until |At+1| = N. This truncation operator is introduced to keep diversity 
	 * in the population; it avoids the posible loss of outer 
	 * solutions, preserving during the whole algorithm 
	 * execution, the range of Pareto solutions achieved.
	 * 
	 * @param presentExternalCensus means which is the size that
	 * has the external population in this moment.
	 * 
	 */
	
	protected List<IIndividual> decrementPopulation(List<IIndividual> eset, int presentExternalCensus)
	{
		double [] distance = new double[presentExternalCensus];
		double [] k_distance = new double[presentExternalCensus];
		double auxDistance;
		int [] k_nearest = new int[presentExternalCensus];
		int [] sortedIndividuals = new int[presentExternalCensus];
		int auxSortedIndividual;
		
		for(int i=0; i<presentExternalCensus; i++)
		{
			k_distance[i] = Double.MAX_VALUE;
			k_nearest[i] = i;
		}
		
		while(presentExternalCensus > externalSize)
		{
			for(int i=0; i<presentExternalCensus; i++)
			{
				for(int j=0; j<presentExternalCensus; j++)
				{	
					distance[j] = calculateDistance(eset.get(i), eset.get(j));
					sortedIndividuals[j] = j;
				}

				//Sort the individuals with respect to its distance
				//to individual 'i'
				for(int j=0; j<presentExternalCensus-1; j++)
					for(int l=j+1; l<presentExternalCensus; l++)
						if(distance[j] > distance[l])
						{
						
							auxDistance = distance[j];
							distance[j] = distance[l];
							distance[l] = auxDistance;
							
							auxSortedIndividual = sortedIndividuals[j];
							sortedIndividuals[j] = sortedIndividuals[l];
							sortedIndividuals[l] = auxSortedIndividual;
						}
			
				k_nearest[i] = sortedIndividuals[kValue];
				k_distance[i] = distance[kValue];
			}

			//Make the ordering having present the k_distance
			//with k_nearest neighbour calculated in the previous step
			for(int j=0; j<presentExternalCensus; j++)
				sortedIndividuals[j] = j;
				
			for(int j=0; j<presentExternalCensus-1; j++)
				for(int l=j+1; l<presentExternalCensus; l++)
					if(k_distance[j] > k_distance[l])
					{
						auxDistance = k_distance[j];
						k_distance[j] = k_distance[l];
						k_distance[l] = auxDistance;
						
						auxSortedIndividual = sortedIndividuals[j];
						sortedIndividuals[j] = sortedIndividuals[l];
						sortedIndividuals[l] = auxSortedIndividual;
					}
			//Se selecciona el mas cercano con respecto al k-eth mi?oes cercano
			eset.remove(k_nearest[sortedIndividuals[0]]);
			presentExternalCensus --;
		}
	
		//the external population already has the wished size
		return eset;
	}
	
	/**
	 * 
	 * This method is invoked when the archive is too
	 * small (|At+1| < N) the best N - |At+1| dominated 
	 * individuals in the previous archive and population 
	 * are copied to the new archive. This can be implemented 
	 * by sorting the multiset Pt+At according to the fitness 
	 * values and copy the first N - |At+1| individuals i with 
	 * F(i) > 1 from the resulting ordered list to At+1. 
	 * 
	 * @param aset the population union between bset and eset
	 * 
	 * @param currentExternalCensus The size of population which is sent as parameter
	 */
	protected List<IIndividual> incrementPopulation(List<IIndividual> aset, List<IIndividual> eset, int currentExternalCensus)
	{
		// The individuals are sorted of decreasing form
		List<IIndividual> sortedASet = sortPopulation(aset);
		boolean exit = false;
	
		for(int i=0; i<aset.size()&& exit == false; i++)
			if( ((IValueFitness) (sortedASet.get(i)).getFitness()).getValue() >= 1)
			{
				eset.add(sortedASet.get(i));	
				currentExternalCensus ++;
				
				if(currentExternalCensus == externalSize)
					exit = true;
			}

		return eset;
	}

	/**
	 * 
	 * This method sorts a population of individuals in function
	 * of fitness value of increasing form, that is to say,
	 * the individual with fitness value lower are first.
	 * 
	 * @param source contains the list of individuals to sort
	 * by theirs fitness value.
	 * 
	 * @return The population sorted by fitness
	 */
	protected List<IIndividual> sortPopulation(List<IIndividual> source)
	{
		// Selection result
		List<IIndividual> result = new ArrayList<IIndividual>();
		
		// We ordered of decreasing form the present set
		result = worstSelect.select(source, source.size());
		
		// Return the result
		return result;
	}
}