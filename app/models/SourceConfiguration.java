package models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.utils.dao.TimestampModel;

import com.pickleproject.shopping.ProductConfigurationModel;

@Entity
@Table(name = "TBL_SOURCE_CONFIG")
public class SourceConfiguration extends Model implements TimestampModel<Long>, ProductConfigurationModel<Long> {

	/** serial id */
	private static final long serialVersionUID = 1L;

	@Id
	private Long key;

	@Basic
	@Required
	private String sourceKey;

	@Basic
	@Required
	private String nameSelector;
	@Basic
	private String nameValue;
	@Basic
	private boolean nameRequired;
	@Basic
	@Required
	private String descriptionSelector;
	@Basic
	private String descriptionValue;
	@Basic
	private boolean descriptionRequired;
	@Basic
	@Required
	private String priceSelector;
	@Basic
	private String priceValue;
	@Basic
	private boolean priceRequired;
	@Basic
	@Required
	private String discountPriceSelector;
	@Basic
	private String discountPriceValue;
	@Basic
	private boolean discountPriceRequired;
	@Basic
	@Required
	private String imageLinkSelector;
	@Basic
	private String imageLinkValue;
	@Basic
	private boolean imageLinkRequired;

	@Basic
	private Date createdOn;

	@Basic
	private Date updatedOn;

	@Version
	private int revision;

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getSourceKey() {
		return sourceKey;
	}

	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
	}

	public String getNameSelector() {
		return nameSelector;
	}

	public void setNameSelector(String nameSelector) {
		this.nameSelector = nameSelector;
	}

	public String getNameValue() {
		return nameValue;
	}

	public void setNameValue(String nameValue) {
		this.nameValue = nameValue;
	}

	public boolean isNameRequired() {
		return nameRequired;
	}

	public void setNameRequired(boolean nameRequired) {
		this.nameRequired = nameRequired;
	}

	public String getDescriptionSelector() {
		return descriptionSelector;
	}

	public void setDescriptionSelector(String descriptionSelector) {
		this.descriptionSelector = descriptionSelector;
	}

	public String getDescriptionValue() {
		return descriptionValue;
	}

	public void setDescriptionValue(String descriptionValue) {
		this.descriptionValue = descriptionValue;
	}

	public boolean isDescriptionRequired() {
		return descriptionRequired;
	}

	public void setDescriptionRequired(boolean descriptionRequired) {
		this.descriptionRequired = descriptionRequired;
	}

	public String getPriceSelector() {
		return priceSelector;
	}

	public void setPriceSelector(String priceSelector) {
		this.priceSelector = priceSelector;
	}

	public String getPriceValue() {
		return priceValue;
	}

	public void setPriceValue(String priceValue) {
		this.priceValue = priceValue;
	}

	public boolean isPriceRequired() {
		return priceRequired;
	}

	public void setPriceRequired(boolean priceRequired) {
		this.priceRequired = priceRequired;
	}

	public String getDiscountPriceSelector() {
		return discountPriceSelector;
	}

	public void setDiscountPriceSelector(String discountPriceSelector) {
		this.discountPriceSelector = discountPriceSelector;
	}

	public String getDiscountPriceValue() {
		return discountPriceValue;
	}

	public void setDiscountPriceValue(String discountPriceValue) {
		this.discountPriceValue = discountPriceValue;
	}

	public boolean isDiscountPriceRequired() {
		return discountPriceRequired;
	}

	public void setDiscountPriceRequired(boolean discountPriceRequired) {
		this.discountPriceRequired = discountPriceRequired;
	}

	public String getImageLinkSelector() {
		return imageLinkSelector;
	}

	public void setImageLinkSelector(String imageLinkSelector) {
		this.imageLinkSelector = imageLinkSelector;
	}

	public String getImageLinkValue() {
		return imageLinkValue;
	}

	public void setImageLinkValue(String imageLinkValue) {
		this.imageLinkValue = imageLinkValue;
	}

	public boolean isImageLinkRequired() {
		return imageLinkRequired;
	}

	public void setImageLinkRequired(boolean imageLinkRequired) {
		this.imageLinkRequired = imageLinkRequired;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

}
