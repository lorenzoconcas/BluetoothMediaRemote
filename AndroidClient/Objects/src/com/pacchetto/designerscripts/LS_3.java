package com.pacchetto.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_3{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("mouse").vw.setWidth((int)((views.get("panel1").vw.getWidth())));
views.get("mouse").vw.setHeight((int)(((views.get("panel1").vw.getWidth())*9d)/16d));
views.get("mouse").vw.setLeft((int)(0d));

}
}