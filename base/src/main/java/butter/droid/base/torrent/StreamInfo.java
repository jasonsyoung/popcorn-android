/*
 * This file is part of Butter.
 *
 * Butter is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Butter is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Butter. If not, see <http://www.gnu.org/licenses/>.
 */

package butter.droid.base.torrent;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import butter.droid.provider.base.module.Media;
import butter.droid.provider.base.module.Show;
import butter.droid.provider.base.module.Streamable;
import butter.droid.provider.base.module.Torrent;
import java.util.Locale;
import org.parceler.Parcels;

public class StreamInfo implements Parcelable {

    @NonNull private final Media media;
    @Nullable private final Media parentMedia;
    @NonNull private final Streamable streamable;

    public StreamInfo(@NonNull Streamable streamable, @NonNull Media media, @Nullable Media parentMedia) {
        this.streamable = streamable;
        this.media = media;
        this.parentMedia = parentMedia;

        // color = media.color;
    }

    private StreamInfo(Parcel in) {
        this.media = Parcels.unwrap(in.readParcelable(Media.class.getClassLoader()));
        this.parentMedia = Parcels.unwrap(in.readParcelable(Media.class.getClassLoader()));
        this.streamable = Parcels.unwrap(in.readParcelable(Streamable.class.getClassLoader()));
    }

    @NonNull public String getFullTitle() {
        if (parentMedia != null && !(parentMedia instanceof Show)) {
            return String.format(Locale.US, "%s: %s", parentMedia.getTitle(), media.getTitle());
        } else {
            return media.getTitle();
        }
    }

    @NonNull public String getMediaTitle() {
        return media.getTitle();
    }

    @Nullable public String getParentMediaTitle() {
        if (parentMedia != null) {
            return parentMedia.getTitle();
        } else {
            return null;
        }
    }

    @Nullable public String getTorrentUrl() {
        if (streamable instanceof Torrent) {
            return ((Torrent) streamable).getTorrentUrl();
        } else {
            return null;
        }
    }

    public String getStreamUrl() {
        return streamable.getUrl();
    }

    public void setStreamUrl(final String url) {
        streamable.setUrl(url);
    }

    public int getPaletteColor() {
        // TODO: 7/29/17 Color
        return Color.TRANSPARENT;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(Parcels.wrap(this.media), 0);
        dest.writeParcelable(Parcels.wrap(this.parentMedia), 0);
        dest.writeParcelable(Parcels.wrap(this.streamable), 0);
    }

    public static final Creator<StreamInfo> CREATOR = new Creator<StreamInfo>() {
        public StreamInfo createFromParcel(Parcel source) {
            return new StreamInfo(source);
        }

        public StreamInfo[] newArray(int size) {
            return new StreamInfo[size];
        }
    };

    @Nullable public String getBackdropImage() {
        if (media.getBackdrop() != null) {
            return media.getBackdrop();
        } else if (parentMedia != null) {
            return parentMedia.getBackdrop();
        } else {
            return null;
        }
    }

    @Nullable public String getPosterImage() {
        if (media.getPoster() != null) {
            return media.getPoster();
        } else if (parentMedia != null) {
            return media.getPoster();
        } else {
            return null;
        }
    }

}
