    

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
            private class EnemyInfo
                        {
                                private String name;
                                private double bearing;
                                private double energy;
                                private double distance;
                                private double heading;
                                private double velocity;

                                public EnemyInfo() {
                                    name = "";   bearing = 0;
                                    energy = 0;  distance = 0;
                                    heading = 0; velocity = 0;
                                }
                                
                                public String getName() { return name; }
                                public double getBearing() { return bearing; }
                                public double getEnergy() { return energy;}
                                public double getDistance() { return distance; }
                                public double getHeading() { return heading; }
                                public double getVelocity() { return velocity; }
                                public void refresh(ScannedRobotEvent e) {
                                        name = e.getName();
                                        bearing  = e.getBearing();
                                        energy  = e.getEnergy();
                                        distance  = e.getDistance();
                                        heading  = e.getHeading();
                                        velocity  = e.getBearing();
                                }
                                //TODO laske koordinaatit ennakointia varten
                        }
            private double moved = 0;
                        private int direction = 1;
                        private boolean turning = false;
                        private EnemyInfo currentEnemy = new EnemyInfo();
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
                            //if(getVelocity() == 0) move();
                            move();
                                                        setTurnGunLeft(10);
                                                        execute();
                                                        while(getDistanceRemaining() > 0 && getTurnRemaining() > 0) {
                                                            execute();
                                                        }
                                                        turning = false;
                            //scanForRobot();
                    }
            }
                        public void move() {
                                //direction *= -1;
                                if(turning == false) startBehaviour();
                                turning = true; 
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
                    setDebugProperty("Kääntö","-");
                    setDebugProperty("Battlefield_x",String.valueOf(getBattleFieldWidth()));
                    setDebugProperty("Battlefield_y",String.valueOf(getBattleFieldHeight()));
                    setDebugProperty("Condishun", String.valueOf((getX() < ((1/4)*getBattleFieldWidth())) && (getY() < ((1/4)*getBattleFieldHeight()))));
                    //Case alavasen
                    if( (getX() < ((1/4)*getBattleFieldWidth())) && (getY() < ((1/4)*getBattleFieldHeight())) ){
                        setDebugProperty("Kääntö case","alavasen");
                        if(getHeading() < 225 && getHeading() > 90){
                                setTurnLeft(getHeading() - 90); 
                            setDebugProperty("Kääntö vasen",String.valueOf(getHeading()-90));
                        }else if(getHeading() < 360 && getHeading() >= 225){
                                setTurnRight(360 - getHeading());  
                            setDebugProperty("Kääntö oikea",String.valueOf(360-getHeading()));
                        }
                    }
                    // Case ylävasen (kulma wrappaa ympäri)
                    else if(getX() < (1/4)*getBattleFieldWidth() && getY() > (3/4)*getBattleFieldHeight()){
                        if(getHeading() < 315 &&  getHeading() > 180) {
                            setTurnLeft(getHeading() - 180);
                        } else if(getHeading() >= 315) {
                            setTurnRight(360 - getHeading() + 90); //case 315 -- 360
                        } else if(getHeading() < 90) {
                            setTurnRight(90 - getHeading()); //case 0 -- 90
                        }
                    }
                    //Case alaoikea
                    else if(getX() > (3/4)*getBattleFieldWidth() && getY() < (1/4)*getBattleFieldHeight()){
                        if(getHeading() < 135) {
                            setTurnLeft(getHeading());
                        } else if(getHeading() >= 135 && getHeading() < 270) {
                            setTurnRight(270 - getHeading());
                        }
                    }
                    //Case yläoikea (wrappaa ympäri)
                    else if(getX() > (3/4)*getBattleFieldWidth() && getY() > (3/4)*getBattleFieldHeight()){
                        if(getHeading() < 45) {
                            setTurnLeft(90 + getHeading());
                        } else if(getHeading() > 270) {
                            setTurnLeft(getHeading() - 270);
                        } else if(getHeading() >= 45 && getHeading() < 180) {
                            setTurnRight(180 - getHeading());
                        }
                    }
                    //Case yläkeski
                    else if(( getX() >= (1/4)*getBattleFieldWidth() && getX() <= (3/4)*getBattleFieldWidth() ) &&
                            ( getY() > (3/4)*getBattleFieldHeight()) ){
                        if(getHeading() > 270) {
                            setTurnLeft(getHeading() - 270);
                        } else if(getHeading() < 90) {
                            setTurnRight(90 - getHeading());
                        }
                    }
                    //Case alakeski
                    else if(( getX() >= (1/4)*getBattleFieldWidth() && getX() <= (3/4)*getBattleFieldWidth() ) &&
                            ( getY() < (1/4)*getBattleFieldHeight()) ) {
                        if(getHeading() > 90 && getHeading() < 180) {
                            setTurnLeft(getHeading() - 90);
                        } else if(getHeading() < 270 && getHeading() >= 180) {
                            setTurnRight(270 - getHeading());
                        }
                    }
                    //Case vasenkeski
                    else if(( getX() < (1/4)*getBattleFieldWidth() ) &&
                            ( getY() >= (1/4)*getBattleFieldHeight() && getY() <= (3/4)*getBattleFieldHeight()) ){
                        if(getHeading() < 270 && getHeading() > 180) {
                            setTurnLeft(getHeading() - 180);
                        } else if(getHeading() >= 270) {
                            setTurnRight(360 - getHeading());
                        }
                    }
                    //Case oikeakeski
                    else if(( getX() > (3/4)*getBattleFieldWidth() ) &&
                            ( getY() >= (1/4)*getBattleFieldHeight() && getY() <= (3/4)*getBattleFieldHeight()) ){
                        if(getHeading() < 90) {
                            setTurnLeft(getHeading());
                        } else if(getHeading() >= 90 && getHeading() < 180) {
                            setTurnRight(180 - getHeading());
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
                    // Lukittuu lähimpään viholliseen
                    if(e.getName().equals(currentEnemy.getName())) {
                        currentEnemy.refresh(e);
                    } else if(e.getDistance() < currentEnemy.getDistance()) {
                        currentEnemy.refresh(e);
                    }

                    //TODO ammu lähempänä olevaa lujempaa?
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
                   // moved = 0;
                   
            }      
    }


