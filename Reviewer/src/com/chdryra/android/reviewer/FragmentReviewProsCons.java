package com.chdryra.android.reviewer;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chdryra.android.myandroidwidgets.ClearableEditText;

public class FragmentReviewProsCons extends FragmentReviewGrid {
	public static final String PROCON = "com.chdryra.android.reviewer.pro_con";
	public static final String PROCON_HINT = "com.chdryra.android.reviewer.pro_con_hint";
	public static final String DIALOG_PROCON_TAG = "ProConDialog";
	
	private static final int PRO_EDIT = 4;
	private static final int CON_EDIT = 5;
	
	private ControllerReviewNode mController;
	
	private ArrayList<String> mPros; 
	private ArrayList<String> mCons;
	
	private ClearableEditText mProEditText;
	private ClearableEditText mConEditText;
	private ListView mProsListView;
	private ListView mConsListView;
	
	private int mProTextColour;
	private int mConTextColour;
	private String mProHint;
	private String mConHint;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mController = Controller.unpack(getActivity().getIntent().getExtras());
		
		mPros = mController.hasProsCons()? mController.getPros() :  new ArrayList<String>();
		mCons = mController.hasProsCons()? mController.getCons() :  new ArrayList<String>();
		mProTextColour = getResources().getColor(R.color.Chartreuse);
		mConTextColour = getResources().getColor(R.color.Crimson);
		mProHint = getResources().getString(R.string.edit_text_pro_hint);
		mConHint = getResources().getString(R.string.edit_text_con_hint);
		
		setDeleteWhatTitle(getResources().getString(R.string.activity_title_procon));
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.fragment_review_proscons, container, false);				
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		RelativeLayout prosLayout = (RelativeLayout)v.findViewById(R.id.pros_layout);
		RelativeLayout consLayout = (RelativeLayout)v.findViewById(R.id.cons_layout);
		
		mProEditText = (ClearableEditText)prosLayout.findViewById(R.id.procon_edit_text);
		mConEditText = (ClearableEditText)consLayout.findViewById(R.id.procon_edit_text);
		
		mProsListView = (ListView)prosLayout.findViewById(R.id.procon_listview);
		mConsListView = (ListView)consLayout.findViewById(R.id.procon_listview);
		
		initUI();
		updateUI();
		
		return v;
	}
	
	@Override
	protected void initUI() {
		getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		mProEditText.setHint(R.string.edit_text_pro_hint);
		mProEditText.setTextColor(mProTextColour);
		mProEditText.setHintTextColor(mProTextColour);
		mProEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_GO)
					addProCon(mProEditText, mPros);
					
	            mProEditText.requestFocus();
	            return true;
	        }
	    });

		mConEditText.setHint(R.string.edit_text_con_hint);
		mConEditText.setTextColor(mConTextColour);
		mConEditText.setHintTextColor(mConTextColour);
		mConEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_GO)
	            	addProCon(mConEditText, mCons);
					
	            mConEditText.requestFocus();
	            return true;
	        }
	    });

		mProsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v, int pos, long id) {
				ReviewProConAdaptor adapter = (ReviewProConAdaptor)parent.getAdapter();
				String pro = (String)adapter.getItem(pos);
				showProConDialog(pro, PRO_EDIT, mProHint);
				return true;
			}
		});

		mConsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v, int pos, long id) {
				ReviewProConAdaptor adapter = (ReviewProConAdaptor)parent.getAdapter();
				String con = (String)adapter.getItem(pos);
				showProConDialog(con, CON_EDIT, mConHint);
				return true;
			}
		});

		
		mProsListView.setAdapter(new ReviewProConAdaptor(mPros, mProTextColour));
		mConsListView.setAdapter(new ReviewProConAdaptor(mCons, mConTextColour));
	}

	@Override
	protected void updateUI() {
		((ReviewProConAdaptor)mProsListView.getAdapter()).notifyDataSetChanged();
		((ReviewProConAdaptor)mConsListView.getAdapter()).notifyDataSetChanged();
	}

	private void showProConDialog(String proCon, int requestCode, String proConHint) {
		DialogProConFragment dialog = new DialogProConFragment();
		dialog.setTargetFragment(FragmentReviewProsCons.this, requestCode);
		Bundle args = new Bundle();
		args.putString(PROCON, proCon);
		args.putString(PROCON_HINT, proConHint);
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), DIALOG_PROCON_TAG);	
	}
	
	private void addProCon(ClearableEditText editText, ArrayList<String> proCons) {
		String proCon = editText.getText().toString();
		if(proCon == null || proCon.length() == 0) {
			Toast.makeText(getSherlockActivity(), getResources().getString(R.string.toast_enter_procon), Toast.LENGTH_SHORT).show();
			return;
		}

		proCons.add(proCon);
		editText.setText(null);

		updateUI();
	}

	@Override
	protected void onDoneSelected() {
		mController.setProsCons(mPros, mCons);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
		switch(requestCode) {
			case PRO_EDIT:
				updateProCon(resultCode, data, mPros);
				break;
			case CON_EDIT:
				updateProCon(resultCode, data, mCons);
				break;
			default:
				super.onActivityResult(requestCode, resultCode, data);
				break;
		}
		
		updateUI();				
	}

	private void updateProCon(int resultCode, Intent data, ArrayList<String> proCons) {
		switch(ActivityResultCode.get(resultCode)) {
			case OK:
				String oldPro = (String)data.getSerializableExtra(DialogProConFragment.PROCON_OLD);
				String newPro = (String)data.getSerializableExtra(PROCON);
				proCons.remove(oldPro);
				proCons.add(newPro);
				break;
			case DELETE:
				String toDelete = (String)data.getSerializableExtra(DialogProConFragment.PROCON_OLD);
				proCons.remove(toDelete);
				break;
			default:
				return;
		}
	}
	
	@Override
	protected void onDeleteSelected() {
		mController.deleteProsCons();
		mPros.clear();
		mCons.clear();
	}

	@Override
	protected boolean hasDataToDelete() {
		return mController.hasProsCons();
	}

	class ReviewProConAdaptor extends BaseAdapter {	
		private ArrayList<String> mData;
		private int mTextColour;
		
		public ReviewProConAdaptor(ArrayList<String> data, int textColour){
		    mData = data;
		    mTextColour = textColour;
		}
			
		@Override
		public int getCount() {
			return mData.size();
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public Object getItem(int position) {
			return mData.get(position);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh;
			
			if (convertView == null) {						
				LayoutInflater inflater = getSherlockActivity().getLayoutInflater();
				convertView = inflater.inflate(R.layout.procon_row, parent, false);
				
				TextView proCon = (TextView)convertView.findViewById(R.id.procon_text_view);
				
				vh = new ViewHolder();
				vh.proCon = proCon;
				
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder)convertView.getTag();
			}
				
			String proCon = (String)getItem(position);
			
			vh.proCon.setText(proCon);			
			vh.proCon.setTextColor(mTextColour);
			
			return(convertView);
		};
	};
	
	static class ViewHolder {
	    public TextView proCon;
	}
}
