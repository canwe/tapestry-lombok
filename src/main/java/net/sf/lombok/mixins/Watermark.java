// Copyright 2010 Shing Hing Man
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

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * A mixin to add watermark to a TextField or TextArea component.
 * The javascript is written by Blaz Lipuscek.
 * 
 * @author Shing Hing Man
 */

@Import(library="watermark.js",
		  stylesheet="watermark.css")
public class Watermark {

    @Parameter(value = "Please enter a value.", required = false,
    		defaultPrefix = BindingConstants.LITERAL)
    private String message;

    @Inject
    private JavaScriptSupport jsSupport;

    @InjectContainer
    private ClientElement element;
    

    private String clientId;

	public String getClientId() {
		return clientId;
	}
	
    protected void afterRender(MarkupWriter writer) {
  
    	clientId = element.getClientId();
		String var = String.format("$('%s').watermark('%s');", clientId, message);
		
		jsSupport.addScript(var);
	
	}

	
    
}
