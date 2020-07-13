import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class Controller extends Thread{

    private LinkedHashSet<Movement> movementSet = new LinkedHashSet<>(); // ������� �������
    private static final Controller INSTANCE = new Controller(); // ���������� ��������

    private Lift cargoLift;
    private Lift passengerLift;

    {
        // �������� � ������ ������
        cargoLift = new Lift(this);
        cargoLift.setDaemon(true);
        passengerLift = new Lift(this);
        passengerLift.setDaemon(true);
        cargoLift.start();
        passengerLift.start();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            if (!movementSet.isEmpty()) {
//                ConsoleHelper.writeMessage("Test. ���� �������.");
                liftStart();
            } else {
                try {sleep(100);} catch (InterruptedException e) {Thread.currentThread().interrupt();}
            }
        }
    }

    public static Controller getInstance() {
        return INSTANCE;
    }

    public synchronized void addMovement (Movement movement) {
        movementSet.add(movement);
//        ConsoleHelper.writeMessage(String.format("Test. �������� ����� �����. ������ �� %d.", movementSet.size()));
//        liftStart();
    }

    public synchronized ArrayList<Integer> takePassengersOnDirection(int floor, char direction) {
        ArrayList<Integer> result = new ArrayList<>();
        Iterator<Movement> iterator = movementSet.iterator();
//        ConsoleHelper.writeMessage(String.format("Test. �������� ������ �������. ���� %d.", movementSet.size()));
        while (iterator.hasNext()) {
            Movement movement = iterator.next();
            if (movement.getFloor() >= floor && movement.getDirection() == 'u' && movement.getDirection() == direction) {
                result.add(movement.getFloor());
                iterator.remove();
            } else if (movement.getFloor() <= floor && movement.getDirection() == 'd' && movement.getDirection() == direction) {
                result.add(movement.getFloor());
                iterator.remove();
            }
        }
//        ConsoleHelper.writeMessage(String.format("Test. ����������� ������ �������. �������� %d.", movementSet.size()));
        return result;
    }

    public synchronized void liftStart() {
        if (movementSet.isEmpty())
            return;
        if (passengerLift.getDirection() == 's' || cargoLift.getDirection() == 's') {
            Movement movement = movementSet.stream().findFirst().get();
            int passengerDist = Constants.FLOORS,
                cargoDist = Constants.FLOORS;
            if (passengerLift.getDirection() == 's')
                passengerDist = Math.abs(passengerLift.getFloor() - movement.getFloor());
//            ConsoleHelper.writeMessage(String.format("Test. Passenger floor %d.", passengerLift.getFloor()));
//            ConsoleHelper.writeMessage(String.format("Test. Passenger dist %d.", passengerDist));
            if (cargoLift.getDirection() == 's')
                cargoDist = Math.abs(cargoLift.getFloor() - movement.getFloor());
//            ConsoleHelper.writeMessage(String.format("Test. Cargo floor %d.", cargoLift.getFloor()));
//            ConsoleHelper.writeMessage(String.format("Test. Cargo dist %d.", cargoDist));
            if (cargoDist < passengerDist) {
                cargoLift.setDestinationFloor(movement.getFloor());
                cargoLift.setDirection(movement.getDirection());
//                ConsoleHelper.writeMessage("Test. ��������� �������� ����");
            } else {
                passengerLift.setDestinationFloor(movement.getFloor());
                passengerLift.setDirection(movement.getDirection());
//                ConsoleHelper.writeMessage("Test. ��������� ������������ ����");
            }
            movementSet.remove(movement);
        }
    }

    private Controller() {}

    public void exit() {
        passengerLift.interrupt();
        cargoLift.interrupt();
        interrupt();
    }
}
