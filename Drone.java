import java.awt.*;

public class Drone implements Contract{//implement the contract
    public String name;
    public int age;
    public boolean status;
    private Point current_location;
    public int Battery;
    public int current_height;

    /* constructor of the Drone */
    public Drone(String name, int age, boolean status, Point location, int Battery, int height) {
        this.name = name;
        this.age = age;
        this.status = status;
        this.current_location = new Point(0, 0);
        this.Battery = Battery;
        this.current_height = height;
    }

    /*
     * grab the item
     * @param item
     */
    public void grab(String item) {
        System.out.println(name + " grabs a" + item);
    }

    /*
     * drop the item
     * @param item
     */
    public String drop(String item) {
        System.out.println(name + " drops a" + item);
        return item;
    }

    /*
     * examine whether we are controlling the targeted drone
     * @param item
     */
    public void examine(String item) {
        if (item.equals(name)){
            use(item);
        } else{
            throw new RuntimeException("You have connected to the wrong drone.");
        }
    }

    /*
     * start the drone
     */
    public void use(String item){
        System.out.println(item +" started.");
    }
    
    /*
     * check to see if we can still go forward on the direction from user's input
     * Our limited zone is -100 to 100 on x-axis and -100 to 100 on y-axis
     * if the drone is already on the edge of our zone we can't walk anymore
     * @param direction
     * return whether the drone can walk
     */
    public boolean walk(String direction) {
        boolean canWalk = true;
        if (direction.equals("left")) {
            double x = current_location.getX();
            if(x==-100){
                canWalk = false;
            } 
        }
        if (direction.equals("right")){
            double x = current_location.getX();
            if (x==100){
                canWalk = false;
            }
        }
        if (direction.equals("forward")){
            double y = current_location.getY();
            if (y==100){
                canWalk = false;
            }
        }
        if (direction.equals("backwords")){
            double y = current_location.getY();
            if (y==-100){
                canWalk = false;
            }
        }
        System.out.println();
        return canWalk;
    }

    /*
     * check if the input height is reachable for the drone
     * print out the current location after flying
     * @param x
     * @param y
     * return whether it can fly the input distance
     */
    public boolean fly(int x, int y){
        boolean canFly = true;
        if (y > 500){
            canFly = false;
            System.out.println("Too high for the drone");
        }
        if (canFly == true){
            double current_x = this.current_location.getX();
            double current_y = this.current_location.getY();
            this.current_location.setLocation(x+current_x, current_y);
            this.current_height += y;
            System.out.println("Your drone is now at Point "+ this.current_location + " with " + this.current_height + " meters height.");
            shrink();
        }
        return canFly;
    }

    /*
     * shrink the number of battery after each action
     */
    public Number shrink(){
        this.Battery -= 50;
        if (Battery<20){
            System.out.println("Warning: Low Battery");
        }
        if (Battery <= 0){
            rest();
        }
        return Battery;
    }

    /*
     * charging the battery to full
     */
    public Number grow(){
        this.Battery = 100;
        System.out.println("The battery is full now.");
        return Battery;
    }

    /*
     * closing the drone (turn it off)
     */
    public void rest() {
        System.out.println("Bye! See you next time");
    }

    /*
     * undo the execution
     * restart the 
     */
    public void undo() {
        this.status = true;
        this.current_location = new Point(0, 0);
        this.Battery = 100;
        this.current_height = 0;
        System.out.println("The system has restarted.");
    }

    /*
     * move the drone on the ground and return/print out its current location after the movement
     * @param x
     * @param y
     * return the current location of the drone
     */
    public Point move(int x, int y){
        double current_x = current_location.getX();
        double current_y = current_location.getY();
        current_location.setLocation(x+current_x, y+current_y);
        if (x+current_x > 500 || y+current_y >500){
            System.out.println("Too far too reach");
        }else{
            System.out.println("Your drone is now at "+ current_location);
        }
        shrink();
        return current_location;
    }

    public static void main(String[] args) {
        Drone myDrone = new Drone("Zhirou's Drone", 2, true, new Point(0,0), 100, 0);
        myDrone.grab("camera");
        myDrone.drop("camera");
        myDrone.walk("left");
        myDrone.examine("Zhirou's Drone");
        myDrone.walk("left");
        myDrone.fly(39, 52);
        myDrone.undo();
        myDrone.move(700, 150);
        myDrone.fly(40, 1000);
        myDrone.fly(20, 10);
        myDrone.move(30, 80);
    }
}
