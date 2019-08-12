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
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.main");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
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
        if (_activity == null)
            return;
        if (this != mostCurrent)
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
            main mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
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
public anywheresoftware.b4a.objects.ButtonWrapper _btnenter = null;
public anywheresoftware.b4a.objects.EditTextWrapper _password = null;
public anywheresoftware.b4a.objects.EditTextWrapper _username = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnwifi = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblconfig = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcamera = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldaninha = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmap = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltractor = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldata = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblfalha = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblwifi = null;
public static boolean _first_time = false;
public anywheresoftware.b4a.objects.ButtonWrapper _btncamera = null;
public static String _mode = "";
public b4a.example.starter _starter = null;
public b4a.example.connection _connection = null;
public b4a.example.maps _maps = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (connection.mostCurrent != null);
vis = vis | (maps.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 39;BA.debugLine="Sub Activity_Create(firstTime As Boolean)";
 //BA.debugLineNum = 40;BA.debugLine="Activity.LoadLayout(\"login\")";
mostCurrent._activity.LoadLayout("login",mostCurrent.activityBA);
 //BA.debugLineNum = 41;BA.debugLine="first_time = firstTime";
_first_time = _firsttime;
 //BA.debugLineNum = 42;BA.debugLine="File.Delete(File.DirInternal, \"default.json\")";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"default.json");
 //BA.debugLineNum = 43;BA.debugLine="File.Delete(File.DirInternal, \"status.json\")";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"status.json");
 //BA.debugLineNum = 44;BA.debugLine="If File.Exists(File.DirInternal,\"setIP.txt\") And";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"setIP.txt") && (anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"setIP.txt")).equals("") == false) { 
 //BA.debugLineNum = 45;BA.debugLine="Starter.ip = File.ReadString(File.DirInternal, \"";
mostCurrent._starter._ip /*String*/  = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"setIP.txt");
 };
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 53;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 54;BA.debugLine="If UserClosed Then";
if (_userclosed) { 
 //BA.debugLineNum = 57;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 49;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public static void  _btnconfig_click() throws Exception{
ResumableSub_btnconfig_Click rsub = new ResumableSub_btnconfig_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnconfig_Click extends BA.ResumableSub {
public ResumableSub_btnconfig_Click(b4a.example.main parent) {
this.parent = parent;
}
b4a.example.main parent;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = -1;
 //BA.debugLineNum = 94;BA.debugLine="CallSub2(Starter, \"RequestFile\", \"default\")";
anywheresoftware.b4a.keywords.Common.CallSubNew2(processBA,(Object)(parent.mostCurrent._starter.getObject()),"RequestFile",(Object)("default"));
 //BA.debugLineNum = 95;BA.debugLine="Sleep(200)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (200));
this.state = 1;
return;
case 1:
//C
this.state = -1;
;
 //BA.debugLineNum = 96;BA.debugLine="StartActivity(\"connection\")";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)("connection"));
 //BA.debugLineNum = 97;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _btnenter_click() throws Exception{
 //BA.debugLineNum = 65;BA.debugLine="Sub btnEnter_Click";
 //BA.debugLineNum = 66;BA.debugLine="If username.Text.Trim=\"\" Or password.Text.Trim=\"\"";
if ((mostCurrent._username.getText().trim()).equals("") || (mostCurrent._password.getText().trim()).equals("")) { 
 //BA.debugLineNum = 67;BA.debugLine="MsgboxAsync(\"Por favor, preencha usuário e senha";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Por favor, preencha usuário e senha"),BA.ObjectToCharSequence(""),processBA);
 }else if((mostCurrent._username.getText()).equals("admin") && (mostCurrent._password.getText()).equals("admin")) { 
 //BA.debugLineNum = 69;BA.debugLine="If first_time Then";
if (_first_time) { 
 //BA.debugLineNum = 70;BA.debugLine="If Not(File.Exists(File.DirInternal,\"setIP.txt\"";
if (anywheresoftware.b4a.keywords.Common.Not(anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"setIP.txt")) || (anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"setIP.txt")).equals("")) { 
 //BA.debugLineNum = 71;BA.debugLine="setIP";
_setip();
 };
 };
 //BA.debugLineNum = 74;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 75;BA.debugLine="Activity.LoadLayout(\"main\")";
mostCurrent._activity.LoadLayout("main",mostCurrent.activityBA);
 }else {
 //BA.debugLineNum = 77;BA.debugLine="MsgboxAsync(\"usuário ou senha incorreta\",\"\")";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("usuário ou senha incorreta"),BA.ObjectToCharSequence(""),processBA);
 };
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return "";
}
public static String  _btnfalha_click() throws Exception{
 //BA.debugLineNum = 125;BA.debugLine="Sub btnfalha_Click";
 //BA.debugLineNum = 126;BA.debugLine="If Not(mode = \"startFalhaMode\") Then";
if (anywheresoftware.b4a.keywords.Common.Not((mostCurrent._mode).equals("startFalhaMode"))) { 
 //BA.debugLineNum = 127;BA.debugLine="mode = \"startFalhaMode\"";
mostCurrent._mode = "startFalhaMode";
 //BA.debugLineNum = 128;BA.debugLine="CallSub2(Starter, \"RequestFile\", mode)";
anywheresoftware.b4a.keywords.Common.CallSubNew2(processBA,(Object)(mostCurrent._starter.getObject()),"RequestFile",(Object)(mostCurrent._mode));
 }else {
 //BA.debugLineNum = 130;BA.debugLine="mode = \"stopAnyMode\"";
mostCurrent._mode = "stopAnyMode";
 //BA.debugLineNum = 131;BA.debugLine="CallSub2(Starter, \"RequestFile\", mode)";
anywheresoftware.b4a.keywords.Common.CallSubNew2(processBA,(Object)(mostCurrent._starter.getObject()),"RequestFile",(Object)(mostCurrent._mode));
 };
 //BA.debugLineNum = 133;BA.debugLine="MsgboxAsync(\"Comando \" & mode & \" enviado\",\"\")";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Comando "+mostCurrent._mode+" enviado"),BA.ObjectToCharSequence(""),processBA);
 //BA.debugLineNum = 134;BA.debugLine="End Sub";
return "";
}
public static void  _btnmap_click() throws Exception{
ResumableSub_btnmap_Click rsub = new ResumableSub_btnmap_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnmap_Click extends BA.ResumableSub {
public ResumableSub_btnmap_Click(b4a.example.main parent) {
this.parent = parent;
}
b4a.example.main parent;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = -1;
 //BA.debugLineNum = 82;BA.debugLine="CallSub2(Starter,\"RequestFile\", \"shapefile\")";
anywheresoftware.b4a.keywords.Common.CallSubNew2(processBA,(Object)(parent.mostCurrent._starter.getObject()),"RequestFile",(Object)("shapefile"));
 //BA.debugLineNum = 83;BA.debugLine="Sleep(200)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (200));
this.state = 1;
return;
case 1:
//C
this.state = -1;
;
 //BA.debugLineNum = 84;BA.debugLine="StartActivity(\"maps\")";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)("maps"));
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _btnwifi_click() throws Exception{
 //BA.debugLineNum = 105;BA.debugLine="Sub btnwifi_Click";
 //BA.debugLineNum = 106;BA.debugLine="setIP";
_setip();
 //BA.debugLineNum = 107;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 22;BA.debugLine="Private btnEnter As Button";
mostCurrent._btnenter = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private password As EditText";
mostCurrent._password = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private username As EditText";
mostCurrent._username = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private btnwifi As Button";
mostCurrent._btnwifi = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private Lblconfig As Label";
mostCurrent._lblconfig = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private Lblcamera As Label";
mostCurrent._lblcamera = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private Lbldaninha As Label";
mostCurrent._lbldaninha = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private Lblmap As Label";
mostCurrent._lblmap = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private Lbltractor As Label";
mostCurrent._lbltractor = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private Lbldata As Label";
mostCurrent._lbldata = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private Lblfalha As Label";
mostCurrent._lblfalha = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private Lblwifi As Label";
mostCurrent._lblwifi = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private first_time As Boolean";
_first_time = false;
 //BA.debugLineNum = 35;BA.debugLine="Private btncamera As Button";
mostCurrent._btncamera = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private mode As String = \"stopAnyMode\"		'mantem r";
mostCurrent._mode = "stopAnyMode";
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
public static String  _input_validation(String _answer,String _compactanswer) throws Exception{
 //BA.debugLineNum = 210;BA.debugLine="Sub Input_Validation(Answer As String, CompactAnsw";
 //BA.debugLineNum = 211;BA.debugLine="If CompactAnswer = \"\" Then";
if ((_compactanswer).equals("")) { 
 //BA.debugLineNum = 212;BA.debugLine="Return \"Preencha com um valor válido\"";
if (true) return "Preencha com um valor válido";
 }else {
 //BA.debugLineNum = 214;BA.debugLine="Return \"\"";
if (true) return "";
 };
 //BA.debugLineNum = 216;BA.debugLine="End Sub";
return "";
}
public static void  _lblconfig_click() throws Exception{
ResumableSub_Lblconfig_Click rsub = new ResumableSub_Lblconfig_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_Lblconfig_Click extends BA.ResumableSub {
public ResumableSub_Lblconfig_Click(b4a.example.main parent) {
this.parent = parent;
}
b4a.example.main parent;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = -1;
 //BA.debugLineNum = 100;BA.debugLine="CallSub2(Starter, \"RequestFile\", \"default\")";
anywheresoftware.b4a.keywords.Common.CallSubNew2(processBA,(Object)(parent.mostCurrent._starter.getObject()),"RequestFile",(Object)("default"));
 //BA.debugLineNum = 101;BA.debugLine="Sleep(200)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (200));
this.state = 1;
return;
case 1:
//C
this.state = -1;
;
 //BA.debugLineNum = 102;BA.debugLine="StartActivity(\"connection\")";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)("connection"));
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _lblfalha_click() throws Exception{
 //BA.debugLineNum = 114;BA.debugLine="Sub Lblfalha_Click";
 //BA.debugLineNum = 115;BA.debugLine="If Not(mode = \"startFalhaMode\") Then";
if (anywheresoftware.b4a.keywords.Common.Not((mostCurrent._mode).equals("startFalhaMode"))) { 
 //BA.debugLineNum = 116;BA.debugLine="mode = \"startFalhaMode\"";
mostCurrent._mode = "startFalhaMode";
 //BA.debugLineNum = 117;BA.debugLine="CallSub2(Starter, \"RequestFile\", mode)";
anywheresoftware.b4a.keywords.Common.CallSubNew2(processBA,(Object)(mostCurrent._starter.getObject()),"RequestFile",(Object)(mostCurrent._mode));
 }else {
 //BA.debugLineNum = 119;BA.debugLine="mode = \"stopAnyMode\"";
mostCurrent._mode = "stopAnyMode";
 //BA.debugLineNum = 120;BA.debugLine="CallSub2(Starter, \"RequestFile\", mode)";
anywheresoftware.b4a.keywords.Common.CallSubNew2(processBA,(Object)(mostCurrent._starter.getObject()),"RequestFile",(Object)(mostCurrent._mode));
 };
 //BA.debugLineNum = 122;BA.debugLine="MsgboxAsync(\"Comando \" & mode & \" enviado\",\"\")";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Comando "+mostCurrent._mode+" enviado"),BA.ObjectToCharSequence(""),processBA);
 //BA.debugLineNum = 123;BA.debugLine="End Sub";
return "";
}
public static String  _lbllogout_click() throws Exception{
 //BA.debugLineNum = 136;BA.debugLine="Sub LblLogout_Click";
 //BA.debugLineNum = 137;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 138;BA.debugLine="Activity.LoadLayout(\"login\")";
mostCurrent._activity.LoadLayout("login",mostCurrent.activityBA);
 //BA.debugLineNum = 139;BA.debugLine="End Sub";
return "";
}
public static void  _lblmap_click() throws Exception{
ResumableSub_Lblmap_Click rsub = new ResumableSub_Lblmap_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_Lblmap_Click extends BA.ResumableSub {
public ResumableSub_Lblmap_Click(b4a.example.main parent) {
this.parent = parent;
}
b4a.example.main parent;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = -1;
 //BA.debugLineNum = 88;BA.debugLine="CallSub2(Starter,\"RequestFile\", \"shapefile\")";
anywheresoftware.b4a.keywords.Common.CallSubNew2(processBA,(Object)(parent.mostCurrent._starter.getObject()),"RequestFile",(Object)("shapefile"));
 //BA.debugLineNum = 89;BA.debugLine="Sleep(200)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (200));
this.state = 1;
return;
case 1:
//C
this.state = -1;
;
 //BA.debugLineNum = 90;BA.debugLine="StartActivity(\"maps\")";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)("maps"));
 //BA.debugLineNum = 91;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _lblwifi_click() throws Exception{
 //BA.debugLineNum = 109;BA.debugLine="Sub Lblwifi_Click";
 //BA.debugLineNum = 110;BA.debugLine="setIP";
_setip();
 //BA.debugLineNum = 111;BA.debugLine="End Sub";
return "";
}
public static String  _password_enterpressed() throws Exception{
 //BA.debugLineNum = 61;BA.debugLine="Sub password_EnterPressed";
 //BA.debugLineNum = 62;BA.debugLine="btnEnter_Click";
_btnenter_click();
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
starter._process_globals();
connection._process_globals();
maps._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
public static String  _setip() throws Exception{
flm.b4a.betterdialogs.BetterDialogs _bd = null;
flm.b4a.betterdialogs.BetterDialogs.InputParams _bdinput = null;
int _dr = 0;
 //BA.debugLineNum = 182;BA.debugLine="Sub setIP";
 //BA.debugLineNum = 183;BA.debugLine="File.OpenOutput(File.DirInternal,\"setIP.txt\", Fal";
anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"setIP.txt",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 186;BA.debugLine="Dim BD As BetterDialogs";
_bd = new flm.b4a.betterdialogs.BetterDialogs();
 //BA.debugLineNum = 187;BA.debugLine="Dim bdinput As BD_InputBoxParams";
_bdinput = new flm.b4a.betterdialogs.BetterDialogs.InputParams();
 //BA.debugLineNum = 188;BA.debugLine="bdinput.Initialize";
_bdinput.Initialize();
 //BA.debugLineNum = 189;BA.debugLine="bdinput.Question = \"<I>Defina seu endereço IP </I";
_bdinput.setQuestion("<I>Defina seu endereço IP </I>");
 //BA.debugLineNum = 190;BA.debugLine="bdinput.QuestionTextSize = 18";
_bdinput.setQuestionTextSize((int) (18));
 //BA.debugLineNum = 191;BA.debugLine="bdinput.SpaceBetween = 4dip";
_bdinput.setSpaceBetween(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (4)));
 //BA.debugLineNum = 192;BA.debugLine="bdinput.InputTextSize = 24";
_bdinput.setInputTextSize((int) (24));
 //BA.debugLineNum = 193;BA.debugLine="bdinput.InputType = bdinput.INPUT_TYPE_TEXT";
_bdinput.setInputType(_bdinput.INPUT_TYPE_TEXT);
 //BA.debugLineNum = 194;BA.debugLine="bdinput.Gravity = Gravity.CENTER_VERTICAL + Gravi";
_bdinput.Gravity = (int) (anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL+anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 195;BA.debugLine="bdinput.ValidationCallback = \"Input_Validation\"";
_bdinput.setValidationCallback("Input_Validation");
 //BA.debugLineNum = 196;BA.debugLine="bdinput.WithSuggestions = True";
_bdinput.setWithSuggestions(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 197;BA.debugLine="bdinput.Format = \"###.###.##.##\"";
_bdinput.setFormat("###.###.##.##");
 //BA.debugLineNum = 199;BA.debugLine="Dim DR As Int";
_dr = 0;
 //BA.debugLineNum = 200;BA.debugLine="DR = BD.InputBox(\"Configuração\", bdinput, \"OK\", \"";
_dr = _bd.InputBox("Configuração",_bdinput,"OK","Cancel","",anywheresoftware.b4a.keywords.Common.Null,mostCurrent.activityBA);
 //BA.debugLineNum = 201;BA.debugLine="If DR = DialogResponse.POSITIVE Then";
if (_dr==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 202;BA.debugLine="Log(bdinput.Answer)";
anywheresoftware.b4a.keywords.Common.LogImpl("51114132",_bdinput.getAnswer(),0);
 //BA.debugLineNum = 203;BA.debugLine="Log(bdinput.CompactAnswer)";
anywheresoftware.b4a.keywords.Common.LogImpl("51114133",_bdinput.getCompactAnswer(),0);
 //BA.debugLineNum = 204;BA.debugLine="File.WriteString(File.DirInternal, \"setIP.txt\",";
anywheresoftware.b4a.keywords.Common.File.WriteString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"setIP.txt",_bdinput.getCompactAnswer());
 //BA.debugLineNum = 205;BA.debugLine="Starter.ip = bdinput.CompactAnswer";
mostCurrent._starter._ip /*String*/  = _bdinput.getCompactAnswer();
 };
 //BA.debugLineNum = 207;BA.debugLine="End Sub";
return "";
}
public static String  _showjsonfile() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
anywheresoftware.b4a.objects.collections.JSONParser _json = null;
String _controle = "";
 //BA.debugLineNum = 142;BA.debugLine="Sub showJsonFile";
 //BA.debugLineNum = 143;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 144;BA.debugLine="Dim JSON As JSONParser";
_json = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 145;BA.debugLine="Try";
try { //BA.debugLineNum = 146;BA.debugLine="If File.Exists(File.DirInternal,\"status.json\") T";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"status.json")) { 
 //BA.debugLineNum = 147;BA.debugLine="JSON.Initialize(File.ReadString(File.DirInterna";
_json.Initialize(anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"status.json"));
 }else {
 //BA.debugLineNum = 149;BA.debugLine="Log(\"Arquivo status.json nao encontrado\")";
anywheresoftware.b4a.keywords.Common.LogImpl("51048583","Arquivo status.json nao encontrado",0);
 //BA.debugLineNum = 150;BA.debugLine="If btnwifi.IsInitialized Then";
if (mostCurrent._btnwifi.IsInitialized()) { 
 //BA.debugLineNum = 151;BA.debugLine="btnwifi.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._btnwifi.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"icons8-wi-fi-red.png").getObject()));
 };
 //BA.debugLineNum = 153;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 155;BA.debugLine="Map1 = JSON.NextObject";
_map1 = _json.NextObject();
 //BA.debugLineNum = 157;BA.debugLine="Dim controle As String = Map1.Get(\"trator\")";
_controle = BA.ObjectToString(_map1.Get((Object)("trator")));
 //BA.debugLineNum = 158;BA.debugLine="Lbltractor.Text =\"Trator: \" & controle";
mostCurrent._lbltractor.setText(BA.ObjectToCharSequence("Trator: "+_controle));
 //BA.debugLineNum = 160;BA.debugLine="controle = Map1.Get(\"armazenamento\")";
_controle = BA.ObjectToString(_map1.Get((Object)("armazenamento")));
 //BA.debugLineNum = 161;BA.debugLine="Lbldata.Text =\"Uso do armazenamento: \" & control";
mostCurrent._lbldata.setText(BA.ObjectToCharSequence("Uso do armazenamento: "+_controle+"%"));
 //BA.debugLineNum = 163;BA.debugLine="controle = Map1.Get(\"camera\")";
_controle = BA.ObjectToString(_map1.Get((Object)("camera")));
 //BA.debugLineNum = 164;BA.debugLine="Lblcamera.Text =\"Câmera: \" & controle";
mostCurrent._lblcamera.setText(BA.ObjectToCharSequence("Câmera: "+_controle));
 //BA.debugLineNum = 165;BA.debugLine="If controle = \"ligada\" Then";
if ((_controle).equals("ligada")) { 
 //BA.debugLineNum = 166;BA.debugLine="btncamera.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._btncamera.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"icons8-unsplash-green.png").getObject()));
 }else if((_controle).equals("desligada")) { 
 //BA.debugLineNum = 168;BA.debugLine="btncamera.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._btncamera.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"icons8-unsplash-red.png").getObject()));
 };
 //BA.debugLineNum = 171;BA.debugLine="If (Starter.requestedFile) Then";
if ((mostCurrent._starter._requestedfile /*boolean*/ )) { 
 //BA.debugLineNum = 172;BA.debugLine="btnwifi.SetBackgroundImage(LoadBitmap(File.DirA";
mostCurrent._btnwifi.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"icons8-wi-fi-red.png").getObject()));
 }else {
 //BA.debugLineNum = 174;BA.debugLine="btnwifi.SetBackgroundImage(LoadBitmap(File.DirA";
mostCurrent._btnwifi.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"icons8-wi-fi-green.png").getObject()));
 };
 } 
       catch (Exception e31) {
			processBA.setLastException(e31); //BA.debugLineNum = 177;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("51048611",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 179;BA.debugLine="End Sub";
return "";
}
}
