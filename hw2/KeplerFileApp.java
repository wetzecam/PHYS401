import java.io.*;

public class KeplerFileApp {

	public static void main(String[] args) {
			double PI = 3.141592653589793;

	    double x0 = 10.;
	    double vx0 = 0.;
	    double y0 = 0.;
	    double vy0 = 0.1;

	    double t = 0.0;
	    double dt = 0.1;
	    double x = x0;
	    double vx = vx0;
	    double y = y0;
	    double vy = vy0;

	    double ax, ay;
	    double r, r2;

			double E0 = 0.5*(vx*vx + vy*vy) - 1./Math.sqrt(x*x + y*y);
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

	      try{
	    	  FileWriter wtraject = new FileWriter("traject.txt");
	    	  BufferedWriter trajectout = new BufferedWriter(wtraject);

					FileWriter wenergy = new FileWriter("energy_01.txt");
	    	  BufferedWriter energyout = new BufferedWriter(wenergy);

					energyout.write(t_P+"  "+E_err+"\n");

	    	   for(int n = 0;n<5000;n++) {

						  r2 =x*x+y*y;
						  r  =Math.sqrt(r2);
						  ax=-x/r/r2;
						  ay=-y/r/r2;
	    			      vx = vx+ax*dt;
	    			      vy = vy+ay*dt;
	    			      x = x+vx*dt;
	    			      y = y+vy*dt;
	    				t = t+dt;
							t_P = t / Period;
							E = 0.5*(vx*vx + vy*vy) - 1./Math.sqrt(x*x + y*y);
							E_err = (E - E0) / Math.abs(E0);



		      trajectout.write(x+"  "+y);
		      trajectout.newLine();

					energyout.write(t_P+"  "+E_err+"\n");
	    		                        } //end of loop
	    	  trajectout.close();
					energyout.close();
				//close output
	    	  }catch (Exception e){//Catch exception if any
	    	   System.err.println("Error: " + e.getMessage());
	    	                      }
		System.out.println("  ");
		System.out.println("All done!");
	}

}
