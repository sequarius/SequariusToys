package com.sequarius.lightplayer.tools;

import java.io.File;
import java.io.FileFilter;
/**
 * 文件过滤接口，实现对音频后缀文件的判断
 * @author Sequarius
 *
 */
public class FileFilterBySuffix implements FileFilter {

	private String suffix;
	
	public FileFilterBySuffix(String suffix) {
		super();
		this.suffix = suffix;
	}

	@Override
	public boolean accept(File pathname) {
		
		return pathname.getName().endsWith(suffix);
	}
}
