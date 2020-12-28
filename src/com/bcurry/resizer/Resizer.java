package com.bcurry.resizer;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Resizer {
	public static void main(String[] args) {
		System.out.println("Starting...");
		File inFile = new File(args[0]);
		File[] files = inFile.listFiles();
		List<File> fileList = Arrays.asList(files);
		int chunkAmt = fileList.size() / 8;
		CountDownLatch latch = new CountDownLatch(8);
		System.out.println(chunkAmt);
		for (int x = 1; x <= 8; x++) {
			try {
				int start = (x * chunkAmt) - chunkAmt;
				int end = x * chunkAmt;
				System.out.println("Thread " + x + "Start: " + start);
				System.out.println("Thread " + x + "End: " + end);
				new ResizeThread(fileList.subList(start, end), latch, args[1], Integer.parseInt(args[2]),
						Integer.parseInt(args[3])).start();
			} catch (Exception e) {
				System.out.println("Syntax Error. Usage: java -jar [thisjarname].jar [inputDir] [outputDir] [width] [height]");
				System.exit(0);
			}
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
