import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;
import java.awt.Shape;
import javax.swing.JFrame;
import org.opensourcephysics.frames.PlotFrame;
import java.io.*;
import java.lang.*;


public class ChaoticMapApp extends AbstractSimulation {
  PlotFrame frame = new PlotFrame("x", "y", "Chaotic Map");
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
    PlotFrame frame = new PlotFrame("T", "Q", "Trajectory");

    frame.setSize(600,600);


    frame.setMarkerSize(0,1);
    try{
    FileWriter fw = new FileWriter("1a_output.txt");
    BufferedWriter fout = new BufferedWriter(fw);

    for(int i=0; i < y_o.length; i++){
      double X = 0.0;
      double Y = y_o[i];
      double Term, Xplot, Yplot;

      for(int n=0; n < 1000; n++){
        Term =Y-X*X;
        Xplot= rcos*X-rsin*Term;
        Yplot= rsin*X+rcos*Term;
        X=Xplot;
        Y=Yplot;

        System.out.println(X + "  " + Y + '\n');
        if((X <= 0.4) && (X >= 0.2) && (Y <= 0.6) && (Y >= 0.4)){
          frame.append(i%5, X, Y);
        }

        fout.write(X+"    "+Y);
        fout.newLine();
      }


      System.out.println( y_o[i] + '\n');


    }
    fout.close();
    }catch (Exception e){      //Catch exception if any
     System.err.println("Error: " + e.getMessage());
                        }
                        frame.setVisible(true);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }
}
