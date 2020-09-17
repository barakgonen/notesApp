package com.example.app.model;

public class NoteEntity {
//    @JsonProperty("title")
    private String title;
//    @JsonProperty("body")
    private String body;
//    @JsonProperty("priority")
    private PriorityEnum priority;
//    @JsonProperty("hasRead")
    private boolean hasRead;
//    @JsonProperty("bgColor")
    private String bgColor;

    public NoteEntity() {
    }

    public NoteEntity(String title, String body, PriorityEnum priority, boolean hasRead, String bgColor) {
        this.title = title;
        this.body = body;
        this.priority = priority;
        this.hasRead = hasRead;
        this.bgColor = bgColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public PriorityEnum getPriority() {
        return priority;
    }

    public void setPriority(PriorityEnum priority) {
        this.priority = priority;
    }

    public boolean isHasRead() {
        return hasRead;
    }

    public void setHasRead(boolean hasRead) {
        this.hasRead = hasRead;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }
}
