import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Passenger {

    private static Controller controller;

    public static void main(String[] args) {

        controller = Controller.getInstance();
        // ������ ���������� �������
        controller.start();
        // ����������� ����������
        addFloorAndDirection();
    }

    public static void addFloorAndDirection() {
        String input;
        while (true) {
            try {
//                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                input = ConsoleHelper.readString();
            } catch (IOException e) {
                ConsoleHelper.writeMessage("����������� ����. ���������� �����.");
                continue;
            }

            if (input.equals("exit")) {
                controller.exit();
                break;
            }
            String[] command = input.split(" ");
            if (command.length != 2) {
                ConsoleHelper.writeMessage("������ ���� ������� ���� � ����������� �������. ���������� �����.");
                continue;
            }
            int floor = 0;
            try {
                floor = Integer.parseInt(command[0]);
            } catch (NumberFormatException e) {
                ConsoleHelper.writeMessage("���� ������ ���� ������ ����� ������. ���������� �����.");
                continue;
            }
            char direction = command[1].charAt(0);

            String check = checkCommand(floor, direction);
            if (check.equals("")) {
                controller.addMovement(new Movement(floor, direction));
            }
            else {
                ConsoleHelper.writeMessage(check);
            }
        }
    }

    public static String checkCommand(int floor, char direction) {
        if (direction != 'u' && direction != 'd')
            return ("����� ��������� ������ 'u' � 'd' ������������. ���������� �����.");
        if (floor < 1 || floor > Constants.FLOORS)
            return (String.format("���� ������ ���� �� 1 �� %d. ���������� �����.", Constants.FLOORS));
        if (floor == 1 && direction == 'd')
            return ("� ������� ����� ����� ������� ������ �����. ���������� �����.");
        if (floor == Constants.FLOORS && direction == 'u')
            return ("� ���������� ����� ����� ������� ������ ����. ���������� �����.");
        return "";
    }
}
