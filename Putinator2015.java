    

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
            private int gunScanLeft = 360;
            private int gunScanDir = 1;
            private boolean gunScanned = false;
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
                    scan();
                    execute();
                }
            }
            public void move() {
                int enemyBearing = currentEnemy.getBearing();
                if(enemyBearing > 0)
                    setTurnRight(enemyBearing + 90);
                else
                    setTurnLeft(-enemyBearing + 90);
                setAhead(250);
            }
            public void scan() {
                while(gunScanLeft
                setTurnGunRight()
            }
            /**
             * onScannedRobot: What to do when you see another robot
             */
            public void onScannedRobot(ScannedRobotEvent e) {
                    // Replace the next line with any behavior you would like
                    // Lukittuu lähimpään viholliseen
                    if(e.getName().equals("")) {
                        currentEnemy.refresh(e);

                    } else if(e.getName().equals(currentEnemy.getName())) {
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


