package models;

import com.avaje.ebean.annotation.EnumValue;

public enum ContentStatus {

	@EnumValue("N")
	NEW,

	@EnumValue("U")
	UPDATED,

	@EnumValue("A")
	APPROVED,

	@EnumValue("D")
	DELETED,

	@EnumValue("E")
	EXPIRED,

}