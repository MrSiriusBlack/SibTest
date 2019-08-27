import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    Controller controller = Controller.getInstance();

    @BeforeEach
    void setUp() {
        controller.addMovement(new Movement(1, 'u'));
        controller.addMovement(new Movement(7, 'd'));
        controller.addMovement(new Movement(3, 'u'));
        controller.addMovement(new Movement(2, 'd'));
        controller.addMovement(new Movement(4, 'd'));
    }

    @Test
    void testTakePassengersOnDirectionUp() {
        ArrayList<Integer> result = new ArrayList<>(Arrays.asList(7, 2, 4));
        assertEquals(result, controller.takePassengersOnDirection(7, 'd'), "here");
    }

    @Test
    void testTakePassengersonDirectionDown() {
        ArrayList<Integer> result = new ArrayList<>(Arrays.asList(1, 3));
        assertEquals(result, controller.takePassengersOnDirection(1, 'u'));
    }

    @Test
    void testTakePassengersOnDirectionUpNoPassengers() {
        ArrayList<Integer> result = new ArrayList<>(Arrays.asList());
        assertEquals(result, controller.takePassengersOnDirection(4, 'u'));
    }
}
