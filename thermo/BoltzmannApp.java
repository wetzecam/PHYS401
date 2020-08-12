import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.HistogramFrame;

public class BoltzmannApp extends AbstractSimulation {
  double beta;    // thermo beta = 1/kT
  int mcs;
  int accepted;
  double velocity;
  HistogramFrame velocityDistribution = new HistogramFrame("v", "P(v)", "Velocity distribution");

  public void initialize() {
    velocityDistribution.clearData();
    beta = 1.0/control.getDouble("Temperature");
    velocity = control.getDouble("Initial velocity");
    accepted = 0;
    mcs = 0;
  }

  public void doStep(){
    double delta = control.getDouble("Maximum velocity change");
    mcs++;
    double ke = 0.5 * velocity * velocity;
    double vTrial = velocity+delta*(2.0*Math.random()-1.0);
    double keTrial = 0.5*vTrial*vTrial;
    double dE = keTrial - ke;
    if((dE<0) || (Math.exp(-beta*dE)>Math.random())) {
      accepted++;
      ke = keTrial;
      velocity = vTrial;
    }
    velocityDistribution.append(velocity);
    control.clearMessages();
    control.println("mcs = "+mcs);
    control.println("acceptance probability = "+(double)(accepted/mcs));
  }

  public void reset(){
    control.setValue("Maximum velocity change", 10.0);
    control.setValue("Temperatue", 10.0);
    control.setValue("Initial velocity", 0.0);
    enableStepsPerDisplay(true);
  }
  public static void main(String[] args){
    SimulationControl.createApp(new BoltzmannApp());
  }
}
