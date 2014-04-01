package com.chdryra.android.reviewer;

import java.util.LinkedHashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.chdryra.android.mygenerallibrary.IntentObjectHolder;
import com.chdryra.android.myandroidwidgets.ClearableEditText;

public class FragmentReviewCreate extends SherlockFragment{
	private final static String TAG = "ReviewerFragment";
	private final static String DIALOG_CRITERION_TAG = "CriterionDialog";
	public final static String CRITERION = "com.chdryra.android.reviewer.criterion";
	public final static String REVIEW_OBJECT = "com.chdryra.android.reviewer.review_object";
	public final static int CRITERION_EDIT = 0;
	public static enum Result {TOTAL_IS_AVERAGE, TOTAL_IS_USER};
	
	private ReviewNode mReview;
	private ReviewNodeCollection mCriteria = new ReviewNodeCollection();

	private ClearableEditText mSubject;
	private ClearableEditText mCriterionName;
	private ImageButton mAddCriterionButton;
	private ImageButton mCalcAverageRatingButton;
	private ListView mCriteriaListView;
	private RatingBar mTotalRatingBar;
	
	private boolean mTotalRatingIsAverage = false;
	private float mTotalRatingUser;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mReview = (UserReview)IntentObjectHolder.getObject(FragmentReviewOptions.REVIEW_OBJECT);
		setHasOptionsMenu(true);		
		setRetainInstance(true);		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_review_create, container, false);		
		
		mSubject = (ClearableEditText)v.findViewById(R.id.review_subject);

		mAddCriterionButton = (ImageButton)v.findViewById(R.id.criterion_add_button);
		mAddCriterionButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View button) {							
				addCriterion();							
			}
		});

		mCriterionName = (ClearableEditText)v.findViewById(R.id.criterion_add_edit_text);
		mCriterionName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_DONE)
	            	mAddCriterionButton.performClick();	        		

	            return true;
	        }
	    });
		
		mCriteriaListView = (ListView) v.findViewById(R.id.criterion_listview);		
		mCriteriaListView.setAdapter(new CriterionAdaptor(mCriteria));
		mCriteriaListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
		
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v, int pos, long id) {
					showCriterionDialog((Review)parent.getItemAtPosition(pos));
					return true;
			}
		});
				
		mTotalRatingBar = (RatingBar)v.findViewById(R.id.total_rating_bar);
		mTotalRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				if(fromUser) {
					mTotalRatingUser = rating;
					setTotalRatingIsUser();
				}
			}
		});
		
		mCalcAverageRatingButton = (ImageButton)v.findViewById(R.id.criterion_avg_button);
		mCalcAverageRatingButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mTotalRatingIsAverage)
					setTotalRatingIsUser();
				else
					setTotalRatingIsAverage();
				
				recomputeTotalRating();
			}
		});
		
		return v;		
	}
	
	private void showCriterionDialog(Review criterion) {
		DialogCriterionFragment dialog = new DialogCriterionFragment();
		dialog.setTargetFragment(FragmentReviewCreate.this, CRITERION_EDIT);
		Bundle args = new Bundle();
		
		args.putParcelable(CRITERION, criterion);
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), DIALOG_CRITERION_TAG);
	}
	
	private void setTotalRatingIsAverage() {
		mTotalRatingIsAverage = true;
		mCalcAverageRatingButton.setImageResource(android.R.drawable.ic_input_add);
		recomputeTotalRating();
	}
	
	private void setTotalRatingIsUser() {
		mTotalRatingIsAverage = false;
		mCalcAverageRatingButton.setImageResource(android.R.drawable.ic_menu_add);
		mTotalRatingBar.setRating(mTotalRatingUser);
	}
	
	private void recomputeTotalRating() {
		if(mTotalRatingIsAverage)
			mTotalRatingBar.setRating(mCriteria.getRating());
	}
	
	class CriterionAdaptor extends BaseAdapter {	
		private ReviewNodeCollection mCriteria;
	
		public CriterionAdaptor(ReviewNodeCollection criteria){
		    mCriteria  = criteria;
		}
			
		@Override
		public int getCount() {
			return mCriteria.size();
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public Object getItem(int position) {
			return mCriteria.getItem(position);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh;
			
			if (convertView == null) {						
				LayoutInflater inflater = getSherlockActivity().getLayoutInflater();
				convertView = inflater.inflate(R.layout.criterion_row_stars, parent, false);
				
				TextView criterionText = (TextView)convertView.findViewById(R.id.criterion_name_text_view);				
				RatingBar ratingBar = (RatingBar)convertView.findViewById(R.id.criterion_rating_bar);		
				
				vh = new ViewHolder();
				vh.criterionText = criterionText;
				vh.ratingBar = ratingBar;
				
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder)convertView.getTag();
			}
				
			Review c = (Review)getItem(position);
			
			vh.ratingBar.setTag(Integer.valueOf(position));			
			vh.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
				
				@Override
				public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
					if (fromUser) {
						Review c = (Review)getItem((Integer)ratingBar.getTag()) ;
						mCriteria.get(c.getID()).setRating(rating);
						recomputeTotalRating();
					}					
				}
			});
			
			vh.ratingBar.setRating(c.getRating());
									
			vh.criterionText.setTag(Integer.valueOf(position));
			vh.criterionText.setText(c.getTitle());		
	
			return(convertView);
		};
	};

	static class ViewHolder {
	    public RatingBar ratingBar;
	    public TextView criterionText;
	}
	
	private void updateUI() {
 		((CriterionAdaptor)mCriteriaListView.getAdapter()).notifyDataSetChanged();
 		recomputeTotalRating();
	}
	
	private void addCriterion() {
		String criterionName = mCriterionName.getText().toString();
		
		if (mCriteria.size() == 0 && criterionName.length() > 0)
			setTotalRatingIsAverage();		      		
		
		mCriteria.add(new UserReview(criterionName));		
		mCriterionName.setText(null);
		
		updateUI();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_CANCELED) {
			return;
		}
		
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case CRITERION_EDIT:
				mCriteria.add((Review)data.getParcelableExtra(CRITERION));
				break;
			default:
				break;
			};
		}
			
		if (resultCode == DialogCriterionFragment.RESULT_DELETE_CRITERION) {
			Review criterion = (Review)data.getParcelableExtra(CRITERION);
			mCriteria.remove(criterion.getID());
			if(mCriteria.size() == 0)
				setTotalRatingIsUser();
		}		

		updateUI();				
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_review_define, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_next_screen:
			if (mSubject == null || mSubject.length() < 1)
				Toast.makeText(getSherlockActivity(), "Please enter a subject name...", Toast.LENGTH_SHORT).show();
			else {
				Review review = ReviewFactory.createUserReview(mSubject.getText().toString());
				mReview = ReviewFactory.createReviewNode(review);
				mReview.addChildren(mCriteria);
				mReview.setRating(mTotalRatingBar.getRating());
				mReview.setRatingIsAverage(mTotalRatingIsAverage);
				IntentObjectHolder.addObject(REVIEW_OBJECT, mReview);
				Intent i = new Intent(getSherlockActivity(), ActivityReviewOptions.class);
				startActivity(i);
			}
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
