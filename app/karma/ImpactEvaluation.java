package karma;

import karma.model.Reputable;
import karma.model.ReputationHolder;

public interface ImpactEvaluation<C, R extends Reputable, RH extends ReputationHolder> {

	RH evaluate(C context);
	
	RH reevaluate(R subject, RH holder);
	
}
