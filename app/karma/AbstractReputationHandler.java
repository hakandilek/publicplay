package karma;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import karma.model.ImpactIdentifier;
import karma.model.Reputable;
import karma.model.ReputationHolder;
import karma.model.ReputationHolderStore;

import play.Logger;
import play.Logger.ALogger;

public abstract class AbstractReputationHandler<R extends Reputable, RH extends ReputationHolder> {

	private static ALogger log = Logger.of(AbstractReputationHandler.class);

	Map<ImpactIdentifier, ImpactEvaluation<?, R, RH>> evaluations;

	ReputationHolderStore<R, RH> reputationHolderStore;

	public AbstractReputationHandler(
			ReputationHolderStore<R, RH> reputationHolderStore) {
		this.evaluations = new HashMap<ImpactIdentifier, ImpactEvaluation<?, R, RH>>();
		this.reputationHolderStore = reputationHolderStore;
	}

	protected <C> void addImpact(ImpactIdentifier type,
			ImpactEvaluation<C, R, RH> evaluation) {
		evaluations.put(type, evaluation);
	}

	public <C> RH evaluate(C context, ImpactIdentifier impactType) {
		ImpactEvaluation<C, R, RH> evaluation = evaluation(impactType);
		if (log.isDebugEnabled())
			log.debug("impact : " + evaluation);

		if (evaluation != null) {
			return evaluation.evaluate(context);
		}

		return null;
	}

	public <C> void reevaluateForSubject(R subject) {
		// retrieve a valid holder
		RH holder = reputationHolderStore.get(subject);
		if (holder == null) {
			holder = reputationHolderStore.create(subject);
		}

		Set<ImpactIdentifier> impactTypes = evaluations.keySet();
		for (ImpactIdentifier impactType : impactTypes) {
			ImpactEvaluation<C, R, RH> evaluation = evaluation(impactType);
			holder = evaluation.reevaluate(subject, holder);
		}

		reputationHolderStore.update(holder);
	}

	@SuppressWarnings("unchecked")
	private <C> ImpactEvaluation<C, R, RH> evaluation(ImpactIdentifier impactType) {
		return (ImpactEvaluation<C, R, RH>) evaluations.get(impactType);
	}

}
