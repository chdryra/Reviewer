package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.GridViewCellAdapter;
import com.chdryra.android.reviewer.GVCommentList.GVComment;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

public class FragmentReviewComments extends FragmentReviewGridAddEditDone<GVComment> {
	public static final String COMMENT = "com.chdryra.android.reviewer.comment";	

	private GVCommentList mComments; 	
	private boolean mCommentsAreSplit = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mComments = (GVCommentList) setAndInitData(GVType.COMMENTS);
		setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_comment_title));
		setBannerButtonText(getResources().getString(R.string.button_add_comment));
	}
		
	@Override
	protected void onBannerButtonClick() {
		DialogShower.show(new DialogCommentAddFragment(), FragmentReviewComments.this, DATA_ADD, DATA_ADD_TAG, Controller.pack(getController()));
	}
	
	@Override
	protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
		GVComment comment = (GVComment)parent.getItemAtPosition(position);
		Bundle args = new Bundle();
		args.putString(COMMENT, comment.getUnSplitComment().getComment());
		DialogShower.show(new DialogCommentEditFragment(), FragmentReviewComments.this, DATA_EDIT, DATA_EDIT_TAG, args);
	}
			
	
	@Override
	protected void addData(int resultCode, Intent data) {
		switch(ActivityResultCode.get(resultCode)) {
		case ADD:
			String comment = (String)data.getSerializableExtra(DialogCommentAddFragment.COMMENT);
			if(comment != null && comment.length() > 0)
				mComments.add(comment);
			break;
		default:
			return;
		}
	}
	
	@Override
	protected void editData(int resultCode, Intent data) {
		switch(ActivityResultCode.get(resultCode)) {
		case DONE:
			String oldComment = (String)data.getSerializableExtra(DialogCommentEditFragment.COMMENT_OLD);
			String newComment = (String)data.getSerializableExtra(DialogCommentEditFragment.COMMENT_NEW);
			mComments.remove(oldComment);
			mComments.add(newComment);
			break;
		case DELETE:
			String toDelete = (String)data.getSerializableExtra(DialogCommentEditFragment.COMMENT_OLD);
			mComments.remove(toDelete);
			break;
		default:
			return;
		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_review_comment, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_split_comment:
			splitOrUnsplitComments(item);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
	
	@Override
	protected void updateGridDataUI() {
		if(mCommentsAreSplit)
			((GridViewCellAdapter)getGridView().getAdapter()).setData(mComments.getSplitComments());
		else
			((GridViewCellAdapter)getGridView().getAdapter()).setData(mComments);
	}
	
	private void splitOrUnsplitComments(MenuItem item) {
		mCommentsAreSplit = !mCommentsAreSplit;
		item.setIcon(mCommentsAreSplit? R.drawable.ic_action_return_from_full_screen : R.drawable.ic_action_full_screen);
		if(mCommentsAreSplit)
			Toast.makeText(getActivity(), R.string.toast_split_comment, Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(getActivity(), R.string.toast_unsplit_comment, Toast.LENGTH_SHORT).show();
		updateGridDataUI();
	}
}