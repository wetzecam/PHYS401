import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.display.*;

public class IsingTwo extends AbstractSimulation {
  LatticeFrame lattice= new LatticeFrame("Percolation");
  PlotFrame phiFrame = new PlotFrame("x","Phi_0","Phi_0(x)");
  IsingModelTwo ising = new IsingModelTwo(lattice);
  DisplayFrame frame;
  int NMCS;

  public void initialize(){
    System.out.println("SHIT");
    NMCS = control.getInt("Mone Carlo iterations");
    int N = control.getInt("Number of particles");
    ising.setTemperature(control.getDouble("desired total energy"));
    ising.initialize(N);
    ising.lattice.repaint();
    phiFrame.setMarkerSize(0,6);
    phiFrame.setMarkerSize(1,6);
    phiFrame.setMarkerSize(2,6);
    phiFrame.setMarkerSize(3,6);
  }

  public void doStep(){
    ising.doMCStep();
  }

  public void reset() {
    control.setValue("Number of particles", 4);
    control.setValue("desired total energy", 1.0);
    control.setValue("Mone Carlo iterations", 10000);
  }

  public void resetData() {
    ising.resetData();
  }

  public void heatCapacity(){
    double T = 1.5;
    while(T<=3.6){
      ising.setTemperature(T);
      ising.mcs = 0;
      for(int n=0;n<NMCS;n++){
        ising.doMCStep();
      }
      ising.updateStats();
      phiFrame.append(0,T,ising.specHeat);
      phiFrame.append(1,T,ising.magSucc);
      phiFrame.append(2,T,ising.avgM);
      ising.resetData();
      System.out.println(T);
      T+=0.2;
    }
  }

  public static void main(String[] args) {
    SimulationControl control = SimulationControl.createApp(new IsingTwo());
    control.addButton("resetData", "Reset Data");
    control.addButton("heatCapacity", "Heat heatCapacity");
  }
}
