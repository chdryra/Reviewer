package com.chdryra.android.reviewer;

import java.util.Iterator;
import java.util.LinkedHashMap;

import android.annotation.TargetApi;
import android.app.Activity;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class ReviewCommentFragment extends SherlockFragment {
	
	public static final String EXTRA_COMMENT_STRING = "com.chdryra.android.reviewer.comment_string";
	public static final int RESULT_DELETE_COMMENT = Activity.RESULT_FIRST_USER;
	
	private static final int MIN_HEADLINE_EDITTEXT_LINES = 3;
	private static final int MAX_COMMENT_EDITTEXT_LINES = 5;
	
	private Review mReview;
	
	private Button mDeleteButton;
	private Button mCancelButton;
	private Button mDoneButton;

	private MenuItem mAddCriteriaCommentsMenuItem;
	private boolean mAddCriteriaComments = false;
	
	private View mHeadlineCommentsView;
	private LinearLayout mCriteriaCommentsLinearLayout;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mReview = (Review)IntentObjectHolder.getObject(ReviewOptionsFragment.REVIEW_OBJECT);
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
		LinkedHashMap<String, Criterion> criteria = mReview.getCriteriaList().getCriterionHashMap();
		Iterator<Criterion> it = criteria.values().iterator();
		while (it.hasNext())
			mCriteriaCommentsLinearLayout.addView(getCommentLineView(it.next(), null));
		
		hideCriteriaComments();
		
	    mDeleteButton = (Button)v.findViewById(R.id.button_map_delete);
	    mDeleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendResult(RESULT_DELETE_COMMENT);
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
		mHeadlineCommentsView = getCommentLineView(mReview, mHeadlineCommentsView);
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
	
	private View getCommentLineView(final Commentable commentable, View v) {
		if(v == null)
			v = getSherlockActivity().getLayoutInflater().inflate(R.layout.comment_line_view, null);
		
		TextView criterionName = (TextView)v.findViewById(R.id.comment_text_view);
		criterionName.setText(commentable.getCommentTitle());
		
		EditText comment = (EditText)v.findViewById(R.id.comment_edit_text);
		comment.setHorizontallyScrolling(false);
		comment.setMaxLines(MAX_COMMENT_EDITTEXT_LINES);
		
		//To allow scrolling within edit text 
		//if contains string by disallowing scrollview scrolling.
		comment.setOnTouchListener(new View.OnTouchListener() {
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
		
		if( commentable.getComment() != null )
			comment.setText(commentable.getComment());
		
		comment.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String newComment = s.toString().trim();
				if(newComment.length() > 0)
					commentable.setComment(newComment);
			}	
		});
		
		comment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) {
		        if (hasFocus) {
		            getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		        }
		    }
		});
		
		return v;
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void hideCriteriaComments() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mCriteriaCommentsLinearLayout.setAlpha((float) 0.25);
			setEditTextsEnabled(mCriteriaCommentsLinearLayout, false);
		} else
			mCriteriaCommentsLinearLayout.setVisibility(View.GONE);
		
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
		if (resultCode == RESULT_DELETE_COMMENT)
			mReview.deleteCommentIncludingCriteria();
		
		if(mReview.getCriteriaList().size() > 0 && !mAddCriteriaComments)
			mReview.getCriteriaList().deleteComments();
		
		IntentObjectHolder.addObject(ReviewOptionsFragment.REVIEW_OBJECT, mReview);
		
		getSherlockActivity().setResult(resultCode);		 
		getSherlockActivity().finish();	
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_review_comment, menu);
		mAddCriteriaCommentsMenuItem = menu.findItem(R.id.menu_item_add_criteria_comments);
		if(mReview.getCriteriaList().size() == 0)
			mAddCriteriaCommentsMenuItem.setVisible(false);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			mCancelButton.performClick();
			return true;
		case R.id.menu_item_add_criteria_comments:
			mAddCriteriaComments = !mAddCriteriaComments;
			if(mAddCriteriaComments) {
				showCriteriaComments();
				mAddCriteriaCommentsMenuItem.setIcon(R.drawable.ic_delete);
			} else {
				hideCriteriaComments();
				mAddCriteriaCommentsMenuItem.setIcon(R.drawable.ic_input_add);
			}
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
