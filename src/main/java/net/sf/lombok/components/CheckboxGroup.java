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

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import net.sf.lombok.componenthelpers.CheckboxContainer;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.FormSupport;

/**
 * The components CheckboxGroup, ControlCheckbox, ControlledCheckbox 
 * together allows simultaneous selection of a group of checkboxes.
 * 
 * @author Shing Hing Man 
 * 11th November, 07
 * 
 *
 */
public class CheckboxGroup {
	
	@Environmental
	private FormSupport formSupport;

	@Inject
	private Environment environment;

	@Inject
	private ComponentResources compResources;
	
	private Collection<String> checkboxNames;
	
	private String functionName;

	void beginRender(MarkupWriter writer) {
	
		initialize();

		if (checkboxNames == null){
			checkboxNames = new HashSet<String>();
		}
	
		// The value (class) in environment.push(key,value) must be a class outside
		// component, page directory. Otherwise, tapestry will try to enhance it!
		environment.push(CheckboxContainer.class, new CheckboxContainer(){

			public void registerControlledCheckbox(ControlledCheckbox checkbox) {
				    String name = checkbox.getCheckboxName();					
					String form = formSupport.getClientId();                    
					checkboxNames.add(form + "." + name);				
			}

			public String getFunctionName() {
			     return functionName;
			}
						
		});
	
			
		
	}

	public void afterRender(MarkupWriter writer) {
		// Add  <javascript> at top of <body> 
		Element body = writer.getDocument().find("html/body");
       
		body.elementAt(0, "script", "type", "text/javascript");
		Element script = writer.getDocument().find("html/body/script");
		
		script.raw("\n");
		script.text("function " + functionName + "(value) {");
		script.raw("\n");
		Iterator<String> it = checkboxNames.iterator();
		while (it.hasNext()){
			String checkboxName = it.next();
			String str =  "document.forms." + checkboxName + ".checked=value;";
			script.text(str);
			script.raw("\n");
		}
		script.text("}");
		
	
		environment.pop(CheckboxContainer.class);
	}

	private void initialize() {
		String id= compResources.getId();
		
		// Hopefull this will make the functionName unqiue on a page
		functionName = "setCheckboxGroup_" + id;		
	}	
	
}
