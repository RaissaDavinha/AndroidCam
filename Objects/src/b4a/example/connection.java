package b4a.example;


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

public class connection extends Activity implements B4AActivity{
	public static connection mostCurrent;
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
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.connection");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (connection).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.connection");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.connection", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (connection) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (connection) Resume **");
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
		return connection.class;
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
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (connection) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            connection mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (connection) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
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
public anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
public b4a.example.clseditablegrid _clseg = null;
public b4a.example.customlistview _lsttable = null;
public anywheresoftware.b4a.objects.collections.Map _general = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.maps _maps = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 19;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 20;BA.debugLine="Activity.LoadLayout(\"config\")";
mostCurrent._activity.LoadLayout("config",mostCurrent.activityBA);
 //BA.debugLineNum = 23;BA.debugLine="clsEG.Initialize";
mostCurrent._clseg._initialize /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 24;BA.debugLine="clsEG.cv = lstTable";
mostCurrent._clseg._cv /*b4a.example.customlistview*/  = mostCurrent._lsttable;
 //BA.debugLineNum = 25;BA.debugLine="clsEG.pnl = pnl";
mostCurrent._clseg._pnl /*anywheresoftware.b4a.objects.PanelWrapper*/  = mostCurrent._pnl;
 //BA.debugLineNum = 26;BA.debugLine="clsEG.FontSize = 14";
mostCurrent._clseg._fontsize /*int*/  = (int) (14);
 //BA.debugLineNum = 27;BA.debugLine="clsEG.RowHeight = 50dip			'altura de cada celula";
mostCurrent._clseg._rowheight /*int*/  = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50));
 //BA.debugLineNum = 28;BA.debugLine="clsEG.AddColumn(\"variavel\", \"Nome da variavel\", \"";
mostCurrent._clseg._addcolumn /*String*/ ("variavel","Nome da variavel","800","{0}",mostCurrent._clseg._textalign /*b4a.example.clseditablegrid._textaligntype*/ .Left /*String*/ ,mostCurrent._clseg._edittype /*b4a.example.clseditablegrid._controltype*/ .IsLabel /*String*/ ,mostCurrent._clseg._inputtype /*b4a.example.clseditablegrid._inputtypes*/ .None /*String*/ );
 //BA.debugLineNum = 29;BA.debugLine="clsEG.AddColumn(\"valo\", \"valor\", \"200\", \"{0:#,##0";
mostCurrent._clseg._addcolumn /*String*/ ("valo","valor","200","{0:#,##0.00}",mostCurrent._clseg._textalign /*b4a.example.clseditablegrid._textaligntype*/ .Center /*String*/ ,mostCurrent._clseg._edittype /*b4a.example.clseditablegrid._controltype*/ .IsTextBox /*String*/ ,mostCurrent._clseg._inputtype /*b4a.example.clseditablegrid._inputtypes*/ .Decimal /*String*/ );
 //BA.debugLineNum = 32;BA.debugLine="showJsonFile";
_showjsonfile();
 //BA.debugLineNum = 33;BA.debugLine="clsEG.DrawGrid";
mostCurrent._clseg._drawgrid /*String*/ ();
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 40;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 36;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return "";
}
public static String  _btnback_click() throws Exception{
 //BA.debugLineNum = 51;BA.debugLine="Sub Btnback_Click";
 //BA.debugLineNum = 52;BA.debugLine="File.Delete(File.DirInternal, \"default.json\")";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"default.json");
 //BA.debugLineNum = 53;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public static String  _btnsave_click() throws Exception{
 //BA.debugLineNum = 45;BA.debugLine="Sub BtnSave_Click";
 //BA.debugLineNum = 46;BA.debugLine="saveAndSend";
_saveandsend();
 //BA.debugLineNum = 47;BA.debugLine="MsgboxAsync(\"Configuracao salva\",\"Sucesso\")";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Configuracao salva"),BA.ObjectToCharSequence("Sucesso"),processBA);
 //BA.debugLineNum = 48;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 13;BA.debugLine="Private pnl As Panel";
mostCurrent._pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private clsEG As clsEditableGrid";
mostCurrent._clseg = new b4a.example.clseditablegrid();
 //BA.debugLineNum = 15;BA.debugLine="Private lstTable As CustomListView";
mostCurrent._lsttable = new b4a.example.customlistview();
 //BA.debugLineNum = 16;BA.debugLine="Private general As Map";
mostCurrent._general = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 17;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _saveandsend() throws Exception{
anywheresoftware.b4a.objects.collections.Map _mout = null;
anywheresoftware.b4a.objects.collections.Map _general1 = null;
anywheresoftware.b4a.objects.collections.JSONParser.JSONGenerator _jsongen = null;
int _i = 0;
byte[] _buffer = null;
anywheresoftware.b4a.objects.SocketWrapper.UDPSocket.UDPPacket _packet = null;
 //BA.debugLineNum = 74;BA.debugLine="Sub saveAndSend";
 //BA.debugLineNum = 75;BA.debugLine="Dim mOut As Map";
_mout = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 76;BA.debugLine="Dim general1 As Map";
_general1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 77;BA.debugLine="Dim jsonGEN As JSONGenerator";
_jsongen = new anywheresoftware.b4a.objects.collections.JSONParser.JSONGenerator();
 //BA.debugLineNum = 78;BA.debugLine="Try";
try { //BA.debugLineNum = 79;BA.debugLine="jsonGEN.Initialize(general)";
_jsongen.Initialize(mostCurrent._general);
 //BA.debugLineNum = 80;BA.debugLine="general1.Initialize";
_general1.Initialize();
 //BA.debugLineNum = 82;BA.debugLine="For i = 0 To general.Size-1";
{
final int step7 = 1;
final int limit7 = (int) (mostCurrent._general.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit7 ;_i = _i + step7 ) {
 //BA.debugLineNum = 83;BA.debugLine="mOut = clsEG.GetRow(i)";
_mout = mostCurrent._clseg._getrow /*anywheresoftware.b4a.objects.collections.Map*/ (_i);
 //BA.debugLineNum = 84;BA.debugLine="general1.Put(mOut.GetValueAt(0), mOut.GetValueA";
_general1.Put(_mout.GetValueAt((int) (0)),_mout.GetValueAt((int) (1)));
 }
};
 //BA.debugLineNum = 86;BA.debugLine="mOut.Clear";
_mout.Clear();
 //BA.debugLineNum = 87;BA.debugLine="mOut.Put(\"controle\",\"0\")";
_mout.Put((Object)("controle"),(Object)("0"));
 //BA.debugLineNum = 88;BA.debugLine="mOut.Put(\"general\", general1)";
_mout.Put((Object)("general"),(Object)(_general1.getObject()));
 //BA.debugLineNum = 89;BA.debugLine="jsonGEN.Initialize(mOut)";
_jsongen.Initialize(_mout);
 //BA.debugLineNum = 90;BA.debugLine="Log(jsonGEN.ToString)";
anywheresoftware.b4a.keywords.Common.LogImpl("52424848",_jsongen.ToString(),0);
 //BA.debugLineNum = 91;BA.debugLine="File.OpenOutput(File.DirInternal, \"send.json\", F";
anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"send.json",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 92;BA.debugLine="File.WriteString(File.DirInternal, \"send.json\",j";
anywheresoftware.b4a.keywords.Common.File.WriteString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"send.json",_jsongen.ToString());
 //BA.debugLineNum = 111;BA.debugLine="Dim buffer() As Byte";
_buffer = new byte[(int) (0)];
;
 //BA.debugLineNum = 112;BA.debugLine="File.OpenInput(File.DirInternal, \"send.json\")";
anywheresoftware.b4a.keywords.Common.File.OpenInput(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"send.json");
 //BA.debugLineNum = 113;BA.debugLine="buffer = File.ReadBytes(File.DirInternal, \"send.";
_buffer = anywheresoftware.b4a.keywords.Common.File.ReadBytes(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"send.json");
 //BA.debugLineNum = 115;BA.debugLine="If Starter.UDPSender.IsInitialized Then";
if (mostCurrent._starter._udpsender /*anywheresoftware.b4a.objects.SocketWrapper.UDPSocket*/ .IsInitialized()) { 
 //BA.debugLineNum = 116;BA.debugLine="Dim Packet As UDPPacket";
_packet = new anywheresoftware.b4a.objects.SocketWrapper.UDPSocket.UDPPacket();
 //BA.debugLineNum = 117;BA.debugLine="If Starter.ip <> \"\" Then";
if ((mostCurrent._starter._ip /*String*/ ).equals("") == false) { 
 //BA.debugLineNum = 118;BA.debugLine="Packet.Initialize(buffer, Starter.ip, 5075)";
_packet.Initialize(_buffer,mostCurrent._starter._ip /*String*/ ,(int) (5075));
 //BA.debugLineNum = 119;BA.debugLine="Starter.UDPSender.Send(Packet)";
mostCurrent._starter._udpsender /*anywheresoftware.b4a.objects.SocketWrapper.UDPSocket*/ .Send(_packet);
 }else {
 //BA.debugLineNum = 121;BA.debugLine="MsgboxAsync(\"Nenhum ip encontrado\",\"Erro\")";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Nenhum ip encontrado"),BA.ObjectToCharSequence("Erro"),processBA);
 //BA.debugLineNum = 122;BA.debugLine="Starter.requestedFile = True";
mostCurrent._starter._requestedfile /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 };
 }else {
 //BA.debugLineNum = 125;BA.debugLine="Starter.requestedFile = True";
mostCurrent._starter._requestedfile /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 126;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 128;BA.debugLine="File.Delete(File.DirInternal, \"send.json\")";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"send.json");
 } 
       catch (Exception e36) {
			processBA.setLastException(e36); //BA.debugLineNum = 130;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("52424888",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 132;BA.debugLine="End Sub";
return "";
}
public static String  _showjsonfile() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
anywheresoftware.b4a.objects.collections.JSONParser _json = null;
int _i = 0;
 //BA.debugLineNum = 57;BA.debugLine="Sub showJsonFile";
 //BA.debugLineNum = 58;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 59;BA.debugLine="Dim JSON As JSONParser";
_json = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 60;BA.debugLine="If File.Exists(File.DirInternal,\"default.json\") T";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"default.json")) { 
 //BA.debugLineNum = 61;BA.debugLine="Log(\"default.json existe\")";
anywheresoftware.b4a.keywords.Common.LogImpl("52359300","default.json existe",0);
 //BA.debugLineNum = 62;BA.debugLine="JSON.Initialize(File.ReadString(File.DirInternal";
_json.Initialize(anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"default.json"));
 //BA.debugLineNum = 63;BA.debugLine="Map1 = JSON.NextObject";
_map1 = _json.NextObject();
 //BA.debugLineNum = 64;BA.debugLine="general = Map1.Get(\"general\")";
mostCurrent._general.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_map1.Get((Object)("general"))));
 //BA.debugLineNum = 65;BA.debugLine="For i = 0 To general.Size - 1";
{
final int step8 = 1;
final int limit8 = (int) (mostCurrent._general.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit8 ;_i = _i + step8 ) {
 //BA.debugLineNum = 66;BA.debugLine="clsEG.AddRow(i, Array As String(general.GetKeyA";
mostCurrent._clseg._addrow /*String*/ (BA.NumberToString(_i),new String[]{BA.ObjectToString(mostCurrent._general.GetKeyAt(_i)),BA.ObjectToString(mostCurrent._general.GetValueAt(_i))});
 }
};
 }else {
 //BA.debugLineNum = 69;BA.debugLine="Starter.requestedFile = True";
mostCurrent._starter._requestedfile /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 70;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
}
