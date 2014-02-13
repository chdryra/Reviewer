package com.chdryra.android.reviewer;

import java.util.Iterator;
import java.util.LinkedHashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class CommentDialogFragment extends SherlockDialogFragment {
	public static final String EXTRA_COMMENT_STRING = "com.chdryra.android.reviewer.comment_string";
	public static final int RESULT_DELETE_COMMENT = Activity.RESULT_FIRST_USER;

	private Review mReview;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		ScrollView scrollView = new ScrollView(getSherlockActivity());
		final AlertDialog dialog = buildDialog(scrollView);
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		
		LinearLayout linearlayout = new LinearLayout(getSherlockActivity());
		linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));        
		
		mReview = (Review)IntentObjectHolder.getObject(ReviewFinishFragment.REVIEW_OBJECT);
		
		linearlayout.addView(getCommentLineView(dialog, mReview));
		LinkedHashMap<String, Criterion> criteria = mReview.getCriteriaList().getCriterionHashMap();
		Iterator<Criterion> it = criteria.values().iterator();
		while (it.hasNext()) {
			final Criterion criterion = it.next();			
			linearlayout.addView(getCommentLineView(dialog, criterion));
		}
		
		scrollView.addView(linearlayout);
		
		return dialog;
		}

	@Override
	public void onPause() {
		IntentObjectHolder.addObject(ReviewFinishFragment.REVIEW_OBJECT, mReview);
		super.onPause();
	}
	
	@Override
	public void onSaveInstanceState(Bundle arg0) {
		IntentObjectHolder.addObject(ReviewFinishFragment.REVIEW_OBJECT, mReview);
		super.onSaveInstanceState(arg0);
	}
	
	private View getCommentLineView(final AlertDialog dialog, final Commentable commentable) {
		View v = getSherlockActivity().getLayoutInflater().inflate(R.layout.dialog_comment, null);
		TextView criterionName = (TextView)v.findViewById(R.id.comment_text_view);
		criterionName.setText(commentable.getCommentTitle());
		EditText comment = (EditText)v.findViewById(R.id.comment_edit_text);
		
		if( getTargetRequestCode() == ReviewFinishFragment.COMMENT_EDIT )
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
		            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		        }
		    }
		});
		
		return v;
	}
	
	private void sendResult(int resultCode) {
		if (getTargetFragment() == null || resultCode == Activity.RESULT_CANCELED) {
			return;
		}
		
		Intent i = new Intent();
		
		if (resultCode == Activity.RESULT_OK)
			i.putExtra(EXTRA_COMMENT_STRING, mReview.getCommentIncludingCriteria());	
		
		if (resultCode == RESULT_DELETE_COMMENT)
			mReview.deleteCommentIncludingCriteria();
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
	private AlertDialog buildDialog(View v) {
		return new AlertDialog.Builder(getActivity()).
				setView(v).
				setTitle(R.string.dialog_comment_title).
				setPositiveButton(R.string.button_done_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(Activity.RESULT_OK);
					}
				}).
				setNeutralButton(R.string.button_cancel_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(Activity.RESULT_CANCELED);
					}
				}).
				setNegativeButton(R.string.button_delete_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(RESULT_DELETE_COMMENT);
					}
				}).
				create();
	}

}
