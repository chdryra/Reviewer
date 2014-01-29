package com.chdryra.android.reviewer;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ReviewerFragment extends Fragment {
	private final static String TAG = "ReviewerFragment";
	private final static String CRITERION_DIALOG_TAG = "CriterionDialog";
	public static final String CRITERION_NAME = "com.chdryra.android.reviewer.criterion_name";
	
	public static final int CRITERION_NEW = 0;
	public static final int CRITERION_EDIT = 1;

	private static enum ToastType {ADDED, DELETED, CHANGED, EXISTS};
	
	private Review mReview;
	private ArrayList<Criterion> mCriteria = new ArrayList<Criterion>();
	
	private EditText mSubject;
	private EditText mCriterionName;
	private ImageButton mAddCriterionButton;
	private ListView mCriteriaListView;
	private RatingBar mRatingBar;
	
	
	private void hideKeyboard(EditText editText)
	{
	    InputMethodManager imm= (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        editText.setCursorVisible(false);
	}
	
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
		
		mSubject = (EditText)v.findViewById(R.id.review_subject);
		setupEditText(mSubject);		
		mSubject.setCursorVisible(false);
		mSubject.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	        	EditText eT;
	            if(actionId == EditorInfo.IME_ACTION_DONE)
	            {
	              	eT = (EditText)v;
	                hideKeyboard(eT);
	            }
	            return true;
	        }
	    });
		
//		mSubject.addTextChangedListener(new TextWatcher() {
//			
//			@Override
//			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
//					int arg3) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void afterTextChanged(Editable arg0) {
//				
//			}
//		});
//		
		mCriterionName = (EditText)v.findViewById(R.id.criterion_add_edit_text);
		setupEditText(mCriterionName);
		mCriterionName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	        	EditText eT;
	            if(actionId == EditorInfo.IME_ACTION_DONE)
	            {
	              	addCriterion();
	            	eT = (EditText)v;	              	
					hideKeyboard(eT);
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
		mCriteriaListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v, int pos, long id) {
				Criterion c = (Criterion)parent.getItemAtPosition(pos);
				Log.i(TAG, "Criterion: " + c.getName());
				
				FragmentManager fm = getActivity().getSupportFragmentManager();
				CriterionDialogFragment dialog = new CriterionDialogFragment();
				dialog.setTargetFragment(ReviewerFragment.this, CRITERION_EDIT);
				Bundle args = new Bundle();
				args.putSerializable(CRITERION_NAME, c.getName());
				dialog.setArguments(args);
				dialog.show(fm, CRITERION_DIALOG_TAG);
				
				return false;
			}
		});
				
		mRatingBar = (RatingBar)v.findViewById(R.id.total_rating_bar);
		
		return v;		
	}
	
	class CriterionAdaptor extends ArrayAdapter<Criterion> {
		
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
				
				TextView criterionText = (TextView)convertView.findViewById(R.id.criterion_name_text_view);				
				RatingBar ratingBar = (RatingBar)convertView.findViewById(R.id.criterion_rating_bar);		
				
				vh = new ViewHolder();
				vh.criterionText = criterionText;
				vh.ratingBar = ratingBar;
				
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder)convertView.getTag();
			}
				
			Criterion c = getItem(position);
			
			vh.ratingBar.setTag(Integer.valueOf(position));			
			vh.ratingBar.setOnRatingBarChangeListener(new CriterionRatingChangeListener());
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
     		if(mCriteria.size() > 0)
     		{
     			mRatingBar.setRating(0);
     			mRatingBar.setEnabled(false);
     		}
     		else
     			mRatingBar.setEnabled(true);
	}
	
	private void addCriterion() {
		String criterionName = mCriterionName.getText().toString();
      	if(criterionName.length() > 0)
		{      		
      		if(criterionExists(criterionName)) {
				makeToast(ToastType.EXISTS, criterionName);
				mCriterionName.setText(null);
				return;
			}
      		
			Criterion c = new Criterion(criterionName);
			mCriteria.add(c);
			makeToast(ToastType.ADDED, criterionName);
		};
		
		mCriterionName.setText(null);
		updateUI();
	}
	
	private void setupEditText(EditText editText) {	 
		 
		editText.setOnClickListener(new View.OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	EditText eT = (EditText)v;
		        	eT.setCursorVisible(true);
		        }
		    });
		
		editText.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				EditText eT = (EditText)v;
	        	eT.setCursorVisible(true);
				return false;
			}
		});
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
				if(newName != null)
				{
					if(criterionExists(newName)) {
						makeToast(ToastType.EXISTS, newName);
						return;
					}
					
					for (Criterion cr : mCriteria) {
						if(cr.getName().equals(oldName))
							cr.setName(newName);
					}
					Toast.makeText(getActivity(), "Changed " + oldName + " to " + newName + "...", Toast.LENGTH_SHORT).show();
				};				
				break;

			default:
				break;
			};
		}
			
		if (resultCode == CriterionDialogFragment.RESULT_DELETE_CRITERION) {
			String toDelete = (String)data.getSerializableExtra(CriterionDialogFragment.EXTRA_CRITERION_OLD_NAME);
			
			Iterator<Criterion> iter = mCriteria.iterator();
			while (iter.hasNext()) {
			    if (iter.next().getName().equals(toDelete)) {
			    	iter.remove();
			    	makeToast(ToastType.DELETED, toDelete);
			    	break;
			    }			        
			}
		}		

		updateUI();				
	}

	private boolean criterionExists(String name) {
		Iterator<Criterion> iter = mCriteria.iterator();
		while (iter.hasNext()) {
		    if (iter.next().getName().equals(name)) {
		    	return true;
		    }			        
		}
		
		return false;
	}
	
	private void makeToast(ToastType t, String s1, String s2) {
		switch (t) {
		case DELETED:
			Toast.makeText(getActivity(), "Deleted " + s1 + "...", Toast.LENGTH_SHORT).show();	
			break;

		case ADDED:
			Toast.makeText(getActivity(), "Added " + s1 + "...", Toast.LENGTH_SHORT).show();	
			break;

		case CHANGED:
			Toast.makeText(getActivity(), "Changed " + s1 + " to " + s2 + "...", Toast.LENGTH_SHORT).show();	
			break;

		case EXISTS:
			Toast.makeText(getActivity(), s1 + " already exists...", Toast.LENGTH_SHORT).show();			
			break;
			
		default:
			break;
		}
		
		
	}
	
	private void makeToast(ToastType t, String s1) {
		makeToast(t, s1, null);		
	}
}
