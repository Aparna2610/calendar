/*
 * CalendarPanel
 * CalendarPanel is the main tabular panel which show date and month
 * 
 *  @author		Aparna Gangwar
 *  @version	1.0
 *  @since		2019-02-08
 */
package GUIComponents;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;

public class CalendarPanel extends JPanel{
	//UI and Color Scheme
	private Color backgroundColor;
	private Color lineColor;
	private Font font;
	private int headingHeight = 40;
	private int padding = 8;
	private int dateCellHeight = 100;
	
	//Child Components
	private JPanel daysPanel;
	private JScrollPane scrollPane;
	private JPanel dateTable;
	private ArrayList<JLabel> dayLabels;
	private ArrayList<DateCell> dateCells;

	public CalendarPanel() {
		// Initializing State Variables
		//Colors
		backgroundColor = ColorPalette.PALETTE1;
		lineColor = ColorPalette.PALETTE5;
				 
		//Fonts
		font = new Font("Helvetica Neue", Font.BOLD, 20);
		
		// Initialize Components
		daysPanel = new JPanel();
		daysPanel.setOpaque(false);
		dayLabels = new ArrayList<JLabel>();
		
		String[] days = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
		for(String day : days) {
			JLabel dayLabel = new JLabel(day);
			dayLabel.setForeground(ColorPalette.PALETTE6);
			dayLabel.setFont(font);
			dayLabel.setVerticalAlignment(SwingConstants.CENTER);
			dayLabel.setHorizontalAlignment(SwingConstants.LEFT);
			dayLabels.add(dayLabel);
			daysPanel.add(dayLabel);
		}
		
		dateTable = new JPanel();
		dateTable.setLayout(null);
		dateTable.setLocation(0, 0);
		dateTable.setSize(500,500);
		dateTable.setOpaque(false);
		//scrollPane.setBorder(null);
		
		createDateCells();
		
		scrollPane = new JScrollPane(dateTable);
		scrollPane.setPreferredSize(getSize());
		scrollPane.setLocation(0, headingHeight);
		scrollPane.setBorder(null);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		setLayout(null);
		add(daysPanel);
		add(scrollPane);
		setBackground(backgroundColor);
		placeAndResizeComponents();
		scrollPane.repaint();
	}
	
	/*
	 * createDateCells: Creates complete year date cell in tabular manner
	 */
	public void createDateCells() {
		//Two Padded Cells
		int x = 0;
		int y = headingHeight;
		
		//Today's Date
		int currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		
		dateCells = new ArrayList<DateCell>();
		DateCell tmp = new DateCell();
		dateTable.add(tmp);
		dateCells.add(tmp);
		tmp = new DateCell();
		dateTable.add(tmp);
		dateCells.add(tmp);
		
		int daysInMonths[] = {31,28,31,30,31,30,31,31,30,31,30,31};
		for(int month = 1; month <= daysInMonths.length; month++) {
			for(int date = 1; date <= daysInMonths[month - 1]; date++) {
				DateCell dc = new DateCell(date,month);
				dc.setPadding(padding);
				if(date == currentDate && month - 1 == currentMonth) {
					dc.setHighlighted(true);
				}
				dateCells.add(dc);
				dateTable.add(dc);
			}
		}
	}
	
	/*
	 * setHoliday: Mark holiday on specific date cell
	 */
	public void setHoliday(int date, int month, String text) {
		int daysInMonths[] = {31,28,31,30,31,30,31,31,30,31,30,31};
		int index = 2+date;
		for(int i=0;i<month-1;i++) {
			index = index + daysInMonths[i];
		}
		index = index - 1;
		
		dateCells.get(index).setHoliday(text);;
	}
	
	public void setActiveMonth(int month) {
		for(DateCell dc : dateCells) {
			if(dc.getMonth() == month) {
				dc.setActiveMonth(true);
			} else {
				dc.setActiveMonth(false);
			}
		}
	}
	
	public void scrollToMonth(int month) {
		for(DateCell dc : dateCells) {
			if(dc.getMonth() == month) {
				scrollPane.getViewport().setViewPosition(dc.getLocation());
				break;
			}
		}
	}
	
	public Rectangle getTableVisibleRect() {
		return dateTable.getVisibleRect();
	}
	
	@Override
	public Component getComponentAt(int x, int y) {
		return dateTable.getComponentAt(x, y);
	}
	
	/*
	 * placeAndResizeComponents: Calculates size and location of child 
	 * components dynamically to ensure resizing flexibility
	 */
	public void placeAndResizeComponents() {
		daysPanel.setSize(getSize().width, headingHeight);
		daysPanel.setLocation(0, 0);
		
		scrollPane.setSize(getSize().width,getSize().height - headingHeight);
		dateTable.setSize(getSize().width - 10,dateCellHeight * ((int)Math.ceil((365+2)/7)+1)); //It sets actual size
		dateTable.setPreferredSize(dateTable.getSize());	//It indicates scroll pane what size is needed
		
		int colWidth = getSize().width / 7;
		int labelWidth = colWidth - 2*padding;
		int x = 0; //This 8 is half of label
		int y = 0;
		for(JLabel dayLabel : dayLabels) {
			dayLabel.setSize(labelWidth,headingHeight);
			dayLabel.setLocation(x+padding, y);
			x = x + colWidth;
		}
		
		x = y = 0;
		int cols = 0;
		for(DateCell dc : dateCells) {
			dc.setLocation(x, y);
			dc.setSize(colWidth,dateCellHeight);
			x = x + colWidth;
			cols++;
			if(cols == 7) { // Max 7 Days
				y = y + dateCellHeight;
				x = 0;
				cols = 0;
			}
		}
	}
	
	/*
	 * Overrides the superclass paint method to ensure dynamic recalculation of
	 * size and location of components before painting.
	 */
	public void paint(Graphics g) {
		placeAndResizeComponents();
		super.paint(g);
		Dimension d = getSize();
		int top = 0;
		int left = 0;
		int right = d.width;
		
		g.setColor(lineColor);
		g.drawLine(left, top, right, top);
		g.drawLine(left, top + headingHeight, right, top + headingHeight);
		
		super.paintChildren(g);
	}
	
}
