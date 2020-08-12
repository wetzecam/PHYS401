import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;
import java.awt.Shape;
import javax.swing.JFrame;
import org.opensourcephysics.frames.PlotFrame;
import java.io.*;
import java.lang.*;


public class Hennon extends AbstractSimulation {
  PlotFrame frame = new PlotFrame("x", "y", "Hennon");
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
    PlotFrame frame = new PlotFrame("X", "Y", "Hennon");

    frame.setSize(600,600);
    frame.setMaximumPoints(0,1000000000);

    frame.setMarkerSize(0,1);

    double dr = 0.25/500.0;
    double r = 0.75;
    int N = 900;
    int M = 300;
    int qq = 0;

    double a = 1.4;
    double b = 0.3;

    double X = 0.65;
    double Y = -0.45;
    //frame.append(0,X,Y);

    for(int n=0; n < 100000; ){
      double xold = X;
      X = Y + 1 - a*X*X;
      Y = b*xold;
      if((X >= 0.6305) && (X <= 0.6325) && (Y >= 0.1889) && (Y <= 0.1895)){
        frame.append(0,X,Y);
        n++;
      }


    }

    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }
}
