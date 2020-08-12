import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.PlotFrame;


public class PathIntegralApp extends AbstractSimulation {
  PlotFrame phiFrame = new PlotFrame("x","Phi_0","Phi_0(x)");
  PathIntegral qmwalk = new PathIntegral();

  public void initialize() {
    qmwalk.N_beads = control.getInt("initial number of walkers");
    qmwalk.ds = control.getDouble("step size ds");
    qmwalk.numberOfBins = control.getInt("number of bins for wavefunction");
    qmwalk.Time = 10.0;
    qmwalk.initialize();
  }

  public void doStep() {
    for(int i=0;i<1000;i++){
      qmwalk.doMCS();
    }

    phiFrame.clearData();
    phiFrame.append(0,qmwalk.xv,qmwalk.phi0);
    phiFrame.setMessage("E = "+decimalFormat.format(qmwalk.eAccum/((double)(qmwalk.mcs)))+" N = ");
  }

  public void reset() {
    control.setValue("initial number of walkers", 5);
    control.setValue("step size ds", 0.1);
    control.setValue("number of bins for wavefunction", 100);
    enableStepsPerDisplay(true);
  }

  public void resetData() {
    //qmwalk.resetData();
    phiFrame.clearData();
    phiFrame.repaint();
  }

  public static void main(String[] args) {
    SimulationControl control = SimulationControl.createApp(new PathIntegralApp());
    control.addButton("resetData", "Reset Data");
  }

}
