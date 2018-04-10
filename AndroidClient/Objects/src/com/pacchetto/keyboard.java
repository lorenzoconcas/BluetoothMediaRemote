package com.pacchetto;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class keyboard extends Activity implements B4AActivity{
	public static keyboard mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "com.pacchetto", "com.pacchetto.keyboard");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (keyboard).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, true))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "com.pacchetto", "com.pacchetto.keyboard");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.pacchetto.keyboard", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (keyboard) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (keyboard) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return keyboard.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (keyboard) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (keyboard) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.PanelWrapper _mouse = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_q = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_w = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_e = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_r = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_t = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_y = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_u = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_i = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_o = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_p = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_a = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_s = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_d = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_f = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_g = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_h = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_j = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_k = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_l = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_z = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_x = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_c = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_v = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_b = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_n = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_m = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_shift = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_enter = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_space = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_super = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_delete = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_3 = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_4 = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_5 = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_6 = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_7 = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_8 = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_9 = null;
public anywheresoftware.b4a.objects.PanelWrapper _key_0 = null;
public anywheresoftware.b4a.objects.PanelWrapper _mouseleft = null;
public anywheresoftware.b4a.objects.PanelWrapper _mouseright = null;
public com.pacchetto.main _main = null;
public com.pacchetto.controls _controls = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 63;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 65;BA.debugLine="Activity.LoadLayout(\"3\")";
mostCurrent._activity.LoadLayout("3",mostCurrent.activityBA);
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 87;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 83;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private mouse As Panel";
mostCurrent._mouse = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private key_q As Panel";
mostCurrent._key_q = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private key_w As Panel";
mostCurrent._key_w = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private key_e As Panel";
mostCurrent._key_e = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private key_r As Panel";
mostCurrent._key_r = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private key_t As Panel";
mostCurrent._key_t = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private key_y As Panel";
mostCurrent._key_y = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private key_u As Panel";
mostCurrent._key_u = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private key_i As Panel";
mostCurrent._key_i = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private key_o As Panel";
mostCurrent._key_o = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private key_p As Panel";
mostCurrent._key_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private key_a As Panel";
mostCurrent._key_a = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private key_s As Panel";
mostCurrent._key_s = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private key_d As Panel";
mostCurrent._key_d = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private key_f As Panel";
mostCurrent._key_f = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private key_g As Panel";
mostCurrent._key_g = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private key_h As Panel";
mostCurrent._key_h = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private key_j As Panel";
mostCurrent._key_j = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private key_k As Panel";
mostCurrent._key_k = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private key_l As Panel";
mostCurrent._key_l = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private key_z As Panel";
mostCurrent._key_z = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private key_x As Panel";
mostCurrent._key_x = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private key_c As Panel";
mostCurrent._key_c = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private key_v As Panel";
mostCurrent._key_v = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private key_b As Panel";
mostCurrent._key_b = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private key_n As Panel";
mostCurrent._key_n = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private key_m As Panel";
mostCurrent._key_m = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private key_shift As Panel";
mostCurrent._key_shift = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private key_enter As Panel";
mostCurrent._key_enter = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private key_space As Panel";
mostCurrent._key_space = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private key_super As Panel";
mostCurrent._key_super = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private key_delete As Panel";
mostCurrent._key_delete = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private key_1 As Panel";
mostCurrent._key_1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private key_2 As Panel";
mostCurrent._key_2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private key_3 As Panel";
mostCurrent._key_3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private key_4 As Panel";
mostCurrent._key_4 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private key_5 As Panel";
mostCurrent._key_5 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private key_6 As Panel";
mostCurrent._key_6 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private key_7 As Panel";
mostCurrent._key_7 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private key_8 As Panel";
mostCurrent._key_8 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private key_9 As Panel";
mostCurrent._key_9 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Private key_0 As Panel";
mostCurrent._key_0 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Private mouseLeft As Panel";
mostCurrent._mouseleft = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Private mouseRight As Panel";
mostCurrent._mouseright = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 61;BA.debugLine="End Sub";
return "";
}
public static String  _key_0_click() throws Exception{
 //BA.debugLineNum = 110;BA.debugLine="Sub key_0_Click";
 //BA.debugLineNum = 111;BA.debugLine="sendKeystroke(\"0x30\")";
_sendkeystroke("0x30");
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return "";
}
public static String  _key_1_click() throws Exception{
 //BA.debugLineNum = 146;BA.debugLine="Sub key_1_Click";
 //BA.debugLineNum = 147;BA.debugLine="sendKeystroke(\"0x31\")";
_sendkeystroke("0x31");
 //BA.debugLineNum = 148;BA.debugLine="End Sub";
return "";
}
public static String  _key_2_click() throws Exception{
 //BA.debugLineNum = 142;BA.debugLine="Sub key_2_Click";
 //BA.debugLineNum = 143;BA.debugLine="sendKeystroke(\"0x32\")";
_sendkeystroke("0x32");
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
return "";
}
public static String  _key_3_click() throws Exception{
 //BA.debugLineNum = 138;BA.debugLine="Sub key_3_Click";
 //BA.debugLineNum = 139;BA.debugLine="sendKeystroke(\"0x33\")";
_sendkeystroke("0x33");
 //BA.debugLineNum = 140;BA.debugLine="End Sub";
return "";
}
public static String  _key_4_click() throws Exception{
 //BA.debugLineNum = 134;BA.debugLine="Sub key_4_Click";
 //BA.debugLineNum = 135;BA.debugLine="sendKeystroke(\"0x34\")";
_sendkeystroke("0x34");
 //BA.debugLineNum = 136;BA.debugLine="End Sub";
return "";
}
public static String  _key_5_click() throws Exception{
 //BA.debugLineNum = 130;BA.debugLine="Sub key_5_Click";
 //BA.debugLineNum = 131;BA.debugLine="sendKeystroke(\"0x35\")";
_sendkeystroke("0x35");
 //BA.debugLineNum = 132;BA.debugLine="End Sub";
return "";
}
public static String  _key_6_click() throws Exception{
 //BA.debugLineNum = 126;BA.debugLine="Sub key_6_Click";
 //BA.debugLineNum = 127;BA.debugLine="sendKeystroke(\"0x36\")";
_sendkeystroke("0x36");
 //BA.debugLineNum = 128;BA.debugLine="End Sub";
return "";
}
public static String  _key_7_click() throws Exception{
 //BA.debugLineNum = 122;BA.debugLine="Sub key_7_Click";
 //BA.debugLineNum = 123;BA.debugLine="sendKeystroke(\"0x37\")";
_sendkeystroke("0x37");
 //BA.debugLineNum = 124;BA.debugLine="End Sub";
return "";
}
public static String  _key_8_click() throws Exception{
 //BA.debugLineNum = 118;BA.debugLine="Sub key_8_Click";
 //BA.debugLineNum = 119;BA.debugLine="sendKeystroke(\"0x38\")";
_sendkeystroke("0x38");
 //BA.debugLineNum = 120;BA.debugLine="End Sub";
return "";
}
public static String  _key_9_click() throws Exception{
 //BA.debugLineNum = 114;BA.debugLine="Sub key_9_Click";
 //BA.debugLineNum = 115;BA.debugLine="sendKeystroke(\"0x39\")";
_sendkeystroke("0x39");
 //BA.debugLineNum = 116;BA.debugLine="End Sub";
return "";
}
public static String  _key_a_click() throws Exception{
 //BA.debugLineNum = 230;BA.debugLine="Sub key_a_Click";
 //BA.debugLineNum = 231;BA.debugLine="sendKeystroke(\"0x41\")";
_sendkeystroke("0x41");
 //BA.debugLineNum = 232;BA.debugLine="End Sub";
return "";
}
public static String  _key_b_click() throws Exception{
 //BA.debugLineNum = 178;BA.debugLine="Sub key_b_Click";
 //BA.debugLineNum = 179;BA.debugLine="sendKeystroke(\"0x42\")";
_sendkeystroke("0x42");
 //BA.debugLineNum = 180;BA.debugLine="End Sub";
return "";
}
public static String  _key_c_click() throws Exception{
 //BA.debugLineNum = 186;BA.debugLine="Sub key_c_Click";
 //BA.debugLineNum = 187;BA.debugLine="sendKeystroke(\"0x43\")";
_sendkeystroke("0x43");
 //BA.debugLineNum = 188;BA.debugLine="End Sub";
return "";
}
public static String  _key_d_click() throws Exception{
 //BA.debugLineNum = 222;BA.debugLine="Sub key_d_Click";
 //BA.debugLineNum = 223;BA.debugLine="sendKeystroke(\"0x44\")";
_sendkeystroke("0x44");
 //BA.debugLineNum = 224;BA.debugLine="End Sub";
return "";
}
public static String  _key_delete_click() throws Exception{
 //BA.debugLineNum = 150;BA.debugLine="Sub key_delete_Click";
 //BA.debugLineNum = 151;BA.debugLine="sendKeystroke(\"0x08\")";
_sendkeystroke("0x08");
 //BA.debugLineNum = 152;BA.debugLine="End Sub";
return "";
}
public static String  _key_e_click() throws Exception{
 //BA.debugLineNum = 259;BA.debugLine="Sub key_e_Click";
 //BA.debugLineNum = 260;BA.debugLine="sendKeystroke(\"0x45\")";
_sendkeystroke("0x45");
 //BA.debugLineNum = 261;BA.debugLine="End Sub";
return "";
}
public static String  _key_enter_click() throws Exception{
 //BA.debugLineNum = 162;BA.debugLine="Sub key_enter_Click";
 //BA.debugLineNum = 163;BA.debugLine="sendKeystroke(\"0x0D\")";
_sendkeystroke("0x0D");
 //BA.debugLineNum = 164;BA.debugLine="End Sub";
return "";
}
public static String  _key_f_click() throws Exception{
 //BA.debugLineNum = 218;BA.debugLine="Sub key_f_Click";
 //BA.debugLineNum = 219;BA.debugLine="sendKeystroke(\"0x46\")";
_sendkeystroke("0x46");
 //BA.debugLineNum = 220;BA.debugLine="End Sub";
return "";
}
public static String  _key_g_click() throws Exception{
 //BA.debugLineNum = 214;BA.debugLine="Sub key_g_Click";
 //BA.debugLineNum = 215;BA.debugLine="sendKeystroke(\"0x47\")";
_sendkeystroke("0x47");
 //BA.debugLineNum = 216;BA.debugLine="End Sub";
return "";
}
public static String  _key_h_click() throws Exception{
 //BA.debugLineNum = 210;BA.debugLine="Sub key_h_Click";
 //BA.debugLineNum = 211;BA.debugLine="sendKeystroke(\"0x48\")";
_sendkeystroke("0x48");
 //BA.debugLineNum = 212;BA.debugLine="End Sub";
return "";
}
public static String  _key_i_click() throws Exception{
 //BA.debugLineNum = 247;BA.debugLine="Sub key_i_Click";
 //BA.debugLineNum = 248;BA.debugLine="sendKeystroke(\"0x49\")";
_sendkeystroke("0x49");
 //BA.debugLineNum = 249;BA.debugLine="End Sub";
return "";
}
public static String  _key_j_click() throws Exception{
 //BA.debugLineNum = 206;BA.debugLine="Sub key_j_Click";
 //BA.debugLineNum = 207;BA.debugLine="sendKeystroke(\"0x4A\")";
_sendkeystroke("0x4A");
 //BA.debugLineNum = 208;BA.debugLine="End Sub";
return "";
}
public static String  _key_k_click() throws Exception{
 //BA.debugLineNum = 202;BA.debugLine="Sub key_k_Click";
 //BA.debugLineNum = 203;BA.debugLine="sendKeystroke(\"0x4B\")";
_sendkeystroke("0x4B");
 //BA.debugLineNum = 204;BA.debugLine="End Sub";
return "";
}
public static String  _key_l_click() throws Exception{
 //BA.debugLineNum = 198;BA.debugLine="Sub key_l_Click";
 //BA.debugLineNum = 199;BA.debugLine="sendKeystroke(\"0x4C\")";
_sendkeystroke("0x4C");
 //BA.debugLineNum = 200;BA.debugLine="End Sub";
return "";
}
public static String  _key_m_click() throws Exception{
 //BA.debugLineNum = 170;BA.debugLine="Sub key_m_Click";
 //BA.debugLineNum = 171;BA.debugLine="sendKeystroke(\"0x4D\")";
_sendkeystroke("0x4D");
 //BA.debugLineNum = 172;BA.debugLine="End Sub";
return "";
}
public static String  _key_n_click() throws Exception{
 //BA.debugLineNum = 174;BA.debugLine="Sub key_n_Click";
 //BA.debugLineNum = 175;BA.debugLine="sendKeystroke(\"0x4E\")";
_sendkeystroke("0x4E");
 //BA.debugLineNum = 176;BA.debugLine="End Sub";
return "";
}
public static String  _key_o_click() throws Exception{
 //BA.debugLineNum = 238;BA.debugLine="Sub key_o_Click";
 //BA.debugLineNum = 239;BA.debugLine="sendKeystroke(\"0x4F\")";
_sendkeystroke("0x4F");
 //BA.debugLineNum = 240;BA.debugLine="End Sub";
return "";
}
public static String  _key_p_click() throws Exception{
 //BA.debugLineNum = 234;BA.debugLine="Sub key_p_Click";
 //BA.debugLineNum = 235;BA.debugLine="sendKeystroke(\"0x50\")";
_sendkeystroke("0x50");
 //BA.debugLineNum = 236;BA.debugLine="End Sub";
return "";
}
public static String  _key_q_click() throws Exception{
 //BA.debugLineNum = 106;BA.debugLine="Sub key_q_Click";
 //BA.debugLineNum = 107;BA.debugLine="sendKeystroke(\"0x51\")";
_sendkeystroke("0x51");
 //BA.debugLineNum = 108;BA.debugLine="End Sub";
return "";
}
public static String  _key_r_click() throws Exception{
 //BA.debugLineNum = 243;BA.debugLine="Sub key_r_Click";
 //BA.debugLineNum = 244;BA.debugLine="sendKeystroke(\"0x52\")";
_sendkeystroke("0x52");
 //BA.debugLineNum = 245;BA.debugLine="End Sub";
return "";
}
public static String  _key_s_click() throws Exception{
 //BA.debugLineNum = 226;BA.debugLine="Sub key_s_Click";
 //BA.debugLineNum = 227;BA.debugLine="sendKeystroke(\"0x53\")";
_sendkeystroke("0x53");
 //BA.debugLineNum = 228;BA.debugLine="End Sub";
return "";
}
public static String  _key_shift_click() throws Exception{
 //BA.debugLineNum = 166;BA.debugLine="Sub key_shift_Click";
 //BA.debugLineNum = 167;BA.debugLine="sendKeystroke(\"0x10\")";
_sendkeystroke("0x10");
 //BA.debugLineNum = 168;BA.debugLine="End Sub";
return "";
}
public static String  _key_space_click() throws Exception{
 //BA.debugLineNum = 158;BA.debugLine="Sub key_space_Click";
 //BA.debugLineNum = 159;BA.debugLine="sendKeystroke(\"0x20\")";
_sendkeystroke("0x20");
 //BA.debugLineNum = 160;BA.debugLine="End Sub";
return "";
}
public static String  _key_super_click() throws Exception{
 //BA.debugLineNum = 154;BA.debugLine="Sub key_super_Click";
 //BA.debugLineNum = 155;BA.debugLine="sendKeystroke(\"0x5B\")";
_sendkeystroke("0x5B");
 //BA.debugLineNum = 156;BA.debugLine="End Sub";
return "";
}
public static String  _key_t_click() throws Exception{
 //BA.debugLineNum = 255;BA.debugLine="Sub key_t_Click";
 //BA.debugLineNum = 256;BA.debugLine="sendKeystroke(\"0x54\")";
_sendkeystroke("0x54");
 //BA.debugLineNum = 257;BA.debugLine="End Sub";
return "";
}
public static String  _key_u_click() throws Exception{
 //BA.debugLineNum = 251;BA.debugLine="Sub key_u_Click";
 //BA.debugLineNum = 252;BA.debugLine="sendKeystroke(\"0x55\")";
_sendkeystroke("0x55");
 //BA.debugLineNum = 253;BA.debugLine="End Sub";
return "";
}
public static String  _key_v_click() throws Exception{
 //BA.debugLineNum = 182;BA.debugLine="Sub key_v_Click";
 //BA.debugLineNum = 183;BA.debugLine="sendKeystroke(\"0x56\")";
_sendkeystroke("0x56");
 //BA.debugLineNum = 184;BA.debugLine="End Sub";
return "";
}
public static String  _key_w_click() throws Exception{
 //BA.debugLineNum = 263;BA.debugLine="Sub key_w_Click";
 //BA.debugLineNum = 264;BA.debugLine="sendKeystroke(\"0x57\")";
_sendkeystroke("0x57");
 //BA.debugLineNum = 265;BA.debugLine="End Sub";
return "";
}
public static String  _key_x_click() throws Exception{
 //BA.debugLineNum = 190;BA.debugLine="Sub key_x_Click";
 //BA.debugLineNum = 191;BA.debugLine="sendKeystroke(\"0x58\")";
_sendkeystroke("0x58");
 //BA.debugLineNum = 192;BA.debugLine="End Sub";
return "";
}
public static String  _key_z_click() throws Exception{
 //BA.debugLineNum = 194;BA.debugLine="Sub key_z_Click";
 //BA.debugLineNum = 195;BA.debugLine="sendKeystroke(\"0x5A\")";
_sendkeystroke("0x5A");
 //BA.debugLineNum = 196;BA.debugLine="End Sub";
return "";
}
public static void  _mouse_touch(int _action,float _x,float _y) throws Exception{
ResumableSub_mouse_Touch rsub = new ResumableSub_mouse_Touch(null,_action,_x,_y);
rsub.resume(processBA, null);
}
public static class ResumableSub_mouse_Touch extends BA.ResumableSub {
public ResumableSub_mouse_Touch(com.pacchetto.keyboard parent,int _action,float _x,float _y) {
this.parent = parent;
this._action = _action;
this._x = _x;
this._y = _y;
}
com.pacchetto.keyboard parent;
int _action;
float _x;
float _y;
String _new_mode = "";
int _finalx = 0;
int _finaly = 0;
String _new_pos = "";

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = -1;
 //BA.debugLineNum = 93;BA.debugLine="Dim new_mode As String = \"mouse\"";
_new_mode = "mouse";
 //BA.debugLineNum = 94;BA.debugLine="Dim finalX, finalY As Int";
_finalx = 0;
_finaly = 0;
 //BA.debugLineNum = 95;BA.debugLine="controls.AStream.Write(new_mode.GetBytes(\"UTF8\"))";
parent.mostCurrent._controls._astream.Write(_new_mode.getBytes("UTF8"));
 //BA.debugLineNum = 96;BA.debugLine="X = (X*100)/mouse.Width";
_x = (float) ((_x*100)/(double)parent.mostCurrent._mouse.getWidth());
 //BA.debugLineNum = 97;BA.debugLine="Y = (Y*100)/mouse.Height";
_y = (float) ((_y*100)/(double)parent.mostCurrent._mouse.getHeight());
 //BA.debugLineNum = 98;BA.debugLine="finalX = X";
_finalx = (int) (_x);
 //BA.debugLineNum = 99;BA.debugLine="finalY = Y";
_finaly = (int) (_y);
 //BA.debugLineNum = 100;BA.debugLine="Log(\"POS : X \"&finalX&\" Y: \"&finalY)";
anywheresoftware.b4a.keywords.Common.Log("POS : X "+BA.NumberToString(_finalx)+" Y: "+BA.NumberToString(_finaly));
 //BA.debugLineNum = 101;BA.debugLine="Dim new_pos As String = finalX&\"-\"&finalY";
_new_pos = BA.NumberToString(_finalx)+"-"+BA.NumberToString(_finaly);
 //BA.debugLineNum = 102;BA.debugLine="controls.AStream.Write(new_pos.GetBytes(\"UTF8\"))";
parent.mostCurrent._controls._astream.Write(_new_pos.getBytes("UTF8"));
 //BA.debugLineNum = 103;BA.debugLine="Sleep(100)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (100));
this.state = 1;
return;
case 1:
//C
this.state = -1;
;
 //BA.debugLineNum = 104;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _mouseleft_click() throws Exception{
 //BA.debugLineNum = 271;BA.debugLine="Sub mouseLeft_Click";
 //BA.debugLineNum = 272;BA.debugLine="sendKeystroke(\"0x01\")";
_sendkeystroke("0x01");
 //BA.debugLineNum = 273;BA.debugLine="End Sub";
return "";
}
public static String  _mouseright_click() throws Exception{
 //BA.debugLineNum = 267;BA.debugLine="Sub mouseRight_Click";
 //BA.debugLineNum = 268;BA.debugLine="sendKeystroke(\"0x02\")";
_sendkeystroke("0x02");
 //BA.debugLineNum = 269;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _sendkeystroke(String _key) throws Exception{
String _new_mode = "";
 //BA.debugLineNum = 75;BA.debugLine="Sub sendKeystroke(key As String)";
 //BA.debugLineNum = 76;BA.debugLine="Dim new_mode As String = \"keyboard\"";
_new_mode = "keyboard";
 //BA.debugLineNum = 77;BA.debugLine="controls.AStream.Write(new_mode.GetBytes(\"UTF8\"))";
mostCurrent._controls._astream.Write(_new_mode.getBytes("UTF8"));
 //BA.debugLineNum = 78;BA.debugLine="controls.AStream.Write(key.GetBytes(\"UTF8\"))";
mostCurrent._controls._astream.Write(_key.getBytes("UTF8"));
 //BA.debugLineNum = 79;BA.debugLine="Log(key)";
anywheresoftware.b4a.keywords.Common.Log(_key);
 //BA.debugLineNum = 80;BA.debugLine="Main.vib.Vibrate(Main.vibDuration)";
mostCurrent._main._vib.Vibrate(processBA,mostCurrent._main._vibduration);
 //BA.debugLineNum = 81;BA.debugLine="End Sub";
return "";
}
}
