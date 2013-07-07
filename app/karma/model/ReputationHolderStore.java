package karma.model;

/**
 * Persistent store to contain ReputationHolder entries.
 * 
 * @author Hakan.Dilek
 * 
 * @param <R>
 *            the Reputation type
 * @param <RH>
 *            the Reputation Holder type
 */
public interface ReputationHolderStore<R extends Reputable, RH extends ReputationHolder> {

	/**
	 * Retrieves an existing reputation holder for the given subject
	 * 
	 * @param subject
	 *            the reputation subject
	 * @return associated reputation holder
	 */
	RH get(R subject);

	/**
	 * Creates a new reputation holder and associates with the given subject
	 * 
	 * @param subject
	 *            the reputation subject
	 * @return a new reputation holder
	 */
	RH create(R subject);

	/**
	 * Updates the given reputation holder in the store
	 * 
	 * @param holder
	 *            the holder to update
	 */
	void update(RH holder);

}
