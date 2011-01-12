/*==================================================================
 * This is the main program class. 
 =================================================================*/

import javax.swing.*;



class Program implements Runnable
{
	public static void main(String[] args)
	{
		Program program = new Program();
		SwingUtilities.invokeLater(program);
	}
  
	public void run()
	{
		new Gui();
	}
}