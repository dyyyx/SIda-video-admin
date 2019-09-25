package com.imooc.service;

import com.imooc.pojo.Bgm;
import com.imooc.pojo.Users;
import com.imooc.utils.PagedResult;

public interface VideoService {

	public PagedResult getReportList(Integer page,Integer pageSize);

	public void updateVideoStatus(String videoId, int value);

	public PagedResult queryBgmList(Integer page, Integer pageSize);

	public void addBgm(Bgm bgm);
	
	public void deleteBgm(String bgmId);
	
}
