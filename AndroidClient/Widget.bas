B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Service
Version=7.8
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: False
	
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim rv As RemoteViews
	Dim c As String
	Dim AStream As AsyncStreams
End Sub

Sub Service_Create
		rv = ConfigureHomeWidget("2", "rv", 0, "Bluetooth Media Remote", True)
	
End Sub

Sub AStream_Error
	ToastMessageShow("Connection is broken.", True)

End Sub

Sub AStream_Terminated
	AStream_Error
End Sub

Sub Service_Start (StartingIntent As Intent)
	If AStream.IsInitialized = False Then
		AStream.InitializePrefix(Main.serial1.InputStream, True, Main.serial1.OutputStream, "AStream")
	End If
End Sub

Sub Service_Destroy

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