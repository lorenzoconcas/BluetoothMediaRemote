package com.loreconcas.btmr;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.debug.*;

public class main extends javafx.application.Application{
public static main mostCurrent = new main();

public static BA ba;
static {
		ba = new  anywheresoftware.b4j.objects.FxBA("com.loreconcas.btmr", "com.loreconcas.btmr.main", null);
		ba.loadHtSubs(main.class);
        if (ba.getClass().getName().endsWith("ShellBA")) {
			
			ba.raiseEvent2(null, true, "SHELL", false);
			ba.raiseEvent2(null, true, "CREATE", true, "com.loreconcas.btmr.main", ba);
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}

 
    public static void main(String[] args) {
    	launch(args);
    }
    public void start (javafx.stage.Stage stage) {
        try {
            if (!false)
                System.setProperty("prism.lcdtext", "false");
            anywheresoftware.b4j.objects.FxBA.application = this;
		    anywheresoftware.b4a.keywords.Common.setDensity(javafx.stage.Screen.getPrimary().getDpi());
            anywheresoftware.b4a.keywords.Common.LogDebug("Program started.");
            initializeProcessGlobals();
            anywheresoftware.b4j.objects.Form frm = new anywheresoftware.b4j.objects.Form();
            frm.initWithStage(ba, stage, 360, 400);
            ba.raiseEvent(null, "appstart", frm, (String[])getParameters().getRaw().toArray(new String[0]));
        } catch (Throwable t) {
            BA.printException(t, true);
            System.exit(1);
        }
    }
public static anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4j.objects.JFX _fx = null;
public static anywheresoftware.b4j.objects.Form _mainform = null;
public static anywheresoftware.b4j.objects.Bluetooth _bt = null;
public static anywheresoftware.b4j.objects.ListViewWrapper _listview1 = null;
public static anywheresoftware.b4j.objects.ButtonWrapper _btnsearch = null;
public static anywheresoftware.b4j.objects.LabelWrapper _lblconnectionstate = null;
public static anywheresoftware.b4j.objects.ButtonWrapper _btnconnect = null;
public static anywheresoftware.b4j.objects.ButtonWrapper _btndisconnect = null;
public static anywheresoftware.b4j.objects.LabelWrapper _lblsearchstate = null;
public static boolean _connected = false;
public static boolean _searchingfordevices = false;
public static anywheresoftware.b4j.objects.Bluetooth.BluetoothConnection _currentconnection = null;
public static anywheresoftware.b4a.randomaccessfile.AsyncStreams _astream = null;
public static anywheresoftware.b4a.objects.collections.Map _founddevices = null;
public static int _mode = 0;
public static anywheresoftware.b4j.object.JavaObject _robot = null;
public static boolean  _application_error(anywheresoftware.b4a.objects.B4AException _error,String _stacktrace) throws Exception{
 //BA.debugLineNum = 54;BA.debugLine="Sub Application_Error (Error As Exception, StackTr";
 //BA.debugLineNum = 55;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return false;
}
public static String  _appstart(anywheresoftware.b4j.objects.Form _form1,String[] _args) throws Exception{
anywheresoftware.b4j.object.JavaObject _jo = null;
 //BA.debugLineNum = 29;BA.debugLine="Sub AppStart (Form1 As Form, Args() As String)";
 //BA.debugLineNum = 31;BA.debugLine="MainForm = Form1";
_mainform = _form1;
 //BA.debugLineNum = 32;BA.debugLine="MainForm.Icon = fx.LoadImage(File.DirAssets, \"ico";
_mainform.setIcon((javafx.scene.image.Image)(_fx.LoadImage(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"icon.png").getObject()));
 //BA.debugLineNum = 33;BA.debugLine="MainForm.RootPane.LoadLayout(\"1\") 'Load the layou";
_mainform.getRootPane().LoadLayout(ba,"1");
 //BA.debugLineNum = 34;BA.debugLine="MainForm.Show";
_mainform.Show();
 //BA.debugLineNum = 35;BA.debugLine="MainForm.Title = \"Bluetooth Media Remote\"";
_mainform.setTitle("Bluetooth Media Remote");
 //BA.debugLineNum = 37;BA.debugLine="bt.Initialize(\"bt\")";
_bt.Initialize(ba,"bt");
 //BA.debugLineNum = 38;BA.debugLine="If bt.IsEnabled Then";
if (_bt.getIsEnabled()) { 
 //BA.debugLineNum = 39;BA.debugLine="bt.Listen";
_bt.Listen();
 };
 //BA.debugLineNum = 42;BA.debugLine="foundDevices.Initialize";
_founddevices.Initialize();
 //BA.debugLineNum = 43;BA.debugLine="UpdateState";
_updatestate();
 //BA.debugLineNum = 45;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 46;BA.debugLine="jo.InitializeStatic(\"com.sun.glass.ui.Application";
_jo.InitializeStatic("com.sun.glass.ui.Application");
 //BA.debugLineNum = 47;BA.debugLine="robot = jo.RunMethodJO(\"GetApplication\", Null).Ru";
_robot = _jo.RunMethodJO("GetApplication",(Object[])(anywheresoftware.b4a.keywords.Common.Null)).RunMethodJO("createRobot",(Object[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public static String  _astream_error() throws Exception{
 //BA.debugLineNum = 162;BA.debugLine="Sub AStream_Error";
 //BA.debugLineNum = 163;BA.debugLine="connected = False";
_connected = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 164;BA.debugLine="UpdateState";
_updatestate();
 //BA.debugLineNum = 165;BA.debugLine="End Sub";
return "";
}
public static String  _astream_newdata(byte[] _buffer) throws Exception{
String _command = "";
 //BA.debugLineNum = 126;BA.debugLine="Sub AStream_NewData (Buffer() As Byte)";
 //BA.debugLineNum = 127;BA.debugLine="Dim command As String = BytesToString(Buffer, 0,";
_command = anywheresoftware.b4a.keywords.Common.BytesToString(_buffer,(int) (0),_buffer.length,"UTF8");
 //BA.debugLineNum = 129;BA.debugLine="If(Not(command == \"\")) Then";
if ((anywheresoftware.b4a.keywords.Common.Not((_command).equals("")))) { 
 //BA.debugLineNum = 130;BA.debugLine="If(command == \"keyboard\") Then";
if (((_command).equals("keyboard"))) { 
 //BA.debugLineNum = 131;BA.debugLine="mode = 1";
_mode = (int) (1);
 }else if(((_command).equals("media"))) { 
 //BA.debugLineNum = 133;BA.debugLine="mode = 0";
_mode = (int) (0);
 }else if(((_command).equals("mouse"))) { 
 //BA.debugLineNum = 136;BA.debugLine="mode = 2";
_mode = (int) (2);
 }else if(((_command).equals("scrof"))) { 
 //BA.debugLineNum = 138;BA.debugLine="scrof";
_scrof();
 }else {
 //BA.debugLineNum = 140;BA.debugLine="If(mode == 1) Then";
if ((_mode==1)) { 
 //BA.debugLineNum = 141;BA.debugLine="keyStroke(command)";
_keystroke(_command);
 }else if((_mode==2)) { 
 //BA.debugLineNum = 143;BA.debugLine="mouse(command)";
_mouse(_command);
 }else if((_mode==0)) { 
 //BA.debugLineNum = 145;BA.debugLine="shTest(command)";
_shtest((int)(Double.parseDouble(_command)));
 };
 };
 };
 //BA.debugLineNum = 149;BA.debugLine="End Sub";
return "";
}
public static String  _astream_terminated() throws Exception{
 //BA.debugLineNum = 167;BA.debugLine="Sub AStream_Terminated";
 //BA.debugLineNum = 168;BA.debugLine="AStream_Error";
_astream_error();
 //BA.debugLineNum = 169;BA.debugLine="End Sub";
return "";
}
public static String  _bt_connected(boolean _success,anywheresoftware.b4j.objects.Bluetooth.BluetoothConnection _connection) throws Exception{
 //BA.debugLineNum = 112;BA.debugLine="Private Sub bt_Connected (Success As Boolean, conn";
 //BA.debugLineNum = 113;BA.debugLine="Log($\"Connected, success=${Success}\"$)";
anywheresoftware.b4a.keywords.Common.Log(("Connected, success="+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_success))+""));
 //BA.debugLineNum = 114;BA.debugLine="If Success Then";
if (_success) { 
 //BA.debugLineNum = 115;BA.debugLine="currentConnection = connection";
_currentconnection = _connection;
 //BA.debugLineNum = 116;BA.debugLine="connected = True";
_connected = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 117;BA.debugLine="If astream.IsInitialized Then astream.Close";
if (_astream.IsInitialized()) { 
_astream.Close();};
 //BA.debugLineNum = 118;BA.debugLine="astream.InitializePrefix(connection.InputStream,";
_astream.InitializePrefix(ba,(java.io.InputStream)(_connection.getInputStream().getObject()),anywheresoftware.b4a.keywords.Common.True,(java.io.OutputStream)(_connection.getOutputStream().getObject()),"AStream");
 };
 //BA.debugLineNum = 120;BA.debugLine="bt.Listen";
_bt.Listen();
 //BA.debugLineNum = 121;BA.debugLine="UpdateState";
_updatestate();
 //BA.debugLineNum = 123;BA.debugLine="End Sub";
return "";
}
public static String  _bt_devicefound(String _name,String _macaddress) throws Exception{
String _description = "";
 //BA.debugLineNum = 94;BA.debugLine="Private Sub bt_DeviceFound (Name As String, MacAdd";
 //BA.debugLineNum = 95;BA.debugLine="Dim description As String = Name & \": \" & MacAddr";
_description = _name+": "+_macaddress;
 //BA.debugLineNum = 96;BA.debugLine="ListView1.Items.Add(description)";
_listview1.getItems().Add((Object)(_description));
 //BA.debugLineNum = 97;BA.debugLine="foundDevices.Put(description, MacAddress)";
_founddevices.Put((Object)(_description),(Object)(_macaddress));
 //BA.debugLineNum = 98;BA.debugLine="End Sub";
return "";
}
public static String  _bt_discoveryfinished() throws Exception{
 //BA.debugLineNum = 100;BA.debugLine="Private Sub bt_DiscoveryFinished";
 //BA.debugLineNum = 101;BA.debugLine="searchingForDevices = False";
_searchingfordevices = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 102;BA.debugLine="UpdateState";
_updatestate();
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
return "";
}
public static String  _btnconnect_action() throws Exception{
String _address = "";
 //BA.debugLineNum = 105;BA.debugLine="Private Sub btnConnect_Action";
 //BA.debugLineNum = 106;BA.debugLine="Dim address As String = foundDevices.Get(ListView";
_address = BA.ObjectToString(_founddevices.Get(_listview1.getSelectedItem()));
 //BA.debugLineNum = 107;BA.debugLine="Log(ListView1.SelectedItem)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(_listview1.getSelectedItem()));
 //BA.debugLineNum = 108;BA.debugLine="bt.Connect(address)";
_bt.Connect(_address);
 //BA.debugLineNum = 110;BA.debugLine="End Sub";
return "";
}
public static String  _btnsearch_action() throws Exception{
boolean _res = false;
 //BA.debugLineNum = 83;BA.debugLine="Private Sub btnSearch_Action";
 //BA.debugLineNum = 84;BA.debugLine="Dim res As Boolean = bt.StartDiscovery";
_res = _bt.StartDiscovery();
 //BA.debugLineNum = 85;BA.debugLine="If res Then";
if (_res) { 
 //BA.debugLineNum = 86;BA.debugLine="searchingForDevices = True";
_searchingfordevices = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 87;BA.debugLine="ListView1.Items.Clear";
_listview1.getItems().Clear();
 //BA.debugLineNum = 88;BA.debugLine="UpdateState";
_updatestate();
 }else {
 //BA.debugLineNum = 90;BA.debugLine="Log(\"Error starting discovery\")";
anywheresoftware.b4a.keywords.Common.Log("Error starting discovery");
 };
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
return "";
}
public static String  _keystroke(String _command) throws Exception{
anywheresoftware.b4a.objects.collections.List _candidatearg = null;
anywheresoftware.b4j.objects.Shell _sh = null;
String _pathtoexe = "";
 //BA.debugLineNum = 193;BA.debugLine="Sub keyStroke(command As String)";
 //BA.debugLineNum = 194;BA.debugLine="Log(command)";
anywheresoftware.b4a.keywords.Common.Log(_command);
 //BA.debugLineNum = 195;BA.debugLine="Dim candidateArg As List";
_candidatearg = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 196;BA.debugLine="candidateArg.Initialize";
_candidatearg.Initialize();
 //BA.debugLineNum = 197;BA.debugLine="candidateArg.Add(command)";
_candidatearg.Add((Object)(_command));
 //BA.debugLineNum = 198;BA.debugLine="Dim sh As Shell";
_sh = new anywheresoftware.b4j.objects.Shell();
 //BA.debugLineNum = 199;BA.debugLine="Dim pathToExe As String = File.Combine(File.DirAp";
_pathtoexe = anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirApp(),"keyboardExecuter.exe");
 //BA.debugLineNum = 200;BA.debugLine="sh.Initialize(\"test\", pathToExe, candidateArg)";
_sh.Initialize("test",_pathtoexe,_candidatearg);
 //BA.debugLineNum = 201;BA.debugLine="sh.RunSynchronous(-1)";
_sh.RunSynchronous((long) (-1));
 //BA.debugLineNum = 202;BA.debugLine="End Sub";
return "";
}
public static String  _mouse(String _command) throws Exception{
int _x = 0;
int _y = 0;
int _p = 0;
int _i = 0;
 //BA.debugLineNum = 171;BA.debugLine="Sub mouse(command As String)";
 //BA.debugLineNum = 172;BA.debugLine="Dim x , y As Int";
_x = 0;
_y = 0;
 //BA.debugLineNum = 173;BA.debugLine="Dim p As Int";
_p = 0;
 //BA.debugLineNum = 177;BA.debugLine="For i = 0 To command.Length-1";
{
final int step3 = 1;
final int limit3 = (int) (_command.length()-1);
_i = (int) (0) ;
for (;(step3 > 0 && _i <= limit3) || (step3 < 0 && _i >= limit3) ;_i = ((int)(0 + _i + step3))  ) {
 //BA.debugLineNum = 178;BA.debugLine="If command.CharAt(i) == \"-\" Then";
if (_command.charAt(_i)==BA.ObjectToChar("-")) { 
 //BA.debugLineNum = 179;BA.debugLine="p = i ' p è la posizione del trattino";
_p = _i;
 };
 }
};
 //BA.debugLineNum = 187;BA.debugLine="x = fx.PrimaryScreen.MaxX/100*command.SubString2(";
_x = (int) (_fx.getPrimaryScreen().getMaxX()/(double)100*(double)(Double.parseDouble(_command.substring((int) (0),_p))));
 //BA.debugLineNum = 188;BA.debugLine="Log(\"Y : \"&command.SubString2(p+1, command.Length";
anywheresoftware.b4a.keywords.Common.Log("Y : "+_command.substring((int) (_p+1),_command.length()));
 //BA.debugLineNum = 189;BA.debugLine="y = fx.PrimaryScreen.MaxY/100*command.SubString2(";
_y = (int) (_fx.getPrimaryScreen().getMaxY()/(double)100*(double)(Double.parseDouble(_command.substring((int) (_p+1),_command.length()))));
 //BA.debugLineNum = 190;BA.debugLine="Log(\"Pos : \"&x&\"-\"&y)";
anywheresoftware.b4a.keywords.Common.Log("Pos : "+BA.NumberToString(_x)+"-"+BA.NumberToString(_y));
 //BA.debugLineNum = 191;BA.debugLine="robot.RunMethod(\"mouseMove\", Array As Object(x, y";
_robot.RunMethod("mouseMove",new Object[]{(Object)(_x),(Object)(_y)});
 //BA.debugLineNum = 192;BA.debugLine="End Sub";
return "";
}

private static boolean processGlobalsRun;
public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Private fx As JFX";
_fx = new anywheresoftware.b4j.objects.JFX();
 //BA.debugLineNum = 8;BA.debugLine="Private MainForm As Form";
_mainform = new anywheresoftware.b4j.objects.Form();
 //BA.debugLineNum = 10;BA.debugLine="Private bt As Bluetooth";
_bt = new anywheresoftware.b4j.objects.Bluetooth();
 //BA.debugLineNum = 11;BA.debugLine="Private ListView1 As ListView";
_listview1 = new anywheresoftware.b4j.objects.ListViewWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Private btnSearch As Button";
_btnsearch = new anywheresoftware.b4j.objects.ButtonWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Private lblConnectionState As Label";
_lblconnectionstate = new anywheresoftware.b4j.objects.LabelWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private btnConnect As Button";
_btnconnect = new anywheresoftware.b4j.objects.ButtonWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private btnDisconnect As Button";
_btndisconnect = new anywheresoftware.b4j.objects.ButtonWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private lblSearchState As Label";
_lblsearchstate = new anywheresoftware.b4j.objects.LabelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private connected As Boolean";
_connected = false;
 //BA.debugLineNum = 19;BA.debugLine="Private searchingForDevices As Boolean";
_searchingfordevices = false;
 //BA.debugLineNum = 20;BA.debugLine="Private currentConnection As BluetoothConnection";
_currentconnection = new anywheresoftware.b4j.objects.Bluetooth.BluetoothConnection();
 //BA.debugLineNum = 21;BA.debugLine="Private astream As AsyncStreams";
_astream = new anywheresoftware.b4a.randomaccessfile.AsyncStreams();
 //BA.debugLineNum = 22;BA.debugLine="Private foundDevices As Map";
_founddevices = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 24;BA.debugLine="Private mode As Int = 0";
_mode = (int) (0);
 //BA.debugLineNum = 25;BA.debugLine="Private robot As JavaObject";
_robot = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return "";
}
public static String  _scrof() throws Exception{
anywheresoftware.b4a.objects.collections.List _candidatearg = null;
anywheresoftware.b4j.objects.Shell _sh = null;
String _pathtoexe = "";
 //BA.debugLineNum = 151;BA.debugLine="Sub scrof";
 //BA.debugLineNum = 153;BA.debugLine="Dim candidateArg As List";
_candidatearg = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 154;BA.debugLine="candidateArg.Initialize";
_candidatearg.Initialize();
 //BA.debugLineNum = 155;BA.debugLine="candidateArg.Add(\"\")";
_candidatearg.Add((Object)(""));
 //BA.debugLineNum = 156;BA.debugLine="Dim sh As Shell";
_sh = new anywheresoftware.b4j.objects.Shell();
 //BA.debugLineNum = 157;BA.debugLine="Dim pathToExe As String = File.Combine(File.DirAp";
_pathtoexe = anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirApp(),"scrof.exe");
 //BA.debugLineNum = 158;BA.debugLine="sh.Initialize(\"test\", pathToExe, candidateArg)";
_sh.Initialize("test",_pathtoexe,_candidatearg);
 //BA.debugLineNum = 159;BA.debugLine="sh.RunSynchronous(-1)";
_sh.RunSynchronous((long) (-1));
 //BA.debugLineNum = 160;BA.debugLine="End Sub";
return "";
}
public static String  _shtest(int _id) throws Exception{
anywheresoftware.b4a.objects.collections.List _arglist = null;
anywheresoftware.b4a.objects.collections.List _candidatearg = null;
anywheresoftware.b4j.objects.Shell _sh = null;
String _pathtoexe = "";
 //BA.debugLineNum = 204;BA.debugLine="Sub shTest(id As Int)";
 //BA.debugLineNum = 205;BA.debugLine="Dim argList As List";
_arglist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 206;BA.debugLine="argList.Initialize";
_arglist.Initialize();
 //BA.debugLineNum = 207;BA.debugLine="argList.AddAll(Array As String(\"play\", \"pause\", \"";
_arglist.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"play","pause","next","prev","VUP","VDOWN","VMUTE",""}));
 //BA.debugLineNum = 208;BA.debugLine="Dim candidateArg As List";
_candidatearg = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 209;BA.debugLine="candidateArg.Initialize";
_candidatearg.Initialize();
 //BA.debugLineNum = 210;BA.debugLine="candidateArg.Add(argList.Get(id))";
_candidatearg.Add(_arglist.Get(_id));
 //BA.debugLineNum = 211;BA.debugLine="Dim sh As Shell";
_sh = new anywheresoftware.b4j.objects.Shell();
 //BA.debugLineNum = 212;BA.debugLine="Dim pathToExe As String = File.Combine(File.DirAp";
_pathtoexe = anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirApp(),"MediaExecuter.exe");
 //BA.debugLineNum = 213;BA.debugLine="sh.Initialize(\"test\", pathToExe, candidateArg)";
_sh.Initialize("test",_pathtoexe,_candidatearg);
 //BA.debugLineNum = 214;BA.debugLine="sh.RunSynchronous(-1)";
_sh.RunSynchronous((long) (-1));
 //BA.debugLineNum = 215;BA.debugLine="End Sub";
return "";
}
public static String  _updatestate() throws Exception{
anywheresoftware.b4j.objects.NodeWrapper.ConcreteNodeWrapper _n = null;
String _state = "";
 //BA.debugLineNum = 58;BA.debugLine="Private Sub UpdateState";
 //BA.debugLineNum = 59;BA.debugLine="If bt.IsEnabled = False Then";
if (_bt.getIsEnabled()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 60;BA.debugLine="lblSearchState.Text = \"Bluetooth not available\"";
_lblsearchstate.setText("Bluetooth not available");
 //BA.debugLineNum = 61;BA.debugLine="For Each n As Node In MainForm.RootPane";
_n = new anywheresoftware.b4j.objects.NodeWrapper.ConcreteNodeWrapper();
{
final anywheresoftware.b4a.BA.IterableList group3 = _mainform.getRootPane();
final int groupLen3 = group3.getSize()
;int index3 = 0;
;
for (; index3 < groupLen3;index3++){
_n.setObject((javafx.scene.Node)(group3.Get(index3)));
 //BA.debugLineNum = 62;BA.debugLine="n.Enabled = False";
_n.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 }
};
 }else {
 //BA.debugLineNum = 66;BA.debugLine="btnSearch.Enabled = Not(searchingForDevices)";
_btnsearch.setEnabled(anywheresoftware.b4a.keywords.Common.Not(_searchingfordevices));
 //BA.debugLineNum = 68;BA.debugLine="btnConnect.Enabled = True";
_btnconnect.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 69;BA.debugLine="Log(btnConnect.Enabled)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(_btnconnect.getEnabled()));
 //BA.debugLineNum = 70;BA.debugLine="btnDisconnect.Enabled = True";
_btndisconnect.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 71;BA.debugLine="Dim state As String";
_state = "";
 //BA.debugLineNum = 72;BA.debugLine="If connected Then";
if (_connected) { 
 //BA.debugLineNum = 73;BA.debugLine="state = \"Connected: \" & currentConnection.Name";
_state = "Connected: "+_currentconnection.getName();
 }else {
 //BA.debugLineNum = 75;BA.debugLine="state = \"Disconnected\"";
_state = "Disconnected";
 };
 //BA.debugLineNum = 77;BA.debugLine="lblConnectionState.Text = state";
_lblconnectionstate.setText(_state);
 //BA.debugLineNum = 78;BA.debugLine="lblSearchState.Text = \"Searching...\"";
_lblsearchstate.setText("Searching...");
 //BA.debugLineNum = 79;BA.debugLine="lblSearchState.Visible = searchingForDevices";
_lblsearchstate.setVisible(_searchingfordevices);
 };
 //BA.debugLineNum = 81;BA.debugLine="End Sub";
return "";
}
}
