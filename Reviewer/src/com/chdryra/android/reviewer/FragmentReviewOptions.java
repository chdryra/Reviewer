package com.chdryra.android.reviewer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelUuid;
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
	private final static String DIALOG_URL_TAG = "URLDialog";
	private final static String DIALOG_DATE_TAG = "DateDialog";
	
	public static final String LOCATION_BUTTON = "com.chdryra.android.reviewer.location_button";
	public static final String REVIEW_ID = "com.chdryra.android.reviewer.review_id";
	
	public final static int IMAGE_REQUEST = 0;
	public final static int IMAGE_EDIT = 1;
	public final static int LOCATION_REQUEST = 2;
	public final static int LOCATION_EDIT = 3;
	public final static int COMMENT_REQUEST = 4;
	public final static int COMMENT_EDIT = 5;
	public final static int DATA_REQUEST = 6;
	public final static int DATA_EDIT = 7;
	public final static int URL_EDIT = 8;
	public final static int DATE_EDIT = 9;

	public final static int DATA_TABLE_MAX_VALUES = 1;

	private final static SimpleDateFormat mDateFormat = 
			new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
	
	private Controller mController = Controller.getInstance();
	private Controller mChildrenController;
	private RDId mReviewID;
	
	private HelperReviewImage mHelperReviewImage;
	
	private TextView mSubject;
	private RatingBar mRatingBar;
	private TextView mTouchStarsTextView;
	private LinearLayout mChildrenLayout;
	private boolean mChildrenLayoutVisible = true;
	private String mTouchStarsText;
	
	private ImageButton mAddPhotoImageButton;
	private ImageButton mAddLocationImageButton;
	private ImageButton mAddCommentImageButton;
	private ImageButton mAddDataImageButton;
	
	private TextView mCommentTextView;
	private LinearLayout mDataLinearLayout;
	private TextView mURLTextView;
	private TextView mLocationDateTextView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		mReviewID = getActivity().getIntent().getParcelableExtra(REVIEW_ID);
		mChildrenController = mController.getChildReviewsControllerFor(mReviewID);
		if(mController.hasImage(mReviewID))
			mHelperReviewImage = HelperReviewImage.getInstance(mReviewID);
		setHasOptionsMenu(true);		
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_review_options, container, false);			
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//***Get all view objects***//
		mSubject = (TextView)v.findViewById(R.id.review_subject_text_view);
		mRatingBar = (RatingBar)v.findViewById(R.id.total_rating_bar);
		mTouchStarsTextView = (TextView)v.findViewById(R.id.stars_touch_text_view);
		mChildrenLayout = (LinearLayout)v.findViewById(R.id.linear_layout_criteria_rating_bars);
		
		mAddPhotoImageButton = (ImageButton)v.findViewById(R.id.add_photo_image_button);	
		mAddLocationImageButton = (ImageButton)v.findViewById(R.id.add_location_image_button);
		mAddCommentImageButton = (ImageButton)v.findViewById(R.id.add_comment_image_button);	
		mAddDataImageButton = (ImageButton)v.findViewById(R.id.add_data_image_button);
		
		mCommentTextView = (TextView)v.findViewById(R.id.comment_text_view);
		mDataLinearLayout= (LinearLayout)v.findViewById(R.id.data_table_linear_layout);
		
		mLocationDateTextView = (TextView)v.findViewById(R.id.date_location_text_view);
		mURLTextView = (TextView)v.findViewById(R.id.url_text_view);
		
		//***Total Rating Bar***//
		mRatingBar.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_UP) {
				mChildrenLayoutVisible = !mChildrenLayoutVisible;
				if(mChildrenLayoutVisible)
					mChildrenLayout.setVisibility(View.VISIBLE);
				else
					mChildrenLayout.setVisibility(View.GONE);
				}
				return true;
			}
		});
			
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
				if (!mController.hasImage(mReviewID))
					requestImageCaptureIntent();
				else
					showImageEditDialog();			
			}
		});
		
		//***Location image button***//
		mAddLocationImageButton.getLayoutParams().height = maxHeight;
		mAddLocationImageButton.getLayoutParams().width = maxWidth;		
		mAddLocationImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {	
				if (!mController.hasLocation(mReviewID))
					requestLocationFindIntent();
				else
					showLocationEditDialog();
			}
		});
		
		//***Comment Image Button***//
		mAddCommentImageButton.getLayoutParams().height = maxHeight;
		mAddCommentImageButton.getLayoutParams().width = maxWidth;		
		mAddCommentImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!mController.hasComment(mReviewID))
					requestCommentMakeIntent();
				else
					showCommentEditDialog();
			}
		});
		
		//***Comment Text View***//
		mCommentTextView.getLayoutParams().height = maxHeight;
		mCommentTextView.getLayoutParams().width = maxWidth;		
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
				if (!mController.hasFacts(mReviewID))
					requestFactsAddIntent();
				else
					showDataEditDialog();
				
			}
		});

		//***Comment Text View***//
		mDataLinearLayout.getLayoutParams().height = maxHeight;
		mDataLinearLayout.getLayoutParams().width = maxWidth;
		mDataLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mAddDataImageButton.performClick();
			}
		});
		
		//***URL Text View***//
		mURLTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showURLEditDialog();
			}
		});
		
		//***Location Date Text View***//
		mLocationDateTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDateEditDialog();
			}
		});

		
		updateUI();
		
		return v;
	}

	private void updateUI() {
		updateSubjectText();
		updateRatingBar();
		updateTouchStarsText();
		updateChildrenLayout();
		updateCommentHeadline();
		updateLocationButtonImage();
		updateImageButtonImage();
		updateDataTable();
		updateLocationAndDateDisplay();
		updateURLDisplay();		
	}

	private void updateSubjectText() {
		mSubject.setText(mController.getTitle(mReviewID));
	}
	
	private void updateRatingBar() {
		mRatingBar.setRating(mController.getRating(mReviewID));
	}
	
	private void updateTouchStarsText() {
		int numChildren = mChildrenController.size();
		mTouchStarsText = numChildren == 0? getResources().getString(R.string.text_view_no_criteria) : getResources().getString(R.string.text_view_touch_criteria);
		mTouchStarsTextView.setText(mTouchStarsText);		
	}

	private void updateChildrenLayout() {
		mChildrenLayout.removeAllViews();
		boolean dark = false;
		for(RDId id : mChildrenController.getIDs()) {
			View reviewView = getSherlockActivity().getLayoutInflater().inflate(R.layout.criterion_row_stars_small, null);
			
			reviewView.setBackgroundResource(dark == true? android.R.drawable.divider_horizontal_bright: android.R.drawable.divider_horizontal_dark);
			dark = !dark;
			
			TextView criterionTextView = (TextView)reviewView.findViewById(R.id.criterion_name_text_view);				
			RatingBar ratingBar = (RatingBar)reviewView.findViewById(R.id.criterion_rating_bar);		
			
			criterionTextView.setText(mChildrenController.getTitle(id));
			criterionTextView.setTextColor(mTouchStarsTextView.getTextColors().getDefaultColor());
			ratingBar.setRating(mChildrenController.getRating(id));
			ratingBar.setIsIndicator(true);
			ratingBar.setFocusable(false);
			mChildrenLayout.addView(reviewView);
		}
		
		if(mChildrenController.size() > 0) {
			View divider = getSherlockActivity().getLayoutInflater().inflate(R.layout.horizontal_divider, null);
			mChildrenLayout.addView(divider);
		}	
	}
	
	private void updateImageButtonImage() {
		if( mController.hasImage(mReviewID) )
			mAddPhotoImageButton.setImageBitmap(mController.getImageBitmap(mReviewID));
		else
			mAddPhotoImageButton.setImageResource(R.drawable.ic_menu_camera);
	}
	
	private void updateLocationButtonImage() {		
		if(mController.hasMapSnapshot(mReviewID))
			mAddLocationImageButton.setImageBitmap(mController.getMapSnapshot(mReviewID));
		else
			mAddLocationImageButton.setImageResource(R.drawable.ic_menu_mylocation);
	}
	
	private void updateReviewImage() {
        mHelperReviewImage.setReviewImage(getSherlockActivity(), mAddPhotoImageButton);
	}
	
	private void updateCommentHeadline() {
		if(!mController.hasComment(mReviewID)) {
			setVisibleGoneView(mAddCommentImageButton, mCommentTextView);
			return;
		}
		
		mCommentTextView.setText(mController.getCommentHeadline(mReviewID));

		//Have to ellipsise here as can't get it to work in XML
		int maxLines = RandomTextUtils.getMaxNumberLines(mCommentTextView);
		mCommentTextView.setMaxLines(maxLines > 1? maxLines - 1 : 1);
		mCommentTextView.setEllipsize(TextUtils.TruncateAt.END);
		
		setVisibleGoneView(mCommentTextView, mAddCommentImageButton);
	}	
	
	private void updateDataTable() {
		if(!mController.hasFacts(mReviewID)) {
			setVisibleGoneView(mAddDataImageButton, mDataLinearLayout);
			return;
		}
				
		mDataLinearLayout.removeAllViews();
		setVisibleGoneView(mDataLinearLayout, mAddDataImageButton);
		int i = 0;
		LinkedHashMap<String, String> factMap = mController.getFacts(mReviewID);
		for(Entry<String, String> entry : factMap.entrySet()) {
			FrameLayout labelRow = (FrameLayout)getSherlockActivity().getLayoutInflater().inflate(R.layout.data_table_label_row, null);
			FrameLayout valueRow = (FrameLayout)getSherlockActivity().getLayoutInflater().inflate(R.layout.data_table_value_row, null);
			
			TextView label = (TextView)labelRow.findViewById(R.id.data_table_label_text_view);
			TextView value = (TextView)valueRow.findViewById(R.id.data_table_value_text_view);			
			
			label.setText(entry.getKey());
			value.setText(entry.getValue());
			
			mDataLinearLayout.addView(labelRow);
			mDataLinearLayout.addView(valueRow);
			
			++i;
			if(i == DATA_TABLE_MAX_VALUES)
				break;
		}
	}
	
	private void updateLocationAndDateDisplay() {
		SimpleDateFormat format = mDateFormat;
		StringBuilder locationDate = new StringBuilder("@");
		
		if(!mController.hasLocation(mReviewID) && !mController.hasDate(mReviewID)) {
			locationDate.append(getResources().getString(R.string.text_view_location_date_hint));
		} else {
			if(mController.hasLocationName(mReviewID)) {
				locationDate.append(mController.getShortLocationName(mReviewID));
				if(mController.hasDate(mReviewID)) {
					locationDate.append(", ");
					locationDate.append(format.format(mController.getDate(mReviewID)));
				}
			} else {
				locationDate.append(format.format(mController.getDate(mReviewID)));
			}
		}
					
		mLocationDateTextView.setText(locationDate.toString());
	}
	
	private void updateURLDisplay() {
		if(mController.hasURL(mReviewID))
			mURLTextView.setText(mController.getURLShortenedString(mReviewID));
		else
			mURLTextView.setText(getResources().getString(R.string.text_view_link));
	}
	
	private void setVisibleGoneView(View visibleView, View goneView) {
		visibleView.setVisibility(View.VISIBLE);
		goneView.setVisibility(View.GONE);
	}
	
	private Bundle packReview() {
		Bundle args = new Bundle();
		args.putParcelable(REVIEW_ID, mReviewID);
		return args;
	}	
	
	private <T> void requestIntent(Class<T> c, int requestCode) {
		Intent i = new Intent(getSherlockActivity(), c);
		i.putExtras(packReview());
		startActivityForResult(i, requestCode);
	}
	
	private void requestCommentMakeIntent() {
		requestIntent(ActivityReviewComment.class, COMMENT_REQUEST);
	}
	
	private void requestFactsAddIntent() {
		requestIntent(ActivityReviewData.class, DATA_REQUEST);
	}
	
	private void requestLocationFindIntent() {
		IntentObjectHolder.addObject(LOCATION_BUTTON, mAddLocationImageButton);
		requestIntent(ActivityReviewLocation.class, LOCATION_REQUEST);
	}
	
	private void requestImageCaptureIntent() {
		if(mHelperReviewImage == null)
			mHelperReviewImage = HelperReviewImage.getInstance(mReviewID);
		
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
		showDialog(new DialogURLFragment(), URL_EDIT, DIALOG_URL_TAG);
	}
	
	private void showDateEditDialog() {
		showDialog(new DialogDateFragment(), DATE_EDIT, DIALOG_DATE_TAG);
	}
	
	private void showDialog(DialogBasicFragment dialog, int requestCode, String tag) {
		dialog.setTargetFragment(FragmentReviewOptions.this, requestCode);
		dialog.setArguments(packReview());
		dialog.show(getFragmentManager(), tag);
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
				Intent i = NavUtils.getParentActivityIntent(getSherlockActivity());
				i.putExtras(packReview());
				NavUtils.navigateUpTo(getSherlockActivity(), i);
			}
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode != IMAGE_REQUEST && resultCode == Activity.RESULT_CANCELED)
			return;
		
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
					default:
						updateImageButtonImage();
				}
				break;
							
			case LOCATION_REQUEST:
				updateLocationButtonImage();
				updateLocationAndDateDisplay();
				break;
			
			case LOCATION_EDIT:
				switch (resultCode) {
					case Activity.RESULT_OK:
						requestLocationFindIntent();
						break;
					case DialogLocationFragment.RESULT_DELETE:
						updateLocationButtonImage();
					default:
						updateLocationAndDateDisplay();
						break;
				}
				break;

			case COMMENT_REQUEST:
				updateCommentHeadline();
				break;
				
			case COMMENT_EDIT:
				switch (resultCode) {
					case Activity.RESULT_OK:
						requestCommentMakeIntent();
						break;
					default:
						updateCommentHeadline();
						break;
				}
				break;
				
			case DATA_REQUEST:
				updateDataTable();	
				break;
				
			case DATA_EDIT:
				switch (resultCode) {
					case Activity.RESULT_OK:
						requestFactsAddIntent();
						break;
					default:
						updateDataTable();
						break;
				}
				break;
					
			case DATE_EDIT:
				updateLocationAndDateDisplay();
				break;
				
			case URL_EDIT:
				updateURLDisplay();
				break;
		}
	}
}
