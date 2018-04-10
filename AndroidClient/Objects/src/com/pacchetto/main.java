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

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "com.pacchetto", "com.pacchetto.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
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
		activityBA = new BA(this, layout, processBA, "com.pacchetto", "com.pacchetto.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.pacchetto.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
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
		return main.class;
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
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (main) Resume **");
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
public static anywheresoftware.b4a.objects.Serial.BluetoothAdmin _admin = null;
public static anywheresoftware.b4a.objects.Serial _serial1 = null;
public static anywheresoftware.b4a.objects.collections.List _founddevices = null;
public static com.pacchetto.main._nameandmac _connecteddevice = null;
public static anywheresoftware.b4a.phone.Phone.PhoneVibrate _vib = null;
public static long _vibduration = 0L;
public anywheresoftware.b4a.objects.ButtonWrapper _btnsearchfordevices = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnallowconnection = null;
public com.pacchetto.controls _controls = null;
public com.pacchetto.keyboard _keyboard = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (controls.mostCurrent != null);
vis = vis | (keyboard.mostCurrent != null);
return vis;}
public static class _nameandmac{
public boolean IsInitialized;
public String Name;
public String Mac;
public void Initialize() {
IsInitialized = true;
Name = "";
Mac = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _i = null;
 //BA.debugLineNum = 29;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 30;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 31;BA.debugLine="admin.Initialize(\"admin\")";
_admin.Initialize(processBA,"admin");
 //BA.debugLineNum = 32;BA.debugLine="serial1.Initialize(\"serial1\")";
_serial1.Initialize("serial1");
 };
 //BA.debugLineNum = 34;BA.debugLine="Activity.LoadLayout(\"1\")";
mostCurrent._activity.LoadLayout("1",mostCurrent.activityBA);
 //BA.debugLineNum = 38;BA.debugLine="Dim i As Intent";
_i = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 39;BA.debugLine="i.Initialize(\"android.bluetooth.adapter.action.RE";
_i.Initialize("android.bluetooth.adapter.action.REQUEST_DISCOVERABLE","");
 //BA.debugLineNum = 40;BA.debugLine="i.PutExtra(\"android.bluetooth.adapter.extra.DISCO";
_i.PutExtra("android.bluetooth.adapter.extra.DISCOVERABLE_DURATION",(Object)(600));
 //BA.debugLineNum = 41;BA.debugLine="StartActivity(i)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_i.getObject()));
 //BA.debugLineNum = 42;BA.debugLine="ToastMessageShow(\"Attendo connessioni...\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Attendo connessioni..."),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 43;BA.debugLine="serial1.Listen";
_serial1.Listen(processBA);
 //BA.debugLineNum = 45;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 68;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 69;BA.debugLine="If UserClosed = True Then";
if (_userclosed==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 70;BA.debugLine="serial1.Disconnect";
_serial1.Disconnect();
 };
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 48;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 49;BA.debugLine="btnSearchForDevices.Enabled = False";
mostCurrent._btnsearchfordevices.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 50;BA.debugLine="btnAllowConnection.Enabled = False";
mostCurrent._btnallowconnection.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 51;BA.debugLine="If admin.IsEnabled = False Then";
if (_admin.IsEnabled()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 52;BA.debugLine="If admin.Enable = False Then";
if (_admin.Enable()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 53;BA.debugLine="ToastMessageShow(\"Error enabling Bluetooth adap";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error enabling Bluetooth adapter."),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 55;BA.debugLine="ToastMessageShow(\"Enabling Bluetooth adapter...";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Enabling Bluetooth adapter..."),anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
 //BA.debugLineNum = 59;BA.debugLine="Admin_StateChanged(admin.STATE_ON, 0)";
_admin_statechanged(_admin.STATE_ON,(int) (0));
 };
 //BA.debugLineNum = 61;BA.debugLine="End Sub";
return "";
}
public static String  _admin_devicefound(String _name,String _macaddress) throws Exception{
com.pacchetto.main._nameandmac _nm = null;
 //BA.debugLineNum = 105;BA.debugLine="Sub Admin_DeviceFound (Name As String, MacAddress";
 //BA.debugLineNum = 106;BA.debugLine="Log(Name & \":\" & MacAddress)";
anywheresoftware.b4a.keywords.Common.Log(_name+":"+_macaddress);
 //BA.debugLineNum = 107;BA.debugLine="Dim nm As NameAndMac";
_nm = new com.pacchetto.main._nameandmac();
 //BA.debugLineNum = 108;BA.debugLine="nm.Name = Name";
_nm.Name = _name;
 //BA.debugLineNum = 109;BA.debugLine="nm.Mac = MacAddress";
_nm.Mac = _macaddress;
 //BA.debugLineNum = 110;BA.debugLine="foundDevices.Add(nm)";
_founddevices.Add((Object)(_nm));
 //BA.debugLineNum = 111;BA.debugLine="ProgressDialogShow(\"Cerco dispositivi (~ trovato)";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Cerco dispositivi (~ trovato)...".replace("~",BA.NumberToString(_founddevices.getSize()))));
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return "";
}
public static String  _admin_discoveryfinished() throws Exception{
anywheresoftware.b4a.objects.collections.List _l = null;
int _i = 0;
com.pacchetto.main._nameandmac _nm = null;
int _res = 0;
 //BA.debugLineNum = 83;BA.debugLine="Sub Admin_DiscoveryFinished";
 //BA.debugLineNum = 84;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 85;BA.debugLine="If foundDevices.Size = 0 Then";
if (_founddevices.getSize()==0) { 
 //BA.debugLineNum = 86;BA.debugLine="ToastMessageShow(\"Nessun dispositivo trovato.\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Nessun dispositivo trovato."),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 88;BA.debugLine="Dim l As List";
_l = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 89;BA.debugLine="l.Initialize";
_l.Initialize();
 //BA.debugLineNum = 90;BA.debugLine="For i = 0 To foundDevices.Size - 1";
{
final int step7 = 1;
final int limit7 = (int) (_founddevices.getSize()-1);
_i = (int) (0) ;
for (;(step7 > 0 && _i <= limit7) || (step7 < 0 && _i >= limit7) ;_i = ((int)(0 + _i + step7))  ) {
 //BA.debugLineNum = 91;BA.debugLine="Dim nm As NameAndMac";
_nm = new com.pacchetto.main._nameandmac();
 //BA.debugLineNum = 92;BA.debugLine="nm = foundDevices.Get(i)";
_nm = (com.pacchetto.main._nameandmac)(_founddevices.Get(_i));
 //BA.debugLineNum = 93;BA.debugLine="l.Add(nm.Name)";
_l.Add((Object)(_nm.Name));
 }
};
 //BA.debugLineNum = 95;BA.debugLine="Dim res As Int";
_res = 0;
 //BA.debugLineNum = 96;BA.debugLine="res = InputList(l, \"Scegli a quale dispositivo c";
_res = anywheresoftware.b4a.keywords.Common.InputList(_l,BA.ObjectToCharSequence("Scegli a quale dispositivo collegarti"),(int) (-1),mostCurrent.activityBA);
 //BA.debugLineNum = 97;BA.debugLine="If res <> DialogResponse.CANCEL Then";
if (_res!=anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
 //BA.debugLineNum = 98;BA.debugLine="connectedDevice = foundDevices.Get(res)";
_connecteddevice = (com.pacchetto.main._nameandmac)(_founddevices.Get(_res));
 //BA.debugLineNum = 99;BA.debugLine="ProgressDialogShow(\"Provo a connettermi a : \" &";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Provo a connettermi a : "+_connecteddevice.Name+" ("+_connecteddevice.Mac+")"));
 //BA.debugLineNum = 100;BA.debugLine="serial1.Connect(connectedDevice.Mac)";
_serial1.Connect(processBA,_connecteddevice.Mac);
 };
 };
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
return "";
}
public static String  _admin_statechanged(int _newstate,int _oldstate) throws Exception{
 //BA.debugLineNum = 63;BA.debugLine="Sub Admin_StateChanged (NewState As Int, OldState";
 //BA.debugLineNum = 64;BA.debugLine="btnSearchForDevices.Enabled = (NewState = admin.S";
mostCurrent._btnsearchfordevices.setEnabled((_newstate==_admin.STATE_ON));
 //BA.debugLineNum = 65;BA.debugLine="btnAllowConnection.Enabled = btnSearchForDevices.";
mostCurrent._btnallowconnection.setEnabled(mostCurrent._btnsearchfordevices.getEnabled());
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public static String  _btnallowconnection_click() throws Exception{
 //BA.debugLineNum = 114;BA.debugLine="Sub btnAllowConnection_Click";
 //BA.debugLineNum = 118;BA.debugLine="End Sub";
return "";
}
public static String  _btnsearchfordevices_click() throws Exception{
 //BA.debugLineNum = 74;BA.debugLine="Sub btnSearchForDevices_Click";
 //BA.debugLineNum = 75;BA.debugLine="foundDevices.Initialize";
_founddevices.Initialize();
 //BA.debugLineNum = 76;BA.debugLine="If admin.StartDiscovery	= False Then";
if (_admin.StartDiscovery()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 77;BA.debugLine="ToastMessageShow(\"Error starting discovery proce";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error starting discovery process."),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 79;BA.debugLine="ProgressDialogShow(\"Cerco computer nelle vicinan";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Cerco computer nelle vicinanze..."));
 };
 //BA.debugLineNum = 81;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 23;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 24;BA.debugLine="Dim btnSearchForDevices As Button";
mostCurrent._btnsearchfordevices = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim btnAllowConnection As Button";
mostCurrent._btnallowconnection = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
controls._process_globals();
keyboard._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 14;BA.debugLine="Dim admin As BluetoothAdmin";
_admin = new anywheresoftware.b4a.objects.Serial.BluetoothAdmin();
 //BA.debugLineNum = 15;BA.debugLine="Dim serial1 As Serial";
_serial1 = new anywheresoftware.b4a.objects.Serial();
 //BA.debugLineNum = 16;BA.debugLine="Dim foundDevices As List";
_founddevices = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 17;BA.debugLine="Type NameAndMac (Name As String, Mac As String)";
;
 //BA.debugLineNum = 18;BA.debugLine="Dim connectedDevice As NameAndMac";
_connecteddevice = new com.pacchetto.main._nameandmac();
 //BA.debugLineNum = 19;BA.debugLine="Dim vib As PhoneVibrate";
_vib = new anywheresoftware.b4a.phone.Phone.PhoneVibrate();
 //BA.debugLineNum = 20;BA.debugLine="Dim vibDuration As Long = 25";
_vibduration = (long) (25);
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return "";
}
public static String  _serial1_connected(boolean _success) throws Exception{
 //BA.debugLineNum = 120;BA.debugLine="Sub Serial1_Connected (Success As Boolean)";
 //BA.debugLineNum = 121;BA.debugLine="Log(Success)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(_success));
 //BA.debugLineNum = 122;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 124;BA.debugLine="If Success = False Then";
if (_success==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 125;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.Log(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage());
 //BA.debugLineNum = 126;BA.debugLine="ToastMessageShow(\"Errore di connessione : \" & La";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Errore di connessione : "+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage()),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 128;BA.debugLine="StartActivity(controls)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._controls.getObject()));
 //BA.debugLineNum = 129;BA.debugLine="vib.Vibrate(vibDuration)";
_vib.Vibrate(processBA,_vibduration);
 };
 //BA.debugLineNum = 132;BA.debugLine="End Sub";
return "";
}
}
