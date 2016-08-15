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

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.corelib.base.AbstractTextField;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * A component to convert a number value into word.
 * 
 * @author Shing Hing Man
 */
@SupportsInformalParameters


@Import(library="N2WTextField.js")
public class N2WTextField extends AbstractTextField {

	/*
	 * The position of the word to be displayed relative to the input text
	 * field. The possible values are SAME_LINE and BELOW.
	 */
	@Parameter(required = true, defaultPrefix = "literal", value = "literal:SAME_LINE")
	private String wordPosition;

	/*
	 * If true, the word displayed will includes commas and/or full stops. eg :
	 * For 101001, one hundred one thousand, twenty. Otherwise, a word will be
	 * displayed without commas, nor full stops. eg : For 101001, one hundred
	 * one thousand twenty
	 */
	@Parameter(required = false, value = "false")
	private boolean commaFlag;

	/*
	 * wrapFlag is only applicable to wordPosition BELOW. If true, the word (if
	 * it is long enough) will be displayed in multi-line eg : one thousand, two
	 * hundred thirty three. If false, the word will be displayed in a single
	 * line.
	 */
	@Parameter(required = false, value = "false")
	private boolean wrapFlag;

	/*
	 * The css class name of error message
	 */
	@Parameter(required = false, defaultPrefix = "literal")
	private String errorCSS;

	/*
	 * The css class name of word message
	 */
	@Parameter(required = false, defaultPrefix = "literal")
	private String wordCSS;

	/*
	 * The error message to be displayed when the entered number is not numeric
	 * (when the javascript function w2n_isValid(x) returns false).
	 */
	@Parameter(required = false, defaultPrefix = "litreal", value = "literal:Please enter a numeric value.")
	private String errorMessage;

	@Inject
	private ComponentResources resources;

	public static final String SAME_LINE = "SAME_LINE";

	public static final String BELOW = "BELOW";

	@Override
	protected void writeFieldTag(MarkupWriter writer, String value) {
		if (wordPosition.equals(SAME_LINE) && wrapFlag) {
			throw new RuntimeException("wrapFlag=T and wordPostion=SAME_LINE  "
					+ "are incompatible. wrapFlag=T is only applicable to "
					+ "wordPosition BELOW.");
		}

		// output attribute for number to word
		String outputId = getControlName() + "_word";
		String onblur = "w2n_clear('" + outputId + "')";
	
		String currentEltId = "$('" + getControlName() + "')";
		String onkeyup = "w2n_update(" + currentEltId + ",'" + outputId + "',"
				+ commaFlag + "," + wrapFlag + ",'" + errorMessage + "')";

		writer.element("input", "type", "text", "name", getControlName(), "id",
				getClientId(), "value", value, "onblur", onblur, "onkeyup",
				onkeyup, "onfocus", onkeyup);

		resources.renderInformalParameters(writer);

		//support.addScriptLink(n2WTextFieldScript);

		// create html element that holds the word.
		String wordHtml = "";
		if (wordPosition.equals(SAME_LINE)) {
			wordHtml = "span";
		} else if (wordPosition.equals(BELOW)) {
			wordHtml = "div";
		} else {
			throw new RuntimeException("Invalid word position :" + wordPosition);
		}

		// write tag to hold the word
		if (wordCSS != null && wordCSS.length() > 0) {
			writer.element(wordHtml, "name", outputId, "id", outputId, "class",
					wordCSS);
		} else {
			writer.element(wordHtml, "name", outputId, "id", outputId);
		}

		writer.end();

		// Write tag to hold error message
		// Holding error and word message in two different html tags
		// makes setting css class easier.
		// If we hold error and word in one tag, we will have to set the
		// css class using dhtml and this might lead to browser
		// incompatibility problem.
		if (errorCSS != null && errorCSS.length() > 0) {
			writer.element(wordHtml, "name", outputId + "Error", "id", outputId
					+ "Error", "class", errorCSS);
		} else {
			writer.element(wordHtml, "name", outputId + "Error", "id", outputId
					+ "Error");
		}
		writer.end();

	}

	void afterRender(MarkupWriter writer) {
		writer.end();
	}

}
