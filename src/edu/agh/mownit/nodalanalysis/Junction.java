package edu.agh.mownit.nodalanalysis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mkuligowski on 17.04.16.
 */
public class Junction {
    private String label;
    private double voltage;
    private boolean isReferenceNode;
    private List<Branch> branches = new ArrayList<>();

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
}
