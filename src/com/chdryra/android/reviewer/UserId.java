/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import java.util.UUID;

public class UserId {
	private UUID mID;
	
	private UserId() {
		mID = UUID.randomUUID();
	}
	
	public static UserId generateID() {
		return new UserId();
	}
	
	public boolean equals(UserId userId) {
		return mID.equals(userId.mID);
	}
	
	public String toString() {
		return mID.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null || obj.getClass() != getClass())
			return false;
		
		UserId		 objId = (UserId)obj;
		if(this.mID.equals(objId.mID))
			return true;
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return mID.hashCode();
	}
}