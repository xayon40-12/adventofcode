import java.awt.*;
import java.util.HashMap;

public class Day3 {
    public static int square(int n){
        return 4*n*(n+1) + 1;
    }

    public static int level(int v){
        //4*l*(l+1) + 1=number of values inside the ring l included
        //the center is ring number 0 : 4*0*(0+1) + 1 = 1 there is 1 value
        //value from 2 to 9 is ring 1 : 4*1*(1+1) + 1 = 9 : there are 9 values inside the ring l
        //to know on which ring is v we need to solve : 4*l*(l+1) + 1 = v
        //so solve : l*(l+1) = (v-1)/4 <=> l^2 + l - (v-1)/4 = 0
        //them classical method for second degree equation
        double d = 1+v-1;//determinant
        double r = (-1+Math.sqrt(d))/2;//positive solution (the negative one isn't meaningful here)
        //we want a natural number whereas the solution r is a real number between l-1 and l
        //so we use the ceil of this value to have l
        return (int)Math.ceil(r);
    }

    public static Point pos(int v){
        int l = level(v);//get the level
        int s = square(l);//get the number of value inside ring l
        // s-v give the position on the ring: if v=7 s-v=9-7=2
        //  5 4 3   v=7     4 5 6
        //  6 1 2   s-v =>  3 * 7
        //  7 8 9   s=9     2 1 0

        //if we divide by 2*l we will have the number of the side
        //    2
        //  1   3
        //    0
        int side = (s-v)/(2*l);
        //if we compute the modulus of s-v by 2*l : (s-v)%(2*l) we will have the position on th side
        //  0 1 0
        //  1   1
        //  0 1 0
        //l is half of the length of the side minus 1
        //so if we need to compute l-(s-v)%(2*l) we'll have the distance from the middle of the side
        int dist = l-(s-v)%(2*l);
        //there is 4 side so 4 cases, but there is a symmetry: top/bottom with opposite direction, same for left/right
        //sides 0 and 2 are bottom and top, sides 1 and 3 are left and right
        //    2                  0
        //  1   3   side%2 =>  1   1
        //    0                  0
        //the distance between the center of the side and the point (0,0) is the value of the ring level so l
        //the distance on the side to the center of the side is what was computed above : dist
        //last to do is to know if each distance is positive or negative
        //to do that we use the number of the side
        //side = 1 or 3 : side-2 = -1 or 1
        //side = 0 or 2 : side-1 = -1 or 1
        //with side-2 and side-1 depending on which side we are permit to know if the distance is positive or negative
        int x = side%2==1?l*(side-2):dist*(1-side);// 1-side so -(side-1) because the ring is clockwise (=anti-trigonometric)
        int y = side%2==0?l*(side-1):dist*(side-2);
        return new Point(x, y);
    }

    public static int distance(int v){
        //1 is at coord 0,0 so the distance is just the sum the distance x and y of the value (absolute value of the coord)
        Point p = pos(v);
        return Math.abs(p.x) + Math.abs(p.y);
    }

    public static void main(String args[]){
        int puzzle = 368078;

        //part 1
        System.out.println("d:"+distance(puzzle));//just print the Manhattan distance

        //part 2
        HashMap<Point, Integer> map = new HashMap<>();
        int result = -1;
        Point p0 = null, p1 = new Point(0,0);
        map.put(p1, 1);
        for(int i = 2;result == -1;i++){
            p0 = p1;
            p1 = pos(i);
            if(map.get(p0) > puzzle){
                result = map.get(p0);
            }else{
                int sum = 0;
                for(int j = 2;j<=9;j++){
                    Point dir = pos(j), tmp = new Point(p1.x+dir.x, p1.y+dir.y);
                    if(map.containsKey(tmp)){
                        sum += map.get(tmp);
                    }
                }
                map.put(p1, sum);
            }
        }
        System.out.println("result:"+result);
    }
}
