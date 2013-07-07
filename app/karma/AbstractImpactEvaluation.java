package karma;

import java.util.Collection;

import karma.model.Impact;
import karma.model.ImpactIdentifier;
import karma.model.ImpactStore;
import karma.model.Reputable;
import karma.model.ReputationEntry;
import karma.model.ReputationEntryStore;
import karma.model.ReputationHolder;
import karma.model.ReputationHolderStore;
import play.Logger;
import play.Logger.ALogger;

public abstract class AbstractImpactEvaluation<C, E extends ReputationEntry, R extends Reputable, RH extends ReputationHolder>
		implements ImpactEvaluation<C, R, RH> {

	private static ALogger log = Logger.of(AbstractImpactEvaluation.class);

	ReputationEntryStore<?, E> reputationEntryStore;
	ReputationHolderStore<R, RH> reputationHolderStore;
	ImpactStore impactStore;
	ImpactIdentifier impactId;

	public AbstractImpactEvaluation(
			ReputationEntryStore<?, E> reputationEntryStore,
			ImpactStore impactStore,
			ReputationHolderStore<R, RH> reputationHolderStore,
			ImpactIdentifier impactId) {
		super();
		this.reputationEntryStore = reputationEntryStore;
		this.impactStore = impactStore;
		this.reputationHolderStore = reputationHolderStore;
		this.impactId = impactId;
	}

	@Override
	public RH evaluate(C ctx) {
		try {
			R subject = subject(ctx);
			Impact impact = impact(impactId);
			E entry = createReputationEntry(subject, impact);
			return updateReputationHolder(impact, entry, subject);
		} catch (Exception e) {
			log.error("error evaluating reputation", e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public RH reevaluate(R subject, RH holder) {
		try {
			Impact impact = impact(impactId);
			Collection<E> entries = findReputationEntries(subject, impact);
			holder = updateReputationHolder(impact, entries, holder);
			return holder;
		} catch (Exception e) {
			log.error("error reevaluating reputation", e);
			e.printStackTrace();
			return null;
		}
	}

	protected Collection<E> findReputationEntries(R subject, Impact impact) {
		return reputationEntryStore.findFor(subject, impact);
	}

	protected Impact impact(ImpactIdentifier impactIdentifier) {
		if (log.isDebugEnabled())
			log.debug("impactIdentifier : " + impactIdentifier);

		return impactStore.get(impactIdentifier);
	}

	protected E createReputationEntry(Reputable subject, Impact impact) {
		if (log.isDebugEnabled())
			log.debug("createReputationEntry <-");
		
		E rep = createReputationEntry();
		rep.setOwner(subject);
		rep.setImpact(impact);
		reputationEntryStore.create(rep);
		if (log.isDebugEnabled())
			log.debug("rep : " + rep);

		return rep;
	}

	protected RH updateReputationHolder(Impact impact, E entry, R subject) {
		if (log.isDebugEnabled())
			log.debug("updateReputationHolder <-");
		
		RH rh = getReputationHolder(subject);
		if (log.isDebugEnabled())
			log.debug("rh : " + rh);

		return updateReputationHolder(impact, entry, rh, true);
	}

	protected RH updateReputationHolder(Impact impact, Collection<E> entries,
			RH holder) {
		for (E e : entries) {
			updateReputationHolder(impact, e, holder, false);
		}
		return holder;
	}

	protected RH updateReputationHolder(Impact impact, E entry, RH rh,
			boolean persist) {
		Number v = impact.getNumberValue();
		rh.increase(v);
		if (persist)
			reputationHolderStore.update(rh);
		return rh;
	}

	public RH getReputationHolder(R subject) {
		if (log.isDebugEnabled())
			log.debug("getReputationHolder <-");
		if (log.isDebugEnabled())
			log.debug("subject : " + subject);

		RH rh = reputationHolderStore.get(subject);
		if (log.isDebugEnabled())
			log.debug("rh : " + rh);

		if (rh == null) {
			rh = reputationHolderStore.create(subject);
		}
		return rh;
	}

	/**
	 * Creates a new Reputation Entry
	 * 
	 * @return a newly created Reputation Entry
	 */
	protected abstract E createReputationEntry();

	/**
	 * Extracts the subject from the given context
	 * 
	 * @param ctx
	 *            the evaluation context
	 * @return subject from the context
	 */
	protected abstract R subject(C ctx);

}
