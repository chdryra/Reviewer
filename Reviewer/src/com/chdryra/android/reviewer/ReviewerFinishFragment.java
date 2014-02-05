package com.chdryra.android.reviewer;

import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class ReviewerFinishFragment extends SherlockFragment {
	private final static String TAG = "ReviewerFinishFragment";
	private final static String COMMENT_DIALOG_TAG = "CommentDialog";
	private final static String SOCIAL_DIALOG_TAG = "SocialDialog";
	public final static String REVIEW_OBJECT = "com.chdryra.android.reviewer.review_object";
	public final static int COMMENT_EDIT = 0;
	public final static int SOCIAL_EDIT = 1;
	public static final String COMMENT_TEXT = "com.chdryra,android,reviewer.comment_text";

	private Review mReview;

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
		
	View v = inflater.inflate(R.layout.fragment_review_finish_page, container, false);	
	
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
		
		mLinearLayout.addView(criteriaView);
	}
	
	mRatingBar = (RatingBar)v.findViewById(R.id.total_rating_bar_finish_page);
	mRatingBar.setRating(mReview.getRating());
	mRatingBar.setOnTouchListener(new View.OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			mCriteriaLayoutVisible = !mCriteriaLayoutVisible;
			if(mCriteriaLayoutVisible)
				mLinearLayout.setVisibility(View.VISIBLE);
			else
				mLinearLayout.setVisibility(View.GONE);
											
			return false;
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
			dialog.setTargetFragment(ReviewerFinishFragment.this, 0);
			Bundle args = new Bundle();
			args.putSerializable(COMMENT_TEXT, mComment.getText().toString());
			dialog.setArguments(args);
			dialog.show(getFragmentManager(), COMMENT_DIALOG_TAG);			
		}
	});
	
	mAddPhotoButton = (ImageButton)v.findViewById(R.id.add_photo_button);
	mAddLocationButton = (ImageButton)v.findViewById(R.id.add_location_button);
	
	return v;
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
	
			default:
				break;
			};
		}
			
		if (resultCode == CommentDialogFragment.RESULT_DELETE_COMMENT) {
			updateComment(null);
		}		
	
	}

	private void updateComment(String comment) {
		mComment.setText(comment);
	}	
}
