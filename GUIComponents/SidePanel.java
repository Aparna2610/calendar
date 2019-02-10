/*
 * SidePanel
 * SidePanel shows left panel and shows currents month
 * 
 *  @author		Aparna Gangwar
 *  @version	1.0
 *  @since		2019-02-08
 */
package GUIComponents;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class SidePanel extends JPanel {
	
	//UI and Color Scheme
	private Color backgroundColor1;
	private Color backgroundColor2;
	private Color fontColor;
	
	//State variables
	private String activeMonth;
	private int optionWidth = 80;
	private int optionHeight = 30;
	
	//Child Component
	private ArrayList<SidePanelOptions> months;
	
	public SidePanel() {
		// Initializing State Variables
		backgroundColor1 = ColorPalette.PALETTE2;
		backgroundColor2 = ColorPalette.PALETTE4;
		fontColor = ColorPalette.PALETTE5;
		months = new ArrayList<SidePanelOptions>();
		
		String[] monthNames = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		
		for(String monthName : monthNames) {
			SidePanelOptions spo = new SidePanelOptions(monthName);
			months.add(spo);
			spo.setVerticalAlignment(SwingConstants.CENTER);
			spo.setHorizontalAlignment(SwingConstants.CENTER);
			//spo.setBorder(BorderFactory.createLineBorder(Color.black));
			spo.setSize(optionWidth,optionHeight);
			spo.setForeground(fontColor);
			spo.setCursor(new Cursor(Cursor.HAND_CURSOR));
			add(spo);
		}
		
		setLayout(null);
	}
	
	/*
	 * setActiveMonth: Highlight the active month
	 */
	public void setActiveMonth(String activeMonth) {
		for(SidePanelOptions spo : months) {
			if(spo.getText().equalsIgnoreCase(activeMonth)) {
				spo.setActive(true);
			} else {
				spo.setActive(false);
			}
		}
	}
	
	/*
	 * placeAndResizeComponents: Calculates size and location of child 
	 * components dynamically to ensure resizing flexibility
	 */
	private void placeAndResizeComponents() {
		int margin = 10;
		
		int actualHeight = 2 * margin + optionHeight;
		int totalHeight = 12 * actualHeight; //12 - Number of Months
		
		int base_y = (getSize().height - totalHeight)/2;
		int base_x = (getSize().width - optionWidth)/2;
		int y = base_y;
		int x = base_x;
		
		for(SidePanelOptions spo : months) {
			spo.setLocation(x, y);
			y = y + actualHeight;
		}
	}
	
	public void addMouseListener(MouseAdapter mouseAdapter) {
		for(SidePanelOptions spo : months) {
			spo.addMouseListener(mouseAdapter);
		}
	}
	
	public int getClickedMonth(Component clickedComponent) {
		SidePanelOptions spo = (SidePanelOptions) clickedComponent;
		String[] monthNames = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		for(int i=0;i<monthNames.length;i++) {
			if(monthNames[i].equalsIgnoreCase(spo.getText())) {
				return i+1;
			}
		}
		return 1;
	}
	
	/*
	 * Overrides the superclass paint method to ensure dynamic recalculation of
	 * size and location of components before painting.
	 */
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D) g;
		Dimension dimension = getSize();
		
		//Gradient
		GradientPaint gp = new GradientPaint(0, 0, backgroundColor1, 0, dimension.height, backgroundColor2);
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, dimension.width, dimension.height);
		placeAndResizeComponents();
		
		//After drawing, we need to redraw child components
		super.paintChildren(g);
	}
	
	/*
	 * SidePanelOptions: Options in sidepane
	 */
	static class SidePanelOptions extends JLabel {
		// State Variables
		private Font activeFont;
		private Font regularFont;
		private Color activeColor;
		private Color regularColor;
		private boolean isActive = false;
		
		SidePanelOptions(String text) {
			regularFont = new Font("Helvetica Neue", Font.PLAIN, 14);
			activeFont = new Font("Helvetica Neue", Font.BOLD, 14);
			activeColor = ColorPalette.PALETTE1;
			regularColor = ColorPalette.PALETTE5;
			
			setText(text);
			setFont(regularFont);
		}
		
		public void setActive(boolean val) {
			if(val != isActive) {
				isActive = val;
				if(val) {
					setFont(activeFont);
					setForeground(activeColor);
					setBorder(BorderFactory.createBevelBorder(0, ColorPalette.PALETTE2, ColorPalette.PALETTE4));
				}
				else  {
					setFont(regularFont);
					setForeground(regularColor);
					setBorder(null);
				}
				repaint();
			}
		}
		
	}
}
