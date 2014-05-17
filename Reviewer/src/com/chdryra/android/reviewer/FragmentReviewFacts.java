package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
		setAddDataButtonText(getResources().getString(R.string.button_add_facts));
		
		setIsEditable(true);
	}
		
	@Override
	protected void onAddDataButtonClick() {
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
			case FACTS_ADD:
				switch(ActivityResultCode.get(resultCode)) {
					case DONE:
						mFacts = getController().getFacts();
						break;
					default:
						break;
				}
				break;
			case FACT_EDIT:
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
						break;
				}
				break;
			
			default:
				super.onActivityResult(requestCode, resultCode, data);
				break;
		}
		
		updateUI();				
	}

	@Override
	protected void onDoneSelected() {
		if(mFacts.size() > 0)
			getController().setFacts(mFacts);
	}

	@Override
	protected void onDeleteSelected() {
		getController().deleteFacts();
		mFacts.removeAll();
	}

	@Override
	protected boolean hasDataToDelete() {
		return getController().hasFacts();
	}
	
	@Override
	protected void updateGridDataUI() {
		((GridViewCellAdapter)getGridView().getAdapter()).setData(mFacts);
	}
	
	@Override
	protected GridViewCellAdapter getGridViewCellAdapter() {
		return new GridViewCellAdapter(getActivity(), 
				getController().getFacts(), 
				R.layout.grid_cell_fact, 
				getGridCellWidth(), getGridCellHeight(), 
				getSubjectView().getTextColors().getDefaultColor());
	}
}
