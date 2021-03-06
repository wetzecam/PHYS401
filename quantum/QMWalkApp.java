import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.PlotFrame;

public class QMWalkApp extends AbstractSimulation {
  PlotFrame phiFrame = new PlotFrame("x","Phi_0","Phi_0(x)");
  QMWalk qmwalk = new QMWalk();

  public void initialize() {
    qmwalk.N = control.getInt("initial number of walkers");
    qmwalk.ds = control.getDouble("step size ds");
    qmwalk.numberOfBins = control.getInt("number of bins for wavefunction");
    qmwalk.initialize();
  }

  public void doStep() {
    qmwalk.doMCS();
    phiFrame.clearData();
    phiFrame.append(0,qmwalk.xv,qmwalk.phi0);
    phiFrame.setMessage("E = "+decimalFormat.format(qmwalk.eAccum/qmwalk.mcs)+" N = "+qmwalk.N);
  }

  public void reset() {
    control.setValue("initial number of walkers", 50);
    control.setValue("step size ds", 0.1);
    control.setValue("number of bins for wavefunction", 100);
    enableStepsPerDisplay(true);
  }

  public void resetData() {
    qmwalk.resetData();
    phiFrame.clearData();
    phiFrame.repaint();
  }

  public static void main(String[] args) {
    SimulationControl control = SimulationControl.createApp(new QMWalkApp());
    control.addButton("resetData", "Reset Data");
  }

}
