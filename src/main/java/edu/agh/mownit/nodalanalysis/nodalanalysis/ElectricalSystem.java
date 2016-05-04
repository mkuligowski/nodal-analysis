package edu.agh.mownit.nodalanalysis.nodalanalysis;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mkuligowski on 17.04.16.
 */
public class ElectricalSystem {


    private List<Branch> branches = new ArrayList<>();
    private List<Junction> junctions = new ArrayList<>();
    private double[][] matrix;
    private double[] results;


    public ElectricalSystem(){};

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

    public void doNodalAnalysis() {
        getRandomReferenceNode();
        generateIndexes();
        assignReferenceVoltages();
        double[] voltages = solveMatrix();
        assignVoltagesToJunctions(voltages);
        assignCurrentToBranches();
    }

    private void assignCurrentToBranches() {
        for(Branch branch:branches){
            branch.setCurrent(Math.abs(
                    (branch.getStartNode().getVoltage()-branch.getEndNode().getVoltage()+branch.getExtraEndVoltage())
                            /branch.getResistance()
            ));
        }
    }

    private void assignVoltagesToJunctions(double[] voltages) {
        for(Junction junction:junctions){
            if(!junction.isReferenceNode())
                junction.setVoltage(voltages[junction.getCurrentIndex()]);
        }
    }


    private double[] solveMatrix() {
        return GaussianResolver.calculate(matrix,results);
    }

    private void generateIndexes() {
        for(Junction junction:junctions) {
            if (!junction.isReferenceNode()) {
                if (!junction.hasIndex()) {
                    junction.generateIndex();
                }
            }
        }
    }

    private void assignReferenceVoltages() {
        double[][] mainArray = new double[junctions.size()-1][];
        double[] results = new double[junctions.size()-1];
        int mainArrayCounter = 0;
        for(Junction junction:junctions){
            if(!junction.isReferenceNode()){
                double[] array = new double[junctions.size() - 1];
                double extraVoltage = 0;
                for(Branch branch:junction.getBranches()){
                    Junction anotherJunction =junction == branch.getStartNode() ? branch.getEndNode():branch.getStartNode();

                    array[junction.getCurrentIndex()] += 1/branch.getResistance();
                    if(!anotherJunction.isReferenceNode())
                        array[anotherJunction.getCurrentIndex()]+= -1/branch.getResistance();

                    extraVoltage += branch.getExtraVoltage(anotherJunction)/branch.getResistance()*-1;
                }
                results[mainArrayCounter] = extraVoltage;
                mainArray[mainArrayCounter++] = array;

            }
        }


        for(int i=0;i<mainArray.length;i++){
            for(int j=0; j<mainArray[i].length;j++)
                System.out.print(mainArray[i][j]+"|");
            System.out.println("");
        }

        for(int i=0;i<results.length;i++){
            System.out.println(results[i]+"|");
        }


        this.matrix = mainArray;
        this.results = results;

    }

    private void getRandomReferenceNode() {
        int randomIndex =  new Random().nextInt(junctions.size());
        junctions.get(randomIndex).setReferenceNode(true);
        junctions.get(randomIndex).setVoltage(0);
    }

    public void assignSourcePower(int voltage, Branch branch, String polarization) {
        if("S".equals(polarization)){
            branch.setExtraEndVoltage(-1*voltage);
            branch.setExtraStartVoltage(voltage);
        }else{
            branch.setExtraEndVoltage(voltage);
            branch.setExtraStartVoltage(-1*voltage);
        }

    }

    public void loadJunctions(String path) throws IOException {

        Reader in = new FileReader(path);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
        for (CSVRecord record : records) {
            junctions.add(new Junction(record.get(0)));
        }

    }
    public void loadBranches(String path) throws IOException {
        Reader in = new FileReader(path);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
        for (CSVRecord record : records) {
            Branch branch = new Branch();
            branch.setStartNode(getJuntionByLabel(record.get(0)));
            branch.setEndNode(getJuntionByLabel(record.get(1)));
            branch.setResistance(Double.parseDouble(record.get(2)));
            branch.setLabel(record.get(3));
            branches.add(branch);
        }
    }

    private Junction getJuntionByLabel(String label) {
        for(Junction juntion:junctions){
            if(label.equals(juntion.getLabel()))
                return  juntion;
        }
        throw new RuntimeException(String.format("Juntion %s not found",label));
    }

    public void putSourcePower(int voltage, String branchLabel, String direction) {
        Branch branch = findBranchByLabel(branchLabel);

        if("S".equals(direction)){
            branch.setExtraEndVoltage(-1*voltage);
            branch.setExtraStartVoltage(voltage);
        }else{
            branch.setExtraEndVoltage(voltage);
            branch.setExtraStartVoltage(-1*voltage);
        }
    }

    private Branch findBranchByLabel(String branchLabel) {
        for(Branch branch:branches){
            if(branchLabel.equals(branch.getLabel()))
                return branch;
        }
        throw new RuntimeException(String.format("Branch %s not found",branchLabel));
    }
}
