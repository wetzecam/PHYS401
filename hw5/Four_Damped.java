import java.awt.Shape;
import javax.swing.JFrame;
import org.opensourcephysics.frames.PlotFrame;

public class Four_Damped {
  public static void main(String[] args) {

    double PI = 3.141592653589793;
    double dt = 0.4;
    double t = 0.0;


    PlotFrame frame = new PlotFrame("T", "Q", "Trajectory");

    frame.setSize(600,600);
    frame.setConnected(true);

    frame.setMarkerSize(0,1);
    frame.setMarkerSize(1,0);
    frame.setMarkerSize(2,1);


    Oscillator osc_1 = new Oscillator(1.0, 0.6, 1.0, 0.0);
    Oscillator osc_2 = new Oscillator(1.0, 0.6, 1.0, 0.0);
    Oscillator osc_3 = new Oscillator(1.0, 0.6, 1.0, 0.0);

    osc_1.dt = dt;
    osc_2.dt = dt;
    osc_3.dt = dt;

    //frame.append(0, t, osc_1.q);
    //frame.append(1, t, osc_2.q);
    frame.append(1, t, osc_1.exact_q(t));
    frame.append(0, t, osc_3.q);


  	  for(int n = 0;n<35;n++) {
        double s =  Math.pow(2.,1./3.);
  			double eps = 1. / (2. - s);
        t += dt*eps;

        //osc_1.sym1astep(1.0);
        //osc_2.sym2bstep(1.0);
        osc_3.RK4_b_step();

        //frame.append(0, t, osc_1.q);
        //frame.append(1, t, osc_2.q);
        frame.append(1, t-dt/2., osc_1.exact_q(t-dt/2.));
        frame.append(1, t, osc_1.exact_q(t));
        frame.append(1, t+dt/2., osc_1.exact_q(t+dt/2.));


        frame.append(0, t, osc_3.q);

        }// end of the for-loop
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }

}
