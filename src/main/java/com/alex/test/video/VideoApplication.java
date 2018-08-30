package com.alex.test.video;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class VideoApplication {

	static{ nu.pattern.OpenCV.loadShared(); }

	public static void main(String[] args) {

		SpringApplication.run(VideoApplication.class, args);

		// OpenCV working test - fail fast if OpenCV is not properly installed
		System.out.println("Welcome to OpenCV " + Core.VERSION);
		Mat m = new Mat(5, 10, CvType.CV_8UC1, new Scalar(0));
		System.out.println("OpenCV Mat: " + m);
		Mat mr1 = m.row(1);
		mr1.setTo(new Scalar(1));
		Mat mc5 = m.col(5);
		mc5.setTo(new Scalar(5));
		System.out.println("OpenCV Mat data:\n" + m.dump());
	}

}
