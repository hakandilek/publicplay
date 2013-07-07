package karma.model;

import java.util.Collection;

/**
 * The store (DAO) interface for the ReputationEntry model class
 * 
 * @author Hakan.Dilek
 */
public interface ReputationEntryStore<K, E extends ReputationEntry> {

	/**
	 * stores the given reputation entry
	 * 
	 * @param rep
	 *            the reputation entry to store.
	 */
	K create(E rep);

	/**
	 * Finds all reputation entries, created for the given subject and impact
	 * 
	 * @param subject
	 *            the reputable subject
	 * @param impact
	 *            the reputation impact
	 * @return list of reputation entries for the criteria
	 */
	Collection<E> findFor(Reputable subject, Impact impact);

}
