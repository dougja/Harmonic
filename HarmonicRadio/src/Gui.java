// handles the GUI and the changing of global variables via the control components

// --- fix ---

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class Gui implements ChangeListener
{
	private JFrame window;
	private Hashtable <Object,Object> components;
    private Compilations comp;
    private IPSeeker ip;
    
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
		
		comp = new Compilations();
		ip = new IPSeeker();
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
			slider.setMaximum(9);
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
				comp.play(source.getValue());
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