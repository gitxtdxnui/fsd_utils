package com.feishidai.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileUtils extends org.apache.commons.io.FileUtils {
	public static void getFile(File file, List<File> list){
		if(file != null && file.exists()){
			File[] fs = file.listFiles();
			if(fs != null){
				for(File f: fs){
					if(f.isDirectory()){
						getFile(f, list);
					}else{
						list.add(f);
					}
				}
			}
		}
	}
	
	public static void deleteFile(File file) {
		if(file.exists()){
			if(file.isDirectory()){
				File[] files = file.listFiles();
				for(int i = 0;i < files.length;i++){
					deleteFile(files[i]);
				}
			}
			file.delete();
		}
	}

	public static void deleteFile(String file) {
		deleteFile(new File(file));
	}

	
	public static void main(String[] args) {
		File copyFile = new File("/19200471_07ab0f12f79711dee3243ccc0b000fb27dfa652e$9b40aa6164e1ba52c7c0c786897b359b.json");
		File toFile = new File("\\zzz\\");
		File wFile = new File("\\zz\\bb.json");
//		copyTo(copyFile, toFile);

		try {
			FileUtils.copyFileToDirectory(copyFile, toFile);
//			FileUtils.writeStringToFile(wFile, "中国人");
			
//			String a = readFileToString(copyFile, "gbk");
//			System.out.println(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
