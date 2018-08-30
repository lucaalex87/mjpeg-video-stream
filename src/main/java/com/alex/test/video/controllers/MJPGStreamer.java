/**
 * Copyright (c) 2013 Joshua Dickson
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.alex.test.video.controllers;

import com.alex.test.video.capture.VideoCaptureComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * Original example author: Joshua Dickson
 * December 10, 2013
 *
 * Adapted version to stream a live video capture, by Alex-Dan Luca
 */
@Controller
public class MJPGStreamer  {
	
	@Autowired
	VideoCaptureComponent videoCaptureComponent;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@GetMapping("/stream")
	protected void streamMJpeg (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// set the proper content type for MJPG
		response.setContentType("multipart/x-mixed-replace; boundary=--BoundaryString");
		
		// get the output stream to write to
		OutputStream outputStream = response.getOutputStream();
		 
		// loop over and send the images while the browser is present and listening, then return
		while(true) {
			byte[] nextFrame = videoCaptureComponent.getLastImage();
			try {
				// write the image and wrapper
				outputStream.write((
					"--BoundaryString\r\n" +
					"Content-type: image/jpeg\r\n" +
					"Content-Length: " +
							nextFrame.length +
					"\r\n\r\n").getBytes());
				outputStream.write(nextFrame);
				outputStream.write("\r\n\r\n".getBytes());
				outputStream.flush();			      

				// force sleep to not overwhelm the browser, simulate ~30 FPS
				TimeUnit.MILLISECONDS.sleep(40);
			}
			
			// If there is a problem with the connection (it likely closed), so return
			catch (Exception e) {
				return;
			}
		}
	}
}
