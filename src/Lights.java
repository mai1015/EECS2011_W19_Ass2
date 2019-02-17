/**********************************************************
 * EECS2011: Fundamentals of Data Structures,  Winter 2019
 * Assignment 2
 * Section: Z
 * Student Name: Zhili Mai
 * Student eecs account:  mai1015
 * Student ID number:  215234842
 **********************************************************/

public class Lights {

    /**
     * state of lihgt
     */
    private boolean[] lights;

    /**
     * Initialize number of n lights
     * @param n number of lights
     */
    public Lights(int n) {
        this.lights = new boolean[n];
    }

    /**
     * Initialize number of n lights
     * @param n number of lights
     * @param s initial state
     */
    public Lights(int n, boolean s) {
        this(n);
        for (int i = 0; i < n; i++) {
            lights[i] = s;
        }
    }

    /**
     * turn on or off for n light
     * @param n the number of light from 1...n
     * @param l the state of the light
     * @return {@code true} if the operation is successful
     *         {@code false} otherwise
     */
    public boolean setLight(int n, boolean l) {
        n--;
        if (lights[n] == l) return true;
        String msg = String.format("Turn %s Light %d", l ? "ON" : "OFF", n);
        if (n == 0) {
            lights[0] = l;
            System.out.printf("%s \t %s\n", toString(), msg);
            return true;
        }
        if (!lights[n - 1]) return false;
        for (int i = 0; i < n - 1; i++) {
            if (lights[i]) return false;
        }
        lights[n] = l;
        System.out.printf("%s \t %s\n", toString(), msg);
        return true;
    }

    /**
     * turn on light on n
     * @param n the number of light from 1...n
     */
    public void turnOn(int n) {
        // all on
        if (n == 1) setLight(1, true);
        else {
            turnOn(n - 1);
            if (n > 2) turnOff(n - 2);
            setLight(n, true);
            if (n > 2) turnOn(n - 2);
        }
    }

    /**
     * turn off light on n
     * @param n the number of light from 1...n
     */
    public void turnOff(int n) {
        // all on
        if (n == 1) setLight(1, false);
        else {
            if (n > 2) turnOff(n - 2);
            setLight(n, false);
            if (n > 2) turnOn(n - 2);
            turnOff(n - 1);
        }
        // all off
    }

    /**
     * flip switch for light on n
     * @param n the number of light from 1...n
     */
    public void flipSwitches(int n, boolean s) {
        if (lights[n - 1] == s) return;
        if (n == 1) setLight(1, s);
        else {
            flipSwitches(n - 1, true);
            if (n > 2) flipSwitches(n - 2, false);
            setLight(n, s);
            if (n > 2) flipSwitches(n - 2, true);
            flipSwitches(n - 1, s);
        }
    }

    /**
     * Returns a string representation of this Vector, containing
     * the String representation of each element.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lights.length; i++) {
            sb.append(lights[i] ? 1 : 0);
            sb.append(" ");
        }
        return sb.substring(0, sb.length() - 1);
    }

    public static void main(String[] args){
        System.out.println("------------- TrunOff() -------------");
        for (int i = 3; i < 7; i++) {
            Lights l1 = new Lights(i, true);
            System.out.printf("%s \t %d lights ON initially\n", l1.toString(), i);
            l1.turnOff(i);
            System.out.println("-------------------------------------");
        }
        System.out.println("------------- flipSwitches() -------------");
        for (int i = 3; i < 7; i++) {
            Lights l1 = new Lights(i, true);
            System.out.printf("%s \t %d lights ON initially\n", l1.toString(), i);
            l1.flipSwitches(i, false);
            System.out.println("-------------------------------------------");
        }
        System.out.println("------------- flipSwitches() -------------");
        int n = 5;
        Lights l1 = new Lights(n, false);
        System.out.printf("%s \t %d lights ON initially\n", l1.toString(), n);
        l1.flipSwitches(n, true);
        System.out.println("-------------------------------------");
        System.out.println("------------- TurnOn() -------------");
        Lights l2 = new Lights(n, false);
        System.out.printf("%s \t %d lights ON initially\n", l2.toString(), n);
        l2.turnOn(n);
        System.out.println("-------------------------------------");
    }
}
