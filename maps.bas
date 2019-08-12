B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=9.01
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: False

#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
'	Private NativeMe As JavaObject
'	Dim rp As RuntimePermissions
End Sub

Sub Globals
	Private Btnback As Button
'	Private GoogleMap1 As GoogleMap
'	Private MapFragment1 As MapFragment
	Private LV As ListView
End Sub

'lista arquivos encontrados na pasta externa ao app
Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("mapa")
	
	Starter.RuntimePermissions.CheckAndRequest(Starter.RuntimePermissions.PERMISSION_WRITE_EXTERNAL_STORAGE)
	wait for Activity_PermissionResult(Permission As String, Result As Boolean)
	Dim flist As List
	flist.Initialize
	flist = File.ListFiles(Starter.RuntimePermissions.GetSafeDirDefaultExternal(""))
	LV.singleLineLayout.Label.TextColor = Colors.Black
	LV.singleLineLayout.Label.TextSize = 12
	For i = 0 To flist.Size -1
		LV.AddSingleLine(flist.Get(i))
		Log(flist.Get(i))
	Next
	
'	If MapFragment1.IsGooglePlayServicesAvailable = False Then
'		ToastMessageShow("Please install Google Play Services.", True)
'	End If
'
'	If GoogleMap1.IsInitialized Then
'		Do While GoogleMap1.MyLocation.IsInitialized = False
'			Sleep(100)
'		Loop
'		Dim cp As CameraPosition
'		cp.Initialize(GoogleMap1.MyLocation.Latitude, GoogleMap1.MyLocation.Longitude, 5)
'		'GoogleMap1.AnimateCamera(cp)
'		GoogleMap1.MoveCamera(cp)
'	End If
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

'Sub MapFragment1_Ready
'	Log("MapFragment1_Ready")
'	GoogleMap1=MapFragment1.GetMap
'	rp.CheckAndRequest(rp.PERMISSION_ACCESS_FINE_LOCATION)
'	Wait For Activity_PermissionResult (Permission As String, Result As Boolean)
'	GoogleMap1.MyLocationEnabled = Result
'	
'	If GoogleMap1.IsInitialized Then
'		Dim KMLLayer1 As KMLLayer
'		If File.Exists(File.DirInternal,"shapefile.kml") Then
'			Log("Achou shapefile.kml")
'			Try
'				KMLLayer1.Initialize(GoogleMap1, File.OpenInput(File.DirInternal, "shapefile.kml"))
'				KMLLayer1.AddLayerToMap
'			Catch
'				Log(LastException)
'			End Try
'     
'			If KMLLayer1.HasContainers Then
'				Dim Containers As List=KMLLayer1.GetContainers
'				'   TODO
'			End If
'     
'			If KMLLayer1.HasPlacemarks Then
'				Dim Placemarks As List=KMLLayer1.GetPlacemarks
'				'   TODO
'			End If
'		Else
'			Log("Achou doc.kml")
'			Try
'				KMLLayer1.Initialize(GoogleMap1, File.OpenInput(File.DirAssets, "doc.kml"))
'				KMLLayer1.AddLayerToMap
'			Catch
'				Log(LastException)
'			End Try
'     
'			If KMLLayer1.HasContainers Then
'				Dim Containers As List=KMLLayer1.GetContainers
'				'   TODO
'			End If
'     
'			If KMLLayer1.HasPlacemarks Then
'				Dim Placemarks As List=KMLLayer1.GetPlacemarks
'				'   TODO
'			End If
'		End If
'	Else
'		Log("Error initializing GoogleMap")
'		ToastMessageShow("Error initializing GoogleMap", False)
'	End If
'End Sub
'
'Sub Activity_PermissionResult (Permission As String, Result As Boolean)
'	If Permission = rp.PERMISSION_ACCESS_FINE_LOCATION Then
'		GoogleMap1.MyLocationEnabled = Result
'	End If
'End Sub

Sub Btnback_Click
	Activity.Finish
End Sub