import java.util.Objects;

public class Movement {
    private int floor;
    private char direction;

    public Movement(int floor, char direction) {
        this.floor = floor;
        this.direction = direction;
    }

    public int getFloor() {
        return floor;
    }

    public char getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movement movement = (Movement) o;
        return floor == movement.floor &&
                direction == movement.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floor, direction);
    }
}
