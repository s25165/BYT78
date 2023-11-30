package a_Introductory;

public class Quadrilateral {
	private Point p1, p2, p3, p4;
	public Line l1, l2, l3, l4;
	
	Quadrilateral(Point p1, Point p2, Point p3, Point p4) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
		this.l1 = new Line(p1, p2);
		this.l2 = new Line(p2, p3);
		this.l3 = new Line(p3, p4);
		this.l4 = new Line(p4, p1);
	}
	
	public Boolean isRectangle() {
		Vector2D v1 = l1.getVector();
		Vector2D v2 = l2.getVector();
		Vector2D v3 = l3.getVector();
		Vector2D v4 = l4.getVector();
		
		return (v1.isOrthogonalTo(v2) &&
				v2.isOrthogonalTo(v3) &&
				v3.isOrthogonalTo(v4) &&
				v4.isOrthogonalTo(v1));
	}
	
	public Boolean isSquare() {
		return (isRectangle() &&
				l1.isSameLengthAs(l2));
		//l1 and l3 are parallel so if they are the same it doesn't mean all sides are the same
		//l1 and l2 are orthogonal so if they are the same and all other sides are orthogonal
		//to each other, then every side has the same length
	}

}
