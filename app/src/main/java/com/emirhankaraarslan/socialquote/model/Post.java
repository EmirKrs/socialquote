package com.emirhankaraarslan.socialquote.model;

public class Post {
    public String imageUrl;
    public String username;
    public String quote;
    public String author;
    public String book;

    public Post(String imageUrl, String username, String quote, String author, String book){

        this.imageUrl = imageUrl;
        this.username = username;
        this.quote = quote;
        this.author = author;
        this.book = book;

    }
}
