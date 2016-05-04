package edu.agh.mownit.nodalanalysis.nodalanalysis;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {

        ElectricalSystem electricalSystem = new ElectricalSystem();
        electricalSystem.loadJunctions("data/junctions.csv");
        electricalSystem.loadBranches("data/branches.csv");
        electricalSystem.putSourcePower(100,"branch1",Junction.START_JUNCTION);
        electricalSystem.doNodalAnalysis();
        CircuitDrawer.drawGraph(electricalSystem);


    }
}
