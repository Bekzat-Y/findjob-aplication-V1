package com.findjob.findjobbackend.service.iReqruiment;

import com.findjob.findjobbackend.dto.request.SearchJob;
import com.findjob.findjobbackend.dto.response.RecruitmentNewDTO;
import com.findjob.findjobbackend.repository.IRecruitmentNewDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@Repository
public class IRecruitmentNewDAOImpl implements IRecruitmentNewDAO {

    private static final Logger logger = LoggerFactory.getLogger(IRecruitmentNewDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<RecruitmentNewDTO> findJob(SearchJob searchJob) {
        if (searchJob == null) {
            logger.error("SearchJob parameter is null");
            throw new IllegalArgumentException("SearchJob parameter cannot be null");
        }

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("produces")
                .returningResultSet("recuitmentnew", (RowMapper<RecruitmentNewDTO>) (rs, rowNum) -> {
                    RecruitmentNewDTO recruitmentNewDTO = new RecruitmentNewDTO();
                    recruitmentNewDTO.setId(rs.getLong("id"));
                    recruitmentNewDTO.setTitle(rs.getString("title"));
                    recruitmentNewDTO.setDescription(rs.getString("description"));
                    recruitmentNewDTO.setStatus(rs.getString("status"));
                    recruitmentNewDTO.setCompanyId(rs.getLong("companyId"));
                    recruitmentNewDTO.setCityId(rs.getLong("cityId"));
                    recruitmentNewDTO.setCityName(rs.getString("cityName"));
                    recruitmentNewDTO.setCompanyName(rs.getString("companyName"));
                    recruitmentNewDTO.setFieldId(rs.getLong("fieldId"));
                    recruitmentNewDTO.setFieldName(rs.getString("fieldName"));
                    recruitmentNewDTO.setVacanciesId(rs.getLong("vacanciesId"));
                    recruitmentNewDTO.setVacanciesName(rs.getString("vacanciesName"));
                    recruitmentNewDTO.setWorkingTimeId(rs.getLong("workingTimeId"));
                    recruitmentNewDTO.setWorkingTimeName(rs.getString("workingTimeName"));
                    recruitmentNewDTO.setAvatar(rs.getString("avatar"));
                    recruitmentNewDTO.setSalary(rs.getInt("salary"));
                    recruitmentNewDTO.setExpDate(rs.getString("expDate"));
                    return recruitmentNewDTO;
                });
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("title", searchJob.getTitle())
                .addValue("cityId", searchJob.getCityId())
                .addValue("fieldId", searchJob.getFieldId())
                .addValue("companyId", searchJob.getCompanyId())
                .addValue("vacancies", searchJob.getVacancies())
                .addValue("workingTimeId", searchJob.getWorkingTimeId())
                .addValue("start", searchJob.getStart())
                .addValue("page_size", searchJob.getPageSize())
                .addValue("salary", searchJob.getSalary());
        return (List<RecruitmentNewDTO>) simpleJdbcCall.execute(in);
    }
}

