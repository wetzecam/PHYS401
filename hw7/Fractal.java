import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.PlotFrame;

public class Fractal extends AbstractSimulation {
  PlotFrame frame = new PlotFrame("x", "y", "IFS Map");
  double t = 0, dt = 0.1;
  double x,y,xplot,yplot,term;
  int nspeed;
  Circle circle = new Circle(0, 0, 5);
  double [] a = new double [] { 0.80,  0.23,  0.23, 0.00};
  double [] b = new double [] { 0.04, -0.23,  0.23, 0.00};
  double [] e = new double [] { 0.00,  0.00,  0.00, 0.00};
  double [] c = new double [] {-0.04,  0.23, -0.23, 0.00};
  double [] d = new double [] { 0.80,  0.23,  0.23, 0.25};
  double [] f = new double [] { 0.20,  0.15,  0.18, 0.00};
  double [] p = new double [] { 0.75,  0.10,  0.10, 0.05};

public void reset() {
    t = 0.0;
    frame.setPreferredMinMax(-0.5, 0.5, 0.0, 1.0);
    frame.setSize(600, 600);
    frame.setConnected(false);

	  frame.setMarkerSize(2,0);
    frame.addDrawable(circle);
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
    circle.setXY(0, 0);
    frame.setMessage("t="+decimalFormat.format(t), 1);
  }

  /**
   * Does a simulation step.
   */
  protected void doStep() {
   for(int n = 0;n<nspeed;n++) {
       	 double rnd = Math.random();
         int i=0;
         if(rnd < p[0]){
           i=0;
         }
         else if(rnd < (p[0]+p[1])){
           i=1;
         }
         else if(rnd < (p[0]+p[1]+p[2])){
           i=2;
         }
         else {
           i=3;
         }
         xplot = x*a[i] + y*b[i] + e[i];
         yplot = x*c[i] + y*d[i] + f[i];
         x = xplot;
         y = yplot;

    frame.append(2, x, y);

    circle.setXY(x, y);
    frame.setMessage("t="+decimalFormat.format(t), 1);
    }	// end of for-loop
  }

  public static void main(String[] args) {
    SimulationControl.createApp(new Fractal());
  }
}
