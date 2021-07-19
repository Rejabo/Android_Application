

package com.example.MainActivites;


public class UploadInfo {

    //public String imageName;
    public String imageURL;
    public UploadInfo(){}

    public UploadInfo(String url) {
        this.imageURL = url;
    }


/*    public String getImageName() {
        return imageName;
    }*/

    public String getImageURL() {
        return imageURL;
    }
}
