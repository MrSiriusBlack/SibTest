import java.util.ArrayList;

public class Lift extends Thread {

    private int floor = 1;
    private int destinationFloor;
    private Controller controller;
    private LiftStates state = LiftStates.STAY; // ���� �����
    private char direction = 's'; // � ������� �� ��������
    private boolean[] passengers = new boolean[Constants.FLOORS + 1]; // ��������� �� ���� ����������

    @Override
    public void run() {
        while (!isInterrupted()) {
            if (direction != 's') {
//                ConsoleHelper.writeMessage("Test. ����������� � ����� ����������.");
                goEmpty(destinationFloor);
            } else {
                try {sleep(100);} catch (InterruptedException e) {Thread.currentThread().interrupt();}
            }
        }
    }

    public Lift(Controller controller) {
        this.controller = controller;
    }

    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public void goEmpty(int floor) {

//        ConsoleHelper.writeMessage(String.format("Test. ���� ������ �� %d ����.", destinationFloor));
        passengers[floor] = true; // ��� ����� ���-�� ����
        // ���������� ���� �� ������ ����������
        if (this.floor > floor)
            state = LiftStates.MOVING_DOWN;
        else
            state = LiftStates.MOVING_UP;
        moveLiftOnSomeFloors(Math.abs(this.floor - floor));
        this.floor = floor;
        ConsoleHelper.writeMessage(String.format("���� ������ �� %d ����.", floor));
        checkPassengerOnFloor(floor);
        go();
    }

    private void go() {
        // ���������� ���� ����� �� ��������� ����
        if (direction == 'u') {
            while (floor < Constants.FLOORS) {
                state = LiftStates.MOVING_UP;
                moveLiftOnSomeFloors(1);
                fillPassengersArray(++floor);
                checkPassengerOnFloor(floor);
            }
        } else { // ���������� ���� ���� �� ������ ����
            while (floor > 1) {
                state = LiftStates.MOVING_DOWN;
                moveLiftOnSomeFloors(1);
                fillPassengersArray(--floor);
                checkPassengerOnFloor(floor);
            }
        }
        reachExtremeFloor();
    }

    private void fillPassengersArray(int floor) {
        ArrayList<Integer> array = controller.takePassengersOnDirection(floor, direction);
        for (int i : array) {
//            ConsoleHelper.writeMessage(String.format("Test. �������� �� %d ����� ��������.", i));
            passengers[i] = true;
        }
    }

    private void checkPassengerOnFloor(int floor) {
        // ���������, ���� �� �������� �� �����
        if (passengers[floor] == true) {
            passengers[floor] = false; // ������� ���������
            ConsoleHelper.writeMessage(String.format("�������� ����� � ���� �� %d �����.", floor));
            stayLift();
        }
    }

    private void moveLiftOnSomeFloors(int floors) {
        try {
            Thread.currentThread().sleep(Constants.MOVING_TIME * 1000 * floors);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void stayLift() {
        try {
            state = LiftStates.STAY;
            Thread.currentThread().sleep(Constants.STAY_TIME * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getFloor() {
        return floor;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    private void reachExtremeFloor() {
        ConsoleHelper.writeMessage(String.format("���� ������ �� %d ����. ��� �����.", floor));
        state = LiftStates.STAY;
        stayLift();
        direction = 's';
        controller.liftStart();
    }
}
