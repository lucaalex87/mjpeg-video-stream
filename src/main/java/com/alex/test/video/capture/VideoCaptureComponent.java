package com.alex.test.video.capture;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * Created by Alex on 29/08/2018.
 *
 * Capture video frame by frame, either from webcam or a video file
 */

@Component
public class VideoCaptureComponent {

    @Autowired
    ImageProcessor imageProcessor;

    private volatile byte[] lastImage;
    private Thread activeCaptureThread;

    public enum CaptureType {
        WEBCAM, VIDEO;
    }

    @PostConstruct
    public void initVideoCapture() {
        initializeVideoCapture(CaptureType.VIDEO);
    }

    public void initializeVideoCapture(CaptureType type) {

        VideoCapture capture;
        if (type == CaptureType.WEBCAM) {
            capture = new VideoCapture(0);
        } else {
            capture = new VideoCapture("src/main/resources/static/video/no-webcam.mp4");
        }

        capture.set(Videoio.CAP_PROP_FRAME_WIDTH,320);
        capture.set(Videoio.CAP_PROP_FRAME_HEIGHT,240);

        if( capture.isOpened()){
            startVideoCaptureThread(capture);
        } else {
            File file = new File("src/main/resources/static/video/no-webcam.mp4");
            System.out.println("can read file: "+file.canRead());

            if( capture.isOpened()){
                startVideoCaptureThread(capture);
            } else {
                System.out.println("cannot open file capture, no webcam either");
            }
        }
    }

    private void startVideoCaptureThread(final VideoCapture capture) {
        if (activeCaptureThread != null) {
            activeCaptureThread.interrupt();
        }
        activeCaptureThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Mat oneFrame = new Mat();
                boolean interrupted = false;
                while (!interrupted){
                    try {
                        // simulate 30 FPS
                        Thread.sleep(33);
                    } catch (InterruptedException e) {
                        capture.release();
                        interrupted = true;
                    }
                    capture.read(oneFrame);
                    if( !oneFrame.empty() ){
                        byte[] toSend = imageProcessor.processImage(oneFrame);
                        lastImage = toSend;
                    }
                    else {
                        System.out.println(" -- Frame not captured -- Break!");
                        break;
                    }
                }
            }});
        activeCaptureThread.start();
    }

    public byte[] getLastImage() {
        return lastImage;
    }
}
