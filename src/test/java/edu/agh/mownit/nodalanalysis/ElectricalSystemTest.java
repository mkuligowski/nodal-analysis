package edu.agh.mownit.nodalanalysis;

import edu.agh.mownit.nodalanalysis.nodalanalysis.Branch;
import edu.agh.mownit.nodalanalysis.nodalanalysis.ElectricalSystem;
import edu.agh.mownit.nodalanalysis.nodalanalysis.Junction;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ElectricalSystemTest {


    @Test
    public void testLoadJunctions() throws IOException {
        ElectricalSystem system = new ElectricalSystem();
        system.loadJunctions("data/junctions.csv");
        assertEquals(4,system.getJunctions().size());
        assertEquals("J1",system.getJunctions().get(0).getLabel());
        assertEquals("J2",system.getJunctions().get(1).getLabel());
        assertEquals("J3",system.getJunctions().get(2).getLabel());
        assertEquals("J4",system.getJunctions().get(3).getLabel());
    }


    @Test
    public void testLoadBranches() throws IOException {
        ElectricalSystem system = new ElectricalSystem();
        List<Junction> junctions = new ArrayList<>();
        junctions.add(new Junction("J1"));
        junctions.add(new Junction("J2"));
        junctions.add(new Junction("J3"));
        junctions.add(new Junction("J4"));
        system.setJunctions(junctions);
        system.loadBranches("data/branches.csv");
        assertEquals(6,system.getBranches().size());
    }


    @Test
    public void testPutSourcePower() throws IOException {
        ElectricalSystem system = new ElectricalSystem();
        system.loadJunctions("data/junctions.csv");
        system.loadBranches("data/branches.csv");
        system.putSourcePower(100,"branch1",Junction.START_JUNCTION);
        assertEquals(100,system.getBranches().get(0).getExtraStartVoltage(),0.1);
        assertEquals(-100,system.getBranches().get(0).getExtraEndVoltage(),0.1);
    }


    @Test
    public void testVoltages(){
        Junction junction1 = new Junction("J1");
        Junction junction2 = new Junction("J2");
        Junction junction3 = new Junction("J3");
        Junction junction4 = new Junction("J4");



        Branch branch1 = new Branch();
        branch1.setStartNode(junction1);
        branch1.setEndNode(junction3);
        branch1.setResistance(5);
        branch1.setSource(100);


        Branch branch2 = new Branch();
        branch2.setStartNode(junction1);
        branch2.setEndNode(junction3);
        branch2.setResistance(30);

        Branch branch3 = new Branch();
        branch3.setStartNode(junction1);
        branch3.setEndNode(junction2);
        branch3.setResistance(10);

        Branch branch4 = new Branch();
        branch4.setStartNode(junction2);
        branch4.setEndNode(junction4);
        branch4.setResistance(20);

        Branch branch5 = new Branch();
        branch5.setStartNode(junction3);
        branch5.setEndNode(junction4);
        branch5.setResistance(50);

        Branch branch6 = new Branch();
        branch6.setStartNode(junction2);
        branch6.setEndNode(junction4);
        branch6.setResistance(10);

        List<Branch> branches = new ArrayList<>();
        List<Junction> junctions = new ArrayList<>();

        branches.add(branch1);
        branches.add(branch2);
        branches.add(branch3);
        branches.add(branch4);
        branches.add(branch5);
        branches.add(branch6);

        junctions.add(junction1);
        junctions.add(junction2);
        junctions.add(junction3);
        junctions.add(junction4);


        ElectricalSystem electricalSystem = new ElectricalSystem(branches,junctions);
        electricalSystem.assignSourcePower(100, branch1, Junction.START_JUNCTION);
        electricalSystem.doNodalAnalysis();

        assertEquals(20.13,junction1.getVoltage(),0.1);
        assertEquals(8.05,junction2.getVoltage(),0.1);
        assertEquals(-60.4,junction3.getVoltage(),0.1);
        assertEquals(0.0,junction4.getVoltage(),0.1);

        assertEquals(0.402,branch4.getCurrent(),0.1 );
        assertEquals(1.21,branch3.getCurrent(),0.1);
        assertEquals(3.89,branch1.getCurrent(),0.1);
        assertEquals(1.21,branch5.getCurrent(),0.1);
        assertEquals(2.68,branch2.getCurrent(),0.1);
        assertEquals(0.805,branch6.getCurrent(),0.1);



    }
}
