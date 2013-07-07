package karma.model;

/**
 * The a single reputation entry created for each reputation action
 * 
 * @author Hakan.Dilek
 */
public interface ReputationEntry {

	void setImpact(Impact impact);

	void setOwner(Reputable subject);

}
