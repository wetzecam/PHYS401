import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;

/**
* LatticeGasApp simulates and displays the LatticeGas model of fluid flow.
*
* @author Jan Tobochnik, Wolfgang Christiann, Harvey Gould
* @version 1.0  revised 06/24/05
*/
public class LatticeGasApp extends AbstractSimulation {
 FastLatticeGas model = new FastLatticeGas();
 DisplayFrame display = new DisplayFrame("Lattice gas");

 public LatticeGasApp() {
   display.addDrawable(model);
   display.setSize(800, (int) (400*Math.sqrt(3)/2));
 }

 public void initialize() {
   int lx = control.getInt("lx");
   int ly = control.getInt("ly");
   double density = control.getDouble("Particle density");
   model.initialize(lx, ly, density);
   model.flowSpeed = control.getDouble("Flow speed");
   model.spatialAveragingLength = control.getInt("Spatial averaging length");
   model.arrowSize = control.getInt("Arrow size");
   display.setPreferredMinMax(-1, lx, -Math.sqrt(3)/2, ly*Math.sqrt(3)/2);
 }

 public void doStep() {
   model.flowSpeed = control.getDouble("Flow speed");
   model.spatialAveragingLength = control.getInt("Spatial averaging length");
   model.arrowSize = control.getDouble("Arrow size");
   model.step();
 }

 public void reset() {
   control.setValue("lx", 1000);
   control.setValue("ly", 500);
   control.setValue("Particle density", 0.2);
   control.setAdjustableValue("Flow speed", 0.2);
   control.setAdjustableValue("Spatial averaging length", 20);
   control.setAdjustableValue("Arrow size", 2);
   enableStepsPerDisplay(true);
   control.setAdjustableValue("steps per display", 100);
 }

 public static void main(String[] args) {
   SimulationControl.createApp(new LatticeGasApp());
 }
}
