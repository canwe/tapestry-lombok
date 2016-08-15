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

Tapestry.Initializer.submitOnChange = function(spec) {

	eventName = spec.eventName
	formId = spec.formId
	elementId = spec.elementId
	
    $(elementId).observe (eventName, function(event) {

            var hiddenSubmit = document.createElement("input");

            hiddenSubmit.type = "submit";

            hiddenSubmit.name = "submit_" + elementId;

            hiddenSubmit.style.visibility = "hidden";

            hiddenSubmit.style.display = "none";

            $(formId).appendChild(hiddenSubmit);

            hiddenSubmit.click();


    });

};