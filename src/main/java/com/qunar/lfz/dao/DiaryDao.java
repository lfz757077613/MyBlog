package com.qunar.lfz.dao;

import com.qunar.lfz.model.Diary;

import java.util.List;

public interface DiaryDao {

    void writediary(Diary diary);

    List<Diary> selectAllDiary();

    void deleteDiaryById(int diaryid);

}
