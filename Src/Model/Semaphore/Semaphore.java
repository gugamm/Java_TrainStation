package Model.Semaphore;

import java.awt.Color;
import java.awt.Point;
import java.util.Observable;

public class Semaphore extends Observable{
	/**********************CONSTANT DECLARATION**********************/
	private static final int DEFAULT_WIDTH = 42;
	private static final int DEFAULT_HEIGHT = 42;

	/**********************PRIVATE VARIABLE DECLARATION**********************/
	private SemaphoreState state;
	private int width;
	private int height;
	private Point position;
	private Color color;
	
	/**********************PRIVATE METHODS DECLARATION**********************/
	private void updateColor() {
		switch(state) {
		case open : color = Color.GREEN;
			break;
		case closed : color = Color.RED;
			break;
		}
	}
	
	private void notifyObs() {
		setChanged();
		notifyObservers();
	}
	
	/**********************PUBLIC METHODS DECLARATION**********************/
	public Semaphore(SemaphoreState state, int width, int height, Point position) {
		this.state = state;
		this.width = width;
		this.height = height;
		this.position = position;
		updateColor();
	}
	
	public Semaphore(SemaphoreState state, Point position) {
		this(state,DEFAULT_WIDTH,DEFAULT_HEIGHT,position);
	}
	
	public SemaphoreState changeState() {
		switch(state) {
		case open : state = SemaphoreState.closed;
			break;
		case closed : state = SemaphoreState.open;
			break;
		}
		updateColor();
		notifyObs();
		return state;
	}
	
	public void setState(SemaphoreState state) {
		this.state = state;
		updateColor();
		notifyObs();
	}
	
	public SemaphoreState getState() {
		return state;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		notifyObs();
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		notifyObs();
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
		notifyObs();
	}
	
	public Color getColor() {
		return this.color;
	}
}
