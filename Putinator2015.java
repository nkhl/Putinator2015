    

package digittiteenirobo;
 
 
    import robocode.*;
    import java.awt.Color;
    import java.util.Random;
    import java.lang.String;
     
    // API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html
     
    /**
     * Putinator2015 - a robot by (your name here)
     */
    public class Putinator2015 extends AdvancedRobot
    {
            private class enemyInfo
                        {
                                private String name;
                                private double bearing;
                                private double distance;
                                private double heading;
                                private double velocity;
                                enemyInfo(ScannedRobotEvent e) {
                                        name = e.getName();
                                }
                                public String getName() { return name; }
                        }
            private double moved = 0;
                        private int direction = 1;
            /**
             * run: Putinator2015's default behavior
             */
            public void run() {
                    // Initialization of the robot should be put here
     
                    // After trying out your robot, try uncommenting the import at the top,
                    // and the next line:
     
                    setColors(Color.red,Color.blue,Color.green); // body,gun,radar
     
                    // Robot main loop
                    while(true) {
                            // Replace the next 4 lines with any behavior you would like
                            if(getVelocity() == 0) move();
                                                        setTurnGunLeft(1000);
                                                        execute();
                            //scanForRobot();
                    }
            }
                        public void move() {
                                //direction *= -1;
                                startBehaviour();
                                setAhead(80);
                        }
            public void scanForRobot(){
                   
                    if(moved>100){
                            moved = 0;
                            vasenKaari();
                            System.out.println(moved);
                    }
                    oikeaKaari();
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
           
            public void oikeaKaari(){
                    setTurnRight(getHeading());
                    setTurnGunLeft(1000);
                    ahead(1000);
                   
                    moved+=10;
            }
            public void vasenKaari(){
                    setTurnLeft(1000);
                    setTurnGunLeft(1000);
                    back(1000);
                    moved+=1;
            }
           
            public void checkTurn(double x, double y){
                    setTurnLeft(100);
                   
                    setTurnRight(100);
            }
           
            public void checkForSeina(double x, double y){
                    //adsadas
            }
            /**
             * onScannedRobot: What to do when you see another robot
             */
            public void onScannedRobot(ScannedRobotEvent e) {
                    // Replace the next line with any behavior you would like
                    fire(5);
                   
                   
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
                    turnLeft(90);
                    moved = 0;
                   
            }      
    }


