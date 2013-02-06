package models;

import play.utils.dao.BasicModel;

public interface Owned<K> extends BasicModel<K> {

	User getCreatedBy();

	void setCreatedBy(User createdBy);

	User getUpdatedBy();

	void setUpdatedBy(User updatedBy);

}
