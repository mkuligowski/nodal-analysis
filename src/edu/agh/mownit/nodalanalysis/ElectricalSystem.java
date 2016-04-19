package edu.agh.mownit.nodalanalysis;

import java.util.List;
import java.util.List;

/**
 * Created by mkuligowski on 17.04.16.
 */
public class ElectricalSystem {


    private List<Branch> branches;
    private List<Junction> junctions;

    public ElectricalSystem(List<Branch> branches, List<Junction> junctions) {
        this.branches = branches;
        this.junctions = junctions;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public List<Junction> getJunctions() {
        return junctions;
    }

    public void setJunctions(List<Junction> junctions) {
        this.junctions = junctions;
    }

    public void calculateVoltages() {
        getRandomReferenceNode();
        assignReferenceVoltages();
    }

    private void assignReferenceVoltages() {
        for(Junction junction:junctions){
            System.out.printf("Equation for %s\n",junction.getLabel());
            for(Branch branch:junction.getBranches()){

                System.out.printf("branch: %s\n",branch.toString());

                Junction anotherJunction =junction == branch.getStartNode() ? branch.getEndNode():branch.getStartNode();

                System.out.printf("%s - %s / %s\n",
                        junction.getLabel(),
                        anotherJunction.getLabel(),
                        branch.getResistance()
                        );
            }
            System.out.println("");
        }
    }

    private void getRandomReferenceNode() {
        junctions.get(0).setReferenceNode(true);
        junctions.get(0).setVoltage(0);
    }
}
