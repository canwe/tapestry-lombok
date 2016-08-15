package net.sf.lombok.pages.testcomponents;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;

public class SubmitOnChangeOfSelectDemo {

	@SuppressWarnings("unused")
	@Persist(PersistenceConstants.SESSION)
	@Property
	private Date maturityDate;

	@Property
	@Persist(PersistenceConstants.SESSION)
	private String dateFormat;

	@Component(id = "myForm")
	private Form form;

	private boolean changedFormat;

	@SuppressWarnings("unused")
	@Property
	@Persist(PersistenceConstants.SESSION)
	private boolean showInput;

	/**
	 * On the html template, when  select (with id=dateFormatS ) changes value,
	 * the form will be submitted and calls this method.
	 * <p>
	 * Alternative : @OnEvent(component = "dateFormt", value = EventConstants.ACTION)
	 */
	//@OnEvent(component = "dateFormatS", value = "changed")
	//public void onChange(){
	public void onChangedFromDateFormatS() {
		changedFormat = true;
	}

	public void beginRender() {
		if (dateFormat == null) {
			// One of format in the inline date format select
			dateFormat = "dd/MMM/yyyy";
		}
	}

	public DateFormat getDateFormat() {
		return new SimpleDateFormat(dateFormat);
	}


	public void onSubmitFromMyForm() {
		showInput = false;
		if (changedFormat) {
			form.clearErrors();
		} else if (!form.getHasErrors()) {
			showInput = true;
		}

	}

}

