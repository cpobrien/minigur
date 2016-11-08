package org.minigur.site.models;


import com.sun.org.apache.xpath.internal.operations.Bool;

public class SearchRequest {
    String searchString;
    Boolean searchTitle;
    Boolean searchUsername;
    Boolean searchComment;
    Boolean searchTag;

    public SearchRequest(String searchString, Boolean searchTitle, Boolean searchUsername, Boolean searchComment, Boolean searchTag) {
        this.searchString = searchString;
        this.searchTitle = searchTitle;
        this.searchUsername = searchUsername;
        this.searchComment = searchComment;
        this.searchTag = searchTag;
    }

    public String getSearchString() {

        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public Boolean getSearchTitle() {
        return searchTitle;
    }

    public void setSearchTitle(Boolean searchTitle) {
        this.searchTitle = searchTitle;
    }

    public Boolean getSearchUsername() {
        return searchUsername;
    }

    public void setSearchUsername(Boolean searchUsername) {
        this.searchUsername = searchUsername;
    }

    public Boolean getSearchComment() {
        return searchComment;
    }

    public void setSearchComment(Boolean searchComment) {
        this.searchComment = searchComment;
    }

    public Boolean getSearchTag() {
        return searchTag;
    }

    public void setSearchTag(Boolean searchTag) {
        this.searchTag = searchTag;
    }
}
