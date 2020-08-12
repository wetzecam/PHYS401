import java.awt.Shape;
import javax.swing.JFrame;
import org.opensourcephysics.frames.PlotFrame;

public class FRK {
  public static void main(String[] args) {

    double PI = 3.141592653589793;


    PlotFrame frame = new PlotFrame("X", "Y", "Trajectory");

    frame.setSize(600,600);
    frame.setConnected(true);

    frame.setMarkerSize(0,1);
    frame.setMarkerSize(1,1);
    frame.setMarkerSize(2,1);

    Magnetic electron_4 = new Magnetic();
    Magnetic electron_2 = new Magnetic();
    Magnetic electron_1 = new Magnetic();

    electron_4.dt= 0.4;

    electron_4.x = 1.0;
    electron_4.y = 0.0;
    electron_4.vx = 0.0;
    electron_4.vy = 0.5;

    electron_2.dt = 0.2;

    electron_2.x = 1.0;
    electron_2.y = 0.0;
    electron_2.vx = 0.0;
    electron_2.vy = 0.5;

    electron_1.dt = 0.1;

    electron_1.x = 1.0;
    electron_1.y = 0.0;
    electron_1.vx = 0.0;
    electron_1.vy = 0.5;

    frame.append(0,electron_4.x, electron_4.y);
    frame.append(1,electron_2.x, electron_2.y);
    frame.append(2,electron_1.x, electron_1.y);

  	  for(int n = 0;n<42;n++) {
        electron_4.FR4_b_step();
        frame.append(0,electron_4.x, electron_4.y);

        electron_2.FR4_b_step();
        frame.append(1,electron_2.x, electron_2.y);
        electron_2.FR4_b_step();
        frame.append(1,electron_2.x, electron_2.y);

        electron_1.FR4_b_step();
        frame.append(2,electron_1.x, electron_1.y);
        electron_1.FR4_b_step();
        frame.append(2,electron_1.x, electron_1.y);
        electron_1.FR4_b_step();
        frame.append(2,electron_1.x, electron_1.y);
        electron_1.FR4_b_step();
        frame.append(2,electron_1.x, electron_1.y);

        }// end of the for-loop
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }

}
