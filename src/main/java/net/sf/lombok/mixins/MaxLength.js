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



// The source code taken from Inge Solvoll's blog at
// http://tinybits.blogspot.com/2009/04/enforce-max-length-on-textareas-and_15.html
var MaxLength = Class.create();

MaxLength.prototype = {
	initialize : function(id, counterId, max) {
		this.max = max;
		this.element = $(id);
		this.element.observe('keyup', this.keyup.bindAsEventListener(this));
		this.counterElement = $(counterId);
		this.updateCounter();
	},

	keyup : function(event) {
		if (this.element.value.length > this.max) {
			this.element.value = this.element.value.substring(0, this.max);
		}
		this.updateCounter();
	},

	updateCounter : function() {
		var currentLength = this.element.value.length;
		this.counterElement.innerHTML = "Number of characters left out of maximum of " 
			            + this.max + " : " + (this.max - currentLength);
	}
}