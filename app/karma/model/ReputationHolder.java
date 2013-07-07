package karma.model;

/**
 * Model class to hold reputation for the subject
 * 
 * @author Hakan.Dilek
 */
public interface ReputationHolder {

	/**
	 * increases this reputation holder value with the given value
	 * 
	 * @param value
	 *            increase amount
	 */
	void increase(Number value);

}
