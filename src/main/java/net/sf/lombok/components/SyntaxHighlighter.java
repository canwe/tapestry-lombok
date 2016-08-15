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

import java.util.HashMap;
import java.util.Iterator;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * This component is wrapper around the syntaxhighter (version 1.5.1) 
 * by alex.gorbatchev
 * at http://code.google.com/p/syntaxhighlighter/. 
 * @author Shing Hing Man
 *
 */
@SupportsInformalParameters

@Import(library=
		{"classpath:/net/sf/lombok/components/syntaxhighlighter/shCore.js",
			  "classpath:/net/sf/lombok/components/syntaxhighlighter/shBrushCss.js",
			  "classpath:/net/sf/lombok/components/syntaxhighlighter/clipboard.js"},
          stylesheet="classpath:/net/sf/lombok/components/syntaxhighlighter/SyntaxHighlighter.css")
public class SyntaxHighlighter implements ClientElement {
	

	@Parameter(required = true)
	private String language;

	@Environmental
	private JavaScriptSupport support;

	@Inject
	private ComponentResources resources;

	@Inject
	@Path("classpath:/net/sf/lombok/components/syntaxhighlighter/shBrushJava.js")
	private Asset shBrushJava;
	
	@Inject
	@Path("classpath:/net/sf/lombok/components/syntaxhighlighter/shBrushXml.js")
	private Asset shBrushXml;
	
	@Inject
	@Path("classpath:/net/sf/lombok/components/syntaxhighlighter/shBrushJScript.js")
	private Asset shBrushJScript;
	

	@Inject
	@Path("classpath:/net/sf/lombok/components/syntaxhighlighter/shBrushCss.js")
	private Asset shBrushCss;
	
	@Inject
	@Path("classpath:/net/sf/lombok/components/syntaxhighlighter/shBrushSql.js")
	private Asset shBrushSql;
	
	
	private String clientId;

	
	private HashMap<String, Asset> codeToScriptMap = 
		  new  HashMap<String,Asset>();
	
	public String getClientId() {
		return clientId;
	}

	
	protected void beginRender(MarkupWriter writer) {

		
		if (!codeToScriptMap.containsKey(language)){
			throw new RuntimeException("The following lanuage is not supported : " +
					 language + "\n Supported lanuages are :"  +
					 getKeys());
					
		}
		
		support.importJavaScriptLibrary(codeToScriptMap.get(language));
		
		
		clientId = support.allocateClientId(resources.getId());

		writer.element("pre", "name", clientId, "class", language);
		
		resources.renderInformalParameters(writer);
		

	}


	protected void afterRender(MarkupWriter writer) {
		writer.end(); // <tag>
		
	    support.addScript("dp.SyntaxHighlighter.HighlightAll('%s');", clientId);
	}
	
	
	@SuppressWarnings("unused")
	private void pageLoaded(){
		initialse();
	}
	
	private void initialse(){
         codeToScriptMap = new HashMap<String, Asset>();
         codeToScriptMap.put("java", shBrushJava);
         codeToScriptMap.put("xml", shBrushXml);
         codeToScriptMap.put("html", shBrushXml);
         codeToScriptMap.put("xhtml", shBrushXml);
         codeToScriptMap.put("xslt", shBrushXml);
         codeToScriptMap.put("js", shBrushJScript);
         codeToScriptMap.put("jscript", shBrushJScript);
         codeToScriptMap.put("javascript", shBrushJScript);
         codeToScriptMap.put("css", shBrushCss);
         codeToScriptMap.put("sql", shBrushSql);
         
	}
	
	
	public String getKeys(){
		Iterator<String> it = codeToScriptMap.keySet().iterator();
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()){
			sb.append(it.next()).append(",");
		}
			
		
		
		return sb.toString();
	}
}
