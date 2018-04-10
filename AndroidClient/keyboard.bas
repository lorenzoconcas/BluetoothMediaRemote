B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=8
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

	Private mouse As Panel
	Private key_q As Panel
	Private key_w As Panel
	Private key_e As Panel
	Private key_r As Panel
	Private key_t As Panel
	Private key_y As Panel
	Private key_u As Panel
	Private key_i As Panel
	Private key_o As Panel
	Private key_p As Panel
	Private key_a As Panel
	Private key_s As Panel
	Private key_d As Panel
	Private key_f As Panel
	Private key_g As Panel
	Private key_h As Panel
	Private key_j As Panel
	Private key_k As Panel
	Private key_l As Panel
	Private key_z As Panel
	Private key_x As Panel
	Private key_c As Panel
	Private key_v As Panel
	Private key_b As Panel
	Private key_n As Panel
	Private key_m As Panel
	Private key_shift As Panel
	Private key_enter As Panel
	Private key_space As Panel
	Private key_super As Panel
	Private key_delete As Panel
	Private key_1 As Panel
	Private key_2 As Panel
	Private key_3 As Panel
	Private key_4 As Panel
	Private key_5 As Panel
	Private key_6 As Panel
	Private key_7 As Panel
	Private key_8 As Panel
	Private key_9 As Panel
	Private key_0 As Panel

	Private mouseLeft As Panel
	Private mouseRight As Panel
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("3")
'	If AStream.IsInitialized = False Then
'		AStream.InitializePrefix(Main.serial1.InputStream, True, Main.serial1.OutputStream, "AStream")
'	
'	End If
	
	
End Sub


Sub sendKeystroke(key As String)
	Dim new_mode As String = "keyboard"
	controls.AStream.Write(new_mode.GetBytes("UTF8"))
	controls.AStream.Write(key.GetBytes("UTF8"))
	Log(key)
	Main.vib.Vibrate(Main.vibDuration)
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub mouse_Touch (Action As Int, X As Float, Y As Float)
	Dim new_mode As String = "mouse"
	Dim finalX, finalY As Int
	controls.AStream.Write(new_mode.GetBytes("UTF8"))
	X = (X*100)/mouse.Width
	Y = (Y*100)/mouse.Height
	finalX = X
	finalY = Y
	Log("POS : X "&finalX&" Y: "&finalY)
	Dim new_pos As String = finalX&"-"&finalY
	controls.AStream.Write(new_pos.GetBytes("UTF8"))
	Sleep(100)
End Sub

Sub key_q_Click
	sendKeystroke("0x51")
End Sub

Sub key_0_Click
	sendKeystroke("0x30")
End Sub

Sub key_9_Click
	sendKeystroke("0x39")
End Sub

Sub key_8_Click
	sendKeystroke("0x38")
End Sub

Sub key_7_Click
	sendKeystroke("0x37")
End Sub

Sub key_6_Click
	sendKeystroke("0x36")
End Sub

Sub key_5_Click
	sendKeystroke("0x35")
End Sub

Sub key_4_Click
	sendKeystroke("0x34")
End Sub

Sub key_3_Click
	sendKeystroke("0x33")
End Sub

Sub key_2_Click
	sendKeystroke("0x32")
End Sub

Sub key_1_Click
	sendKeystroke("0x31")
End Sub

Sub key_delete_Click
	sendKeystroke("0x08")
End Sub

Sub key_super_Click
	sendKeystroke("0x5B")
End Sub

Sub key_space_Click
	sendKeystroke("0x20")
End Sub

Sub key_enter_Click
	sendKeystroke("0x0D")
End Sub

Sub key_shift_Click
	sendKeystroke("0x10")
End Sub

Sub key_m_Click
	sendKeystroke("0x4D")
End Sub

Sub key_n_Click
	sendKeystroke("0x4E")
End Sub

Sub key_b_Click
	sendKeystroke("0x42")
End Sub

Sub key_v_Click
	sendKeystroke("0x56")
End Sub

Sub key_c_Click
	sendKeystroke("0x43")
End Sub

Sub key_x_Click
	sendKeystroke("0x58")
End Sub

Sub key_z_Click
	sendKeystroke("0x5A")
End Sub

Sub key_l_Click
	sendKeystroke("0x4C")
End Sub

Sub key_k_Click
	sendKeystroke("0x4B")
End Sub

Sub key_j_Click
	sendKeystroke("0x4A")
End Sub

Sub key_h_Click
	sendKeystroke("0x48")
End Sub

Sub key_g_Click
	sendKeystroke("0x47")
End Sub

Sub key_f_Click
	sendKeystroke("0x46")
End Sub

Sub key_d_Click
	sendKeystroke("0x44")
End Sub

Sub key_s_Click
	sendKeystroke("0x53")
End Sub

Sub key_a_Click
	sendKeystroke("0x41")
End Sub

Sub key_p_Click
	sendKeystroke("0x50")
End Sub

Sub key_o_Click
	sendKeystroke("0x4F")
End Sub


Sub key_r_Click
	sendKeystroke("0x52")
End Sub

Sub key_i_Click
	sendKeystroke("0x49")
End Sub

Sub key_u_Click
	sendKeystroke("0x55")
End Sub

Sub key_t_Click
	sendKeystroke("0x54")
End Sub

Sub key_e_Click
	sendKeystroke("0x45")
End Sub

Sub key_w_Click
	sendKeystroke("0x57")
End Sub

Sub mouseRight_Click
	sendKeystroke("0x02")
End Sub

Sub mouseLeft_Click
	sendKeystroke("0x01")
End Sub