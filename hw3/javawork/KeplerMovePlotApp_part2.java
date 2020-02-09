import java.awt.Shape;
import javax.swing.JFrame;
import org.opensourcephysics.frames.PlotFrame;

public class KeplerMovePlotApp_part2 {
  public static void main(String[] args) {

    double PI = 3.141592653589793;

    double x0 = 10.;
    double vx0 = 0.;
    double y0 = 0.;
    double vy0 = 0.1;
    double t=0.;
    double t_p = 0.;
    double dt=0.1;

    double E0 = 0.5*(vx0*vx0 + vy0*vy0) - 1./Math.sqrt(x0*x0 + y0*y0);
    double a = -1/(2.*E0);
    double script_P = x0*x0*vy0*vy0;
    double ecc = Math.sqrt(1 - script_P/a);
    double Period = 2.*PI*Math.sqrt(a*a*a);

    System.out.println("Initial Energy = "+E0);
    System.out.println("Semi-Major axis = "+a);
    System.out.println("Script_P = "+script_P);
    System.out.println("Eccentricity = "+ecc);
    System.out.println("Orbit Period = "+Period);

    dt = Period / 1000.;

    double t_P = 0.0;
    double E;
    double E_err = 0.;

    MovingObject planet =new MovingObject();
    MovingObject asteroid = new MovingObject();

    planet.dt = dt;
    planet.x  = x0;
    planet.vx = vx0;
    planet.y  = y0;
    planet.vy = vy0;

    asteroid.dt = dt;
    asteroid.x  = x0;
    asteroid.vx = vx0;
    asteroid.y  = y0;
    asteroid.vy = vy0;

    planet.energy();
    double E0_planet = planet.E;
    double E_err_1a;

  double x;
  double dx=0.2;

 PlotFrame frame = new PlotFrame("X", "Y", "Orbital Trajectory");

    frame.setSize(600,600);
    frame.setConnected(true);

    //frame.setMarkerShape(0,1);
    //frame.setMarkerShape(1,2);
    frame.setMarkerSize(0,1);
    frame.setMarkerSize(1,1);
    //frame.setMarkerSize(2,5);
    //frame.setMarkerSize(3,5);



  	  for(int n = 0;n<5*Period/dt;n++) {
        planet.sym2bstep(1.0);
        asteroid.RK2step();
        t=t+dt;
        t_p = t / Period;

        x=n*dx;
	      frame.append(0, planet.x, planet.y);
	      frame.append(1, asteroid.x, asteroid.y);
    //frame.append(2, x, Math.exp(-0.5*x*x));
    //frame.append(3, x, Math.exp(-0.5*x)*x);
        }// end of the for-loop
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

}
