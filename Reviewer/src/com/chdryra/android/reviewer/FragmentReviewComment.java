package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.GridViewCellAdapter;
import com.chdryra.android.reviewer.GVComments.GVComment;

public class FragmentReviewComment extends FragmentReviewGrid {
	public static final String COMMENT = "com.chdryra.android.reviewer.comment";	
	public static final String DIALOG_COMMENT_ADD_TAG = "CommentAddDialog";
	public static final String DIALOG_COMMENT_EDIT_TAG = "CommentEditDialog";

	public final static int COMMENT_ADD = 10;
	public final static int COMMENT_EDIT = 11;
	
	private GVComments mComments; 	
	private boolean mCommentsAreSplit = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mComments = getController().getComments();
		setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_comment_title));
		setGridCellDimension(CellDimension.HALF, CellDimension.QUARTER);
		setBannerButtonText(getResources().getString(R.string.button_add_comment));
		setIsEditable(true);
	}
		
	@Override
	protected void onBannerButtonClick() {
		DialogShower.show(new DialogCommentAddFragment(), FragmentReviewComment.this, COMMENT_ADD, DIALOG_COMMENT_ADD_TAG, Controller.pack(getController()));
	}
	
	@Override
	protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
		GVComment comment = (GVComment)parent.getItemAtPosition(position);
		Bundle args = new Bundle();
		args.putString(COMMENT, comment.getUnSplitComment().getComment());
		DialogShower.show(new DialogCommentEditFragment(), FragmentReviewComment.this, COMMENT_EDIT, DIALOG_COMMENT_EDIT_TAG, args);
	}
			
	@Override
	protected void onDoneSelected() {
		getController().setComments(mComments);
	}

	@Override
	protected void onDeleteSelected() {
		mComments.removeAll();
	}

	@Override
	protected boolean hasDataToDelete() {
		return mComments.size() > 0;
	}
	
	@Override
	protected GridViewCellAdapter getGridViewCellAdapter() {
		return new GridViewCellAdapter(getActivity(), 
				mComments, 
				R.layout.grid_cell_comment, 
				getGridCellWidth(), getGridCellHeight());
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
		switch(requestCode) {
		case COMMENT_ADD:
			addComment(resultCode, data);
			break;
		case COMMENT_EDIT:
			editComment(resultCode, data);
			break;
		default:
			super.onActivityResult(requestCode, resultCode, data);
			break;
		}
		
		updateUI();				
	}

	private void addComment(int resultCode, Intent data) {
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
	
	private void editComment(int resultCode, Intent data) {
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
