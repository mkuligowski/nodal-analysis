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
        double[][] mainArray = new double[junctions.size()-1][];
        int mainArrayCounter = 0;
        for(Junction junction:junctions){
            if(!junction.isReferenceNode()){
                System.out.printf("Equation for %s\n",junction.getLabel());
                double[] array = new double[junctions.size()];
                double extraVoltage = 0;
                for(Branch branch:junction.getBranches()){

                    System.out.printf("branch: %s\n",branch.toString());

                    Junction anotherJunction =junction == branch.getStartNode() ? branch.getEndNode():branch.getStartNode();


                    array[junction.getCurrentIndex()] += 1/branch.getResistance();
                    if(!anotherJunction.isReferenceNode())
                        array[anotherJunction.getCurrentIndex()]+= -1/branch.getResistance();

                    extraVoltage += branch.getExtraVoltage(anotherJunction)/branch.getResistance()*-1;

                    System.out.printf("%s - %s - extra %s / %s\n",
                            junction.getLabel(),
                            anotherJunction.getLabel(),
                            branch.getExtraVoltage(anotherJunction),
                            branch.getResistance()
                    );


                    System.out.println("extraVoltage"+extraVoltage);
                }

                mainArray[mainArrayCounter++] = array;

                for(int i=0;i<array.length;i++){
                    System.out.println(i+"="+array[i]);
                }
                System.out.println("");
            }
        }


        for(int i=0;i<mainArray.length;i++){
            for(int j=0; j<mainArray[i].length;j++)
                System.out.print(mainArray[i][j]+"|");
            System.out.println("");
        }


    }

    private void getRandomReferenceNode() {
        junctions.get(3).setReferenceNode(true);
        junctions.get(3).setVoltage(0);
    }

    public void assignSourcePower(int voltage, Branch branch, String polarization) {
        if("S".equals(polarization)){
            branch.setExtraEndVoldate(-1*voltage);
            branch.setExtraStartVoltage(voltage);
        }else{
            branch.setExtraEndVoldate(voltage);
            branch.setExtraStartVoltage(-1*voltage);
        }

    }
}
