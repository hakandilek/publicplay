package models;

import com.avaje.ebean.annotation.EnumValue;

public enum ContentStatus {

	@EnumValue("N")
	NEW,

	@EnumValue("U")
	UPDATED,

	@EnumValue("A")
	APPROVED,

	@EnumValue("R")
	REMOVED,

	@EnumValue("E")
	EXPIRED,

}