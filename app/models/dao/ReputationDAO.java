package models.dao;

import java.util.Collection;

import javax.inject.Singleton;

import com.avaje.ebean.Expr;

import karma.model.Impact;
import karma.model.Reputable;
import karma.model.ReputationEntryStore;
import models.Reputation;
import play.utils.dao.CachedDAO;
import play.utils.dao.TimestampListener;

@Singleton
public class ReputationDAO extends CachedDAO<Long, Reputation> implements
		ReputationEntryStore<Long, Reputation> {

	public ReputationDAO() {
		super(Long.class, Reputation.class);
		addListener(new TimestampListener<Long, Reputation>());
	}

	public Collection<Reputation> findFor(Reputable subject, Impact impact) {
		// TODO:use cache
		return find
				.where()
				.and(Expr.eq("owner_key", subject.getKey()),
						Expr.eq("reputation_value_key", impact.getKey()))
				.findList();
	}

}
