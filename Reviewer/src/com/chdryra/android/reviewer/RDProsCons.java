package com.chdryra.android.reviewer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RDProsCons implements RData, Iterable<RDProCon> {

	private Review mHoldingReview;
	private ArrayList<RDProCon> mProsCons;
	
	public RDProsCons() {
		mProsCons = new ArrayList<RDProCon>();
	}
	
	public RDProsCons(Review holdingReview) {
		mHoldingReview = holdingReview;
		mProsCons = new ArrayList<RDProCon>();
	}
	
	public void addPro(String pro) {
		mProsCons.add(new RDProCon(pro, true, mHoldingReview));
	}

	private void add(RDProCon proCon) {
		mProsCons.add(proCon);
	}
	
	public void addCon(String con) {
		mProsCons.add(new RDProCon(con, false, mHoldingReview));
	}
	
	public int getNumberOfPros() {
		return getPros().size();
	}
	
	public int getNumberOfCons() {
		return getCons().size();
	}
	
	private int size() {
		return mProsCons.size();
	}
	
	private RDProsCons get(boolean getPros) {
		RDProsCons prosOrCons = new RDProsCons(mHoldingReview);
		for(RDProCon pc : this)
			if(pc.isPro() == getPros)
				prosOrCons.add(pc);
		
		return prosOrCons;
	}

	public RDProsCons getPros() {
		return get(true);
	}
	
	public RDProsCons getCons() {
		return get(false);
	}
	
	@Override
	public void setHoldingReview(Review review) {
		mHoldingReview = review;
	}

	@Override
	public Review getHoldingReview() {
		return mHoldingReview;
	}

	@Override
	public boolean hasData() {
		return mProsCons.size() > 0;
	}

	public RDProCon getItem(int position) {
		return mProsCons.get(position);
	}
	
	@Override
	public Iterator<RDProCon> iterator() {
		return new ProConIterator();
	}

	class ProConIterator implements Iterator<RDProCon> {
		int mPosition = 0;
		
		@Override
		public boolean hasNext() {
			return mPosition < mProsCons.size() && getItem(mPosition) != null;
		}

		@Override
		public RDProCon next() {
			if(hasNext())
				return getItem(mPosition++);
			else
				throw new NoSuchElementException("No more elements left");
		}

		@Override
		public void remove() {
			if(mPosition <= 0) {
				throw new IllegalStateException("Have to do at least one next() before you can delete");
			} else
				mProsCons.remove(mPosition-1);
		}
	}
}
