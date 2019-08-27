import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PassengerTest {

    @Test
    public void testCheckCommand() {
        assertEquals("", Passenger.checkCommand(4, 'u'));
        assertEquals("� ������� ����� ����� ������� ������ �����. ���������� �����.", Passenger.checkCommand(1, 'd'));
        assertEquals("� ���������� ����� ����� ������� ������ ����. ���������� �����.", Passenger.checkCommand(Constants.FLOORS, 'u'));
        assertEquals("����� ��������� ������ 'u' � 'd' ������������. ���������� �����.", Passenger.checkCommand(3, 's'));
        assertEquals(String.format("���� ������ ���� �� 1 �� %d. ���������� �����.", Constants.FLOORS), Passenger.checkCommand(-1, 'd'));
    }
}