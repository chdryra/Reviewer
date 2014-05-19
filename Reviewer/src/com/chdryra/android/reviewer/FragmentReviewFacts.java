package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.GridViewCellAdapter;
import com.chdryra.android.reviewer.GVFacts.GVFact;

public class FragmentReviewFacts extends FragmentReviewGrid {
	public static final String FACT_LABEL = "com.chdryra.android.reviewer.datum_label";
	public static final String FACT_VALUE = "com.chdryra.android.reviewer.datum_value";	
	public static final String DIALOG_FACT_ADD_TAG = "FactAddDialog";
	public static final String DIALOG_FACT_EDIT_TAG = "FactEditDialog";

	public final static int FACTS_ADD = 40;
	public final static int FACT_EDIT = 41;
	
	private GVFacts mFacts; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFacts = getController().getFacts();
		setDeleteWhatTitle(getResources().getString(R.string.activity_title_facts));
		setGridCellDimension(CellDimension.HALF, CellDimension.QUARTER);
		setBannerButtonText(getResources().getString(R.string.button_add_facts));
		setIsEditable(true);
	}
		
	@Override
	protected void onBannerButtonClick() {
		DialogShower.show(new DialogFactAddFragment(), FragmentReviewFacts.this, FACTS_ADD, DIALOG_FACT_ADD_TAG, Controller.pack(getController()));
	}
	
	@Override
	protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
		Bundle args = Controller.pack(getController());
		GVFact fact = (GVFact)parent.getItemAtPosition(position);
		args.putString(FACT_LABEL, fact.getLabel());
		args.putString(FACT_VALUE, fact.getValue());
		DialogShower.show(new DialogFactEditFragment(), FragmentReviewFacts.this, FACT_EDIT, DIALOG_FACT_EDIT_TAG, args);
	}
			
	@Override
	protected void onDoneSelected() {
		if(mFacts.size() > 0)
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
		switch(requestCode) {
		case FACTS_ADD:
			addFact(resultCode, data);
			break;
		case FACT_EDIT:
			editFact(resultCode, data);
			break;
		default:
			super.onActivityResult(requestCode, resultCode, data);
			break;
		}
		
		updateUI();				
	}

	private void addFact(int resultCode, Intent data) {
		switch(ActivityResultCode.get(resultCode)) {
		case ADD:
			String label = (String)data.getSerializableExtra(DialogFactAddFragment.FACT_LABEL);
			String value = (String)data.getSerializableExtra(DialogFactAddFragment.FACT_VALUE);
			if(label != null && label.length() > 0 && value != null && value.length() > 0)
				mFacts.add(label, value);
			break;
		default:
			return;
		}
	}
	
	private void editFact(int resultCode, Intent data) {
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
