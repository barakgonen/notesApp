package com.notesapp.model;

public class NoteEntity {
    private Long id;
    private String title;
    private String body;
    private PriorityEnum priority;
    private boolean hasRead;
    private int bgColor;

    public NoteEntity(String title, String body, PriorityEnum priority, boolean hasRead, int bgColor) {
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

    public Integer getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", priority=" + priority +
                ", hasRead=" + hasRead +
                ", bgColor='" + bgColor + '\'' +
                '}';
    }
}
