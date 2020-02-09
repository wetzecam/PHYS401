import java.awt.Shape;
import javax.swing.JFrame;
import java.awt.Color;
import org.opensourcephysics.frames.PlotFrame;

public class PlotApp {
  public static void main(String[] args) {

  double x;
  double dx=0.2;


 PlotFrame frame = new PlotFrame("position", "amplitude", "Plot");

    frame.setSize(600,600);
    frame.setConnected(true);

    //frame.setMarkerShape(0,1);
    //frame.setMarkerShape(1,2);
    frame.setMarkerSize(0,2);
    frame.setMarkerSize(1,2);
    frame.setMarkerSize(2,2);
    frame.setMarkerSize(3,2);
    frame.setMarkerColor(3, Color.BLACK);
    //frame.setLineColor(3, Color.BLACK);

  	  for(int n = 0;n<50;n++) {
      x=n*dx;
	     frame.append(0, x, Math.sin(x)/x);
	     frame.append(1, x, Math.exp(-0.5*x));
       frame.append(2, x, Math.exp(-0.5*x*x));
       frame.append(3, x, Math.exp(-0.5*x)*x);
        }// end of the for-loop
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

}
