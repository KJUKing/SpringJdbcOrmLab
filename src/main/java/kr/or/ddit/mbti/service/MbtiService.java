package kr.or.ddit.mbti.service;

import java.util.List;

import kr.or.ddit.vo.MbtiVO;

public interface MbtiService {
	public void createMbti(MbtiVO mbti);
	public MbtiVO readMbti(String mtType);
	public List<MbtiVO> readMbtiList();
	public void modifyMbti(MbtiVO mbti);
	public void removeMbti(String mtType);
}
