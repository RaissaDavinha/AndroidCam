package b4a.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class starter extends  android.app.Service{
	public static class starter_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
            BA.LogInfo("** Receiver (starter) OnReceive **");
			android.content.Intent in = new android.content.Intent(context, starter.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
            ServiceHelper.StarterHelper.startServiceFromReceiver (context, in, true, BA.class);
		}

	}
    static starter mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return starter.class;
	}
	@Override
	public void onCreate() {
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "b4a.example", "b4a.example.starter");
            if (BA.isShellModeRuntimeCheck(processBA)) {
                processBA.raiseEvent2(null, true, "SHELL", false);
		    }
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        
        if (BA.isShellModeRuntimeCheck(processBA)) {
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.starter", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!true && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, false) == false) {
				
		}
		else {
            processBA.setActivityPaused(false);
            BA.LogInfo("*** Service (starter) Create ***");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (true) {
			ServiceHelper.StarterHelper.runWaitForLayouts();
		}
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		onStartCommand(intent, 0, 0);
    }
    @Override
    public int onStartCommand(final android.content.Intent intent, int flags, int startId) {
    	if (ServiceHelper.StarterHelper.onStartCommand(processBA, new Runnable() {
            public void run() {
                handleStart(intent);
            }}))
			;
		else {
			ServiceHelper.StarterHelper.addWaitForLayout (new Runnable() {
				public void run() {
                    processBA.setActivityPaused(false);
                    BA.LogInfo("** Service (starter) Create **");
                    processBA.raiseEvent(null, "service_create");
					handleStart(intent);
                    ServiceHelper.StarterHelper.removeWaitForLayout();
				}
			});
		}
        processBA.runHook("onstartcommand", this, new Object[] {intent, flags, startId});
		return android.app.Service.START_NOT_STICKY;
    }
    public void onTaskRemoved(android.content.Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        if (true)
            processBA.raiseEvent(null, "service_taskremoved");
            
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (starter) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = ServiceHelper.StarterHelper.handleStartIntent(intent, _service, processBA);
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	
	@Override
	public void onDestroy() {
        super.onDestroy();
        if (true) {
            BA.LogInfo("** Service (starter) Destroy (ignored)**");
        }
        else {
            BA.LogInfo("** Service (starter) Destroy **");
		    processBA.raiseEvent(null, "service_destroy");
            processBA.service = null;
		    mostCurrent = null;
		    processBA.setActivityPaused(true);
            processBA.runHook("ondestroy", this, null);
        }
	}

@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.SocketWrapper.UDPSocket _udpsender = null;
public static anywheresoftware.b4a.objects.SocketWrapper.UDPSocket _udpreciver = null;
public static String _typefile = "";
public static b4a.example.fileprovider _provider = null;
public static String _ip = "";
public static anywheresoftware.b4a.objects.RuntimePermissions _runtimepermissions = null;
public static boolean _requestedfile = false;
public b4a.example.main _main = null;
public b4a.example.connection _connection = null;
public b4a.example.maps _maps = null;
public static boolean  _application_error(anywheresoftware.b4a.objects.B4AException _error,String _stacktrace) throws Exception{
 //BA.debugLineNum = 35;BA.debugLine="Sub Application_Error (Error As Exception, StackTr";
 //BA.debugLineNum = 36;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return false;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Dim UDPSender As UDPSocket";
_udpsender = new anywheresoftware.b4a.objects.SocketWrapper.UDPSocket();
 //BA.debugLineNum = 8;BA.debugLine="Dim UDPReciver As UDPSocket";
_udpreciver = new anywheresoftware.b4a.objects.SocketWrapper.UDPSocket();
 //BA.debugLineNum = 9;BA.debugLine="Dim typefile As String";
_typefile = "";
 //BA.debugLineNum = 10;BA.debugLine="Public Provider As FileProvider";
_provider = new b4a.example.fileprovider();
 //BA.debugLineNum = 11;BA.debugLine="Dim ip As String = \"192.168.2.11\"";
_ip = "192.168.2.11";
 //BA.debugLineNum = 12;BA.debugLine="Dim RuntimePermissions As RuntimePermissions";
_runtimepermissions = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 13;BA.debugLine="Dim requestedFile As Boolean = False		'flag defin";
_requestedfile = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return "";
}
public static String  _requestfile(String _str) throws Exception{
anywheresoftware.b4a.objects.SocketWrapper.UDPSocket.UDPPacket _packet = null;
byte[] _data = null;
 //BA.debugLineNum = 52;BA.debugLine="Sub RequestFile(str As String)";
 //BA.debugLineNum = 53;BA.debugLine="typefile=str";
_typefile = _str;
 //BA.debugLineNum = 54;BA.debugLine="Log(\"request chamado com o parametro\")";
anywheresoftware.b4a.keywords.Common.LogImpl("51703938","request chamado com o parametro",0);
 //BA.debugLineNum = 55;BA.debugLine="Log(str)";
anywheresoftware.b4a.keywords.Common.LogImpl("51703939",_str,0);
 //BA.debugLineNum = 56;BA.debugLine="If UDPSender.IsInitialized Then";
if (_udpsender.IsInitialized()) { 
 //BA.debugLineNum = 57;BA.debugLine="Dim Packet As UDPPacket";
_packet = new anywheresoftware.b4a.objects.SocketWrapper.UDPSocket.UDPPacket();
 //BA.debugLineNum = 58;BA.debugLine="Dim data() As Byte";
_data = new byte[(int) (0)];
;
 //BA.debugLineNum = 59;BA.debugLine="data = str.GetBytes(\"UTF8\")";
_data = _str.getBytes("UTF8");
 //BA.debugLineNum = 60;BA.debugLine="If ip <> \"\" Then";
if ((_ip).equals("") == false) { 
 //BA.debugLineNum = 61;BA.debugLine="Packet.Initialize(data, ip, 5075)";
_packet.Initialize(_data,_ip,(int) (5075));
 //BA.debugLineNum = 62;BA.debugLine="UDPSender.Send(Packet)";
_udpsender.Send(_packet);
 //BA.debugLineNum = 63;BA.debugLine="requestedFile = True";
_requestedfile = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 65;BA.debugLine="Log(\"Nenhum ip encontrado\")";
anywheresoftware.b4a.keywords.Common.LogImpl("51703949","Nenhum ip encontrado",0);
 };
 };
 //BA.debugLineNum = 68;BA.debugLine="End Sub";
return "";
}
public static void  _service_create() throws Exception{
ResumableSub_Service_Create rsub = new ResumableSub_Service_Create(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_Service_Create extends BA.ResumableSub {
public ResumableSub_Service_Create(b4a.example.starter parent) {
this.parent = parent;
}
b4a.example.starter parent;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = -1;
 //BA.debugLineNum = 17;BA.debugLine="Provider.Initialize";
parent._provider._initialize /*String*/ (processBA);
 //BA.debugLineNum = 18;BA.debugLine="UDPSender.Initialize(\"UDPSend\", 0, 0)";
parent._udpsender.Initialize(processBA,"UDPSend",(int) (0),(int) (0));
 //BA.debugLineNum = 19;BA.debugLine="UDPReciver.Initialize(\"UDP\",6075,8000)";
parent._udpreciver.Initialize(processBA,"UDP",(int) (6075),(int) (8000));
 //BA.debugLineNum = 20;BA.debugLine="Sleep(200)";
anywheresoftware.b4a.keywords.Common.Sleep(processBA,this,(int) (200));
this.state = 1;
return;
case 1:
//C
this.state = -1;
;
 //BA.debugLineNum = 21;BA.debugLine="RequestFile(\"shapefile\")";
_requestfile("shapefile");
 //BA.debugLineNum = 22;BA.debugLine="Sleep(500)";
anywheresoftware.b4a.keywords.Common.Sleep(processBA,this,(int) (500));
this.state = 2;
return;
case 2:
//C
this.state = -1;
;
 //BA.debugLineNum = 23;BA.debugLine="updateStatus";
_updatestatus();
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 39;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 40;BA.debugLine="UDPSender.Close";
_udpsender.Close();
 //BA.debugLineNum = 41;BA.debugLine="UDPReciver.Close";
_udpreciver.Close();
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 26;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 27;BA.debugLine="Service.StopAutomaticForeground 'Starter service";
mostCurrent._service.StopAutomaticForeground();
 //BA.debugLineNum = 28;BA.debugLine="End Sub";
return "";
}
public static String  _service_taskremoved() throws Exception{
 //BA.debugLineNum = 30;BA.debugLine="Sub Service_TaskRemoved";
 //BA.debugLineNum = 32;BA.debugLine="End Sub";
return "";
}
public static String  _socket_connected(boolean _successful) throws Exception{
 //BA.debugLineNum = 44;BA.debugLine="Sub Socket_Connected (Successful As Boolean)";
 //BA.debugLineNum = 45;BA.debugLine="If Successful = False Then";
if (_successful==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 46;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("51638402",anywheresoftware.b4a.keywords.Common.LastException(processBA).getMessage(),0);
 //BA.debugLineNum = 47;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return "";
}
public static String  _udp_packetarrived(anywheresoftware.b4a.objects.SocketWrapper.UDPSocket.UDPPacket _packet) throws Exception{
String _msg = "";
String _name = "";
 //BA.debugLineNum = 71;BA.debugLine="Sub  UDP_PacketArrived (Packet As UDPPacket)";
 //BA.debugLineNum = 72;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 73;BA.debugLine="msg = BytesToString(Packet.Data, Packet.Offset, P";
_msg = anywheresoftware.b4a.keywords.Common.BytesToString(_packet.getData(),_packet.getOffset(),_packet.getLength(),"UTF8");
 //BA.debugLineNum = 74;BA.debugLine="requestedFile = False";
_requestedfile = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 76;BA.debugLine="If typefile = \"shapefile\" Then";
if ((_typefile).equals("shapefile")) { 
 //BA.debugLineNum = 77;BA.debugLine="Log(\"recebi um shapefile\")";
anywheresoftware.b4a.keywords.Common.LogImpl("51769478","recebi um shapefile",0);
 //BA.debugLineNum = 78;BA.debugLine="Dim name As String = \"Shapefile_\" & DateTime.Now";
_name = "Shapefile_"+BA.NumberToString(anywheresoftware.b4a.keywords.Common.DateTime.getNow())+".kml";
 //BA.debugLineNum = 79;BA.debugLine="File.OpenOutput(RuntimePermissions.GetSafeDirDef";
anywheresoftware.b4a.keywords.Common.File.OpenOutput(_runtimepermissions.GetSafeDirDefaultExternal(""),_name,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 80;BA.debugLine="File.WriteString(RuntimePermissions.GetSafeDirDe";
anywheresoftware.b4a.keywords.Common.File.WriteString(_runtimepermissions.GetSafeDirDefaultExternal(""),_name,_msg);
 }else if((_typefile).equals("default")) { 
 //BA.debugLineNum = 83;BA.debugLine="Log(\"recebi um default\")";
anywheresoftware.b4a.keywords.Common.LogImpl("51769484","recebi um default",0);
 //BA.debugLineNum = 84;BA.debugLine="File.OpenOutput(File.DirInternal,\"default.json\",";
anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"default.json",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 85;BA.debugLine="File.WriteString(File.DirInternal,\"default.json\"";
anywheresoftware.b4a.keywords.Common.File.WriteString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"default.json",_msg);
 }else if((_typefile).equals("status")) { 
 //BA.debugLineNum = 88;BA.debugLine="Log(\"recebi um status\")";
anywheresoftware.b4a.keywords.Common.LogImpl("51769489","recebi um status",0);
 //BA.debugLineNum = 89;BA.debugLine="File.OpenOutput(File.DirInternal, \"status.json\",";
anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"status.json",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 90;BA.debugLine="File.WriteString(File.DirInternal, \"status.json\"";
anywheresoftware.b4a.keywords.Common.File.WriteString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"status.json",_msg);
 }else {
 //BA.debugLineNum = 92;BA.debugLine="Log(\"arquivo nao valido\")";
anywheresoftware.b4a.keywords.Common.LogImpl("51769493","arquivo nao valido",0);
 //BA.debugLineNum = 93;BA.debugLine="requestedFile = True";
_requestedfile = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 95;BA.debugLine="End Sub";
return "";
}
public static void  _updatestatus() throws Exception{
ResumableSub_updateStatus rsub = new ResumableSub_updateStatus(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_updateStatus extends BA.ResumableSub {
public ResumableSub_updateStatus(b4a.example.starter parent) {
this.parent = parent;
}
b4a.example.starter parent;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 99;BA.debugLine="Do While True";
if (true) break;

case 1:
//do while
this.state = 4;
while (anywheresoftware.b4a.keywords.Common.True) {
this.state = 3;
if (true) break;
}
if (true) break;

case 3:
//C
this.state = 1;
 //BA.debugLineNum = 100;BA.debugLine="RequestFile(\"status\")";
_requestfile("status");
 //BA.debugLineNum = 101;BA.debugLine="Sleep(500)";
anywheresoftware.b4a.keywords.Common.Sleep(processBA,this,(int) (500));
this.state = 5;
return;
case 5:
//C
this.state = 1;
;
 //BA.debugLineNum = 102;BA.debugLine="CallSub(Main,\"showJsonFile\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(parent.mostCurrent._main.getObject()),"showJsonFile");
 //BA.debugLineNum = 103;BA.debugLine="Sleep(4500)";
anywheresoftware.b4a.keywords.Common.Sleep(processBA,this,(int) (4500));
this.state = 6;
return;
case 6:
//C
this.state = 1;
;
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 105;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
}
