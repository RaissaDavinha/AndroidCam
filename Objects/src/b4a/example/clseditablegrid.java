package b4a.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class clseditablegrid extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "b4a.example.clseditablegrid");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", b4a.example.clseditablegrid.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public b4a.example.clseditablegrid._textaligntype _textalign = null;
public b4a.example.clseditablegrid._controltype _edittype = null;
public b4a.example.clseditablegrid._inputtypes _inputtype = null;
public anywheresoftware.b4a.objects.collections.Map _columns = null;
public anywheresoftware.b4a.objects.ConcreteViewWrapper _dummyview = null;
public int _fontsize = 0;
public anywheresoftware.b4a.objects.collections.Map _rows = null;
public int _rowheight = 0;
public b4a.example.customlistview _cv = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
public anywheresoftware.b4a.objects.collections.Map _clist = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.connection _connection = null;
public b4a.example.maps _maps = null;
public static class _textaligntype{
public boolean IsInitialized;
public String Right;
public String Left;
public String Center;
public void Initialize() {
IsInitialized = true;
Right = "";
Left = "";
Center = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _controltype{
public boolean IsInitialized;
public String IsLabel;
public String IsTextBox;
public String IsSpinner;
public String IsSave;
public String IsDelete;
public String IsClone;
public void Initialize() {
IsInitialized = true;
IsLabel = "";
IsTextBox = "";
IsSpinner = "";
IsSave = "";
IsDelete = "";
IsClone = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _inputtypes{
public boolean IsInitialized;
public String Decimal;
public String None;
public String Numbers;
public String Text;
public void Initialize() {
IsInitialized = true;
Decimal = "";
None = "";
Numbers = "";
Text = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public String  _addcolumn(String _columnname,String _caption,String _width,String _format,String _alignment,String _cedittype,String _cinputtype) throws Exception{
anywheresoftware.b4a.objects.collections.Map _m = null;
 //BA.debugLineNum = 40;BA.debugLine="Public Sub AddColumn(ColumnName As String, Caption";
 //BA.debugLineNum = 41;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 42;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 43;BA.debugLine="m.Put(\"ColumnName\", ColumnName)";
_m.Put((Object)("ColumnName"),(Object)(_columnname));
 //BA.debugLineNum = 44;BA.debugLine="m.Put(\"Caption\", Caption)";
_m.Put((Object)("Caption"),(Object)(_caption));
 //BA.debugLineNum = 45;BA.debugLine="m.Put(\"Width\", Width)";
_m.Put((Object)("Width"),(Object)(_width));
 //BA.debugLineNum = 46;BA.debugLine="m.Put(\"Format\", Format)";
_m.Put((Object)("Format"),(Object)(_format));
 //BA.debugLineNum = 47;BA.debugLine="m.Put(\"Alignment\", Alignment)";
_m.Put((Object)("Alignment"),(Object)(_alignment));
 //BA.debugLineNum = 48;BA.debugLine="m.Put(\"EditType\", cEditType)";
_m.Put((Object)("EditType"),(Object)(_cedittype));
 //BA.debugLineNum = 49;BA.debugLine="m.Put(\"InputType\", cInputType)";
_m.Put((Object)("InputType"),(Object)(_cinputtype));
 //BA.debugLineNum = 50;BA.debugLine="Columns.Put(ColumnName, m)";
_columns.Put((Object)(_columnname),(Object)(_m.getObject()));
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public String  _addcolumnlist(String _columnname,anywheresoftware.b4a.objects.collections.List _columnlist) throws Exception{
 //BA.debugLineNum = 53;BA.debugLine="Public Sub AddColumnList(ColumnName As String, Col";
 //BA.debugLineNum = 54;BA.debugLine="cList.Put(ColumnName, ColumnList)";
_clist.Put((Object)(_columnname),(Object)(_columnlist.getObject()));
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return "";
}
public String  _addrow(String _rowid,String[] _rowitems) throws Exception{
anywheresoftware.b4a.objects.collections.Map _m = null;
int _i = 0;
String _scolumnname = "";
 //BA.debugLineNum = 58;BA.debugLine="Public Sub AddRow(rowID As String, rowItems() As S";
 //BA.debugLineNum = 59;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 60;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 61;BA.debugLine="For i = 0 To Columns.Size - 1";
{
final int step3 = 1;
final int limit3 = (int) (_columns.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit3 ;_i = _i + step3 ) {
 //BA.debugLineNum = 62;BA.debugLine="Dim sColumnName As String";
_scolumnname = "";
 //BA.debugLineNum = 63;BA.debugLine="sColumnName = Columns.GetKeyAt(i)";
_scolumnname = BA.ObjectToString(_columns.GetKeyAt(_i));
 //BA.debugLineNum = 64;BA.debugLine="m.Put(sColumnName, rowItems(i))";
_m.Put((Object)(_scolumnname),(Object)(_rowitems[_i]));
 }
};
 //BA.debugLineNum = 66;BA.debugLine="Rows.Put(rowID, m)";
_rows.Put((Object)(_rowid),(Object)(_m.getObject()));
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
public String  _addrowmap(String _rowid,anywheresoftware.b4a.objects.collections.Map _rowmap) throws Exception{
 //BA.debugLineNum = 70;BA.debugLine="Public Sub AddRowMap(rowID As String, rowMap As Ma";
 //BA.debugLineNum = 71;BA.debugLine="Rows.put(rowID, rowMap)";
_rows.Put((Object)(_rowid),(Object)(_rowmap.getObject()));
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 3;BA.debugLine="Type TextAlignType (Right As String, Left As Stri";
;
 //BA.debugLineNum = 4;BA.debugLine="Type ControlType (IsLabel As String, IsTextBox As";
;
 //BA.debugLineNum = 5;BA.debugLine="Type InputTypes(Decimal As String, None As String";
;
 //BA.debugLineNum = 6;BA.debugLine="Public TextAlign As TextAlignType";
_textalign = new b4a.example.clseditablegrid._textaligntype();
 //BA.debugLineNum = 7;BA.debugLine="Public EditType As ControlType";
_edittype = new b4a.example.clseditablegrid._controltype();
 //BA.debugLineNum = 8;BA.debugLine="Public InputType As InputTypes";
_inputtype = new b4a.example.clseditablegrid._inputtypes();
 //BA.debugLineNum = 9;BA.debugLine="Public Columns As Map";
_columns = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 10;BA.debugLine="Dim dummyView As View ' bug fix";
_dummyview = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
 //BA.debugLineNum = 11;BA.debugLine="Public FontSize As Int";
_fontsize = 0;
 //BA.debugLineNum = 12;BA.debugLine="Public Rows As Map";
_rows = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 13;BA.debugLine="Public RowHeight As Int";
_rowheight = 0;
 //BA.debugLineNum = 14;BA.debugLine="Public cv As CustomListView";
_cv = new b4a.example.customlistview();
 //BA.debugLineNum = 15;BA.debugLine="Public pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Public cList As Map";
_clist = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 17;BA.debugLine="End Sub";
return "";
}
public String  _drawgrid() throws Exception{
int _intl = 0;
String _scolumname = "";
String _swidth = "";
String _scaption = "";
String _sformat = "";
String _salignment = "";
String _cedittype = "";
String _cinputtype = "";
int _intw = 0;
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _cm = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
int _r = 0;
String _rowid = "";
anywheresoftware.b4a.objects.collections.Map _rd = null;
anywheresoftware.b4a.objects.PanelWrapper _rp = null;
anywheresoftware.b4a.objects.EditTextWrapper _txt = null;
 //BA.debugLineNum = 80;BA.debugLine="Sub DrawGrid()";
 //BA.debugLineNum = 81;BA.debugLine="Dim intL As Int: intL = 10";
_intl = 0;
 //BA.debugLineNum = 81;BA.debugLine="Dim intL As Int: intL = 10";
_intl = (int) (10);
 //BA.debugLineNum = 82;BA.debugLine="Dim sColumName As String: sColumName = \"\"";
_scolumname = "";
 //BA.debugLineNum = 82;BA.debugLine="Dim sColumName As String: sColumName = \"\"";
_scolumname = "";
 //BA.debugLineNum = 83;BA.debugLine="Dim sWidth As String: sWidth = \"\"";
_swidth = "";
 //BA.debugLineNum = 83;BA.debugLine="Dim sWidth As String: sWidth = \"\"";
_swidth = "";
 //BA.debugLineNum = 84;BA.debugLine="Dim sCaption As String: sCaption = \"\"";
_scaption = "";
 //BA.debugLineNum = 84;BA.debugLine="Dim sCaption As String: sCaption = \"\"";
_scaption = "";
 //BA.debugLineNum = 85;BA.debugLine="Dim sFormat As String: sFormat = \"\"";
_sformat = "";
 //BA.debugLineNum = 85;BA.debugLine="Dim sFormat As String: sFormat = \"\"";
_sformat = "";
 //BA.debugLineNum = 86;BA.debugLine="Dim sAlignment As String: sAlignment = \"\"";
_salignment = "";
 //BA.debugLineNum = 86;BA.debugLine="Dim sAlignment As String: sAlignment = \"\"";
_salignment = "";
 //BA.debugLineNum = 87;BA.debugLine="Dim cEditType As String: cEditType = \"\"";
_cedittype = "";
 //BA.debugLineNum = 87;BA.debugLine="Dim cEditType As String: cEditType = \"\"";
_cedittype = "";
 //BA.debugLineNum = 88;BA.debugLine="Dim cInputType As String: cInputType = \"\"";
_cinputtype = "";
 //BA.debugLineNum = 88;BA.debugLine="Dim cInputType As String: cInputType = \"\"";
_cinputtype = "";
 //BA.debugLineNum = 89;BA.debugLine="Dim intW As Int: intW = 0";
_intw = 0;
 //BA.debugLineNum = 89;BA.debugLine="Dim intW As Int: intW = 0";
_intw = (int) (0);
 //BA.debugLineNum = 91;BA.debugLine="pnl.Color = Colors.Black				'aqui muda a cor das";
_pnl.setColor(__c.Colors.Black);
 //BA.debugLineNum = 92;BA.debugLine="For i = 0 To Columns.Size - 1";
{
final int step20 = 1;
final int limit20 = (int) (_columns.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit20 ;_i = _i + step20 ) {
 //BA.debugLineNum = 93;BA.debugLine="Dim cm As Map";
_cm = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 94;BA.debugLine="cm.Initialize";
_cm.Initialize();
 //BA.debugLineNum = 95;BA.debugLine="cm = Columns.GetValueAt(i)";
_cm.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_columns.GetValueAt(_i)));
 //BA.debugLineNum = 96;BA.debugLine="sColumName = cm.Get(\"ColumnName\")";
_scolumname = BA.ObjectToString(_cm.Get((Object)("ColumnName")));
 //BA.debugLineNum = 97;BA.debugLine="sCaption = cm.Get(\"Caption\")";
_scaption = BA.ObjectToString(_cm.Get((Object)("Caption")));
 //BA.debugLineNum = 98;BA.debugLine="sWidth = cm.Get(\"Width\")";
_swidth = BA.ObjectToString(_cm.Get((Object)("Width")));
 //BA.debugLineNum = 99;BA.debugLine="sAlignment = cm.Get(\"Alignment\")";
_salignment = BA.ObjectToString(_cm.Get((Object)("Alignment")));
 //BA.debugLineNum = 100;BA.debugLine="cEditType = cm.Get(\"EditType\")";
_cedittype = BA.ObjectToString(_cm.Get((Object)("EditType")));
 //BA.debugLineNum = 101;BA.debugLine="intW = sWidth";
_intw = (int)(Double.parseDouble(_swidth));
 //BA.debugLineNum = 103;BA.debugLine="Dim lbl As Label";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 104;BA.debugLine="lbl.Initialize(\"h\" & sColumName)";
_lbl.Initialize(ba,"h"+_scolumname);
 //BA.debugLineNum = 105;BA.debugLine="Select Case sAlignment";
switch (BA.switchObjectToInt(_salignment,_textalign.Center /*String*/ ,_textalign.Left /*String*/ ,_textalign.Right /*String*/ )) {
case 0: {
 //BA.debugLineNum = 106;BA.debugLine="Case TextAlign.Center: lbl.Gravity = Bit.Or(Gra";
_lbl.setGravity(__c.Bit.Or(__c.Gravity.CENTER_VERTICAL,__c.Gravity.CENTER));
 break; }
case 1: {
 //BA.debugLineNum = 107;BA.debugLine="Case TextAlign.Left: lbl.Gravity = Bit.Or(Gravi";
_lbl.setGravity(__c.Bit.Or(__c.Gravity.CENTER_VERTICAL,__c.Gravity.LEFT));
 break; }
case 2: {
 //BA.debugLineNum = 108;BA.debugLine="Case TextAlign.Right: lbl.Gravity = Bit.Or(Grav";
_lbl.setGravity(__c.Bit.Or(__c.Gravity.CENTER_VERTICAL,__c.Gravity.RIGHT));
 break; }
}
;
 //BA.debugLineNum = 110;BA.debugLine="lbl.Text = sCaption";
_lbl.setText(BA.ObjectToCharSequence(_scaption));
 //BA.debugLineNum = 111;BA.debugLine="lbl.TextSize = FontSize";
_lbl.setTextSize((float) (_fontsize));
 //BA.debugLineNum = 112;BA.debugLine="lbl.TextColor = Colors.Red";
_lbl.setTextColor(__c.Colors.Red);
 //BA.debugLineNum = 113;BA.debugLine="pnl.AddView(lbl, intL, 0dip, intW, 60)";
_pnl.AddView((android.view.View)(_lbl.getObject()),_intl,__c.DipToCurrent((int) (0)),_intw,(int) (60));
 //BA.debugLineNum = 114;BA.debugLine="intL = intL + intW + 10";
_intl = (int) (_intl+_intw+10);
 }
};
 //BA.debugLineNum = 117;BA.debugLine="pnl.Width = intL";
_pnl.setWidth(_intl);
 //BA.debugLineNum = 118;BA.debugLine="cv.AsView.Width = intL";
_cv._asview /*anywheresoftware.b4a.objects.ConcreteViewWrapper*/ ().setWidth(_intl);
 //BA.debugLineNum = 121;BA.debugLine="cv.clear";
_cv._clear /*String*/ ();
 //BA.debugLineNum = 122;BA.debugLine="For r = 0 To Rows.Size - 1";
{
final int step49 = 1;
final int limit49 = (int) (_rows.getSize()-1);
_r = (int) (0) ;
for (;_r <= limit49 ;_r = _r + step49 ) {
 //BA.debugLineNum = 124;BA.debugLine="Dim rowID As String";
_rowid = "";
 //BA.debugLineNum = 125;BA.debugLine="Dim rd As Map";
_rd = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 126;BA.debugLine="rd.Initialize";
_rd.Initialize();
 //BA.debugLineNum = 127;BA.debugLine="rowID = Rows.GetKeyAt(r)";
_rowid = BA.ObjectToString(_rows.GetKeyAt(_r));
 //BA.debugLineNum = 128;BA.debugLine="rd = Rows.GetValueAt(r)";
_rd.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_rows.GetValueAt(_r)));
 //BA.debugLineNum = 130;BA.debugLine="Dim rp As Panel";
_rp = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 131;BA.debugLine="rp.Initialize(\"\")";
_rp.Initialize(ba,"");
 //BA.debugLineNum = 132;BA.debugLine="rp.Color = Colors.White			'esse aqui muda o fund";
_rp.setColor(__c.Colors.White);
 //BA.debugLineNum = 134;BA.debugLine="intL = 10";
_intl = (int) (10);
 //BA.debugLineNum = 135;BA.debugLine="For i = 0 To Columns.Size - 1";
{
final int step59 = 1;
final int limit59 = (int) (_columns.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit59 ;_i = _i + step59 ) {
 //BA.debugLineNum = 136;BA.debugLine="Dim cm As Map";
_cm = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 137;BA.debugLine="cm.Initialize";
_cm.Initialize();
 //BA.debugLineNum = 138;BA.debugLine="cm = Columns.GetValueAt(i)";
_cm.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_columns.GetValueAt(_i)));
 //BA.debugLineNum = 139;BA.debugLine="sColumName = cm.Get(\"ColumnName\")";
_scolumname = BA.ObjectToString(_cm.Get((Object)("ColumnName")));
 //BA.debugLineNum = 140;BA.debugLine="sWidth = cm.Get(\"Width\")";
_swidth = BA.ObjectToString(_cm.Get((Object)("Width")));
 //BA.debugLineNum = 141;BA.debugLine="sFormat = cm.Get(\"Format\")";
_sformat = BA.ObjectToString(_cm.Get((Object)("Format")));
 //BA.debugLineNum = 142;BA.debugLine="sAlignment = cm.Get(\"Alignment\")";
_salignment = BA.ObjectToString(_cm.Get((Object)("Alignment")));
 //BA.debugLineNum = 143;BA.debugLine="cEditType = cm.Get(\"EditType\")";
_cedittype = BA.ObjectToString(_cm.Get((Object)("EditType")));
 //BA.debugLineNum = 144;BA.debugLine="cInputType = cm.Get(\"InputType\")";
_cinputtype = BA.ObjectToString(_cm.Get((Object)("InputType")));
 //BA.debugLineNum = 145;BA.debugLine="intW = sWidth";
_intw = (int)(Double.parseDouble(_swidth));
 //BA.debugLineNum = 146;BA.debugLine="Select Case cEditType";
switch (BA.switchObjectToInt(_cedittype,_edittype.IsLabel /*String*/ ,_edittype.IsTextBox /*String*/ )) {
case 0: {
 //BA.debugLineNum = 148;BA.debugLine="Dim lbl As Label";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 149;BA.debugLine="lbl.Initialize(sColumName)";
_lbl.Initialize(ba,_scolumname);
 //BA.debugLineNum = 150;BA.debugLine="Select Case sAlignment";
switch (BA.switchObjectToInt(_salignment,_textalign.Center /*String*/ ,_textalign.Left /*String*/ ,_textalign.Right /*String*/ )) {
case 0: {
 //BA.debugLineNum = 151;BA.debugLine="Case TextAlign.Center: lbl.Gravity = Bit.Or(Gr";
_lbl.setGravity(__c.Bit.Or(__c.Gravity.CENTER_VERTICAL,__c.Gravity.CENTER));
 break; }
case 1: {
 //BA.debugLineNum = 152;BA.debugLine="Case TextAlign.Left: lbl.Gravity = Bit.Or(Grav";
_lbl.setGravity(__c.Bit.Or(__c.Gravity.CENTER_VERTICAL,__c.Gravity.LEFT));
 break; }
case 2: {
 //BA.debugLineNum = 153;BA.debugLine="Case TextAlign.Right: lbl.Gravity = Bit.Or(Gra";
_lbl.setGravity(__c.Bit.Or(__c.Gravity.CENTER_VERTICAL,__c.Gravity.RIGHT));
 break; }
}
;
 //BA.debugLineNum = 155;BA.debugLine="lbl.Text = rd.Get(sColumName)";
_lbl.setText(BA.ObjectToCharSequence(_rd.Get((Object)(_scolumname))));
 //BA.debugLineNum = 156;BA.debugLine="lbl.TextSize = FontSize";
_lbl.setTextSize((float) (_fontsize));
 //BA.debugLineNum = 157;BA.debugLine="lbl.TextColor = Colors.Black";
_lbl.setTextColor(__c.Colors.Black);
 //BA.debugLineNum = 158;BA.debugLine="rp.AddView(lbl, intL, 80, intW, 70)";
_rp.AddView((android.view.View)(_lbl.getObject()),_intl,(int) (80),_intw,(int) (70));
 break; }
case 1: {
 //BA.debugLineNum = 160;BA.debugLine="Dim txt As EditText";
_txt = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 161;BA.debugLine="txt.Initialize(sColumName)";
_txt.Initialize(ba,_scolumname);
 //BA.debugLineNum = 162;BA.debugLine="Select Case sAlignment";
switch (BA.switchObjectToInt(_salignment,_textalign.Center /*String*/ ,_textalign.Left /*String*/ ,_textalign.Right /*String*/ )) {
case 0: {
 //BA.debugLineNum = 163;BA.debugLine="Case TextAlign.Center: txt.Gravity = Bit.Or(Gr";
_txt.setGravity(__c.Bit.Or(__c.Gravity.CENTER_VERTICAL,__c.Gravity.CENTER));
 break; }
case 1: {
 //BA.debugLineNum = 164;BA.debugLine="Case TextAlign.Left: txt.Gravity = Bit.Or(Grav";
_txt.setGravity(__c.Bit.Or(__c.Gravity.CENTER_VERTICAL,__c.Gravity.LEFT));
 break; }
case 2: {
 //BA.debugLineNum = 165;BA.debugLine="Case TextAlign.Right: txt.Gravity = Bit.Or(Gra";
_txt.setGravity(__c.Bit.Or(__c.Gravity.CENTER_VERTICAL,__c.Gravity.RIGHT));
 break; }
}
;
 //BA.debugLineNum = 167;BA.debugLine="txt.Text = rd.Get(sColumName)";
_txt.setText(BA.ObjectToCharSequence(_rd.Get((Object)(_scolumname))));
 //BA.debugLineNum = 168;BA.debugLine="txt.TextSize = FontSize";
_txt.setTextSize((float) (_fontsize));
 //BA.debugLineNum = 169;BA.debugLine="txt.TextColor = Colors.Black";
_txt.setTextColor(__c.Colors.Black);
 //BA.debugLineNum = 170;BA.debugLine="txt.SingleLine = True";
_txt.setSingleLine(__c.True);
 //BA.debugLineNum = 171;BA.debugLine="Select Case cInputType";
switch (BA.switchObjectToInt(_cinputtype,_inputtype.Decimal /*String*/ ,_inputtype.None /*String*/ ,_inputtype.Numbers /*String*/ ,_inputtype.Text /*String*/ )) {
case 0: {
 //BA.debugLineNum = 172;BA.debugLine="Case InputType.Decimal: txt.InputType = txt.IN";
_txt.setInputType(_txt.INPUT_TYPE_DECIMAL_NUMBERS);
 break; }
case 1: {
 //BA.debugLineNum = 173;BA.debugLine="Case InputType.None: txt.InputType = txt.INPUT";
_txt.setInputType(_txt.INPUT_TYPE_NONE);
 break; }
case 2: {
 //BA.debugLineNum = 174;BA.debugLine="Case InputType.Numbers: txt.InputType = txt.IN";
_txt.setInputType(_txt.INPUT_TYPE_NUMBERS);
 break; }
case 3: {
 //BA.debugLineNum = 175;BA.debugLine="Case InputType.Text: txt.InputType = txt.INPUT";
_txt.setInputType(_txt.INPUT_TYPE_TEXT);
 break; }
}
;
 //BA.debugLineNum = 177;BA.debugLine="rp.AddView(txt, intL, 80, intW, 110)";
_rp.AddView((android.view.View)(_txt.getObject()),_intl,(int) (80),_intw,(int) (110));
 break; }
}
;
 //BA.debugLineNum = 179;BA.debugLine="intL = intL + intW + 10";
_intl = (int) (_intl+_intw+10);
 }
};
 //BA.debugLineNum = 181;BA.debugLine="cv.Add(rp, RowHeight, rowID)";
_cv._add /*String*/ (_rp,_rowheight,(Object)(_rowid));
 }
};
 //BA.debugLineNum = 183;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.collections.Map  _getrow(int _index) throws Exception{
anywheresoftware.b4a.objects.collections.Map _mout = null;
String _cedittype = "";
String _ccolumnname = "";
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _cm = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.EditTextWrapper _txt = null;
 //BA.debugLineNum = 186;BA.debugLine="Sub GetRow(Index As Int) As Map";
 //BA.debugLineNum = 187;BA.debugLine="Dim mOut As Map";
_mout = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 188;BA.debugLine="mOut.Initialize";
_mout.Initialize();
 //BA.debugLineNum = 189;BA.debugLine="Dim cEditType As String";
_cedittype = "";
 //BA.debugLineNum = 190;BA.debugLine="Dim cColumnName As String";
_ccolumnname = "";
 //BA.debugLineNum = 191;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 192;BA.debugLine="pnl = cv.GetPanel(Index)";
_pnl = _cv._getpanel /*anywheresoftware.b4a.objects.PanelWrapper*/ (_index);
 //BA.debugLineNum = 193;BA.debugLine="For i = 0 To Columns.Size - 1";
{
final int step7 = 1;
final int limit7 = (int) (_columns.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit7 ;_i = _i + step7 ) {
 //BA.debugLineNum = 194;BA.debugLine="Dim cm As Map";
_cm = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 195;BA.debugLine="cm.Initialize";
_cm.Initialize();
 //BA.debugLineNum = 196;BA.debugLine="cm = Columns.GetValueAt(i)";
_cm.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_columns.GetValueAt(_i)));
 //BA.debugLineNum = 197;BA.debugLine="cColumnName = cm.Get(\"ColumnName\")";
_ccolumnname = BA.ObjectToString(_cm.Get((Object)("ColumnName")));
 //BA.debugLineNum = 198;BA.debugLine="cEditType = cm.Get(\"EditType\")";
_cedittype = BA.ObjectToString(_cm.Get((Object)("EditType")));
 //BA.debugLineNum = 199;BA.debugLine="Select Case cEditType";
switch (BA.switchObjectToInt(_cedittype,_edittype.IsLabel /*String*/ ,_edittype.IsTextBox /*String*/ )) {
case 0: {
 //BA.debugLineNum = 201;BA.debugLine="Dim lbl As Label";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 202;BA.debugLine="lbl = pnl.GetView(i)";
_lbl.setObject((android.widget.TextView)(_pnl.GetView(_i).getObject()));
 //BA.debugLineNum = 204;BA.debugLine="mOut.Put(cColumnName, lbl.Text)";
_mout.Put((Object)(_ccolumnname),(Object)(_lbl.getText()));
 break; }
case 1: {
 //BA.debugLineNum = 206;BA.debugLine="Dim txt As EditText";
_txt = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 207;BA.debugLine="txt = pnl.GetView(i)";
_txt.setObject((android.widget.EditText)(_pnl.GetView(_i).getObject()));
 //BA.debugLineNum = 209;BA.debugLine="mOut.Put(cColumnName, txt.Text)";
_mout.Put((Object)(_ccolumnname),(Object)(_txt.getText()));
 break; }
}
;
 }
};
 //BA.debugLineNum = 212;BA.debugLine="Return mOut";
if (true) return _mout;
 //BA.debugLineNum = 213;BA.debugLine="End Sub";
return null;
}
public String  _initialize(anywheresoftware.b4a.BA _ba) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 20;BA.debugLine="Public Sub Initialize()";
 //BA.debugLineNum = 21;BA.debugLine="cList.Initialize";
_clist.Initialize();
 //BA.debugLineNum = 22;BA.debugLine="Columns.Initialize";
_columns.Initialize();
 //BA.debugLineNum = 23;BA.debugLine="Rows.Initialize";
_rows.Initialize();
 //BA.debugLineNum = 24;BA.debugLine="TextAlign.Initialize";
_textalign.Initialize();
 //BA.debugLineNum = 25;BA.debugLine="TextAlign.Center = \"Center\"";
_textalign.Center /*String*/  = "Center";
 //BA.debugLineNum = 26;BA.debugLine="TextAlign.Left = \"Left\"";
_textalign.Left /*String*/  = "Left";
 //BA.debugLineNum = 27;BA.debugLine="TextAlign.Right = \"Right\"";
_textalign.Right /*String*/  = "Right";
 //BA.debugLineNum = 28;BA.debugLine="EditType.Initialize";
_edittype.Initialize();
 //BA.debugLineNum = 29;BA.debugLine="EditType.IsLabel = \"Label\"";
_edittype.IsLabel /*String*/  = "Label";
 //BA.debugLineNum = 30;BA.debugLine="EditType.IsTextBox = \"EditText\"";
_edittype.IsTextBox /*String*/  = "EditText";
 //BA.debugLineNum = 31;BA.debugLine="InputType.Initialize";
_inputtype.Initialize();
 //BA.debugLineNum = 32;BA.debugLine="InputType.Decimal = \"Decimal\"";
_inputtype.Decimal /*String*/  = "Decimal";
 //BA.debugLineNum = 33;BA.debugLine="InputType.None = \"None\"";
_inputtype.None /*String*/  = "None";
 //BA.debugLineNum = 34;BA.debugLine="InputType.Numbers = \"Numbers\"";
_inputtype.Numbers /*String*/  = "Numbers";
 //BA.debugLineNum = 35;BA.debugLine="InputType.Text = \"Text\"";
_inputtype.Text /*String*/  = "Text";
 //BA.debugLineNum = 36;BA.debugLine="FontSize = 12";
_fontsize = (int) (12);
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
public String  _removerow(String _rowid) throws Exception{
 //BA.debugLineNum = 75;BA.debugLine="Public Sub RemoveRow(rowID As String)";
 //BA.debugLineNum = 76;BA.debugLine="If Rows.ContainsKey(rowID) Then Rows.Remove(rowID";
if (_rows.ContainsKey((Object)(_rowid))) { 
_rows.Remove((Object)(_rowid));};
 //BA.debugLineNum = 77;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
