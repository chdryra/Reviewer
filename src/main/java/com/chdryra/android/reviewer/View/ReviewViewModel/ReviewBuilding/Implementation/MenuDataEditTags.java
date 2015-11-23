package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvTag;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuDataEditTags extends MenuDataEdit<GvTag> {
    private static final GvDataType<GvTag> TYPE = GvTag.TYPE;
    private TagAdjuster mTagAdjuster;


    public MenuDataEditTags(TagAdjuster tagAdjuster) {
        super(TYPE);
        mTagAdjuster = tagAdjuster;
    }

    @Override
    protected void doDoneSelected() {
        mTagAdjuster.adjustTagsIfNecessary(getEditor());
        super.doDoneSelected();
    }

    @Override
    protected void doDeleteSelected() {
        super.doDeleteSelected();
        mTagAdjuster.setCurrentSubjectTag(null);
        mTagAdjuster.adjustTagsIfNecessary(getEditor());
    }
}
