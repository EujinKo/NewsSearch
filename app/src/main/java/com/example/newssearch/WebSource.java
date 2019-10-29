/**
 * WebSource class to store website and author in a same class
 */

package com.example.newssearch;

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
