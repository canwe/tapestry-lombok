package net.sf.lombok.pages.testcomponents;

import java.text.SimpleDateFormat;
import java.util.List;

import net.sf.lombok.data.Access;
import net.sf.lombok.data.AccessListProducer;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;

public class AccessGrid {

	// Should be injected
	@SuppressWarnings("unused")
	@Property
	private List<Access> accessList;

	private Access access;

	@Inject
	private Messages messages;

	@Inject
	private BeanModelSource beanModelSource;

	@Component(id = "myGrid")
	private Grid grid;

	private int rowNoWithCurrentPage;

	@Property
	private int noOfRowsPerPage = 15;

	/**
	 * Initialise accessList. Usually, it should be injected.
	 *
	 * @throws Exception
	 */
	public void pageLoaded() throws Exception {
		AccessListProducer listProducer = new AccessListProducer();
		accessList = listProducer.getList();
	}

	public Access getAccess() {
		return access;
	}

	public void setAccess(Access access) {
		this.access = access;
		rowNoWithCurrentPage++;
	}

	/**
	 * Add a custom column to hold the row no to the table.
	 */
	public BeanModel<Access> getBeanModel() {
		BeanModel<Access> beanModel = beanModelSource.createDisplayModel(Access.class, messages);

		beanModel.add("rowNo", null).label("Row No");
		beanModel.get("os").label("Operating System");
		return beanModel;
	}

	public int getRowNo() {
		int page = grid.getCurrentPage();
		int rowNo = (page - 1) * noOfRowsPerPage + rowNoWithCurrentPage;

		return rowNo;
	}

	public String getMyRowClass() {
		return (getRowNo() % 2 == 0 ? "even" : "odd");
	}

	// A custom formater for accessTime
	public String getAccessTime() {
		SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat(
				"yyyy MMM dd HH:mm:ss");

		return dateFormatter.format(access.getAccessTime());
	}
}
