package com.imooc.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.InputSource;

import com.imooc.enums.VideoStatusEnum;
import com.imooc.pojo.Bgm;
import com.imooc.service.VideoService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.PagedResult;

@Controller
@RequestMapping("/video")
public class VideoController {

	@Autowired
	VideoService videoService;

	@GetMapping("/showReportList")
	public String showReportList() {
		return "video/reportList";
	}

	@ResponseBody
	@PostMapping("/reportList")
	public PagedResult reportList(Integer page) {
		return videoService.getReportList(page, 10);
	}

	@ResponseBody
	@PostMapping("/forbidVideo")
	public IMoocJSONResult forbidVideo(String videoId) {
		videoService.updateVideoStatus(videoId, VideoStatusEnum.FORBID.value);

		return IMoocJSONResult.ok();
	}

	@GetMapping("/showBgmList")
	public String showBgmList() {
		return "video/bgmList";
	}

	@ResponseBody
	@PostMapping("/queryBgmList")
	public PagedResult queryBgmList(Integer page) {
		return videoService.queryBgmList(page, 10);
	}

	@GetMapping("/showAddBgm")
	public String showAddBgm() {
		return "video/addBgm";
	}

	@ResponseBody
	@PostMapping("/addBgm")
	public IMoocJSONResult addBgm(Bgm bgm) {
		videoService.addBgm(bgm);
		return IMoocJSONResult.ok();
	}

	@ResponseBody
	@PostMapping("/delBgm")
	public IMoocJSONResult deleteBgm(String bgmId) {
		videoService.deleteBgm(bgmId);
		return IMoocJSONResult.ok();
	}

	@ResponseBody
	@PostMapping("/bgmUpload")
	public IMoocJSONResult uploadBgm(@RequestParam("file") MultipartFile[] files) throws Exception {
		String filePath = "D:" + File.separator + "video_space_dev" + File.separator + "mvc-bgm";
		String uploadPathDB = File.separator + "bgm";
		
		FileOutputStream outputStream = null;
		InputStream inputStream = null;
		
		try {
			if (files != null && files.length > 0) {
				String fileName = files[0].getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					filePath = filePath + uploadPathDB + File.separator + fileName;
					uploadPathDB += File.separator + fileName;

					File file = new File(filePath);
					if (file.getParentFile() != null || !file.getParentFile().isDirectory()) {

						file.getParentFile().mkdirs();
					}
					outputStream = new FileOutputStream(file);
					inputStream = files[0].getInputStream();
					IOUtils.copy(inputStream, outputStream);
				}
			}else {
				return IMoocJSONResult.errorMsg("Bgm上传失败....");
						
			} 
		} catch (Exception e) {
			e.printStackTrace();
			return IMoocJSONResult.errorMsg("Bgm上传失败....");
		}finally {
			if(outputStream!=null){
				outputStream.flush();
				outputStream.close();
			}
		}
		return IMoocJSONResult.ok(uploadPathDB);
	}

}
