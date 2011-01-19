/*==================================================================
 * This is the main program class. 
 =================================================================*/

import javax.swing.SwingUtilities;

class Harmonic implements Runnable
{
	public static void main(String[] args)
	{
		Harmonic program = new Harmonic();
		SwingUtilities.invokeLater(program);
	}
  
	public void run()
	{
		new Gui();
	}
}