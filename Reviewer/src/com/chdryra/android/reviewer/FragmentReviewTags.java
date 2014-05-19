package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.GVStrings;
import com.chdryra.android.mygenerallibrary.GridViewCellAdapter;

public class FragmentReviewTags  extends FragmentReviewGrid {
	public final static String TAG_STRING = "com.chdryra.android.reviewer.tag_string";
	
	private final static String DIALOG_TAG_ADD_TAG = "TagAddDialog";
	private final static String DIALOG_TAG_EDIT_TAG = "TagEditDialog";
	
	public final static int TAG_ADD = 10;
	public final static int TAG_EDIT = 11;

	private GVStrings mTags;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		
		if(getController() == null)
			setController(Controller.addNewReviewInProgress());
		
		mTags = getController().getTags();
		
		setDismissOnDone(false);
		setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_tags_title));
		setGridCellDimension(CellDimension.HALF, CellDimension.QUARTER);
		setBannerButtonText(getResources().getString(R.string.button_add_tag));
		setIsEditable(true);
	}

	@Override
	protected void onBannerButtonClick() {
		DialogShower.show(new DialogTagAddFragment(), FragmentReviewTags.this, TAG_ADD, DIALOG_TAG_ADD_TAG, Controller.pack(getController()));
	}

	@Override
	protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
		Bundle args = Controller.pack(getController());
		args.putString(TAG_STRING, (String)parent.getItemAtPosition(position));
		DialogShower.show(new DialogTagEditFragment(), FragmentReviewTags.this, TAG_EDIT, DIALOG_TAG_EDIT_TAG, args);
	}

	@Override
	protected GridViewCellAdapter getGridViewCellAdapter() {
		return new GridViewCellAdapter(getActivity(), mTags, 
				R.layout.grid_cell_tag, getGridCellWidth(), getGridCellHeight());
	}

	@Override
	protected void onUpSelected() {
		if (NavUtils.getParentActivityName(getSherlockActivity()) != null) {
			Intent j = NavUtils.getParentActivityIntent(getSherlockActivity());
			NavUtils.navigateUpTo(getActivity(), j);
		}
	}
	
	@Override
	protected void onDoneSelected() {
		if(mTags.size() == 0) {
			Toast.makeText(getActivity(), R.string.toast_enter_tag, Toast.LENGTH_SHORT).show();
			return;
		}
		
		getController().setTags(mTags);
		Intent i = new Intent(getActivity(), ActivityReviewEdit.class);
		Controller.pack(getController(), i);
		startActivity(i);
	}
	
	@Override
	protected void onDeleteSelected() {
		mTags.removeAll();
	}

	@Override
	protected boolean hasDataToDelete() {
		return mTags.size() > 0;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
		switch(requestCode) {
		case TAG_ADD:
			addTag(resultCode, data);
			break;
		case TAG_EDIT:
			editTag(resultCode, data);
			break;
		default:
			super.onActivityResult(requestCode, resultCode, data);
			break;
		}
		
		updateUI();				
	}

	private void addTag(int resultCode, Intent data) {
		switch(ActivityResultCode.get(resultCode)) {
		case ADD:
			String tag = (String)data.getSerializableExtra(DialogTagAddFragment.TAG);
			if(tag != null && tag.length() > 0)
				mTags.add(tag);
			break;
		default:
			return;
		}
	}
	
	private void editTag(int resultCode, Intent data) {
		switch(ActivityResultCode.get(resultCode)) {
		case DONE:
			String oldTag = (String)data.getSerializableExtra(DialogTagEditFragment.TAG_OLD);
			String newTag = (String)data.getSerializableExtra(DialogTagEditFragment.TAG_NEW);
			mTags.remove(oldTag);
			mTags.add(newTag);
			break;
		case DELETE:
			String toDelete = (String)data.getSerializableExtra(DialogTagEditFragment.TAG_OLD);
			mTags.remove(toDelete);
			break;
		default:
			return;
		}
	}
}
