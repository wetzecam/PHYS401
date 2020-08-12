import java.awt.*;
import org.opensourcephysics.display.*;

public class MovingRObject implements Drawable {

	   double cof,dt,t;
	   double x,y,vx,vy,E;
	   private double ax,ay,r,r2;
	   Trail trail = new Trail();
     int nspeed;
		 double rcos,rsin;

       public MovingRObject(){System.out.println("A new moving object is created.");
       }
//------------------object properties
	   public void energy(){
			E=0.5*(vx*vx+vy*vy)-1./Math.sqrt(x*x+y*y);
	   }

	   public void accel(double t){
			double dx1 = x - (0.5)*Math.cos(t);
			double dy1 = y - (0.5)*Math.sin(t);

			double dr1_2 = dx1*dx1 + dy1*dy1;
			double dr1 = Math.sqrt(dr1_2);

			double dx2 = x + (0.5)*Math.cos(t);
			double dy2 = y + (0.5)*Math.sin(t);

			double dr2_2 = dx2*dx2 + dy2*dy2;
			double dr2 = Math.sqrt(dr2_2);
			rcos = Math.cos(t);
			rsin = Math.sin(t);

			//r2 =x*x+y*y;
			//r  =Math.sqrt(r2);
			ax= (-0.5)*((dx1/dr1)/dr1_2 + (dx2/dr2)/dr2_2);
			ay= (-0.5)*((dy1/dr1)/dr1_2 + (dy2/dr2)/dr2_2);
	   }
//------------------object motion
	   public void positionstep(double cof){
	    			      x = x+vx*dt*cof;
	    			      y = y+vy*dt*cof;
	   }
	   public void velocitystep(double cof){
	                      accel(t);
	    			      vx = vx+ax*dt*cof;
	    			      vy = vy+ay*dt*cof;
	   }
     public void sym1bstep(double cof){
	    			  positionstep(cof);
	    			  velocitystep(cof);
	   }
	   public void sym1astep(double cof){
	    			  velocitystep(cof);
	    			  positionstep(cof);
	   }
	   public void sym2bstep(double cof){
	    			  positionstep(0.5*cof);
							t += 0.5*cof*dt;
	    			  velocitystep(1.0*cof);
	    			  positionstep(0.5*cof);
							t += 0.5*cof*dt;

	   }
	   public void sym2astep(double cof){
	    			  velocitystep(0.5*cof);
	    			  positionstep(1.0*cof);
	    			  velocitystep(0.5*cof);
	   }
     public void RK2step(){
			 						double x_0 = x;
									double y_0 = y;
									double vx_0 = vx;
									double vy_0 = vy;
									this.sym1astep(1);
									double x_1a = x;
									double y_1a = y;
									double vx_1a = vx;
									double vy_1a = vy;
									x = x_0;
									y = y_0;
									vx = vx_0;
									vy = vy_0;
									this.sym1bstep(1);
									x = (x + x_1a)/2;
									y = (y + y_1a)/2;
									vx = (vx + vx_1a)/2;
									vy = (vy + vy_1a)/2;
									return;
		 }
     public void RK4_a_step(){
       double s =  Math.pow(2,1/3);
       double eps = 1 / (2 - s);
                  double x_0 = this.x;
                  double y_0 = this.y;
                  double vx_0 = this.vx;
                  double vy_0 = this.vy;
                  this.sym2astep(eps/2);
                  this.sym2astep(eps/2);
                  double x_n = this.x;
                  double y_n = this.y;
                  double vx_n = this.vx;
                  double vy_n = this.vy;
                  x = x_0;
									y = y_0;
									vx = vx_0;
									vy = vy_0;
                  this.sym2astep(eps);
                  x = (4/3)*x_n - (1/3)*x;
                  y = (4/3)*y_n - (1/3)*y;
                  vx = (4/3)*vx_n - (1/3)*vx;
                  vy = (4/3)*vy_n - (1/3)*vy;
                  return;
     }
     public void RK4_b_step(){
       double s =  Math.pow(2,1/3);
       double eps = 1 / (2 - s);
       double x_0 = this.x;
       double y_0 = this.y;
       double vx_0 = this.vx;
       double vy_0 = this.vy;
       this.sym2bstep(eps/2);
       this.sym2bstep(eps/2);
       double x_n = this.x;
       double y_n = this.y;
       double vx_n = this.vx;
       double vy_n = this.vy;
       x = x_0;
       y = y_0;
       vx = vx_0;
       vy = vy_0;
       this.sym2bstep(eps);
       x = (4/3)*x_n - (1/3)*x;
       y = (4/3)*y_n - (1/3)*y;
       vx = (4/3)*vx_n - (1/3)*vx;
       vy = (4/3)*vy_n - (1/3)*vy;
       return;
     }
     public void FR4_a_step(){
                  double s =  Math.pow(2,1/3);
                  double eps = 1 / (2 - s);
                  this.sym2astep(eps);
                  this.sym2astep(-s*eps);
                  this.sym2astep(eps);
                  return;
     }
     public void FR4_b_step(){
                  double s =  Math.pow(2,1/3);
                  double eps = 1 / (2 - s);
                  this.sym2bstep(eps);
                  this.sym2bstep(-s*eps);
                  this.sym2bstep(eps);
                  return;
     }

     public void doStep(double cof){
              sym2bstep(cof);
			        //t=t+dt;
              trail.addPoint(x*rcos + y*rsin, y*rcos - x*rsin);
	   }
     public void draw_trans(DrawingPanel panel, Graphics g) {
	        int irad=8;
            int xpix = panel.xToPix(0.5*Math.cos(t))-irad;
            int ypix = panel.yToPix(0.5*Math.sin(t))-irad;   //sun at the origin
            g.setColor(Color.BLUE);
            g.fillOval(xpix, ypix, 2*irad, 2*irad);

						// Second Sun
					 xpix = panel.xToPix(-0.5*Math.cos(t))-irad;
					 ypix = panel.yToPix(-0.5*Math.sin(t))-irad;   //sun at the origin
				   g.setColor(Color.GREEN);
				   g.fillOval(xpix, ypix, 2*irad, 2*irad);
	        irad=5;            //smaller moving planet
            xpix = panel.xToPix(x)-irad;
            ypix = panel.yToPix(y)-irad;
            g.setColor(Color.RED);
            g.fillOval(xpix, ypix, 2*irad, 2*irad);
            trail.draw(panel, g);
       }

			 public void draw(DrawingPanel panel, Graphics g) {
		        int irad=8;
	            int xpix = panel.xToPix(0.5*Math.cos(0.0))-irad;
	            int ypix = panel.yToPix(0.5*Math.sin(0.0))-irad;   //sun at the origin
	            g.setColor(Color.BLUE);
	            g.fillOval(xpix, ypix, 2*irad, 2*irad);

							// Second Sun
						 xpix = panel.xToPix(-0.5*Math.cos(0.0))-irad;
						 ypix = panel.yToPix(-0.5*Math.sin(0.0))-irad;   //sun at the origin
					   g.setColor(Color.GREEN);
					   g.fillOval(xpix, ypix, 2*irad, 2*irad);
		        irad=5;            //smaller moving planet
	            xpix = panel.xToPix(x*rcos + y*rsin)-irad;
	            ypix = panel.yToPix(y*rcos - x*rsin)-irad;
	            g.setColor(Color.RED);
	            g.fillOval(xpix, ypix, 2*irad, 2*irad);
	            trail.draw(panel, g);
	     }


}
