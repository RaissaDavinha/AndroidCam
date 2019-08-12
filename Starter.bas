B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Service
Version=9.01
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: False
	#ExcludeFromLibrary: True
#End Region

Sub Process_Globals
	Dim UDPSender As UDPSocket
	Dim UDPReciver As UDPSocket
	Dim typefile As String
	Public Provider As FileProvider
	Dim ip As String = "192.168.2.11"
	Dim RuntimePermissions As RuntimePermissions
	Dim requestedFile As Boolean = False		'flag definida para saber se um arquivo foi requisitado e retornado - false: nao foi requisitado ou ja foi respondido,  true: arquivo requeisitado e ainda nao respondido
End Sub

Sub Service_Create
	Provider.Initialize
	UDPSender.Initialize("UDPSend", 0, 0)
	UDPReciver.Initialize("UDP",6075,8000)
	Sleep(200)
	RequestFile("shapefile")
	Sleep(500)
	updateStatus
End Sub

Sub Service_Start (StartingIntent As Intent)
	Service.StopAutomaticForeground 'Starter service can start in the foreground state in some edge cases.
End Sub

Sub Service_TaskRemoved
	'This event will be raised when the user removes the app from the recent apps list.
End Sub

'Return true to allow the OS default exceptions handler to handle the uncaught exception.
Sub Application_Error (Error As Exception, StackTrace As String) As Boolean
	Return True
End Sub

Sub Service_Destroy
	UDPSender.Close
	UDPReciver.Close
End Sub

Sub Socket_Connected (Successful As Boolean)
	If Successful = False Then
		Log(LastException.Message)
		Return
	End If
End Sub

'pede, expecifica qual arquivo deve ser enviado ou envia um comando
Sub RequestFile(str As String)
	typefile=str
	Log("request chamado com o parametro")
	Log(str)
	If UDPSender.IsInitialized Then
		Dim Packet As UDPPacket
		Dim data() As Byte
		data = str.GetBytes("UTF8")
		If ip <> "" Then
			Packet.Initialize(data, ip, 5075)
			UDPSender.Send(Packet)
			requestedFile = True
		Else
			Log("Nenhum ip encontrado")
		End If
	End If
End Sub

'recebe json ou shapefile e salva em arquivo específico conforme o que foi requisitado
Sub  UDP_PacketArrived (Packet As UDPPacket)
	Dim msg As String
	msg = BytesToString(Packet.Data, Packet.Offset, Packet.Length, "UTF8")
	requestedFile = False
	
	If typefile = "shapefile" Then
		Log("recebi um shapefile")
		Dim name As String = "Shapefile_" & DateTime.Now & ".kml"
		File.OpenOutput(RuntimePermissions.GetSafeDirDefaultExternal(""), name, False)
		File.WriteString(RuntimePermissions.GetSafeDirDefaultExternal(""),name, msg)
		
	Else If typefile = "default" Then
		Log("recebi um default")
		File.OpenOutput(File.DirInternal,"default.json", False)
		File.WriteString(File.DirInternal,"default.json", msg)
			
	Else If typefile = "status" Then
		Log("recebi um status")
		File.OpenOutput(File.DirInternal, "status.json", False)
		File.WriteString(File.DirInternal, "status.json", msg)
	Else
		Log("arquivo nao valido")
		requestedFile = True
	End If
End Sub

'Atualiza as informacoes do menu principal a cada 5 segundos
Sub updateStatus
	Do While True
		RequestFile("status")
		Sleep(500)
		CallSub(Main,"showJsonFile")
		Sleep(4500)
	Loop
End Sub