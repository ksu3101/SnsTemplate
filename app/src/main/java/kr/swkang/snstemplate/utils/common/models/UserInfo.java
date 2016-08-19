package kr.swkang.snstemplate.utils.common.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author KangSung-Woo
 * @since 2016/08/19
 */
public class UserInfo
    implements Parcelable {
  int          id;
  String       email;
  String       nickName;
  String       coverImgUrl;
  String       coverImgRGB;
  String       profileImgUrl;
  String       coverText;
  int          follower;
  int          following;
  int          postCount;
  List<String> postThumbnailUrls;

  public UserInfo() {
    init();
  }

  private void init() {
    this.id = -1;
    this.email = null;
    this.nickName = null;
    this.coverImgUrl = null;
    this.coverImgRGB = null;
    this.profileImgUrl = null;
    this.coverText = null;
    this.follower = 0;
    this.following = 0;
    this.postThumbnailUrls = null;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getCoverImgUrl() {
    return coverImgUrl;
  }

  public void setCoverImgUrl(String coverImgUrl) {
    this.coverImgUrl = coverImgUrl;
  }

  public String getCoverImgRGB() {
    return coverImgRGB;
  }

  public void setCoverImgRGB(String coverImgRGB) {
    this.coverImgRGB = coverImgRGB;
  }

  public String getProfileImgUrl() {
    return profileImgUrl;
  }

  public void setProfileImgUrl(String profileImgUrl) {
    this.profileImgUrl = profileImgUrl;
  }

  public String getCoverText() {
    return coverText;
  }

  public void setCoverText(String coverText) {
    this.coverText = coverText;
  }

  public int getFollower() {
    return follower;
  }

  public void setFollower(int follower) {
    this.follower = follower;
  }

  public int getFollowing() {
    return following;
  }

  public void setFollowing(int following) {
    this.following = following;
  }

  public int getPostCount() {
    return postCount;
  }

  public void setPostCount(int postCount) {
    this.postCount = postCount;
  }

  public List<String> getPostThumbnailUrls() {
    return postThumbnailUrls;
  }

  public void setPostThumbnailUrls(List<String> postThumbnailUrls) {
    this.postThumbnailUrls = postThumbnailUrls;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeString(this.email);
    dest.writeString(this.nickName);
    dest.writeString(this.coverImgUrl);
    dest.writeString(this.coverImgRGB);
    dest.writeString(this.profileImgUrl);
    dest.writeString(this.coverText);
    dest.writeInt(this.follower);
    dest.writeInt(this.following);
    dest.writeInt(this.postCount);
    dest.writeStringList(this.postThumbnailUrls);
  }

  protected UserInfo(Parcel in) {
    this.id = in.readInt();
    this.email = in.readString();
    this.nickName = in.readString();
    this.coverImgUrl = in.readString();
    this.coverImgRGB = in.readString();
    this.profileImgUrl = in.readString();
    this.coverText = in.readString();
    this.follower = in.readInt();
    this.following = in.readInt();
    this.postCount = in.readInt();
    this.postThumbnailUrls = in.createStringArrayList();
  }

  public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
    @Override
    public UserInfo createFromParcel(Parcel source) {
      return new UserInfo(source);
    }

    @Override
    public UserInfo[] newArray(int size) {
      return new UserInfo[size];
    }
  };
}
