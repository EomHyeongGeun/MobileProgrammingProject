package my.kmu.com.navigationdrawerrrrr;

import android.graphics.drawable.Drawable;

/**
 * Created by eomhyeong-geun on 2016. 12. 3..
 */

public class ListViewItem {
    private String titleStr;
    private String dateStr;
    private String pictureImg;
    private int idNum;

    public void setIdNum(int id){
        idNum = id;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDate(String date) {
        dateStr = date ;
    }
    public void setPicture(String picture){
        pictureImg = picture;
    }

    public int getIdNum(){
        return this.idNum;
    }
    public String getTitle(){
        return this.titleStr;
    }
    public String getDate(){
        return this.dateStr;
    }
    public String getPicture(){
        return this.pictureImg;
    }
}
