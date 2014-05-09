package com.chdryra.android.reviewer;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class FragmentReviewComment extends FragmentReviewGrid {
	
	public static final String EXTRA_COMMENT_STRING = "com.chdryra.android.reviewer.comment_string";
	
	private static final int MIN_COMMENT_EDITTEXT_LINES = 5;
	private static final int MAX_COMMENT_EDITTEXT_LINES = 10;

	private ControllerReviewNode mController;
	
	private MenuItem mClearCommentMenuItem;
	private Drawable mClearCommentIcon;  
	private EditText mCommentEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mController = Controller.unpack(getActivity().getIntent().getExtras());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_review_comment, container, false);						
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	
		mCommentEditText = (EditText)v.findViewById(R.id.comment_edit_text);
		
		initUI();
		updateUI();
		
		return v;
	}

	@Override
	protected void initUI() {
		getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		mCommentEditText.setHorizontallyScrolling(false);
		mCommentEditText.setMinLines(MIN_COMMENT_EDITTEXT_LINES);
		
		//To allow scrolling within edit text 
		//if contains string by disallowing scrollview scrolling.
		mCommentEditText.setOnTouchListener(new View.OnTouchListener() {
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
		
		mCommentEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				updateClearCommentMenuItemVisibility();
			}	
		});
		
		mCommentEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) {
		        if (hasFocus)
		            getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		    }
		});
		
	}

	@Override
	protected void updateUI() {
		mCommentEditText.setText(mController.getCommentString());
	}
	
	private void updateClearCommentMenuItemVisibility() {
		//Have to hack as setting visibility relegates icon to overflow
		if(mCommentEditText != null &&  mCommentEditText.getText().toString() != null
				&& mCommentEditText.getText().toString().length() > 0) {
			mClearCommentIcon.setAlpha(100);
			mClearCommentMenuItem.setEnabled(true);
		} else {
			mClearCommentIcon.setAlpha(0);
			mClearCommentMenuItem.setEnabled(false);
		}
		
		mClearCommentMenuItem.setIcon(mClearCommentIcon);
	}
	
	@Override
	protected void sendResult(int resultCode) {
		if(resultCode == Activity.RESULT_OK)			
			mController.setComment(mCommentEditText.getText().toString());

		super.sendResult(resultCode);
	}
	
	@Override
	protected void deleteData() {
		mController.deleteComment();
	}
	
	@Override
	protected boolean hasData() {
		return mController.hasComment();
	}
	
	@Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    	mClearCommentMenuItem = menu.findItem(R.id.menu_item_clear);
    	mClearCommentIcon = getResources().getDrawable(R.drawable.ic_clear_search_api_holo_light);
		updateClearCommentMenuItemVisibility();
    }
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_clear_delete_done, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_item_clear:
				if(mCommentEditText != null)
					mCommentEditText.setText(null);
				return true;
				
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected String getDeleteConfirmationTitle() {
		return getResources().getString(R.string.activity_title_comment);
	}
}
