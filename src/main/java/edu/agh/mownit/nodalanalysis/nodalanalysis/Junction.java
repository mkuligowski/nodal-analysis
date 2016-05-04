package edu.agh.mownit.nodalanalysis.nodalanalysis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mkuligowski on 17.04.16.
 */
public class Junction {
    private static int index = 0;

    public Junction(String label) {
        this.label = label;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    private int currentIndex = -1;
    public static final String START_JUNCTION = "S";
    public static final String END_JUNCTION = "E";
    private String label;
    private double voltage;
    private boolean isReferenceNode;
    private List<Branch> branches = new ArrayList<Branch>();
    

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public boolean isReferenceNode() {
        return isReferenceNode;
    }

    public void setReferenceNode(boolean referenceNode) {
        isReferenceNode = referenceNode;
    }

    public void addBranch(Branch branch) {
        branches.add(branch);
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void generateIndex() {
        currentIndex = index++;
    }

    public boolean hasIndex() {
        return currentIndex != -1;
    }
}
