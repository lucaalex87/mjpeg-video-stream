package com.alex.test.video.capture;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;

@Component
public class ImageProcessor {
	

	public byte[] processImage(Mat matrix) {

		Imgproc.putText(matrix, ""+System.currentTimeMillis(), new Point(100,100),
				Core.FONT_HERSHEY_PLAIN, 1.0 ,new Scalar(0,255,255));

		int type = BufferedImage.TYPE_BYTE_GRAY;
		if ( matrix.channels() > 1 ) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		int bufferSize = matrix.channels()*matrix.cols()*matrix.rows();
		byte [] buffer = new byte[bufferSize];
		matrix.get(0,0,buffer); // get all the pixels
		BufferedImage image = new BufferedImage(matrix.cols(),matrix.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "jpg", baos );
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			return imageInByte;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}


}
