// Copyright 2008 Shing Hing Man
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

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.corelib.components.Checkbox;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Heartbeat;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * A control check box to control the standard Checkbox with mixin 
 * ControlledCheckbox.
 * 
 */


@Import(library="ControlCB.js")
public class ControlCB extends Checkbox{

	@Environmental
	private Heartbeat heartbeat;


	@Inject
	private JavaScriptSupport jsSupport;
     	
	/**
	 * A collection of the controlled check box css ids.
	 */
	private  Collection<String> checkboxIds;

	public void afterRender(MarkupWriter writer) {


		Runnable command = new Runnable() {
			public void run() {
				
				StringBuffer controlledCheckboxesSB = new StringBuffer("[");
				Iterator<String> it = getCheckboxIds().iterator();
				while (it.hasNext()) {
					if (!controlledCheckboxesSB.toString().equals("[")) {
						controlledCheckboxesSB.append(",");
					}
					String checkboxId = it.next();
					controlledCheckboxesSB.append("'" + checkboxId + "'");

				}
				controlledCheckboxesSB.append("]");
				
				// add line to instance a CheckboxGroup 
				jsSupport.addScript(String.format(
						"new CheckboxGroup('%s', %s);", 
								getClientId(), controlledCheckboxesSB
								.toString()));
			}
		};

		// The control check box might be places after the controlled checked box.
		// So need to wait until all controlled checkbox are registered before
		// rendering the javscript.
		heartbeat.defer(command);

	}

    public Collection<String> getCheckboxIds()
    {
    	  if (checkboxIds ==null)
          {
          	checkboxIds = new HashSet<String>();
          }
  	      return checkboxIds;
    }
	
	public void registerControlledCheckbox(String checkboxId) {
		getCheckboxIds().add(checkboxId);
	}

}
