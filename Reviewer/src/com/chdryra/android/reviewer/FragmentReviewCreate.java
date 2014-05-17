package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.GridViewCellAdapter;

public class FragmentReviewCreate  extends FragmentReviewGrid {
	public final static String TAG_EDIT_STRING = "com.chdryra.android.reviewer.tag_edit_string";
	
	private final static String DIALOG_TAG_ADD_TAG = "TagAddDialog";
	private final static String DIALOG_TAG_EDIT_TAG = "TagEditDialog";
	
	public final static int TAG_ADD = 10;
	public final static int TAG_EDIT = 11;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		
		if(getController() == null)
			setController(Controller.addNewReviewInProgress());
		
		setDismissOnDone(false);
		setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_tags_title));
		
		setGridCellDimension(CellDimension.HALF, CellDimension.QUARTER);
		setAddDataButtonText(getResources().getString(R.string.button_add_tag));
		setIsEditable(true);
	}

	@Override
	protected void onAddDataButtonClick() {
		DialogShower.show(new DialogTagAddFragment(), FragmentReviewCreate.this, TAG_ADD, DIALOG_TAG_ADD_TAG, Controller.pack(getController()));
	}

	@Override
	protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
		Bundle args = Controller.pack(getController());
		args.putString(TAG_EDIT_STRING, (String)parent.getItemAtPosition(position));
		DialogShower.show(new DialogTagEditFragment(), FragmentReviewCreate.this, TAG_EDIT, DIALOG_TAG_EDIT_TAG, args);
	}
	
	@Override
	protected void updateGridDataUI() {
		((GridViewCellAdapter)getGridView().getAdapter()).setData(getController().getTags());
	};

	@Override
	protected GridViewCellAdapter getGridViewCellAdapter() {
		return new GridViewCellAdapter(getActivity(), getController().getTags(), 
				R.layout.grid_cell_text_view, getGridCellWidth(), getGridCellHeight(), getSubjectView().getTextColors().getDefaultColor());
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
		if(getController().getTags().size() == 0) {
			Toast.makeText(getActivity(), R.string.toast_enter_tag, Toast.LENGTH_SHORT).show();
			return;
		}
		
		Intent i = new Intent(getActivity(), ActivityReviewEdit.class);
		Controller.pack(getController(), i);
		startActivity(i);
	}
	
	@Override
	protected void onDeleteSelected() {
		getController().removeTags();
	}

	@Override
	protected boolean hasDataToDelete() {
		return getController().hasTags();
	}
}
