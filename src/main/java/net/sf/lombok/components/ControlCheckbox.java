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

import org.apache.tapestry5.annotations.Environmental;

/**
 * This component is to be ues with CheckboxGroup and ControlCheckbox.
 * Select all the ControlledCheckboxes by checking this control checkbox.
 * @author Shing Hing Man
 *
 */
public class ControlCheckbox {
	
	@Environmental
	private CheckboxContainer checkboxContainer;
	
	public String getOnclickStr(){
		String str = checkboxContainer.getFunctionName() + "(this.checked)";
		return str;
	}
}
