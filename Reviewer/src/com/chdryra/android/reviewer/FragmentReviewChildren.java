package com.chdryra.android.reviewer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

public class FragmentReviewChildren extends SherlockFragment {
	private final static String DIALOG_CHILD_TAG = "ChildDialog";

	public final static int CHILD_EDIT = 0;
	private static final int DELETE_CONFIRM = 1;
	
	public static final int RESULT_DELETE = Activity.RESULT_FIRST_USER;
	
	private boolean mDeleteConfirmed = false;
		
	private ControllerReviewNode mController;
	private ControllerReviewNodeChildren mChildrenController;

	private ArrayList<String> mChildNames = new ArrayList<String>();
	
	private TextView mSubjectTextView;
	private ClearableEditText mChildNameEditText;
	private ImageButton mAddChildButton;
	private ImageButton mCalcAverageRatingButton;
	private ListView mChildListView;
	private RatingBar mTotalRatingBar;
	
	private float mOldRating;
	private boolean mOldRatingIsAverage;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mController = Controller.unpack(getActivity().getIntent().getExtras());
		
		mChildrenController = mController.getChildrenController();
		for(String id : mChildrenController.getIDs())
			mChildNames.add(mChildrenController.getTitle(id));
		
		setHasOptionsMenu(true);		
		setRetainInstance(true);		
		
		backup();
	}
	
	private void backup() {
		mOldRating = mController.getRating();
		mOldRatingIsAverage = mController.isReviewRatingAverage();
		mChildrenController.backupChildrenNamesAndRatings();
	}

	private void revertToBackup() {
		mController.setRating(mOldRating);
		mController.setReviewRatingAverage(mOldRatingIsAverage);
		mChildrenController.revertToBackUp();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_review_children, container, false);		
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		mSubjectTextView = (TextView)v.findViewById(R.id.review_subject);
		mAddChildButton = (ImageButton)v.findViewById(R.id.criterion_add_button);
		mChildNameEditText = (ClearableEditText)v.findViewById(R.id.criterion_add_edit_text);
		mChildListView = (ListView) v.findViewById(R.id.criterion_listview);
		mTotalRatingBar = (RatingBar)v.findViewById(R.id.total_rating_bar);
		
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
					showChildDialog((String)parent.getItemAtPosition(pos));
					return true;
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
		
		mCalcAverageRatingButton = (ImageButton)v.findViewById(R.id.criterion_avg_button);
		mCalcAverageRatingButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mController.isReviewRatingAverage())
					setTotalRatingIsUser();
				else if(mChildrenController.size() > 0)
					setTotalRatingIsAverage();
			}
		});
		
		updateSubjectText();
		if(mController.isReviewRatingAverage())
			setTotalRatingIsAverage();
		else
			setTotalRatingIsUser();

		return v;		
	}

	private void updateSubjectText() {
		mSubjectTextView.setText(mController.getTitle());
	}
	
	private void updateRatingBar() {
		mTotalRatingBar.setRating(mController.getRating());
	}
	
	private void showChildDialog(String childId) {
		DialogReviewTitleEditFragment dialog = new DialogReviewTitleEditFragment();
		dialog.setTargetFragment(FragmentReviewChildren.this, CHILD_EDIT);
		Bundle args = Controller.pack(mController);
		args.putString(DialogReviewTitleEditFragment.REVIEW_ID, childId);
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), DIALOG_CHILD_TAG);
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
	
	private void updateUI() {
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
			Toast.makeText(getSherlockActivity(), childName + ": " + getResources().getString(R.string.toast_exists_criterion), Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (mChildrenController.size() == 0 && childName.length() > 0)
			setTotalRatingIsAverage();		      		

		mChildrenController.addChild(childName);
		mChildNames.add(childName);
		mChildNameEditText.setText(null);		
		
		updateUI();
	}
	
	private void deleteChild(String childID) {
		mChildNames.remove(mChildrenController.getTitle(childID));
		mChildrenController.removeChild(childID);
		if(mChildrenController.size() == 0)
			setTotalRatingIsUser();
		updateUI();
	}
	
	private void sendResult(int resultCode) {
		if (resultCode == RESULT_DELETE && mChildrenController.size() > 0) {
			if(mDeleteConfirmed)
				mChildrenController.clear();
			else {
				DialogBasicFragment.showDeleteConfirmDialog(getResources().getString(R.string.dialog_delete_criteria_title), 
						FragmentReviewChildren.this, DELETE_CONFIRM, getFragmentManager());
				return;
			}
		}
		
		if (resultCode == Activity.RESULT_CANCELED)
			revertToBackup();
		
		getSherlockActivity().setResult(resultCode);		 
		getSherlockActivity().finish();	
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_CANCELED)
			return;
		
		String childID = data.getStringExtra(DialogReviewTitleEditFragment.REVIEW_ID);
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

				case DELETE_CONFIRM:
					if(resultCode == Activity.RESULT_OK) {
						mDeleteConfirmed = true;
						sendResult(RESULT_DELETE);
					}
					break;
					
				default:
					break;
			};
		}

		updateUI();				
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_review_children, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			sendResult(Activity.RESULT_CANCELED);
			return true;
		
		case R.id.menu_item_delete:
			sendResult(RESULT_DELETE);
			return true;

		case R.id.menu_item_done:
			sendResult(Activity.RESULT_OK);
			return true;

		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}


	class ChildReviewsAdaptor extends BaseAdapter {	
		private ControllerReviewNodeChildren mChildren;
	
		public ChildReviewsAdaptor(ControllerReviewNodeChildren children){
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
				convertView = inflater.inflate(R.layout.child_row_stars, parent, false);
				
				TextView criterionText = (TextView)convertView.findViewById(R.id.criterion_name_text_view);				
				RatingBar ratingBar = (RatingBar)convertView.findViewById(R.id.child_rating_bar);		
				
				vh = new ViewHolder();
				vh.childSubject = criterionText;
				vh.ratingBar = ratingBar;
				
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder)convertView.getTag();
			}
				
			String id = (String)getItem(position);
			
			vh.ratingBar.setTag(Integer.valueOf(position));			
			vh.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
				
				@Override
				public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
					if (fromUser) {
						String id = (String)getItem((Integer)ratingBar.getTag()) ;
						mChildren.setRating(id, rating);
						updateRatingBar();
					}					
				}
			});
			
			vh.ratingBar.setRating(mChildrenController.getRating(id));
									
			vh.childSubject.setTag(Integer.valueOf(position));
			vh.childSubject.setText(mChildrenController.getTitle(id));		
			vh.childSubject.setTextColor(mSubjectTextView.getTextColors().getDefaultColor());
	
			return(convertView);
		};
	};

	static class ViewHolder {
	    public RatingBar ratingBar;
	    public TextView childSubject;
	}
}
