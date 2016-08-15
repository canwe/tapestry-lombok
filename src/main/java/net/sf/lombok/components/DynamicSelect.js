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
	
	
	var OptionModel = Class.create();	
	OptionModel.prototype ={
	
		   initialize: function(label, value) {	        
			  this.label = label;
			  this.value=value;
			
	       },
	   
	     equal: function(optModel) {
	          return ((this.label == optModel.label) &&
	                   (this.value == optModel.value)); 
	     
	     }
	     // should be no comma here, otherwise IE won't work
    };
    
   var SelectionModel = Class.create();	
   SelectionModel.prototype ={
	       // optionModels is an array
	       // defaultOptionModel is an OptionModel
		   initialize: function(optionModels, defaultOptionModel) {	        
			  this.optionModels = optionModels;
			  this.defaultOptionModel=defaultOptionModel;
			  this.length=optionModels.length;
			 // this.indexOfDefault=optionModels.indexOf(defaultOptionModel);
	      }
    };
  
   var ChildListController = Class.create();
   ChildListController.prototype ={
	       // parentSelectId - id of the parent selection list
	       // childSelectId  - id of the child selection list 
	       //childSelectionModels is an array of SelectionModels
		   initialize: function(parentSelectId, childSelectId, childSelectionModels) {
		     this.parentSelect = $(parentSelectId);
		     this.childSelect = $(childSelectId);
			 this.childSelectionModels = childSelectionModels;
			 this.debug=false;
		     this.parentSelect.onchange = this.setChildList.bindAsEventListener(this);	
	      },
	   
	  setChildList: function() { 
	      if (this.debug){ 
	         alert("In setChildList");
	      }
	       	 	    
	      var selectedIndex =   this.parentSelect.selectedIndex;
	      var selectedChildModel= this.childSelectionModels[selectedIndex]; 
	      
	      // reset childSelect
	      this.childSelect.options.length=0;
	               
	      if (this.debug){         
	         alert("Parent list selected index :" + selectedIndex + "\n"
	                          + ", no of options in selected child list=" +
	                            selectedChildModel.length);
	      }
	      
	      var defaultIndex =0;
	      
	      // Populate childSelect
	      for (var i=0; i< selectedChildModel.length;i++){
	           optionModel = selectedChildModel.optionModels[i];
	           if (this.debug) {	           
	             alert("i=" + i + "optioModel.label=" + optionModel.label +
	                                ", value=" + optionModel.value ) ;
	           }
	  
	           this.childSelect.options[this.childSelect.options.length]= 
	                    new Option(optionModel.value, optionModel.label);
	                 
	          if (optionModel.equal(selectedChildModel.defaultOptionModel)){
	               defaultIndex = i;
	          }
	                     	      	  
	      }          		 
	      if (this.debug){
	         alert("Default index=" + defaultIndex);
	      }
	      this.childSelect.options[defaultIndex].selected=true;
	   }
	   
    };
    
    
  