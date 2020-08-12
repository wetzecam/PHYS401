import java.awt.Shape;
import javax.swing.JFrame;
import org.opensourcephysics.frames.PlotFrame;

public class KeplerMovePlotApp_b {
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

    MagneticObject planet =new MagneticObject();
    MagneticObject asteroid = new MagneticObject();

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
    asteroid.energy();
    double E0_planet = planet.E;
    double E0_asteroid = asteroid.E;
    double E_err_planet, E_err_asteroid;

  double x;
  double dx=0.2;

 PlotFrame frame = new PlotFrame("X", "Y", "Trajectory");
 PlotFrame eframe = new PlotFrame("t/P Normalized Time", "Energy", "Orbital Energy Error (3)");

    frame.setSize(600,600);
    frame.setConnected(true);
    eframe.setSize(600,600);
    eframe.setConnected(true);

    //frame.setMarkerShape(0,1);
    //frame.setMarkerShape(1,2);
    frame.setMarkerSize(0,5);
    frame.setMarkerSize(1,1);
    eframe.setMarkerSize(0,2);
    eframe.setMarkerSize(1,2);
    //frame.setMarkerSize(2,5);
    //frame.setMarkerSize(3,5);



  	  for(int n = 0;n<1*Period/dt;n++) {
        planet.RK4_b_step();
        asteroid.FR4_b_step();

        planet.energy();
        asteroid.energy();

        E_err_planet = planet.E - E0_planet;
        E_err_asteroid = asteroid.E - E0_asteroid;

        t=t+dt;
        t_p = t / Period;

        x=n*dx;

	          frame.append(0, planet.x, planet.y);
	          frame.append(1, asteroid.x, asteroid.y);
        if(t_p >= 0.45 && t_p <= 0.55){
            eframe.append(0, t_p, E_err_planet);
            eframe.append(1, t_p, E_err_asteroid);
        }
    //frame.append(2, x, Math.exp(-0.5*x*x));
    //frame.append(3, x, Math.exp(-0.5*x)*x);
        }// end of the for-loop
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    eframe.setVisible(true);
    eframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

}
