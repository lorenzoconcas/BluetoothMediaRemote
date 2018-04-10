package com.pacchetto.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_1{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[1/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 3;BA.debugLine="btnAllowConnection.Width = btnAllowConnection.Height"[1/General script]
views.get("btnallowconnection").vw.setWidth((int)((views.get("btnallowconnection").vw.getHeight())));
//BA.debugLineNum = 4;BA.debugLine="btnAllowConnection.Left = (Panel1.Width-btnAllowConnection.Width)/2"[1/General script]
views.get("btnallowconnection").vw.setLeft((int)(((views.get("panel1").vw.getWidth())-(views.get("btnallowconnection").vw.getWidth()))/2d));
//BA.debugLineNum = 5;BA.debugLine="btnAllowConnection.Top = (Panel1.Height-btnAllowConnection.Height)/2"[1/General script]
views.get("btnallowconnection").vw.setTop((int)(((views.get("panel1").vw.getHeight())-(views.get("btnallowconnection").vw.getHeight()))/2d));

}
}