# simple Nodal Analysis algorithm in Java

Hello!

This is simple implementation of the nodal analysis algorithm.

The most important part of code is located in 
```java
edu.agh.mownit.nodalanalysis.nodalanalysis.Application
```


```java
 ElectricalSystem electricalSystem = new ElectricalSystem();
 electricalSystem.loadJunctions("data/junctions.csv");
 electricalSystem.loadBranches("data/branches.csv");
 electricalSystem.putSourcePower(100,"branch1",Junction.START_JUNCTION);
 electricalSystem.doNodalAnalysis();
 CircuitDrawer.drawGraph(electricalSystem);
```


I hope that above code is self-describing.

Enjoy!
