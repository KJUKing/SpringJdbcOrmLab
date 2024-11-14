package kr.or.ddit.mbti.service;

import java.util.List;
import java.util.Optional;

import kr.or.ddit.mbti.dao.MbtiMapper;
import kr.or.ddit.vo.MbtiVO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class MbtiServiceImpl implements MbtiService {
	@Inject
	private MbtiMapper dao;

	@Override
	public void createMbti(MbtiVO mbti) {
		dao.insertMbti(mbti);

	}

	@Override
	public MbtiVO readMbti(String mtType) {
		return Optional.ofNullable(dao.selectMbti(mtType)).get();
	}

	@Override
	public List<MbtiVO> readMbtiList() {
		return dao.selectMbtiList();
	}

	@Override
	public void modifyMbti(MbtiVO mbti) {
		dao.updateMbti(mbti);
	}

	@Override
	public void removeMbti(String mtType) {
		dao.deleteMbti(mtType);
	}

}
