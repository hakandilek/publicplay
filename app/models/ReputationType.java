package models;

import karma.model.ImpactIdentifier;

public enum ReputationType implements ImpactIdentifier {

	CREATE_POST,

	RATE_UP,

	RATE_DOWN,
	
	;

	public String getKey() {
		return name();
	}

}
