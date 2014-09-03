package com.chdryra.android.reviewer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Administrator {

	private static Administrator sAdministrator;
	private static ControllerReviewNode sRoot; 
	private static Author sAnonymousAuthor = new Author();

	private Context mContext;
	private Controller mController;
	private Author mCurrentAuthor;
	private ReviewPublisher mPublisher;
	
	private Administrator(Context c) {
		setCurrentAuthor(sAnonymousAuthor);
		mContext = c.getApplicationContext();
		mController = new Controller();
		sRoot = new ControllerReviewNode(FactoryReview.createReviewNode(FactoryReview.createReviewTreeEditable("ROOT")));
	}
	
	public static Administrator get(Context c) {
		if(sAdministrator == null)
			sAdministrator = new Administrator(c);
		
		return sAdministrator;
	}
	
	public ControllerReviewNode unpack(Bundle args) {
		return getController().unpack(args);
	}
	
	public void pack(ControllerReviewNode controller, Intent intent) {
		getController().pack(controller, intent);
	}
	
	public Bundle pack(ControllerReviewNode controller) {
		return getController().pack(controller);
	}
	
	public Controller getController() {
		return mController;
	}
	
	public void setCurrentAuthor(Author author) {
		mCurrentAuthor = author;
		mPublisher = new ReviewPublisher(mCurrentAuthor);
	}
	
	public Author getCurrentAuthor() {
		return mCurrentAuthor;
	}
	
	public ReviewNode publish(Review review) {
		return mPublisher.publish(review);
	}
	
	public GVSocialPlatformList getSocialPlatformList(boolean latest) {
		if(latest)
			return GVSocialPlatformList.getLatest();
		else
			return GVSocialPlatformList.getCurrent();
	}
}
