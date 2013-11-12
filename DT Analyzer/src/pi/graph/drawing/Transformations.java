package pi.graph.drawing;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class Transformations
{
	private Graph graph;
	private Rectangle content;
	private Dimension size;
	private double t_width;
	private double t_height;
	private int pressureAvoid;
	private int maxPressure;
	
	private double radius = 15.0d;
	private double degToRad = Math.PI / 180.0d;
	
	public Transformations(Graph graph)
	{
		this.graph = graph;
	}
	
	public void recalculate()
	{
		this.size = this.graph.getSize();
		this.content = this.graph.getDrawing().getContent();
		
		if (content == null)
		{
			content = new Rectangle (0, 0, 15000, 15000);
		}
		
		this.t_width = (double)(content.getWidth());
		this.t_height = (double)(content.getHeight());
		
		this.pressureAvoid = this.graph.getDrawing().getPressureAvoid();
		this.maxPressure = this.graph.getDrawing().getMaxPressure();
	}
	
	public void transformToCanvas(double x, double y, Point p)
	{
		p.x = (int) ((x - content.getX()) * (double)this.size.width / this.t_width);
		p.y = (int) ((y - content.getY()) * (double)this.size.height / this.t_height);
		p.y = this.size.height - p.y;
	}
	
	public void transformToCanvas(Rectangle R)
	{
		Point A = new Point();
		Point B = new Point();
		this.transformToCanvas(R.x, R.y, A);
		this.transformToCanvas(R.x + R.width, R.y + R.height, B);
		R.x = A.x;
		R.y = A.y;
		R.width = B.x - R.x;
		R.height = R.y - B.y;
	}
	
	public void transformAnglePointer(double azimuth, double altitude, Point p)
	{
		double r = this.radius * Math.cos(altitude * this.degToRad);
		p.x = (int) (r * Math.sin((azimuth + 0.0d) * this.degToRad));
		p.y = (int) (r * Math.cos((azimuth + 0.0d) * this.degToRad));
		
		double prop = size.width / size.height;
		
		p.x *= prop;
	}

	
	public int transformToPressure(int pressure)
	{
		int minAvoid = this.pressureAvoid;
		int maxAvoid = this.maxPressure - this.pressureAvoid;
		
		if (pressure >= this.maxPressure) return 4;
		else if (pressure < 0) return 0;
		
		int d = maxAvoid / 5;
		for (int i = 1; i <= 5; i++)
		{
			if (pressure <= minAvoid + d * i) return (i - 1);
		}
		
		return 4;
	}
}
