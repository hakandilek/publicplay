package forms;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import com.pickleproject.Selectors;

import play.data.validation.Constraints.Required;
import models.SourceConfiguration;

public class BulkConfiguration {

	private Long key;
	
	private int revision;
	
	@Required
	private String sourceKey;

	@Required
	private String bulkData;

	public BulkConfiguration() {
		super();
	}

	public BulkConfiguration(SourceConfiguration model) {
		key = model.getKey();
		revision = model.getRevision();
		sourceKey = model.getSourceKey();
		bulkData = propertyString(model);
	}

	public SourceConfiguration toModel() {
		SourceConfiguration sc = new SourceConfiguration();
		sc.setKey(key);
		sc.setSourceKey(sourceKey);
		sc.setRevision(revision);
		try {
			Properties p = new Properties();
			p.load(new StringReader(bulkData));
			Selectors sel = new Selectors(p);
			sc.setNameSelector(sel.elementSelector("Name"));
			sc.setNameValue(sel.valueSelector("Name"));
			sc.setNameRequired(sel.required("Name"));
			sc.setDescriptionSelector(sel.elementSelector("Description"));
			sc.setDescriptionValue(sel.valueSelector("Description"));
			sc.setDescriptionRequired(sel.required("Description"));
			sc.setPriceSelector(sel.elementSelector("Price"));
			sc.setPriceValue(sel.valueSelector("Price"));
			sc.setPriceRequired(sel.required("Price"));
			sc.setDiscountPriceSelector(sel.elementSelector("DiscountPrice"));
			sc.setDiscountPriceValue(sel.valueSelector("DiscountPrice"));
			sc.setDiscountPriceRequired(sel.required("DiscountPrice"));
			sc.setImageLinkSelector(sel.elementSelector("ImageLink"));
			sc.setImageLinkValue(sel.valueSelector("ImageLink"));
			sc.setImageLinkRequired(sel.required("ImageLink"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return sc;
	}

	public static String propertyString(SourceConfiguration m) {
		Properties p = new Properties();
		
		p.setProperty("Name", m.getNameSelector());
		if (!empty(m.getNameValue()))
			p.setProperty("Name.value", m.getNameValue());
		if (m.isNameRequired())
			p.setProperty("Name.required", "true");

		p.setProperty("Description", m.getDescriptionSelector());
		if (!empty(m.getDescriptionValue()))
			p.setProperty("Description.value", m.getDescriptionValue());
		if (m.isDescriptionRequired())
			p.setProperty("Description.required", "true");
		
		p.setProperty("Price", m.getPriceSelector());
		if (!empty(m.getPriceValue()))
			p.setProperty("Price.value", m.getPriceValue());
		if (m.isPriceRequired())
			p.setProperty("Price.required", "true");
		
		p.setProperty("DiscountPrice", m.getDiscountPriceSelector());
		if (!empty(m.getDiscountPriceValue()))
			p.setProperty("DiscountPrice.value", m.getDiscountPriceValue());
		if (m.isDiscountPriceRequired())
			p.setProperty("DiscountPrice.required", "true");
		
		p.setProperty("ImageLink", m.getImageLinkSelector());
		if (!empty(m.getImageLinkValue()))
			p.setProperty("ImageLink.value", m.getImageLinkValue());
		if (m.isImageLinkRequired())
			p.setProperty("ImageLink.required", "true");
			
		Writer w = new StringWriter();
		try {
			p.store(w, "key=" + m.getKey());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return w.toString();
	}

	private static boolean empty(String s) {
		return s == null || "".equals(s);
	}

	public String getSourceKey() {
		return sourceKey;
	}

	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
	}

	public String getBulkData() {
		return bulkData;
	}

	public void setBulkData(String bulkData) {
		this.bulkData = bulkData;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BulkConfiguration [key=").append(key)
				.append(", revision=").append(revision).append(", sourceKey=")
				.append(sourceKey).append("]");
		return builder.toString();
	}


}
