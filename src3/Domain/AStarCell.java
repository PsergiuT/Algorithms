package Domain;

public class AStarCell extends Cell{
    private double distance;
    private double cost_from_start;

    public AStarCell(Cell cell, double distance, double cost_from_start) {
        super(cell);
        this.distance = distance;
        this.cost_from_start = cost_from_start;
    }


    public AStarCell(int row, int col, double distance, double cost_from_start) {
        super(row, col);
        this.distance = distance;
        this.cost_from_start = cost_from_start;
    }

    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getCost_from_start() {
        return cost_from_start;
    }

    public void setCost_from_start(double cost_from_start) {
        this.cost_from_start = cost_from_start;
    }
}
