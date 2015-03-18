    

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
             
            //Enemy tracking
            private String enemy_name = "";
            private double enemy_bearing = 0;
            private double enemy_energy = 0;
            private double enemy_distance = 0;
            private double enemy_heading = 0;
            private double enemy_velocity = 0;


            private boolean forward = true;
            private int direction = 1;
            private boolean turning = false;
            private int gunScanAmount = 360; //Pyyhkäyisyn koko
            private int gunScanCurrent = 360; //Tämän hetkinen edistyminen
            private int gunScanDir = 1;      //Pyyhkäysin suunta
            private boolean gunScanned = false; // tuliko valmista?
            private long deltaTime;
            private long nowTime;
            private Random rng;
            /**
             * run: Putinator2015's default behavior
             */
            public void run() {
                // Initialization of the robot should be put here
     
                // After trying out your robot, try uncommenting the import at the top,
                // and the next line:
     
                setColors(Color.red,Color.blue,Color.green); // body,gun,radar
                setAdjustGunForRobotTurn(true); //Ase ei pyöri robotin mukana
                rng = robocode.util.Utils.getRandom();
                deltaTime = System.nanoTime() + ((long) (1000000000*rng.nextDouble())) + 30000000; //Time now + 10-0s + 3s
                // Robot main loop
                while(true) {
                    // Replace the next 4 lines with any behavior you would like
                    //if(getVelocity() == 0) move();
                    
                    nowTime = System.nanoTime();
                    if(nowTime > deltaTime)
                    {
                            swapDirection();
                            deltaTime = nowTime + ((long) (1000000000*rng.nextDouble())) + 30000000; //Time now + 10-0s + 3s
                    }
                    move();
                    //scan();
                    setTurnGunRight(1000);
                    execute();
                    setDebugProperty("enemyName", enemy_name);
                }
            }
            public void move() {
                setDebugProperty("enemyBearing", String.valueOf(enemy_bearing));
                if(enemy_bearing > 0)
                    setTurnRight(enemy_bearing + 90);
                else
                    setTurnLeft(-enemy_bearing + 90);
                if(forward) setAhead(250);
                else setBack(250);
            }
            public void scan() {
                    double current = getGunTurnRemaining();
                    if(current == 0)
                    {
                        newScan((int)(gunScanAmount*1.5), -1*gunScanDir);
                        setTurnGunRight(gunScanCurrent*gunScanDir);
                    }
                    else 
                        setTurnGunRight(current*gunScanDir);
            }
            /**
             * onScannedRobot: What to do when you see another robot
             */
            public void onScannedRobot(ScannedRobotEvent e) {
                    // Replace the next line with any behavior you would like
                    // Lukittuu lähimpään viholliseen
                    if(enemy_name.equals("")) {
                        refresh_enemy(e);

                    } else if(e.getName().equals(enemy_name)) {
                        refresh_enemy(e);
                        newScan(10,gunScanDir); 
                    } else if(e.getDistance() < enemy_distance) {
                        refresh_enemy(e);
                    }
                    
                    //TODO ammu lähempänä olevaa lujempaa?
                    int firepower = 1;
                    if(enemy_distance < 400)
                            firepower = (int) ( 8 - (enemy_distance/400)*8);
                    fire(firepower);
                   
                   
            }

            public void newScan(int amount, int dir)
            {
                gunScanAmount = amount;
                gunScanCurrent = amount;
                gunScanDir = dir;
                gunScanned = false;
            }
     
            /**
             * onHitByBullet: What to do when you're hit by a bullet
             */
            public void onHitByBullet(HitByBulletEvent e) {
                    // Replace the next line with any behavior you would like
                    swapDirection();
            }
            public void swapDirection() {
                    if(forward) forward = false;     
                    else forward = true;
            }
           
            /**
             * onHitWall: What to do when you hit a wall
             */
            public void onHitWall(HitWallEvent e) {
                    // Replace the next line with any behavior you would like
                   //turnLeft(90);
                   swapDirection();
                   // moved = 0;
                   
            }      
            public void refresh_enemy(ScannedRobotEvent e) {
                    enemy_name = new String(e.getName());
                    enemy_bearing  = e.getBearing();
                    enemy_energy  = e.getEnergy();
                    enemy_distance  = e.getDistance();
                    enemy_heading  = e.getHeading();
                    enemy_velocity  = e.getBearing();
           }
    }


