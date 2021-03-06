package com.tech.cloudnausor.ohmytennispro.Calenderpackage;


import androidx.annotation.IntDef;

@IntDef({MonthIndex.PREVIOUS_MONTH, MonthIndex.FOCUSED_MONTH, MonthIndex.NEXT_MONTH})
@interface MonthIndex {
    int PREVIOUS_MONTH = -1;
    int FOCUSED_MONTH = 0;
    int NEXT_MONTH = 1;
}