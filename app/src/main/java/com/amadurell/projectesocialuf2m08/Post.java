package com.amadurell.projectesocialuf2m08;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Post {
    public String uid;
    public String author;
    public String authorPhotoUrl;
    public String content;
    public String mediaUrl;
    public String mediaType;
    public Date currentTime;
    //2.Gestió de Likes
    public Map<String, Boolean> likes = new HashMap<>();
    //Extra opcional
    public int num_likes;

    // Constructor vacio requerido por Firestore
    public Post() {}

    public Post(String uid, String author, String authorPhotoUrl, String
            content, String mediaUrl, String mediaType) {
        this.uid = uid;
        this.author = author;
        this.authorPhotoUrl = authorPhotoUrl;
        this.content = content;
        this.mediaUrl = mediaUrl;
        this.mediaType = mediaType;
        this.currentTime = Calendar.getInstance().getTime();
        this.num_likes = 0;
    }
}