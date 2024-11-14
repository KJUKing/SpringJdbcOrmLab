package kr.or.ddit.mbti.dao;

import kr.or.ddit.lab02.conf.SpringMybatisIntegrationJavaConfig;
import kr.or.ddit.vo.MbtiVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringJUnitConfig(classes = SpringMybatisIntegrationJavaConfig.class)
class MbtiMapperTest {

    @Autowired
    MbtiMapper dao;

    @BeforeEach
    public void setUpBefore(){
        log.info("주입된 dao : {}", dao);
    }

    @Test
    void insertMbti() {
    }

    @Test
    void selectMbti() {
    }

    @Test
    void selectMbtiList() {
        assertDoesNotThrow(() -> dao.selectMbtiList());
    }

    @Test
    void updateMbti() {
        MbtiVO mbti = dao.selectMbti("estj");

        mbti.setMtContent("변경함");
        assertEquals(1, dao.updateMbti(mbti)
        );

    }

    @Test
    void deleteMbti() {
    }
}