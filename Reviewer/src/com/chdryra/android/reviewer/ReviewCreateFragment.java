package com.chdryra.android.reviewer;

import java.util.Date;
import java.util.LinkedHashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;



public class ReviewCreateFragment extends SherlockFragment{
	private final static String TAG = "ReviewerFragment";
	private final static String CRITERION_DIALOG_TAG = "CriterionDialog";
	public final static String CRITERION_NAME = "com.chdryra.android.reviewer.criterion_name";
	public final static String REVIEW_OBJECT = "com.chdryra.android.reviewer.review_object";
	public final static int CRITERION_EDIT = 0;
	public static enum Result {TOTAL_IS_AVERAGE, TOTAL_IS_USER};
	
	private Review mReview;
	private CriterionList mCriteria = new CriterionList();
	private EditText mSubject;
	private EditText mCriterionName;
	private ImageButton mAddCriterionButton;
	private ImageButton mCalcAverageRatingButton;
	private ListView mCriteriaListView;
	private RatingBar mTotalRatingBar;
	private boolean mTotalRatingIsAverage = false;
	private float mTotalRatingUser;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mReview = (Review)IntentObjectHolder.getObject(ReviewOptionsFragment.REVIEW_OBJECT);
		if(mReview == null)
			mReview = new Review();
		setHasOptionsMenu(true);		
		setRetainInstance(true);		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_review_create, container, false);		
		
		mSubject = (EditText)v.findViewById(R.id.review_subject);
		EditTextUtils.setupEditTextCusorVisibility(mSubject);		
		mSubject.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_DONE)
	                EditTextUtils.hideKeyboard(getSherlockActivity(), (EditText)v);
	           
	            return true;
	        }
	    });
			
		mCriterionName = (EditText)v.findViewById(R.id.criterion_add_edit_text);
		EditTextUtils.setupEditTextCusorVisibility(mCriterionName);
		mCriterionName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_DONE)
	            {
	        		if (mCriterionName.getText().toString().length() > 0)
	        			addCriterion();	        		
	              	
	                EditTextUtils.hideKeyboard(getSherlockActivity(),(EditText)v);
	            }
	            return true;
	        }
	    });

		mAddCriterionButton = (ImageButton)v.findViewById(R.id.criterion_add_button);
		mAddCriterionButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View button) {							
				addCriterion();							
			}
		});
		
		mCriteriaListView = (ListView) v.findViewById(R.id.criterion_listview);		
		mCriteriaListView.setAdapter(new CriterionAdaptor(mCriteria));
		mCriteriaListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
		
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v, int pos, long id) {
				Criterion c = (Criterion)parent.getItemAtPosition(pos);
				Log.i(TAG, "Criterion: " + c.getName());
				
				CriterionDialogFragment dialog = new CriterionDialogFragment();
				dialog.setTargetFragment(ReviewCreateFragment.this, CRITERION_EDIT);
				Bundle args = new Bundle();
				args.putSerializable(CRITERION_NAME, c.getName());
				dialog.setArguments(args);
				dialog.show(getFragmentManager(), CRITERION_DIALOG_TAG);
				
				return false;
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
			mTotalRatingBar.setRating(mCriteria.getAverageRating());
	}
	
	class CriterionAdaptor extends BaseAdapter {	
		private CriterionList mCriteria;
	
		public CriterionAdaptor(CriterionList criteria){
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
			LinkedHashMap<String, Criterion> map = mCriteria.getCriterionHashMap();
			String[] keys = map.keySet().toArray(new String[map.size()]);
			return map.get(keys[position]);
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
				
			Criterion c = (Criterion)getItem(position);
			
			vh.ratingBar.setTag(Integer.valueOf(position));			
			vh.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
				
				@Override
				public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
					if (fromUser) {
						Criterion c = (Criterion)getItem((Integer)ratingBar.getTag()) ;
						mCriteria.changeCriterionRating(c.getName(), rating);
						recomputeTotalRating();
					}					
				}
			});
			
			vh.ratingBar.setRating(c.getRating());
									
			vh.criterionText.setTag(Integer.valueOf(position));
			vh.criterionText.setText(c.getName());		
	
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
		
		makeToast(mCriteria.addCriterion(criterionName), criterionName);		
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
				String oldName = (String)data.getSerializableExtra(CriterionDialogFragment.EXTRA_CRITERION_OLD_NAME);
				String newName = (String)data.getSerializableExtra(CriterionDialogFragment.EXTRA_CRITERION_NEW_NAME);				
				makeToast(mCriteria.changeCriterionName(oldName, newName), oldName, newName);
				break;

			default:
				break;
			};
		}
			
		if (resultCode == CriterionDialogFragment.RESULT_DELETE_CRITERION) {
			String toDelete = (String)data.getSerializableExtra(CriterionDialogFragment.EXTRA_CRITERION_OLD_NAME);
			makeToast(mCriteria.deleteCriterion(toDelete), toDelete);
			if(mCriteria.size() == 0)
				setTotalRatingIsUser();
		}		

		updateUI();				
	}
	
	private void makeToast(CriterionList.Result r, String s1, String s2) {
		if( r == CriterionList.Result.DELETED )
			Toast.makeText(getSherlockActivity(), "Deleted " + s1 + "...", Toast.LENGTH_SHORT).show();	

		if( r == CriterionList.Result.ADDED )
			Toast.makeText(getSherlockActivity(), "Added " + s1 + "...", Toast.LENGTH_SHORT).show();	

		if( r == CriterionList.Result.CHANGED )
			Toast.makeText(getSherlockActivity(), "Changed " + s1 + " to " + s2 + "...", Toast.LENGTH_SHORT).show();	

		if( r == CriterionList.Result.EXISTS )
			Toast.makeText(getSherlockActivity(), s1 + " already exists...", Toast.LENGTH_SHORT).show();
		
		if( r == CriterionList.Result.NULLNAME )
			Toast.makeText(getSherlockActivity(), "Enter criterion name...", Toast.LENGTH_SHORT).show();
	}
	
	private void makeToast(CriterionList.Result r, String s1) {
		makeToast(r, s1, null);		
	}
	
	private void makeToast(Result r) {
		if( r == Result.TOTAL_IS_AVERAGE)
			Toast.makeText(getSherlockActivity(), "Total rating switched to average...", Toast.LENGTH_SHORT).show();
		if( r== Result.TOTAL_IS_USER )
			Toast.makeText(getSherlockActivity(), "Total rating switched to independent...", Toast.LENGTH_SHORT).show();
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
				mReview.setSubject(mSubject.getText().toString());
				mReview.setCriteriaList(mCriteria);
				mReview.setRating(mTotalRatingBar.getRating());
				mReview.setRatingIsAverage(mTotalRatingIsAverage);
				IntentObjectHolder.addObject(REVIEW_OBJECT, mReview);
				Intent i = new Intent(getSherlockActivity(), ReviewOptionsActivity.class);
				startActivity(i);
			}
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
