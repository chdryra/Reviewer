package com.chdryra.android.reviewer;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;

import com.chdryra.android.ratexp.R;

public class ReviewerFragment extends Fragment {
	private final static String TAG = "ReviewerFragment";
	private Review mReview;
	private Button mAddCriterionButton;
	private ListView mCriteriaListView;
	private RatingBar mRatingBar;
	private ArrayList<Criterion> mCriteria = new ArrayList<Criterion>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);		
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_review, container, false);		
		
		mCriteriaListView = (ListView) v.findViewById(R.id.criterion_listview);
		mCriteriaListView.setAdapter(new CriterionAdaptor(mCriteria));
		
		mAddCriterionButton = (Button)v.findViewById(R.id.add_criterion_button);
		mAddCriterionButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View button) {				
				mRatingBar.setRating(0);
				mRatingBar.setEnabled(false);
				Criterion c = new Criterion(String.valueOf(mCriteria.size()));
				mCriteria.add(c);
				updateUI();
			}
		});
		
		mRatingBar = (RatingBar)v.findViewById(R.id.total_rating_bar);
		
		return v;		
	}
	
	class CriterionAdaptor extends ArrayAdapter<Criterion> {
		
		private class CriterionTextWatcher implements TextWatcher {
			private EditText mEditText;
			
		    public CriterionTextWatcher(EditText editText) { 
		    	mEditText = editText;
		    }

		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    	
		    }

		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	Integer position = (Integer)mEditText.getTag();
		    	if( position != null )
		    	{
		    		Criterion c = getItem(position);
		    		Log.i(TAG, "Setting Text- Position: " + String.valueOf(position) + ", Criterion: " + c.getName() + ", Rating: " + c.getRating());
		    		getItem(position).setName(s.toString());
		    	}
		    		
		    		
		    }

		    public void afterTextChanged(Editable s) {
		    }
		}

		private class CriterionRatingChangeListener implements RatingBar.OnRatingBarChangeListener {
			public CriterionRatingChangeListener() {
				
			}
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				if (fromUser) {
					getItem((Integer)ratingBar.getTag()).setRating(rating);	
				}				
			}
		}
		
		public CriterionAdaptor(ArrayList<Criterion> criteria) {
			super(getActivity(), 0, criteria);
		}		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh;
			
			if (convertView == null) {						
				LayoutInflater inflater = getActivity().getLayoutInflater();
				convertView = inflater.inflate(R.layout.criterion_row_stars, parent, false);
				
				EditText criterionText = (EditText)convertView.findViewById(R.id.criterion_name_edit_text);
				criterionText.addTextChangedListener(new CriterionTextWatcher(criterionText));
				
				RatingBar ratingBar = (RatingBar)convertView.findViewById(R.id.criterion_rating_bar);				
				ratingBar.setOnRatingBarChangeListener(new CriterionRatingChangeListener());
				
				vh = new ViewHolder();
				vh.editText = criterionText;
				vh.ratingBar = ratingBar;
				
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder)convertView.getTag();
			}
				
			Criterion c = getItem(position);
			
			RatingBar ratingBar = (RatingBar)vh.ratingBar;
			ratingBar.setTag(Integer.valueOf(position));
			ratingBar.setRating(c.getRating());						
			
			EditText criterionText = (EditText)vh.editText;
			criterionText.setTag(Integer.valueOf(position));
			//mEditText = criterionText;
			criterionText.setText(c.getName());		
	
//			Log.i(TAG, "Position: " + String.valueOf(position) + ", Criterion: " + c.getName() + ", Rating: " + c.getRating());
			
			return(convertView);
		};
		
	};
	
	private class ViewHolder {
	    public RatingBar ratingBar;
	    public EditText editText;
	}
	
	private void updateUI() {
		((CriterionAdaptor)mCriteriaListView.getAdapter()).notifyDataSetChanged();
	}
	

	}
