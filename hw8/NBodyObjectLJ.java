import java.awt.*;
import org.opensourcephysics.display.*;

public class NBodyObjectLJ implements Drawable {

	   double cof,dt,t,E,L=10.0;
	   int nspeed,nbody;
	   private double dx,dy,b,dr,dr2, dr8, dr14, dr6, dr12,xnew,ynew;
       final static int arraysize = 100;

	   Trail trail = new Trail();
	   double x[]=new double[arraysize];
	   double y[]=new double[arraysize];
	   double vx[]=new double[arraysize];
	   double vy[]=new double[arraysize];
	   double ax[]=new double[arraysize];
	   double ay[]=new double[arraysize];
	   double r[] =new double[arraysize];
	   double r2[]=new double[arraysize];


       public NBodyObjectLJ(){System.out.println("A new NBody object is created.");
       }
//-------------object properties
//	   public void energy(){
//			E=0.5*(vx*vx+vy*vy)-1./Math.sqrt(x*x+y*y);
//	   }
		public double pbcSeparation(double ds){
			if(ds > 0.5*L){
				ds-=L;
			} else if(ds < -0.5*L){
				ds+=L;
			}
			return ds;
		}

		 public double kineticEnergy(){
			 double T = 0.0;
			 for(int i=0;i<nbody;i++){
				 T += (vx[i]*vx[i] + vy[i]*vy[i]);
			 }
			 return T;
		 }

		 public double interactionPotential(){
			 double V = 0.0;
			 for(int i = 0;i<nbody-1;i++) {
					 for(int j =i+1;j<nbody;j++) {
					 dx=pbcSeparation(x[i]-x[j]);
					 dy=pbcSeparation(y[i]-y[j]);
					 dr2=dx*dx+dy*dy;
					 dr =Math.sqrt(dr2);
					 dr6 = 1.0/(dr2*dr2/dr2);
					 dr12 = dr6*dr6;

					 b=(dr12 - dr6)*4.0;
					 V+=b;
					 }// end j-loop
				}// end i-loop
				return V;
		 }

		 public double potentialEnergy(){
			 return interactionPotential();
		 }

		 public double totalEnergy(){
			 return potentialEnergy() + kineticEnergy();
		 }

	   public void accel(){
            for(int i = 0;i<nbody;i++) {
 			r2[i] =x[i]*x[i]+y[i]*y[i];
			r[i]  =Math.sqrt(r2[i]);
//			ax[i]=-nbody*x[i]/r[i]/r2[i];
//			ay[i]=-nbody*y[i]/r[i]/r2[i];
			ax[i]=-x[i]*r2[i]*r2[i]*.0001;
			ay[i]=-y[i]*r2[i]*r2[i]*.0001;
//			ax[i]=-x[i];
//			ay[i]=-y[i];
            }// end for-loop

	   }
	   public void nbaccel(){
           for(int i = 0;i<nbody;i++) {
			ax[i]=0.0;
			ay[i]=0.0;
            }// end for-loop

            //  accel();
        for(int i = 0;i<nbody-1;i++) {
            for(int j =i+1;j<nbody;j++) {
						dx=pbcSeparation(x[i]-x[j]);
	 					dy=pbcSeparation(y[i]-y[j]);

 		        dr2=dx*dx+dy*dy;
			    	dr =Math.sqrt(dr2);
						dr8 = 1.0/(dr2*dr2*dr2*dr2);
						dr14= 1.0/(dr2*dr2*dr2*dr2*dr2*dr2*dr2);
						b=48.0*(dr8-dr14);
		    		ax[i]=ax[i]+b*dx;
		    		ay[i]=ay[i]+b*dy;
		    		ax[j]=ax[j]-b*dx;
		    		ay[j]=ay[j]-b*dy;
            }// end j-loop
         }// end i-loop

	   }
//------------------object motion
	   public void positionstep(double cof){
            for(int i = 0;i<nbody;i++) {
									xnew = x[i]+vx[i]*dt*cof;
									ynew = y[i]+vy[i]*dt*cof;
	    			      x[i] = (xnew<0) ? (xnew%L+L) : (xnew%L);
	    			      y[i] = (ynew<0) ? (ynew%L+L) : (ynew%L);
            }// end for-loop
	   }
	   public void velocitystep(double cof){
	                      nbaccel();
            for(int i = 0;i<nbody;i++) {
	    			      vx[i] = vx[i]+ax[i]*dt*cof;
	    			      vy[i] = vy[i]+ay[i]*dt*cof;
	    			      //vx[i] = vx[i]*0.999;
	    			      //vy[i] = vy[i]*0.999;
            }// end for-loop
	   }
	   public void sym2bstep(double cof){
	    			  positionstep(0.5*cof);
	    			  velocitystep(1.0*cof);
	    			  positionstep(0.5*cof);
	   }
	   	   public void doStep(double cof){
               sym2bstep(cof);
			  		 	 t=t+dt;
               trail.addPoint(x[0], y[0]);
	   }
       public void draw(DrawingPanel panel, Graphics g) {
	        int irad=60;
            int xpix = panel.xToPix(0.0)-irad;
            int ypix = panel.yToPix(0.0)-irad;   //sun at the origin
            //g.setColor(Color.BLUE);
            //g.fillOval(xpix, ypix, 2*irad, 2*irad);

	        irad=50;            //smaller moving planet
         for(int i = 0;i<nbody;i++) {
            xpix = panel.xToPix(x[i])-irad;
            ypix = panel.yToPix(y[i])-irad;
            g.setColor(Color.RED);
            g.fillOval(xpix, ypix, 2*irad, 2*irad);
		 }
        //trail.draw(panel, g);
       }

}
