package com.bcurry.resizer;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Resizer {
	public static void main(String[] args) {
		System.out.println("Starting...");
		File inFile = new File("C:\\Users\\Redmancometh\\mmp\\abbyscraper\\titsjpg");
		File[] files = inFile.listFiles();
		List<File> fileList = Arrays.asList(files);
		int chunkAmt = fileList.size() / 8;
		CountDownLatch latch = new CountDownLatch(8);
		System.out.println(chunkAmt);
		for (int x = 1; x <= 8; x++) {
			int start = (x * chunkAmt) - chunkAmt;
			int end = x * chunkAmt;
			System.out.println("Thread " + x + "Start: " + start);
			System.out.println("Thread " + x + "End: " + end);
			new ResizeThread(fileList.subList(start, end), latch, args[0], Integer.parseInt(args[1]),
					Integer.parseInt(args[2])).start();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
