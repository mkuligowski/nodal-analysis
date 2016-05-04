package edu.agh.mownit.nodalanalysis.nodalanalysis;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;


public class CircuitDrawer {

public static void drawGraph(ElectricalSystem system){
    Graph graph =  new MultiGraph("Circuit",true,true);
    graph.addAttribute("ui.antialias");
    graph.addAttribute("ui.quality");
    graph.setAttribute("ui.stylesheet", "url(data/style.css);");


    for(Junction junction:system.getJunctions()){
        Node node = graph.addNode(junction.getLabel());
        node.addAttribute("label",junction.getLabel());
    }


    for(Branch branch:system.getBranches()){
        String branchID = branch.toString();
        Node branchBreakSpecialNode = graph.addNode(branchID);
        branchBreakSpecialNode.addAttribute("label", getBranchCurrent(branch));
        Edge edgePart1 = graph.addEdge(branchID+"1",branch.getStartNodeLabel(),branchID);
        Edge edgePart2 = graph.addEdge(branchID+"2",branchID,branch.getEndNodeLabel());


        if(branch.getExtraEndVoltage() != 0){
            branchBreakSpecialNode.addAttribute("ui.class","source");
            assignSourceDirection(branch, edgePart1, edgePart2);
        }else{
            branchBreakSpecialNode.addAttribute("ui.class","connection");
        }


        assignBranchSize(branch, edgePart1);
        assignBranchSize(branch, edgePart2);

    }




    graph.display();
}

    private static void assignBranchSize(Branch branch, Edge edgePart1) {
        edgePart1.addAttribute("ui.style",String.format("size: %spx;",branch.getCurrent()));
    }

    private static void assignSourceDirection(Branch branch, Edge edgePart1, Edge edgePart2) {
        String sign = branch.getExtraStartVoltage() > 0 ? "+":"-";
        String sign2 = branch.getExtraStartVoltage() < 0 ? "+":"-";
        edgePart1.addAttribute("label",sign);
        edgePart2.addAttribute("label",sign2);
    }

    private static String getBranchCurrent(Branch branch) {
        return String.format("%.2f A", branch.getCurrent());
    }

}
