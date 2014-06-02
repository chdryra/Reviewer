package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.VHStringView;
import com.chdryra.android.reviewer.GVCommentList.GVComment;

public class VHCommentView extends VHStringView {
	private static final int LAYOUT = R.layout.grid_cell_comment;
	private static final int TEXTVIEW = R.id.text_view;

	public VHCommentView() {
		super(LAYOUT, TEXTVIEW, new GVDataStringGetter() {
			@Override
			public String getString(GVData data) {
				GVComment comment = (GVComment)data;
				return comment != null? comment.getCommentHeadline() : null;
			}
		});
	}
}
