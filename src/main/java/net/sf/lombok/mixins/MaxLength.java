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


import static org.apache.tapestry5.BindingConstants.LITERAL;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * This is a slight modification of the MaxLength mixin created by  Inge Solvoll.
 * Please see http://tinybits.blogspot.com/2009/04/enforce-max-length-on-textareas-and_15.html
 * 
 * @author Shing Hing Man
 *
 */
@Import(library="MaxLength.js",stylesheet="MaxLength.css")
public class MaxLength {

	@InjectContainer
	private ClientElement container;

	@Parameter(required = true)
	private int max;

	@Parameter(required=false, value="true")
	private boolean displayCounter;
	

	@Parameter(required = false, defaultPrefix = LITERAL,value="maxLengthMsg")
	@Property
	private String cssClass;

	
	@Environmental
	private JavaScriptSupport renderSupport;
	  
	void afterRender(MarkupWriter writer) {

		String id = container.getClientId();

		String counterId = id + "-counter";


		if (displayCounter) {
			writer.element("div", "id", counterId,"class",cssClass );
		
		    writer.end();
		}


		renderSupport.addScript("new MaxLength('%s', '%s', %s)", id, counterId, max);

	}

}