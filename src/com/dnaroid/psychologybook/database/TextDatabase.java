package com.dnaroid.psychologybook.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.dnaroid.psychologybook.model.AnswerModel;
import com.dnaroid.psychologybook.model.TestItem;

import android.content.Context;

public class TextDatabase {

	public static String loadFullText(String path, Context context) {
		String tContents = "";

		try {
			InputStream stream = context.getAssets().open(path);

			int size = stream.available();
			byte[] buffer = new byte[size];
			stream.read(buffer);
			stream.close();
			tContents = new String(buffer);
		} catch (IOException e) {
			// Handle exceptions here
			e.printStackTrace();
		}

		return tContents;
	}

	public static ArrayList<AnswerModel> getAnswers(int testId, Context context) {
		ArrayList<AnswerModel> array = new ArrayList<AnswerModel>();
		AnswerModel answer = null;
		int low = 0;
		int high = 0;
		String description = "";
		int counter = 0;
		try {
			InputStream stream = context.getAssets().open(
					"tests/test_" + String.valueOf(testId) + "/answer.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					stream, "UTF-8"));
			for (;;) {
				String line = in.readLine();

				if (line == null) {
					break;
				}

				if (line.equals("") || line.equals(" ")) {
					continue;
				}
				if (line.contains("#")) {
					if (counter > 0) {
						answer = new AnswerModel();
						answer.setHigh(high);
						answer.setLow(low);
						answer.setDescription(description);
						array.add(answer);
						description = "";
					}

					String[] lineSplit = line.split("#");
					String[] lowHigh = lineSplit[1].split("\\s+");
					low = Integer.parseInt(lowHigh[0]);
					high = Integer.parseInt(lowHigh[1]);
					counter++;

				} else {
					description += line;
				}

			}

			answer = new AnswerModel();
			answer.setHigh(high);
			answer.setLow(low);
			answer.setDescription(description);
			array.add(answer);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	public static ArrayList<TestItem> getQuestions(int testId, Context context) {
		ArrayList<TestItem> array = new ArrayList<TestItem>();
		TestItem testItem = null;
		ArrayList<String> choices = null;
		String question = "";
		int counter = 0;
		try {
			InputStream stream = context.getAssets().open(
					"tests/test_" + String.valueOf(testId) + "/questions.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					stream, "UTF-8"));
			for (;;) {
				String line = in.readLine();

				if (line == null) {
					break;
				}

				if (line.equals("") || line.equals(" ")) {
					continue;
				}

				if (line.contains("#")) {
					if (counter > 0) {
						testItem = new TestItem();
						testItem.setQuestion(question);
						testItem.setChoices(choices);
						array.add(testItem);
					}
					question = line.split("#")[1];
					choices = new ArrayList<String>();
					counter++;
				} else {
					choices.add(line);
				}

			}
			testItem = new TestItem();
			testItem.setQuestion(question);
			testItem.setChoices(choices);
			array.add(testItem);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	public static ArrayList<ArrayList<Integer>> getPoints(int testId,
			Context context) {
		ArrayList<ArrayList<Integer>> array = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> list;
		try {
			InputStream stream = context.getAssets().open(
					"tests/test_" + String.valueOf(testId) + "/points.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					stream, "UTF-8"));
			for (;;) {
				String line = in.readLine();

				if (line == null) {
					break;
				}

				if (line.equals("") || line.equals(" ")) {
					continue;
				} else {
					String[] pp = line.split("\\s+");
					list = new ArrayList<Integer>();
					int i = 0;
					while (i < pp.length) {

						char[] y = pp[i].toCharArray();
						if (y.length > 1 && i == 0) {
							list.add(Integer.parseInt(String.valueOf(y[1])));
						} else {
							list.add(Integer.parseInt(pp[i]));
						}

						i++;
					}

					array.add(list);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}
}
