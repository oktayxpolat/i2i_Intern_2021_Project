package com.i2iproject.builders;

import com.i2iproject.builders.models.Template;
import com.i2iproject.builders.models.TemplateData;

public interface TemplateBuilder {
	public void addTemplateData(TemplateData dataToBeAdded);
	public Template getConstructedTemplate();
}
