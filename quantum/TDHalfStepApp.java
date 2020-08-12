import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.ComplexPlotFrame;

public class TDHalfStepApp extends AbstractSimulation {
  ComplexPlotFrame psiFrame = new ComplexPlotFrame("x", "|Psi|", "Wave function");
  TDHalfStep wavefunction;
  double time;

  public TDHalfStepApp(){
    psiFrame.limitAutoscaleY(-1,1);
  }

  public void initialize() {
    time =0;
    psiFrame.setMessage("t = "+0);
    double xmin = control.getDouble("xmin");
    double xmax = control.getDouble("xmax");
    int numberOfPoints = control.getInt("number of points");
    double width = control.getDouble("packet width");
    double x0 = control.getDouble("packet offset");
    double momentum = control.getDouble("packet momentum");
    GaussianPacket packet = new GaussianPacket(width, x0, momentum);
    wavefunction = new TDHalfStep(packet, numberOfPoints, xmin, xmax);
    psiFrame.clearData();
    psiFrame.append(wavefunction.x, wavefunction.realPsi, wavefunction.imagPsi);
  }

  public void doStep(){
    time += wavefunction.step();
    psiFrame.clearData();
    psiFrame.append(wavefunction.x, wavefunction.realPsi, wavefunction.imagPsi);
    psiFrame.setMessage("t = "+decimalFormat.format(time));
  }

  public void reset(){
    control.setValue("xmin", -40);
    control.setValue("xmax",  40);
    control.setValue("number of points", 1000);
    control.setValue("packet width",1);
    control.setValue("packet offset", -15);
    control.setValue("packet momentum", 2);
    // multiple computation steps per animatoin step
    setStepsPerDisplay(40);
    enableStepsPerDisplay(true);
    initialize();
  }

  public static void main(String[] args){
    SimulationControl.createApp(new TDHalfStepApp());
  }
}
