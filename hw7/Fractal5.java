import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.PlotFrame;

public class Fractal5 extends AbstractSimulation {
  PlotFrame frame = new PlotFrame("x", "y", "IFS Map");
  double t = 0, dt = 0.1;
  double x,y,xplot,yplot,term;
  double w1,w2,w3;
  int nspeed;
  //Circle circle = new Circle(0, 0, 5);
  double cos_1 = Math.cos(Math.toRadians(0.0));
  double sin_1 = Math.sin(Math.toRadians(0.0));

  double cos_2 = Math.cos(Math.toRadians(-30.0));
  double sin_2 = Math.sin(Math.toRadians(-30.0));

  double cos_3 = Math.cos(Math.toRadians(40.0));
  double sin_3 = Math.sin(Math.toRadians(40.0));

  double a1 = 0.80;    double b1 =   0.04;
  double c1 = -0.04;   double d1 =   0.80;

  double a1_n = a1*cos_1 - c1*sin_1;
  double b1_n = b1*cos_1 - d1*sin_1;
  double c1_n = a1*sin_1 + c1*cos_1;
  double d1_n = b1*sin_1 + d1*cos_1;

  double a2 = 0.23;   double b2 = -0.23;
  double c2 = 0.23;   double d2 =  0.23;

  double a2_n = a2*cos_2 - c2*sin_2;
  double b2_n = b2*cos_2 - d2*sin_2;
  double c2_n = a2*sin_2 + c2*cos_2;
  double d2_n = b2*sin_2 + d2*cos_2;

  double a3 = 0.23;   double b3 =  0.23;
  double c3 =-0.23;   double d3 =  0.23;

  double a3_n = a3*cos_3 - c3*sin_3;
  double b3_n = b3*cos_3 - d3*sin_3;
  double c3_n = a3*sin_3 + c3*cos_3;
  double d3_n = b3*sin_3 + d3*cos_3;

  double [] a = new double [] { a1_n,  a2_n,      a3_n,  0.00};
  double [] b = new double [] { b1_n,  b2_n,      b3_n,  0.00};
  double [] e = new double [] { 0.00,  0.00,  0.00,  0.00};
  double [] c = new double [] { c1_n,  c2_n,      c3_n,  0.00};
  double [] d = new double [] { d1_n,  d2_n,      d3_n,  0.25};
  double [] f = new double [] { 0.20,  0.15,  0.18,  0.00};
  double [] p = new double [] { 0.75,  0.10,  0.10,  0.05};
public void reset() {
    t = 0.0;
    frame.setPreferredMinMax(-0.15, 0.35, 0.0, 0.35);
    frame.setSize(1080, 1080);
    frame.setConnected(false);
    frame.setMaximumPoints(0,2147483647);
    frame.setMaximumPoints(1,2147483647);
    frame.setMaximumPoints(2,2147483647);
    frame.setMaximumPoints(3,2147483647);
    frame.setMaximumPoints(4,2147483647);
    frame.setMaximumPoints(5,2147483647);
    frame.setMaximumPoints(6,2147483647);
    frame.setMaximumPoints(7,2147483647);
    frame.setMaximumPoints(8,2147483647);

	  frame.setMarkerSize(0,0);
    frame.setMarkerSize(1,0);
    frame.setMarkerSize(2,0);
    frame.setMarkerSize(3,0);
    frame.setMarkerSize(4,0);
    frame.setMarkerSize(5,0);

    //frame.addDrawable(circle);
    control.setValue("x0", "0.0");
    control.setValue("y0", "0.441");

    control.setValue("w1", "-65.0");
    control.setValue("w2", "75.0");
    control.setValue("w3", "-290.0");

    control.setValue("nspeed", "10000");
    initialize();
  }

  /**
   * Initializes the simulation.
   */
  public void initialize() {
    x = control.getDouble("x0");
    y = control.getDouble("y0");
    w1 = control.getDouble("w1");
    w2 = control.getDouble("w2");
    w3 = control.getDouble("w3");

    nspeed = control.getInt("nspeed");
    t = 0.0;

    cos_1 = Math.cos(Math.toRadians(w1));
    sin_1 = Math.sin(Math.toRadians(w1));

    cos_2 = Math.cos(Math.toRadians(w2));
    sin_2 = Math.sin(Math.toRadians(w2));

    cos_3 = Math.cos(Math.toRadians(w3));
    sin_3 = Math.sin(Math.toRadians(w3));

    a1 = 0.80;   b1 =   0.04;
    c1 =-0.04;   d1 =   0.80;

    a1_n = a1*cos_1 - c1*sin_1;
    b1_n = b1*cos_1 - d1*sin_1;
    c1_n = a1*sin_1 + c1*cos_1;
    d1_n = b1*sin_1 + d1*cos_1;

    a2 = 0.23;   b2 =  -0.23;
    c2 = 0.23;   d2 =   0.23;

    a2_n = a2*cos_2 - c2*sin_2;
    b2_n = b2*cos_2 - d2*sin_2;
    c2_n = a2*sin_2 + c2*cos_2;
    d2_n = b2*sin_2 + d2*cos_2;

    a3 = 0.23;   b3 =  0.23;
    c3 =-0.23;   d3 =  0.23;

    a3_n = a3*cos_3 - c3*sin_3;
    b3_n = b3*cos_3 - d3*sin_3;
    c3_n = a3*sin_3 + c3*cos_3;
    d3_n = b3*sin_3 + d3*cos_3;

    a[0] = a1_n;  b[0] = b1_n;
    c[0] = c1_n;  d[0] = d1_n;

    a[1] = a2_n;  b[1] = b2_n;
    c[1] = c2_n;  d[1] = d2_n;

    a[2] = a3_n;  b[2] = b3_n;
    c[2] = c3_n;  d[2] = d3_n;

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
           i=2;
         }

         xplot = x*a[i] + y*b[i] + e[i];
         yplot = x*c[i] + y*d[i] + f[i];
         x = xplot;
         y = yplot;

    frame.append(2, x, y);
    t++;

    //circle.setXY(x, y);
    frame.setMessage("Iterations : "+t, 1);
    }	// end of for-loop
  }

  public static void main(String[] args) {
    SimulationControl.createApp(new Fractal5());
  }
}
