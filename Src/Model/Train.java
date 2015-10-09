package Model;

import java.awt.Color;
import java.awt.Point;
import java.util.Observable;

public class Train extends Observable implements Runnable{
	/**********************CONSTANT DECLARATION**********************/
	private static final int DEFAULT_WIDTH = 22;
	private static final int DEFAULT_HEIGHT = 22;

	/**********************PRIVATE VARIABLE DECLARATION**********************/
	private Point position;
	private TrainDirection direction;
	private int width;
	private int height;
	private int speedX;
	private int endOfLine;
	private Color color;
	
	/**********************PRIVATE METHODS DECLARATION**********************/
	private void updateColor() {
		switch(direction) {
		case left : color = Color.RED;
			break;
		case right : color = Color.BLACK;
			break;
		}
	}
	
	private boolean hasReachEndOfLine() {
		boolean result = true;
		switch(direction) {
		case left : result = (position.x <= endOfLine);
			break;
		case right : result = (position.x > endOfLine);
			break;
		}
		return result;
	}
	
	private void notifyObs() {
		setChanged();
		notifyObservers();
	}
	
	private int getSpeedYByStepLeft(int step, int speedY) {
		int speedVert = speedY;
		if (step == 0) {
			if (position.x <= 684) {
				speedVert = calculateSpeedY(position.x, position.y, 578, 282);
			}
		} else if (step == 1) {
			if (position.y <= 282) {
				speedVert = 0;
			}
		} else if (step == 2) {
			if (position.y <= 282 && position.x <= 235)  {
				speedVert = -calculateSpeedY(position.x, position.y, 100, 339);
			}
		} else if (step == 3) {
			if (position.y >= 330 && position.x <= 105) { 
				speedVert = 0;
			}
		}
		return speedVert;
	}
	
	private int getSpeedYByStepRight(int step, int speedY) {
		int speedVert = speedY;
		if (step == 0) {
			if (position.x >= 100) {
				speedVert = -calculateSpeedY(position.x, position.y, 223, 283);
			}
		} else if (step == 1) {
			if (position.y >= 283 && position.x >= 223) {
				speedVert = 0;
			}
		} else if (step == 2) {
			if (position.x >= 564 && position.y > 282)  {
				speedVert = calculateSpeedY(position.x, position.y, 683, 250);
			}
		} else if (step == 3) {
			if (position.x >= 683 && position.y <= 250) { 
				speedVert = 0;
			}
		}
		return speedVert;
	}
	
	private int getSpeedYByStep(int step, int speedY) {
		switch(direction) {
		case right : return getSpeedYByStepRight(step,speedY);
		case left : return getSpeedYByStepLeft(step,speedY);
		}
		return 0;
	}
	
	/**********************PUBLIC METHODS DECLARATION**********************/
	public Train(TrainDirection direction, int endOfLine, int speed, int width, int height, Point position) {
		this.position = position;
		this.direction = direction;
		this.width = width;
		this.height = height;
		this.endOfLine = endOfLine;
		this.speedX = speed/5;
		updateColor();
	}
	
	public Train(TrainDirection direction, int endOfLine, int speed, int width, int height, int x, int y) {
		this(direction,endOfLine,speed,width,height,new Point(x,y));
	}
	
	public Train(TrainDirection direction, int endOfLine, int speed, int x, int y) {
		this(direction,endOfLine,speed,DEFAULT_WIDTH, DEFAULT_HEIGHT,new Point(x,y));
	}
	
	public Train(TrainDirection direction, int endOfLine, int speed, Point position) {
		this(direction,endOfLine,speed,DEFAULT_WIDTH,DEFAULT_HEIGHT,position);
	}
	
	public int calculateSpeedY(int xBegin, int yBegin, int xEnd, int yEnd) {
		int distanceX = Math.abs(xBegin - xEnd);
		int distanceY = Math.abs(yBegin - yEnd);
		int timeX = distanceX/speedX;
		return distanceY/timeX;
	}
	
	@Override
	public void run() {
		int speedVert = 0;
		int speedHorz;
		int step = 0;
		int sbs = 0;
		switch(direction) {
		case left : speedHorz = -speedX;
			break;
		case right: speedHorz = speedX;
			break;
		default: speedHorz = speedX;
			break;
		}
		while (!hasReachEndOfLine()) {
			sbs = getSpeedYByStep(step,speedVert);
			if (speedVert != sbs) {
				step++;
				speedVert = sbs;
			}
			setPosition(position.x + speedHorz, position.y - speedVert); //This automatically notify observers
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		setChanged();
		notifyObservers("Finished");
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public TrainDirection getDirection() {
		return direction;
	}
	
	public int getSpeed() {
		return speedX;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setWidth(int width) {
		this.width = width;
		notifyObs();
	}
	
	public void setHeight(int height) {
		this.height = height;
		notifyObs();
	}
	
	public void setPosition(int x, int y) {
		position.x = x;
		position.y = y;
		notifyObs();
	}
	
	public void setPosition(Point p) {
		this.setPosition(p.x,p.y);
		notifyObs();
	}
	
	public void setDirection(TrainDirection direction) {
		this.direction = direction;
		updateColor();
		notifyObs();
	}
	
	public void setSpeed(int speed) {
		this.speedX = speed;
		notifyObs();
	}
}
