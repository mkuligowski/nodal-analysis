package edu.agh.mownit.nodalanalysis;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ElectricalSystemTest {





    @Test
    public void testVoltages(){
        Junction junction1 = new Junction();
        junction1.setLabel("J1");
        Junction junction2 = new Junction();
        junction2.setLabel("J2");
        Junction junction3 = new Junction();
        junction3.setLabel("J3");
        Junction junction4 = new Junction();
        junction4.setLabel("J4");



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
        electricalSystem.calculateVoltages();

        assertEquals(20.13,junction1.getVoltage(),0.1);
        assertEquals(8.05,junction2.getVoltage(),0.1);
        assertEquals(60.4,junction3.getVoltage(),0.1);
        assertEquals(0.0,junction4.getVoltage(),0.1);

    }
}
