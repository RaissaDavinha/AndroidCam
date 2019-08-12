Group=Default Group
ModulesStructureVersion=1
Type=Class
Version=2.71
@EndOfDesignText@
'Class module
Sub Class_Globals
	Type TextAlignType (Right As String, Left As String, Center As String)
	Type ControlType (IsLabel As String, IsTextBox As String, IsSpinner As String, IsSave As String, IsDelete As String, IsClone As String)
	Type InputTypes(Decimal As String, None As String, Numbers As String, Text As String)
	Public TextAlign As TextAlignType
	Public EditType As ControlType
	Public InputType As InputTypes
	Public Columns As Map
	Dim dummyView As View ' bug fix
	Public FontSize As Int
	Public Rows As Map
	Public RowHeight As Int
	Public cv As CustomListView 
	Public pnl As Panel
	Public cList As Map
End Sub

'Initializes the object. You can add parameters to this method if needed.
Public Sub Initialize()
	cList.Initialize 
	Columns.Initialize 
	Rows.Initialize
	TextAlign.Initialize 
	TextAlign.Center = "Center"
	TextAlign.Left = "Left"
	TextAlign.Right = "Right"
	EditType.Initialize
	EditType.IsLabel = "Label"
	EditType.IsTextBox = "EditText"
	InputType.Initialize 
	InputType.Decimal = "Decimal"
	InputType.None = "None"
	InputType.Numbers = "Numbers"
	InputType.Text = "Text"	
	FontSize = 12
End Sub

' add a column to the grid
Public Sub AddColumn(ColumnName As String, Caption As String, Width As String, Format As String, Alignment As String, cEditType As String, cInputType As String)
	Dim m As Map
	m.Initialize
	m.Put("ColumnName", ColumnName)
	m.Put("Caption", Caption)
	m.Put("Width", Width)
	m.Put("Format", Format)
	m.Put("Alignment", Alignment)
	m.Put("EditType", cEditType)
	m.Put("InputType", cInputType)
	Columns.Put(ColumnName, m)
End Sub

Public Sub AddColumnList(ColumnName As String, ColumnList As List)
	cList.Put(ColumnName, ColumnList)
End Sub

' add a row to the grid
Public Sub AddRow(rowID As String, rowItems() As String)
	Dim m As Map
	m.Initialize
	For i = 0 To Columns.Size - 1
		Dim sColumnName As String
		sColumnName = Columns.GetKeyAt(i)
		m.Put(sColumnName, rowItems(i))
	Next
	Rows.Put(rowID, m)
End Sub

' add a row to the grid
Public Sub AddRowMap(rowID As String, rowMap As Map)
	Rows.put(rowID, rowMap)
End Sub

' remove a row using a rowid
Public Sub RemoveRow(rowID As String)
	If Rows.ContainsKey(rowID) Then Rows.Remove(rowID)  
End Sub

' draw the grid
Sub DrawGrid()
	Dim intL As Int: intL = 10
	Dim sColumName As String: sColumName = ""
	Dim sWidth As String: sWidth = ""
	Dim sCaption As String: sCaption = ""
	Dim sFormat As String: sFormat = ""
	Dim sAlignment As String: sAlignment = ""
	Dim cEditType As String: cEditType = ""
	Dim cInputType As String: cInputType = ""
	Dim intW As Int: intW = 0
	' add headers
	pnl.Color = Colors.Black				'aqui muda a cor das linhas que separam as celulas da tabela
	For i = 0 To Columns.Size - 1
		Dim cm As Map
		cm.Initialize 
		cm = Columns.GetValueAt(i) 
		sColumName = cm.Get("ColumnName")
		sCaption = cm.Get("Caption")
		sWidth = cm.Get("Width")
		sAlignment = cm.Get("Alignment")
		cEditType = cm.Get("EditType")
		intW = sWidth
		' build the headings
		Dim lbl As Label
		lbl.Initialize("h" & sColumName)
		Select Case sAlignment
			Case TextAlign.Center: lbl.Gravity = Bit.Or(Gravity.CENTER_VERTICAL, Gravity.CENTER)
			Case TextAlign.Left: lbl.Gravity = Bit.Or(Gravity.CENTER_VERTICAL, Gravity.LEFT)
			Case TextAlign.Right: lbl.Gravity = Bit.Or(Gravity.CENTER_VERTICAL, Gravity.RIGHT)
		End Select
		lbl.Text = sCaption
		lbl.TextSize = FontSize
		lbl.TextColor = Colors.Red
		pnl.AddView(lbl, intL, 0dip, intW, 60)
		intL = intL + intW + 10
	Next
	' resize the controls, potrait mode cuts controls
	pnl.Width = intL
	cv.AsView.Width = intL
	
	' addrows
	cv.clear
	For r = 0 To Rows.Size - 1
		' get row data
		Dim rowID As String
		Dim rd As Map
		rd.Initialize 
		rowID = Rows.GetKeyAt(r)
		rd = Rows.GetValueAt(r) 
		' the panel to hold everything
		Dim rp As Panel
		rp.Initialize("")
		rp.Color = Colors.White			'esse aqui muda o fundo do grid
		' define each column
		intL = 10
		For i = 0 To Columns.Size - 1
			Dim cm As Map
			cm.Initialize 
			cm = Columns.GetValueAt(i) 
			sColumName = cm.Get("ColumnName")
			sWidth = cm.Get("Width")
			sFormat = cm.Get("Format")
			sAlignment = cm.Get("Alignment")
			cEditType = cm.Get("EditType")
			cInputType = cm.Get("InputType")
			intW = sWidth
			Select Case cEditType
			Case EditType.IsLabel
				Dim lbl As Label
				lbl.Initialize(sColumName)
				Select Case sAlignment
				Case TextAlign.Center: lbl.Gravity = Bit.Or(Gravity.CENTER_VERTICAL, Gravity.CENTER)
				Case TextAlign.Left: lbl.Gravity = Bit.Or(Gravity.CENTER_VERTICAL, Gravity.LEFT)
				Case TextAlign.Right: lbl.Gravity = Bit.Or(Gravity.CENTER_VERTICAL, Gravity.RIGHT)
				End Select
				lbl.Text = rd.Get(sColumName)
				lbl.TextSize = FontSize
				lbl.TextColor = Colors.Black
				rp.AddView(lbl, intL, 80, intW, 70)
			Case EditType.IsTextBox
				Dim txt As EditText
				txt.Initialize(sColumName)
				Select Case sAlignment
				Case TextAlign.Center: txt.Gravity = Bit.Or(Gravity.CENTER_VERTICAL, Gravity.CENTER)
				Case TextAlign.Left: txt.Gravity = Bit.Or(Gravity.CENTER_VERTICAL, Gravity.LEFT)
				Case TextAlign.Right: txt.Gravity = Bit.Or(Gravity.CENTER_VERTICAL, Gravity.RIGHT)
				End Select
				txt.Text = rd.Get(sColumName)
				txt.TextSize = FontSize
				txt.TextColor = Colors.Black
				txt.SingleLine = True
				Select Case cInputType
				Case InputType.Decimal: txt.InputType = txt.INPUT_TYPE_DECIMAL_NUMBERS 
				Case InputType.None: txt.InputType = txt.INPUT_TYPE_NONE 
				Case InputType.Numbers: txt.InputType = txt.INPUT_TYPE_NUMBERS
				Case InputType.Text: txt.InputType = txt.INPUT_TYPE_TEXT 
				End Select
				rp.AddView(txt, intL, 80, intW, 110)
			End Select
			intL = intL + intW + 10
		Next
		cv.Add(rp, RowHeight, rowID)
	Next
End Sub

' get the row as a map of the selected position
Sub GetRow(Index As Int) As Map
	Dim mOut As Map
	mOut.Initialize 
	Dim cEditType As String
	Dim cColumnName As String
	Dim pnl As Panel
	pnl = cv.GetPanel(Index)
	For i = 0 To Columns.Size - 1
		Dim cm As Map
		cm.Initialize 
		cm = Columns.GetValueAt(i)
		cColumnName = cm.Get("ColumnName")
		cEditType = cm.Get("EditType")
		Select Case cEditType
		Case EditType.IsLabel
			Dim lbl As Label
			lbl = pnl.GetView(i)
			'cColumnName = lbl.Text
			mOut.Put(cColumnName, lbl.Text)
		Case EditType.IsTextBox
			Dim txt As EditText
			txt = pnl.GetView(i)
			'cColumnValue = txt.Text
			mOut.Put(cColumnName, txt.Text)
		End Select
	Next
	Return mOut
End Sub
