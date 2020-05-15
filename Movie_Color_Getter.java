import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class Movie_Color_Getter {
    public static void main(String args[]) {
        // configurations ///////////////

        // mp4 file to use:
        String movie = "movie_you-want_to_use.mp4";

        // how many pixels to skip (optimizes performance):
        int step = 10;

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Imgcodecs imageCodecs = new Imgcodecs();
        VideoCapture vc = new VideoCapture();
        Mat frame = new Mat();
        vc.read(frame);
        Mat output = frame.clone();
        int[] data = { 0, 0, 0 };
        vc.open(movie);

        for (int i = 0; i < frame.width(); i++) {
            vc.set(1, i * vc.get(7) / frame.width());
            System.out.println(i);
            vc.grab();
            vc.retrieve(frame);
            for (int y = 0; y < frame.height() / step; y++) {
                for (int x = 0; x < frame.width() / step; x++) {
                    double[] temp = frame.get(y * step, x * step);
                    data[0] += temp[0];
                    data[1] += temp[1];
                    data[2] += temp[2];
                }
                data[0] /= frame.width() / step;
                data[1] /= frame.width() / step;
                data[2] /= frame.width() / step;
                for (int p = 0; p < step; p++) {
                    output.put(y * step + p, i, data);
                }

            }
        }
        imageCodecs.imwrite("result" + ".jpg", output);
    }
}
