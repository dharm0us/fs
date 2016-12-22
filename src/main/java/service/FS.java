package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

public class FS {
	private static Map<String, String> fileContents = new HashMap<String, String>();

	private static boolean directoryExists(String dirPath) {
		if (fileContents.containsKey(dirPath)) {
			return fileContents.get(dirPath) == null;
		}
		return false;
	}

	private static boolean fileExists(String filePath) {
		if (fileContents.containsKey(filePath)) {
			return !directoryExists(filePath);
		}
		return false;
	}

	public static void createDirectory(String dirPath) throws Exception {
		if (dirPath.substring(dirPath.length() - 1) != "/")
			dirPath = dirPath + '/';
		if (directoryExists(dirPath))
			throw new Exception("Directory already exists " + dirPath);
		fileContents.put(dirPath, null);
	}

	public static void createFile(String filePath) throws Exception {
		if (fileExists(filePath))
			throw new Exception("File already exists " + filePath);
		String dir = FilenameUtils.getFullPath(filePath);
		if(StringUtils.isBlank(dir)) dir ="/";
		if (dir != "/" && !directoryExists(dir))
			throw new Exception("base dir for file " + filePath + " which is " + dir + " doesn't exist");
		fileContents.put(filePath, "");
	}

	public static void deleteFile(String filePath) throws Exception {
		if (!fileExists(filePath))
			throw new Exception("File doesn't exist " + filePath);
		fileContents.remove(filePath);
	}

	public static void deleteDirectory(String dirPath) throws Exception {
		if (dirPath.substring(dirPath.length() - 1) != "/")
			dirPath = dirPath + '/';

		if (!directoryExists(dirPath))
			throw new Exception("Directory doesn't exist " + dirPath);

		for (Iterator<Map.Entry<String, String>> it = fileContents.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = it.next();
			if (entry.getKey().startsWith(dirPath)) {
				System.out.println("deleting " + entry.getKey());
				it.remove();
			}
		}
	}

	public static String search(String s) {
		String out = "";
		for (Iterator<Map.Entry<String, String>> it = fileContents.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = it.next();
			if (entry.getKey().contains(s)) {
				out += entry.getKey() + "\n";
			}
		}
		return out;

	}

	public static void appendFile(String file, String content) throws Exception {
		if (!fileExists(file))
			throw new Exception("file doesn't exist " + file);
		String contents = fileContents.get(file);
		fileContents.put(file, contents + content);
	}

	public static String getFileContent(String file) throws Exception {
		if (!fileExists(file))
			throw new Exception("file doesn't exist " + file);
		return fileContents.get(file);
	}

	public static String getAll() {
		String out = "";
		for (Iterator<Map.Entry<String, String>> it = fileContents.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = it.next();
			out += entry.getKey() + "\n";
		}
		return out;
	}

	public static String listDir(String dirPath) throws Exception {
		if (dirPath.substring(dirPath.length() - 1) != "/")
			dirPath = dirPath + '/';
		if (!directoryExists(dirPath))
			throw new Exception("Directory doesn't exist " + dirPath);
		List<String> list = new ArrayList<String>();
		for (Iterator<Map.Entry<String, String>> it = fileContents.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = it.next();
			if (entry.getKey().startsWith(dirPath)) {
				System.out.println("listing " + entry.getKey());
				list.add(entry.getKey());
			}
		}
		return list.toString();
	}

}
