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
	boolean justStarted = true;
	boolean turning = false;
	boolean wallhit = false;

	

	public void run(){
		addCustomEvent(new Condition("gunAtWall"){
			public boolean test(){
				out.println("Testing gun");
				return pointingGunAtWall();
			}
		});
		
		setColors(Color.black,Color.yellow,Color.green);
		basicMoveAmount = Math.max(getBattleFieldWidth()-50, 
			getBattleFieldHeight()-50);
		peek = false;
		
		//Tämä hoitaa käytännössä seinän etsimisen.
		turnGunRight(90);
		findWall();
		
		while(true){
			justStarted = false;
			turning = false;
			peek = true;
			ahead(direction*basicMoveAmount);
			peek = false;
			//turnRight(direction*90);
		}
	}
	//Condition jonka avulla käännetään ase mikäli se sattuu seinää tuijottamaan.
	public boolean pointingGunAtWall(){
		if(direction == 11 && getGunHeading()%getHeading()-90==0){
			out.println("tested : true : " + direction);
			return true;
		}else if(direction ==-1 && getGunHeading()%getHeading()+90==0){
			out.println("tested : true : " + direction);
			return true;
		}else{
			out.println("tested : false");
			return false;
		}
	}
	
	//Ylemmän kondition toteutuessa toteutetaan seuraava koodi
	public void onCustomEvent(CustomEvent e){
		if(e.getCondition().getName().equals("gunAtWall")){
			out.println("Rotating this bad motherfucker.");
			turnGunRight(-direction*180);
		}
	}
	
	//Jos robotti löytää idiootteja jotka vaan lääppii seinää pitkin ja ampuu keskelle,
	//Se räiskii niitä kunnes ne kuolee.
	public void foundFuckingWallhuggers(ScannedRobotEvent e){
		fire(20);
		direction = -direction;
	}
	
	public void findWall(){
		turnLeft(getHeading()%90);
		ahead(direction*basicMoveAmount);
	}
	
	public void onHitRobot(HitRobotEvent e){
		out.println("Hit another robot, must escape : "+ -direction);
		turnLeft(-e.getBearing());
		direction = -direction;
		fire(6);
		findWall();
	}
	//Tutkii robotin taistelutilannetta ja lähettää sen karkuun mikäli tilanne on robotille huono.
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
		if((shot.getBearing()+180)%getHeading()==0){
			wallhit = true;
			if(wallhit){
			turnLeft(-direction*90);
		
			ahead(direction*basicMoveAmount);
			wallhit = false;
			}
			//findWall();
			//fire(7);
		}
	}
	//Robotin kääntely toimii käytännössä tällä metodilla.
	public void onHitWall(HitWallEvent e){
		back(direction*-1);
		turning = true;
		turnRight(direction*90);
		turnGunLeft(direction*90);
		turnGunRight(direction*180);
		turnGunLeft(direction*90);
		
	}
	/**
	 * Scannerin ideana on lukittua viholliseen, katsoa vieläkö tämä on tutkan sisällä
	 * Ja hallita tilannetta.
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		if(justStarted){
			findWall();
		}
		
		if(turning && e.getHeading()+180%getHeading()==0){
			foundFuckingWallhuggers(e);
			turning = false;
		}else if(turning){
			turning = false;
		}
		

	
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