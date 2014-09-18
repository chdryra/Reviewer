package com.chdryra.android.reviewer;

import java.util.HashMap;

import android.app.Activity;
import android.app.DialogFragment;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

public class ReviewDataOptions {
	private final static String DIALOG_TAG_TAG = "TagDialog";
	private final static String DIALOG_COMMENT_TAG = "CommentDialog";
	private final static String DIALOG_IMAGE_TAG = "ImageDialog";
	private final static String DIALOG_LOCATION_TAG = "LocationDialog";
	private final static String DIALOG_URL_TAG = "URLDialog";
	private final static String DIALOG_CHILD_TAG = "ChildDialog";
	private final static String DIALOG_FACTS_TAG = "FactsDialog";
	
	public final static int IMAGE_REQUEST = 10;
	public final static int IMAGE_ADD = 11;
	public final static int LOCATION_REQUEST = 20;
	public final static int LOCATION_ADD = 21;
	public final static int COMMENT_REQUEST = 30;
	public final static int COMMENT_ADD = 31;
	public final static int FACTS_REQUEST = 40;
	public final static int FACTS_ADD = 41;
	public final static int URL_REQUEST = 50;
	public final static int URL_ADD = 51;
	public final static int CHILDREN_REQUEST = 60;
	public final static int CHILDREN_ADD = 61;
	public final static int TAGS_REQUEST = 80;
	public final static int TAGS_ADD = 81;

	
	private HashMap<GVType, ReviewDataOption> mReviewDataOptions;
	private static  ReviewDataOptions sReviewDataOptions;
	
	private ReviewDataOptions() {
		mReviewDataOptions = new HashMap<GVReviewDataList.GVType, ReviewDataOptions.ReviewDataOption>();
		mReviewDataOptions.put(GVType.TAGS, 
				new ReviewDataOption(GVType.TAGS, DialogTagAddFragment.class, TAGS_ADD, DIALOG_TAG_TAG, ActivityReviewTags.class, TAGS_REQUEST, new VHTagView()));
		mReviewDataOptions.put(GVType.CRITERIA, 
				new ReviewDataOption(GVType.CRITERIA, DialogChildAddFragment.class, CHILDREN_ADD, DIALOG_CHILD_TAG, ActivityReviewChildren.class, CHILDREN_REQUEST, new VHReviewNodeSubjectRating()));
		mReviewDataOptions.put(GVType.COMMENTS, 
				new ReviewDataOption(GVType.COMMENTS, DialogCommentAddFragment.class, COMMENT_ADD, DIALOG_COMMENT_TAG, ActivityReviewComments.class, COMMENT_REQUEST, new VHCommentView()));
		mReviewDataOptions.put(GVType.IMAGES, 
				new ReviewDataOption(GVType.IMAGES, DialogImageEditFragment.class, IMAGE_ADD, DIALOG_IMAGE_TAG, ActivityReviewImages.class, IMAGE_REQUEST, new VHImageView()));
		mReviewDataOptions.put(GVType.FACTS, 
				new ReviewDataOption(GVType.FACTS, DialogFactAddFragment.class, FACTS_ADD, DIALOG_FACTS_TAG, ActivityReviewFacts.class, FACTS_REQUEST, new VHFactView()));
		mReviewDataOptions.put(GVType.LOCATIONS, 
				new ReviewDataOption(GVType.LOCATIONS, DialogLocationFragment.class, LOCATION_ADD, DIALOG_LOCATION_TAG, ActivityReviewLocations.class, LOCATION_REQUEST, new VHLocationView(true)));
		mReviewDataOptions.put(GVType.URLS, 
				new ReviewDataOption(GVType.URLS, DialogURLFragment.class, URL_ADD, DIALOG_URL_TAG, ActivityReviewURLs.class, URL_REQUEST, new VHUrlView()));
	
	}

	public static ReviewDataOptions getOptions() {
		if(sReviewDataOptions == null)
			sReviewDataOptions = new ReviewDataOptions();
		
		return sReviewDataOptions;
	}
	
	public static ReviewDataOption get(GVType dataType) {
		return getOptions().mReviewDataOptions.get(dataType);
	}
	
	class ReviewDataOption {
		
		private GVType mDataType;
		private Class<? extends DialogFragment> mDialogClass;
		private Class<? extends Activity> mActivityClass;
		private int mActivityRequestCode;
		private int mDialogRequestCode;
		private String mDialogTag;
		private ViewHolder mViewHolder;

		private ReviewDataOption(GVType dataType, Class<? extends DialogFragment> dialogClass, int dialogRequestCode, String dialogTag, 
				Class<? extends Activity> activityClass, int activityRequestCode, ViewHolder viewHolder) {
			mDataType = dataType;
			mDialogClass = dialogClass;
			mDialogRequestCode = dialogRequestCode;
			mDialogTag = dialogTag;
			mActivityClass = activityClass;
			mActivityRequestCode = activityRequestCode;
			mViewHolder = viewHolder;
		}
	
		public GVType getDataType() {
			return mDataType;
		}
		
		public DialogFragment getDialogFragment() {
			try {
				return mDialogClass.newInstance();
			} catch (java.lang.InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		public Class<? extends Activity> getActivityRequestClass() {
			return mActivityClass;
		}
		
		public int getActivityRequestCode() {
			return mActivityRequestCode;
		}
		
		public int getDialogRequestCode() {
			return mDialogRequestCode;
		}
		
		public String getDialogTag() {
			return mDialogTag;
		}
		
		public String getDatumString() {
			return mDataType.getDatumString();
		}
		
		public String getDataString() {
			return mDataType.getDataString();
		}
		
		public ViewHolder getViewHolder() {
			return mViewHolder;
		}
	}
}
