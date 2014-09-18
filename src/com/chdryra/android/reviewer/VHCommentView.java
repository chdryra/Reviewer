package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.reviewer.GVCommentList.GVComment;

public class VHCommentView extends VHTextView {

	public VHCommentView() {
		super(new GVDataStringGetter() {
			@Override
			public String getString(GVData data) {
				GVComment comment = (GVComment)data;
				return comment != null? comment.getCommentHeadline() : null;
			}
		});
		
	}
}
