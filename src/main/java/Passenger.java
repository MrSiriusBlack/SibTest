import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Passenger {

    private static Controller controller;

    public static void main(String[] args) {

        controller = Controller.getInstance();
        // запуск управления лифтами
        controller.start();
        // перемещения пассажиров
        addFloorAndDirection();
    }

    public static void addFloorAndDirection() {
        String input;
        while (true) {
            try {
//                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                input = ConsoleHelper.readString();
            } catch (IOException e) {
                ConsoleHelper.writeMessage("Неизвестный ввод. Попробуйте снова.");
                continue;
            }

            if (input.equals("exit")) {
                controller.exit();
                break;
            }
            String[] command = input.split(" ");
            if (command.length != 2) {
                ConsoleHelper.writeMessage("Должны быть указаны этаж и направление поездки. Попробуйте снова.");
                continue;
            }
            int floor = 0;
            try {
                floor = Integer.parseInt(command[0]);
            } catch (NumberFormatException e) {
                ConsoleHelper.writeMessage("Этаж должен быть указан целым числом. Попробуйте снова.");
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
            return ("Можно указывать только 'u' и 'd' напроавления. Попробуйте снова.");
        if (floor < 1 || floor > Constants.FLOORS)
            return (String.format("Этаж должен быть от 1 до %d. Попробуйте снова.", Constants.FLOORS));
        if (floor == 1 && direction == 'd')
            return ("С первого этажа можно поехать только вверх. Попробуйте снова.");
        if (floor == Constants.FLOORS && direction == 'u')
            return ("С последнего этажа можно поехать только вниз. Попробуйте снова.");
        return "";
    }
}
