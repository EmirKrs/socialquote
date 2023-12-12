package com.emirhankaraarslan.socialquote.model;

public class Post {
    public String imageUrl;
    public String username;
    public String quote;
    public String author;
    public String book;

    public Post(String username, String imageUrl, String quote, String author, String book){

        this.username = username;
        this.imageUrl = imageUrl;
        this.quote = quote;
        this.author = author;
        this.book = book;

    }
}
