/*
 * Copyright 2019 Olawale Oladehin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package com.ooladehin.awsiot.registry.models;

/***
 * The persistence logic class is responsible for determining if an IoT event is a duplicate event
 * Duplicate events are events that have already been replicated into the target region. This class
 * is used to avoid looping registry events back and forth between regions indefinitely.
 * 
 * For active/passive deployments this logic can simply return false
 * 
 * @author oladehin
 *
 */
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
