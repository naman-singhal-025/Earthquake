package com.naman.earthquake;

import java.util.Comparator;

public class list_item{
    private String mMagnitude;
    private String mLocation;
    /** Time of the earthquake */
    private long mTimeInMilliseconds;
    private String murl;


    public list_item(String magnitude, String location, long timeInMilliseconds, String url) {
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
        murl = url;
    }

    public String getmMagnitude() {
        return mMagnitude;
    }

    public void setmMagnitude(String mMagnitude) {
        this.mMagnitude = mMagnitude;
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public void setmDate(long mDate) {
        mTimeInMilliseconds = mDate;
    }

    public String getUrl() {
        return murl;
    }

    //sort by magnitude
    public static Comparator<list_item> list_itemComparator = new Comparator<list_item>() {
        @Override
        public int compare(list_item l1, list_item l2) {
            String mag1 = l1.getmMagnitude();
            String mag2 = l2.getmMagnitude();
            return mag1.compareTo(mag2);
        }
    };
}
