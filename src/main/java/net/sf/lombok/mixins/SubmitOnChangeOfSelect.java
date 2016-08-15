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

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentAction;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * A mixin for the select component to submit the enclosing form on change of
 * value. Also call an Java listener method. The listener method name has the
 * form : onChangedFrom<elementId>
 * 
 * The code is based on the SubmitOfChange.java example posted by IIya Obshadko
 * in the Tapestry mailing list.
 * 
 * @author Shing Hing Man
 * 
 */
@Import(library = "SubmitOnChangeOfSelect.js")
public class SubmitOnChangeOfSelect {

	@Environmental
	private JavaScriptSupport renderSupport;

	@Environmental
	private FormSupport formSupport;

	@InjectContainer
	private ClientElement container;

	@Inject
	private ComponentResources resources;

	@Inject
	private Request request;

	private final String monitorEvent = "change";

	private final String submitEvent = "changed";

	/**
	 * 
	 * Form submit context
	 */

	@Parameter(required = false, allowNull = false, defaultPrefix = "prop")
	private Object[] submitContext;

	private static class ProcessFakeSubmission implements
			ComponentAction<SubmitOnChangeOfSelect> {

		private static final long serialVersionUID = -2205200857980521346L;

		private String fakeSubmitName;

		public ProcessFakeSubmission(String fakeSubmitName) {

			this.fakeSubmitName = fakeSubmitName;

		}

		public void execute(SubmitOnChangeOfSelect component) {

			component.processFakeSubmission(fakeSubmitName);

		}

	}

	public void afterRender() {

		final String formId = formSupport.getClientId();

		formSupport.store(this,
				new ProcessFakeSubmission("submit_" + container.getClientId()));

		// KeyValue pairs
		JSONObject spec = new JSONObject("eventName", monitorEvent, "formId",
				formId, "elementId", container.getClientId());

		// Call javascript function submitOnChange
		renderSupport.addInitializerCall("submitOnChange", spec);

	}

	private void processFakeSubmission(String elementName) {

		String value = request.getParameter(elementName);

		if (value == null)

			return;

		Runnable sendNotification = new Runnable() {

			public void run() {

				resources.triggerEvent(submitEvent, submitContext, null);

			}

		};

		formSupport.defer(sendNotification);

	}

}