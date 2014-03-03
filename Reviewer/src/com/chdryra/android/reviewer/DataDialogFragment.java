package com.chdryra.android.reviewer;

import java.util.Iterator;
import java.util.LinkedHashMap;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.chdryra.android.reviewer.ReviewData.Datum;

public class DataDialogFragment extends BasicDialogFragment {

	private Review mReview;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_data, null);
		TableLayout dataTable = (TableLayout)v.findViewById(R.id.data_table_layout);
		
		mReview = (Review)IntentObjectHolder.getObject(ReviewOptionsFragment.REVIEW_OBJECT);
		LinkedHashMap<String, Datum> dataMap = mReview.getData().getDataMap();
		Iterator<Datum> it = dataMap.values().iterator();
		boolean dark = true;
		while (it.hasNext()) {
			Datum datum = (Datum) it.next();
			
			View datumRow = getSherlockActivity().getLayoutInflater().inflate(R.layout.datum_row, null);
			TextView label = (TextView)datumRow.findViewById(R.id.datum_label_text_view);
			TextView value = (TextView)datumRow.findViewById(R.id.datum_value_text_view);
			value.setGravity(Gravity.RIGHT);
			
			label.setText(datum.getLabel());
			value.setText(datum.getValue());
		
			datumRow.setBackgroundResource(dark == true? android.R.drawable.divider_horizontal_bright: android.R.drawable.divider_horizontal_dark);
			dataTable.addView(datumRow);
			
			dark = !dark;
		}

		return buildDialog(v);
	}
}
