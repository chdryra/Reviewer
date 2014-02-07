package com.chdryra.android.reviewer;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.NavUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class ReviewerFinishFragment extends SherlockFragment {
	private final static String TAG = "ReviewerFinishFragment";
	
	private final static String DIALOG_COMMENT_TAG = "CommentDialog";
	private final static String DIALOG_IMAGE_TAG = "ImageDialog";
	private final static String DIALOG_SOCIAL_TAG = "SocialDialog";
	
	public final static String REVIEW_OBJECT = "com.chdryra.android.reviewer.review_object";
	public final static String REVIEW_SUBJECT = "com.chdryra.android.reviewer.review_subject";
	public final static String REVIEW_IMAGE = "com.chdryra.android.reviewer.review_image";
	public static final String COMMENT_TEXT = "com.chdryra,android,reviewer.comment_text";
	
	public final static int COMMENT_EDIT = 0;
	public final static int SOCIAL_EDIT = 1;
	public final static int IMAGE_REQUEST = 2;
	public final static int IMAGE_EDIT = 3;
	public final static int LOCATION_EDIT = 4;
	
	private Review mReview;
	private Uri mImageUri;
	
	private TextView mSubject;
	private RatingBar mRatingBar;
	private LinearLayout mLinearLayout;
	private TextView mComment;
	private ImageButton mAddPhotoButton;
	private ImageButton mAddLocationButton;
	private boolean mCriteriaLayoutVisible = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);		
		setRetainInstance(true);
		mReview = (Review)IntentObjectHolder.getObject(ReviewerDefineFragment.REVIEW_OBJECT);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
	View v = inflater.inflate(R.layout.fragment_review_finish, container, false);	
	
	getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	
	mSubject = (TextView)v.findViewById(R.id.review_subject_finish_page);
	mSubject.setText(mReview.getSubject());

	mLinearLayout = (LinearLayout)v.findViewById(R.id.linear_layout_criteria_rating_bars);
	Iterator<Criterion> it = mReview.getCriteriaList().getCriterionHashMap().values().iterator();
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
		mLinearLayout.addView(criteriaView);
	}
	
	mRatingBar = (RatingBar)v.findViewById(R.id.total_rating_bar_finish_page);
	mRatingBar.setRating(mReview.getRating());
	mRatingBar.setOnTouchListener(new View.OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction() == MotionEvent.ACTION_UP) {
			mCriteriaLayoutVisible = !mCriteriaLayoutVisible;
			if(mCriteriaLayoutVisible)
				mLinearLayout.setVisibility(View.VISIBLE);
			else
				mLinearLayout.setVisibility(View.GONE);
			}
			return true;
		}
	});
		
	
	mComment = (TextView)v.findViewById(R.id.comment_text_view);
	String comment = mReview.getCommentIncludingCriteria();
	if(comment != null)
		mComment.setText(comment);
	
	mComment.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			IntentObjectHolder.addObject(REVIEW_OBJECT, mReview);
			CommentDialogFragment dialog = new CommentDialogFragment();
			dialog.setTargetFragment(ReviewerFinishFragment.this, COMMENT_EDIT);
			Bundle args = new Bundle();
			args.putSerializable(COMMENT_TEXT, mComment.getText().toString());
			dialog.setArguments(args);
			dialog.show(getFragmentManager(), DIALOG_COMMENT_TAG);			
		}
	});
	
	
	DisplayMetrics displaymetrics = new DisplayMetrics();
	getSherlockActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
	int maxWidth = Math.min(displaymetrics.widthPixels, displaymetrics.heightPixels) / 2;				
	int maxHeight = maxWidth;

	mAddPhotoButton = (ImageButton)v.findViewById(R.id.add_photo_button);	
	mAddPhotoButton.getLayoutParams().height = maxWidth;
	mAddPhotoButton.getLayoutParams().width = maxHeight;
	mAddPhotoButton.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (mReview.getImage() == null) {
				requestImageCaptureIntent();
			} else {
				ImageDialogFragment dialog = new ImageDialogFragment();
				IntentObjectHolder.addObject(REVIEW_OBJECT, mReview);
				dialog.setTargetFragment(ReviewerFinishFragment.this, IMAGE_EDIT);
				Bundle args = new Bundle();
				args.putParcelable(REVIEW_IMAGE, mReview.getImage());				
				dialog.setArguments(args);
				dialog.show(getFragmentManager(), DIALOG_IMAGE_TAG);
			}
		}
	});
	
	setImageButtonImage();
	
	mAddLocationButton = (ImageButton)v.findViewById(R.id.add_location_button);
	mAddLocationButton.getLayoutParams().height = maxWidth;
	mAddLocationButton.getLayoutParams().width = maxHeight;
	
	mAddLocationButton.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			Intent i = new Intent(getSherlockActivity(), ReviewerLocationActivity.class);
			startActivityForResult(i, LOCATION_EDIT);
		}
	});
	
	setImageButtonImage();
	
	return v;
	}

	private void requestImageCaptureIntent() {
		File imageFile = null;
        try {
            imageFile = ImageCaptureAndProcessingUtils.createImageFile(getSherlockActivity(), mReview.getSubject());
            mImageUri = Uri.fromFile(imageFile);
        } catch (IOException ex) {
        	Log.i(TAG, "File not created:", ex);
        }

        if (imageFile.exists()) {
        	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);	        			        	
        	intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
            startActivityForResult(intent, IMAGE_REQUEST);
        }				
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
//			args.putSerializable(COMMENT_TEXT, mComment.getText().toString());
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
		if (resultCode == Activity.RESULT_CANCELED) {
			return;
		}
		
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case COMMENT_EDIT:
				updateComment((String)data.getSerializableExtra(CommentDialogFragment.EXTRA_COMMENT_STRING));				
				break;
	
			case IMAGE_REQUEST:
				setReviewImage();
				break;
				
			case IMAGE_EDIT:
				requestImageCaptureIntent();
				break;
			default:
				break;
			};
		}
			
		if (resultCode == CommentDialogFragment.RESULT_DELETE_COMMENT) {
			updateComment(null);
		}
		
		if (resultCode == ImageDialogFragment.RESULT_DELETE_IMAGE) {
			deleteReviewImage();
		}
		
	
	}

	private void setImageButtonImage() {
		Bitmap reviewImage = mReview.getImage();
		
		if(reviewImage == null) {
			mAddPhotoButton.setImageResource(R.drawable.ic_menu_camera);
			return;
		} else {
			int maxWidth = mAddPhotoButton.getLayoutParams().width;				
			int maxHeight = mAddPhotoButton.getLayoutParams().height;
			
			Bitmap imageThumbnail = ImageCaptureAndProcessingUtils.resizeImage(reviewImage, maxWidth, maxHeight);	        		        
			mAddPhotoButton.setImageBitmap(imageThumbnail);
		}	
				

	}

	private void setReviewImage() {
		if(mImageUri != null) {
			try
		    {
				Bitmap imageBitmap = Media.getBitmap(getSherlockActivity().getContentResolver(), mImageUri);
				
				int maxWidth = (int)getSherlockActivity().getResources().getDimension(R.dimen.imageMaxWidth);				
				int maxHeight = (int)getSherlockActivity().getResources().getDimension(R.dimen.imageMaxHeight);;
				
				Bitmap reviewImage = ImageCaptureAndProcessingUtils.resizeImage(imageBitmap, maxWidth, maxHeight);
				mReview.setImage(reviewImage);
		    } catch (IOException e)
		    {
		        e.printStackTrace();
		    }
		}
			
		setImageButtonImage();
	}
	
	private void deleteReviewImage() {
		mImageUri = null;
		mReview.setImage(null);
		setImageButtonImage();
	}
	
	private void updateComment(String comment) {
		mComment.setText(comment);
	}	
}
