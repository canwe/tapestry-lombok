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

var CheckboxGroup = Class.create();

//defining the rest of the class implementation
CheckboxGroup.prototype = {

   initialize: function(controlCheckbox, controlledCheckboxes) {	        
		this.controlCheckbox = $(controlCheckbox);
		this.controlledCheckboxes = controlledCheckboxes;
		
		this.controlCheckbox.onclick = 
		   this.updateControlledCheckboxes.bindAsEventListener(this);
		
   },

      // Set the state of each of the controlled checkboxs, to the state of 
      // control checkbox
   updateControlledCheckboxes: function() {	   
      var flag = $F(this.controlCheckbox);
	  this.controlledCheckboxes.each(function(cb) {		     
	       $(cb).checked=flag;
	  });
   }
};