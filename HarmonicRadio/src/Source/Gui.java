package Source;

// handles the GUI and the changing of global variables via the control components

// --- fix ---
import java.util.Hashtable; 
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

class Gui implements ChangeListener
{
	private StreamHttp player;
	private JFrame window;
	private Hashtable <Object,Object> components;

	// initialises Gui
	Gui()
	{
		// initialise window
		window = new JFrame("Harmonic Radio");
		window.getContentPane().setBackground(Color.BLACK);
		components = new Hashtable<Object,Object>();
		window.setResizable (true);
		window.setSize(800,300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	window.setLocationByPlatform(true);
    	window.setVisible(true);
		addComponents();
		window.setVisible(true);
		
		player = new StreamHttp();
	}

	// adds components to window

	private void addComponents()
	{
		window.setLayout(new BorderLayout());
		// add volume slider
		window.add(addSlider(SliderType.LEFT_SLIDER, VarType.TUNER, true),  BorderLayout.CENTER);
	}

	// adds a slider to the frame:

 	private  JSlider addSlider(SliderType sliderType, VarType varControl, Boolean snapping)
	{
		// creates vertical slider
		JSlider slider;
		if (varControl == VarType.TUNER)
		{
			slider = new JSlider(0);	
			slider.setMaximum(10);
			slider.setMinimum(1);
			slider.setMajorTickSpacing(1);
			slider.setMaximumSize(new Dimension(535, 90));
		}
		else if (varControl == VarType.PLAYLIST)
		{
			slider = new JSlider(1);
			slider.setMaximum(10);
			slider.setMinimum(1);
			slider.setMajorTickSpacing(1);
		}
		else
		{
			slider = new JSlider(1);
			slider.setMajorTickSpacing(1);
		}
		// sets whether the slider snaps or not
		// --- fix ---
		slider.setSnapToTicks(true);
		// --- --- ---
		// initialize slider to var
		slider.setValue(0);
		// add the Gui as the change listener
		slider.addChangeListener(this);
		// insert into hashtable with identifier of var it will control
		slider.setBackground(Color.BLACK);
		components.put(slider, varControl);
		return slider;
	}

	public void stateChanged(ChangeEvent e)
	{
		JSlider source = (JSlider) e.getSource();
		VarType varControl = (VarType) components.get(source);
		switch (varControl)
		{
			case VOLUME:; break;
			case TUNER:
			{
				try 
				{
					player.playnewURL(source.getValue());	
				} 
				catch (InterruptedException e1) 
				{
					e1.printStackTrace();
				}
			}
			break;
		}
	}
	
	void stopListener()
	{

	}

	// enum for the types of slider

	private enum SliderType
	{
		LEFT_SLIDER, RIGHT_SLIDER, MIDDLE_SLIDER;
	}

	// enum for the controls

	private enum VarType
	{
		VOLUME, PLAYLIST, TUNER;
	}
}