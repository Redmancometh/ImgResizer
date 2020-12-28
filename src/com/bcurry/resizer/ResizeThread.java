package com.bcurry.resizer;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.imageio.ImageIO;

public class ResizeThread extends Thread {
	private List<File> iteratedFiles;
	private CountDownLatch latch;
	private String outputString;
	private int width, height;

	public ResizeThread(List<File> iteratedFiles, CountDownLatch latch, String outputString, int width, int height) {
		this.iteratedFiles = iteratedFiles;
		this.latch = latch;
		this.outputString = outputString;
		this.width = width;
		this.height = height;
	}

	@Override
	public void run() {
		for (File iteratedFile : iteratedFiles) {
			try {
				BufferedImage imgIn = ImageIO.read(iteratedFile);
				double ratio = (double) imgIn.getWidth() / (double) imgIn.getHeight();
				System.out.println(iteratedFile.getName());
				System.out.println(!iteratedFile.getName().endsWith("jpg"));
				System.out.println("NO SKIP");
				BufferedImage resized = new BufferedImage(width, height, imgIn.getType());
				File outputPath = new File(outputString + File.separator + iteratedFile.getName().replace(".png", ""));
				Graphics2D g = resized.createGraphics();
				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g.drawImage(imgIn, 0, 0, width, height, 0, 0, imgIn.getWidth(), imgIn.getHeight(), null);
				g.dispose();
				System.out.println("Written: " + ImageIO.write(resized, "jpg", outputPath));
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		latch.countDown();
	}
}
