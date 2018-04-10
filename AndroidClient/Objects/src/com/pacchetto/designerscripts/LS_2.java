package com.pacchetto.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_2{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("prev").vw.setHeight((int)((views.get("prev").vw.getWidth())));
views.get("playpause").vw.setHeight((int)((views.get("prev").vw.getHeight())*3d));
//BA.debugLineNum = 5;BA.debugLine="playpause.Width = prev.Height*3"[2/General script]
views.get("playpause").vw.setWidth((int)((views.get("prev").vw.getHeight())*3d));
//BA.debugLineNum = 6;BA.debugLine="next1.Height = prev.Height"[2/General script]
views.get("next1").vw.setHeight((int)((views.get("prev").vw.getHeight())));
//BA.debugLineNum = 7;BA.debugLine="next1.Width = prev.Height"[2/General script]
views.get("next1").vw.setWidth((int)((views.get("prev").vw.getHeight())));
//BA.debugLineNum = 8;BA.debugLine="voldown.Height = prev.Height"[2/General script]
views.get("voldown").vw.setHeight((int)((views.get("prev").vw.getHeight())));
//BA.debugLineNum = 9;BA.debugLine="voldown.Width = prev.Height"[2/General script]
views.get("voldown").vw.setWidth((int)((views.get("prev").vw.getHeight())));
//BA.debugLineNum = 10;BA.debugLine="volup.Height = prev.Height"[2/General script]
views.get("volup").vw.setHeight((int)((views.get("prev").vw.getHeight())));
//BA.debugLineNum = 11;BA.debugLine="volup.Width = prev.Height"[2/General script]
views.get("volup").vw.setWidth((int)((views.get("prev").vw.getHeight())));
//BA.debugLineNum = 12;BA.debugLine="volmute.Height = prev.Height"[2/General script]
views.get("volmute").vw.setHeight((int)((views.get("prev").vw.getHeight())));
//BA.debugLineNum = 13;BA.debugLine="volmute.Width = prev.Height"[2/General script]
views.get("volmute").vw.setWidth((int)((views.get("prev").vw.getHeight())));
//BA.debugLineNum = 14;BA.debugLine="keyboardButton.Height = prev.Height"[2/General script]
views.get("keyboardbutton").vw.setHeight((int)((views.get("prev").vw.getHeight())));
//BA.debugLineNum = 15;BA.debugLine="keyboardButton.Width = prev.Height"[2/General script]
views.get("keyboardbutton").vw.setWidth((int)((views.get("prev").vw.getHeight())));
//BA.debugLineNum = 16;BA.debugLine="scrof.Height = prev.Height"[2/General script]
views.get("scrof").vw.setHeight((int)((views.get("prev").vw.getHeight())));
//BA.debugLineNum = 17;BA.debugLine="scrof.Width = prev.Height"[2/General script]
views.get("scrof").vw.setWidth((int)((views.get("prev").vw.getHeight())));
//BA.debugLineNum = 18;BA.debugLine="playpause.Left = (Panel1.Width-playpause.Width)/2"[2/General script]
views.get("playpause").vw.setLeft((int)(((views.get("panel1").vw.getWidth())-(views.get("playpause").vw.getWidth()))/2d));
//BA.debugLineNum = 19;BA.debugLine="prev.right = playpause.Left"[2/General script]
views.get("prev").vw.setLeft((int)((views.get("playpause").vw.getLeft()) - (views.get("prev").vw.getWidth())));
//BA.debugLineNum = 20;BA.debugLine="next1.left= playpause.right"[2/General script]
views.get("next1").vw.setLeft((int)((views.get("playpause").vw.getLeft() + views.get("playpause").vw.getWidth())));
//BA.debugLineNum = 21;BA.debugLine="playpause.Top = playpause.Top-prev.Height"[2/General script]
views.get("playpause").vw.setTop((int)((views.get("playpause").vw.getTop())-(views.get("prev").vw.getHeight())));
//BA.debugLineNum = 23;BA.debugLine="volmute.Left = (Panel1.Width-prev.Width)/2"[2/General script]
views.get("volmute").vw.setLeft((int)(((views.get("panel1").vw.getWidth())-(views.get("prev").vw.getWidth()))/2d));
//BA.debugLineNum = 24;BA.debugLine="volup.right = volmute.left"[2/General script]
views.get("volup").vw.setLeft((int)((views.get("volmute").vw.getLeft()) - (views.get("volup").vw.getWidth())));
//BA.debugLineNum = 25;BA.debugLine="voldown.Left = volmute.Right"[2/General script]
views.get("voldown").vw.setLeft((int)((views.get("volmute").vw.getLeft() + views.get("volmute").vw.getWidth())));
//BA.debugLineNum = 27;BA.debugLine="keyboardButton.Left = Panel1.Width/2"[2/General script]
views.get("keyboardbutton").vw.setLeft((int)((views.get("panel1").vw.getWidth())/2d));
//BA.debugLineNum = 28;BA.debugLine="scrof.Right = keyboardButton.Left"[2/General script]
views.get("scrof").vw.setLeft((int)((views.get("keyboardbutton").vw.getLeft()) - (views.get("scrof").vw.getWidth())));

}
}