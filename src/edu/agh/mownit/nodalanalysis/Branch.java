package edu.agh.mownit.nodalanalysis;

/**
 * Created by mkuligowski on 17.04.16.
 */
public class Branch {


    private Junction startNode;
    private Junction endNode;
    private double resistance;
    private double current;
    private double source;
    private double extraStartVoltage;
    private double extraEndVoldate;

    public double getExtraStartVoltage() {
        return extraStartVoltage;
    }

    public double getExtraEndVoldate() {
        return extraEndVoldate;
    }

    public void setExtraStartVoltage(double extraStartVoltage) {
        this.extraStartVoltage = extraStartVoltage;
    }

    public void setExtraEndVoldate(double extraEndVoldate) {
        this.extraEndVoldate = extraEndVoldate;
    }





    public Junction getStartNode() {
        return startNode;
    }

    public void setStartNode(Junction startNode) {
        startNode.addBranch(this);
        this.startNode = startNode;
    }

    public Junction getEndNode() {
        return endNode;
    }

    public void setEndNode(Junction endNode) {
        endNode.addBranch(this);
        this.endNode = endNode;
    }

    public double getResistance() {
        return resistance;
    }

    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public void setSource(double source) {
        this.source = source;
    }

    public double getSource() {
        return source;
    }

    @Override
    public String toString(){
        return String.format("Start %s - To %s", startNode.getLabel(), endNode.getLabel());
    }

    public double getExtraVoltage(Junction anotherJunction) {
        if(anotherJunction == startNode)
            return extraStartVoltage;
        else
            return extraEndVoldate;
    }
}
