package kr.or.ddit.lab03;

import kr.or.ddit.mbti.service.MbtiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.inject.Inject;

@SpringJUnitConfig(locations = "file:src/main/resources/kr/or/ddit/lab03/conf/context-*.xml")
public class TestLab03Context {

    @Inject
    MbtiService service;

    @Test
    void test() {
        service.readMbtiList();
    }
}
