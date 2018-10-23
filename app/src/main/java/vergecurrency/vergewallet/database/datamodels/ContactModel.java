package vergecurrency.vergewallet.contracts.datamodels;

import android.provider.BaseColumns;

public final class ContactModel {
	
	private ContactModel() {}
	
	
	public String getContactAddress() {
		return contactAddress;
	}
	
	public void setContactAddress(String contactAddress) { this.contactAddress = contactAddress; }
	
	public String getContactName() {
		return contactName;
	}
	
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	public int getContactId() {	return contactId;	}
	
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	
	private int contactId;
	private String contactAddress;
	private String contactName;
	
}
