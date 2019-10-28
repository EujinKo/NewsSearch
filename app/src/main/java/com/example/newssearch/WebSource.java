package com.example.newssearch;


//Class to store website & author
public class WebSource {
    public String website="";
    public String author="";

    public WebSource(String website, String author){
        this.website = website;
        this.author = author;
    }
    public String getWebsite(){
        return this.website;
    }
    public String getAuthor(){
        return this.author;
    }

}
