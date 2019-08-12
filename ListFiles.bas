B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=9.01
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Used just to create test files
	'Call like this
	Dim ml As List = WildCardFilesList(File.DirInternal, ".png, .mov, .db", True, True)
	'Used just to log the results
	For l = 0 To ml.Size -1
		Log(ml.Get(l))
	Next
End Sub
 
'This is the only sub needed, the rest is just for a sample
Sub WildCardFilesList(FilesPath As String, WildCards As String, Sorted As Boolean, Ascending As Boolean) As List
	If File.IsDirectory("", FilesPath) Then
		Dim FilesFound As List = File.ListFiles(FilesPath)
		Dim GetCards() As String = Regex.Split(",", WildCards)
		Dim FilteredFiles As List : FilteredFiles.Initialize
		For i = 0 To FilesFound.Size -1
			For l = 0 To GetCards.Length -1
				Dim TestItem As String = FilesFound.Get(i)
				If TestItem.EndsWith(GetCards(l).Trim) Then
					FilteredFiles.Add(TestItem.Trim)
				End If
			Next
		Next
		If Sorted Then
			FilteredFiles.SortCaseInsensitive(Ascending)
		End If
		Return FilteredFiles
	Else
		Msgbox("You must pass a valid Directory.", "NOTICE")
	End If
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub
