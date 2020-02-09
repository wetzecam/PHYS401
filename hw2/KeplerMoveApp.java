import java.io.*;

public class KeplerMoveApp {

	public static void RK2step(){		// Runge-Kutta

	}

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

 	    MovingObject planet1a =new MovingObject();
			MovingObject planet1b =new MovingObject();
			MovingObject planet2a =new MovingObject();
			MovingObject comet =new MovingObject();

	    planet1a.dt = dt;
	    planet1a.x  = x0;
	    planet1a.vx = vx0;
	    planet1a.y  = y0;
	    planet1a.vy = vy0;

			planet1b.dt = dt;
	    planet1b.x  = x0;
	    planet1b.vx = vx0;
	    planet1b.y  = y0;
	    planet1b.vy = vy0;

			planet2a.dt = dt;
	    planet2a.x  = x0;
	    planet2a.vx = vx0;
	    planet2a.y  = y0;
	    planet2a.vy = vy0;

			comet.dt = dt;
			comet.x  = x0;
			comet.vx = vx0;
			comet.y  = y0;
			comet.vy = vy0;

			planet1a.energy();
			double E0_1a = planet1a.E;
			double E_err_1a;

			planet1b.energy();
			double E0_1b = planet1b.E;
			double E_err_1b;

			planet2a.energy();
			double E0_2a = planet2a.E;
			double E_err_2a;

			comet.energy();
			double E0_comet = comet.E;
			double E_err_comet;

	      try{
	    	  FileWriter wtraject_1a = new FileWriter("traject1a.txt");
	    	  BufferedWriter trajectout_1a = new BufferedWriter(wtraject_1a);
					FileWriter wtraject_1b = new FileWriter("traject1b.txt");
	    	  BufferedWriter trajectout_1b = new BufferedWriter(wtraject_1b);
					FileWriter wtraject_2a = new FileWriter("traject2a.txt");
	    	  BufferedWriter trajectout_2a = new BufferedWriter(wtraject_2a);

					FileWriter wtraject_com = new FileWriter("traject_comet.txt");
					BufferedWriter trajectout_comet = new BufferedWriter(wtraject_com);

					FileWriter wenergy_1a = new FileWriter("energy1a.txt");
					BufferedWriter energyout_1a = new BufferedWriter(wenergy_1a);
					FileWriter wenergy_1b = new FileWriter("energy1b.txt");
					BufferedWriter energyout_1b = new BufferedWriter(wenergy_1b);
					FileWriter wenergy_2a = new FileWriter("energy2a.txt");
					BufferedWriter energyout_2a = new BufferedWriter(wenergy_2a);
					FileWriter wenergy_comet = new FileWriter("energyComet.txt");
					BufferedWriter energyout_comet = new BufferedWriter(wenergy_comet);

	    	   for(int n = 0;n<5*Period/dt;n++) {
                     planet1a.sym1astep(1.0);
										 planet1b.sym1bstep(1.0);
										 planet2a.sym2astep(1.0);
										 comet.RK2step();


										 t=t+dt;
										 t_p = t / Period;

										 planet1a.energy();
										 planet1b.energy();
										 planet2a.energy();
										 comet.energy();


										 E_err_1a = (planet1a.E - E0_1a)/Math.abs(E0_1a);
										 energyout_1a.write(t_p+"  "+E_err_1a + "\n");

										 E_err_1b = (planet1b.E - E0_1b)/Math.abs(E0_1b);
										 energyout_1b.write(t_p+"  "+E_err_1b + "\n");

										 E_err_2a = (planet2a.E - E0_2a)/Math.abs(E0_2a);
										 energyout_2a.write(t_p+"  "+E_err_2a + "\n");

										 E_err_comet = (comet.E - E0_comet)/Math.abs(E0_comet);
										 energyout_comet.write(t_p+"  "+E_err_comet+"\n");

		      		 			 trajectout_1a.write(planet1a.x+"  "+planet1a.y+'\n');
										 trajectout_1b.write(planet1b.x+"  "+planet1b.y+'\n');
										 trajectout_2a.write(planet2a.x+"  "+planet2a.y+'\n');
										 trajectout_comet.write(comet.x+"  "+comet.y+"\n");

	    		} //end of loop
	    	  trajectout_1a.close();
					trajectout_1b.close();
					trajectout_2a.close();
					trajectout_comet.close();
					energyout_1a.close();
					energyout_1b.close();
					energyout_2a.close();
					energyout_comet.close();
				//close output
	    	  }catch (Exception e){//Catch exception if any
	    	   System.err.println("Error: " + e.getMessage());
	    	                      }
		System.out.println("  ");
		System.out.println("All done!");
	}

}
