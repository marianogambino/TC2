package unimoron.ar.edu.camera;

/**
 * Created by mariano on 17/11/17.
 */

public interface CameraSupport {
    CameraSupport open(int cameraId);
    int getOrientation(int cameraId);
}
