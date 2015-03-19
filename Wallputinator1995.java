package lorenzo;
import robocode.*;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

import java.awt.Color;
import java.util.Random;
import java.lang.Math;

public class Wallputinator1995 extends AdvancedRobot{
	boolean peek;
	boolean lockedOn = false;
	double basicMoveAmount;
	int metEnemy;
	int direction = 1;
	boolean ambushed = false;

	

	public void run(){
		setColors(Color.black,Color.yellow,Color.green);
		basicMoveAmount = Math.max(getBattleFieldWidth()+50, 
			getBattleFieldHeight()+50);
		peek = false;
		
		//Tämä hoitaa käytännössä seinän etsimisen.
		turnGunRight(90);
		findWall();
		
		while(true){
			
			peek = true;
			ahead(direction*basicMoveAmount);
			peek = false;
			//turnRight(direction*90);
		}
	}
	
	public void findWall(){
		turnLeft(getHeading()%90);
		ahead(basicMoveAmount);
	}
	
	public void onHitRobot(HitRobotEvent e){
		
		turnLeft(-e.getBearing());
		direction = -direction;
		fire(6);
		findWall();
	}
	
	public void onHitByBullet(HitByBulletEvent shot){
		if(lockedOn){
			if(shot.getBearing() > getHeading()+45 && shot.getBearing()< getHeading()+135){
				out.println("Being fired from front");
			}else{
				ambushed = true;
				turnRadarLeft(360);
				out.println("Being ambushed!");
				
			}
		}
	}
	
	public void onHitWall(HitWallEvent e){
		back(direction*-1);
		turnRight(90);
	}
	/**
	 * Scannerin ideana on lukittua viholliseen, katsoa vieläkö tämä on tutkan sisällä
	 * Ja hallita tilannetta.
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		if(ambushed){
			ambushed = false;
			findWall();
		}
		lockedOn = true;
		while(lockedOn){
			fire(4);
			ahead(direction*70);
			ahead(direction*-70);
			lockedOn = false;
		}
	}

}