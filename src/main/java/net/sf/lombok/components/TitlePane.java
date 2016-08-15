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

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * A tile pane whose content can be toggled between visible and 
 * invisible by clicking the title.
 * @author Shing Hing Man
 *
 */

@Import (library="TitlePane.js", stylesheet="TitlePane.css")
public class TitlePane implements ClientElement {

	private static final String BLIND = "blind";

	private static final String APPEAR = "appear";

	@Parameter(required = true)
	private String title;

	// The scriptaculous effect on how the content will appear/disappear.
	@Parameter(value="defaultEffect")
	private String effect;

	// CSS class for the title
	@Parameter(value="defaultTitleClass")
	private String titleClass;

	// CSS class for the content
	@Parameter(value="defaultContentClass")
	private String contentClass;

	@Parameter(required = false)
	private boolean hideIcon;
	
	@Parameter(required = false)
	private boolean showOnLoad;

	@Parameter(value="arrowRightImg")
	private Asset closedIcon;
	
	@Parameter(value="arrowDownImg")
	private Asset openedIcon;
	
	
	
	@Inject
	@Path("classpath:/net/sf/lombok/components/solidTriangleRight.gif")
	private Asset arrowRightImg;

	@Inject
	@Path("classpath:/net/sf/lombok/components/solidTriangleDown.gif")
	private Asset arrowDownImg;

	@Environmental
	private JavaScriptSupport support;

	@Inject
	private ComponentResources resources;

	private String clientId;

	public String getClientId() {
		return clientId;
	}

	protected void beginRender(MarkupWriter writer) {

		if (!effect.equals(BLIND) && !effect.equals(APPEAR)){
			throw new RuntimeException("Invalid affect : " + effect 
					+ ". Effect must blink or appear.");
		}
		clientId = support.allocateClientId(resources.getId());
		
		String onclickVaue="javascript:void tp_toggle('" + getContentId() + "', '"
		  + getArrowId() + "','" +  openedIcon + "','" + closedIcon + "', '" + effect
		      + "')";
	
		writer.element("div","class", getTitleClass(), "id", getClientId(), "onclick", onclickVaue );
		writer.element("img", "id", getArrowId(), "src", getIcon(), "style", getShowIcon() );
		writer.end();
		writer.getElement().raw(getTitle());
		writer.end();

	}

	public String getTitle() {
		return title;
	}

	public Asset getArrowRightImg() {
		return arrowRightImg;
	}

	public Asset getArrowDownImg() {
		return arrowDownImg;
	}

	public String getContentId() {
		return clientId + "_content";
	}

	public String getTitleClass() {
		return titleClass;
	}

	public void setTitleClass(String titleClass) {
		this.titleClass = titleClass;
	}

	public String getContentClass() {
		return contentClass;
	}

	public void setContentClass(String contentClass) {
		this.contentClass = contentClass;
	}

	public String getArrowId() {
		return clientId + "_arrow";
	}

	public String getDefaultContentClass() {
		return "tpContent";
	}

	public String getDefaultTitleClass() {
		return "tpTitle";
	}

	public String getDefaultEffect() {
		return BLIND;
	}

	public String getEffect() {
		return effect;
	}

	public String getShowIcon(){
		String show = (hideIcon ? "display:none" : null);
		return show;
	}
	
	public String getShowOnLoad(){
		String show = ( showOnLoad ? null: "display:none" );
		return show;
	}
	
	public Asset getIcon(){
		Asset icon = (showOnLoad ? openedIcon : closedIcon);
		return icon;
	}

	public Asset getOpenedIcon() {
		return openedIcon;
	}
	
	public Asset getClosedIcon() {
		return closedIcon;
	}
	
}
