package com.alex.test.video.controllers;

import com.alex.test.video.capture.VideoCaptureComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Alex on 30/08/2018.
 *
 * Change video source between webcam or local video file
 */

@Controller
public class SourcePickController {

    @Autowired
    VideoCaptureComponent videoCaptureComponent;

    @GetMapping("/source/{sourceType}")
    public void setSource(@PathVariable String sourceType, HttpServletResponse response) {
        VideoCaptureComponent.CaptureType type = VideoCaptureComponent.CaptureType.valueOf(sourceType);
        videoCaptureComponent.initializeVideoCapture(type);

        try {
            response.sendRedirect("/index.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
