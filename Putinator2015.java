package lorenzo;
import robocode.*;
import java.awt.Color;
import java.util.Random;
import java.lang.Math;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Putinator2015 - a robot by (your name here)
 */
public class Putinator2015 extends AdvancedRobot
{
	
	private double moved;
	private double maksimi;
	private int suunta = 1;
	private boolean lockedOn = false;
	//Alternative battleMoven käyttöön koordinaatteja kentästä
	private double width;
	private double height;
	private double xlb; //Kääntymiskohta vasen alakulma clockwise
	private double ylb; //Kääntymiskohta vasen alakulma c-clockwise
	private double ylt; //Kääntymiskohta vasen yläkulma clockwise
	private double xlt; //Kääntymiskohta vasen yläkulma c-clockwise
	private double xrt; //kääntymiskohta oikea yläkulma clockwise
	private double yrt; //Kääntymiskohta oikea yläkulma c-clockwise
	private double yrb; //Kääntymiskohta oikea alakulma clockwise
	private double xrb; //Kääntymiskohta oikea alakulma c-clockwise
	/**
	 * run: Putinator2015's default behavior
	 */
	public void run() {
	width = 700;
	height = 400;
	xlb = width/3;
	ylb = height/3;
	ylt = 2*height/3;
	xlt = xlb;
	xrt = 2*width/3;
	yrt = ylt;
	yrb = ylb;
	xrb = xrt;
	moved = 1;
	maksimi = randomMaksimi();
	
		addCustomEvent(new Condition("triggerHit"){
			public boolean test(){
				return closeToWall();
			}
		});
		
		
		
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		setColors(Color.black,Color.yellow,Color.green); // body,gun,radar

		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			//startBehaviour();
			battleMove2();
		}
	}
	public double randomMaksimi(){
		Random randomno = new Random();
		return 1000*randomno.nextDouble();
		
	}
	
	public boolean closeToWall(){
			if(getHeading() == 90 || getHeading() == 270){
					if(getX() >= xrt || getX() <= xlb){
						return true;
					}else { return false; }
				
				}else if(getHeading() == 0 || getHeading() == 180){
					if(getY() >= 400 || getY() <= yrb){
						return true;
					}else { return false; }
			}
			return false;
		}	

	public void battleMove(){
		while(moved<maksimi){
			setTurnLeft(suunta * 1000);
			setTurnGunLeft(1000);
			ahead(suunta*1000);
			moved=moved+100;
		}
		fire(1);
		moved = 0;
		suunta = suunta*-1;
		
	}
	
	public void battleMove2(){
	while(suunta == 1 && !lockedOn){
	/*	if(getHeading() == 90 || getHeading() == 270){
			if(getX() >= xrt || getX() <= xlb){
				setTurnRight(90);
				setTurnGunRight(180);
			}
		}else if(getHeading() == 0 || getHeading() == 180){
			if(getY() >= 400 || getY() <= yrb){
				setTurnRight(90);
			}
		}*/
		setTurnGunLeft(1000);
		ahead(1000);
	}
		
}
	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		double heading = e.getHeading();
		fire(5);
		
		
	}
	
	public void onCustomEvent(CustomEvent e) {
		// If our custom event "triggerhit" went off,
		if (e.getCondition().getName().equals("triggerHit")) {
			// Adjust the trigger value, or
			// else the event will fire again and again and again...
			setTurnRight(90);
			setTurnGunRight(180);
		}
	}
	
	public void startBehaviour(){
                    //Case alavasen
                    if(getX() < getWidth()/4 && getY() < getHeight()/4 ){
                        if(getHeading() < 225 && getHeading() > 90){
                                turnLeft(getHeading() - 90);                                  
                        }else if(getHeading() < 360 && getHeading() >= 225){
                                turnRight(360-getHeading());  
                        }
                    }
                    // Case ylävasen (kulma wrappaa ympäri)
                    else if(getX() > (3/4)*getWidth()&& getY() < (1/4)*getHeight()){
                        if(getHeading() < 315 &&  getHeading() > 180) {
                            turnLeft(getHeading() - 180);
                        } else if(getHeading() >= 315) {
                            turnRight(360 - getHeading() + 90); //case 315 -- 360
                        } else if(getHeading() < 90) {
                            turnRight(90 - getHeading()); //case 0 -- 90
                        }
                    }
                    //Case alaoikea
                    else if(getX() < (1/4)*getWidth() && getY() > (3/4)*getHeight()){
                        if(getHeading() < 135) {
                            turnLeft(getHeading());
                        } else if(getHeading() >= 135 && getHeading() < 270) {
                            turnRight(270 - getHeading());
                        }
                    }
                    //Case yläoikea (wrappaa ympäri)
                    else if(getX() > (3/4)*getWidth() && getY() > (3/4)*getHeight()){
                        if(getHeading() < 45) {
                            turnLeft(90 + getHeading());
                        } else if(getHeading() > 270) {
                            turnLeft(getHeading() - 270);
                        } else if(getHeading() >= 45 && getHeading() < 180) {
                            turnRight(180 - getHeading());
                        }
                    }
                    //Case yläkeski
                    else if(( getX() >= (1/4)*getWidth() && getX() <= (3/4)*getWidth() ) &&
                            ( getY() > (3/4)*getHeight()) ){
                        if(getHeading() > 270) {
                            turnLeft(getHeading() - 270);
                        } else if(getHeading() < 90) {
                            turnRight(90 - getHeading());
                        }
                    }
                    //Case alakeski
                    else if(( getX() >= (1/4)*getWidth() && getX() <= (3/4)*getWidth() ) &&
                            ( getY() < (1/4)*getHeight()) ) {
                        if(getHeading() > 90 && getHeading() < 180) {
                            turnLeft(getHeading() - 90);
                        } else if(getHeading() < 270 && getHeading() >= 180) {
                            turnRight(270 - getHeading());
                        }
                    }
                    //Case vasenkeski
                    else if(( getX() < (1/4)*getWidth() ) &&
                            ( getY() >= (1/4)*getHeight() && getY() <= (3/4)*getHeight()) ){
                        if(getHeading() < 270 && getHeading() > 180) {
                            turnLeft(getHeading() - 180);
                        } else if(getHeading() >= 270) {
                            turnRight(360 - getHeading());
                        }
                    }
                    //Case oikeakeski
                    else if(( getX() > (3/4)*getWidth() ) &&
                            ( getY() >= (1/4)*getHeight() && getY() <= (3/4)*getHeight()) ){
                        if(getHeading() < 90) {
                            turnLeft(getHeading());
                        } else if(getHeading() >= 90 && getHeading() < 180) {
                            turnRight(180 - getHeading());
                        }
                    }
            }

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		
		if(getHeading() < 90){
			setTurnGunLeft(180);
			back(10);
			turnRight(180-getHeading());
		}else if(getHeading() < 180){
			setTurnGunLeft(180);
			back(10);
			turnRight(270-getHeading());
		}else if(getHeading() < 270){
			setTurnGunLeft(180);
			back(10);
			turnRight(360-getHeading());
		}else{
			setTurnGunLeft(180);
			back(10);
			turnRight(450-getHeading());
		}
		
	}	
}
