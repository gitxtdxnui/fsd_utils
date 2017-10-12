package com.feishidai.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class ControllerUtils {
	private static Logger logger = LoggerFactory.getLogger(ControllerUtils.class);
	
	public static JSONObject getFormParams(HttpServletRequest req){
		JSONObject obj = new JSONObject();
		Map<String, String[]> map = req.getParameterMap();
		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			String key = it.next();
			String[] value = map.get(key);
			if(value.length > 1){
				JSONArray arr = new JSONArray();
				for(String v:value){
					arr.add(v);
				}
			}else{
				obj.put(key, value[0]);
			}
		}
		String params = "";
		if(StringUtils.isNotBlank(req.getQueryString())){
			params = "?" + req.getQueryString();
		}
		logger.info(req.getServletPath() + params + "---params::" + obj.toString());
		return obj;
	}
	
	public static JSONObject getHeaderJson(HttpServletRequest req){
		JSONObject obj = new JSONObject();
		Enumeration<String> names = req.getHeaderNames();
		while(names != null && names.hasMoreElements()){
			String key = names.nextElement();
			String value = req.getHeader(key);
			obj.put(key, value);
		}
		return obj;
	}
	
	public static void loadFile(String fileName, String value, HttpServletResponse res) throws Exception {
		if(StringUtils.isBlank(fileName)){
			throw new Exception("fileName is null");
		}
		File file = new File(fileName);
		logger.info(file.getAbsolutePath());
		PrintWriter pw = null;
		try {
			String downloadName = new String(file.getName().getBytes("UTF-8"), "iso8859-1");
			// 设置相应类型
			res.setContentType("application/octet-stream");
			// 设置相应头
			// servletResponse.setCharacterEncoding("UTF-8");
			res.setHeader("Content-Disposition", "attachment; filename=\"" + downloadName + "\"");
			pw = res.getWriter();
			pw.write(value);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("下载文件失败：");
		} finally {
			try {
				if (pw != null) {
					pw.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void loadFile(String fileName, HttpServletResponse servletResponse) throws Exception {
		if(StringUtils.isBlank(fileName)){
			throw new Exception("filepath is null");
		}
		File file = new File(fileName);//需要重新处理，加入redis缓存，key：keyinfo+_文件路径，添加1分钟的超时时间
		logger.info(file.getAbsolutePath());
		if(!file.exists()){
			throw new Exception("file is null");
		}
		FileInputStream inputStream = null;
		ServletOutputStream outputStream = null;
		try {
			String downloadName = new String(file.getName().getBytes("UTF-8"), "iso8859-1");
			// 设置相应类型
			servletResponse.setContentType("application/octet-stream");
			// 设置相应头
			// servletResponse.setCharacterEncoding("UTF-8");
			servletResponse.setHeader("Content-Disposition", "attachment; filename=\"" + downloadName + "\"");
			
			inputStream = new FileInputStream(file);
			outputStream = servletResponse.getOutputStream();
			
			byte[] fileStream = new byte[(int) file.length()];
			int read = inputStream.read(fileStream);
			int count = 0;
			while (read != -1 && count < 50) {
				if (read == 0) {
					count++;
				}
				outputStream.write(fileStream);
				outputStream.flush();
				read = inputStream.read(fileStream);
			}
			outputStream.flush();
			inputStream.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("下载文件失败：");
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getResponseTxt(HttpServletRequest req) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		InputStreamReader isr = null;
		try {
			req.setCharacterEncoding("UTF-8");
			isr = new InputStreamReader((ServletInputStream) req.getInputStream(), "UTF-8");
			br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			String params = "";
			if(StringUtils.isNotBlank(req.getQueryString())){
				params = "?" + req.getQueryString();
			}
			logger.info(req.getServletPath() + params + "------params::" + sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (isr != null) {
					isr.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static void printWriter(HttpServletRequest req, HttpServletResponse res, String outJsonStr) {
		PrintWriter printWriter = null;
		try {
			req.setCharacterEncoding("UTF-8");
			res.setCharacterEncoding("UTF-8");
			res.setContentType("application/json;charset=UTF-8");
			printWriter = res.getWriter();

			printWriter.write(outJsonStr);
			printWriter.flush();
			String params = "";
			if(StringUtils.isNotBlank(req.getQueryString())){
				params = "?" + req.getQueryString();
			}
			logger.info(req.getServletPath() + params + "------return::" + outJsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (printWriter != null) {
				printWriter.close();
			}
		}
	}

	public static String getIpPort(HttpServletRequest req, HttpServletResponse res) {
		return "http://" + req.getServerName() + ":" +req.getServerPort() + "/";
	}
}
