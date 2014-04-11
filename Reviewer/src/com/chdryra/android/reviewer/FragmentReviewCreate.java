package com.chdryra.android.reviewer;

import java.util.ArrayList;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
import com.chdryra.android.myandroidwidgets.ClearableEditText;

public class FragmentReviewCreate extends SherlockFragment {
	private final static String DIALOG_CHILD_TAG = "CriterionDialog";

	public final static String CHILD = "com.chdryra.android.reviewer.child_review";

	public final static int CHILD_EDIT = 0;
	
	private Controller mController = Controller.getInstance();
	private Controller mChildrenController;
	
	private RDId mReviewID;
	private ArrayList<String> mChildNames = new ArrayList<String>();
	
	private ClearableEditText mSubjectEditText;
	private ClearableEditText mChildNameEditText;
	private ImageButton mAddChildButton;
	private ImageButton mCalcAverageRatingButton;
	private ListView mChildListView;
	private RatingBar mTotalRatingBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mReviewID = getActivity().getIntent().getParcelableExtra(FragmentReviewOptions.REVIEW_ID);
		if(mReviewID == null)
			createNewUserReview();
		
		mChildrenController = mController.getChildReviewsControllerFor(mReviewID);
		for(RDId id : mChildrenController.getIDs())
			mChildNames.add(mChildrenController.getTitle(id));
		
		setHasOptionsMenu(true);		
		setRetainInstance(true);		
	}
	
	private void createNewUserReview() {
		mReviewID = mController.addUserReview("");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_review_create, container, false);		
		
		mSubjectEditText = (ClearableEditText)v.findViewById(R.id.review_subject);
		mAddChildButton = (ImageButton)v.findViewById(R.id.criterion_add_button);
		mChildNameEditText = (ClearableEditText)v.findViewById(R.id.criterion_add_edit_text);
		mChildListView = (ListView) v.findViewById(R.id.criterion_listview);
		mTotalRatingBar = (RatingBar)v.findViewById(R.id.total_rating_bar);
		
		mSubjectEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				mController.setTitle(mReviewID, s.toString());
			}
		});
		
		mAddChildButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View button) {							
				addChild();							
			}
		});

		mChildNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	        	EditText et = (EditText)v;
	            if(actionId == EditorInfo.IME_ACTION_DONE && et.getText().toString().length() > 0)
	            	mAddChildButton.performClick();	        		

	            return true;
	        }
	    });
		
		mChildListView.setAdapter(new ChildReviewsAdaptor(mChildrenController));
		mChildListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {	
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v, int pos, long id) {
					showChildDialog((Review)parent.getItemAtPosition(pos));
					return true;
			}
		});
				
		mTotalRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				if(fromUser) {
					mController.setRating(mReviewID, rating);
					setTotalRatingIsUser();
				}
			}
		});
		
		mCalcAverageRatingButton = (ImageButton)v.findViewById(R.id.criterion_avg_button);
		mCalcAverageRatingButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mController.isReviewRatingAverage(mReviewID))
					setTotalRatingIsUser();
				else if(mChildrenController.size() > 0)
					setTotalRatingIsAverage();
			}
		});
		
		updateSubjectText();
		if(mController.isReviewRatingAverage(mReviewID))
			setTotalRatingIsAverage();
		else
			setTotalRatingIsUser();

		return v;		
	}

	private void updateSubjectText() {
		mSubjectEditText.setText(mController.getTitle(mReviewID));
	}
	
	private void updateRatingBar() {
		mTotalRatingBar.setRating(mController.getRating(mReviewID));
	}
	
	private void showChildDialog(Review child) {
		DialogReviewTitleEditFragment dialog = new DialogReviewTitleEditFragment();
		dialog.setTargetFragment(FragmentReviewCreate.this, CHILD_EDIT);
		Bundle args = new Bundle();
		args.putParcelable(CHILD, child.getID());
		dialog.show(getFragmentManager(), DIALOG_CHILD_TAG);
	}
	
	private void setTotalRatingIsAverage() {
		mController.setReviewRatingAverage(mReviewID, true);
		mCalcAverageRatingButton.setImageResource(android.R.drawable.ic_input_add);
		updateRatingBar();
	}
	
	private void setTotalRatingIsUser() {
		mController.setReviewRatingAverage(mReviewID, false);
		mCalcAverageRatingButton.setImageResource(android.R.drawable.ic_menu_add);
		updateRatingBar();
	}
	
	public void update() {
		((ChildReviewsAdaptor)mChildListView.getAdapter()).notifyDataSetChanged();
 		updateSubjectText();
		updateRatingBar();
	}
	
	private void addChild() {
		String childName = mChildNameEditText.getText().toString();
		
		if(childName == null || childName.length() == 0) {
			Toast.makeText(getSherlockActivity(), getResources().getString(R.string.toast_enter_criterion), Toast.LENGTH_SHORT).show();
			return;
		}
		
		if(mChildNames.contains(childName)) {
			Toast.makeText(getSherlockActivity(), "Criterion: " + childName + " already exists...", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (mChildrenController.size() == 0 && childName.length() > 0)
			setTotalRatingIsAverage();		      		

		mChildrenController.addUserReview(childName);
		mChildNames.add(childName);
		mChildNameEditText.setText(null);		
		
		update();
	}
	
	private void deleteChild(RDId childID) {
		mChildNames.remove(mChildrenController.getTitle(childID));
		mChildrenController.remove(childID);
		if(mChildrenController.size() == 0)
			setTotalRatingIsUser();
		update();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_CANCELED)
			return;
		
		RDId childID = data.getParcelableExtra(CHILD);
		if (resultCode == DialogReviewTitleEditFragment.RESULT_DELETE)
			deleteChild(childID);

		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
				case CHILD_EDIT:
					String childName = mChildrenController.getTitle(childID);
					String oldName = data.getStringExtra(DialogReviewTitleEditFragment.OLD_NAME);
					
					if(childName.equals(oldName))
						break;
					
					if(mChildNames.contains(childName)) {
						String newName = childName;
						int i = 1;
						while(mChildNames.contains(newName))
							newName = childName + "_" + String.valueOf(i++);
						Toast.makeText(getSherlockActivity(), "Criterion: " + childName + " already exists, changing name to " + newName, Toast.LENGTH_SHORT).show();
						mChildrenController.setTitle(childID, newName);
					}
					
					mChildNames.remove(oldName);
					mChildNames.add(mChildrenController.getTitle(childID));
					break;

				default:
					break;
			};
		}

		update();				
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
			if (mSubjectEditText == null || mSubjectEditText.length() == 0)
				Toast.makeText(getSherlockActivity(), getResources().getString(R.string.toast_enter_subject), Toast.LENGTH_SHORT).show();
			else {
				Intent i = new Intent(getSherlockActivity(), ActivityReviewOptions.class);
				i.putExtra(FragmentReviewOptions.REVIEW_ID, mReviewID);
				startActivity(i);
			}
			break;

		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}


	class ChildReviewsAdaptor extends BaseAdapter {	
		private Controller mChildren;
	
		public ChildReviewsAdaptor(Controller children){
		    mChildren  = children;
		}
			
		@Override
		public int getCount() {
			return mChildren.size();
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public Object getItem(int position) {
			return mChildren.getItem(position);
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
				
			RDId id = (RDId)getItem(position);
			
			vh.ratingBar.setTag(Integer.valueOf(position));			
			vh.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
				
				@Override
				public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
					if (fromUser) {
						RDId id = (RDId)getItem((Integer)ratingBar.getTag()) ;
						mChildren.setRating(id, rating);
						updateRatingBar();
					}					
				}
			});
			
			vh.ratingBar.setRating(mChildrenController.getRating(id));
									
			vh.criterionText.setTag(Integer.valueOf(position));
			vh.criterionText.setText(mChildrenController.getTitle(id));		
	
			return(convertView);
		};
	};

	static class ViewHolder {
	    public RatingBar ratingBar;
	    public TextView criterionText;
	}
}
