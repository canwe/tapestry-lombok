// Copyright 2011 Shing Hing Man
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on
// an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
// express or implied. See the License for the specific language
// governing permissions and limitations under the License.

package net.sf.lombok.mixins;

import java.util.Map;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 *
 * A masked input mixin using Javascript by Bjarte K. Vebjørnsen.
 * https://github.com/bjartekv/MaskedInput
 * 
 */
@Import(library = "MaskedInput.js")
public class MaskedInput {

	@InjectContainer
	private ClientElement container;

	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String mask;

	
	@Parameter(required = false, defaultPrefix = BindingConstants.PROP)
	private Map<String, String> definitions;

	@Environmental
	private JavaScriptSupport renderSupport;

	void afterRender(MarkupWriter writer) {

		if (definitions != null && !definitions.isEmpty()) {
			for (Map.Entry<String, String> entry : definitions.entrySet()) {
				renderSupport.addScript("MaskedInput.definitions['%s']='[%s]';", entry.getKey(), entry.getValue());
			}
		}
		renderSupport.addScript("new MaskedInput('#%s', '%s');", container.getClientId(), mask);

	}

}