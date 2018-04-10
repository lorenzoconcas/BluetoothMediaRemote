using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.InteropServices;

namespace KeyboardExecuter
{
    class Program
    {
        [DllImport("user32.dll")]
        static extern void keybd_event(byte bVk, byte bScan, uint dwFlags, UIntPtr dwExtraInfo);

        static void Main(string[] args)
        {
            String command = "";
            if (args.Length < 1)
            {
                Console.WriteLine("Keyboard Commander");
                
                Console.WriteLine("Inserisci commando : ");
                command = Console.ReadLine();
            }
            if (args.Length == 1)
            {
                command = args[0];
            }
            if (CheckValidity(command))
                //converto il commando da stringa a valore HEX (e cast verso byte)
                keybd_event(Convert.ToByte(command, 16), 0, 0, UIntPtr.Zero);
            else
                Console.WriteLine("commando non valido");
        }
        private static bool CheckValidity(string command)
        {
           
            if (command.StartsWith("0x"))
                return true;
            else return false;
        }
    }
}
