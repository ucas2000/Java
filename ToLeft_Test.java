package org.example;

import java.util.List;

/**
 * @Description
 * @Author: lyc
 * @Date: 2025/1/5
*/
class Point{
    double x;
    double y;
    public Point(double lon, double lat){
        x = lon;
        y = lat;
    }
}


public class ToLeft_Test {
    public static void main(String[] args) {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(5, 0);
        Point p3 = new Point(5, 5);
        Point p4 = new Point(0, 5);
        List<Point> polygon = List.of(p1, p2, p3, p4);
        Point testPoint1 = new Point(3, 3);
        Point testPoint2 = new Point(10, 1);

        if(isPolygon(testPoint1, polygon)){
            System.out.println("点在多边形内");
        }else {
            System.out.println("点不在多边形内");
        }
        System.out.println("#############");
        if(isPolygon(testPoint2, polygon)){
            System.out.println("点在多边形内");
        }else {
            System.out.println("点不在多边形内");
        }
    }

    //整个算法的时间复杂度是 O(n)，其中 n 是多边形的顶点数。
    public static boolean isPolygon(Point p, List<Point> polygon){
        int n = polygon.size();
        boolean isInside = true;

        for(int i = 0; i < n; i++){
            Point current = polygon.get(i);
            Point next = polygon.get((i + 1) % n);
            if(!ToleftTest(p, current, next)) {
                isInside = false;
                break;
            }
        }
        return isInside;
    }
    public static boolean ToleftTest(Point p, Point q, Point s){
        return area(p,q,s) > 0;
    }
    //    计算三点p, q, s组成的有向面积
//    px*qy-py*qx+qx*sy-qy*sx+sx*py-sy*px
    private static double area(Point p, Point q, Point s) {
        return p.x * q.y - p.y* q.x + q.x * s.y - q.y * s.x + s.x * p.y - s.y * p.x;
    }
}
