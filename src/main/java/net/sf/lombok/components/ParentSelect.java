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

import java.util.List;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.FieldValidationSupport;
import org.apache.tapestry5.FieldValidator;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.SelectModelVisitor;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.BeforeRenderTemplate;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Mixin;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.base.AbstractField;
import org.apache.tapestry5.corelib.mixins.RenderDisabled;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.internal.util.SelectModelRenderer;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.services.FieldValidatorDefaultSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.apache.tapestry5.util.EnumSelectModel;

/**
 * This component is to be used with {@link ChildSelect}.
 *  This component is a clone of the Select component to drive a {@link ChildSelect}.
 * The model in ChildSelect depends on the value chosen in this select.
 * This component is same as the usual select apart from the following.
 * 1) The blankoption is removed.
 * 2) The selected index is placed in the Request to make it
 * available to the ChildSelect component.
 * 
 * Note that this component must be placed before a ChildSelect in the html
 * template.
 * @author Shing Hing Man
 *
 */
public class ParentSelect extends AbstractField
{
    private class Renderer extends SelectModelRenderer
    {

        public Renderer(MarkupWriter writer)
        {
            super(writer, encoder);
        }

        @Override
        protected boolean isOptionSelected(OptionModel optionModel, String clientValue)
        {
            return isSelected(clientValue);
        }
    }

    /**
     * Allows a specific implementation of {@link ValueEncoder} to be supplied. This is used to create client-side
     * string values for the different options.
     *
     * @see ValueEncoderSource
     */
	@SuppressWarnings("rawtypes")
	@Parameter
    private ValueEncoder encoder;

    @Inject
    private ComponentDefaultProvider defaultProvider;

   
    /**
     * The model used to identify the option groups and options to be presented to the user. This can be generated
     * automatically for Enum types.
     */
    @Parameter(required = true)
    private SelectModel model;


    @Inject
    private Request request;

    @Inject
    private ComponentResources resources;

    @Environmental
    private ValidationTracker tracker;

    /**
     * Performs input validation on the value supplied by the user in the form submission.
     */
    @Parameter(defaultPrefix = BindingConstants.VALIDATE)
    private FieldValidator<Object> validate;

   
    
    /**
     * The value to read or update.
     */
    @Parameter(required = true, principal = true)
    private Object value;

    @Inject
    private FieldValidationSupport fieldValidationSupport;

    @SuppressWarnings("unused")
    @Mixin
    private RenderDisabled renderDisabled;

    private String selectedClientValue;

    private boolean isSelected(String clientValue)
    {
        return TapestryInternalUtils.isEqual(clientValue, selectedClientValue);
    }

   
    @Override
    protected void processSubmission(String elementName)
    {
        String submittedValue = request.getParameter(elementName);

        tracker.recordInput(this, submittedValue);

        Object selectedValue = InternalUtils.isBlank(submittedValue)
                               ? null :
                               encoder.toValue(submittedValue);

        try
        {
            fieldValidationSupport.validate(selectedValue, resources, validate);

            value = selectedValue;
            
            setSelectedIndexInRequest();
            
        }
        catch (ValidationException ex)
        {
            tracker.recordError(this, ex.getMessage());
        }
    }

    void afterRender(MarkupWriter writer)
    {
        writer.end();
    }

    void beginRender(MarkupWriter writer)
    {
        writer.element("select", "name", getControlName(), "id", getClientId());

        validate.render(writer);

        resources.renderInformalParameters(writer);
        setSelectedIndexInRequest();
        // Disabled is via a mixin
    }

   
    @SuppressWarnings("rawtypes")
	ValueEncoder defaultEncoder()
    {
        return defaultProvider.defaultValueEncoder("value", resources);
    }

    @SuppressWarnings("unchecked")
    SelectModel defaultModel()
    {
        @SuppressWarnings("rawtypes")
		Class valueType = resources.getBoundType("value");

        if (valueType == null) return null;

        if (Enum.class.isAssignableFrom(valueType))
            return new EnumSelectModel(valueType, resources.getContainerMessages());

        return null;
    }

   
    
    /**
     * Computes a default value for the "validate" parameter using {@link FieldValidatorDefaultSource}.
     */
    Binding defaultValidate()
    {
        return defaultProvider.defaultValidatorBinding("value", resources);
    }

    
    /**
     * Renders the options, including the blank option.
     */
    @SuppressWarnings("unchecked")
	@BeforeRenderTemplate
    void options(MarkupWriter writer)
    {
        selectedClientValue = tracker.getInput(this);

        // Use the value passed up in the form submission, if available.
        // Failing that, see if there is a current value (via the value parameter), and
        // convert that to a client value for later comparison.

        if (selectedClientValue == null) selectedClientValue = value == null ? null : encoder.toClient(value);

        SelectModelVisitor renderer = new Renderer(writer);

        model.visit(renderer);
    }

    @Override
    public boolean isRequired()
    {
        return validate.isRequired();
    }

    void setValue(Object value)
    {
        this.value = value;
    }

   
	@SuppressWarnings("rawtypes")
	void setValueEncoder(ValueEncoder encoder)
    {
        this.encoder = encoder;
    }

    
    void setValidationTracker(ValidationTracker tracker)
    {
        this.tracker = tracker;
    }


    public String getSelectedIndexKey(){
    	return getControlName() + "_SelectedIndex";
    }
    
    
    /**
     * Place selected index in request, so that the child select can retrieve it.
     * 
     */
    protected void setSelectedIndexInRequest(){
    	if (value==null){
    		return;
    	}
        // index such that model.options[index].value = value;
        int selectedIndex = -1;
        List<OptionModel> options = model.getOptions();
        for (int i=0; i < options.size(); i++){
        	if (value.equals(options.get(i).getValue())){
        		selectedIndex = i;
        		break;
        	}
        		
        }
        request.setAttribute(getSelectedIndexKey(), selectedIndex);
    }
    
}
