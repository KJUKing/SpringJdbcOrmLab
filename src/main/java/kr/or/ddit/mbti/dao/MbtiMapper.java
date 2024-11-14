package kr.or.ddit.mbti.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.MbtiVO;


@Mapper
public interface MbtiMapper {
	public int insertMbti(MbtiVO mbti);
	public MbtiVO selectMbti(@Param("mtType") String mtType);
	public List<MbtiVO> selectMbtiList();
	public int updateMbti(MbtiVO mbti);
	public int deleteMbti(String mtType);
}
