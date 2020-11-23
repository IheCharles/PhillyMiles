package com.example.phillymiles;

public class card {
    private String time;
    private int steps;
    private String image;
    private String title;
    private String username;
    private String Story;
    private int color;


    private card(){


    }

    public card(String image, String title) {
        this.image = image;
        this.title = title;
        this.username = username;


    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStory() {
        return Story;
    }

    public void setStory(String story) {
        Story = story;
    }
}
