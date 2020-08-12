import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.PlotFrame;

public class ChaoticMapApp extends AbstractSimulation {
  PlotFrame frame = new PlotFrame("x", "y", "Chaotic Map");
  double t = 0, dt = 0.1;
  double x,y,xplot,yplot,term;
  int nspeed;
  final static double rcos = Math.cos(3.141592653*76.11/180.0);
  final static double rsin = Math.sin(3.141592653*76.11/180.0);
  Circle circle = new Circle(0, 0, 5);

public void reset() {
    t = 0.0;
    frame.setPreferredMinMax(-1.0, 1.0, -1.0, 1.0);
    frame.setSize(600, 600);
    frame.setConnected(false);
//	frame.setMarkerSize(0,1);
//	frame.setMarkerSize(1,1);
	frame.setMarkerSize(2,0);
 //   frame.setMarkerShape(0, Dataset.NO_MARKER);
    frame.addDrawable(circle);
//    frame.append(0, 0, 0);
    control.setValue("x0", "0.0");
    control.setValue("y0", "0.441");
    control.setValue("nspeed", "1");
    initialize();
  }

  /**
   * Initializes the simulation.
   */
  public void initialize() {
    x = control.getDouble("x0");
    y = control.getDouble("y0");
    nspeed = control.getInt("nspeed");
    t = 0.0;
 //   frame.append(0, 0, 0);
    circle.setXY(0, 0);
    frame.setMessage("t="+decimalFormat.format(t), 1);
  }

  /**
   * Does a simulation step.
   */
  protected void doStep() {
   for(int n = 0;n<nspeed;n++) {
       	   term =y-x*x;
	       xplot= rcos*x-rsin*term;
	       yplot= rsin*x+rcos*term;
		   x=xplot;
		   y=yplot;
         t += dt;
    frame.append(2, x, y);
//    frame.append(1, y, x);
    circle.setXY(x, y);
    frame.setMessage("t="+decimalFormat.format(t), 1);
      }	// end of for-loop
  }

  public static void main(String[] args) {
    SimulationControl.createApp(new ChaoticMapApp());
  }
}
