import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

public class Music implements Parcelable {
    public static String defaultRingtoneUrl = null;
    public static String defaultRingtoneName = "In the busting square";
    private String url;
    private String name;

    public Music(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static class UrlComparator implements Comparator<Music>{
        @Override
        public int compare(Music o1, Music o2) {
            return o1.url.compareToIgnoreCase(o2.url);
        }
    }
    public static class NameComparator implements Comparator<Music>{
        @Override
        public int compare(Music o1, Music o2) {
            return o1.name.compareToIgnoreCase(o2.name);
        }
    }



    protected Music(Parcel in) {
        url = in.readString();
        name = in.readString();
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(name);
    }
}