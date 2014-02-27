package com.chdryra.android.reviewer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class ReviewOptionsFragment extends SherlockFragment {
	private final static String TAG = "ReviewerFinishFragment";
	
	private final static String DIALOG_COMMENT_TAG = "CommentDialog";
	private final static String DIALOG_IMAGE_TAG = "ImageDialog";
	private final static String DIALOG_LOCATION_TAG = "LocationDialog";
	
	public final static String REVIEW_OBJECT = "com.chdryra.android.reviewer.review_object";
	public final static String REVIEW_SUBJECT = "com.chdryra.android.reviewer.review_subject";
	public final static String REVIEW_IMAGE = "com.chdryra.android.reviewer.review_image";
	public final static String REVIEW_COMMENT = "com.chdryra.android.reviewer.review_comment";
	public static final String COMMENT_TEXT = "com.chdryra,android,reviewer.comment_text";
	public static final String IMAGE_FILE = "com.chdryra,android,reviewer.image_file";
	public static final String IMAGE_LATLNG = "com.chdryra,android,reviewer.image_latlng";
	public static final String REVIEW_LATLNG = "com.chdryra,android,reviewer.review_latlng";
	public static final String LOCATION_BUTTON = "com.chdryra,android,reviewer.location_button";

	public final static int CAPTION_MAX_LINES = 5;
	
	public final static int IMAGE_REQUEST = 0;
	public final static int IMAGE_EDIT = 1;
	public final static int LOCATION_REQUEST = 2;
	public final static int LOCATION_EDIT = 3;
	public final static int COMMENT_REQUEST = 4;
	public final static int COMMENT_EDIT = 5;
	public final static int INFO_REQUEST = 4;
	public final static int INFO_EDIT = 5;
	
	private Review mReview;
	private ReviewImageHandler mReviewImageHandler;
	
	private TextView mSubject;
	private RatingBar mRatingBar;
	private LinearLayout mCriteriaLayout;
	
	private ImageButton mAddPhotoImageButton;
	private ImageButton mAddLocationImageButton;
	private ImageButton mAddCommentImageButton;
	private ImageButton mAddInfoImageButton;
	
	private TextView mCommentTextView;
	private TextView mInfoTextView;
	
	private boolean mCriteriaLayoutVisible = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);		
		setRetainInstance(true);
		
		mReview = (Review)IntentObjectHolder.getObject(ReviewCreateFragment.REVIEW_OBJECT);
		if(mReview.hasImage())
			mReviewImageHandler = ReviewImageHandler.getInstance(mReview);		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_review_options, container, false);			
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//***Get all view objects***//
		mSubject = (TextView)v.findViewById(R.id.review_subject_finish_page);
		mRatingBar = (RatingBar)v.findViewById(R.id.total_rating_bar_finish_page);
		mCriteriaLayout = (LinearLayout)v.findViewById(R.id.linear_layout_criteria_rating_bars);
		
		mAddPhotoImageButton = (ImageButton)v.findViewById(R.id.add_photo_image_button);	
		mAddLocationImageButton = (ImageButton)v.findViewById(R.id.add_location_image_button);
		mAddCommentImageButton = (ImageButton)v.findViewById(R.id.add_comment_image_button);	
		mAddInfoImageButton = (ImageButton)v.findViewById(R.id.add_info_image_button);
		
		mCommentTextView = (TextView)v.findViewById(R.id.comment_text_view);
		mInfoTextView = (TextView)v.findViewById(R.id.info_text_view);
		
		//***Subject Heading***//
		mSubject.setText(mReview.getSubject());
	
		//***Total Rating Bar***//
		mRatingBar.setRating(mReview.getRating());
		mRatingBar.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_UP) {
				mCriteriaLayoutVisible = !mCriteriaLayoutVisible;
				if(mCriteriaLayoutVisible)
					mCriteriaLayout.setVisibility(View.VISIBLE);
				else
					mCriteriaLayout.setVisibility(View.GONE);
				}
				return true;
			}
		});
	
		//***Criteria Rating Bars***//
		Iterator<Criterion> it = mReview.getCriteriaList().getCriterionHashMap().values().iterator();
		if(it.hasNext())
			mCriteriaLayout.setVisibility(View.VISIBLE);
		boolean dark = false;
		while (it.hasNext()) {
			Criterion c = it.next();
			View criteriaView = getSherlockActivity().getLayoutInflater().inflate(R.layout.criterion_row_stars_small, null);
			
			criteriaView.setBackgroundResource(dark == true? android.R.drawable.divider_horizontal_bright: android.R.drawable.divider_horizontal_dark);
			dark = !dark;
			
			TextView criterionText = (TextView)criteriaView.findViewById(R.id.criterion_name_text_view);				
			RatingBar ratingBar = (RatingBar)criteriaView.findViewById(R.id.criterion_rating_bar);		
			
			criterionText.setText(c.getName());
			ratingBar.setRating(c.getRating());
			ratingBar.setIsIndicator(true);
			ratingBar.setFocusable(false);
			mCriteriaLayout.addView(criteriaView);
		}
	
		//***Get display metrics to ensure square buttons***//
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getSherlockActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int maxWidth = Math.min(displaymetrics.widthPixels, displaymetrics.heightPixels) / 2;				
		int maxHeight = maxWidth;
		
		//***Image image button***//
		mAddPhotoImageButton.getLayoutParams().height = maxWidth;
		mAddPhotoImageButton.getLayoutParams().width = maxHeight;
		mAddPhotoImageButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				if (!mReview.hasImage()) {
					requestImageCaptureIntent();
				} else
					showImageEditDialog();			
			}
		});
		setImageButtonImage();
		
		//***Location image button***//
		mAddLocationImageButton.getLayoutParams().height = maxWidth;
		mAddLocationImageButton.getLayoutParams().width = maxHeight;		
		mAddLocationImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {	
				if (!mReview.hasLatLng()) {
					requestLocationFindIntent();
				} else
					showLocationEditDialog();
			}
		});
		setLocationButtonImage();
		
		//***Comment Text View***//
		mAddCommentImageButton.getLayoutParams().height = maxWidth;
		mAddCommentImageButton.getLayoutParams().width = maxHeight;		
		mAddCommentImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {	
				if (!mReview.hasComment()) {
					requestCommentMakeIntent();
				} else
					showCommentEditDialog();
			}
		});
		
		mCommentTextView.getLayoutParams().height = maxWidth;
		mCommentTextView.getLayoutParams().width = maxHeight;		
		String comment = mReview.getCommentHeadline();
		if(comment != null) {
			mCommentTextView.setText(comment);
			setVisibleGoneView(mCommentTextView, mAddCommentImageButton);
		} else
			setVisibleGoneView(mAddCommentImageButton, mCommentTextView);
		mCommentTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mAddCommentImageButton.performClick();
			}
		});

		//***Comment Text View***//
		mInfoTextView.getLayoutParams().height = maxWidth;
		mInfoTextView.getLayoutParams().width = maxHeight;		
		setVisibleGoneView(mAddInfoImageButton, mInfoTextView);
		
		return v;
	}

	private void requestCommentMakeIntent() {
		IntentObjectHolder.addObject(REVIEW_OBJECT, mReview);
		Intent i = new Intent(getSherlockActivity(), ReviewCommentActivity.class);
		startActivityForResult(i, COMMENT_REQUEST);
	}
	
	private void requestImageCaptureIntent() {
		if(mReviewImageHandler == null)
			mReviewImageHandler = ReviewImageHandler.getInstance(mReview);
		
		//Set up image file
		try {
			mReviewImageHandler.createNewImageFile();
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
			e.printStackTrace();
			return;
		}
		File imageFile = new File(mReviewImageHandler.getImageFilePath());
		Uri imageUri = Uri.fromFile(imageFile);		

	    //Create intents
		final List<Intent> cameraIntents = new ArrayList<Intent>();
		final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		final PackageManager packageManager = getSherlockActivity().getPackageManager();
		final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
		for(ResolveInfo res : listCam) {
	        final String packageName = res.activityInfo.packageName;
	        final Intent intent = new Intent(captureIntent);
	        intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
	        intent.setPackage(packageName);
	        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
	        cameraIntents.add(intent);
	    }
        
		final Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");		
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));

        startActivityForResult(chooserIntent, IMAGE_REQUEST);
	}
	
	private void requestLocationFindIntent() {
		Intent i = new Intent(getSherlockActivity(), ReviewLocationActivity.class);
		if (mReview.hasLatLng())					
			i.putExtra(REVIEW_LATLNG, mReview.getLatLng());
		if (mReview.hasImage() && mReviewImageHandler.hasGPSTag())					
			i.putExtra(IMAGE_LATLNG, mReviewImageHandler.getLatLngFromEXIF());
		IntentObjectHolder.addObject(REVIEW_OBJECT, mReview);
		IntentObjectHolder.addObject(LOCATION_BUTTON, mAddLocationImageButton);
		startActivityForResult(i, LOCATION_REQUEST);
	}
	
	private void showImageEditDialog() {
		ImageDialogFragment dialog = new ImageDialogFragment();
		dialog.setTargetFragment(ReviewOptionsFragment.this, IMAGE_EDIT);
		Bundle args = new Bundle();
		args.putParcelable(REVIEW_IMAGE, mReview.getImage());				
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), DIALOG_IMAGE_TAG);
	}

	private void showLocationEditDialog() {
//		LocationDialogFragment dialog = new LocationDialogFragment();
//		dialog.setTargetFragment(ReviewOptionsFragment.this, LOCATION_EDIT);
//		Bundle args = new Bundle();
//		args.putParcelable(REVIEW_IMAGE, mReview.getImage());				
//		dialog.setArguments(args);
//		dialog.show(getFragmentManager(), DIALOG_IMAGE_TAG);
	}
	
	private void showCommentEditDialog() {
		CommentDialogFragment dialog = new CommentDialogFragment();
		dialog.setTargetFragment(ReviewOptionsFragment.this, COMMENT_EDIT);
		Bundle args = new Bundle();
		args.putString(REVIEW_COMMENT, mReview.getCommentIncludingCriteria());				
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), DIALOG_COMMENT_TAG);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_review_finish, menu);
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_review_done:
			break;
		case R.id.menu_item_choose_social:
//			SocialDialogFragment dialog = new SocialDialogFragment();
//			dialog.setTargetFragment(ReviewerFinishFragment.this, 0);
//			Bundle args = new Bundle();
//			args.putSerializable(COMMENT_TEXT, mCommentTextView.getText().toString());
//			dialog.setArguments(args);
//			dialog.show(getFragmentManager(), COMMENT_DIALOG_TAG);
			break;
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getSherlockActivity()) != null) {
				IntentObjectHolder.addObject(REVIEW_OBJECT, mReview);
				Intent intent = NavUtils.getParentActivityIntent(getSherlockActivity()); 
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP); 
				NavUtils.navigateUpTo(getSherlockActivity(), intent);
			}
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode != IMAGE_REQUEST && resultCode == Activity.RESULT_CANCELED) {
			return;
		}
		
		switch (requestCode) {
			
			//Getting image
			case IMAGE_REQUEST:
				switch (resultCode) {
					case Activity.RESULT_OK:
						final boolean isCamera;
				        if(data == null)
				            isCamera = true;
				        else {
				        	final String action = data.getAction();
				            if(action == null)
				                isCamera = false;
				            else
				                isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				        }
				        
				        if(!isCamera) {
				        	Uri uri = data == null ? null : data.getData();
				        	String path = ImageHandler.getRealPathFromURI(getSherlockActivity(), uri);
				        	mReviewImageHandler.setImageFilePath(path);
				        }
				     
				        if(mReviewImageHandler.bitmapExists())
				        {
				        	if(isCamera) {
					    		Uri imageUri = Uri.fromFile(new File(mReviewImageHandler.getImageFilePath()));
					        	getSherlockActivity().sendBroadcast(
					        			new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageUri));
				        	}
				        	updateReviewImage();
				        }
						break;		
					case Activity.RESULT_CANCELED:
						if(!mReviewImageHandler.bitmapExists())
							mReviewImageHandler.deleteImageFile();
						break;
					default:
						break;
				}
				break;
			
			//Changing image
			case IMAGE_EDIT:
				switch (resultCode) {
					case Activity.RESULT_OK:
						requestImageCaptureIntent();
						break;
					case ImageDialogFragment.RESULT_DELETE:
						deleteReviewImage();
						break;		
					default:
						break;
				}
				break;
			
				
			//Getting location
			case LOCATION_REQUEST:
				mReview = (Review)IntentObjectHolder.getObject(REVIEW_OBJECT);
				if(resultCode == ReviewLocationFragment.RESULT_DELETE_LOCATION)
					deleteLocation();
				break;

			default:
				break;

				//Changing image
			case LOCATION_EDIT:
				switch (resultCode) {
					case Activity.RESULT_OK:
						requestLocationFindIntent();
						break;
					case CommentDialogFragment.RESULT_DELETE:
						deleteLocation();
						break;		
					default:
						break;
				}
				break;
			

			//Editing comments
			case COMMENT_REQUEST:
				mReview = (Review)IntentObjectHolder.getObject(REVIEW_OBJECT);
				switch (resultCode) {
					case Activity.RESULT_OK:
						updateComment();	
						break;
					case ReviewCommentFragment.RESULT_DELETE_COMMENT:
						deleteComment();
						break;		
					default:
						break;
				}
				break;
				
				//Changing image
			case COMMENT_EDIT:
				switch (resultCode) {
					case Activity.RESULT_OK:
						requestCommentMakeIntent();
						break;
					case CommentDialogFragment.RESULT_DELETE:
						deleteComment();
						break;		
					default:
						break;
				}
				break;
			
		}
		
	}

	private void setImageButtonImage() {
		if( mReview.hasImage() ) {
			mAddPhotoImageButton.setImageBitmap(mReview.getImage());
		}
		else
			deleteImageButtonImage();
	}

	private void deleteImageButtonImage() {
		mAddPhotoImageButton.setImageResource(R.drawable.ic_menu_camera);
	}
	
	private void setLocationButtonImage() {		
		if(mReview.hasMapSnapshot()) {
			mAddLocationImageButton.setImageBitmap(mReview.getMapSnapshot());
		}
		else
			deleteLocation();
	}
	
	private void deleteLocation() {
		mAddLocationImageButton.setImageResource(R.drawable.ic_menu_mylocation);
		mReview.deleteLatLng();
	}
	
	private void updateReviewImage() {
        mReviewImageHandler.setReviewImage(getSherlockActivity(), mAddPhotoImageButton);
	}
	
	private void deleteReviewImage() {
		mReviewImageHandler.deleteImage();
		deleteImageButtonImage();
	}
	
	private void deleteComment() {
		mCommentTextView.setText(null);
		setVisibleGoneView(mAddCommentImageButton, mCommentTextView);
	}
	
	private void updateComment() {
		mCommentTextView.setText(mReview.getCommentHeadline());
		setVisibleGoneView(mCommentTextView, mAddCommentImageButton);
	}	
	
	public void setVisibleGoneView(View visibleView, View goneView) {
		visibleView.setVisibility(View.VISIBLE);
		goneView.setVisibility(View.GONE);
	}
}
