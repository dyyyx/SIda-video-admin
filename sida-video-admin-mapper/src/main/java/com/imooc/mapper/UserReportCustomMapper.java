package com.imooc.mapper;

import com.imooc.pojo.UserReport;
import com.imooc.pojo.UserReportExample;
import com.imooc.pojo.vo.Reports;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserReportCustomMapper {
    List<Reports> selectAllReport();
}