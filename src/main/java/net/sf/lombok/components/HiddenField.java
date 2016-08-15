package net.sf.lombok.components;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.base.AbstractField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

public class HiddenField extends AbstractField {
	
	@Parameter(required = true, principal = true)
	private String value;

	@Inject
	private Request request;

	final void afterRender(MarkupWriter writer) {
		writer.end(); // input
	}

	@Override
	protected void processSubmission(String elementName) {
		
		value = request.getParameter(elementName);
	}

	void beginRender(MarkupWriter writer) {
		writer.element("input", "type", "hidden", "name", getControlName(),
				"id", getClientId(), "value", value);
	}

}
