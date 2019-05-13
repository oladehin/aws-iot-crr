package com.ooladehin.awsiot.registry.models;

public class PersistenceLogic {

	private Boolean isDuplicate;

	public PersistenceLogic() { }
	
	public PersistenceLogic(Boolean isDuplicate) {
		super();
		this.isDuplicate = isDuplicate;
	}

	public Boolean getIsDuplicate() {
		return isDuplicate;
	}

	public void setIsDuplicate(Boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}
	
	
}
