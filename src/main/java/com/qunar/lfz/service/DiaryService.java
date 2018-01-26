package com.qunar.lfz.service;

import com.qunar.lfz.model.Diary;

import java.util.List;

public interface DiaryService {

    void writediary(Diary diary);

    List<Diary> selectAllDiary();

    void deleteDiaryById(int diaryid);

}
