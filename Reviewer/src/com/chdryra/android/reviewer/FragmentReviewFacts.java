package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.GridViewCellAdapter;
import com.chdryra.android.reviewer.GVFacts.GVFact;

public class FragmentReviewFacts extends FragmentReviewGridAddEdit {
	public static final String FACT_LABEL = "com.chdryra.android.reviewer.datum_label";
	public static final String FACT_VALUE = "com.chdryra.android.reviewer.datum_value";	
	
	private GVFacts mFacts; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFacts = getController().getFacts();
		setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_fact_title));
		setGridCellDimension(CellDimension.HALF, CellDimension.QUARTER);
		setBannerButtonText(getResources().getString(R.string.button_add_facts));
		setIsEditable(true);
	}
		
	@Override
	protected void onBannerButtonClick() {
		DialogShower.show(new DialogFactAddFragment(), FragmentReviewFacts.this, DATA_ADD, DATA_ADD_TAG, Controller.pack(getController()));
	}
	
	@Override
	protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
		Bundle args = Controller.pack(getController());
		GVFact fact = (GVFact)parent.getItemAtPosition(position);
		args.putString(FACT_LABEL, fact.getLabel());
		args.putString(FACT_VALUE, fact.getValue());
		DialogShower.show(new DialogFactEditFragment(), FragmentReviewFacts.this, DATA_EDIT, DATA_EDIT_TAG, args);
	}
			
	@Override
	protected void onDoneSelected() {
		getController().setFacts(mFacts);
	}

	@Override
	protected void onDeleteSelected() {
		mFacts.removeAll();
	}

	@Override
	protected boolean hasDataToDelete() {
		return mFacts.size() > 0;
	}
	
	@Override
	protected GridViewCellAdapter getGridViewCellAdapter() {
		return new GridViewCellAdapter(getActivity(), 
				mFacts, 
				R.layout.grid_cell_fact, 
				getGridCellWidth(), getGridCellHeight());
	}

	@Override
	protected void addData(int resultCode, Intent data) {
		switch(ActivityResultCode.get(resultCode)) {
		case ADD:
			String label = (String)data.getSerializableExtra(DialogFactAddFragment.FACT_LABEL);
			String value = (String)data.getSerializableExtra(DialogFactAddFragment.FACT_VALUE);
			if(label != null && label.length() > 0 && value != null && value.length() > 0)
				mFacts.add(new GVFact(label, value));
			break;
		default:
			return;
		}
	}
	
	@Override
	protected void editData(int resultCode, Intent data) {
		switch(ActivityResultCode.get(resultCode)) {
		case DONE:
			String oldLabel = (String)data.getSerializableExtra(DialogFactEditFragment.FACT_OLD_LABEL);
			String oldValue = (String)data.getSerializableExtra(DialogFactEditFragment.FACT_OLD_VALUE);
			String newLabel = (String)data.getSerializableExtra(FACT_LABEL);
			String newValue = (String)data.getSerializableExtra(FACT_VALUE);
			mFacts.remove(oldLabel, oldValue);
			mFacts.add(newLabel, newValue);
			break;
		case DELETE:
			String deleteLabel = (String)data.getSerializableExtra(DialogFactEditFragment.FACT_OLD_LABEL);
			String deleteValue = (String)data.getSerializableExtra(DialogFactEditFragment.FACT_OLD_VALUE);
			mFacts.remove(deleteLabel, deleteValue);
			break;
		default:
			return;
		}
	}
}
