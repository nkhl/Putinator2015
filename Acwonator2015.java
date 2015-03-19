    

package digittiteenirobo;
 
 
    import robocode.*;
    import robocode.util.Utils.*;
    import java.awt.Color;
    import java.util.Random;
    import java.lang.String;
     
    // API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html
     
    /**
     * Acwonator - a robot by (your name here)
     */
    public class Acwonator2015 extends AdvancedRobot
    {
             
            //Enemy tracking
            private String enemy_name = "";
            private double enemy_bearing = 0;
            private double enemy_energy = 0;
            private double enemy_distance = 0;
            private double enemy_heading = 0;
            private double enemy_velocity = 0;
            private int enemy_x = 0;
            private int enemy_y = 0;


            private boolean forward = true;
            private int moveTowardsDir = 1;
            private int direction = 1;
            private boolean turning = false;
            private int gunScanAmount = 360; //Pyyhkäyisyn koko
            private int gunScanCurrent = 360; //Tämän hetkinen edistyminen
            private int gunScanDir = 1;      //Pyyhkäysin suunta
            private boolean gunScanned = false; // tuliko valmista?
            private long deltaTime;
            private long nowTime;
            private Random rng;

            //Tähtäilymuutujia
            private int firingTime = 1000;
            private int firepower = 1;
            private long lastSeen = 100000;
            /**
             * run: Putinator2015's default behavior
             */
            public void run() {
                // Initialization of the robot should be put here
     
                // After trying out your robot, try uncommenting the import at the top,
                // and the next line:
     
                setColors(Color.red,Color.blue,Color.green); // body,gun,radar
                setAdjustGunForRobotTurn(true); //Ase ei pyöri robotin mukana
                setAdjustRadarForRobotTurn(true); //Tutka ei pyöri robotin mukana
                setAdjustRadarForGunTurn(true);   // Tutka ei pyöri aseen mukana
                rng = robocode.util.Utils.getRandom();
                deltaTime = System.nanoTime() + 1000000000L*((long)rng.nextInt(10)) + 3000000000L; //Time now + 10-0s + 3s
                // Robot main loop
                while(true) {
                    // Replace the next 4 lines with any behavior you would like
                    //if(getVelocity() == 0) move();
                    
                    //nowTime = System.nanoTime();
                    //if(nowTime > deltaTime)
                    //{
                    //        swapDirection();
                    //        //deltaTime = nowTime + ((long) (10000000000*rng.nextDouble())) + 300000000; //Time now + 10-0s + 3s
                    //        deltaTime = nowTime + 1000000000L*((long)rng.nextInt(10)) + 1000000000L; //Time now + 10-0s + 3s
                    //}
                    move();
                    scan();
                    if(firingTime == getTime() && getGunTurnRemaining() == 0)
                            setFire(firepower);
                    execute();
                    setDebugProperty("enemyName", enemy_name);
                    setDebugProperty("enemyBearing", String.valueOf(enemy_bearing));
                    setDebugProperty("enemyEnergy", String.valueOf(enemy_energy));
                    setDebugProperty("enemyDistance", String.valueOf(enemy_distance));
                    setDebugProperty("enemyHeading", String.valueOf(enemy_heading));
                    setDebugProperty("enemyVelocity", String.valueOf(enemy_velocity));
                }
            }
            public void move() {
                setDebugProperty("enemyBearing", String.valueOf(enemy_bearing));
                int towardsWeight = 30;
                if(enemy_distance < 200){
                        moveTowardsDir = -1;
                        towardsWeight = 50 - ((int)enemy_distance/200)*20;
                }
                else if(enemy_distance > 500){
                        moveTowardsDir = 1;
                        towardsWeight = 20;
                }

                if(enemy_bearing > 0)
                    setTurnRight(enemy_bearing + 90 + towardsWeight*moveTowardsDir);
                else
                    setTurnLeft(-enemy_bearing + 90 + towardsWeight*moveTowardsDir);
                if(forward) setAhead(250);
                else setBack(250);
            }
            public void scan() {
                if(enemy_name.equals("") || lastSeen > getTime() + 30) setTurnRadarRight(30);
                else {
                    //Acwocode
                    double position;
                    double turn;
                    double bears;
                    turn = enemy_bearing + (getHeading() - getRadarHeading());
                    if(turn > 180) turn = turn - 360;
                    //if(enemy_bearing < 0) bears = 360 - enemy_bearing;
                    //else bears = enemy_bearing;
                    //
                    //position = getHeading() + bears;
                    //turn = position - getGunHeading();

                    setTurnRadarRight(turn);
                    //Acwocode

                }
            }
            /**
             * onScannedRobot: What to do when you see another robot
             */
            public void onScannedRobot(ScannedRobotEvent e) {
                    // Replace the next line with any behavior you would like
                    // Lukittuu lähimpään viholliseen
                    if(enemy_name.equals("")) {
                        refresh_enemy(e);
                        lastSeen = getTime();
                        doFire();
                    } else if(e.getName().equals(enemy_name)) {
                        refresh_enemy(e);
                        lastSeen = getTime();
                        //newScan(10,gunScanDir); 
                        doFire();
                    } else if(e.getDistance() < enemy_distance) {
                        refresh_enemy(e);
                        lastSeen = getTime();
                        doFire();
                    }
                    
                   // calculateAim();
                   // firepower = 1;
                   // if(getEnergy() > 40){ 
                   //     if(enemy_distance < 400) {
                   //         firepower = (int) ( 8 - (enemy_distance/400)*8);
                   //         fire(firepower);
                   //     }
                   //    //Älä ammu liian kauas jos on vähän HP:ta
                   // } else {
                   //     if(enemy_distance < 250) {
                   //         firepower = (int) ( 8 - (enemy_distance/400)*4);
                   //         fire(firepower);
                   //     }
                   // }

            }
            public void doFire() {
                    //Acwocode
                    double position;
                    double turn;
                    double bears;
                   // if(enemy_bearing < 0) bears = 360 - enemy_bearing;
                   // else bears = enemy_bearing;
                   //  //position = getHeading() + e.getBearing();
                   //  position = getHeading() + bears;
                   //  turn = position - getGunHeading();
                    // if(getGunHeading() % 90 == 0){
                    //         fire(3);
                    // }
                    turn = enemy_bearing + (getHeading() - getGunHeading());
                    if(turn > 180) turn = turn - 360;
                    //turn = normalRelativeAngleDegrees(enemy_bearing + (getHeading() - getGunHeading()));
                     if(Math.abs(turn) <= 1){
                             setTurnGunRight(turn);
                             if(getGunHeat() == 0){
                                     if(enemy_distance < 150.0){
                                             fire(Rules.MAX_BULLET_POWER);
                                     }
                                     else{
                                             fire(1);
                                     }
                             }
                     }
                     else{
                             setTurnGunRight(turn);
                     }
                    //Acwocode
            }
            public void calculateAim() {
                    //TODO
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
                    enemy_velocity  = e.getVelocity();

                    //enemy_x = Math.cos(enemy_bearing
           }
            public void reset_enemy() {
                    enemy_name = new String("");
                    enemy_bearing  = 0;
                    enemy_energy  = 0;
                    enemy_distance  = 0;
                    enemy_heading  = 0;
                    enemy_velocity  = 0;

                    //enemy_x = Math.cos(enemy_bearing
           }
           public void onRobotDeath(RobotDeathEvent e){
               if(e.getName().equals(enemy_name)){
                   reset_enemy();
               }
           }
    }


