package com.example.notesapp;

public class NoteItem {
    private Long id;
    private String title;
    private String body;
    private PriorityEnum priority;
    private boolean hasRead;
    private String bgColor;

    public NoteItem() {
    }

    public NoteItem(Long id, String title, String body, PriorityEnum priority, boolean hasRead, String bgColor) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.priority = priority;
        this.hasRead = hasRead;
        this.bgColor = bgColor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "NoteItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", priority=" + priority +
                ", hasRead=" + hasRead +
                ", bgColor='" + bgColor + '\'' +
                '}';
    }
}
