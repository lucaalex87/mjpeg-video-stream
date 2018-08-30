Test project to stream a video in the browser from a webcam or a local video file.
Emphasis on low latency.

To run:

```
mvn spring-boot:run
```
Observation:
Webcam VideoCapture is done on server-side - webcam from the server (which works if the app is running on a laptop).
The video option was added for when there is no webcam.