package YoutubeActivity;

public class YouTubeViewModel {
    String videoUrl;

    public YouTubeViewModel() {

    }

    public YouTubeViewModel(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
