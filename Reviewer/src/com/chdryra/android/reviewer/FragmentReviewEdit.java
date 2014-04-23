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
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.ImageHelper;
import com.chdryra.android.mygenerallibrary.IntentObjectHolder;
import com.chdryra.android.mygenerallibrary.RandomTextUtils;
import com.chdryra.android.reviewer.RDProsCons.ProConIterator;

public class FragmentReviewEdit extends SherlockFragment {
	private final static String TAG = "ReviewerFinishFragment";
	
	private final static String DIALOG_COMMENT_TAG = "CommentDialog";
	private final static String DIALOG_IMAGE_TAG = "ImageDialog";
	private final static String DIALOG_LOCATION_TAG = "LocationDialog";
	private final static String DIALOG_DATA_TAG = "DataDialog";
	private final static String DIALOG_URL_TAG = "URLDialog";
	private final static String DIALOG_CHILD_TAG = "ChildDialog";
	
	public static final String LOCATION_BUTTON = "com.chdryra.android.reviewer.location_button";
	
	public final static int IMAGE_REQUEST = 0;
	public final static int IMAGE_EDIT = 1;
	public final static int LOCATION_REQUEST = 2;
	public final static int LOCATION_EDIT = 3;
	public final static int COMMENT_REQUEST = 4;
	public final static int COMMENT_EDIT = 5;
	public final static int FACTS_REQUEST = 6;
	public final static int FACTS_EDIT = 7;
	public final static int URL_EDIT = 8;
	public final static int DATE_EDIT = 9;
	public final static int CHILDREN_REQUEST = 10;
	public final static int CHILDREN_EDIT = 11;
	public final static int PROSCONS_REQUEST = 12;
	public final static int PROSCONS_EDIT = 13;

	public final static int DATA_TABLE_MAX_VALUES = 1;

	private final static SimpleDateFormat mDateFormat = 
			new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
	
	private ControllerReviewNode mController;
	private ControllerReviewNodeChildren mChildrenController;
	
	private HelperReviewImage mHelperReviewImage;
	
	private ClearableEditText mSubjectEditText;
	private RatingBar mTotalRatingBar;
	private TextView mNumChildrenTextView;
	private LinearLayout mChildrenLayout;
	private boolean mChildrenLayoutVisible = true;
	private ImageButton mCalcAverageRatingButton;
	
	private ImageButton mAddPhotoImageButton;
	private ImageButton mAddLocationImageButton;
	private ImageButton mAddCommentImageButton;
	private ImageButton mAddFactsImageButton;
	private ImageButton mAddProsImageButton;
	private ImageButton mAddConsImageButton;
	
	private TextView mCommentTextView;
	private TextView mProsTextView;
	private TextView mConsTextView;
	private TextView mLocationTextView;
	private LinearLayout mFactsLinearLayout;
	private TextView mURLTextView;

	int mProTextColour;
	int mConTextColour;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		mController = Controller.unpack(getActivity().getIntent().getExtras());
		if(mController == null)
			mController = Controller.addNewReviewInProgress();

		mChildrenController = mController.getChildrenController();
		
		if(mController.hasImage())
			mHelperReviewImage = HelperReviewImage.getInstance(mController.getID());

		mProTextColour = getResources().getColor(R.color.Chartreuse);
		mConTextColour = getResources().getColor(R.color.Crimson);

		setHasOptionsMenu(true);		
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_review_edit, container, false);			
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//***Get all view objects***//
		mSubjectEditText = (ClearableEditText)v.findViewById(R.id.review_subject_edit_text);
		mTotalRatingBar = (RatingBar)v.findViewById(R.id.total_rating_bar);
		mCalcAverageRatingButton = (ImageButton)v.findViewById(R.id.children_avg_button);
		mNumChildrenTextView = (TextView)v.findViewById(R.id.num_children_text_view);
		mChildrenLayout = (LinearLayout)v.findViewById(R.id.linear_layout_criteria_rating_bars);
		
		mAddPhotoImageButton = (ImageButton)v.findViewById(R.id.add_photo_image_button);	
		mAddLocationImageButton = (ImageButton)v.findViewById(R.id.add_location_image_button);
		mAddCommentImageButton = (ImageButton)v.findViewById(R.id.add_comment_image_button);	
		mAddFactsImageButton = (ImageButton)v.findViewById(R.id.add_data_image_button);
		mAddProsImageButton = (ImageButton)v.findViewById(R.id.add_pros_image_button);	
		mAddConsImageButton = (ImageButton)v.findViewById(R.id.add_cons_image_button);
		
		mCommentTextView = (TextView)v.findViewById(R.id.comment_text_view);
		mLocationTextView = (TextView)v.findViewById(R.id.location_text_view);
		mProsTextView = (TextView)v.findViewById(R.id.pros_text_view);
		mConsTextView = (TextView)v.findViewById(R.id.cons_text_view);
		mFactsLinearLayout= (LinearLayout)v.findViewById(R.id.data_table_linear_layout);
		
		//mLocationTextView = (TextView)v.findViewById(R.id.location_text_view);
		//mDateTextView = (TextView)v.findViewById(R.id.date_text_view);
		mURLTextView = (TextView)v.findViewById(R.id.url_text_view);
		//mFactsTextView = (TextView)v.findViewById(R.id.facts_text_view);
		

		mSubjectEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				mController.setTitle(s.toString());
			}
		});

		mTotalRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				if(fromUser) {
					mController.setRating(rating);
					setTotalRatingIsUser();
				}
			}
		});
		
		mCalcAverageRatingButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mController.isReviewRatingAverage())
					setTotalRatingIsUser();
				else if(mChildrenController.size() > 0)
					setTotalRatingIsAverage();
			}
		});

		mNumChildrenTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showProConEditDialog();
			}
		});

		mNumChildrenTextView.setOnLongClickListener(new View.OnLongClickListener() {			
			@Override
			public boolean onLongClick(View v) {
				requestChildrenDefineIntent();
				return true;
			}
		});

		mChildrenLayout.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				requestChildrenDefineIntent();		
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
				if (mController.hasImage())
					showImageEditDialog();			
				else
					mAddPhotoImageButton.performLongClick();
			}
		});

		mAddPhotoImageButton.setOnLongClickListener(new View.OnLongClickListener() {			
			@Override
			public boolean onLongClick(View v) {
				requestImageCaptureIntent();
				return true;
			}
		});
		
		//***Location image button***//
		mAddLocationImageButton.getLayoutParams().height = maxHeight/2;
		mAddLocationImageButton.getLayoutParams().width = maxWidth;	
		mAddLocationImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {	
				if (mController.hasLocation())
					showLocationEditDialog();
				else
					mAddLocationImageButton.performLongClick();
			}
		});
		
		mAddLocationImageButton.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {	
				requestLocationFindIntent();
				return true;
			}
		});
		
		//***Location Text View***//
		mLocationTextView.getLayoutParams().height = maxHeight/2;
		mLocationTextView.getLayoutParams().width = maxWidth;		
		mLocationTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mAddLocationImageButton.performClick();
			}
		});
		
		mLocationTextView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				return mAddLocationImageButton.performLongClick();
			}
		});

		
		//***Comment Image Button***//
		mAddCommentImageButton.getLayoutParams().height = maxHeight;
		mAddCommentImageButton.getLayoutParams().width = maxWidth;		
		mAddCommentImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mController.hasComment())
					showCommentEditDialog();
				else
					mAddCommentImageButton.performLongClick();
			}
		});

		mAddCommentImageButton.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				requestCommentMakeIntent();
				return true;
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
		
		mCommentTextView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				return mAddCommentImageButton.performLongClick();
			}
		});
		
		//***Data Image Button***//
		mAddFactsImageButton.getLayoutParams().height = maxHeight/2;
		mAddFactsImageButton.getLayoutParams().width = maxWidth;		
		mAddFactsImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mController.hasFacts())
					showFactsEditDialog();					
				else
					mAddFactsImageButton.performLongClick();
				
			}
		});

		mAddFactsImageButton.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				requestFactsAddIntent();
				return true;
			}
		});

		//***Facts Text View***//
		mFactsLinearLayout.getLayoutParams().height = maxHeight/2;
		mFactsLinearLayout.getLayoutParams().width = maxWidth;
		mFactsLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mAddFactsImageButton.performClick();
			}
		});
		
		mFactsLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				return mAddFactsImageButton.performLongClick();
			}
		});
		
		//***Pros Image Button***//
		mAddProsImageButton.getLayoutParams().height = maxHeight/2;
		mAddProsImageButton.getLayoutParams().width = maxWidth;		
		mAddProsImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				requestProsConsAddIntent();
			}
		});
		
		mAddProsImageButton.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				return mAddProsImageButton.performClick();
			}
		});
		
		//***Pros Text View***//
		mProsTextView.getLayoutParams().height = maxHeight/2;
		mProsTextView.getLayoutParams().width = maxWidth;		
		mProsTextView.setTextColor(mProTextColour);
		mProsTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mAddProsImageButton.performClick();
			}
		});
		
		mProsTextView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				return mAddProsImageButton.performLongClick();
			}
		});
		
		//***Cons Image Button***//
		mAddConsImageButton.getLayoutParams().height = maxHeight/2;
		mAddConsImageButton.getLayoutParams().width = maxWidth;		
		mAddConsImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				requestProsConsAddIntent();
			}
		});
		
		mAddConsImageButton.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				return mAddConsImageButton.performClick();
			}
		});
		
		//***Cons Text View***//
		mConsTextView.getLayoutParams().height = maxHeight/2;
		mConsTextView.getLayoutParams().width = maxWidth;
		mConsTextView.setTextColor(mConTextColour);
		mConsTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mAddConsImageButton.performClick();
			}
		});
		
		mConsTextView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				return mAddConsImageButton.performLongClick();
			}
		});
		
		
		//***URL Text View***//
		mURLTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showURLEditDialog();
			}
		});

		mURLTextView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				mURLTextView.performClick();
				return true;
			}
		});

		updateUI();
		
		return v;
	}

	private void updateUI() {
		updateSubjectText();
		updateRatingBar();
		updateRatingIsAverageButton();
		updateNumChildrenText();
		updateChildrenLayout();
		updateCommentHeadline();
		updateLocationButtonImage();
		updateImageButtonImage();
		updateDataTable();
		updateLocationDisplay();
		updateURLDisplay();		
		updateFactsDisplay();
		updateProsConsDisplay();
	}

	private void updateSubjectText() {
		mSubjectEditText.setText(mController.getTitle());
	}
	
	private void updateRatingBar() {
		mTotalRatingBar.setRating(mController.getRating());
	}
	
	private void updateNumChildrenText() {
		int numChildren = mChildrenController.size();
		String text = getActivity().getString(R.string.text_view_number_children, numChildren);
		mNumChildrenTextView.setText(text);
	}

	private void updateRatingIsAverageButton() {
		int visibility = View.INVISIBLE;
		boolean enabled = false;
		if(mChildrenController.size() > 0) {
			visibility = View.VISIBLE;
			enabled = true;
		}
		
		mCalcAverageRatingButton.setVisibility(visibility);
		mCalcAverageRatingButton.setEnabled(enabled);
		
		if(mController.isReviewRatingAverage())
			setTotalRatingIsAverage();
		else
			setTotalRatingIsUser();
	}

	private void updateChildrenLayoutVisibility() {
		if(mChildrenLayoutVisible)
			mChildrenLayout.setVisibility(View.VISIBLE);
		else
			mChildrenLayout.setVisibility(View.GONE);	
	}
	
	private void updateChildrenLayout() {
		mChildrenLayout.removeAllViews();
		boolean dark = false;
		for(String id : mChildrenController.getIDs()) {
			View reviewView = getSherlockActivity().getLayoutInflater().inflate(R.layout.criterion_row_stars_small, null);
			
			reviewView.setBackgroundResource(dark == true? android.R.drawable.divider_horizontal_bright: android.R.drawable.divider_horizontal_dark);
			dark = !dark;
			
			TextView criterionTextView = (TextView)reviewView.findViewById(R.id.criterion_name_text_view);				
			RatingBar ratingBar = (RatingBar)reviewView.findViewById(R.id.criterion_rating_bar);		
			
			criterionTextView.setText(mChildrenController.getTitle(id));
			criterionTextView.setTextColor(mSubjectEditText.getTextColors().getDefaultColor());
			ratingBar.setRating(mChildrenController.getRating(id));
			ratingBar.setIsIndicator(true);
			ratingBar.setFocusable(false);
			mChildrenLayout.addView(reviewView);
		}
		
		if(mChildrenController.size() > 0) {
			View divider = getSherlockActivity().getLayoutInflater().inflate(R.layout.horizontal_divider, null);
			mChildrenLayout.addView(divider);
			mChildrenLayoutVisible = true;
		}	
		
		updateChildrenLayoutVisibility();
	}
	
	private void updateImageButtonImage() {
		if( mController.hasImage() )
			mAddPhotoImageButton.setImageBitmap(mController.getImageBitmap());
		else
			mAddPhotoImageButton.setImageResource(R.drawable.ic_menu_camera);
	}
	
	private void updateLocationButtonImage() {		
		if(mController.hasMapSnapshot())
			mAddLocationImageButton.setImageBitmap(mController.getMapSnapshot());
		else
			mAddLocationImageButton.setImageResource(R.drawable.ic_menu_mylocation);
	}
	
	private void updateReviewImage() {
        mHelperReviewImage.setReviewImage(getSherlockActivity(), mAddPhotoImageButton);
	}
	
	private void updateCommentHeadline() {
		if(!mController.hasComment()) {
			setVisibleGoneView(mAddCommentImageButton, mCommentTextView);
			return;
		}

		switchImageButtonToTextView(mCommentTextView, mAddCommentImageButton, mController.getCommentHeadline());
	}	
	
	private void updateProsConsDisplay() {
		if(!mController.hasProsCons()) {
			setVisibleGoneView(mAddProsImageButton, mProsTextView);
			setVisibleGoneView(mAddConsImageButton, mConsTextView);
			return;
		}
		
		
		if(mController.hasPros())
			switchImageButtonToTextView(mProsTextView, mAddProsImageButton, mController.getPros().get(0));
		else
			setVisibleGoneView(mAddProsImageButton, mProsTextView);
		
		if(mController.hasCons())
			switchImageButtonToTextView(mConsTextView, mAddConsImageButton, mController.getCons().get(0));
		else
			setVisibleGoneView(mAddConsImageButton, mConsTextView);
	}
	
	private void switchImageButtonToTextView(TextView textView, ImageButton imageButton, String text) {
		textView.setText(text);
		
		//Have to ellipsise here as can't get it to work in XML
		int maxLines = RandomTextUtils.getMaxNumberLines(textView);
		textView.setMaxLines(maxLines > 1? maxLines - 1 : 1);
		textView.setEllipsize(TextUtils.TruncateAt.END);
		
		setVisibleGoneView(textView, imageButton);
	}
	
	private void updateDataTable() {
		if(!mController.hasFacts()) {
			setVisibleGoneView(mAddFactsImageButton, mFactsLinearLayout);
			return;
		}
				
		mFactsLinearLayout.removeAllViews();
		setVisibleGoneView(mFactsLinearLayout, mAddFactsImageButton);
		int i = 0;
		LinkedHashMap<String, String> factMap = mController.getFacts();
		for(Entry<String, String> entry : factMap.entrySet()) {
			FrameLayout labelRow = (FrameLayout)getSherlockActivity().getLayoutInflater().inflate(R.layout.data_table_label_row, null);
			FrameLayout valueRow = (FrameLayout)getSherlockActivity().getLayoutInflater().inflate(R.layout.data_table_value_row, null);
			
			TextView label = (TextView)labelRow.findViewById(R.id.data_table_label_text_view);
			TextView value = (TextView)valueRow.findViewById(R.id.data_table_value_text_view);			
			
			label.setText(entry.getKey());
			value.setText(entry.getValue());
			
			mFactsLinearLayout.addView(labelRow);
			mFactsLinearLayout.addView(valueRow);
			
			++i;
			if(i == DATA_TABLE_MAX_VALUES)
				break;
		}
	}

	private void updateFactsDisplay() {
		StringBuilder facts = new StringBuilder();
		
		if(!mController.hasFacts())
			facts.append(getResources().getString(R.string.text_view_facts_hint));
		else
			facts.append(mController.getNumberOfFacts() + " facts");
					
		//mFactsTextView.setText(facts.toString());
	}

	private void updateLocationDisplay() {
		if(!mController.hasLocation()) {
			setVisibleGoneView(mAddLocationImageButton, mLocationTextView);
			return;
		}

		StringBuilder location = new StringBuilder("@");
		
		if(!mController.hasLocationName())
			location.append(getResources().getString(R.string.text_view_location_hint));
		else
			location.append(mController.getShortLocationName());
					
		switchImageButtonToTextView(mLocationTextView, mAddLocationImageButton, location.toString());
	}
	
	private void updateURLDisplay() {
		if(mController.hasURL())
			mURLTextView.setText(mController.getURLShortenedString());
		else
			mURLTextView.setText(getResources().getString(R.string.text_view_link_hint));
	}

	private void setTotalRatingIsAverage() {
		mController.setReviewRatingAverage(true);
		mCalcAverageRatingButton.setImageResource(android.R.drawable.ic_input_add);
		updateRatingBar();
	}
	
	private void setTotalRatingIsUser() {
		mController.setReviewRatingAverage(false);
		mCalcAverageRatingButton.setImageResource(android.R.drawable.ic_menu_add);
		updateRatingBar();
	}
	
	private void setVisibleGoneView(View visibleView, View goneView) {
		visibleView.setVisibility(View.VISIBLE);
		goneView.setVisibility(View.GONE);
	
	}	
	private <T> void requestIntent(Class<T> c, int requestCode) {
		Intent i = new Intent(getSherlockActivity(), c);
		Controller.pack(mController, i);
		startActivityForResult(i, requestCode);
	}
	
	private void requestChildrenDefineIntent() {
		requestIntent(ActivityReviewChildren.class, CHILDREN_REQUEST);
	}
	
	private void requestCommentMakeIntent() {
		requestIntent(ActivityReviewComment.class, COMMENT_REQUEST);
	}
	
	private void requestFactsAddIntent() {
		requestIntent(ActivityReviewFacts.class, FACTS_REQUEST);
	}

	private void requestProsConsAddIntent() {
		requestIntent(ActivityReviewProsCons.class, PROSCONS_REQUEST);
	}
	
	private void requestLocationFindIntent() {
		IntentObjectHolder.addObject(LOCATION_BUTTON, mAddLocationImageButton);
		requestIntent(ActivityReviewLocation.class, LOCATION_REQUEST);
	}
	
	private void requestImageCaptureIntent() {
		if(mHelperReviewImage == null)
			mHelperReviewImage = HelperReviewImage.getInstance(mController.getID());
		
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

	private void showFactsEditDialog() {
		showDialog(new DialogDataFragment(), FACTS_EDIT, DIALOG_DATA_TAG);
	}

	private void showURLEditDialog() {
		showDialog(new DialogURLFragment(), URL_EDIT, DIALOG_URL_TAG);
	}
	
	private void showProConEditDialog() {
		DialogChildAdd dialog = new DialogChildAdd();
		dialog.setTargetFragment(FragmentReviewEdit.this, CHILDREN_EDIT);
		dialog.setArguments(Controller.pack(mController));
		dialog.show(getFragmentManager(), DIALOG_CHILD_TAG);
	}
	
	private void showDialog(DialogBasicFragment dialog, int requestCode, String tag) {
		dialog.setTargetFragment(FragmentReviewEdit.this, requestCode);
		dialog.setArguments(Controller.pack(mController));
		dialog.show(getFragmentManager(), tag);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_review_edit, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_review_done:
			break;
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getSherlockActivity()) != null) {
				Intent i = NavUtils.getParentActivityIntent(getSherlockActivity());
				Controller.pack(mController, i);
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
			
			case CHILDREN_REQUEST:
				updateRatingIsAverageButton();
				updateNumChildrenText();
				updateChildrenLayout();	
				break;
			
			case CHILDREN_EDIT:
				updateRatingIsAverageButton();
				updateNumChildrenText();
				updateChildrenLayout();	
				break;
				
			case LOCATION_REQUEST:
				updateLocationButtonImage();
				updateLocationDisplay();
				break;
			
			case LOCATION_EDIT:
				switch (resultCode) {
					case Activity.RESULT_OK:
						requestLocationFindIntent();
						break;
					case DialogLocationFragment.RESULT_DELETE:
						updateLocationButtonImage();
						updateLocationDisplay();
					default:
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
				
			case FACTS_REQUEST:
				updateDataTable();	
				updateFactsDisplay();
				break;
				
			case FACTS_EDIT:
				switch (resultCode) {
					case Activity.RESULT_OK:
						requestFactsAddIntent();
						break;
					default:
						updateDataTable();
						updateFactsDisplay();
						break;
				}
				break;

			case PROSCONS_REQUEST:
				updateProsConsDisplay();	
				break;
				
			case PROSCONS_EDIT:
				switch (resultCode) {
					case Activity.RESULT_OK:
						requestProsConsAddIntent();
						break;
					default:
						updateProsConsDisplay();
						break;
				}
				break;
					
			case URL_EDIT:
				updateURLDisplay();
				break;
		}
	}
}
