package com.chdryra.android.reviewer;

import java.util.HashMap;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.chdryra.android.mygenerallibrary.RandomTextUtils;

public class FragmentReviewComment extends SherlockFragment {
	
	public static final String EXTRA_COMMENT_STRING = "com.chdryra.android.reviewer.comment_string";
	public static final int RESULT_DELETE = Activity.RESULT_FIRST_USER;
	
	private static final int MIN_HEADLINE_EDITTEXT_LINES = 2;
	private static final int MAX_COMMENT_EDITTEXT_LINES = 5;

	private static final int DELETE_CONFIRM = DialogBasicFragment.DELETE_CONFIRM;
	private static final int DELETE_CRITERIA_CONFIRM = DELETE_CONFIRM + 1;
	
	private ReviewUser mReviewUser;

	private Button mDeleteButton;
	private Button mCancelButton;
	private Button mDoneButton;

	private boolean mDeleteConfirmed = false;
	
	private boolean mAddCriteriaComments = false;
	private MenuItem mAddCriteriaCommentsMenuItem;
	private MenuItem mClearCommentMenuItem;

	private Drawable mClearCommentIcon;  
	private View mHeadlineCommentsView;
	private LinearLayout mCriteriaCommentsLinearLayout;
	private EditText mCurrentFocusedEditText;
	private HashMap<RDId, EditText> mEditTexts = new HashMap<RDId, EditText>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mReviewUser = (ReviewUser)UtilReviewPackager.get(getActivity().getIntent());
		setRetainInstance(true);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_review_comment, container, false);		
		
		getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);		
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	
		mHeadlineCommentsView = v.findViewById(R.id.headline_comment_line_view);
		setHeadlineCommentsView();

		mCriteriaCommentsLinearLayout = (LinearLayout)v.findViewById(R.id.criteria_comments_linear_layout);
		for(Review criterion : mReviewUser.getCriteria()) {
			mCriteriaCommentsLinearLayout.addView(getCommentLineView(criterion, null));
			if(!mAddCriteriaComments)
				mAddCriteriaComments = criterion.hasComment();
		}
		updateCriteriaCommentsDisplay();
		
	    mDeleteButton = (Button)v.findViewById(R.id.button_map_delete);
	    mDeleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendResult(RESULT_DELETE);
			}
		});
	    
	    mCancelButton = (Button)v.findViewById(R.id.button_map_cancel);
	    mCancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendResult(Activity.RESULT_CANCELED);
			}
		});
	    
	    mDoneButton = (Button)v.findViewById(R.id.button_map_done);
	    mDoneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendResult(Activity.RESULT_OK);
			}
		});
	    
		return v;
	}
	
	private void setHeadlineCommentsView() {
		mHeadlineCommentsView = getCommentLineView(mReviewUser, mHeadlineCommentsView);
		EditText comment = (EditText)mHeadlineCommentsView.findViewById(R.id.comment_edit_text);
		comment.setMinLines(MIN_HEADLINE_EDITTEXT_LINES);
		comment.setGravity(Gravity.TOP);
		comment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		     	if(actionId == EditorInfo.IME_ACTION_DONE) {
            		mDoneButton.performClick();
            	}        
				return false;
			}
		});
	}
	
	private View getCommentLineView(final Review review, View v) {
		if(v == null)
			v = getSherlockActivity().getLayoutInflater().inflate(R.layout.comment_line_view, null);
		
		String commentTitle = review.hasComment()? review.getComment().getCommentTitle() : review.getTitle().get();
		TextView criterionName = (TextView)v.findViewById(R.id.comment_text_view);
		criterionName.setText(commentTitle);
		
		EditText commentET = (EditText)v.findViewById(R.id.comment_edit_text);
		commentET.setHorizontallyScrolling(false);
		commentET.setMaxLines(MAX_COMMENT_EDITTEXT_LINES);
		
		//To allow scrolling within edit text 
		//if contains string by disallowing scrollview scrolling.
		commentET.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 if (v.getId() == R.id.comment_edit_text) {
                	 
                	 EditText et = (EditText)v;
                	 
                	 if(et.getLineCount() <= MAX_COMMENT_EDITTEXT_LINES)
                		 return false;
                     
                	 et.getParent().requestDisallowInterceptTouchEvent(true);
                     switch (event.getAction() & MotionEvent.ACTION_MASK) {
                     case MotionEvent.ACTION_UP:
                         et.getParent().requestDisallowInterceptTouchEvent(false);
                         break;
                     }
                 }
                 return false;
             }
         });
		
		if( review.hasComment() && review.getComment().getCommentString() != null )
			commentET.setText(review.getComment().getCommentString());
		
		commentET.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				updateClearCommentMenuItemVisibility();
			}	
		});
		
		commentET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) {
		        if (hasFocus) {
		            getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		            setCurrentFocussedEditText((EditText)v);
		        }
		    }
		});
		
		mEditTexts.put(review.getID(), commentET);
		
		return v;
	}

	
	private void setCurrentFocussedEditText(EditText v) {
		mCurrentFocusedEditText = v;
		updateClearCommentMenuItemVisibility();
	}

	private void updateClearCommentMenuItemVisibility() {
		//Have to hack as setting visibility relegates icon to overflow
		if(mCurrentFocusedEditText != null &&  mCurrentFocusedEditText.getText().toString() != null
				&& mCurrentFocusedEditText.getText().toString().length() > 0) {
			mClearCommentIcon.setAlpha(100);
			mClearCommentMenuItem.setEnabled(true);
		} else {
			mClearCommentIcon.setAlpha(0);
			mClearCommentMenuItem.setEnabled(false);
		}
		
		mClearCommentMenuItem.setIcon(mClearCommentIcon);
	}
	
	private void updateCriteriaCommentsDisplay() {
		if(mAddCriteriaComments) {
			showCriteriaComments();
			mAddCriteriaCommentsMenuItem.setIcon(R.drawable.ic_delete);
		} else {
			hideCriteriaComments();
			mAddCriteriaCommentsMenuItem.setIcon(R.drawable.ic_input_add);
		}
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void hideCriteriaComments() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mCriteriaCommentsLinearLayout.setAlpha((float) 0.25);
			setEditTextsEnabled(mCriteriaCommentsLinearLayout, false);
		} else
			mCriteriaCommentsLinearLayout.setVisibility(View.GONE);
		
		//Hacky crap to get keyboard to show Done instead of Next due to caching issues;
		EditText et = (EditText)mHeadlineCommentsView.findViewById(R.id.comment_edit_text);
		int it = et.getInputType();
		et.setInputType(InputType.TYPE_NULL);
		et.setImeOptions(EditorInfo.IME_ACTION_DONE);
		et.setInputType(it);
		et.requestFocus();
		RandomTextUtils.showKeyboard(getSherlockActivity(), et);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void showCriteriaComments() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mCriteriaCommentsLinearLayout.setAlpha((float) 1);
			setEditTextsEnabled(mCriteriaCommentsLinearLayout, true);
		} else
			mCriteriaCommentsLinearLayout.setVisibility(View.VISIBLE);
		
		//Hacky crap to get keyboard to show Next instead of Done due to caching issues;
		EditText et = (EditText)mHeadlineCommentsView.findViewById(R.id.comment_edit_text);
		int it = et.getInputType();
		et.setInputType(InputType.TYPE_NULL);
		et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
		et.setInputType(it);
	}

	private void setEditTextsEnabled(ViewGroup vg, boolean enabled) {
	    int numberChildren = vg.getChildCount();
		for (int i = 0; i < numberChildren; ++i ){
			View v = vg.getChildAt(i);
	        if(v instanceof EditText){
	            EditText editText = (EditText)v;
	            editText.setEnabled(enabled);
	        } else if(v instanceof ViewGroup)
	        	setEditTextsEnabled((ViewGroup)v, enabled);
	    }
	}
	
	private void sendResult(int resultCode) {
		if (resultCode == RESULT_DELETE && reviewHasComments(false)) {
			checkAndDeleteComments(DELETE_CONFIRM, getResources().getString(R.string.comment_activity_title));
			if(!mDeleteConfirmed)
				return;
		}
		
		if(resultCode == Activity.RESULT_OK) {
			if(!mAddCriteriaComments && reviewHasComments(true)) {
				checkAndDeleteComments(DELETE_CRITERIA_CONFIRM, getResources().getString(R.string.dialog_delete_criteria_title));
				if(!mDeleteConfirmed)
					return;
			}
			
			RDComment comment = 
					new RDCommentSingle(mEditTexts.get(mReviewUser.getID()).getText().toString());
			mReviewUser.setComment(comment);
			
			if(mAddCriteriaComments) {
				for (HashMap.Entry<RDId, EditText> entry : mEditTexts.entrySet())
				{
					RDId id = entry.getKey();
					
					if(id.equals(mReviewUser.getID()))
				    	continue;
					
					mReviewUser.getCriterion(id).setComment(new RDCommentSingle(entry.getValue().getText().toString()));
				}
			}
		}	

		getSherlockActivity().setResult(resultCode);		 
		getSherlockActivity().finish();	
	}

	private void checkAndDeleteComments(int requestCode, String dialogTitle) {
		if(mDeleteConfirmed)
			deleteAllComments(mReviewUser.getReviewNode());
		else
			DialogBasicFragment.showDeleteConfirmDialog(dialogTitle, FragmentReviewComment.this, requestCode, getFragmentManager());
	}
	
	private void deleteAllComments(ReviewNode node) {
		ReviewNodeTraverser traverser = new ReviewNodeTraverser(node);
		traverser.setVisitor(new VisitorCommentDeleter());
		traverser.traverse();	
	}

	private boolean reviewHasComments(boolean criteriaOnly) {
		int reviewComment = mReviewUser.hasComment()? 1 :0;
		int numberComments = numberOfComments();
		return criteriaOnly?  numberComments - reviewComment > 0 : numberComments > 0;
	}

	private int numberOfComments() {
		ReviewNodeTraverser traverser = new ReviewNodeTraverser(mReviewUser.getReviewNode());
		VisitorCommentCollector collector = new VisitorCommentCollector();
		traverser.setVisitor(collector);
		traverser.traverse();
		return collector.getComments().size();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    switch (requestCode) {
	        case DELETE_CONFIRM:
				if(resultCode == Activity.RESULT_OK) {
					mDeleteConfirmed = true;
					sendResult(RESULT_DELETE);
				}
				break;
	        case DELETE_CRITERIA_CONFIRM:
				if(resultCode == Activity.RESULT_OK) {
					mDeleteConfirmed = true;
					sendResult(resultCode);
				}
				break;
				
			default:
				break;
		    }
	}
	
	@Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    	mClearCommentMenuItem = menu.findItem(R.id.menu_item_clear_comment);
    	mClearCommentIcon = getResources().getDrawable(R.drawable.ic_clear_search_api_holo_light);
		updateClearCommentMenuItemVisibility();

		mAddCriteriaCommentsMenuItem = menu.findItem(R.id.menu_item_add_criteria_comments);
		if(mReviewUser.getCriteria().size() == 0)
			mAddCriteriaCommentsMenuItem.setVisible(false);
    }
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);		
		inflater.inflate(R.menu.fragment_review_comment, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			mCancelButton.performClick();
			return true;
		case R.id.menu_item_add_criteria_comments:
			mAddCriteriaComments = !mAddCriteriaComments;
			updateCriteriaCommentsDisplay();
			break;
		case R.id.menu_item_clear_comment:
			if(mCurrentFocusedEditText != null)
				mCurrentFocusedEditText.setText(null);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
