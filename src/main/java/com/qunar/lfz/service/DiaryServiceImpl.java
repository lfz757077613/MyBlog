package com.qunar.lfz.service;

import java.util.List;

import com.qunar.lfz.dao.DiaryDao;
import com.qunar.lfz.model.Diary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiaryServiceImpl implements DiaryService {

	@Autowired
	private DiaryDao diaryDao;
	
	@Override
	public void writediary(Diary diary) {
		diaryDao.writediary(diary);
		
	}

	@Override
	public List<Diary> selectAllDiary() {
		return this.diaryDao.selectAllDiary();
	}

	@Override
	public void deleteDiaryById(int diaryid) {
		diaryDao.deleteDiaryById(diaryid);
		
	}

}
