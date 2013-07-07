package models;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import karma.model.Impact;

import play.db.ebean.Model;
import play.utils.dao.BasicModel;

@Entity
@Table(name="TBL_REPUTATION_VALUE")
@SuppressWarnings("serial")
public class ReputationValue extends Model implements BasicModel<String>, Impact {
	
	@Id
	private String name;
	
	@Basic
	private Integer value;

	public String getKey() {
		return getName();
	}

	public void setKey(String name) {
		this.name = name;
	} 
	
	public Integer getNumberValue() {
		return value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 
}