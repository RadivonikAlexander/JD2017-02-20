package by.it.prigozhanov.jd01_08.DemoLevelA;

/**
 * Created by v-omf on 3/10/2017!
 */
public class Runner {
    public static void main(String[] args) {
        Player videoPlayer = new VideoPlayer("Cometa");
        videoPlayer.play();
        videoPlayer.showState();
        videoPlayer.pause();
        videoPlayer.showState();
        videoPlayer.stop();
        videoPlayer.showState();
    }
}
