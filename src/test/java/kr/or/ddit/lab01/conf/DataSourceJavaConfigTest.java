package kr.or.ddit.lab01.conf;

import kr.or.ddit.vo.MbtiVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringJUnitConfig(classes = DataSourceJavaConfig.class)
class DataSourceJavaConfigTest {


    @Inject
    @Named("dataSource")
    DataSource dataSource;

    @Inject
    JdbcTemplate jdbcTemplate;

    @Inject
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    void testNamedParameterUsingJavaBean() {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT MT_TITLE, MT_TYPE, MT_SORT, MT_CONTENT ");
        sql.append(" FROM TB_MBTIDESC");
        sql.append(" WHERE MT_TYPE = :mtType AND MT_SORT = :mtSort ");

        RowMapper<MbtiVO> rm = (rs, rowNum) -> {
            MbtiVO mbti = new MbtiVO();
            mbti.setMtSort(rs.getInt("MT_SORT"));
            mbti.setMtType(rs.getString("MT_TYPE"));
            mbti.setMtTitle(rs.getString("MT_TITLE"));
            mbti.setMtContent(rs.getString("MT_CONTENT"));
            return mbti;
        };

//        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("mtSort", 13);
//        paramMap.put("mtType", "estj");

        MbtiVO paramVO = new MbtiVO();
        paramVO.setMtSort(13);
        paramVO.setMtType("estj");
        MbtiVO mbti = namedParameterJdbcTemplate.queryForObject(sql.toString(),
                new BeanPropertySqlParameterSource(paramVO), rm);
        log.info(mbti.toString());
    }

    @Test
    void testNamedParameter() {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT MT_TITLE, MT_TYPE, MT_SORT, MT_CONTENT ");
        sql.append(" FROM TB_MBTIDESC");
        sql.append(" WHERE MT_TYPE = :mtType AND MT_SORT = :mtSort ");

        RowMapper<MbtiVO> rm = (rs, rowNum) -> {
            MbtiVO mbti = new MbtiVO();
            mbti.setMtSort(rs.getInt("MT_SORT"));
            mbti.setMtType(rs.getString("MT_TYPE"));
            mbti.setMtTitle(rs.getString("MT_TITLE"));
            mbti.setMtContent(rs.getString("MT_CONTENT"));
            return mbti;
        };

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("mtSort", 13);
        paramMap.put("mtType", "estj");
        MbtiVO mbti = namedParameterJdbcTemplate.queryForObject(sql.toString(), paramMap, rm);
        log.info(mbti.toString());
    }

    @Test
    void testTemplateOne() {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT MT_TITLE, MT_TYPE, MT_SORT, MT_CONTENT ");
        sql.append(" FROM TB_MBTIDESC");

        RowMapper<MbtiVO> rm = new RowMapper<MbtiVO>() {
            @Override
            public MbtiVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                MbtiVO mbti = new MbtiVO();
                mbti.setMtSort(rs.getInt("MT_SORT"));
                mbti.setMtType(rs.getString("MT_TYPE"));
                mbti.setMtTitle(rs.getString("MT_TITLE"));
                mbti.setMtContent(rs.getString("MT_CONTENT"));
                return mbti;
            }
        };
        List<MbtiVO> result = jdbcTemplate.query(sql.toString(), rm);
        result.forEach(t -> log.info(t.toString()));
    }

    @Test
    void testTemplate() {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT MT_TITLE, MT_TYPE, MT_SORT, MT_CONTENT ");
        sql.append(" FROM TB_MBTIDESC");
        sql.append(" WHERE MT_TYPE = ? AND MT_SORT = ? ");

        RowMapper<MbtiVO> rm = new RowMapper<MbtiVO>() {
            @Override
            public MbtiVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                MbtiVO mbti = new MbtiVO();
                mbti.setMtSort(rs.getInt("MT_SORT"));
                mbti.setMtType(rs.getString("MT_TYPE"));
                mbti.setMtTitle(rs.getString("MT_TITLE"));
                mbti.setMtContent(rs.getString("MT_CONTENT"));
                return mbti;
            }
        };
        MbtiVO result = jdbcTemplate.queryForObject(sql.toString(), new Object[] {13, "estj"}, rm);
        log.info(result.toString());
    }

    @Test
    void dataSource() {

        assertDoesNotThrow(() ->{
                try (
                    Connection conn = dataSource.getConnection();
            ){
                    log.info("수립된 연결 객체 : {}", conn);
            }
        });
    }
}