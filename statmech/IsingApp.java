import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.display.*;

public class IsingApp extends AbstractSimulation {
  LatticeFrame lattice= new LatticeFrame("Percolation");
  IsingModel ising = new IsingModel(lattice);
  DisplayFrame frame;

  public void initialize(){
    System.out.println("SHIT");
    ising.systemEnergy = control.getInt("desired total energy");
    int N = control.getInt("Number of particles");
    ising.initialize(N);
    ising.lattice.repaint();

  }

  public void doStep(){
    ising.doMCStep();
  }

  public void reset() {
    control.setValue("Number of particles", 20);
    control.setValue("desired total energy", 20);
  }

  public void resetData() {
    ising.resetData();
  }

  public static void main(String[] args) {
    SimulationControl control = SimulationControl.createApp(new IsingApp());
    control.addButton("resetData", "Reset Data");
  }
}
