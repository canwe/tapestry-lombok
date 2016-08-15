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

import net.sf.lombok.componenthelpers.CheckboxContainer;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.components.Checkbox;

/**
 * This component is to be ues with CheckboxGroup and ControlCheckbox.
 * A wrapper around tapestry Checkbox component.
 * @author Shing Hing Man
 *
 */
public class ControlledCheckbox {

	@SuppressWarnings("unused")
	@Parameter(required = true)
	private boolean value;

	@SuppressWarnings("unused")
	@Parameter
	private boolean disabled;

	@Environmental
	private CheckboxContainer checkboxContainer;

	
	 @Component(parameters =
	 { "value=inherit:value", "disabled=inherit:disabled" }, id="checkbox")
	private Checkbox checkbox;
	
	public String getCheckboxName() {		
		String name = checkbox.getClientId();
		return name;
	}

	void afterRender(MarkupWriter write) {

		checkboxContainer.registerControlledCheckbox(this);
	}

	
}
