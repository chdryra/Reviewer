package com.chdryra.android.reviewer;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;

import java.util.Iterator;
import java.util.LinkedHashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

public class ReviewCommentFragment extends SherlockFragment {
	
	public static final String EXTRA_COMMENT_STRING = "com.chdryra.android.reviewer.comment_string";
	public static final int RESULT_DELETE_COMMENT = Activity.RESULT_FIRST_USER;
	
	private static final int MAX_COMMENT_EDITTEXT_LINES = 5;
	
	private Review mReview;
	
	private Button mDeleteButton;
	private Button mCancelButton;
	private Button mDoneButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
		mReview = (Review)IntentObjectHolder.getObject(ReviewOptionsFragment.REVIEW_OBJECT);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_review_comment, container, false);		
		
		getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);		
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		LinearLayout linearlayout = (LinearLayout)v.findViewById(R.id.commentCriteriaLinearLayout);	
		linearlayout.addView(getCommentLineView(mReview));
		
		LinkedHashMap<String, Criterion> criteria = mReview.getCriteriaList().getCriterionHashMap();
		Iterator<Criterion> it = criteria.values().iterator();
		while (it.hasNext()) {
			final Criterion criterion = it.next();			
			linearlayout.addView(getCommentLineView(criterion));
		}
		
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
	
	private View getCommentLineView(final Commentable commentable) {
		View v = getSherlockActivity().getLayoutInflater().inflate(R.layout.dialog_comment, null);
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
		
		if( getTargetRequestCode() == ReviewOptionsFragment.COMMENT_EDIT )
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
	
	private void sendResult(int resultCode) {
		if (resultCode == RESULT_DELETE_COMMENT)
			mReview.deleteCommentIncludingCriteria();
		
		IntentObjectHolder.addObject(ReviewOptionsFragment.REVIEW_OBJECT, mReview);
		
		getSherlockActivity().setResult(resultCode);		 
		getSherlockActivity().finish();	
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getSherlockActivity()) != null) {
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
}
