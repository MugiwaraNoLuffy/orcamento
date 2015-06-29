package com.apps.redir.orcamento.MenuActivitiesTest;

/**
 * Created by redir on 6/25/2015.
 */
public class MenuOrcItem {
    private int imageId;
    private String name;
    private boolean clickable = true;

    public MenuOrcItem( String item){
        super();
        //this.imageId = image;
        this.name = item;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String item) {
        this.name = item;
    }

    public void setClickable(boolean bool){
        clickable = bool;
    }

    public boolean isClickable(){
        return clickable;
    }
}
