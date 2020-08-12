import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.PlotFrame;

public class Fractal6 extends AbstractSimulation {
  PlotFrame frame = new PlotFrame("x", "y", "IFS Map");
  double t = 0, dt = 0.1;
  double x,y,xplot,yplot,term;
  int nspeed;

  double rcos_30 = Math.cos(30.0);
  double rsin_30 = Math.sin(30.0);
  double rcos_15 = Math.cos(15.0 + 29.75);
  double rsin_15 = Math.sin(15.0 + 29.75);
  double rcos_90 = Math.cos(90.0);
  double rsin_90 = Math.sin(90.0);
                              // stm,  A
  double [] a = new double [] { 0.01,  0.20, 0.15, 0.25,  0.05*rcos_90,  0.05*rcos_90,  0.1*rcos_30,  0.1*rcos_30, 0.1*rcos_15,  0.1*rcos_15, 0.1*rcos_15,  0.1*rcos_15};
  double [] b = new double [] { 0.00,  0.00, 0.00, 0.00, -0.3*rsin_90,  0.3*rsin_90, -0.45*rsin_30,  0.45*rsin_30, -0.6*rsin_15,  0.6*rsin_15, -0.1*rsin_15,  0.1*rsin_15};
  double [] e = new double [] { 0.00,  0.00, 0.00, 0.00,  0.00,         0.00,         0.00,         0.00,          0.00,        0.00, -0.10,        0.10};
  double [] c = new double [] { 0.00,  0.00, 0.00, 0.00,  0.05*rsin_90, -0.05*rsin_90,  0.1*rsin_30, -0.1*rsin_30,  0.1*rsin_15, -0.1*rsin_15,  0.1*rsin_15, -0.1*rsin_15};
  double [] d = new double [] { 0.50,  0.95, 0.95, 0.95,  0.2*rcos_90,  0.2*rcos_90,  0.45*rcos_30,  0.45*rcos_30,  0.6*rcos_15,  0.6*rcos_15, 0.1*rcos_15,  0.1*rcos_15};
  double [] f = new double [] { 0.00,  1.0, 0.75, 0.25,  0.20,         0.20,         0.20,         0.20,        0.20,         0.20,           0.30,         0.30};
  double [] p = new double [] { 0.05,  0.079, 0.079, 0.079, 0.079, 0.079, 0.079, 0.079, 0.079, 0.079, 0.079, 0.079};

public void reset() {
    t = 0.0;
    frame.setPreferredMinMax(-2.0, 2.0, -1.0, 3.0);
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
    control.setValue("nspeed", "10000");
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
         else if(rnd < (p[0]+p[1]+p[2]+p[3])){
           i=3;
         }
         else if(rnd < (p[0]+p[1]+p[2]+p[3]+p[4])){
           i=4;
         }
         else if(rnd < (p[0]+p[1]+p[2]+p[3]+p[4]+p[5])){
           i=5;
         }
         else if(rnd < (p[0]+p[1]+p[2]+p[3]+p[4]+p[5]+p[6])){
           i=6;
         }
         else if(rnd < (p[0]+p[1]+p[2]+p[3]+p[4]+p[5]+p[6]+p[7])){
           i=7;
         }
         else if(rnd < (p[0]+p[1]+p[2]+p[3]+p[4]+p[5]+p[6]+p[7]+p[8])){
           i=8;
         }
         else if(rnd < (p[0]+p[1]+p[2]+p[3]+p[4]+p[5]+p[6]+p[7]+p[8]+p[9])){
           i=9;
         }
         else if(rnd < (p[0]+p[1]+p[2]+p[3]+p[4]+p[5]+p[6]+p[7]+p[8]+p[9]+p[10])){
           i=10;
         }
         else{
           i=11;
         }

         xplot = x*a[i] + y*b[i] + e[i];
         yplot = x*c[i] + y*d[i] + f[i];
         x = xplot;
         y = yplot;

    frame.append(1, x, y);
    t++;

    //circle.setXY(x, y);
    frame.setMessage("Iterations : "+t, 1);
    }	// end of for-loop
  }

  public static void main(String[] args) {
    SimulationControl.createApp(new Fractal6());
  }
}
