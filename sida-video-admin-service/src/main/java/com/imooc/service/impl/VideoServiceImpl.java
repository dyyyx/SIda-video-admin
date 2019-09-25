package com.imooc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.enums.BGMOperatorTypeEnum;
import com.imooc.mapper.BgmMapper;
import com.imooc.mapper.UserReportCustomMapper;
import com.imooc.mapper.VideosMapper;
import com.imooc.pojo.Bgm;
import com.imooc.pojo.BgmExample;
import com.imooc.pojo.Videos;
import com.imooc.pojo.vo.Reports;
import com.imooc.service.VideoService;
import com.imooc.utils.PagedResult;
import com.imooc.web.util.ZKCurator;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	Sid sid;
	
	@Autowired
	BgmMapper bgmMapper;
	
	@Autowired
	UserReportCustomMapper userReportCustomMapper;
	
	@Autowired
	VideosMapper videoMapper;
	
	@Autowired
	ZKCurator zkCurator;
	
	@Override
	public PagedResult getReportList(Integer page, Integer pageSize) {
		List<Reports> reports = userReportCustomMapper.selectAllReport();
		PageHelper.startPage(page, pageSize);
		PageInfo<Reports> info = new PageInfo<>(reports);
		
		PagedResult result = new PagedResult();
		result.setRows(reports);
		result.setPage(page);
		result.setRecords(info.getTotal());
		result.setTotal(info.getPages());
		
		return result;
	}

	@Override
	public void updateVideoStatus(String videoId, int value) {
		Videos video = new Videos();
		video.setId(videoId);
		video.setStatus(value);
		videoMapper.updateByPrimaryKeySelective(video);
		
	}

	@Override
	public PagedResult queryBgmList(Integer page, Integer pageSize) {
		BgmExample example = new BgmExample();
		List<Bgm> list = bgmMapper.selectByExample(example);
		PageHelper.startPage(page, pageSize);
		PageInfo<Bgm> info = new PageInfo<>(list);
		PagedResult result = new PagedResult();
		result.setRows(list);
		result.setPage(page);
		result.setRecords(info.getTotal());
		result.setPage(info.getPages());
		return result;
	}

	@Override
	public void addBgm(Bgm bgm) {
		String id = sid.nextShort();
		bgm.setId(id);
		bgmMapper.insert(bgm);
		
		Map<String,String> operator = new HashMap<>();
		operator.put("path", bgm.getPath());
		operator.put("operaType", BGMOperatorTypeEnum.ADD.type);
		zkCurator.sendBgmOperator(id, JSONUtils.toJSONString(operator));
		
	}

	@Override
	public void deleteBgm(String bgmId) {
		Bgm bgm = bgmMapper.selectByPrimaryKey(bgmId);
		bgmMapper.deleteByPrimaryKey(bgmId);
		
		Map<String,String> operator = new HashMap<>();
		operator.put("path", bgm.getPath());
		operator.put("operaType", BGMOperatorTypeEnum.DELETE.type);
		zkCurator.sendBgmOperator(bgmId, JSONUtils.toJSONString(operator));
		
	}
	
	

}
