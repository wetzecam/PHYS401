public class Magnetic {

	   double cof,dt;
	   double x,y,vx,vy,E;
	   private double ax,ay,r,r2, theta;
	   private double s,fs,bs,eps;
	   private double vxold,vyold,xmid,ymid,xold,yold;
       public Magnetic(){System.out.println("A new moving object is created.");
       }
//------------------object properties
	   public void energy(){
			E=0.5*(vx*vx+vy*vy)-1./Math.sqrt(x*x+y*y);
	   }
	   public void accel(){
			theta = dt/x/x;

			ax=  (-vy)*Math.sin(theta) - (vx)*(1. - Math.cos(theta));
			ay=  (vx)*Math.sin(theta) - (vy)*(1. - Math.cos(theta));
	   }
//------------------object motion
	   public void positionstep(double cof){
	    			      x = x+vx*dt*cof;
	    			      y = y+vy*dt*cof;
	   }
	   public void velocitystep(double cof){
	                      //accel();
												theta = cof*dt/x/x;

												ax=  (-vy)*Math.sin(theta) - (vx)*(1. - Math.cos(theta));
												ay=  (vx)*Math.sin(theta) - (vy)*(1. - Math.cos(theta));
	    			      vx = vx+ax;
	    			      vy = vy+ay;
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
	    			  velocitystep(1.0*cof);
	    			  positionstep(0.5*cof);
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
									this.sym1astep(1.0);
									double x_1a = x;
									double y_1a = y;
									double vx_1a = vx;
									double vy_1a = vy;
									x = x_0;
									y = y_0;
									vx = vx_0;
									vy = vy_0;
									this.sym1bstep(1.0);
									x = (x + x_1a)/2.;
									y = (y + y_1a)/2.;
									vx = (vx + vx_1a)/2.;
									vy = (vy + vy_1a)/2.;
									return;
		 }
     public void RK4_a_step(){
       double s =  Math.pow(2.,1/3.);
       double eps = 1. / (2. - s);
                  double x_0 = this.x;
                  double y_0 = this.y;
                  double vx_0 = this.vx;
                  double vy_0 = this.vy;
                  this.sym2astep(eps/2.);
                  this.sym2astep(eps/2.);
                  double x_n = this.x;
                  double y_n = this.y;
                  double vx_n = this.vx;
                  double vy_n = this.vy;
                  x = x_0;
									y = y_0;
									vx = vx_0;
									vy = vy_0;
                  this.sym2astep(eps);
                  x = (4./3.)*x_n - (1./3.)*x;
                  y = (4./3.)*y_n - (1./3.)*y;
                  vx = (4./3.)*vx_n - (1./3.)*vx;
                  vy = (4./3.)*vy_n - (1./3.)*vy;
                  return;
     }
     public void RK4_b_step(){
       double s =  Math.pow(2.,1./3.);
       double eps = 1. / (2. - s);
       double x_0 = this.x;
       double y_0 = this.y;
       double vx_0 = this.vx;
       double vy_0 = this.vy;
       this.sym2bstep(eps/2.);
       this.sym2bstep(eps/2.);
       double x_n = this.x;
       double y_n = this.y;
       double vx_n = this.vx;
       double vy_n = this.vy;
       x = x_0;
       y = y_0;
       vx = vx_0;
       vy = vy_0;
       this.sym2bstep(eps);
       x = (4./3.)*x_n - (1./3.)*x;
       y = (4./3.)*y_n - (1/3.)*y;
       vx = (4./3.)*vx_n - (1./3.)*vx;
       vy = (4./3.)*vy_n - (1./3.)*vy;
       return;
     }
     public void FR4_a_step(){
                  double s =  Math.pow(2.,1./3.);
                  double eps = 1. / (2. - s);
                  this.sym2astep(eps);
                  this.sym2astep(-s*eps);
                  this.sym2astep(eps);
                  return;
     }
     public void FR4_b_step(){
                  s =  Math.pow(2.,1./3.);
                  eps = 1. / (2. - s);
                  this.sym2bstep(eps);
                  this.sym2bstep(-s*eps);
                  this.sym2bstep(eps);
                  return;
     }

}
