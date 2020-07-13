import java.util.ArrayList;

public class Lift extends Thread {

    private int floor = 1;
    private int destinationFloor;
    private Controller controller;
    private LiftStates state = LiftStates.STAY; // лифт стоит
    private char direction = 's'; // и никкуда не движется
    private boolean[] passengers = new boolean[Constants.FLOORS + 1]; // пассажиры на пути следования

    @Override
    public void run() {
        while (!isInterrupted()) {
            if (direction != 's') {
//                ConsoleHelper.writeMessage("Test. Выдвинулись к месту назначения.");
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

//        ConsoleHelper.writeMessage(String.format("Test. Лифт поехал на %d этаж.", destinationFloor));
        passengers[floor] = true; // там точно кто-то есть
        // Перемещаем лифт за первым пассажиром
        if (this.floor > floor)
            state = LiftStates.MOVING_DOWN;
        else
            state = LiftStates.MOVING_UP;
        moveLiftOnSomeFloors(Math.abs(this.floor - floor));
        this.floor = floor;
        ConsoleHelper.writeMessage(String.format("Лифт прибыл на %d этаж.", floor));
        checkPassengerOnFloor(floor);
        go();
    }

    private void go() {
        // перемещаем лифт вверх на последний этаж
        if (direction == 'u') {
            while (floor < Constants.FLOORS) {
                state = LiftStates.MOVING_UP;
                moveLiftOnSomeFloors(1);
                fillPassengersArray(++floor);
                checkPassengerOnFloor(floor);
            }
        } else { // перемещаем лифт вниз на первый этаж
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
//            ConsoleHelper.writeMessage(String.format("Test. Пассажир на %d этаже добавлен.", i));
            passengers[i] = true;
        }
    }

    private void checkPassengerOnFloor(int floor) {
        // проверяем, есть ли пассажир на этаже
        if (passengers[floor] == true) {
            passengers[floor] = false; // забрали пассажира
            ConsoleHelper.writeMessage(String.format("Пассажир вошел в лифт на %d этаже.", floor));
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
        ConsoleHelper.writeMessage(String.format("Лифт прибыл на %d этаж. Все вышли.", floor));
        state = LiftStates.STAY;
        stayLift();
        direction = 's';
        controller.liftStart();
    }
}
