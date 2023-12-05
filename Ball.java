/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cs304;

/**
 *
 * @author Ahmed hany
 */
public class  Ball {
    int x,y,mode;
    int knockDx,knockDy;
    int xMode=0,yMode=0;
    int radarX=-1,radarY=-1,radarRadius=10;
    int x_dir=0,y_dir=1,knockBack=0,r=10,c=5,w=900,h=500;
    int[][] dir={{1,0},{0,-1},{-1,0},{0,1} };
    int[][] orbital=null;
    int orbitalIdx=0;
    int prevIdx=-1;
    Ball(){
        this.mode=0;
    }
    Ball(int x,int y){
        this.x=x;
        this.y=y;
        this.mode=0;
    }
    public static double dis(Ball b1,Ball b2){
        return Math.sqrt((b1.x-b2.x)*(b1.x-b2.x)+(b1.y-b2.y)*(b1.y-b2.y) );
    }
    public static double dis(int x1,int y1,int x2,int y2){
        return Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1) );
    }
    
    public static boolean isIntersectBall(Ball b1, Ball b2) {
    	double distance = dis(b1, b2);
    	return distance >=  Math.abs(b1.r - b2.r) && distance <= (b1.r + b2.r);
    }
    public void moveBallX(int dir,Rectangle[] bound){
        for(int i=c;i>=0;i--){
//            ok=1.cam ,mpve.ok=0,cant move
            int ok=1;
            for(int j=0;j<bound.length;j++)if(Rectangle.isIntersectRectBall(bound[j],new Ball(x+dir*i,y) ) ) ok=0;
            if(ok==1){
                x+=dir*i;
                return;
            }
        }
    }
    public void moveBallX(int dir,Rectangle[] bound,int c){
        for(int i=c;i>=0;i--){
//            ok=1.cam ,mpve.ok=0,cant move
            int ok=1;
            for(int j=0;j<bound.length;j++)if(Rectangle.isIntersectRectBall(bound[j],new Ball(x+dir*i,y) ) ) ok=0;
            if(ok==1){
                x+=dir*i;
                return;
            }
        }
    }
    public void moveBallY(int dir,Rectangle[] bound){
        for(int i=c;i>=0;i--){
            int ok=1;
            for(int j=0;j<bound.length;j++)if(Rectangle.isIntersectRectBall(bound[j],new Ball(x,y+dir*i) ) ) ok=0;
            if(ok==1){
                y+=dir*i;
                return;
            }
        }
    }
    public void moveBallY(int dir,Rectangle[] bound,int c){
        for(int i=c;i>=0;i--){
            int ok=1;
            for(int j=0;j<bound.length;j++)if(Rectangle.isIntersectRectBall(bound[j],new Ball(x,y+dir*i) ) ) ok=0;
            if(ok==1){
                y+=dir*i;
                return;
            }
        }
    }
    
    public void moveBall(Rectangle[] bound,int c){
        if(this.orbital==null)return;
        
        int nX=orbital[(orbitalIdx+1)%4][0];
		int nY=orbital[(orbitalIdx+1)%4][1];
        double dis=Math.sqrt((nX-x)*(nX-x)+(nY-y)*(nY-y));
//        System.out.println(dis);
//        System.out.println((nX-x)+" "+(nY-y));
        if(dis>5){
            moveBallX(dir[orbitalIdx][0],bound,c);
            moveBallY(dir[orbitalIdx][1],bound,c);
        }
        else{
            orbitalIdx=(orbitalIdx+1)%4;
            this.x=orbital[orbitalIdx][0];
            this.y=orbital[orbitalIdx][1];
        }
    }
    
    public void setOrbital(Rectangle r,int i){
        if(i>3) return;
        orbital=new int[4][2];
//        v1 okay
        orbital[0][0]=r.bottomX-this.r;
        orbital[0][1]=r.topY+this.r;
//        v2 okay
        orbital[1][0]=r.topX+this.r;
        orbital[1][1]=r.topY+this.r;
        
        orbital[2][0]=r.topX+this.r;
        orbital[2][1]=r.bottomY-this.r;
        
        orbital[3][0]=r.bottomX-this.r;
        orbital[3][1]=r.bottomY-this.r;
        
        this.x=orbital[i][0];
        this.y=orbital[i][1];
        orbitalIdx=i;
        for(int j=0;j<4;j++){
            int nX=orbital[j][0],nY=orbital[j][1];
//            System.out.println(nX+" "+nY);
        }
    }
    public int isBounded(Ball b,Rectangle[] bound){
        int ok=1;
        for(int j=0;j<bound.length;j++)if(Rectangle.isIntersectRectBall(bound[j],b ) ) ok=0;
        return ok;
    }
    
    public void moveTo(Ball b,Rectangle[] bound){
        int dx=b.x>this.x?1:-1;
        int dy=b.y>this.y?1:-1;
        if(knockBack!=0){
            this.x+=5*knockDx;
            this.y+=5*knockDy;
            knockBack--;
        }
        else if(isBounded(new Ball(this.x+dx,this.y),bound)==0){
            knockBack=10;
            knockDx=-1*dx;
            knockDy=0;
        }
        else if(isBounded(new Ball(this.x,this.y+dy),bound)==0){
            knockBack=10;
            knockDx=0;
            knockDy=-1*dy;
        }
        else{
            this.x+=dx;
            this.y+=dy;
        }
    }
    
    
    
    public void moveTo(Ball b,Rectangle[] bound,int c){
        int dx=b.x>this.x?1*c:-1*c;
        int dy=b.y>this.y?1*c:-1*c;
        if(knockBack!=0){
            Ball newBall=new Ball(this.x,this.y);
            newBall.moveBallX(knockDx, bound,5);
            newBall.moveBallY(knockDy, bound,5);
            this.x=newBall.x;
            this.y=newBall.y;
            knockBack--;
        }
        if(isBounded(new Ball(this.x+dx,this.y+dy),bound)==0){
//            go back 50;
            double rd=Math.random()*360;
//            this.x+=-2*dx*Math.cos(Math.toRadians(rd) );
//            this.y+=-2*dy*Math.sin(Math.toRadians(rd) );
            knockBack=10;
            knockDx=-1*dx;
            knockDy=-1*dy;
        }
        else{
            this.x+=dx;
            this.y+=dy;
        }
    }
}