package karma.model;

/**
 * DAO interface for the Impact model
 * 
 * @author Hakan.Dilek
 */
public interface ImpactStore {

	/**
	 * Retrieves an impact out of the store with the given identifier
	 * 
	 * @param identifier
	 *            the impact identifier
	 * @return retrieved impact if one exists
	 */
	Impact get(ImpactIdentifier identifier);

}
