// Copyright 2007 Shing Hing Man
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on
// an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
// express or implied. See the License for the specific language
// governing permissions and limitations under the License.

package net.sf.lombok.components;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.corelib.base.AbstractField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

// Orders of the javascripts is important!

@Import(library={"AnchorPosition.js",
		"PopupWindow.js",
		"ColorPicker2.js"}) 
		
public class ColorPicker extends AbstractField {

	@Parameter(required = true, principal = true)
	private String color;

	@Inject
	private Request request;

	@Inject
	@Path("classpath:/net/sf/lombok/components/colorPicker.gif")
	private Asset icon;

	@Inject
	private ComponentResources resources;

	void beginRender(MarkupWriter writer) {

		String name = getControlName();

		String inputTextField = getInputTextField(name);

		String color = getColor();
		if (color == null) {
			color = "#FFFFFF"; // white
		}

		String style = "background:" + color + ";color:" + color + ";";

		writer.element("input", "type", "text", "name", inputTextField,
				 "id", inputTextField,
				"onFocus", "blur()", "style", style, "value", color);

		resources.renderInformalParameters(writer);

	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	protected void afterRender(MarkupWriter writer) {
		writer.end(); // <a>
		writer.writeRaw("\n");
		String name = getControlName();

		String pickerName = name + "_pick";
	
		String targetField = "$('" + getInputTextField(name) + "')";
		
		if (!isDisabled()) {
			writer.element("a", "href", "javascript:void(null)", "name",
					pickerName, "id", pickerName, "onclick", "select_color("
							+ targetField + "," + "'" + pickerName + "'" + ")");
		}
		writer.element("img", "src", icon.toClientURL(), "border", 0, "align",
				"top");

		writer.end(); // end of <img>
		if (!isDisabled()) {
			writer.end(); // end of </a>
		}
		writer.writeRaw("\n");
	}

	@Override
	protected void processSubmission(String elementName) {

		color = request.getParameter(getInputTextField(elementName));

	}

	/**
	 * 
	 * @param elementName
	 * @return The name attribute of the input text.
	 */
	public String getInputTextField(String elementName) {
		return elementName + "_color";
	}
}
