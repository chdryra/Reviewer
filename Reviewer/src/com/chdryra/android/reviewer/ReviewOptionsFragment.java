package com.chdryra.android.reviewer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

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
import android.text.format.DateFormat;
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
import com.chdryra.android.reviewer.ReviewData.Datum;

public class ReviewOptionsFragment extends SherlockFragment {
	private final static String TAG = "ReviewerFinishFragment";
	
	private final static String DIALOG_COMMENT_TAG = "CommentDialog";
	private final static String DIALOG_IMAGE_TAG = "ImageDialog";
	private final static String DIALOG_LOCATION_TAG = "LocationDialog";
	private final static String DIALOG_DATA_TAG = "DataDialog";
	private final static String DIALOG_DATE_TAG = "DateDialog";
	
	private final static String NO_CRITERIA = "no criteria";
	private final static String TOGGLE_CRITERIA = "touch stars to toggle criteria";
	
	public final static String REVIEW_OBJECT = "com.chdryra.android.reviewer.review_object";
	public final static String REVIEW_SUBJECT = "com.chdryra.android.reviewer.review_subject";
	public static final String REVIEW_LATLNG = "com.chdryra,android,reviewer.review_latlng";
	public static final String REVIEW_DATE = "com.chdryra,android,reviewer.review_date";
	
	public final static String DIALOG_IMAGE = "com.chdryra.android.reviewer.review_image";
	public final static String DIALOG_IMAGE_CAPTION = "com.chdryra.android.reviewer.review_image_caption";
	public final static String DIALOG_IMAGE_CAPTION_HINT = "com.chdryra.android.reviewer.review_image_caption_hint";
	public final static String DIALOG_COMMENT = "com.chdryra.android.reviewer.review_comment";
	
	public static final String COMMENT_TEXT = "com.chdryra,android,reviewer.comment_text";
	public static final String IMAGE_FILE = "com.chdryra,android,reviewer.image_file";
	public static final String IMAGE_LATLNG = "com.chdryra,android,reviewer.image_latlng";
	public static final String LOCATION_BUTTON = "com.chdryra,android,reviewer.location_button";
	
	public final static int IMAGE_REQUEST = 0;
	public final static int IMAGE_EDIT = 1;
	public final static int LOCATION_REQUEST = 2;
	public final static int LOCATION_EDIT = 3;
	public final static int COMMENT_REQUEST = 4;
	public final static int COMMENT_EDIT = 5;
	public final static int DATA_REQUEST = 6;
	public final static int DATA_EDIT = 7;
	public final static int DATE_EDIT = 8;

	public final static int DATA_TABLE_MAX_VALUES = 3;

	private Review mReview;
	private ReviewImageHandler mReviewImageHandler;
	
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
	private TextView mDateTextView;
	
	private boolean mCriteriaLayoutVisible = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);		
		setRetainInstance(true);
		mReview = (Review)IntentObjectHolder.getObject(ReviewCreateFragment.REVIEW_OBJECT);
		if(mReview.hasImage())
			mReviewImageHandler = ReviewImageHandler.getInstance(mReview);
		mReview.setDate(new Date());
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
		
		mDateTextView = (TextView)v.findViewById(R.id.date_text_view);
		
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
	
		int numCriteria = mReview.getCriteriaList().size();
		mTouchStarsText = numCriteria == 0? NO_CRITERIA : TOGGLE_CRITERIA;
		mTouchStarsTextView.setText(mTouchStarsText);
		
		//***Criteria Rating Bars***//
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
			criterionText.setTextColor(mTouchStarsTextView.getTextColors().getDefaultColor());
			ratingBar.setRating(c.getRating());
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
		mAddPhotoImageButton.getLayoutParams().height = maxWidth;
		mAddPhotoImageButton.getLayoutParams().width = maxHeight;
		mAddPhotoImageButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				if (!mReview.hasImage())
					requestImageCaptureIntent();
				else
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
				if (!mReview.hasLatLng())
					requestLocationFindIntent();
				else
					showLocationEditDialog();
			}
		});
		setLocationButtonImage();
		
		//***Comment Image Button***//
		mAddCommentImageButton.getLayoutParams().height = maxWidth;
		mAddCommentImageButton.getLayoutParams().width = maxHeight;		
		mAddCommentImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!mReview.hasComment())
					requestCommentMakeIntent();
				else
					showCommentEditDialog();
			}
		});
		
		//***Comment Text View***//
		mCommentTextView.getLayoutParams().height = maxWidth;
		mCommentTextView.getLayoutParams().width = maxHeight;		
		if(mReview.getComment() != null) {
			mCommentTextView.setText(mReview.getCommentHeadline());
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
		mAddDataImageButton.getLayoutParams().height = maxWidth;
		mAddDataImageButton.getLayoutParams().width = maxHeight;		
		mAddDataImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!mReview.hasData())
					requestDataAddIntent();
				else
					showDataEditDialog();
				
			}
		});

		//***Comment Text View***//
		mDataLinearLayout.getLayoutParams().height = maxWidth;
		mDataLinearLayout.getLayoutParams().width = maxHeight;

		if(mReview.hasData()) {
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

		mDateTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDateEditDialog();
			}
		});
		
		updateDateDisplay();

		return v;
	}

	private void requestCommentMakeIntent() {
		IntentObjectHolder.addObject(REVIEW_OBJECT, mReview);
		Intent i = new Intent(getSherlockActivity(), ReviewCommentActivity.class);
		startActivityForResult(i, COMMENT_REQUEST);
	}
	
	private void requestDataAddIntent() {
		IntentObjectHolder.addObject(REVIEW_OBJECT, mReview);
		Intent i = new Intent(getSherlockActivity(), ReviewDataActivity.class);
		startActivityForResult(i, DATA_REQUEST);
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
		Bundle args = new Bundle();
		args.putParcelable(DIALOG_IMAGE, mReview.getImage());						
		args.putString(DIALOG_IMAGE_CAPTION, mReview.getImageCaption());
		args.putString(DIALOG_IMAGE_CAPTION_HINT, getResources().getString(R.string.edit_text_image_caption_hint));
		showDialog(new ImageDialogFragment(), IMAGE_EDIT, DIALOG_IMAGE_TAG, args);
	}

	private void showLocationEditDialog() {
		Bundle args = new Bundle();
		args.putParcelable(DIALOG_IMAGE, mReview.getMapSnapshot());
		args.putString(DIALOG_IMAGE_CAPTION, mReview.getLocationName());
		args.putString(DIALOG_IMAGE_CAPTION_HINT, getResources().getString(R.string.edit_text_name_location_hint));		
		showDialog(new ImageDialogFragment(), LOCATION_EDIT, DIALOG_LOCATION_TAG, args);
	}

	private void showCommentEditDialog() {
		Bundle args = new Bundle();
		args.putString(DIALOG_COMMENT, mReview.getCommentIncludingCriteria());						
		showDialog(new CommentDialogFragment(), COMMENT_EDIT, DIALOG_COMMENT_TAG, args);
	}

	private void showDataEditDialog() {
		IntentObjectHolder.addObject(REVIEW_OBJECT, mReview);
		showDialog(new DataDialogFragment(), DATA_EDIT, DIALOG_DATA_TAG, null);
	}

	private void showDateEditDialog() {
		Bundle args = new Bundle();
		args.putSerializable(REVIEW_DATE, mReview.getDate());						
		showDialog(new DateDialogFragment(), DATE_EDIT, DIALOG_DATE_TAG, args);
	}
	
	private void showDialog(BasicDialogFragment dialog, int requestCode, String tag, Bundle args) {
		dialog.setTargetFragment(ReviewOptionsFragment.this, requestCode);
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), tag);
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
			
			case IMAGE_EDIT:
				switch (resultCode) {
					case Activity.RESULT_OK:
						requestImageCaptureIntent();
						break;
					case ImageDialogFragment.RESULT_DELETE:
						deleteReviewImage();
						break;	
					case ImageDialogFragment.CAPTION_CHANGED:
						mReview.setImageCaption(data.getStringExtra(DIALOG_IMAGE_CAPTION));
						break;
					default:
						break;
				}
				break;
							
			case LOCATION_REQUEST:
				mReview = (Review)IntentObjectHolder.getObject(REVIEW_OBJECT);
				if(resultCode == ReviewLocationFragment.RESULT_DELETE_LOCATION)
					deleteLocation();
				updateDateDisplay();
				break;

			default:
				break;

			case LOCATION_EDIT:
				switch (resultCode) {
					case Activity.RESULT_OK:
						requestLocationFindIntent();
						break;
					case ImageDialogFragment.RESULT_DELETE:
						deleteLocation();
						break;
					case ImageDialogFragment.CAPTION_CHANGED:
						mReview.setLocationName(data.getStringExtra(DIALOG_IMAGE_CAPTION));
						updateDateDisplay();
						break;
					default:
						break;
				}
				break;

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
				
			case DATA_REQUEST:
				mReview = (Review)IntentObjectHolder.getObject(REVIEW_OBJECT);
				updateDataTable();	
				break;
				
			case DATA_EDIT:
				switch (resultCode) {
					case Activity.RESULT_OK:
						requestDataAddIntent();
						break;
					case DataDialogFragment.RESULT_DELETE:
						deleteData();
						break;		
					default:
						break;
				}
				break;
					
			case DATE_EDIT:
				mReview.setDate((Date)data.getSerializableExtra(REVIEW_DATE));
				updateDateDisplay();
				break;		
		}
	}

	private void setImageButtonImage() {
		if( mReview.hasImage() ) {
			mAddPhotoImageButton.setImageBitmap(mReview.getImage());
			//mAddPhotoImageButton.setBackgroundColor(getResources().getColor(R.color.DarkGray));
		}
		else
			deleteImageButtonImage();
	}

	private void deleteImageButtonImage() {
		mAddPhotoImageButton.setImageResource(R.drawable.ic_menu_camera);
		//mAddPhotoImageButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
	}
	
	private void setLocationButtonImage() {		
		if(mReview.hasMapSnapshot())
			mAddLocationImageButton.setImageBitmap(mReview.getMapSnapshot());
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
		String headline = mReview.getCommentHeadline();
		mCommentTextView.setText(headline);
		if(headline == null)
			setVisibleGoneView(mAddCommentImageButton, mCommentTextView);
		else
			setVisibleGoneView(mCommentTextView, mAddCommentImageButton);
	}	
	
	private void updateDataTable() {
		if(!mReview.hasData()) {
			setVisibleGoneView(mAddDataImageButton, mDataLinearLayout);
			return;
		}
		
		
		mDataLinearLayout.removeAllViews();
		setVisibleGoneView(mDataLinearLayout, mAddDataImageButton);
		LinkedHashMap<String, Datum> dataMap = mReview.getData().getDataMap();
		Iterator<Datum> it = dataMap.values().iterator();
		int i = 0;
		while(it.hasNext() && i < DATA_TABLE_MAX_VALUES) {
			Datum datum = it.next();
			
			FrameLayout labelRow = (FrameLayout)getSherlockActivity().getLayoutInflater().inflate(R.layout.data_table_label_row, null);
			FrameLayout valueRow = (FrameLayout)getSherlockActivity().getLayoutInflater().inflate(R.layout.data_table_value_row, null);
			
			TextView label = (TextView)labelRow.findViewById(R.id.data_table_label_text_view);
			TextView value = (TextView)valueRow.findViewById(R.id.data_table_value_text_view);			
			
			label.setText(datum.getLabel());
			value.setText(datum.getValue());
			
			mDataLinearLayout.addView(labelRow);
			mDataLinearLayout.addView(valueRow);
			
			++i;
		}

		
	}
	
	private void deleteData() {
		mReview.deleteData();
		updateDataTable();
	}
	
	private void setVisibleGoneView(View visibleView, View goneView) {
		visibleView.setVisibility(View.VISIBLE);
		goneView.setVisibility(View.GONE);
	}
	
	private void updateDateDisplay() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy, HH:mm", Locale.getDefault());
		
		String dateString = "@";
		if(mReview.hasLocationName())
			dateString += mReview.getLocationName() +", ";
		
		dateString += format.format(mReview.getDate());
		mDateTextView.setText(dateString);
	}
}
