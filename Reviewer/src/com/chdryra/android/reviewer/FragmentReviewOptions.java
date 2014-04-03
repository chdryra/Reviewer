package com.chdryra.android.reviewer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.chdryra.android.mygenerallibrary.ImageHelper;
import com.chdryra.android.mygenerallibrary.IntentObjectHolder;
import com.chdryra.android.mygenerallibrary.RandomTextUtils;

public class FragmentReviewOptions extends SherlockFragment {
	private final static String TAG = "ReviewerFinishFragment";
	
	private final static String DIALOG_COMMENT_TAG = "CommentDialog";
	private final static String DIALOG_IMAGE_TAG = "ImageDialog";
	private final static String DIALOG_LOCATION_TAG = "LocationDialog";
	private final static String DIALOG_DATA_TAG = "DataDialog";
	
	public final static String REVIEW_OBJECT = FragmentReviewCreate.REVIEW_OBJECT;		
	public static final String LOCATION_BUTTON = "com.chdryra.android.reviewer.location_button";
	
	public final static int IMAGE_REQUEST = 0;
	public final static int IMAGE_EDIT = 1;
	public final static int LOCATION_REQUEST = 2;
	public final static int LOCATION_EDIT = 3;
	public final static int COMMENT_REQUEST = 4;
	public final static int COMMENT_EDIT = 5;
	public final static int DATA_REQUEST = 6;
	public final static int DATA_EDIT = 7;
	public final static int URL_EDIT = 8;

	public final static int DATA_TABLE_MAX_VALUES = 3;

	private UserReview mUserReview;
	private HelperReviewImage mHelperReviewImage;
	
	private TextView mSubject;
	private RatingBar mRatingBar;
	private TextView mTouchStarsTextView;
	private LinearLayout mCriteriaLayout;
	private String mTouchStarsText;
	
	private ImageButton mAddPhotoImageButton;
	private ImageButton mAddLocationImageButton;
	private ImageButton mAddCommentImageButton;
	private ImageButton mAddDataImageButton;
	
	private TextView mCommentTextView;
	private LinearLayout mDataLinearLayout;
	private TextView mLocationNameTextView;
	private TextView mURLTextView;
	
	private boolean mCriteriaLayoutVisible = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);		
		setRetainInstance(true);
		mUserReview = (UserReview)IntentObjectHolder.getObject(REVIEW_OBJECT);
		if(mUserReview.hasImage())
			mHelperReviewImage = HelperReviewImage.getInstance(mUserReview);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_review_options, container, false);			
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//***Get all view objects***//
		mSubject = (TextView)v.findViewById(R.id.review_subject_text_view);
		mRatingBar = (RatingBar)v.findViewById(R.id.total_rating_bar);
		mTouchStarsTextView = (TextView)v.findViewById(R.id.stars_touch_text_view);
		mCriteriaLayout = (LinearLayout)v.findViewById(R.id.linear_layout_criteria_rating_bars);
		
		mAddPhotoImageButton = (ImageButton)v.findViewById(R.id.add_photo_image_button);	
		mAddLocationImageButton = (ImageButton)v.findViewById(R.id.add_location_image_button);
		mAddCommentImageButton = (ImageButton)v.findViewById(R.id.add_comment_image_button);	
		mAddDataImageButton = (ImageButton)v.findViewById(R.id.add_data_image_button);
		
		mCommentTextView = (TextView)v.findViewById(R.id.comment_text_view);
		mDataLinearLayout= (LinearLayout)v.findViewById(R.id.data_table_linear_layout);
		
		mLocationNameTextView = (TextView)v.findViewById(R.id.location_name_text_view);
		mURLTextView = (TextView)v.findViewById(R.id.url_text_view);
		
		//***Subject Heading***//
		mSubject.setText(mUserReview.getTitle().get());
	
		//***Total Rating Bar***//
		mRatingBar.setRating(mUserReview.getRating().get());
		int numCriteria = mUserReview.getCriteria().size();
		mTouchStarsText = numCriteria == 0? getResources().getString(R.string.text_view_no_criteria) : getResources().getString(R.string.text_view_touch_criteria);
		mTouchStarsTextView.setText(mTouchStarsText);
		if(numCriteria > 0) {
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
		}
	
		
		//***Criteria Rating Bars***//
		ReviewNodeCollection criteria = mUserReview.getCriteria();
		boolean dark = false;
		for(ReviewNode c : criteria) {
			View criteriaView = getSherlockActivity().getLayoutInflater().inflate(R.layout.criterion_row_stars_small, null);
			
			criteriaView.setBackgroundResource(dark == true? android.R.drawable.divider_horizontal_bright: android.R.drawable.divider_horizontal_dark);
			dark = !dark;
			
			TextView criterionTextView = (TextView)criteriaView.findViewById(R.id.criterion_name_text_view);				
			RatingBar ratingBar = (RatingBar)criteriaView.findViewById(R.id.criterion_rating_bar);		
			
			criterionTextView.setText(c.getTitle().get());
			criterionTextView.setTextColor(mTouchStarsTextView.getTextColors().getDefaultColor());
			ratingBar.setRating(c.getRating().get());
			ratingBar.setIsIndicator(true);
			ratingBar.setFocusable(false);
			mCriteriaLayout.addView(criteriaView);
		}
		
		if(numCriteria > 0) {
			View divider = getSherlockActivity().getLayoutInflater().inflate(R.layout.horizontal_divider, null);
			mCriteriaLayout.addView(divider);
		}
		
		//***Get display metrics to ensure square buttons***//
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getSherlockActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int maxWidth = Math.min(displaymetrics.widthPixels, displaymetrics.heightPixels) / 2;				
		int maxHeight = maxWidth;
		
		//***Image image button***//
		mAddPhotoImageButton.getLayoutParams().height = maxHeight;
		mAddPhotoImageButton.getLayoutParams().width = maxWidth;
		mAddPhotoImageButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				if (!mUserReview.hasImage())
					requestImageCaptureIntent();
				else
					showImageEditDialog();			
			}
		});
		setImageButtonImage();
		
		//***Location image button***//
		mAddLocationImageButton.getLayoutParams().height = maxHeight;
		mAddLocationImageButton.getLayoutParams().width = maxWidth;		
		mAddLocationImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {	
				if (!mUserReview.hasLocation())
					requestLocationFindIntent();
				else
					showLocationEditDialog();
			}
		});
		setLocationButtonImage();
		
		//***Comment Image Button***//
		mAddCommentImageButton.getLayoutParams().height = maxHeight;
		mAddCommentImageButton.getLayoutParams().width = maxWidth;		
		mAddCommentImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!mUserReview.hasComment())
					requestCommentMakeIntent();
				else
					showCommentEditDialog();
			}
		});
		
		//***Comment Text View***//
		mCommentTextView.getLayoutParams().height = maxHeight;
		mCommentTextView.getLayoutParams().width = maxWidth;		
		if(mUserReview.getComment() != null) {
			ReviewCommentFormatter formatter = new ReviewCommentFormatter(mUserReview.getComment());
			mCommentTextView.setText(formatter.getHeadline());
			setVisibleGoneView(mCommentTextView, mAddCommentImageButton);
		} else
			setVisibleGoneView(mAddCommentImageButton, mCommentTextView);
		
		mCommentTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mAddCommentImageButton.performClick();
			}
		});
	
		//***Data Image Button***//
		mAddDataImageButton.getLayoutParams().height = maxHeight;
		mAddDataImageButton.getLayoutParams().width = maxWidth;		
		mAddDataImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!mUserReview.hasFacts())
					requestFactsAddIntent();
				else
					showDataEditDialog();
				
			}
		});

		//***Comment Text View***//
		mDataLinearLayout.getLayoutParams().height = maxHeight;
		mDataLinearLayout.getLayoutParams().width = maxWidth;

		if(mUserReview.hasFacts()) {
			updateDataTable();
			setVisibleGoneView(mDataLinearLayout, mAddDataImageButton);
		} else
			setVisibleGoneView(mAddDataImageButton, mDataLinearLayout);
		mDataLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mAddDataImageButton.performClick();
			}
		});

		mURLTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showURLEditDialog();
			}
		});
		
		updateLocationNameDisplay();

		return v;
	}

	private void requestCommentMakeIntent() {
		packageReview();
		startActivityForResult(new Intent(getSherlockActivity(), ActivityReviewComment.class), COMMENT_REQUEST);
	}
	
	private void requestFactsAddIntent() {
		packageReview();
		startActivityForResult(new Intent(getSherlockActivity(), ActivityReviewData.class), DATA_REQUEST);
	}
	
	private void requestLocationFindIntent() {
		packageReview();
		startActivityForResult(new Intent(getSherlockActivity(), ActivityReviewLocation.class), LOCATION_REQUEST);
	}
	
	private void requestImageCaptureIntent() {
		if(mHelperReviewImage == null)
			mHelperReviewImage = HelperReviewImage.getInstance(mUserReview);
		
		//Set up image file
		try {
			mHelperReviewImage.createNewImageFile();
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
			e.printStackTrace();
			return;
		}
		File imageFile = new File(mHelperReviewImage.getImageFilePath());
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
	
	private void showImageEditDialog() {
		showDialog(new DialogImageFragment(), IMAGE_EDIT, DIALOG_IMAGE_TAG);
	}

	private void showLocationEditDialog() {
		showDialog(new DialogLocationFragment(), LOCATION_EDIT, DIALOG_LOCATION_TAG);
	}

	private void showCommentEditDialog() {
		showDialog(new DialogCommentFragment(), COMMENT_EDIT, DIALOG_COMMENT_TAG);
	}

	private void showDataEditDialog() {
		showDialog(new DialogDataFragment(), DATA_EDIT, DIALOG_DATA_TAG);
	}

	private void showURLEditDialog() {
	}
	
	private void showDialog(DialogBasicFragment dialog, int requestCode, String tag) {
		packageReview();
		dialog.setTargetFragment(FragmentReviewOptions.this, requestCode);
		dialog.show(getFragmentManager(), tag);
	}

	private void packageReview() {
		IntentObjectHolder.addObject(REVIEW_OBJECT, mUserReview);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_review_options, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_review_done:
			break;
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getSherlockActivity()) != null) {
				packageReview();
				Intent intent = NavUtils.getParentActivityIntent(getSherlockActivity()); 
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
				        	String path = ImageHelper.getRealPathFromURI(getSherlockActivity(), uri);
				        	mHelperReviewImage.setImageFilePath(path);
				        }
				     
				        if(mHelperReviewImage.bitmapExists())
				        {
				        	if(isCamera) {
					    		Uri imageUri = Uri.fromFile(new File(mHelperReviewImage.getImageFilePath()));
					        	getSherlockActivity().sendBroadcast(
					        			new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageUri));
				        	}
				        	updateReviewImage();
				        }
						break;		
					case Activity.RESULT_CANCELED:
						if(!mHelperReviewImage.bitmapExists())
							mHelperReviewImage.deleteImageFile();
						break;
					default:
						break;
				}
				break;
			
			case IMAGE_EDIT:
				switch (resultCode) {
					case Activity.RESULT_OK:
						requestImageCaptureIntent();
						break;
					case DialogImageFragment.RESULT_DELETE:
						deleteReviewImage();
						break;	
					default:
						break;
				}
				break;
							
			case LOCATION_REQUEST:
				mUserReview = data.getParcelableExtra(REVIEW_OBJECT);
				if(resultCode == FragmentReviewLocation.RESULT_DELETE)
					deleteLocation();
				updateLocationNameDisplay();
				break;

			default:
				break;

			case LOCATION_EDIT:
				switch (resultCode) {
					case Activity.RESULT_OK:
						requestLocationFindIntent();
						break;
					case DialogImageFragment.RESULT_DELETE:
						mUserReview = data.getParcelableExtra(REVIEW_OBJECT);
						deleteLocation();
						updateLocationNameDisplay();
						break;
					case DialogImageFragment.CAPTION_CHANGED:
						mUserReview = data.getParcelableExtra(REVIEW_OBJECT);
						updateLocationNameDisplay();
						break;
					default:
						break;
				}
				break;

			case COMMENT_REQUEST:
				switch (resultCode) {
					case Activity.RESULT_OK:
						mUserReview = data.getParcelableExtra(REVIEW_OBJECT);
						updateComment();	
						break;
					case FragmentReviewComment.RESULT_DELETE:
						mUserReview = data.getParcelableExtra(REVIEW_OBJECT);
						deleteComment();
						break;		
					default:
						break;
				}
				break;
				
			case COMMENT_EDIT:
				switch (resultCode) {
					case Activity.RESULT_OK:
						requestCommentMakeIntent();
						break;
					case DialogCommentFragment.RESULT_DELETE:
						mUserReview = data.getParcelableExtra(REVIEW_OBJECT);
						deleteComment();
						break;		
					default:
						break;
				}
				break;
				
			case DATA_REQUEST:
				mUserReview = data.getParcelableExtra(REVIEW_OBJECT);
				updateDataTable();	
				break;
				
			case DATA_EDIT:
				switch (resultCode) {
					case Activity.RESULT_OK:
						requestFactsAddIntent();
						break;
					case DialogDataFragment.RESULT_DELETE:
						mUserReview = data.getParcelableExtra(REVIEW_OBJECT);
						deleteData();
						break;		
					default:
						break;
				}
				break;
					
//			case DATE_EDIT:
//				mUserReview.setDate((Date)data.getSerializableExtra(REVIEW_DATE));
//				mUserReview.setDateWithTime(data.getBooleanExtra(REVIEW_DATE_INC_TIME, false));
//				updateDateDisplay();
//				break;		
		}
	}

	private void setImageButtonImage() {
		if( mUserReview.hasImage() )
			mAddPhotoImageButton.setImageBitmap(mUserReview.getImage().getBitmap());
		else
			mAddPhotoImageButton.setImageResource(R.drawable.ic_menu_camera);
	}
	
	private void setLocationButtonImage() {		
		if(mUserReview.hasLocation() && mUserReview.getLocation().hasMapSnapshot())
			mAddLocationImageButton.setImageBitmap(mUserReview.getLocation().getMapSnapshot());
		else
			mAddLocationImageButton.setImageResource(R.drawable.ic_menu_mylocation);
	}
	
	private void deleteLocation() {
		mAddLocationImageButton.setImageResource(R.drawable.ic_menu_mylocation);
		mUserReview.deleteLocation();
	}
	
	private void updateReviewImage() {
        mHelperReviewImage.setReviewImage(getSherlockActivity(), mAddPhotoImageButton);
	}
	
	private void deleteReviewImage() {
		mHelperReviewImage.deleteImage();
		setImageButtonImage();
	}
	
	private void deleteComment() {
		mUserReview.deleteCommentIncludingCriteria();
		updateComment();
	}
	
	private void updateComment() {
		ReviewCommentFormatter formatter = new ReviewCommentFormatter(mUserReview.getComment());
		String headline = formatter.getHeadline();
		mCommentTextView.setText(headline);

		//Have to ellipsise here as can't get it to work in XML
		int maxLines = RandomTextUtils.getMaxNumberLines(mCommentTextView);
		mCommentTextView.setMaxLines(maxLines > 1? maxLines - 1 : 1);
		mCommentTextView.setEllipsize(TextUtils.TruncateAt.END);
		
		if(headline == null)
			setVisibleGoneView(mAddCommentImageButton, mCommentTextView);
		else
			setVisibleGoneView(mCommentTextView, mAddCommentImageButton);
	}	
	
	private void updateDataTable() {
		if(!mUserReview.hasFacts()) {
			setVisibleGoneView(mAddDataImageButton, mDataLinearLayout);
			return;
		}
				
		mDataLinearLayout.removeAllViews();
		setVisibleGoneView(mDataLinearLayout, mAddDataImageButton);
		int i = 0;
		for(Datum datum: mUserReview.getFacts()) {
			FrameLayout labelRow = (FrameLayout)getSherlockActivity().getLayoutInflater().inflate(R.layout.data_table_label_row, null);
			FrameLayout valueRow = (FrameLayout)getSherlockActivity().getLayoutInflater().inflate(R.layout.data_table_value_row, null);
			
			TextView label = (TextView)labelRow.findViewById(R.id.data_table_label_text_view);
			TextView value = (TextView)valueRow.findViewById(R.id.data_table_value_text_view);			
			
			label.setText(datum.getLabel());
			value.setText(datum.getValue());
			
			mDataLinearLayout.addView(labelRow);
			mDataLinearLayout.addView(valueRow);
			
			++i;
			if(i == DATA_TABLE_MAX_VALUES)
				break;
		}
	}
	
	private void deleteData() {
		mUserReview.deleteFacts();
		updateDataTable();
	}
	
	private void setVisibleGoneView(View visibleView, View goneView) {
		visibleView.setVisibility(View.VISIBLE);
		goneView.setVisibility(View.GONE);
	}
	
	private void updateLocationNameDisplay() {
		String locationName = null;
		if(mUserReview.hasLocation() && mUserReview.getLocation().hasName())
			locationName = "@" + mUserReview.getLocation().getShortenedName();
		
		mLocationNameTextView.setText(locationName);
	}
}
