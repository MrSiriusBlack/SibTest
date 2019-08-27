import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PassengerTest {

    @Test
    public void testCheckCommand() {
        assertEquals("", Passenger.checkCommand(4, 'u'));
        assertEquals("С первого этажа можно поехать только вверх. Попробуйте снова.", Passenger.checkCommand(1, 'd'));
        assertEquals("С последнего этажа можно поехать только вниз. Попробуйте снова.", Passenger.checkCommand(Constants.FLOORS, 'u'));
        assertEquals("Можно указывать только 'u' и 'd' напроавления. Попробуйте снова.", Passenger.checkCommand(3, 's'));
        assertEquals(String.format("Этаж должен быть от 1 до %d. Попробуйте снова.", Constants.FLOORS), Passenger.checkCommand(-1, 'd'));
    }
}