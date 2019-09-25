package com.imooc.mapper;

import com.imooc.pojo.UserReport;
import com.imooc.pojo.UserReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserReportMapper {
    int countByExample(UserReportExample example);

    int deleteByExample(UserReportExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserReport record);

    int insertSelective(UserReport record);

    List<UserReport> selectByExample(UserReportExample example);

    UserReport selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserReport record, @Param("example") UserReportExample example);

    int updateByExample(@Param("record") UserReport record, @Param("example") UserReportExample example);

    int updateByPrimaryKeySelective(UserReport record);

    int updateByPrimaryKey(UserReport record);
}