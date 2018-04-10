B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=5.5
@EndOfDesignText@
#Region Module Attributes
	#FullScreen: False
	#IncludeTitle: True
#End Region

'Activity module
Sub Process_Globals
	Dim AStream As AsyncStreams
End Sub

Sub Globals

	Private playpause As Button
	Private volup As Button
	Private voldown As Button
	Private volmute As Button
	Dim c As String
	
	Private next1 As Button
	Private prev As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("2")
	If AStream.IsInitialized = False Then
		AStream.InitializePrefix(Main.serial1.InputStream, True, Main.serial1.OutputStream, "AStream")
	End If

End Sub

Sub AStream_Error
	ToastMessageShow("Connection is broken.", True)

End Sub

Sub AStream_Terminated
	AStream_Error
End Sub

Sub Activity_Resume
	
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	If UserClosed Then
		AStream.Close
	End If
End Sub

Sub volmute_Click
	c = "6"
	AStream.Write(c.GetBytes("UTF8"))
End Sub

Sub voldown_Click
	c = "5"
	AStream.Write(c.GetBytes("UTF8"))
End Sub

Sub volup_Click
	c = "4"
	AStream.Write(c.GetBytes("UTF8"))
End Sub

Sub playpause_Click
	'AStream.Write(txtInput.Text.GetBytes("UTF8"))
	c = "0"
	AStream.Write(c.GetBytes("UTF8"))
End Sub

Sub prev_Click
	c = "3"
	AStream.Write(c.GetBytes("UTF8"))
End Sub

Sub next1_Click
	c = "2"
	AStream.Write(c.GetBytes("UTF8"))
End Sub