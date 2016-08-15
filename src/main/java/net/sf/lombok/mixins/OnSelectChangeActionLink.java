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

package net.sf.lombok.mixins;

import java.util.List;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BindParameter;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * A simple mixin to be used with a select element. On change value of select,
 * trigger an action on the server. This is done using the url from an ActionLink.
 * The select value is placed in the context of the ActionLink url.
 * 
 */

@Import(library="OnSelectChangeActionLink.js")
public class OnSelectChangeActionLink {

	@BindParameter
	private SelectModel model;
	
	@SuppressWarnings("rawtypes")
	@BindParameter
	private ValueEncoder encoder;
	 
	@Inject
	private JavaScriptSupport jsSupport;

	@InjectContainer
	private ClientElement element;

	@Inject
	private ComponentResources resources;
	
	@Inject
	private ComponentDefaultProvider defaultProvider;

	
	@SuppressWarnings("rawtypes")
	ValueEncoder defaultEncoder() {
		return defaultProvider.defaultValueEncoder("value", resources);
	}

	@AfterRender
	public void afterRender() {

		StringBuilder sb = new StringBuilder();
		String mapName = "urlMap_" + element.getClientId();
		sb.append(mapName + "= $H({");
		
		List<OptionModel> optionModels = model.getOptions();
		int noOfOptions = optionModels.size();
		for (int i = 0; i < noOfOptions; i++) {
			OptionModel optionModel = optionModels.get(i);
			@SuppressWarnings("unchecked")
			String key = encoder.toClient(optionModel.getValue());
			Object[] context = new Object[] { key };
			String value = resources.createEventLink(EventConstants.ACTION,
					context).toString();
			String keyValue = String.format("%s:'%s'", key, value);
			sb.append(keyValue);
			if (i < (noOfOptions - 1)) {
				sb.append(",");
			}
		}
		sb.append("})" + "\n");
		sb.append(String.format("new SelectWatcher('%s', %s)", element
				.getClientId(), mapName));
		jsSupport.addScript(sb.toString());

	}

}
