package View;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import Controller.InterfaceController;
import Model.Train.TrainDirection;

@SuppressWarnings("serial")
public class TrainInterfacePanel extends JPanel{
	private static final int DEFAULT_RIGHT_X = 19;
	private static final int DEFAULT_RIGHT_Y = 247;
	private static final int DEFAULT_LEFT_X = 763;
	private static final int DEFAULT_LEFT_Y = 338;
	
	private JLabel lblDirection;
	private JComboBox<String> lstDirection;
	private JLabel lblSpeed;
	private JComboBox<String> lstSpeed;
	private JButton btnAdd;
	private JButton btnReset;
	private String speeds[] = {"40","50","60"};
	private String directions[] = {"LEFT", "RIGHT"};
	
	private InterfaceController controller;
	/**********************PUBLIC METHODS DECLARATION**********************/
	public TrainInterfacePanel(InterfaceController controller) {
		SpringLayout layoutManager = new SpringLayout();
		setLayout(layoutManager);
		
		//Create direction pane
		lblDirection = new JLabel("Direction");
		add(lblDirection);
		
		layoutManager.putConstraint(SpringLayout.WEST, lblDirection, 5, SpringLayout.WEST, this);
		layoutManager.putConstraint(SpringLayout.NORTH, lblDirection, 5, SpringLayout.NORTH, this);
		
		lstDirection = new JComboBox<String>(directions);
		add(lstDirection);
		
		layoutManager.putConstraint(SpringLayout.WEST, lstDirection, 5, SpringLayout.EAST, lblDirection);
		layoutManager.putConstraint(SpringLayout.NORTH, lstDirection, 5, SpringLayout.NORTH, this);
		
		lblSpeed = new JLabel("Speed");
		add(lblSpeed);
		
		layoutManager.putConstraint(SpringLayout.WEST, lblSpeed, 5, SpringLayout.WEST, this);
		layoutManager.putConstraint(SpringLayout.NORTH, lblSpeed, 5, SpringLayout.SOUTH, lstDirection);
		
		lstSpeed = new JComboBox<String>(speeds);
		add(lstSpeed);
		
		layoutManager.putConstraint(SpringLayout.WEST, lstSpeed, 5, SpringLayout.EAST, lblDirection);
		layoutManager.putConstraint(SpringLayout.NORTH, lstSpeed, 5, SpringLayout.SOUTH, lstDirection);
		layoutManager.putConstraint(SpringLayout.EAST, lstSpeed, 0, SpringLayout.EAST, lstDirection);
		
		btnAdd = new JButton("ADD");
		add(btnAdd);
		
		layoutManager.putConstraint(SpringLayout.WEST, btnAdd, 5, SpringLayout.WEST, this);
		layoutManager.putConstraint(SpringLayout.NORTH, btnAdd, 5, SpringLayout.SOUTH, lstSpeed);
		
		btnReset = new JButton("RESET");
		add(btnReset);
		
		layoutManager.putConstraint(SpringLayout.WEST, btnReset, 5, SpringLayout.EAST, btnAdd);
		layoutManager.putConstraint(SpringLayout.NORTH, btnReset, 5, SpringLayout.SOUTH, lstSpeed);
		
		layoutManager.putConstraint(SpringLayout.EAST, this, 5, SpringLayout.EAST, lstDirection);
		layoutManager.putConstraint(SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH, btnAdd);
		
		this.controller = controller;
		this.controller.setView(this);
		addEventListeners();
	}
	
	private TrainDirection getTrainDirection() {
		String selDirection = (String)lstDirection.getSelectedItem();
		if (selDirection == "RIGHT")
			return TrainDirection.right;
		return TrainDirection.left;
	}
	
	private int strToInt(String string, int defaultValue) {
		int speed;
		try {
			speed = Integer.parseInt(string);
		} catch (NumberFormatException e) {
			speed = defaultValue;
		}
		return speed;
	}
	
	private int getTrainSpeed() {
		String selSpeed = (String)lstSpeed.getSelectedItem();
		return strToInt(selSpeed,40);
	}
	
	private Point getTrainPosition(TrainDirection direction) {
		switch (direction) {
		case right : return new Point(DEFAULT_RIGHT_X,DEFAULT_RIGHT_Y);
		case left  : return new Point(DEFAULT_LEFT_X,DEFAULT_LEFT_Y);
		default    : return new Point(DEFAULT_RIGHT_X,DEFAULT_RIGHT_Y);
		}
	}

	private void addEventListeners() {
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TrainDirection direction = getTrainDirection();
				controller.addTrain(direction, getTrainSpeed(), getTrainPosition(direction));
			}
		});
		
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lstDirection.setSelectedIndex(0);
				lstSpeed.setSelectedIndex(0);
			}
		});
	}
	
	
	
}
