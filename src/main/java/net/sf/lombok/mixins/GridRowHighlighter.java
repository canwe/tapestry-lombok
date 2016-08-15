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

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.dom.Node;

/**
 * A mixin to highlight a row in the tapestry Grid when the mouse is over it.
 * This piece of code was original posted to tapestry mailing list by 
 * Radoslav Bielik. 
 *
 */
public class GridRowHighlighter {
	
	/**
	 * Highlight colour of a row in #hhhhhh
	 */
	@Parameter(value = "#D3D5FF", defaultPrefix = BindingConstants.LITERAL)
	private String colour ;
	  
	@AfterRender
	void after(MarkupWriter writer)
	{
	  Element tbody = writer.getElement().find("div/table/tbody");

	  if (tbody != null)
	  {
	    for (Node rowNode : tbody.getChildren())
	    {
	      if (rowNode instanceof Element)
	      {
	         ((Element) rowNode).attribute("onMouseOver",
	            String.format("this.style.background='%s';",colour));
	         ((Element) rowNode).attribute("onMouseOut",
	            "this.style.background='';");
	      }
	    }
	  }
	} 
}
