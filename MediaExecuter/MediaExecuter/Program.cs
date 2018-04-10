using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.InteropServices;

namespace MediaExecuter
{
    class Program
    {
        [DllImport("user32.dll")]
        static extern void keybd_event(byte bVk, byte bScan, uint dwFlags, UIntPtr dwExtraInfo);
        public const int KEYEVENTF_KEYUP = 0x002;
        public const byte VK_MEDIA_PLAY_PAUSE = 0xB3; //TOGGLE PLAY/PAUSE
        public const byte VK_MEDIA_STOP = 0xB2; //STOP PLAYING
        public const byte VK_MEDIA_PREV = 0xB1; //PREV TRACK
        public const byte VK_MEDIA_NEXT = 0xB0; //NEXT TRACK

        public const byte VK_MEDIA_VOLUME_UP = 0xAF;
        public const byte VK_MEDIA_VOLUME_DOWN = 0xAE;
        public const byte VK_MEDIA_VOLUME_MUTE = 0xAD;
        //keybd_event(VK_MEDIA_PLAY_PAUSE, 0, 0, UIntPtr.Zero);
        static void Main(string[] args)
        {
            String command = "";
            if(args.Length < 1)
            {
                Console.WriteLine("Media Commander");
                Console.WriteLine("Opzioni : ");
                Console.Write("play\npause\nnext\nprev\nVUP\nVDOWN\nVMUTE\n");
                Console.WriteLine("Inserisci commando : ");
                command = Console.ReadLine();
            }
            if (args.Length == 1)
            {
                command = args[0];
            }
                switch (command)
                {
                    case "play":
                    case "pause":
                        {
                            keybd_event(VK_MEDIA_PLAY_PAUSE, 0, 0, UIntPtr.Zero);
                            break;
                        }
                    case "next":
                        {
                            keybd_event(VK_MEDIA_NEXT, 0, 0, UIntPtr.Zero);
                            break;
                        }
                    case "prev":
                        {
                            keybd_event(VK_MEDIA_PREV, 0, 0, UIntPtr.Zero);
                            break;
                        }
                    case "VUP":
                        {
                            keybd_event(VK_MEDIA_VOLUME_UP, 0, 0, UIntPtr.Zero);
                            break;
                        }
                    case "VDOWN":
                        {
                            keybd_event(VK_MEDIA_VOLUME_DOWN, 0, 0, UIntPtr.Zero);
                            break;
                        }
                    case "VMUTE":
                        {
                            keybd_event(VK_MEDIA_VOLUME_MUTE, 0, 0, UIntPtr.Zero);
                            break;
                        }
                    case default:{
                        Console.WriteLine("Commando non valido");
                        return -1;
                    }

                }
          
        }
    }
}
