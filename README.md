Test project to stream a video in the browser from a webcam or a local video file.
OpenCV processing on each frame to add timestamp.
Emphasis on low latency.

To run:

```
mvn spring-boot:run
```
Observation:
Webcam VideoCapture is done on server-side - webcam from the server (which works if the app is running on a laptop).
The video option was added for when there is no webcam.

OpenCV must be correctly installed. OpenCV 3.2.0 used here