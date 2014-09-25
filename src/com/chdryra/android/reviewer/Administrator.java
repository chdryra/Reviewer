/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Administrator {

	private static Administrator sAdministrator;
	private static Author sAnonymousAuthor = new Author("Anon");

	private ReviewNode mRoot;
	private Controller mController;
	private Author mCurrentAuthor;

	private Administrator() {
		setCurrentAuthor(sAnonymousAuthor);
		mRoot = FactoryReview.createReviewNode(FactoryReview.createReviewTreeEditable("ROOT"));
	}
	
	public static Administrator get(Context c) {
		if(sAdministrator == null)
			sAdministrator = new Administrator();
		
		return sAdministrator;
	}

	public GVReviewOverviewList getFeed() {
		return new ControllerReviewNodeCollection(mRoot.getChildren()).getGridViewablePublished();
	}
	
	public ControllerReviewNode createNewReviewInProgress() {
		mController = new Controller();
		return mController.getReviewInProgress();
	}
	
	public ControllerReviewNode unpack(Bundle args) {
		if(mController != null)
			return mController.unpack(args);
		else
			return null;
	}
	
	public void pack(ControllerReviewNode controller, Intent intent) {
		if(mController != null)
			mController.pack(controller, intent);
	}
	
	public Bundle pack(ControllerReviewNode controller) {
		if(mController != null)
			return mController.pack(controller);
		else
			return null;
	}
	
	public void setCurrentAuthor(Author author) {
		mCurrentAuthor = author;
		
	}

	public void publishReviewInProgress() {
		ReviewTreePublisher publisher = new ReviewTreePublisher(mCurrentAuthor);
		ReviewNode published = mController.getReviewInProgress().publish(publisher);
		mRoot.addChild(published);
	}
	
	public GVSocialPlatformList getSocialPlatformList(boolean latest) {
		if(latest)
			return GVSocialPlatformList.getLatest();
		else
			return GVSocialPlatformList.getCurrent();
	}
}
