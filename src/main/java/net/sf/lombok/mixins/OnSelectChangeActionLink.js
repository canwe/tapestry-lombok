// Copyright 2009 Shing Hing Man
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on
// an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
// express or implied. See the License for the specific language
// governing permissions and limitations under the License.


var SelectWatcher = Class.create();

//defining the rest of the class implementation
SelectWatcher.prototype = {

    initialize: function(selectId, urlMap) {
	  this.selectList= $(selectId);
	  this.urlMap = urlMap;
	  //assigning our method to the event
	  this.selectList.onchange = 
		this.openSelectedURL.bindAsEventListener(this);
    },

    openSelectedURL: function(evt) {

	  //alert('In openSelectedURL: selected=' + this.selectList.value )
	  var selectedValue = this.selectList.value;
	  var selectedURL = this.urlMap.get(selectedValue)

   	 // alert('In openSelectedURL: selectedURL=' + selectedURL )
	  // Reload current window with selectedURL
	  window.location.href = selectedURL
   }
};


