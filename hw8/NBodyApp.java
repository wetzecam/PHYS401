import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.*;

/**
 * PlanetApp models an orbiting planet.
 *
 * This program demonstrates:
 * how to use the Simulation control to run and single step a time dependent model.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class NBodyApp extends AbstractSimulation {
  PlotFrame frame = new PlotFrame("x", "y", "N-Body Orbits");
  PlotFrame eFrame = new PlotFrame("time","Energy", "Energy v.s. Time");

  NBodyObject planet = new NBodyObject();
  Trail kinetic = new Trail();

  /**
   * Constructs the PlanetApp.
   */
  public NBodyApp() {
   frame.setSize(600,600);
   frame.addDrawable(planet);
//   frame.setPreferredMinMax(-1.2, 1.2, -1.2, 1.2);
   frame.setPreferredMinMax(-12., 12., -12., 12.);
   frame.setSquareAspect(true);

   eFrame.setSize(600,600);
//   frame.setPreferredMinMax(-1.2, 1.2, -1.2, 1.2);
   //eFrame.setPreferredMinMax(0.0, 200., 0., 150.);
   eFrame.setMaximumPoints(0,Integer.MAX_VALUE);
   eFrame.setMaximumPoints(1,Integer.MAX_VALUE);
   eFrame.setMaximumPoints(2,Integer.MAX_VALUE);
   eFrame.addDrawable(kinetic);
   //eFrame.setSquareAspect(true);
   eFrame.setConnected(true);
  }

  /**
   * Steps the time.
   */
  public void doStep() {
//    planet.trail.clear();
    for(int i = 0;i<planet.nspeed;i++) { // do nspeed steps between screen draws
      planet.doStep(1.0);       // advances time
      eFrame.append(0,planet.t,planet.kineticEnergy());
      eFrame.append(1,planet.t,planet.potentialEnergy());
      eFrame.append(2,planet.t,planet.totalEnergy());
      //kinetic.addPoint(planet.t, planet.kineticEnergy());
    }
   frame.setMessage("t = "+decimalFormat.format(planet.t));
  }

  /**
   * Initializes the animation using the values in the control.
   */
  public void initialize() {

 //   planet.x  = control.getDouble("x");
//    planet.vx = control.getDouble("vx");
//    planet.y  = control.getDouble("y");
//    planet.vy = control.getDouble("vy");
// initialize
    planet.nbody = control.getInt("nbody");
    planet.dt = control.getDouble("dt");
    planet.nspeed = control.getInt("nspeed");
    planet.t = 0.0;
            for(int k = 0;k<planet.nbody;k++) {
			 planet.x[k] =-10.+1.0*k;
			 planet.y[k] =5;
			 planet.vx[k]=0.0;
			 planet.vy[k]=0.0;
	          }
	planet.trail.clear();
    frame.setMessage("t = 0");
    //eFrame.setPreferredMinMax(0.0, 25.0,0.0,(1.5)*planet.totalEnergy());
  }

  /**
   * Resets animation to a predefined state.
   */
  public void reset() {
//    control.setValue("x",   10.0);
//    control.setValue("vx",  0.00);
//    control.setValue("y",  0.00);
//    control.setValue("vy", 0.10);
    control.setValue("nbody", 3);
    control.setValue("dt", 0.01);
    control.setValue("nspeed", 10);
    initialize();
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    SimulationControl.createApp(new NBodyApp());
  }
}
