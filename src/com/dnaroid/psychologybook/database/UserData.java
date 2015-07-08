package com.dnaroid.psychologybook.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.dnaroid.psychologybook.model.CounterCache;
import com.dnaroid.psychologybook.model.SavedTest;

import android.content.Context;

public class UserData {

	private Context mContext;
	private DatabaseManager mDbManager;

	public UserData(Context context) {
		this.mContext = context;
		mDbManager = new DatabaseManager(mContext);
	}

	public void createCountersCache() {

		try {
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < 16; i++) {
				int freeCount = mDbManager.getFreeTopics(i).getCount();
				int allCount = mDbManager.getCategoryTopics(i).getCount();
				sb.append(i);
				sb.append("#");
				sb.append(allCount);
				sb.append("#");
				sb.append(freeCount);
				sb.append("\n");
			}

			File file = new File(mContext.getCacheDir(), "count.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sb.toString());
			bw.close();
			fw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<CounterCache> getCounterCaches() {
		try {
			ArrayList<CounterCache> counterCacheArray = new ArrayList<>();
			File file = new File(mContext.getCacheDir(), "count.txt");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			while ((line = br.readLine()) != null) {
				CounterCache cc = new CounterCache();
				String[] splited = new String[3];
				splited = line.split("#");
				cc.setCategoryId(Integer.valueOf(splited[0]));
				cc.setAllCount(Integer.valueOf(splited[1]));
				cc.setFreeCount(Integer.valueOf(splited[2]));
				counterCacheArray.add(cc);
			}
			br.close();
			fr.close();
			return counterCacheArray;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void loadUserData() {
		try {
			File file = new File(mContext.getFilesDir(), "data.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			while ((line = br.readLine()) != null) {
				mDbManager.updateTopic(Integer.valueOf(line), 1);
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveUserData(int topicId, String mode) {
		try {
			File file = new File(mContext.getFilesDir(), "data.txt");
			if (!file.exists()) {
				file.createNewFile();
			}

			if (mode.equals("insert")) {
				FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(String.valueOf(topicId) + "\n");
				bw.close();
				fw.close();
				return;
			}

			if (mode.equals("delete")) {
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String line = "";
				HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
				while ((line = br.readLine()) != null) {
					hashMap.put(line, 0);
				}
				hashMap.remove(String.valueOf(topicId));
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				for (String key : hashMap.keySet()) {
					bw.write(key + "\n");
				}
				br.close();
				bw.close();
				fr.close();
				fw.close();
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void saveUserTest(int point, String answer, int testId, String name) {
		try {

			String path = mContext.getFilesDir().getAbsolutePath()
					+ File.separator + "test_" + String.valueOf(testId);
			File directory = new File(path);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss",
					Locale.US);
			String currentTime = sdf.format(new Date());
			String fileName = name + "_" + currentTime + ".txt";
			File testFile = new File(directory, fileName);

			StringBuilder sb = new StringBuilder();
			sb.append("#");
			sb.append(name);
			sb.append(" ");
			sb.append(point);
			sb.append("\n");
			sb.append(answer);

			FileWriter fw = new FileWriter(testFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sb.toString());
			bw.close();
			fw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<SavedTest> getSavedTests(int testId) {
		ArrayList<SavedTest> array = new ArrayList<SavedTest>();
		String path = mContext.getFilesDir().getAbsolutePath() + File.separator
				+ "test_" + String.valueOf(testId);
		File[] files = null;
		File directory = new File(path);
		if (directory.exists()) {
			files = directory.listFiles();
		}else{
			return array;
		}

		for (int i = 0; i < files.length; i++) {
			try {
				SavedTest savedTest = new SavedTest();
				File f = new File(path, files[i].getName());
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				String line = "";
				int counter = 0;
				StringBuilder sb = new StringBuilder();
				while ((line = br.readLine()) != null) {
					if (counter == 0) {
						String[] splited = line.split("#");
						String[] data = splited[1].split(" ");
						savedTest.setUsername(data[0]);
						savedTest.setPoint(data[1]);
					} else {
						sb.append(line);
					}
					counter++;
				}
				savedTest.setAnswer(sb.toString());
				array.add(savedTest);
				br.close();
				fr.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return array;
	}

}
