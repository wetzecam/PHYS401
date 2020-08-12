import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;
import java.awt.Shape;
import javax.swing.JFrame;
import org.opensourcephysics.frames.PlotFrame;
import java.io.*;
import java.lang.*;


public class Bifurcation3_2 extends AbstractSimulation {
  PlotFrame frame = new PlotFrame("r", "x", "Bifurcation");
  double t = 0, dt = 0.1;
  double x,y,xplot,yplot,term;
  int nspeed;
  final static double rcos = Math.cos(3.141592653*76.11/180.0);
  final static double rsin = Math.sin(3.141592653*76.11/180.0);
  Circle circle = new Circle(0, 0, 5);
  final static double y_o[] = {
    0.441,
    0.442
  };

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
    //SimulationControl.createApp(new ChaoticMapApp());
    PlotFrame frame = new PlotFrame("r", "X", "Bifurcation");

    frame.setSize(600,600);
    frame.setMaximumPoints(0,1000000000);

    frame.setMarkerSize(0,1);

    double dr = (0.898-0.886)/500.0;
    double r = 0.886;
    int N = 900;
    int M = 300;
    int qq = 0;

    while(r <= 0.898){
      double X = 0.7;
      /*if(r > 0.891){
        N = 600;
        M = 300;
        qq = 1;
      }*/
      for(int i=0; i < N; i++){
        double XN = 4.0 * r * X * (1.0 - X);
        X = XN;
      }
      for(int i=0; i < M; i++){
        double XN = 4.0 * r * X * (1.0 - X);
        X = XN;
        frame.append(qq, r, X);
      }

      r = r + dr;
    }

    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }
}
