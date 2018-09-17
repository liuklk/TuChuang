package com.twentyfourhours.tuchuang.select.list.sortlistview;


import java.util.Comparator;

/**
 * Created by Administrator on 2018/3/17.
 */

public class PinyinComparator implements Comparator<SortModel> {
    public PinyinComparator() {
    }

    public int compare(SortModel o1, SortModel o2) {
        return !o1.getSortLetters().equals("@") && !o2.getSortLetters().equals("#")?(!o1.getSortLetters().equals("#") && !o2.getSortLetters().equals("@")?o1.getSortLetters().compareTo(o2.getSortLetters()):1):-1;
    }
}
