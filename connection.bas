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

End Sub

Sub Globals
	Private pnl As Panel
	Private clsEG As clsEditableGrid
	Private lstTable As CustomListView
	Private general As Map
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("config")
	'pnl.RemoveView
	'hsv.Panel.AddView(pnl, 1, 0 ,100%x, 100%y)
	clsEG.Initialize
	clsEG.cv = lstTable
	clsEG.pnl = pnl
	clsEG.FontSize = 14
	clsEG.RowHeight = 50dip			'altura de cada celula
	clsEG.AddColumn("variavel", "Nome da variavel", "800" , "{0}", clsEG.TextAlign.Left, clsEG.EditType.IsLabel, clsEG.InputType.None)
	clsEG.AddColumn("valo", "valor", "200", "{0:#,##0.00}", clsEG.TextAlign.Center, clsEG.EditType.IsTextBox, clsEG.InputType.Decimal)
	
	' add rows
	showJsonFile
	clsEG.DrawGrid
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

'salva os dados em um aquivo no diretorio do app e manda o mesmo pela rede
Sub BtnSave_Click
	saveAndSend
	MsgboxAsync("Configuracao salva","Sucesso")
End Sub

'sai da janela sem modificar ou enviar o arquivo
Sub Btnback_Click
	File.Delete(File.DirInternal, "default.json")
	Activity.Finish
End Sub

'abre arquivo recebido caso ele exista, senao, janela de configuração não abre
Sub showJsonFile
	Dim Map1 As Map
	Dim JSON As JSONParser
	If File.Exists(File.DirInternal,"default.json") Then
		Log("default.json existe")
		JSON.Initialize(File.ReadString(File.DirInternal,"default.json"))
		Map1 = JSON.NextObject
		general = Map1.Get("general")
		For i = 0 To general.Size - 1
			clsEG.AddRow(i, Array As String(general.GetKeyAt(i), general.GetValueAt(i)))
		Next
	Else
		Starter.requestedFile = True
		Activity.Finish
	End If
End Sub

Sub saveAndSend
	Dim mOut As Map
	Dim general1 As Map
	Dim jsonGEN As JSONGenerator
	Try
		jsonGEN.Initialize(general)
		general1.Initialize
	
		For i = 0 To general.Size-1
			mOut = clsEG.GetRow(i)
			general1.Put(mOut.GetValueAt(0), mOut.GetValueAt(1))
		Next
		mOut.Clear
		mOut.Put("controle","0")
		mOut.Put("general", general1)
		jsonGEN.Initialize(mOut)
		Log(jsonGEN.ToString)
		File.OpenOutput(File.DirInternal, "send.json", False)
		File.WriteString(File.DirInternal, "send.json",jsonGEN.ToString)

		'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
		'bloco para verificar dados dentro do arquivo criado
'		Dim Map1 As Map
'		Dim JSON As JSONParser
'		Dim jsonGENs As JSONGenerator
'		Map1.Initialize
'		Try
'			JSON.Initialize(File.ReadString(File.DirInternal, "send.json"))
'			Map1 = JSON.NextObject
'			jsonGENs.Initialize(Map1)
'			Log(jsonGENs.ToPrettyString(2))
'		Catch
'			Log(LastException)
'		End Try
		'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	
		'envia arquivo pela rede
		Dim buffer() As Byte
		File.OpenInput(File.DirInternal, "send.json")
		buffer = File.ReadBytes(File.DirInternal, "send.json")
	
		If Starter.UDPSender.IsInitialized Then
			Dim Packet As UDPPacket
			If Starter.ip <> "" Then
				Packet.Initialize(buffer, Starter.ip, 5075)
				Starter.UDPSender.Send(Packet)
			Else
				MsgboxAsync("Nenhum ip encontrado","Erro")
				Starter.requestedFile = True
			End If
		Else
			Starter.requestedFile = True
			Return
		End If
		File.Delete(File.DirInternal, "send.json")
	Catch
		Log(LastException)
	End Try
End Sub